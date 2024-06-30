package com.github.postsolo.service.exception;

public class AlreadyExistsEmail extends RuntimeException{
    public AlreadyExistsEmail(String message) {
        super(message);
    }
}
