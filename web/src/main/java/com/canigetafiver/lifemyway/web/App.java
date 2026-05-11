package com.canigetafiver.lifemyway.web;

import com.canigetafiver.lifemyway.service.UserService;
import com.canigetafiver.lifemyway.web.auth.AuthenticationService;
import com.canigetafiver.lifemyway.web.auth.InMemoryUserStore;
import com.canigetafiver.lifemyway.web.nav.NavigationController;
import com.canigetafiver.lifemyway.web.nav.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);  
        System.out.println("Hello, LifeMyWay!");
        UserService userService = new UserService();
        // Use the userService to get user information
        System.out.println(userService.getUser().getName());
    }

    @Override
    public void start(Stage primaryStage){
        AuthenticationService authService=new AuthenticationService(new InMemoryUserStore()); //when user persistence code is ready, replace InMemoryUserStore with the actual user store
        NavigationController nav= NavigationController.getInstance(); //singleton instance of nav controller
        nav.init(primaryStage, authService);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(700);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        nav.navigateTo(View.LOGIN);
    }
}
