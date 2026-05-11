package com.canigetafiver.lifemyway.web.controller;

import com.canigetafiver.lifemyway.api.Expense;
import com.canigetafiver.lifemyway.api.ExpenseAccount;
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
 *
 * Pulls the active user's expenses from the navigation singleton (set on login)
 * and renders them in a TableView. Falls back to a friendly empty state if no
 * account is set yet.
 */
public class ExpenseListController {
    @FXML private TableView<Row> expenseTable;
    @FXML private TableColumn<Row, String> dateColumn;
    @FXML private TableColumn<Row, String> amountColumn;
    @FXML private TableColumn<Row, String> categoryColumn;
    @FXML private TableColumn<Row, String> descriptionColumn;

    @FXML private void initialize(){
        if(expenseTable==null) return;
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        expenseTable.setItems(loadRows());
    }

    @FXML private void onBack(){
        NavigationController.getInstance().navigateTo(View.HOME);
    }

    @FXML private void onAdd(){
        NavigationController.getInstance().navigateTo(View.ADD_EXPENSE);
    }

    @FXML private void onEdit(){
        Row selected = expenseTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;
        NavigationController.getInstance().setPendingEditTarget(selected.source());
        NavigationController.getInstance().navigateTo(View.EDIT_EXPENSE);
    }

    @FXML private void onDelete(){
        Row selected = expenseTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;
        ExpenseAccount account = NavigationController.getInstance().currentAccount();
        if(account == null) return;
        account.getExpenses().remove(selected.source());
        expenseTable.setItems(loadRows());
    }

    private ObservableList<Row> loadRows(){
        ObservableList<Row> rows = FXCollections.observableArrayList();
        ExpenseAccount account = NavigationController.getInstance().currentAccount();
        if(account == null){
            return rows;
        }
        for(Expense e : account.getExpenses()){
            rows.add(Row.from(e));
        }
        return rows;
    }

    /**
     * Row presents a single Expense to the TableView. The four bean-style getters
     * are what PropertyValueFactory needs; source() keeps a reference to the
     * underlying Expense for Edit/Delete to act on.
     */
    public static class Row {
        private final SimpleStringProperty date;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty category;
        private final SimpleStringProperty description;
        private final Expense source;

        public Row(String date, String amount, String category, String description, Expense source){
            this.date = new SimpleStringProperty(date);
            this.amount = new SimpleStringProperty(amount);
            this.category = new SimpleStringProperty(category);
            this.description = new SimpleStringProperty(description);
            this.source = source;
        }

        public static Row from(Expense e){
            String date = e.getDate() == null ? "" : e.getDate().toString();
            String amount = String.format("$%.2f", e.getAmount());
            String category = e.getCategory() == null ? "" : e.getCategory().toString();
            String description = e.getDescription() == null ? "" : e.getDescription();
            return new Row(date, amount, category, description, e);
        }

        public String getDate(){ return date.get(); }
        public String getAmount(){ return amount.get(); }
        public String getCategory(){ return category.get(); }
        public String getDescription(){ return description.get(); }
        public Expense source(){ return source; }
    }
}
