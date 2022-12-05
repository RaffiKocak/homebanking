package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanDTO> findAllDTO() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public ResponseEntity<Object> addLoan(Client client, LoanApplicationDTO loan) {
        Loan originalLoan = this.findById(loan.getLoanId()).orElse(null);
        ResponseEntity<Object> response = this.validateLoanData(client, loan);

        if (!response.equals(HttpStatus.FORBIDDEN)) {
            double loanFinalAmount = loan.getAmount() + loan.getAmount() * originalLoan.getModifier() / 100;
            Account loanAccount = accountService.findByNumber(loan.getToAccountNumber()).orElse(null);
            ClientLoan approvedLoan = new ClientLoan(loanFinalAmount, loan.getPayments(), client, originalLoan);
            Transaction transaction = new Transaction(loanAccount, TransactionType.CREDITO, loan.getAmount(),
                    originalLoan.getName() + "loan approved");

            accountService.accountCredit(loanAccount, loan.getAmount());

            clientLoanRepository.save(approvedLoan);
            transactionRepository.save(transaction);
        }

        return response;
    }

    private ResponseEntity<Object> validateLoanData(Client client, LoanApplicationDTO loan) {
        Set<Account> clientAccounts = client.getAccounts();
        Loan originalLoan = this.findById(loan.getLoanId()).orElse(null);
        List<Loan> loanList = this.findAll();


        boolean loanHasPaymentQuantity = false;
        boolean existsAccountInClient = false;

        //Verificar que el préstamo exista
        if (originalLoan == null) {
            return new ResponseEntity<>("Loan ID doesn't exist", HttpStatus.FORBIDDEN);
        }

        //Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if (loan.getPayments() < 1 || loan.getAmount() < 1 || loan.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
        }

        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (loan.getAmount() > originalLoan.getMaxAmount()) {
            return new ResponseEntity<>("Invalid values", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        for (int paymentQuantity : originalLoan.getPayments()) {
            if (paymentQuantity == loan.getPayments()) {
                loanHasPaymentQuantity = true;
                break;
            }
        }

        if (!loanHasPaymentQuantity) {
            return new ResponseEntity<>("Payment quantity doesn't exist", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        for(Account account : clientAccounts) {
            if (account.getNumber().equals(loan.getToAccountNumber())) {
                existsAccountInClient = true;
                break;
            }
        }

        if (!existsAccountInClient) {
            return new ResponseEntity<>("Can't add loan into account", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
