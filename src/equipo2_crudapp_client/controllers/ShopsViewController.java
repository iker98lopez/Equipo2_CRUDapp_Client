/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the shops list view
 * @author Diego Corral
 */
public class ShopsViewController{
    
    /**
     * Logger for ShopsViewController class
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ShopsViewController");
    
    /**
     * Stage of the controller
     */
    private Stage stage;
    
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    /**
     * Closes the application
     */
    @FXML
    private Button buttonClose;
    
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {
        scene = new Scene(root);
        scene.getStylesheets().add("/equipo2_crudapp_client/views/table.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Shops");
        stage.show();
        
        buttonClose.setOnAction(this::handleButtonCloseAction);
    }
    
    /**
     * Handles the action of the close button. Closes the application
     * @param event the action event
     */
    public void handleButtonCloseAction(ActionEvent event){
        stage.hide();
    }
    
    /**
     * This method sets the stage
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
