package org.example.microaccountservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public AccountNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND; // 404 по умолчанию
    }

    public AccountNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}