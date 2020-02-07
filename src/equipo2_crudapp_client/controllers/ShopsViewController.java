/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.classes.Wish;
import equipo2_crudapp_client.clients.ShopClient;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
     * List of wishes that will be deleted
     */
    private Map<Wish, BooleanProperty> checkedRows;
    
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
     * Filters the shops
     */
    @FXML
    private Button buttonFilter;
    
    /**
     * TextField for filtering by shop name
     */
    @FXML
    private TextField textFieldShopName;
    
    /**
     * Set of shops
     */
    private Set<Shop> shops = new HashSet<>();
    
    /**
     * Controls if the edit button is checked or not
     */
    private boolean toggleButtonEditIsPressed = false;
    
    public void setUser(User user) {
        this.user = user;
    }
    
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
        
        buttonClose.setOnAction(this::handleButtonCloseAction);
        toggleButtonEdit.setOnAction(this::handleToggleButtonEditAction);
        buttonAdd.setOnAction(this::handleButtonAddAction);
        buttonDelete.setOnAction(this::handleButtonDeleteAction);
        buttonFilter.setOnAction(this::handleButtonFilterAction);
        
        labelFilterNotValid.setVisible(false);
        
        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnName.setOnEditCommit(
            new EventHandler<CellEditEvent<Shop, String>>() {
                @Override
                public void handle(CellEditEvent<Shop, String> t) {
                    Shop modifiedShop = ((Shop) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                    modifiedShop.setName(t.getNewValue());
                    try{
                        CLIENT.modifyShop(modifiedShop, modifiedShop.getShopId().toString());
                    } catch(Exception e) {
                        LOGGER.warning(e.getMessage());
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't connect "
                                + "with the server...", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
        );
        
        tableColumnUrl.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnUrl.setOnEditCommit(
            new EventHandler<CellEditEvent<Shop, String>>() {
                @Override
                public void handle(CellEditEvent<Shop, String> t) {
                    Shop modifiedShop = ((Shop) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                    modifiedShop.setUrl(t.getNewValue());
                    try{
                        CLIENT.modifyShop(modifiedShop, modifiedShop.getShopId().toString());
                    } catch(Exception e) {
                        LOGGER.warning(e.getMessage());
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Couldn't connect "
                                + "with the server...", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
        );
        
        stage.show();
        
        loadData();
        
        setTableData(shops);
    }
    
    /**
     * Handles the action of the close button. Closes the application
     * @param event the action event
     */
    private void handleButtonCloseAction(ActionEvent event){
        stage.hide();
    }
    
    /**
     * Handles the action of the filter button. Filters the shops
     * @param event the action event
     */
    private void handleButtonFilterAction(ActionEvent event) {
        Set<Shop> filteredShops = new HashSet<>();
        
        if(textFieldShopName.getText().length() < 18) {
            filteredShops = shops.stream()
                                .filter(shop -> 
                                        shop.getName().contains(textFieldShopName.getText().toLowerCase()) ||
                                        shop.getName().contains(textFieldShopName.getText().toUpperCase()) ||
                                        shop.getName().contains(textFieldShopName.getText()))
                    .collect(Collectors.toSet());
            setTableData(filteredShops);
        }
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
            LOGGER.warning(e.getMessage());
        }
    }
    
    /**
     * Allows the user to edit the table
     * @param event the action event
     */
    private void handleToggleButtonEditAction(ActionEvent event){
        if(!toggleButtonEditIsPressed){
            tableViewShop.setEditable(true);
            toggleButtonEditIsPressed = true;
        } else{
            tableViewShop.setEditable(false);
            
            toggleButtonEditIsPressed = false;
        }
    }
    
    /**
     * Handles the action of adding a shop
     * @param event the action event
     */
    private void handleButtonAddAction(ActionEvent event) {
        String name = "";
        String url = "";
        
        TextInputDialog textInputDialogName = new TextInputDialog();
        textInputDialogName.setTitle("Add shop");
        textInputDialogName.setHeaderText(null);
        textInputDialogName.setContentText("Enter the shop's name:");
        textInputDialogName.initStyle(StageStyle.UTILITY);
        Optional<String> resultName = textInputDialogName.showAndWait();

        if (resultName.isPresent()) {
            name = textInputDialogName.getEditor().getText();
            TextInputDialog textInputDialogUrl = new TextInputDialog();
            textInputDialogUrl.setTitle("Add shop");
            textInputDialogUrl.setHeaderText(null);
            textInputDialogUrl.setContentText("Enter the shop's url:");
            Optional<String> resultUrl = textInputDialogUrl.showAndWait();

            if (resultUrl.isPresent()) {
                url = textInputDialogUrl.getEditor().getText();
                try {
                    CLIENT.createShop(new Shop(name, url));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Shop added correctly", ButtonType.OK);
                    alert.showAndWait();
                    loadData();
                    setTableData(shops);
                } catch(Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Error connecting with the server", ButtonType.OK);
                    alert.showAndWait();
                    LOGGER.warning(e.getMessage());
                }
            }
        }
    }
    
    /**
     * Deletes a selected shop
     * @param event the action event
     */
    private void handleButtonDeleteAction(ActionEvent event) {
        Shop selectedShop = (Shop) tableViewShop.getSelectionModel().getSelectedItem();
        CLIENT.removeShopById(selectedShop.getShopId().toString());
        shops.remove(selectedShop);
        setTableData(shops);
    }
    
    /**
     * Method that populates tableView with shops
     */
    private void setTableData(Set<Shop> shops) {
        try{
            tableColumnName.setCellValueFactory(new PropertyValueFactory("name"));
            tableColumnUrl.setCellValueFactory(new PropertyValueFactory("url"));
            //tableColumnImage.setCellValueFactory(new PropertyValueFactory("image"));

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
