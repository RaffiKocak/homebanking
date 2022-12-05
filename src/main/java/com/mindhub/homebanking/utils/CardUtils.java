package com.mindhub.homebanking.utils;

public final class CardUtils {
    private CardUtils() {
    }

    public static String createCardNumber() {
        String cardNumber = "4517-";
        String auxNumber;
        int randomNumber;
        for (int i = 0; i < 2; i++) {
            randomNumber = (int) ((Math.random() * (9999 - 1)) + 1);
            auxNumber = String.format("%04d", randomNumber);
            cardNumber = cardNumber + auxNumber + "-";
        }
        randomNumber = (int) ((Math.random() * (9999 - 1)) + 1);
        auxNumber = String.format("%04d", randomNumber);
        cardNumber = cardNumber + auxNumber;

        return cardNumber;
    }

    public static String createCvvNumber() {
        int randomNumber;
        String cvvNumber;
        randomNumber = (int) ((Math.random() * (999 - 1)) + 1);
        cvvNumber = String.format("%03d", randomNumber);

        return cvvNumber;
    }
}
