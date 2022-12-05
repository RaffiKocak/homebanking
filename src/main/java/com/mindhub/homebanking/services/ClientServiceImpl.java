package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> findAllDTO() {
        return this.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO findByEmailDTO(String email) {
        return this.findByEmail(email).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public ClientDTO findByIdDTO(Long id) {
        return this.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public List<Client> findByFirstName(String firstName) {
        return clientRepository.findByFirstName(firstName);
    }

    @Override
    public List<ClientDTO> findByFirstNameDTO(String firstName) {
        return this.findByFirstName(firstName).stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findByFirstNameAndEmail(String name, String email) {
        return clientRepository.findByFirstNameAndEmail(name, email);
    }

    @Override
    public ClientDTO findByFirstNameAndEmailDTO(String name, String email) {
        return this.findByFirstNameAndEmail(name, email).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public List<Client> findByLastName(String lastName) {
        return clientRepository.findByLastName(lastName);
    }

    @Override
    public List<ClientDTO> findByLastNameDTO(String lastName) {
        return this.findByLastName(lastName).stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public Client generateClient(String firstName, String lastName, String password, String email) {
        Client client = new Client(firstName, lastName, passwordEncoder.encode(password), email);

        clientRepository.save(client);

        return client;
    }


}
