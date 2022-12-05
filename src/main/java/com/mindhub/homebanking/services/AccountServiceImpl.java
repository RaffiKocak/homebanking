package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientService clientService;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> findAllDTO() {
        return this.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountDTO findByIdDTO(Long id) {
        return this.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public AccountDTO findByNumberDTO(String number) {
        return this.findByNumber(number).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public List<Account> findByBalanceGreaterThan(double balance) {
        return accountRepository.findByBalanceGreaterThan(balance);
    }

    @Override
    public List<AccountDTO> findByBalanceGreaterThanDTO(double balance) {
        return this.findByBalanceGreaterThan(balance).stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByCreationDateLessThan(LocalDateTime creationDate) {
        return accountRepository.findByCreationDateLessThan(creationDate);
    }

    @Override
    public List<AccountDTO> findByCreationDateLessThanDTO(LocalDateTime creationDate) {
        return this.findByCreationDateLessThan(creationDate).stream().
                map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByBalanceLessThan(double balance) {
        return accountRepository.findByBalanceLessThan(balance);
    }

    @Override
    public List<AccountDTO> findByBalanceLessThanDTO(double balance) {
        return this.findByBalanceGreaterThan(balance).stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByClient(Client client) {
        return client.getAccounts().stream().collect(toList());
    }

    @Override
    public List<AccountDTO> findByClientDTO(Client client) {
        return this.findByClient(client).stream().filter(account -> account.isActive()).map(account -> new AccountDTO(account)).collect(toList());
    }

    @Override
    public Account generateAccount(Client client) {
        Account account = null;

        if (client.getAccounts().size() < 3) {
            account = new Account (client);
            accountRepository.save(account);
        }

        return account;
    }

    @Override
    public ResponseEntity<Object> deleteAccount(Client client, Long id) {
        Set<Account> accounts = client.getAccounts();
        Account accountToDelete = this.findById(id).orElse(null);

        if (accounts.contains(accountToDelete)) {
            accountToDelete.setActive(false);
            accountRepository.save(accountToDelete);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("Account not found", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void accountCredit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    public void accountDebit(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
}
