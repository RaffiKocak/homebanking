package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientUnitTest {

    ClientRepository clientRepository = mock(ClientRepository.class);

    List<Client> mockClients = Arrays.asList(new Client("Raffi", "Kocak", "123",
            "raffi@mail.com"));

    @Test
    public void existAllClients() {
        when(clientRepository.findAll()).thenReturn(mockClients);
        List<Client> clients = clientRepository.findAll();

        assertThat(clients, is(not(empty())));
    }

    @Test
    public void existsClient(){
        when(clientRepository.findAll()).thenReturn(mockClients);
        List<Client> clients = clientRepository.findAll();

        assertTrue(clients.get(0).getEmail() == "raffi@mail.com");
    }
}
