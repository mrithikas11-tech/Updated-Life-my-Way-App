package com.canigetafiver.lifemyway.api;

public class UserDataBaseTest {
    public static void main(String[] args) {
        addAndFindUserWithAccount();
        deleteUserRemovesUserAndAccount();
        missingKeyReturnsNullAndDeleteReturnsFalse();
        System.out.println("UserDataBaseTest passed");
    }

    private static void addAndFindUserWithAccount() {
        UserDataBase db = new UserDataBase();
        User user = new User("Steven", "steven@example.com", "555-0101", "USD");
        ExpenseAccount account = new ExpenseAccount();
        db.addUser("steven", user, account);
        assert db.findUser("steven") == user;
        assert db.findUserAccount("steven") == account;
    }

    private static void deleteUserRemovesUserAndAccount() {
        UserDataBase db = new UserDataBase();
        db.addUser("steven", new User("Steven", "steven@example.com", "555-0101", "USD"), new ExpenseAccount());
        assert db.deleteUser("steven");
        assert db.findUser("steven") == null;
        assert db.findUserAccount("steven") == null;
    }

    private static void missingKeyReturnsNullAndDeleteReturnsFalse() {
        UserDataBase db = new UserDataBase();
        assert db.findUser("missing") == null;
        assert db.findUserAccount("missing") == null;
        assert !db.deleteUser("missing");
    }
}
