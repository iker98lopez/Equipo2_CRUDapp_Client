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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the controller for the fxml view MyOffersView.
 * 
 * @author Iker Lopez
 */
public class MyOffersViewController {
    
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.MyOffersViewController");
    
    private Stage stage;
    
    /**
     * Table containing all the offers.
     */
    @FXML
    private TableView tableOffers;
    
    /**
     * Table column for the attribute Software.
     */
    @FXML
    private TableColumn tableColumnSoftware;
    
    /**
     * Table column for the attribute BasePrice.
     */
    @FXML
    private TableColumn tableColumnBasePrice;
    
    /**
     * Table column for the attribute DiscountedPrice.
     */
    @FXML
    private TableColumn tableColumnDiscountedPrice;
    
    /**
     * Table column for the attribute Discount.
     */
    @FXML
    private TableColumn tableColumnDiscount;
    
    /**
     * Table column for the attribute Shop.
     */
    @FXML
    private TableColumn tableColumnShop;
    
    /**
     * Table column for the attribute Url.
     */
    @FXML
    private TableColumn tableColumnUrl;
    
    /**
     * TextField for introducing a query to search by the name of the software
     * being offered.
     */
    @FXML
    private TextField textFieldFilterByName;
    
    /**
     * TextField for introducing a query to search by the name of the shop
     * making the offer.
     */
    @FXML
    private TextField textFieldFilterByShop;
    
    /**
     * TextField to establish a minimum percentage of discount for an offer to
     * be displayed.
     */
    @FXML
    private TextField textFieldMinimumDiscount;
    
    /**
     * Button to apply the filters from the textfields into the table.
     */
    @FXML
    private Button buttonApplyFilter;
    
    /**
     * Button to close the window.
     */
    @FXML
    private Button buttonClose;
        
    /**
     * This method initialises the stage, shows the window, sets the components
     * and assigns the listeners.
     * 
     * @param root Parent to set the scene.
     */
    public void initStage (Parent root) {
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("table.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("My Offers");
        stage.show();
        
        buttonClose.setOnAction(this::handleButtonCloseAction);
    }
    
    /**
     * This method handles the action of the Button buttonClose.
     * 
     * @param event Event triggered.
     */
    public void handleButtonCloseAction(ActionEvent event){
        stage.hide();
    }
    
    /**
     * This method sets the stage.
     * 
     * @param stage Stage to be set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
