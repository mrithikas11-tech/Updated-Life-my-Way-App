package com.canigetafiver.lifemyway.web;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

import com.canigetafiver.lifemyway.service.BudgetManager;
import com.canigetafiver.lifemyway.service.Category;
import com.canigetafiver.lifemyway.service.Period;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BudgetSystemController {

    @FXML private TextField weeklyAmountField;
    @FXML private TextField monthlyAmountField;
    private double amount;
    private Period period;

    public Period getPeriod() {
        return period;
    }

    @FXML
    private void handleWeekly() {
        period = Period.WEEKLY;
        weeklyAmountField.setVisible(true);
        weeklyAmountField.setManaged(true);
        monthlyAmountField.setVisible(false);
        monthlyAmountField.setManaged(false);
    }

    @FXML
    private void handleMonthly() {
        period = Period.MONTHLY;
        monthlyAmountField.setVisible(true);
        monthlyAmountField.setManaged(true);
        weeklyAmountField.setVisible(false);
        weeklyAmountField.setManaged(false);
    }

    @FXML
    private void handleWeeklyAmount() throws IOException {
        try {
            amount = Double.parseDouble(weeklyAmountField.getText());
            BudgetManager budgetManager = new BudgetManager();
            budgetManager.addBudget(amount, Period.WEEKLY, Category.GENERAL);
            loadCustomization(budgetManager, weeklyAmountField);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    private void handleMonthlyAmount() throws IOException {
        try {
            amount = Double.parseDouble(monthlyAmountField.getText());
            BudgetManager budgetManager = new BudgetManager();
            budgetManager.addBudget(amount, Period.MONTHLY, Category.GENERAL);
            loadCustomization(budgetManager, monthlyAmountField);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/views/HomeView.fxml"));
        javafx.scene.Parent root = loader.load();
        javafx.scene.Scene scene = new javafx.scene.Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
        Stage stage = (Stage) weeklyAmountField.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    private void loadCustomization(BudgetManager budgetManager, javafx.scene.control.Control sourceNode)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BudgetCustomization.fxml"));
        StackPane root = loader.load();
        BudgetCustomizationController controller = loader.getController();
        controller.setAmount(amount);
        controller.setPeriod(period);
        controller.getBudgetManager().getBudgetList().addAll(budgetManager.getBudgetList());
        Scene scene = new Scene(root, 1280, 720);
        String css = getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customization");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
