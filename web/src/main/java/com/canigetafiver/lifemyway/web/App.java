package com.canigetafiver.lifemyway.web;

import com.canigetafiver.lifemyway.web.auth.AuthenticationService;
import com.canigetafiver.lifemyway.web.auth.PersistedUserStore;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        PersistedUserStore userStore = new PersistedUserStore();
        AuthenticationService authService = new AuthenticationService(userStore);
        NavigationController nav = NavigationController.getInstance();
        nav.init(primaryStage, authService);
        nav.setUserStore(userStore);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(700);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        nav.navigateTo(View.LOGIN);
    }
}
