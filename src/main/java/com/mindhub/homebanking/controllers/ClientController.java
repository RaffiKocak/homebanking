package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping ("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.findAllDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientService.findByIdDTO(id);
    }

    @GetMapping("/clients/firstName/{firstName}")
    public List<ClientDTO> getClientByFirstName(@PathVariable String firstName) {
        return clientService.findByFirstNameDTO(firstName);
    }

    @GetMapping("/clients/{firstName}/{email}")
    public ClientDTO getClientByNameAndEmail(@PathVariable String firstName, @PathVariable String email) {
        return clientService.findByFirstNameAndEmailDTO(firstName, email);
    }

    @GetMapping("/clients/lastName/{lastName}")
    public List<ClientDTO> getClientByLastName(@PathVariable String lastName) {
        return clientService.findByLastNameDTO(lastName);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> clientRegister(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.generateClient(firstName, lastName, password, email);

        accountService.generateAccount(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientByEmail(Authentication authentication) {
        return clientService.findByEmailDTO(authentication.getName());
    }

}

