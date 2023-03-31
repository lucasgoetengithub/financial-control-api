package com.br.lsolution.financialcontrol.api.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticatedException extends RuntimeException{

    public AuthenticatedException(String message) {
        super(message);
    }
}