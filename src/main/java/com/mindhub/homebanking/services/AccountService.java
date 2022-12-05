package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();

    List<AccountDTO> findAllDTO();

    Optional<Account> findById(Long id);

    AccountDTO findByIdDTO(Long id);

    Optional<Account> findByNumber(String number);

    AccountDTO findByNumberDTO(String number);

    List<Account> findByBalanceGreaterThan(double balance);

    List<AccountDTO> findByBalanceGreaterThanDTO(double balance);

    List<Account> findByCreationDateLessThan(LocalDateTime creationDate);

    List<AccountDTO> findByCreationDateLessThanDTO(LocalDateTime creationDate);

    List<Account> findByBalanceLessThan(double balance);

    List<AccountDTO> findByBalanceLessThanDTO(double balance);

    List<Account> findByClient(Client client);

    List<AccountDTO> findByClientDTO(Client client);

    Account generateAccount(Client client);

    ResponseEntity<Object> deleteAccount(Client client, Long id);

    void accountCredit(Account account, double amount);

    void accountDebit(Account account, double amount);
}
