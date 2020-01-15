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
    
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    /**
     * This method sets the stage
     *
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
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
     * Checks the syntax of the user's data fields.
     * @return true if corret, otherwise false.
     */
    private boolean syntaxCheckUserData() {
        boolean ret = false;
        
        return ret;
    }
    
    /**
     * Checks the syntax of the forgot password fields.
     * @return true if correct, otherwise false.
     */
    private boolean syntaxCheckForgotPassword(){
        boolean ret = false;
        
        return ret;
    }
    
    /**
     * Checks if there's any empty field on the user's data fields.
     * @return true if correct, otherwise false.
     */
    private boolean emptyFieldsCheckUserData() {
        boolean ret = false;
        final String EMPTY_WARNING = "*This field is empty";
        
        return ret;
    }
    
    /**
     * Checks if there's any empty field on the forgot password fields.
     * @return true if correct, otherwise false.
     */
    private boolean emptyFieldsCheckForgotPassword(){
        boolean ret = false;
        
        return ret;
    }
    
}
