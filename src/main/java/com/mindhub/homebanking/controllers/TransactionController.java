package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.findAllDTO();
    }

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> transfer(@RequestParam double amount, @RequestParam String description,
                                           @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication) {
        if (amount < 0.01 || description.isEmpty() || fromAccountNumber.isEmpty() ||toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("One or more required fields is invalid", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);
        return transactionService.executeTransfer(amount, description, fromAccountNumber, toAccountNumber, client);
    }

    @GetMapping("/transactions/betweenDates/{dateOne}/{dateTwo}")
    public List<TransactionDTO> getTransactionsBetweenDates(@PathVariable String dateOne, @PathVariable String dateTwo) {
        return transactionService.findByDateBetweenDTO(LocalDateTime.parse(dateOne), LocalDateTime.parse(dateTwo));
    }

    @GetMapping("/transactions/betweenAmounts/{amountOne}/{amountTwo}")
    public List<TransactionDTO> getTransactionsBetweenAmounts(@PathVariable double amountOne, @PathVariable double amountTwo) {
        return transactionService.findByAmountBetweenDTO(amountOne, amountTwo);
    }

    @GetMapping("/transactions/type/{type}")
    public List<TransactionDTO> getTransactionsByType(@PathVariable TransactionType type) {
        return transactionService.findByTypeDTO(type);
    }


}
