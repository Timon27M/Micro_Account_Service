package org.example.microaccountservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.microaccountservice.entities.AccountProfile;
import org.example.microaccountservice.exceptions.AccountAlreadyExistsException;
import org.example.microaccountservice.exceptions.AccountNotFoundException;
import org.example.microaccountservice.repositories.AccountProfileRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
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

    @Cacheable(value = "accounts", key = "#userId")
    public AccountProfile addOrGetToCacheAccountData(UUID userId) throws AccountNotFoundException {
        return accountProfileRepository.findByUserId(userId).orElseThrow(() -> new AccountNotFoundException("Account with user_id: " + userId.toString() + " not found"));
    }

    @CachePut(value = "accounts", key = "#userId")
    public AccountProfile update(AccountProfile account) {

        return accountProfileRepository.save(account);
    }

    @CacheEvict(value = "accounts", key = "#userId")
    public void delete(UUID accountId) {

        accountProfileRepository.deleteById(accountId);
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
