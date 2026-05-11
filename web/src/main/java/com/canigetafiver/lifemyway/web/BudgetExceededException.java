package com.canigetafiver.lifemyway.web;

public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
}
