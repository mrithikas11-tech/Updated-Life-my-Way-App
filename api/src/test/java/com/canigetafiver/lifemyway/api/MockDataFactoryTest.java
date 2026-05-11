package com.canigetafiver.lifemyway.api;

public class MockDataFactoryTest {
    public static void main(String[] args) {
        assert MockDataFactory.createUsers().size() == 2;
        assert MockDataFactory.createCategories().size() == 3;
        assert MockDataFactory.createExpenses().size() == 10;

        UserDataBase db = MockDataFactory.createUserDataBase();
        assert db.getUsers().size() == 2;
        assert db.getUserAccounts().size() == 2;
        assert db.findUserAccount("steven") != null;
        System.out.println("MockDataFactoryTest passed");
    }
}
