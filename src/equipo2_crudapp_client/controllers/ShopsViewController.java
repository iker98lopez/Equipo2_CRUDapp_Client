/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.Wish;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private TableView tableViewShops;
    
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
     * Set of shops
     */
    private Set<Shop> shops = new HashSet<Shop>();
    
    /**
     * This method sets the stage
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
        stage.show();
        
        buttonClose.setOnAction(this::handleButtonCloseAction);
        
        setTableData();
    }
    
    /**
     * Handles the action of the close button. Closes the application
     * @param event the action event
     */
    public void handleButtonCloseAction(ActionEvent event){
        stage.hide();
    }
    
    /**
     * Method that populates tableView with shops
     */
    public void setTableData() {

        // wishes = user.getWishList();          
        /*shops.add(new Wish(3, new Software("s3"), 3.0));
        shops.add(new Wish(2, new Software("s2"), 2.0));
        shops.add(new Wish(1, new Software("s1"), 1.0));*/
        shops.add(new Shop(1, "Steam", "www.steam.com"));
        shops.add(new Shop(2, "Epic Games", "www.epicgames.com"));

        tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
        tableColumnUrl.setCellValueFactory(new PropertyValueFactory("url"));
        tableColumnImage.setCellValueFactory(new PropertyValueFactory("image"));

        ObservableList<Shop> observableShops = FXCollections.observableArrayList();
        observableShops.addAll(shops);
        tableViewShops.setItems(observableShops);

    }
}
