package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.findAllDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        return accountService.findByIdDTO(id);
    }

    @GetMapping("/accounts/balance/{balance}")
    public List<AccountDTO> getAccountByBalanceGreaterThan(@PathVariable double balance) {
        return accountService.findByBalanceGreaterThanDTO(balance);
    }

    @GetMapping("/accounts/creationDate/{creationDate}")
    public List<AccountDTO> getAccountByCreationDateLessThan(@PathVariable String creationDate) {
        return accountService.findByCreationDateLessThanDTO(LocalDateTime.parse(creationDate));
    }

    @GetMapping("/accounts/number/{number}")
    public AccountDTO getAccountByNumber(@PathVariable String number) {
        return accountService.findByNumberDTO(number);
    }

    @GetMapping("/accounts/balanceLessThan/{balance}")
    public List<AccountDTO> getAccountByBalanceLessThan(@PathVariable double balance) {
        return accountService.findByBalanceLessThanDTO(balance);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> accountRegister(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        if (client == null || accountService.generateAccount(client) == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        if (client == null) {
            return null;
        }

        return accountService.findByClientDTO(client);
    }

    @DeleteMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam Long id) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        return accountService.deleteAccount(client, id);
    }
}
