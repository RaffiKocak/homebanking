package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<Card> findById(Long id);

    Card generateCard(CardType cardType, CardColor cardColor, Client client);

    List<Card> findByClient(Client client);

    List<CardDTO> findByClientDTO(Client client);

    ResponseEntity<Object> deleteCard(Client client, Long id);

    void updateExpired(Client client);

}
