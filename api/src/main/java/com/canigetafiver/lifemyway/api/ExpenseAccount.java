package com.canigetafiver.lifemyway.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains the expenses attached to a user's account.
 */
public class ExpenseAccount {
    private List<Expense> expenses;

    public ExpenseAccount() {
        expenses = new ArrayList<>();
    }

    public ExpenseAccount(List<Expense> expenses) {
        this.expenses = new ArrayList<>(expenses);
    }

    public void addExpense(Expense expense) {
        ensureExpenses();
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        ensureExpenses();
        return expenses;
    }

    private void ensureExpenses() {
        if (expenses == null) {
            expenses = new ArrayList<>();
        }
    }
}
