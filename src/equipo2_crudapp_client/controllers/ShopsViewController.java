/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_client.clients.ShopClient;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

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
     * Client for the communication with the server
     */
    private static final ShopClient CLIENT = new ShopClient();
    
    /**
     * User logged into the application
     */
    private User user;
    
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
     * TableView of shops
     */
    @FXML
    private TableView tableViewShop;
    
    /**
     * Table column that shows the shop's name
     */
    @FXML
    private TableColumn tableColumnName;
    
    /**
     * Table column that shows the shop's image
     */
    @FXML
    private TableColumn tableColumnImage;
    
    /**
     * Table column that shows the shop's url
     */
    @FXML
    private TableColumn tableColumnUrl;
    
    /**
     * Warning. Shown when the filter is not valid
     */
    @FXML
    private Label labelFilterNotValid;
    
    /**
     * Makes the table editable
     */
    @FXML
    private ToggleButton toggleButtonEdit;
    
    /**
     * Adds a new shop
     */
    @FXML
    private Button buttonAdd;
    
    /**
     * Deletes a shop
     */
    @FXML
    private Button buttonDelete;
    
    /**
     * Set of shops
     */
    private Set<Shop> shops = new HashSet<>();
    
    /**
     * This method sets the stage
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * This method sets the user
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }
    
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
        
        buttonClose.setOnAction(this::handleButtonCloseAction);
        buttonAdd.setVisible(false);
        buttonDelete.setVisible(false);
        labelFilterNotValid.setVisible(false);
        
        stage.show();
        
        loadData();
        
        setTableData();
    }
    
    /**
     * Handles the action of the close button. Closes the application
     * @param event the action event
     */
    private void handleButtonCloseAction(ActionEvent event){
        stage.hide();
    }
    
    /**
     * Loads all the shops from the server
     */
    private void loadData() {
        try{
            shops = CLIENT.findAllShops(new GenericType<Set<Shop>>() {});
        } catch(Exception e) {
            LOGGER.warning(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't connect "
                    + "with the server...", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    /**
     * Method that populates tableView with shops
     */
    private void setTableData() {
        try{
            tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
            tableColumnUrl.setCellValueFactory(new PropertyValueFactory("url"));
            tableColumnImage.setCellValueFactory(new PropertyValueFactory("image"));

            ObservableList<Shop> observableShops = FXCollections.observableArrayList();
            observableShops.addAll(shops);
            tableViewShop.setItems(observableShops);
        }catch(Exception e) {
            LOGGER.severe("Error loading a table " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't load the "
                    + "table data", ButtonType.OK);
            alert.showAndWait();
        }
        

    }
}
