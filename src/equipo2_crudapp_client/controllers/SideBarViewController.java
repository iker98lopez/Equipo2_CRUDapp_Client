/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for the side bar of the application
 * @author Diego Corral
 */
public class SideBarViewController {
    
    /**
     * The user logged in the application
     */
    protected String user;
    
    /**
     * The stage of the controller
     */
    protected Stage stage;
    
    /**
     * Sends the user to a view with the offers published by the current user
     */
    @FXML
    protected Button buttonMyOffers;
    
    /**
     * Sends the user to a window where he can publish new offers
     */
    @FXML
    protected Button buttonNewOffer;
    
    /**
     * Sends them to a window where they can add new softwares fot the 
     * application. Accesible only for admins
     */
    @FXML
    protected Button buttonNewSoftware;
    
    /**
     * Sends the user to a list with the available shops
     */
    @FXML
    protected Button buttonViewShops;
    
    /**
     * Sends the user to his wishlist
     */
    @FXML
    protected Button buttonViewWishlist;
    
    /**
     * Sends the user to the user view 
     */
    @FXML
    protected Button buttonViewUser;  
    
    /**
     * Shows the username of the current user
     */
    @FXML
    protected Label labelUsername;
    
    /**
     * Logs out of the application
     */
    @FXML
    protected Button buttonLogOut;
    
    /**
     * Method that initializes the SideBarViewController stage and shows its 
     * view.
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root){
        buttonMyOffers.setOnAction(this::handleButtonMyOffersAction);
        buttonNewOffer.setOnAction(this::handleButtonNewOfferAction);
        buttonNewSoftware.setOnAction(this::handleButtonNewSoftwareAction);
        buttonViewShops.setOnAction(this::handleButtonViewShopsAction);
        buttonViewWishlist.setOnAction(this::handleButtonViewWishlistAction);
        buttonViewUser.setOnAction(this::handleButtonViewUserAction);
        buttonLogOut.setOnAction(this::handleButtonLogOutAction);
        stage.setOnShowing(this::windowShowing);
    }
    
    /**
     * Initializes some elements of the view
     * @param event The window event
     */
    public void windowShowing(WindowEvent event){
        labelUsername.setText(user);
    }
    
    /**
     * Handles the action event of buttonMyOffers
     * @param event The action event
     */
    public void handleButtonMyOffersAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonNewOffer
     * @param event The action event
     */
    public void handleButtonNewOfferAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonNewSoftware
     * @param event The action event
     */
    public void handleButtonNewSoftwareAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonViewShops
     * @param event The action event
     */
    public void handleButtonViewShopsAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonViewWishlist
     * @param event The action event
     */
    public void handleButtonViewWishlistAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonViewUser
     * @param event The action event
     */
    public void handleButtonViewUserAction(ActionEvent event){
        
    }
    
    /**
     * Handles the action event of buttonLogOut
     * @param event The action event
     */
    public void handleButtonLogOutAction(ActionEvent event){
        
    }
    
    /**
     * Sets the stage
     * @param stage The stage to set
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    /**
     * Sets the user logged in the application
     * @param user The user logged in
     */
    public void setUser(String user){
        this.user = user;
    }
    
    
}
