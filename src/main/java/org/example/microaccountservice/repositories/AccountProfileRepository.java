package org.example.microaccountservice.repositories;

import org.example.microaccountservice.entities.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfile, UUID> {
    boolean existsByUserId(UUID userId);

    Optional<AccountProfile> findByUserId(UUID userId);
}
