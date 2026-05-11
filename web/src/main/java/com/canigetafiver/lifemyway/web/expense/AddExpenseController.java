package com.canigetafiver.lifemyway.web.expense;


import com.canigetafiver.lifemyway.api.Category;
import com.canigetafiver.lifemyway.api.Expense;
import com.canigetafiver.lifemyway.api.ExpenseAccount;
import com.canigetafiver.lifemyway.api.PaymentMethod;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddExpenseController {
    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private ChoiceBox<Category> categoryChoiceBox;
    @FXML
    private ChoiceBox<PaymentMethod> paymentMethodChoiceBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField vendorTextField;
    @FXML
    private DatePicker datePicker;

    private Expense newExpense;
    // Where the new expense should be saved — set by the parent (ExpenseList) before navigating here,
    // or pulled from NavigationController on save if not set.
    private ExpenseAccount targetAccount;
    public void setTargetAccount(ExpenseAccount account) { this.targetAccount = account; }



    //For date picker, we can use the built in JavaFX DatePicker control, which allows users to select a date from a calendar view. We can also add a button that opens a custom date selection dialog if we want to provide more advanced date selection options.
    @FXML
    private void initialize() {
        // Set initial date to current day
        datePicker.setValue(LocalDate.now());
        //updateLabelWithDate(LocalDate.now());
        // Populate category choice box with enum values
        categoryChoiceBox.getItems().setAll(Category.values());
        // Populate payment method choice box with enum values
        paymentMethodChoiceBox.getItems().setAll(PaymentMethod.values());
        // Add listener to amount text field to ensure only valid numbers are entered
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                // Remove invalid characters
                amountTextField.setText(oldValue);
            }
        });

    }


    @FXML
    private void saveExpense() throws IOException {
        if (amountTextField.getText().isEmpty() || categoryChoiceBox.getValue() == null || paymentMethodChoiceBox.getValue() == null) {
            System.out.println("Amount, category and payment method are required.");
            return;
        }
        try {
            newExpense = new Expense.ExpenseBuilder(
                    Double.parseDouble(amountTextField.getText()),
                    categoryChoiceBox.getValue(),
                    paymentMethodChoiceBox.getValue())
                .description(descriptionTextArea.getText())
                .date(datePicker.getValue())
                .vendor(vendorTextField.getText())
                .build();
        } catch (IllegalArgumentException e) {
            // Handle the exception (e.g., show an error message to the user)
            System.out.println("Error creating expense: " + e.getMessage());
            return;
        }

        // Save into whichever ExpenseAccount the parent gave us; fall back to the
        // current navigation-scoped account.
        ExpenseAccount account = targetAccount != null
            ? targetAccount
            : NavigationController.getInstance().currentAccount();
        if (account != null) {
            account.addExpense(newExpense);
        }

        System.out.println("New Expense: " + newExpense.getAmount() + ", " + newExpense.getCategory()
            + ", " + newExpense.getPaymentMethod() + ", " + newExpense.getDescription()
            + ", " + newExpense.getVendor() + ", " + newExpense.getDate());

        NavigationController.getInstance().navigateTo(View.EXPENSE_LIST);
    }

    @FXML
    private void cancel() throws IOException {
        NavigationController.getInstance().navigateTo(View.EXPENSE_LIST);
    }
    @FXML
    private void selectDate() {
        // This method can be used to handle any additional logic when a date is selected, if needed.
    }

    @FXML
    private void selectCategory() {
        // Stub referenced by AddExpense.fxml onContextMenuRequested handler.
    }

    @FXML
    private void updateAmount() throws IOException {
          amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                // Remove invalid characters
                amountTextField.setText(oldValue);
            }
        });
        amountTextField.setText(amountTextField.getText());

    }
    public Consumer<Expense> getSaveExpenseConsumer() {
        return new Consumer<Expense>() {
            @Override
            public void accept(Expense expense) {
                // Handle the new expense (e.g., add it to a list, update the UI, etc.)
                System.out.println("New Expense: " + expense.getAmount() + ", " + expense.getCategory() + ", " + expense.getPaymentMethod() + ", " + expense.getDescription() + ", " + expense.getVendor() + ", " + expense.getDate());
            }
        };
    }


}
