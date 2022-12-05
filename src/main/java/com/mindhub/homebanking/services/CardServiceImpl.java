package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService{
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card generateCard(CardType cardType, CardColor cardColor, Client client) {
        Set<Card> cardList = client.getCards();
        int counter = 0;

        for (Card card : cardList) {
            if (card.getType().equals(cardType)) {
                counter++;
            }
        }

        if (counter > 2) {
            return null;
        }

        Card card = new Card(cardType, cardColor, client);
        cardRepository.save(card);
        return card;
    }

    @Override
    public List<Card> findByClient(Client client) {
        return client.getCards().stream().collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> findByClientDTO(Client client) {
        return this.findByClient(client).stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> deleteCard(Client client, Long id) {
        Set<Card> cards = client.getCards();
        Card cardToDelete = this.findById(id).orElse(null);

        if (cards.contains(cardToDelete)) {
            cardToDelete.setActive(false);
            cardRepository.save(cardToDelete);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("Card not found", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void updateExpired(Client client) {
        Set<Card> cards = client.getCards();
        cards.forEach(card -> {
            card.setExpired(card.isExpired());
            cardRepository.save(card);
        });
    }
}
