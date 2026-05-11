package com.canigetafiver.lifemyway.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Factory for reusable demo and test data.
 */
public final class MockDataFactory {
    private MockDataFactory() {
    }

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Steven Tinoco Calvillo", "steven@example.com", "555-0101", "USD"));
        users.add(new User("Demo Teammate", "demo@example.com", "555-0102", "MXN"));
        return users;
    }

    public static List<Category> createCategories() {
        return new ArrayList<>(Arrays.asList(Category.values()));
    }

    public static List<Expense> createExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(buildExpense(54.20, Category.GROCERY,        PaymentMethod.DEBIT_CARD,  "Groceries",    "Safeway",      "2026-05-01"));
        expenses.add(buildExpense( 5.75, Category.FOOD,           PaymentMethod.CASH,        "Coffee",       "Starbucks",    "2026-05-02"));
        expenses.add(buildExpense(25.00, Category.TRANSPORTATION, PaymentMethod.CREDIT_CARD, "Bus pass",     "VTA",          "2026-05-03"));
        expenses.add(buildExpense( 4.99, Category.ENTERTAINMENT,  PaymentMethod.CASH,        "Notebook",     "Target",       "2026-05-04"));
        expenses.add(buildExpense(12.50, Category.DINING,         PaymentMethod.CREDIT_CARD, "Lunch",        "Chipotle",     "2026-05-05"));
        expenses.add(buildExpense(38.40, Category.TRANSPORTATION, PaymentMethod.DEBIT_CARD,  "Gas",          "Shell",        "2026-05-06"));
        expenses.add(buildExpense(79.99, Category.ENTERTAINMENT,  PaymentMethod.CREDIT_CARD, "Textbook",     "Amazon",       "2026-05-07"));
        expenses.add(buildExpense(18.30, Category.DINING,         PaymentMethod.CREDIT_CARD, "Dinner",       "Olive Garden", "2026-05-08"));
        expenses.add(buildExpense(14.10, Category.TRANSPORTATION, PaymentMethod.APPLE_PAY,   "Ride share",   "Lyft",         "2026-05-09"));
        expenses.add(buildExpense( 8.25, Category.ENTERTAINMENT,  PaymentMethod.DEBIT_CARD,  "Printer paper","Office Depot", "2026-05-10"));
        return expenses;
    }

    private static Expense buildExpense(double amount, Category category, PaymentMethod pm,
                                        String description, String vendor, String isoDate) {
        return new Expense.ExpenseBuilder(amount, category, pm)
            .description(description)
            .vendor(vendor)
            .date(LocalDate.parse(isoDate))
            .build();
    }

    public static UserDataBase createUserDataBase() {
        List<User> users = createUsers();
        List<Expense> expenses = createExpenses();
        ExpenseAccount firstAccount = new ExpenseAccount(expenses.subList(0, 5));
        ExpenseAccount secondAccount = new ExpenseAccount(expenses.subList(5, expenses.size()));

        UserDataBase db = new UserDataBase();
        db.addUser("steven", users.get(0), firstAccount);
        db.addUser("demo", users.get(1), secondAccount);
        return db;
    }
}
