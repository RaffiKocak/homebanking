package com.mindhub.homebanking.utils;

public final class AccountUtils {
    public static String createAccountNumber() {
        int randomNumber = (int) ((Math.random() * (99999999 - 1)) + 1);
        String auxNumber = String.format("%08d", randomNumber);
        String accNumber = "VIN-" + auxNumber;

        return accNumber;
    }
}
