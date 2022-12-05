package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> cardRegister(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                               Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        if (cardService.generateCard(cardType, cardColor, client) == null) {
            return new ResponseEntity<>("Too many " + cardType + " cards", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        if (client == null) {
            return null;
        }

        cardService.updateExpired(client);

        return cardService.findByClientDTO(client);
    }

    @DeleteMapping("/clients/current/cards")
    public ResponseEntity<Object> cardDelete(Authentication authentication, @RequestParam Long id) {
        Client client = clientService.findByEmail(authentication.getName()).orElse(null);

        return cardService.deleteCard(client, id);
    }
}
