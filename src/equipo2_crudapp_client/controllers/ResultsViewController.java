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
public class ResultsViewController {
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ResultsViewController");
    
    private Stage stage;
    private Scene scene;
    
    @FXML
    private TextField textFieldSearchBar;
    @FXML
    private Button buttonSearch;
    @FXML
    private RadioButton radioButtonName;
    @FXML
    private RadioButton radioButtonPublisher;
    @FXML
    private RadioButton radioButtonNumOfOffers;
    @FXML
    private RadioButton radioButtonReleaseDate;
    @FXML
    private CheckBox checkBoxProgram;
    @FXML
    private CheckBox checkBoxExtension;
    @FXML
    private CheckBox checkBoxGame;
    @FXML
    private RadioButton radioButtonAscending;
    @FXML
    private RadioButton radioButtonDescending;
    @FXML
    private ListView listViewResults;
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Results");
        
        stage.show();
        
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
