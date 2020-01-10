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
import javafx.stage.Stage;

/**
 *
 * @author Diego Corral
 */
public class MainViewController extends SideBarViewController{
    
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.MainViewController");
    
    //private Stage stage;
    
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Button buttonSearch; 
    @FXML
    private ListView listViewGiveaways;
    @FXML
    private ListView listViewMostDiscounted;
    @FXML
    private ListView listViewExpiringSoon;
    
    public MainViewController(){
        super();
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
    
    /**
     * This method sets the stage
     *
     * @param stage Stage to be set
     *
    public void setStage(Stage stage) {
        this.stage = stage;
    }*/
    
    public void handleButtonSearchAction(ActionEvent event){
        
        
        
    }
    
    public void handleButtonLogOutAction(){
        
    }
    
    private boolean syntaxCheck(String text){
        
        boolean isValid = false;
        
        if(text.equals("")){
            
        }
        
        return isValid;
    }
    
}
