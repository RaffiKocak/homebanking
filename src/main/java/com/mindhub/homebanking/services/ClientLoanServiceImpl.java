package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ClientLoanServiceImpl implements ClientLoanService{
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<ClientLoan> findAll() {
        return clientLoanRepository.findAll();
    }

    @Override
    public List<ClientLoanDTO> findAllDTO() {
        return this.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public Optional<ClientLoan> findById(Long id) {
        return clientLoanRepository.findById(id);
    }

    @Override
    public ClientLoanDTO findByIdDTO(Long id) {
        return this.findById(id).map(clientLoan -> new ClientLoanDTO(clientLoan)).orElse(null);
    }

    @Override
    public List<ClientLoan> findByClient(Client client) {
        return clientLoanRepository.findByClient(client);
    }

    @Override
    public List<ClientLoanDTO> findByClientDTO(Client client) {
        return this.findByClientDTO(client);
    }

    @Override
    public List<ClientLoan> findByAmountGreaterThan(double amount) {
        return clientLoanRepository.findByAmountGreaterThan(amount);
    }

    @Override
    public List<ClientLoanDTO> findByAmountGreaterThanDTO(double amount) {
        return this.findByAmountGreaterThan(amount).stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public List<ClientLoan> findClientLoansByAccountBalanceLessThan(double balance) {
        Set<Client> clients = new HashSet<>();

        List<ClientLoan> clientLoanList = new ArrayList<>();

        List<Account> accounts = accountService.findByBalanceLessThan(balance);

        for ( Account account: accounts )
        {
            clients.add(account.getClient());
        }

        for (Client client : clients) {
            client.getClientLoans().forEach(clientLoan -> clientLoanList.add(clientLoan));
        }

        return clientLoanList;
    }

    @Override
    public List<ClientLoanDTO> findClientLoansByAccountBalanceLessThanDTO(double balance) {
        return this.findClientLoansByAccountBalanceLessThan(balance).stream().map(ClientLoanDTO::new).collect(toList());
    }
}
