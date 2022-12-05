package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.utils.AccountUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String number;

    private LocalDateTime creationDate;

    private double balance;

    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id") // es para darle el nombre a la columna
    private Client client;

    @OneToMany(mappedBy="account", fetch= FetchType.EAGER)
    private Set<Transaction> transactions;

    public Account() { }

    public Account(Client client) {
        this.number = AccountUtils.createAccountNumber();
        this.creationDate = LocalDateTime.now();
        this.balance = 0;
        this.client = client;
        this.transactions = new HashSet<>();
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //@JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction newTransaction) {
        newTransaction.setAccount(this);
        transactions.add(newTransaction);
    }
}
