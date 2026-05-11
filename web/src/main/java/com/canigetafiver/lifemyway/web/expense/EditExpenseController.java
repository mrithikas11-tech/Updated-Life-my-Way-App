package com.canigetafiver.lifemyway.web.expense;

import java.io.IOException;

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
    //TODO get expense from list
    private Expense originalExpense; // This will hold the original expense being edited
    private Expense expenseToEdit = new Expense(); //test
//empty constructor for now, will need to be able to take in an existing expense to edit
    @FXML
    private void initialize() {
        // Set initial date to current day
        datePicker.setValue(expenseToEdit.getDate());
        //updateLabelWithDate(LocalDate.now());
        // Populate category choice box with enum values
        categoryChoiceBox.getItems().setAll(Category.values());
        // Populate payment method choice box with enum values
        paymentMethodChoiceBox.getItems().setAll(PaymentMethod.values());

        amountTextField.setText(String.valueOf(expenseToEdit.getAmount()));
        // Add listener to amount text field to ensure only valid numbers are entered
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                // Remove invalid characters
                amountTextField.setText(oldValue);
            }
        });

        vendorTextField.setText(expenseToEdit.getVendor());
        descriptionTextArea.setText(expenseToEdit.getDescription());
    }

    //TODO fix
    @FXML
    private void cancelEdit()  {
        
    }
    @FXML
    private void resetFields() {
        datePicker.setValue(expenseToEdit.getDate());
        categoryChoiceBox.setValue(expenseToEdit.getCategory());
        paymentMethodChoiceBox.setValue(expenseToEdit.getPaymentMethod());
        amountTextField.setText(String.valueOf(expenseToEdit.getAmount()));
        vendorTextField.setText(expenseToEdit.getVendor());
        descriptionTextArea.setText(expenseToEdit.getDescription());
    }
    //TODO: Add the ability to delete the expense, which would remove it from the list of expenses in the primary controller and navigate back to the primary view.
    @FXML
    private void saveEdit() {
        // Here you would typically gather the updated information from the UI components
        // and update the existing expense object, then navigate back to the primary view.
        // For example:
        expenseToEdit = new Expense.ExpenseBuilder()
                .amount(Double.parseDouble(amountTextField.getText()))
                .category(categoryChoiceBox.getValue())
                .paymentMethod(paymentMethodChoiceBox.getValue())
                .description(descriptionTextArea.getText())
                .vendor(vendorTextField.getText())
                .date(datePicker.getValue())
                .build();
        // After saving the changes, navigate back to the primary view
       
    }


}
