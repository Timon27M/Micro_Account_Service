package org.example.microaccountservice.exceptions;

import java.util.UUID;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(UUID userId) {
        super("Account with userId " + userId + " is already exists");
    }
}
