package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    List<ClientDTO> findAllDTO();

    Optional<Client> findByEmail(String email);

    ClientDTO findByEmailDTO(String email);

    Optional<Client> findById(Long id);

    ClientDTO findByIdDTO(Long id);

    List<Client> findByFirstName(String firstName);

    List<ClientDTO> findByFirstNameDTO(String firstName);

    Optional<Client> findByFirstNameAndEmail(String name, String email);

    ClientDTO findByFirstNameAndEmailDTO(String name, String email);

    List<Client> findByLastName(String lastName);

    List<ClientDTO> findByLastNameDTO(String lastName);

    Client generateClient(String firstName, String lastName, String password, String email);
}
