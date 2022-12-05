package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String cardHolder;

    private CardType type;

    private CardColor color;

    private String number;

    private String cvv;

    private LocalDate fromDate;

    private LocalDate thruDate;

    private boolean active;

    private boolean expired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(CardType type, CardColor color, Client client) {
        this.number = CardUtils.createCardNumber();
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.color = color;
        this.cvv = CardUtils.createCvvNumber();
        this.client = client;
        this.fromDate = LocalDate.now();
        this.thruDate = LocalDate.now().plusYears(5);
        this.active = true;
        this.expired = this.isExpired();
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExpired() {
        return this.thruDate.isBefore(LocalDate.now());
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
