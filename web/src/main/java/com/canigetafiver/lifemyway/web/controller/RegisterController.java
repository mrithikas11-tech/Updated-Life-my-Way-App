package com.canigetafiver.lifemyway.web.controller;
import com.canigetafiver.lifemyway.web.auth.AuthenticationService;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML controller for RegisterView backing
 */
public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField displayNameField;
    @FXML private Label errorLabel;

    @FXML private void initialize(){
        if(errorLabel !=null){
            errorLabel.setText("");
        }
    }

    @FXML private void onRegister(){
        AuthenticationService auth=NavigationController.getInstance().authService();
        AuthenticationService.Result result=auth.register(
            usernameField.getText(), passwordField.getText(), confirmPasswordField.getText(), displayNameField.getText());
        if(result.success()){
            NavigationController.getInstance().setFlashMessage("Account created - please log in.");
            NavigationController.getInstance().navigateTo(View.LOGIN);
        }else if(errorLabel!=null){
            if (!errorLabel.getStyleClass().contains("error-label")){
                errorLabel.getStyleClass().add("error-label");
            }
            errorLabel.setText(result.message());
        }
    }

    @FXML private void onCancel(){
        NavigationController.getInstance().navigateTo(View.LOGIN);
    }

    private static String text(TextField field){
        return field ==null? "":field.getText();
    }
}
