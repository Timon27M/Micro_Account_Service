package org.example.microaccountservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "account_profile",
        indexes = {
                @Index(name = "idx_account_number", columnList = "account_number")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", nullable = false, updatable = false)
    private UUID accountId;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    @Column(name = "available_balance", precision = 19, scale = 4)
    private BigDecimal availableBalance;

    @Column(name = "current_balance", precision = 19, scale = 4)
    private BigDecimal currentBalance;

    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt;

    @Column(name = "closed_at")
    private OffsetDateTime closedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public AccountProfile(UUID userId, String accountNumber) {
        this.userId = userId;
        this.accountNumber = accountNumber;

        this.accountType = AccountType.CURRENT;
        this.currency = Currency.RUB;
        this.status = AccountStatus.PENDING;

        this.availableBalance = BigDecimal.ZERO;
        this.currentBalance = BigDecimal.ZERO;

        this.openedAt = OffsetDateTime.now();
    }

    public enum AccountType {
        CURRENT,
        SAVINGS,
        DEPOSIT,
        CREDIT
    }

    public enum Currency {
        USD,
        EUR,
        RUB
    }

    public enum AccountStatus {
        PENDING,
        ACTIVE,
        BLOCKED,
        SUSPENDED,
        CLOSED
    }
}