package org.example.microaccountservice.handlers;

import io.grpc.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.example.microaccountservice.exceptions.AccountAlreadyExistsException;

@Slf4j
@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public Status handleIllegalArgumentException(
            IllegalArgumentException ex) {

        log.error(ex.getMessage(), ex);

        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage());
    }

    @GrpcExceptionHandler(IllegalStateException.class)
    public Status handleIllegalStateException(
            IllegalStateException ex) {

        log.error(ex.getMessage(), ex);

        return Status.FAILED_PRECONDITION
                .withDescription(ex.getMessage());
    }

    @GrpcExceptionHandler(EntityNotFoundException.class)
    public Status handleEntityNotFoundException(
            EntityNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        return Status.NOT_FOUND
                .withDescription(ex.getMessage());
    }

    @GrpcExceptionHandler(AccountAlreadyExistsException.class)
    public Status handleAccountAlreadyExistsException(
            AccountAlreadyExistsException ex) {

        log.error(ex.getMessage(), ex);

        return Status.ALREADY_EXISTS
                .withDescription(ex.getMessage());
    }

    @GrpcExceptionHandler(Exception.class)
    public Status handleException(Exception ex) {

        log.error(ex.getMessage(), ex);

        return Status.INTERNAL
                .withDescription("AccountService Error: " + (ex.getMessage() != null ? ex.getMessage() : "Internal server error"));
    }
}