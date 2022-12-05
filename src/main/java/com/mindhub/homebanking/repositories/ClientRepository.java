package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByFirstName(String firstName);

    Optional<Client> findByFirstNameAndEmail(String name, String email);

    List<Client> findByLastName(String lastName);

    Optional<Client> findByEmail(String email);


}
