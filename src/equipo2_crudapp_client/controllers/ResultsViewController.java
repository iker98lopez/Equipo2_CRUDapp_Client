/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Adrián García
 */
public class ResultsViewController extends GenericSideBarController{
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ResultsViewController");
    /**
     * Scene of the controller
     */
    private Scene scene;
   
    /**
     * TextField that gets a text introduced by user to search
     */
    @FXML
    private TextField textFieldSearchBar;
    
    /**
     * Button that launches the search 
     */ 
    @FXML
    private Button buttonSearch;
    /**
     * RadioButton that filters by name of Software
     */
    @FXML
    private RadioButton radioButtonName;
    /**
     * RadioButton that filters by publisher of Software
     */
    @FXML
    private RadioButton radioButtonPublisher;
    /**
     * RadioButton that filters by number of offers of Software
     */
    @FXML
    private RadioButton radioButtonNumOfOffers;
    /**
     * RadioButton that filters by release date of Software
     */
    @FXML
    private RadioButton radioButtonReleaseDate;
    /**
     * CheckBox that filters by programs
     */
    @FXML
    private CheckBox checkBoxProgram;
    /**
     * CheckBox that filters by extensions
     */
    @FXML
    private CheckBox checkBoxExtension;
    /**
     * CheckBox that filters by games
     */
    @FXML
    private CheckBox checkBoxGame;
    /**
     * RadioButton that filters by ascending order
     */
    @FXML
    private RadioButton radioButtonAscending;
    /**
     * RadioButton that filters by descending order
     */
    @FXML
    private RadioButton radioButtonDescending;
    /**
     * ListView that shows all matched results
     */
    @FXML
    private ListView listViewResults;
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
        stage.setTitle("Results");
        stage.show();
        
    }
    
    
    
    
    
}
