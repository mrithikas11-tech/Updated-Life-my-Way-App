package com.canigetafiver.lifemyway.web.nav;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import com.canigetafiver.lifemyway.web.auth.AuthenticationService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * single point of control for all view nav
 */
public final class NavigationController {
    private static final String STYLESHEET_PATH="/com/canigetafiver/lifemyway/web/styles.css";
    private static final NavigationController INSTANCE= new NavigationController(); //singleton initialization
    private Stage primaryStage;
    private AuthenticationService authService;
    private String pendingFlashMessage;
    private NavigationController(){} //prevents any external instantiation

    public static NavigationController getInstance(){
        return INSTANCE;
    }

    /**
     * First wiring from App.start(); subsequent calls overwrite
     */
    public void init(Stage stage, AuthenticationService authService){
        this.primaryStage=Objects.requireNonNull(stage,"Stage cannot be null");
        this.authService=Objects.requireNonNull(authService,"authService cannot be null");
    }

    public AuthenticationService authService(){
        ensureInitialized();
        return authService;
    }

    /**
     * used by RegisterController to display status msg
     */
    public void setFlashMessage(String message){this.pendingFlashMessage=message;}
    public String consumeFlashMessage(){
        String m =pendingFlashMessage;
        pendingFlashMessage=null;
        return m;
    }

    public void navigateTo(View view){
        ensureInitialized();
        Objects.requireNonNull(view,"view cannot be null");
        
        URL fxmlUrl=getClass().getResource(view.fxmlPath());
        if(fxmlUrl==null){
            showMissingViewAlert(view);
            return;
        }try {
            FXMLLoader loader=new FXMLLoader(fxmlUrl);
            Parent root=loader.load();
            Scene scene=new Scene(root);
            attachStylesheet(scene);
            primaryStage.setTitle(view.fxmlPath());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException e){
            showLoadFailureAlert(view,e);
        }
    }
    private void attachStylesheet(Scene scene){
        URL css=getClass().getResource(STYLESHEET_PATH);
        if(css!=null){
            scene.getStylesheets().add(css.toExternalForm());
        }
    }

    private void ensureInitialized(){
        if(primaryStage==null||authService==null){
            throw new IllegalStateException("NavigationController.init(stage, authService) must be called first.");
        }
    }

    private void showMissingViewAlert(View view){
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("View not available");
        alert.setHeaderText(view.title()+" is not available.");
        alert.setContentText("FXML file missing on classpath:\n"+view.fxmlPath()+"\n\nThis screen depends on a teammate's deliverable.");
        alert.showAndWait();
    }

    private void showLoadFailureAlert(View view,Exception e){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to load view");
        alert.setHeaderText("Could not load " +view.title());
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

}
