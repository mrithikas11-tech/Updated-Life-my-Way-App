package com.canigetafiver.lifemyway.api;

import java.util.ArrayList;
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
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Food", "Meals, groceries, and snacks"));
        categories.add(new Category("Transportation", "Gas, rideshare, and public transit"));
        categories.add(new Category("School", "Books, supplies, and project costs"));
        return categories;
    }

    public static List<Expense> createExpenses() {
        List<Category> categories = createCategories();
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Groceries", 54.20, "2026-05-01", categories.get(0)));
        expenses.add(new Expense("Coffee", 5.75, "2026-05-02", categories.get(0)));
        expenses.add(new Expense("Bus pass", 25.00, "2026-05-03", categories.get(1)));
        expenses.add(new Expense("Notebook", 4.99, "2026-05-04", categories.get(2)));
        expenses.add(new Expense("Lunch", 12.50, "2026-05-05", categories.get(0)));
        expenses.add(new Expense("Gas", 38.40, "2026-05-06", categories.get(1)));
        expenses.add(new Expense("Textbook", 79.99, "2026-05-07", categories.get(2)));
        expenses.add(new Expense("Dinner", 18.30, "2026-05-08", categories.get(0)));
        expenses.add(new Expense("Ride share", 14.10, "2026-05-09", categories.get(1)));
        expenses.add(new Expense("Printer paper", 8.25, "2026-05-10", categories.get(2)));
        return expenses;
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
