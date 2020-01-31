/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.enumerators.SoftwareType;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Adrián García
 */
public class ResultsViewController extends GenericSideBarController implements Initializable{
    private ObservableList<Software> results = null;
    private static final SoftwareClient SOFTWARE_CLIENT = new SoftwareClient();
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ResultsViewController");
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    /**
     * Text to search passed from MainView
     */
    private String searchText;
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
        
        buttonSearch.setOnAction(this::handleButtonSearchAction);
    }
    /**
     * Method that returns an observable list with filtered results
     */
    private ObservableList<Software> getResultsData () {
        
        try {
            if (textFieldSearchBar.getText() != null || textFieldSearchBar.getText() != "") {
                results = SOFTWARE_CLIENT.findSoftwaresByName(new GenericType<ObservableList<Software>>() {
                }, searchText);
                
              
            }
        } catch (ClientErrorException clientErrorException) {
           
        }
       return results;
    }
    /**
     * Method that populates the results ListView
     */
    private void printResultsOnList() {
        ObservableList<Software> data = FXCollections.observableArrayList();
        List<Offer> offers = new ArrayList<>();
        Software sp = new Software();
        data.add(new Software(1, "s1", "p", "d", Date.from(Instant.EPOCH), SoftwareType.GAME,offers, sp));
        data.add(new Software(1, "s2", "p", "d", Date.from(Instant.EPOCH), SoftwareType.GAME,offers, sp));
        data.add(new Software(1, "s3", "p", "d", Date.from(Instant.EPOCH), SoftwareType.GAME,offers, sp));
        listViewResults.setItems(data);
        listViewResults.setCellFactory(new Callback<ListView<Software>, ListCell<Software>>() {
            @Override
            public ListCell<Software> call(ListView<Software> listView) {
                return new ListViewResultsController();
            }
        });
    }
    /**
     * Method that searches Software based on filters applied
     */
    public void handleButtonSearchAction(ActionEvent event) {
    
    }
    
    /**
     * Method that sets searchText from MainView
     * @param searchText 
     */
    public void setSearchText(String searchText) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        printResultsOnList();
    }
    
}
