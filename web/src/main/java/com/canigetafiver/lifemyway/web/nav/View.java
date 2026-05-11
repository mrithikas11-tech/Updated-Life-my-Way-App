package com.canigetafiver.lifemyway.web.nav;

/**
 * cental routing table for every screen in the app
 */
public enum View {
    LOGIN("/com/canigetafiver/lifemyway/web/views/LoginView.fxml", "Life My Way — Sign In"),
    REGISTER("/com/canigetafiver/lifemyway/web/views/RegisterView.fxml", "Life My Way — Create Account"),
    HOME("/com/canigetafiver/lifemyway/web/views/HomeView.fxml", "Life My Way — Home"),
    EXPENSE_LIST("/com/canigetafiver/lifemyway/web/views/ExpenseListView.fxml", "Life My Way — Expenses"),
    ADD_EXPENSE("/com/canigetafiver/lifemyway/web/AddExpense.fxml", "Life My Way — Add Expense"),
    EDIT_EXPENSE("/com/canigetafiver/lifemyway/web/EditExpense.fxml", "Life My Way — Edit Expense"),
    SETTINGS("/com/canigetafiver/lifemyway/web/UserSettingsView.fxml", "Life My Way — Settings"),
    BUDGET_DASHBOARD("/com/canigetafiver/lifemyway/web/BudgetDashboard.fxml", "Life My Way — Budgets"),
    BUDGET_SYSTEM("/com/canigetafiver/lifemyway/web/BudgetSystem.fxml", "Life My Way — Budget System"),
    BUDGET_CUSTOMIZATION("/com/canigetafiver/lifemyway/web/BudgetCustomization.fxml", "Life My Way — Customize Budget");

    private final String fxmlPath;
    private final String title;

    View(String fxmlPath,String title){
        this.fxmlPath=fxmlPath;
        this.title=title;
    }
    public String fxmlPath(){ return fxmlPath; }
    public String title(){return title;}
}
