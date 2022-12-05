package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetween(LocalDateTime dateOne, LocalDateTime dateTwo);
    List<Transaction> findByAmountBetween(double amountGreaterThan, double amountLessThan);
    List<Transaction> findByType(TransactionType type);
}
