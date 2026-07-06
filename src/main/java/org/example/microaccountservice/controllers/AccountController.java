package org.example.microaccountservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.microaccountservice.dto.global.DefaultSuccessResponseWithBody;
import org.example.microaccountservice.entities.AccountProfile;
import org.example.microaccountservice.exceptions.AccountNotFoundException;
import org.example.microaccountservice.services.AccountProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountProfileService accountProfileService;

    @GetMapping
    public ResponseEntity<DefaultSuccessResponseWithBody<AccountProfile>> getAccount(@AuthenticationPrincipal Jwt jwt) throws AccountNotFoundException {
       AccountProfile accountProfile = accountProfileService.addOrGetToCacheAccountData(UUID.fromString(jwt.getSubject()));

       return ResponseEntity.status(HttpStatus.CREATED)
               .body(DefaultSuccessResponseWithBody.of(accountProfile));
    }
}
