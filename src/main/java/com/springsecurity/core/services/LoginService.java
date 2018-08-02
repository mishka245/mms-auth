package com.springsecurity.core.services;

import com.springsecurity.core.configurations.JWTTokenProvider;
import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.LoginDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.exceptions.UnauthorisedException;
import com.springsecurity.core.exceptions.UserNotFoundException;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public String getToken(LoginDTO loginDTO) {
        User user = Optional
                .ofNullable(this.userRepository.findByUserName(loginDTO.getUserName()))
                .orElseThrow(UserNotFoundException::new);
        user.setIsActive(1);
    if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            throw new UnauthorisedException();
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));

        return tokenProvider.generateToken(user);
    }

    public UserDTO getUser() {
        UserDTO dto = new UserDTO();
        User user = userRepository.findByUserName(getCurrentUserName());
        dto.setUserName(user.getUserName());
        dto.setId(user.getUserId());

        return dto;
    }

    public boolean isAuthenticated() {
        return !Objects.equals(getCurrentUserName(), "anonymousUser");
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
