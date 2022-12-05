package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository cliRepository, AccountRepository accRepository,
									  TransactionRepository traRepository, LoanRepository loaRepository,
									  ClientLoanRepository cliloaRepository, CardRepository cardRepository) {
		return (args) -> {
			// Clients
			Client clientOne = new Client("Melba", "Morel", passwordEncoder.encode("asd123"), "melba@mindhub.com");
			Client clientTwo = new Client("Joseph", "Joestar", passwordEncoder.encode("222"), "nigerundayo@gmail.com");

			// Accounts
			Account accountOne = new Account(clientOne);
			Account accountTwo = new Account(clientOne);
			Account accountThree = new Account(clientTwo);
			accountOne.setBalance(5000);
			accountTwo.setBalance(7500);
			accountTwo.setCreationDate(LocalDateTime.now().plusDays(1));

			// Transactions
			Transaction transactionOne = new Transaction(accountOne, TransactionType.CREDITO, 10000, "Venta 1");
			Transaction transactionTwo = new Transaction(accountTwo, TransactionType.DEBITO, -2000, "Compra 1");
			Transaction transactionThree = new Transaction(accountThree, TransactionType.CREDITO, 12000, "Sueldo 1");
			Transaction transactionFour = new Transaction(accountOne, TransactionType.CREDITO, 15000, "Venta 2");

			//Loans
			Loan loanOne = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60), 20);
			Loan loanTwo = new Loan("Personal", 100000, List.of(6,12,24), 5);
			Loan loanThree = new Loan("Automotriz", 300000, List.of(12,24,36,48,60), 10);

			//ClientLoans
			ClientLoan clientLoanOne = new ClientLoan(400000, 60, clientOne, loanOne);
			ClientLoan clientLoanTwo = new ClientLoan(50000, 12, clientOne, loanTwo);
			ClientLoan clientLoanThree = new ClientLoan(100000, 24, clientTwo, loanTwo);
			ClientLoan clientLoanFour = new ClientLoan(200000, 36, clientTwo, loanThree);

			//Cards
			Card cardOne = new Card(CardType.DEBIT, CardColor.GOLD, clientOne);
			Card cardTwo = new Card(CardType.CREDIT, CardColor.TITANIUM, clientOne);
			Card cardThree = new Card(CardType.CREDIT, CardColor.SILVER, clientTwo);

			cliRepository.save(clientOne);
			cliRepository.save(clientTwo);
			accRepository.save(accountOne);
			accRepository.save(accountTwo);
			accRepository.save(accountThree);
			traRepository.save(transactionOne);
			traRepository.save(transactionTwo);
			traRepository.save(transactionThree);
			traRepository.save(transactionFour);
			loaRepository.save(loanOne);
			loaRepository.save(loanTwo);
			loaRepository.save(loanThree);
			cliloaRepository.save(clientLoanOne);
			cliloaRepository.save(clientLoanTwo);
			cliloaRepository.save(clientLoanThree);
			cliloaRepository.save(clientLoanFour);
			cardRepository.save(cardOne);
			cardRepository.save(cardTwo);
			cardRepository.save(cardThree);
		};
	}

}
