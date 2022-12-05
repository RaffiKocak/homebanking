package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    List<Loan> findAll();

    List<LoanDTO> findAllDTO();

    Optional<Loan> findById(Long id);

    ResponseEntity<Object> addLoan(Client client, LoanApplicationDTO loan);
}
