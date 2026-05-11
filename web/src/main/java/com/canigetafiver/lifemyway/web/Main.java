package com.canigetafiver.lifemyway.web; 

import com.canigetafiver.lifemyway.service.BudgetManager;
import com.canigetafiver.lifemyway.service.Category;
import com.canigetafiver.lifemyway.service.Period;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budgets");
        StackPane root = new StackPane(); 
        primaryStage.setScene(new Scene(root, 1280,720));
        VBox BudgetBox = new VBox();
        BudgetBox.setSpacing(10);
        BudgetBox.setPadding(new Insets(15));


    
        
        BudgetBox.setStyle( "-fx-border-color: black;" +
        "-fx-border-width: 2;" +
        "-fx-background-color: lightgray;");

        BudgetBox.setMaxSize(200, 100);
        BudgetBox.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.TOP_CENTER);
    
        Label label = new Label("Pick your Budget:");
        label.setAlignment(Pos.CENTER);
    
        HBox WelcomeOptionsBox = new HBox();
        Button WeeklyButton = new Button("Weekly");
         Button MonthlyButton = new Button("Monthly");

        TextField WeeklyAmountField = new TextField();
        WeeklyAmountField.setPromptText("Enter Weekly amount:");  
        WeeklyAmountField.setVisible(false);
        WeeklyAmountField.setManaged(false);

        TextField MonthlyAmountField = new TextField();
        MonthlyAmountField.setPromptText("Enter Monthly amount:");  
        MonthlyAmountField.setVisible(false);
        MonthlyAmountField.setManaged(false);

        


        WeeklyButton.setOnAction(e -> {
            WeeklyAmountField.setVisible(true);
            WeeklyAmountField.setManaged(true);

            MonthlyAmountField.setVisible(false);
            MonthlyAmountField.setManaged(false);

        
        } );

        WeeklyAmountField.setOnAction( e -> {
            try {
               double amount =  Double.parseDouble(WeeklyAmountField.getText());
               BudgetManager budgetManager = new BudgetManager();
               budgetManager.addBudget(amount, Period.WEEKLY, Category.GENERAL);


            } 
            catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid number");
                alert.show();
            }
            
        }
        );

         MonthlyButton.setOnAction(e -> {
            MonthlyAmountField.setVisible(true);
            MonthlyAmountField.setManaged(true);

            WeeklyAmountField.setVisible(false);
            WeeklyAmountField.setManaged(false);

        
        } );
        
        MonthlyAmountField.setOnAction( e -> {
            try {
               double amount =  Double.parseDouble(MonthlyAmountField.getText());
               BudgetManager budgetManager = new BudgetManager();
               budgetManager.addBudget(amount, Period.MONTHLY, Category.GENERAL);


            } 
            catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid number");
                alert.show();
            }
            
        }
        );





        WelcomeOptionsBox.setAlignment(Pos.CENTER);
        WelcomeOptionsBox.getChildren().add(WeeklyButton);
        WelcomeOptionsBox.getChildren().add(MonthlyButton);
        WelcomeOptionsBox.getChildren().add(WeeklyAmountField);
        WelcomeOptionsBox.getChildren().add(MonthlyAmountField);
         



        BudgetBox.getChildren().add(label);
        root.getChildren().add(BudgetBox);
        root.getChildren().add(WelcomeOptionsBox);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
