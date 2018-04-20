package com.example.config.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Json Format Exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJSONFormatException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public String getInvalidJsonStr() {
        return invalidJsonStr;
    }

    private String invalidJsonStr;
    public InvalidJSONFormatException(String invalidJsonStr){
        this.invalidJsonStr = invalidJsonStr;
    }
    @Override
    public String getMessage() {
        return super.getMessage() + " Invalid Json String: " + invalidJsonStr;
    }
}
