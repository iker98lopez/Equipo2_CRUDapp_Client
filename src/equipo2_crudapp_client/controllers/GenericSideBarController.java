/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.User;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for the side bar of the application
 * @author Diego Corral
 */
public class GenericSideBarController {
    
    /**
     * Logger for GenericSideBarController class
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.SideBarViewController");
    
    /**
     * The user logged in the application
     */
    protected User user;
    
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
     * Shows the application name. If clicked sends the user to the main view
     */
    @FXML
    protected Label labelOfertAPPs;
    
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
    public void setUser(User user){
        this.user = user;
    }
    
    /**
     * Method that initializes the GenericSideBarController stage and shows its 
     * view.
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root){
        stage = new Stage();
        
        buttonMyOffers.setOnAction(this::handleButtonMyOffersAction);
        buttonNewOffer.setOnAction(this::handleButtonNewOfferAction);
        buttonNewSoftware.setOnAction(this::handleButtonNewSoftwareAction);
        buttonViewShops.setOnAction(this::handleButtonViewShopsAction);
        buttonViewWishlist.setOnAction(this::handleButtonViewWishlistAction);
        buttonViewUser.setOnAction(this::handleButtonViewUserAction);
        buttonLogOut.setOnAction(this::handleButtonLogOutAction);
        labelOfertAPPs.setOnMouseClicked(this::handleLabelOfertAPPsMouseClick);
        stage.setOnShowing(this::windowShowing);
    }
    
    /**
     * Initializes some elements of the view
     * @param event The window event
     */
    public void windowShowing(WindowEvent event){
        
       // labelUsername.setText(user.getLogin());
    }
    
    /**
     * Handles the action event of buttonMyOffers
     * @param event The action event
     */
    public void handleButtonMyOffersAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/MyOffersView.fxml"));
            Parent root = (Parent) loader.load();
            MyOffersViewController controller = ((MyOffersViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonNewOffer
     * @param event The action event
     */
    public void handleButtonNewOfferAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/InsertOfferView.fxml"));
            Parent root = (Parent) loader.load();
            InsertOfferViewController controller = ((InsertOfferViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonNewSoftware
     * @param event The action event
     */
    public void handleButtonNewSoftwareAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/InsertSoftwareView.fxml"));
            Parent root = (Parent) loader.load();
            InsertSoftwareViewController controller = ((InsertSoftwareViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonViewShops
     * @param event The action event
     */
    public void handleButtonViewShopsAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/ShopsView.fxml"));
            Parent root = (Parent) loader.load();
            ShopsViewController controller = ((ShopsViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonViewWishlist
     * @param event The action event
     */
    public void handleButtonViewWishlistAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/WishListView.fxml"));
            Parent root = (Parent) loader.load();
            WishListViewController controller = ((WishListViewController) loader.getController());
            controller.setStage(new Stage());
            /**
             * Modificacion Adrian Garcia 06/02/2020
             */
            controller.setUser(user);
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonViewUser
     * @param event The action event
     */
    public void handleButtonViewUserAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/UserView.fxml"));
            Parent root = (Parent) loader.load();
            UserViewController controller = ((UserViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the mouse clicking event of labelOfertAPPs
     * @param event 
     */
    public void handleLabelOfertAPPsMouseClick(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/MainView.fxml"));
            Parent root = (Parent) loader.load();
            MainViewController controller = ((MainViewController) loader.getController());
            //controller.setUser(user);
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Handles the action event of buttonLogOut
     * @param event The action event
     */
    public void handleButtonLogOutAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            SignInViewController controller = ((SignInViewController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
}
