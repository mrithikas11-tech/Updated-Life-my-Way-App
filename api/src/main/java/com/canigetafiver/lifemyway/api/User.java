package com.canigetafiver.lifemyway.api;

/**
 * Stores a user's identity and preference information.
 */
public class User {
    private static final String DEFAULT_CURRENCY = "USD";

    private String userName;
    private String userEmail;
    private String userNumber;
    private String preferredCurrency;

    public User(String userName, String userEmail, String userNumber, String preferredCurrency) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
        setPreferredCurrency(preferredCurrency);
    }

    /**
     * Backward-compatible constructor used by earlier project modules.
     */
    public User(String userName) {
        this(userName, null, null, DEFAULT_CURRENCY);
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getNumber() {
        return userNumber;
    }

    public void setNumber(String number) {
        this.userNumber = number;
    }

    public String getPreferredCurrency() {
        if (preferredCurrency == null || preferredCurrency.isBlank()) {
            return DEFAULT_CURRENCY;
        }
        return preferredCurrency;
    }

    public void setPreferredCurrency(String preferredCurrency) {
        if (preferredCurrency == null || preferredCurrency.isBlank()) {
            this.preferredCurrency = DEFAULT_CURRENCY;
            return;
        }
        this.preferredCurrency = preferredCurrency;
    }
}
