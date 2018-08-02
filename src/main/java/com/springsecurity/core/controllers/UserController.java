package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/get-users")
    public Iterable<UserRegisterDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping(path = "/get-user")
    public UserRegisterDTO getUser() {
        return service.getUser();
    }

}
