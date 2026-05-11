package com.canigetafiver.lifemyway.api;

public class CurrencyFormatter {
    public static String format(double amount, String currency) {
        if (currency == null || currency.isBlank()) {
            currency = "USD";
        }

        return switch (currency.toUpperCase()) {
            case "EUR" -> String.format("€%.2f", amount);
            case "MXN" -> String.format("MX$%.2f", amount);
            default -> String.format("$%.2f", amount);
        };
    }
}