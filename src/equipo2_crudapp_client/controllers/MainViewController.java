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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for the main view of the application
 * @author Diego Corral
 */
public class MainViewController extends SideBarViewController{
    
    /**
     * Logger of MainViewController
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.MainViewController");
    
    /**
     * 
     */
    @FXML
    private TextField textFieldSearch;
    
    /**
     * 
     */
    @FXML
    private Button buttonSearch; 
    
    /**
     * Free offers
     */
    @FXML
    private ListView listViewGiveaways;
    
    /**
     * The most discounted offers
     */
    @FXML
    private ListView listViewMostDiscounted;
    
    /**
     * Offers that expire soon
     */
    @FXML
    private ListView listViewExpiringSoon;
    
    /**
     * Empty constructor for the class MainViewController
     */
    public MainViewController(){
        
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
        
        buttonSearch.setOnAction(this::handleButtonSearchAction);
        
    }
    
    public void handleButtonSearchAction(ActionEvent event){
        
        
    }
    
    private boolean syntaxCheck(String text){
        
        boolean isValid = false;
        
        if(text.equals("")){
            
        }
        
        return isValid;
    }
    
}
