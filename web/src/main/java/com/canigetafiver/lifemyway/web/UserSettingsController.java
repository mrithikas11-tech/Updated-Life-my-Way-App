package com.canigetafiver.lifemyway.web;

import com.canigetafiver.lifemyway.api.ExpenseAccount;
import com.canigetafiver.lifemyway.api.PersistenceManager;
import com.canigetafiver.lifemyway.api.User;
import com.canigetafiver.lifemyway.api.UserDataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Coordinates the user settings screen with the core user persistence API.
 */
public class UserSettingsController {
    private static final String DEFAULT_USER_ID = "currentUser";
    private static final String DEFAULT_DATA_PATH = "user-data.json";

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Label statusLabel;

    private final PersistenceManager persistenceManager;
    private UserDataBase userDataBase;
    private User currentUser;
    private String userId;
    private String dataPath;

    public UserSettingsController() {
        persistenceManager = new PersistenceManager();
        userDataBase = new UserDataBase();
        currentUser = new User("", "", "", "USD");
        userId = DEFAULT_USER_ID;
        dataPath = DEFAULT_DATA_PATH;
    }

    @FXML
    private void initialize() {
        currencyComboBox.setValue(currentUser.getPreferredCurrency());
        loadUserValues();
    }

    public void loadUser(UserDataBase userDataBase, String userId, String dataPath) {
        this.userDataBase = userDataBase;
        this.userId = userId;
        this.dataPath = dataPath;
        User foundUser = userDataBase.findUser(userId);
        if (foundUser != null) {
            currentUser = foundUser;
        }
        loadUserValues();
    }

    @FXML
    private void handleSave() {
        currentUser.setName(nameField.getText());
        currentUser.setEmail(emailField.getText());
        currentUser.setNumber(phoneField.getText());
        currentUser.setPreferredCurrency(currencyComboBox.getValue());

        if (userDataBase.findUser(userId) == null) {
            userDataBase.addUser(userId, currentUser, new ExpenseAccount());
        }

        persistenceManager.saveUserData(userDataBase, dataPath);
        statusLabel.setText("Settings saved successfully.");
    }

    private void loadUserValues() {
        if (nameField == null) {
            return;
        }
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getNumber());
        currencyComboBox.setValue(currentUser.getPreferredCurrency());
        statusLabel.setText("");
    }
}
