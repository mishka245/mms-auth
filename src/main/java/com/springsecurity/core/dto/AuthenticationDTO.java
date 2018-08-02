package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    @JsonProperty("authorised")
    private Boolean authorised;

    @JsonProperty("expireTime")
    private Long expireTime;
}
