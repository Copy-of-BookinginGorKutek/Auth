package com.b2.bookingingorkutek.exceptions.advice;

import com.b2.bookingingorkutek.exceptions.ErrorTemplate;
import com.b2.bookingingorkutek.exceptions.UserAlreadyExistException;
import io.jsonwebtoken.JwtException;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Generated
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<Object> userExist() {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var baseException = new ErrorTemplate(
                "Email sudah terdaftar",
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }


    @ExceptionHandler(value = {JwtException.class, AuthenticationException.class, UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<Object> credentialsError(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var baseException = new ErrorTemplate(
                "Email atau password salah",
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }


}

