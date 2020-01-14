/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the user view
 * @author Diego Corral
 */
public class UserViewController extends GenericSideBarController{
    
    /**
     * Logger for UserViewController class
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.UserViewController");
    
    private boolean checkedSyntax;
    
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    @Override
    public void initStage(Parent root) {

        super.initStage(root);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User");
        stage.show();

    }
    
    /**
     * This method checks the syntax of the field received.
     */
    private void syntaxCheck() {

        
    }
    
    /**
     * This method checks if there's any empty field before sending SingUp
     */
    private void emptyFieldsCheck() {
        final String EMPTY_WARNING = "*This field is empty";

    }
    
    /**
     * This method sets the stage
     *
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
