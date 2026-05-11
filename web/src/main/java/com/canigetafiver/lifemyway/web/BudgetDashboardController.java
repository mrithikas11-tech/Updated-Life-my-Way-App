package com.canigetafiver.lifemyway.web;

import java.io.IOException;
import java.util.List;

import com.canigetafiver.lifemyway.service.Budget;
import com.canigetafiver.lifemyway.service.BudgetManager;
import com.canigetafiver.lifemyway.service.Category;
import com.canigetafiver.lifemyway.service.Period;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BudgetDashboardController {

    @FXML private Label currentBudgetLabel;
    @FXML private Label totalBudgetAmount;
    @FXML private ProgressBar totalProgressBar;
    @FXML private Label totalProgressLabel;
    @FXML private VBox budgetsContainer;
    @FXML private VBox expensesContainer;
    @FXML private VBox notificationsContainer;
    @FXML private Label noAlertsLabel;

    private BudgetManager budgetManager;
    private double totalBudget;
    private Period period;

    // sets budget data

    public void setCurrentBudget(Period period) {
        this.period = period;
        if (period == Period.WEEKLY) {
            currentBudgetLabel.setText("This Week's Budget");
        } else {
            currentBudgetLabel.setText("This Month's Budget");
        }
    }

    public void setBudgetAmount(double budget) {
        this.totalBudget = budget;
        totalBudgetAmount.setText("$" + String.format("%.2f", budget));
    }

    public void setBudgetManager(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    // creates cards for each of the elements in the dashbaord (budget, expense, notifications)

    @FXML
    public void createBudgetCards() {
        budgetsContainer.getChildren().clear();
        List<Budget> categories = budgetManager.getCategoryBudgets();

        if (categories.isEmpty()) {
            Label empty = new Label("No category budgets set.");
            empty.getStyleClass().add("muted-label");
            budgetsContainer.getChildren().add(empty);
            return;
        }

        double totalAllocated = budgetManager.getTotalAllocated();
        // Update the top-level progress bar to show allocation vs total
        if (totalBudget > 0) {
            double ratio = Math.min(totalAllocated / totalBudget, 1.0);
            totalProgressBar.setProgress(ratio);
            totalProgressLabel.setText(String.format("$%.2f allocated of $%.2f", totalAllocated, totalBudget));
            if (totalAllocated > totalBudget) {
                totalProgressBar.getStyleClass().add("progress-bar-exceeded");
                totalProgressLabel.getStyleClass().add("error-label");
            }
        }

        for (Budget b : categories) {
            HBox row = buildCategoryRow(b);
            budgetsContainer.getChildren().add(row);
        }
    }

    private HBox buildCategoryRow(Budget b) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("category-row");

        Label nameLabel = new Label(formatCategory(b.getCategory()));
        nameLabel.setPrefWidth(160);
        nameLabel.getStyleClass().add("form-label");

        Label capLabel = new Label("Cap: $" + String.format("%.2f", b.getAmount()));
        capLabel.setPrefWidth(120);
        capLabel.getStyleClass().add("subtitle");

        ProgressBar bar = new ProgressBar(0.0);
        bar.setPrefWidth(200);

        Label spentLabel = new Label("$0.00 spent");
        spentLabel.getStyleClass().add("muted-label");

        // If the budget has been spent on (via calculateRemaining), show it
        double spent = b.getAmount() - b.getRemaining();
        if (spent > 0 && b.getAmount() > 0) {
            double ratio = Math.min(spent / b.getAmount(), 1.0);
            bar.setProgress(ratio);
            spentLabel.setText(String.format("$%.2f spent", spent));
        }

        if (b.isExceeded()) {
            nameLabel.getStyleClass().add("error-label");
            capLabel.getStyleClass().add("error-label");
            bar.getStyleClass().add("progress-bar-exceeded");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().addAll(nameLabel, capLabel, bar, spacer, spentLabel);
        return row;
    }

    
    @FXML
    public void createExpenseCards() {
        expensesContainer.getChildren().clear();
        Label empty = new Label("No expenses recorded yet.");
        empty.getStyleClass().add("muted-label");
        expensesContainer.getChildren().add(empty);
    }

   
    @FXML
    public void createNotificationCards() {
        notificationsContainer.getChildren().clear();
        List<Budget> categories = budgetManager.getCategoryBudgets();
        boolean hasAlerts = false;

        // Top-level check: total allocated vs total budget
        double totalAllocated = budgetManager.getTotalAllocated();
        if (totalBudget > 0 && totalAllocated >= totalBudget * 0.8) {
            String msg = totalAllocated >= totalBudget
                    ? "⚠ Overall budget limit exceeded! ($" + String.format("%.2f", totalAllocated) + " allocated)"
                    : "⚠ You've allocated " + String.format("%.0f%%", (totalAllocated / totalBudget) * 100)
                      + " of your total budget.";
            notificationsContainer.getChildren().add(makeAlert(msg, totalAllocated >= totalBudget));
            hasAlerts = true;
        }

        // Per-category checks
        for (Budget b : categories) {
            if (b.getAmount() <= 0) continue;
            double spent = b.getAmount() - b.getRemaining();
            double ratio = spent / b.getAmount();
            if (ratio >= 1.0) {
                notificationsContainer.getChildren().add(
                    makeAlert("⚠ " + formatCategory(b.getCategory()) + " budget exceeded!", true));
                hasAlerts = true;
            } else if (ratio >= 0.8) {
                notificationsContainer.getChildren().add(
                    makeAlert("You've used " + String.format("%.0f%%", ratio * 100)
                              + " of your " + formatCategory(b.getCategory()) + " budget.", false));
                hasAlerts = true;
            }
        }

        noAlertsLabel.setVisible(!hasAlerts);
        noAlertsLabel.setManaged(!hasAlerts);
        notificationsContainer.setVisible(hasAlerts);
        notificationsContainer.setManaged(hasAlerts);
    }

    private Label makeAlert(String text, boolean isError) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.getStyleClass().add(isError ? "error-label" : "hint-label");
        return lbl;
    }

    @FXML
    public void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BudgetSystem.fxml"));
        javafx.scene.layout.StackPane root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) currentBudgetLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Budget Setup");
        stage.show();
    }

    // number formatting

    private String formatCategory(Category c) {
        String raw = c.name();
        return raw.charAt(0) + raw.substring(1).toLowerCase();
    }
}
