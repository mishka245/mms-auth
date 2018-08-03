package com.springsecurity.core.services;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.entities.UserRole;
import com.springsecurity.core.exceptions.DuplicateUsernameException;
import com.springsecurity.core.repositories.UserRepository;
import com.springsecurity.core.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Component
@Transactional
public class RegisterService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RegisterService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public UserRegisterDTO saveUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        UserRole role = userRoleRepository.findById(2).get();

        if (Objects.nonNull(userRepository.findByUserName(userRegisterDTO.getUserName()))) {
            throw new DuplicateUsernameException();
        }

        user.setRole(role);
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setUserName(userRegisterDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setBirthDate(userRegisterDTO.getBirthDate());
        user.setEmail(userRegisterDTO.getEmail());
        user.setIsActive(0);

        user = userRepository.save(user);


        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setEmail(user.getEmail());

        return userRegisterDTO;
    }
}
