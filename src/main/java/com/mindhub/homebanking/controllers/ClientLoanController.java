package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/clientLoans")
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanService.findAllDTO();
    }

    @GetMapping("/clientLoans/client/{clientId}")
    public List<ClientLoanDTO> getClientLoansByClient(@PathVariable Long clientId) {
        Client client = clientService.findById(clientId).orElse(null);
        return clientLoanService.findByClientDTO(client);
    }

    @GetMapping("/clientLoans/amountGreaterThan/{amount}")
    public List<ClientLoanDTO> getClientLoansByAmountGreaterThan(@PathVariable double amount) {
        return clientLoanService.findByAmountGreaterThanDTO(amount);
    }

    @GetMapping("/clientLoans/accountBalanceLessThan/{balance}")
    public List<ClientLoanDTO> getClientLoansByAccountBalanceLessThan(@PathVariable double balance) {
        return clientLoanService.findClientLoansByAccountBalanceLessThanDTO(balance);

    }
}
