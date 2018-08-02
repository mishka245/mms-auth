package com.springsecurity.core.services;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Iterable<UserRegisterDTO> getUsers() {
        User user = repository.findByUserName(getCurrentUserName());
        List<User> list = repository.findByUserIdNotLike(user.getUserId());
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserRegisterDTO getUser() {
        User user = repository.findByUserName(getCurrentUserName());

        return mapToDTO(user);
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private UserRegisterDTO mapToDTO(User user) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setEmail(user.getEmail());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setIsActive(user.getIsActive());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setUserId(user.getUserId());

        return userRegisterDTO;
    }
}
