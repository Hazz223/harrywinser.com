package com.harry.winser.personal.blog.web.exceptions;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    private HttpStatus code;

    public HttpException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
