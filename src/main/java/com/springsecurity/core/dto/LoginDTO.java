package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank
    @JsonProperty("userName")
    private String userName;

    @NotBlank
    @JsonProperty("password")
    private String password;

}
