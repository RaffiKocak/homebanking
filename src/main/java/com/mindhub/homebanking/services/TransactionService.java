package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    List<TransactionDTO> findAllDTO();

    List<Transaction> findByDateBetween(LocalDateTime dateOne, LocalDateTime dateTwo);

    List<TransactionDTO> findByDateBetweenDTO(LocalDateTime dateOne, LocalDateTime dateTwo);

    List<Transaction> findByAmountBetween(double amountGreaterThan, double amountLessThan);

    List<TransactionDTO> findByAmountBetweenDTO(double amountGreaterThan, double amountLessThan);

    List<Transaction> findByType(TransactionType type);

    List<TransactionDTO> findByTypeDTO(TransactionType type);

    ResponseEntity<Object> executeTransfer(double amount, String description,
                                           String accountFromNumber, String accountToNumber, Client client);

}
