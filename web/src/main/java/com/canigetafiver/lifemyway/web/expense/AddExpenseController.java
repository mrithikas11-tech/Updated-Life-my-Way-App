package com.canigetafiver.lifemyway.web.expense;


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
            
            try {
                if (amountTextField.getText().isEmpty() || categoryChoiceBox.getValue() == null || paymentMethodChoiceBox.getValue() == null) {
                    newExpense = new Expense.ExpenseBuilder(Double.parseDouble(amountTextField.getText()), Category.DINING, PaymentMethod.APPLE_PAY)
                    .description(descriptionTextArea.getText())
                    .date(datePicker.getValue())
                    .vendor(vendorTextField.getText())
                    .build();
                }
                else{

                     newExpense = new Expense.ExpenseBuilder(Double.parseDouble(amountTextField.getText()), categoryChoiceBox.getValue(), paymentMethodChoiceBox.getValue())
                    .description(descriptionTextArea.getText())
                    .date(datePicker.getValue())
                    .vendor(vendorTextField.getText())
                    .build();
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception (e.g., show an error message to the user)
                System.out.println("Error creating expense: " + e.getMessage());
                return; // Exit the method if there was an error
            }
        if (amountTextField.getText().isEmpty() || categoryChoiceBox.getValue() == null || paymentMethodChoiceBox.getValue() == null) {
             newExpense = new Expense.ExpenseBuilder(Double.parseDouble(amountTextField.getText()), Category.DINING, PaymentMethod.APPLE_PAY)
                .description(descriptionTextArea.getText())
                .date(datePicker.getValue())
                .vendor(vendorTextField.getText())
                .build();
        }
    else{

         newExpense = new Expense.ExpenseBuilder(Double.parseDouble(amountTextField.getText()), categoryChoiceBox.getValue(), paymentMethodChoiceBox.getValue())
                .description(descriptionTextArea.getText())
                .date(datePicker.getValue())
                .vendor(vendorTextField.getText())
                .build();
    }
    //TODO: Add the new expense to the list of expenses in the primary controller
        System.out.println("New Expense: " + newExpense.getAmount() + ", " + newExpense.getCategory() + ", " + newExpense.getPaymentMethod() + ", " + newExpense.getDescription() + ", " + newExpense.getVendor() + ", " + newExpense.getDate());
    //Way to return expense
        
    }  

    @FXML
    private void cancel() throws IOException {
        
    }  
    @FXML
    private void selectDate() {
        // This method can be used to handle any additional logic when a date is selected, if needed.
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
