package com.b2.bookingingorkutek.exceptions;

import lombok.Generated;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Generated
public record ErrorTemplate(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
