package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/token")
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(path = "/register")
    public UserRegisterDTO register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return registerService.saveUser(userRegisterDTO);
    }
}
