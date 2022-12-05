package com.mindhub.homebanking;


import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Test
    public void existAllClients(){
        List<Client> clients = clientRepository.findAll();

        assertThat(clients, is(not(empty())));
    }

    @Test
    public void existsClient(){
        List<Client> clients = clientRepository.findAll();

        assertThat(clients, hasItem(hasProperty("email", is("melba@mindhub.com"))));
        //assertTrue(clients.get(0).getEmail() == "melba@mindhub.com");
    }

    @Test
    public void existLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
}
