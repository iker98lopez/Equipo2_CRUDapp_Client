
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_client.clients.OfferClient;
import equipo2_crudapp_client.clients.ShopClient;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * Controller for the main view of the application
 * @author Diego Corral
 */
public class MainViewController extends GenericSideBarController {
    
    /**
     * Logger of MainViewController
     */
    private static final Logger LOGGER = Logger.
            getLogger("equipo2_crudapp_client.controllers.MainViewController");
    
    /**
     * Client for the communication with the server
     */
    private static final OfferClient CLIENT = new OfferClient();
    
    /**
     * Contains all the offers
     */
    private Set<Offer> offers = new HashSet<>();
    
    /**
     * Search text for filtering the offers
     */
    @FXML
    private TextField textFieldSearch;
    
    /**
     * Opens the results view. Filters the results using the values introduced 
     * by the user
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
        loadData();
        
        buttonSearch.setOnAction(this::handleButtonSearchAction);
        
    }
    
    /**
     * Handles the action of the search button. Opens the results view for 
     * filtering the offers
     * @param event 
     */
    public void handleButtonSearchAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/ResultsView.fxml"));
            Parent root = (Parent) loader.load();
            ResultsViewController controller = ((ResultsViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        
    }
    
    /**
     * Loads all the offers from the server
     */
    private void loadData() {
        offers = CLIENT.findAllOffers(new GenericType<Set<Offer>>() {});
    }
    
    /**
     * Load in a list all the free offers
     */
    private void setFreeOffers() {
        ObservableList<String> items = FXCollections.observableArrayList (
            "A", "B", "C", "D");
        listViewGiveaways.setItems(items);
    }
    
    /**
     * Load in a list the most discounted offers
     */
    private void setMostDiscountedOffers() {
        
    }
    
    /**
     * Load in a list the offers that expire soon
     */
    private void setExpiringSoonOffers() {
        
    }
    
    /**
     * Validates that the text introduced in the search text field are correct
     * @param text Text to validate
     * @return True if valid, otherwise false
     */
    private boolean syntaxCheck(String text){
        
        boolean ret = false;
        
        if(text.equals("") && text.length() > 3 && text.length() < 128){
            ret = true;
        }
        
        return ret;
    }
    
}
