package com.springsecurity.core.services;

import com.springsecurity.core.configurations.JWTTokenProvider;
import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.UserLoginDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.exceptions.UserNotFoundException;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@Component
@Transactional
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTTokenProvider tokenProvider;

    @Autowired
    public LoginService(UserRepository userRepository, JWTTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserLoginDTO authenticate(UserLoginDTO loginDTO) {
        User user = Optional
                .ofNullable(this.userRepository.findByUserName(loginDTO.getUserName()))
                .orElseThrow(UserNotFoundException::new);
        user.setIsActive(1);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setToken(tokenProvider.generateToken(user));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
        return userDTO;
    }

    public UserDTO getUser() {
        UserDTO dto = new UserDTO();
        User user = userRepository.findByUserName(getCurrentUserName());
        dto.setUserName(user.getUserName());
        dto.setId(user.getUserId());

        return dto;
    }

    public boolean isAuthenticated() {
        System.out.println(getCurrentUserName());
        return !Objects.equals(getCurrentUserName(), "anonymousUser");
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
