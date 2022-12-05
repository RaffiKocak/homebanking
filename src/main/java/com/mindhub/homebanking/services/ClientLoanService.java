package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;
import java.util.Optional;

public interface ClientLoanService {
    List<ClientLoan> findAll();

    List<ClientLoanDTO> findAllDTO();

    Optional<ClientLoan> findById(Long id);

    ClientLoanDTO findByIdDTO(Long id);

    List<ClientLoan> findByClient(Client client);

    List<ClientLoanDTO> findByClientDTO(Client client);

    List<ClientLoan> findByAmountGreaterThan(double amount);

    List<ClientLoanDTO> findByAmountGreaterThanDTO(double amount);

    List<ClientLoan> findClientLoansByAccountBalanceLessThan(double balance);

    List<ClientLoanDTO> findClientLoansByAccountBalanceLessThanDTO(double balance);
}
