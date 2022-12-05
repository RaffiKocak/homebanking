package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanService loanService;

    @Autowired
    ClientService clientService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.findAllDTO();
    }


    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<Object> loanApply(Authentication authentication, @RequestBody LoanApplicationDTO loanRequested) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        return loanService.addLoan(client, loanRequested);
    }


}
