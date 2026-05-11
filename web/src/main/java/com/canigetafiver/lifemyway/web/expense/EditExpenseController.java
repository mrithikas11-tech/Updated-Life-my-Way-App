package com.canigetafiver.lifemyway.web.expense;

import com.canigetafiver.lifemyway.api.Category;
import com.canigetafiver.lifemyway.api.Expense;
import com.canigetafiver.lifemyway.api.ExpenseAccount;
import com.canigetafiver.lifemyway.api.PaymentMethod;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditExpenseController {

    @FXML
    DatePicker datePicker;
    @FXML
    ChoiceBox<Category> categoryChoiceBox;
    @FXML
    ChoiceBox<PaymentMethod> paymentMethodChoiceBox;
    @FXML
    TextField amountTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    TextField vendorTextField;

    private Expense originalExpense;
    private Expense expenseToEdit;
    private ExpenseAccount targetAccount;

    public void setExpenseToEdit(Expense e) {
        this.originalExpense = e;
        this.expenseToEdit = e;
    }
    public void setTargetAccount(ExpenseAccount account) { this.targetAccount = account; }

    @FXML
    private void initialize() {
        // Populate category and payment dropdowns first
        categoryChoiceBox.getItems().setAll(Category.values());
        paymentMethodChoiceBox.getItems().setAll(PaymentMethod.values());

        // If parent didn't push an expense to edit yet, pull one from the NavigationController's
        // pending slot (set by ExpenseListController.onEdit), otherwise fall back to a blank.
        if (expenseToEdit == null) {
            expenseToEdit = NavigationController.getInstance().consumePendingEditTarget();
        }
        if (expenseToEdit == null) {
            expenseToEdit = new Expense();
        }
        originalExpense = expenseToEdit;

        // Pull the active ExpenseAccount from the navigation singleton if not pushed in.
        if (targetAccount == null) {
            targetAccount = NavigationController.getInstance().currentAccount();
        }

        datePicker.setValue(expenseToEdit.getDate() != null ? expenseToEdit.getDate() : LocalDate.now());
        categoryChoiceBox.setValue(expenseToEdit.getCategory());
        paymentMethodChoiceBox.setValue(expenseToEdit.getPaymentMethod());
        amountTextField.setText(String.valueOf(expenseToEdit.getAmount()));
        vendorTextField.setText(expenseToEdit.getVendor());
        descriptionTextArea.setText(expenseToEdit.getDescription());

        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                amountTextField.setText(oldValue);
            }
        });
    }

    @FXML
    private void cancelEdit() {
        NavigationController.getInstance().navigateTo(View.EXPENSE_LIST);
    }

    @FXML
    private void resetFields() {
        if (originalExpense == null) return;
        datePicker.setValue(originalExpense.getDate());
        categoryChoiceBox.setValue(originalExpense.getCategory());
        paymentMethodChoiceBox.setValue(originalExpense.getPaymentMethod());
        amountTextField.setText(String.valueOf(originalExpense.getAmount()));
        vendorTextField.setText(originalExpense.getVendor());
        descriptionTextArea.setText(originalExpense.getDescription());
    }

    @FXML
    private void saveEdit() {
        try {
            Expense updated = new Expense.ExpenseBuilder()
                .amount(Double.parseDouble(amountTextField.getText()))
                .category(categoryChoiceBox.getValue())
                .paymentMethod(paymentMethodChoiceBox.getValue())
                .description(descriptionTextArea.getText())
                .vendor(vendorTextField.getText())
                .date(datePicker.getValue())
                .build();

            // Swap original with updated in the account (delete + add).
            if (targetAccount != null && originalExpense != null) {
                targetAccount.getExpenses().remove(originalExpense);
                targetAccount.addExpense(updated);
            }
            NavigationController.getInstance().navigateTo(View.EXPENSE_LIST);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error updating expense: " + ex.getMessage());
        }
    }
}
