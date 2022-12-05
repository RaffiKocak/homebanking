package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long loanId;

    private double amount;

    private int payments;

    private String toAccountNumber;

    public LoanApplicationDTO(Long id, double amount, int payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
