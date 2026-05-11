package com.canigetafiver.lifemyway.web.controller;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML controller backing ExpenseListView 
 */
public class ExpenseListController {
    @FXML private TableView<DemoRow> expenseTable;
    @FXML private TableColumn<DemoRow, String> dateColumn;
    @FXML private TableColumn<DemoRow, String> amountColumn;
    @FXML private TableColumn<DemoRow, String> categoryColumn;
    @FXML private TableColumn<DemoRow, String> descriptionColumn;

    @FXML private void initialize(){
        if(expenseTable==null) return;
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        expenseTable.setItems(demoRows());
    }

    @FXML private void onBack(){
        NavigationController.getInstance().navigateTo(View.HOME);
    }

    private static ObservableList<DemoRow> demoRows(){
        ObservableList<DemoRow> rows=FXCollections.observableArrayList();
        rows.add(new DemoRow("2026-05-08", "$12.50", "FOOD", "Lunch"));
        rows.add(new DemoRow("2026-05-09", "$45.00", "TRANSPORT", "Gas"));
        rows.add(new DemoRow("2026-05-10", "$875.00", "RENT", "Rent for May"));
        rows.add(new DemoRow("2026-05-11", "$22.75", "ENTERTAINMENT", "Movie night"));
        return rows;
    }

    public static class DemoRow{
        private final SimpleStringProperty date;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty category;
        private final SimpleStringProperty description;

        public DemoRow(String date, String amount, String category, String description){
            this.date=new SimpleStringProperty(date);
            this.amount= new SimpleStringProperty(amount);
            this.category =new SimpleStringProperty(category);
            this.description=new SimpleStringProperty(description);
        }

        public String getDate(){ return date.get();}
        public String getAmount(){ return amount.get();}
        public String getCategory(){ return category.get();}
        public String getDescription(){ return description.get();}
    }
}
