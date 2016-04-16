package com.harry.winser.personal.blog.web.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends HttpException{

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
