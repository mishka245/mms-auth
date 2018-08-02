package com.springsecurity.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, code = HttpStatus.UNAUTHORIZED, reason = "User not Authorised")
public class UnauthorisedException extends RuntimeException {
}
