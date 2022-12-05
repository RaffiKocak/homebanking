package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> findAllDTO() {
        return this.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public List<Transaction> findByDateBetween(LocalDateTime dateOne, LocalDateTime dateTwo) {
        return transactionRepository.findByDateBetween(dateOne, dateTwo);
    }

    @Override
    public List<TransactionDTO> findByDateBetweenDTO(LocalDateTime dateOne, LocalDateTime dateTwo) {
        return this.findByDateBetween(dateOne, dateTwo).stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public List<Transaction> findByAmountBetween(double amountGreaterThan, double amountLessThan) {
        return transactionRepository.findByAmountBetween(amountGreaterThan, amountLessThan);
    }

    @Override
    public List<TransactionDTO> findByAmountBetweenDTO(double amountGreaterThan, double amountLessThan) {
        return this.findByAmountBetween(amountGreaterThan, amountLessThan).stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    @Override
    public List<TransactionDTO> findByTypeDTO(TransactionType type) {
        return this.findByType(type).stream().map(TransactionDTO::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> executeTransfer(double amount, String description, String accountFromNumber, String accountToNumber, Client client) {
        Account accountFrom = accountService.findByNumber(accountFromNumber).orElse(null);
        Account accountTo = accountService.findByNumber(accountToNumber).orElse(null);
        ResponseEntity<Object> response = this.validateTransferData(amount, accountFrom, accountTo, client);

        if (!response.equals(HttpStatus.FORBIDDEN)) {
            Transaction transactionFrom = new Transaction(accountFrom, TransactionType.DEBITO, amount * -1, description);
            Transaction transactionTo = new Transaction(accountTo, TransactionType.CREDITO, amount, description);

            //accountFrom.setBalance(accountFrom.getBalance() - amount);
            accountService.accountDebit(accountFrom, amount);
            //accountTo.setBalance(accountTo.getBalance() + amount);
            accountService.accountCredit(accountTo, amount);

            transactionRepository.save(transactionFrom);
            transactionRepository.save(transactionTo);
        }

        return response;
    }

    private ResponseEntity<Object> validateTransferData(double amount,Account accountFrom, Account accountTo, Client client) {
        Set<Account> clientAccounts = client.getAccounts();
        boolean existsAccountInClient = false;

        if (accountFrom == null) {
            return new ResponseEntity<>("Account from doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (accountTo == null) {
            return new ResponseEntity<>("Account to doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (accountFrom.equals(accountTo)) {
            return new ResponseEntity<>("Account from and account to cannot be the same", HttpStatus.FORBIDDEN);
        }

        for(Account account : clientAccounts) {
            if (account.equals(accountFrom)) {
                //if (clientAccounts.contains(accountFrom.getId() == account.getId())) {
                existsAccountInClient = true;
                break;
            }
        }

        if (!existsAccountInClient) {
            return new ResponseEntity<>("Account doesn't belong to logged client", HttpStatus.FORBIDDEN);
        }

        if (accountFrom.getBalance() < amount) {
            return new ResponseEntity<>("Account doesn't have enough balance to transfer", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
