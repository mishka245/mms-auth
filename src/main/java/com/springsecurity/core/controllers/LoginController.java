package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.AuthenticationDTO;
import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.LoginDTO;
import com.springsecurity.core.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/token")
public class LoginController {

    private static final String TOKEN_HEADER = "X-Auth-Token";

    private long tokenExpireTime;

    private final LoginService loginService;

    @Value("${token.expire.time.minutes}")
    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime * 60 * 1000;
    }


    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/auth")
    public AuthenticationDTO getUserByName(@RequestBody LoginDTO loginDTO,
                                           HttpServletResponse response) {
        String token = loginService.getToken(loginDTO);
        response.addHeader(TOKEN_HEADER, token);
        return new AuthenticationDTO(Boolean.TRUE, tokenExpireTime);
    }

    @GetMapping(path = "/is-auth")
    public boolean isAuthenticated() {
        return loginService.isAuthenticated();
    }

    @GetMapping(path = "/get-name")
    public UserDTO getUserName() {
        return loginService.getUser();
    }
}