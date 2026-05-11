package com.canigetafiver.lifemyway.web.controller;
import com.canigetafiver.lifemyway.web.auth.AuthenticationService;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML controlller for LoginView backing
 */
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML private void initialize(){ //carries over "Account created" flash msg from RegisterController
        String flash=NavigationController.getInstance().consumeFlashMessage();
        if(flash!=null&& !flash.isBlank()){
            statusLabel.setText(flash);
            statusLabel.getStyleClass().removeAll("error-label");
            statusLabel.getStyleClass().add("success-label");
        }else{
            statusLabel.setText("");
        }
    }

    @FXML private void onLogin(){
        AuthenticationService auth=NavigationController.getInstance().authService();
        AuthenticationService.Result result=auth.login(usernameField.getText(),passwordField.getText());
        if(result.success()){
            NavigationController.getInstance().navigateTo(View.HOME);
        }else{
            statusLabel.getStyleClass().removeAll("success-label");
            if(!statusLabel.getStyleClass().contains("error-label")){
                statusLabel.getStyleClass().add("error-label");
            }
            statusLabel.setText(result.message());
        }
    }
    @FXML private void onGoToRegister(){
        NavigationController.getInstance().navigateTo(View.REGISTER);
    }
}
