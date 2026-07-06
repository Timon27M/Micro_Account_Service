package org.example.microaccountservice.grpc;

import com.finflow.schemas.grpc.account.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.microaccountservice.entities.AccountProfile;
import org.example.microaccountservice.exceptions.AccountNotFoundException;
import org.example.microaccountservice.services.AccountProfileService;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class AccountGrpcEndpoint extends AccountServiceGrpc.AccountServiceImplBase {
    private final AccountProfileService accountProfileService;

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountResponse> responseObserver) {
        UUID userId = UUID.fromString(request.getUserId());

        AccountProfile account = accountProfileService.createAccount(userId);

        CreateAccountResponse response =
                CreateAccountResponse.newBuilder()
                        .setAccountId(account.getAccountId().toString())
                        .setAccountNumber(account.getAccountNumber())
                        .setSuccess(true)
                        .setMessage("Account successfully created")
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addToCacheAccountData(AddToCacheAccountDataRequest request, StreamObserver<AddToCacheAccountDataResponse> responseObserver) {
        UUID userId = UUID.fromString(request.getUserId());

        try {
            accountProfileService.addOrGetToCacheAccountData(userId);
        } catch (AccountNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException());
            return;
        }
        AddToCacheAccountDataResponse response = AddToCacheAccountDataResponse.newBuilder()
                .setMessage("Success")
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
