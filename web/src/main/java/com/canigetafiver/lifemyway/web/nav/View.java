package com.canigetafiver.lifemyway.web.nav;

/**
 * cental routing table for every screen in the app
 */
public enum View {
    LOGIN("/com/canigetafiver/lifemyway/web/views/LoginView.fxml", "Life My Way — Sign In"),
    REGISTER("/com/canigetafiver/lifemyway/web/views/RegisterView.fxml", "Life My Way — Create Account"),
    HOME("/com/canigetafiver/lifemyway/web/views/HomeView.fxml", "Life My Way — Home"),
    EXPENSE_LIST("/com/canigetafiver/lifemyway/web/views/ExpenseListView.fxml", "Life My Way — Expenses");

    private final String fxmlPath;
    private final String title;

    View(String fxmlPath,String title){
        this.fxmlPath=fxmlPath;
        this.title=title;
    }
    public String fxmlPath(){ return fxmlPath; }
    public String title(){return title;}
}
