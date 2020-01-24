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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Adrián García
 */
public class SoftwareViewController {
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.SoftwareViewController");
    /**
     * Stage of the controller
     */
    private Stage stage;
    /**
     * Scene of the controller
     */
    private Scene scene;
    /**
     * The image of the Software
     */
    @FXML
    private ImageView imageView;
    /**
     * The title of the Sotware
     */
    @FXML
    private Label labelTitle;
    /**
     * The publisher of the Software
     */
    @FXML
    private Label labelPublisher;
    /**
     * The date of release of the Software
     */
    @FXML
    private Label labelDate;
    /**
     * The desceription of the Software
     */
    @FXML
    private TextArea textAreaDescription;
    /**
     * ListView containing all the offers for the Software
     */
    @FXML
    private ListView listViewOffer;
    /**
     * ListView containing all the offers for the Software
     */
    @FXML
    private ListView listViewExtensions;
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {
        
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Software");
        
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
