package org.example.microaccountservice.services;

import lombok.RequiredArgsConstructor;
import org.example.microaccountservice.entities.AccountProfile;
import org.example.microaccountservice.exceptions.AccountAlreadyExistsException;
import org.example.microaccountservice.repositories.AccountProfileRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountProfileService {
    private final AccountProfileRepository accountProfileRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public AccountProfile createAccount(UUID userId) {
        boolean accountIsExists = accountProfileRepository.existsByUserId(userId);
        if (accountIsExists) {
            throw new AccountAlreadyExistsException(userId);
        }
        String accountNumber = generateAccountNumber();
        AccountProfile accountProfile = new AccountProfile(userId, accountNumber);

        AccountProfile account = accountProfileRepository.saveAndFlush(accountProfile);
        return account;
    }

    private String generateAccountNumber() {
        Long nextValue = jdbcTemplate.queryForObject(
                "SELECT nextval('account_number_seq')",
                Long.class
        );

        if (nextValue == null) {
            throw new IllegalStateException(
                    "Failed to generate account number"
            );
        }

        return "%020d".formatted(nextValue);
    }
}
