/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client;

import equipo2_crudapp_client.controllers.SignInViewController;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * OfertAPPs application class. This application allows users to upload and view
 * different offers found by users on internet.
 *
 * @author Diego Corral
 *
 */
public class Application extends javafx.application.Application {

    /**
     * @param args args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry point for the application. Loads, sets and shows primary window.
     *
     * @param primaryStage The primary window of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/SignInView.fxml"));
        Parent root = (Parent) loader.load();
        SignInViewController controller = ((SignInViewController) loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
    }
}
