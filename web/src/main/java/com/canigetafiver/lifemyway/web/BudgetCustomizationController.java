package com.canigetafiver.lifemyway.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import com.canigetafiver.lifemyway.service.BudgetManager;
import com.canigetafiver.lifemyway.service.Category;
import com.canigetafiver.lifemyway.service.Period;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BudgetCustomizationController {

    @FXML private Label amountLabel;
    @FXML private Button newCategory;
    @FXML private VBox NewCategoriesContainer;
    @FXML private TextField EntertainmentBudgetField;
    @FXML private TextField FoodBudgetField;
    @FXML private TextField HealthBudgetField;
    @FXML private TextField RentBudgetField;
    @FXML private TextField TransportationBudgetField;
    @FXML private TextField UtilitiesBudgetField;
    @FXML private TextField OtherBudgetField;
    @FXML private HBox EntertainmentContainer;
    @FXML private HBox FoodContainer;
    @FXML private HBox HealthContainer;
    @FXML private HBox RentContainer;
    @FXML private HBox TransportationContainer;
    @FXML private HBox UtilitiesContainer;
    @FXML private HBox OtherContainer;
    @FXML private Label successLabel1;
    @FXML private Label successLabel2;
    @FXML private Label successLabel3;
    @FXML private Label successLabel4;
    @FXML private Label successLabel5;
    @FXML private Label successLabel6;

    private double budget = 0.0;
    private double amount = 0.0;
    private double entertainmentPreviousAmount = 0.0;
    private double foodPreviousAmount = 0.0;
    private double healthPreviousAmount = 0.0;
    private double rentPreviousAmount = 0.0;
    private double transportationPreviousAmount = 0.0;
    private double utilitiesPreviousAmount = 0.0;
    private int newCategoryCounter = 0;
    private int totalCategoryCounter = 0;
    private ArrayList<Button> categoryButtons = new ArrayList<>();
    private Stack<HBox> deletedCategoryHistory = new Stack<>();
    private Period period;
    private BudgetManager budgetManager = new BudgetManager();

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }

    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    @FXML
    public void setAmount(double amount) {
        budget = amount;
        updateRemainingLabel();
    }

    /** Refresh amountLabel to show the budget left after current allocations. */
    private void updateRemainingLabel() {
        if (amountLabel == null) return;
        double remaining = budget - this.amount;
        amountLabel.setText("$" + String.format("%.2f", remaining)
            + "   (of $" + String.format("%.2f", budget) + " total)");
    }

    @FXML
    public void handleEntertainmentCategory() {
        try {
            double newAmount = Double.parseDouble(EntertainmentBudgetField.getText());
            double newTotal = amount - entertainmentPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.ENTERTAINMENT);
            budgetManager.addBudget(newAmount, period, Category.ENTERTAINMENT);
            entertainmentPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel1.setText("Success! Entertainment: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel1);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleFoodCategory() {
        try {
            double newAmount = Double.parseDouble(FoodBudgetField.getText());
            double newTotal = amount - foodPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.FOOD);
            budgetManager.addBudget(newAmount, period, Category.FOOD);
            foodPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel2.setText("Success! Food: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel2);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleHealthCategory() {
        try {
            double newAmount = Double.parseDouble(HealthBudgetField.getText());
            double newTotal = amount - healthPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.HEALTH);
            budgetManager.addBudget(newAmount, period, Category.HEALTH);
            healthPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel3.setText("Success! Health: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel3);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleRentCategory() {
        try {
            double newAmount = Double.parseDouble(RentBudgetField.getText());
            double newTotal = amount - rentPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.RENT);
            budgetManager.addBudget(newAmount, period, Category.RENT);
            rentPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel4.setText("Success! Rent: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel4);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleTransportationCategory() {
        try {
            double newAmount = Double.parseDouble(TransportationBudgetField.getText());
            double newTotal = amount - transportationPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.TRANSPORTATION);
            budgetManager.addBudget(newAmount, period, Category.TRANSPORTATION);
            transportationPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel5.setText("Success! Transportation: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel5);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleUtilitiesCategory() {
        try {
            double newAmount = Double.parseDouble(UtilitiesBudgetField.getText());
            double newTotal = amount - utilitiesPreviousAmount + newAmount;
            if (newTotal > budget) {
                showError("Total category budgets exceed your overall budget. Please lower the amount.");
                return;
            }
            amount = newTotal;
            budgetManager.deleteBudget(Category.UTILITIES);
            budgetManager.addBudget(newAmount, period, Category.UTILITIES);
            utilitiesPreviousAmount = newAmount;
            totalCategoryCounter++;
            updateRemainingLabel();
            successLabel6.setText("Success! Utilities: $" + String.format("%.2f", newAmount));
            showSuccess(successLabel6);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    @FXML
    public void handleOtherCategory() {
        if (newCategoryCounter >= 4) {
            showError("You have reached the maximum number of custom budget categories.");
            return;
        }

        String categoryName = OtherBudgetField.getText().trim();
        if (categoryName.isEmpty()) {
            showError("Please enter a category name.");
            return;
        }

        newCategoryCounter++;
        totalCategoryCounter++;

        HBox newCategoryPanel = new HBox();
        VBox.setMargin(newCategoryPanel, new Insets(5, 0, 0, 0));
        newCategoryPanel.setSpacing(10);

        Button deleteBtn = new Button(categoryName);
        deleteBtn.getStyleClass().add("primary-button");
        deleteBtn.setPrefWidth(250);
        deleteBtn.setPrefHeight(62);
        deleteBtn.setOnAction(this::handleDeleteButton);
        categoryButtons.add(deleteBtn);

        TextField amountField = new TextField();
        amountField.setPromptText("Enter " + categoryName + " budget:");

        Label successLbl = new Label();
        successLbl.getStyleClass().add("success-label");
        successLbl.setVisible(false);
        successLbl.setManaged(false);

        // Track previous amount for this specific custom category so edits work correctly
        double[] previousCustomAmount = {0.0};

        amountField.setOnAction(e -> {
            try {
                double catAmount = Double.parseDouble(amountField.getText());
                // FIX: use delta like the named categories, not a running add
                double newTotal = amount - previousCustomAmount[0] + catAmount;
                if (newTotal > budget) {
                    showError("Total category budgets exceed your overall budget.");
                } else {
                    amount = newTotal;
                    previousCustomAmount[0] = catAmount;
                    budgetManager.deleteBudget(Category.OTHER);
                    budgetManager.addBudget(catAmount, period, Category.OTHER);
                    successLbl.setText("Added $" + String.format("%.2f", catAmount));
                    successLbl.setVisible(true);
                    successLbl.setManaged(true);
                    updateRemainingLabel();
                }
            } catch (NumberFormatException ex) {
                showError("Please enter a valid number.");
            }
        });

        newCategoryPanel.getChildren().addAll(deleteBtn, amountField, successLbl);
        NewCategoriesContainer.getChildren().add(newCategoryPanel);
        NewCategoriesContainer.setSpacing(10);
        OtherBudgetField.clear();
    }

    @FXML
    public void handleDeleteButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        HBox clickedButtonBox = (HBox) clickedButton.getParent();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete This Budget");
        alert.setHeaderText("Choose an Option:");
        alert.setContentText("Would you like to delete this budget?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            deletedCategoryHistory.push(clickedButtonBox);
            NewCategoriesContainer.getChildren().remove(clickedButtonBox);
            clickedButtonBox.setVisible(false);
            clickedButtonBox.setManaged(false);
            NewCategoriesContainer.setManaged(true);
            totalCategoryCounter--;
            budgetManager.deleteBudget(Category.OTHER);
            newCategoryCounter = Math.max(0, newCategoryCounter - 1);
        }
    }

    @FXML
    public void handleUndoButton() {
        if (!deletedCategoryHistory.isEmpty()) {
            HBox lastPanel = deletedCategoryHistory.pop();
            lastPanel.setVisible(true);
            lastPanel.setManaged(true);
            Button lastButton = (Button) lastPanel.getChildren().get(0);
            categoryButtons.add(lastButton);
            NewCategoriesContainer.getChildren().add(lastPanel);
            newCategoryCounter++;
            totalCategoryCounter++;
            updateRemainingLabel();
        }
    }

    @FXML
    public void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BudgetSystem.fxml"));
        StackPane root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        String css = getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = (Stage) amountLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Budget Setup");
        stage.show();
    }

    @FXML
    public void handleNextButton() throws IOException {
        BudgetSession.budgetManager = budgetManager;
        BudgetSession.period = period;
        BudgetSession.totalBudget = budget;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/canigetafiver/lifemyway/web/views/HomeView.fxml"));
        javafx.scene.Parent root = loader.load();
        javafx.scene.Scene scene = new javafx.scene.Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/com/canigetafiver/lifemyway/web/styles.css").toExternalForm());
        Stage stage = (Stage) amountLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void showSuccess(Label label) {
        label.getStyleClass().removeAll("error-label");
        if (!label.getStyleClass().contains("success-label")) {
            label.getStyleClass().add("success-label");
        }
        label.setVisible(true);
        label.setManaged(true);
    }
}
