package com.canigetafiver.lifemyway.web.controller;
import java.io.IOException;
import java.util.Optional;

import com.canigetafiver.lifemyway.web.BudgetDashboardController;
import com.canigetafiver.lifemyway.web.BudgetSession;
import com.canigetafiver.lifemyway.web.auth.AuthSession;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML controller for HomeView backing (post-login dashboard)
 */
public class HomeController {
    @FXML private Label welcomeLabel;

    @FXML private void initialize(){
        if(welcomeLabel==null) return;
        try {
            Optional<AuthSession> session=NavigationController.getInstance().authService().currentSession();
            String displayName=session.map(AuthSession::displayName).orElse("Friend");
            welcomeLabel.setText("Welcome, "+displayName+"!");
        } catch(IllegalStateException e){
            welcomeLabel.setText("Welcome!");
        }
    }

    @FXML private void onCreateBudget() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/BudgetSystem.fxml"));
        StackPane root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Budget Setup");
        stage.show();
    }

    @FXML private void onViewDashboard() throws IOException {
        if(BudgetSession.budgetManager == null || BudgetSession.budgetManager.getBudgetList().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please create a budget first before viewing the dashboard.");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/BudgetDashboard.fxml"));
        Parent root = loader.load();
        BudgetDashboardController dashboardController = loader.getController();
        dashboardController.setCurrentBudget(BudgetSession.period);
        dashboardController.setBudgetAmount(BudgetSession.totalBudget);
        dashboardController.setBudgetManager(BudgetSession.budgetManager);
        dashboardController.createBudgetCards();
        dashboardController.createExpenseCards();
        dashboardController.createNotificationCards();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML private void onViewExpenses() throws IOException {
        try {
            NavigationController.getInstance().navigateTo(View.EXPENSE_LIST);
        } catch(IllegalStateException e){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/views/ExpenseListView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML private void onSettings(){
        NavigationController.getInstance().navigateTo(View.SETTINGS);
    }

    @FXML private void onLogout() throws IOException {
        try {
            NavigationController.getInstance().authService().logout();
            NavigationController.getInstance().navigateTo(View.LOGIN);
        } catch(IllegalStateException e){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/views/LoginView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
