package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByNumber(String number);

    List<Account> findByBalanceGreaterThan(double balance);

    List<Account> findByCreationDateLessThan(LocalDateTime creationDate);

    List<Account> findByBalanceLessThan(double balance);
}
