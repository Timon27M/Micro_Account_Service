package org.example.microaccountservice.grpc;

import com.finflow.schemas.grpc.account.AccountServiceGrpc;
import com.finflow.schemas.grpc.account.CreateAccountRequest;
import com.finflow.schemas.grpc.account.CreateAccountResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.microaccountservice.entities.AccountProfile;
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
}
