package com.github.postsolo.web.advice;

import com.github.postsolo.service.exception.AlreadyExistsEmail;
import com.github.postsolo.service.exception.NotFoundException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AlreadyBoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsEmail.class)
    public String alreadyExistsEmailHandler(AlreadyExistsEmail aee){
        log.error("클라이언트 요청 이후에 DB에서 검색 중 에러 발생 : "+aee.getMessage());
        return aee.getMessage();

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public String notFoundException(NotFoundException nfe){
        log.error(nfe.getMessage());
        return nfe.getMessage();
    }
}
