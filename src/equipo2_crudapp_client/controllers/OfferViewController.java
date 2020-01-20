/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_client.clients.OfferClient;
import equipo2_crudapp_client.clients.ShopClient;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This is the controller for the fxml view OfferView
 * 
 * @author Iker Lopez
 */
public class OfferViewController {
    
    /**
     * Logger to show error messages and exceptions.
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.InsertOfferView");

    /**
     * Stage of the controller InsertSoftwareViewController
     */
    private Stage stage;

    /**
     * Instance of the client manager for the entity Offer.
     */
    private final OfferClient OFFERCLIENT = new OfferClient();
    
    /**
     * Instance of the client manager for the entity Software.
     */
    private final SoftwareClient SOFTWARECLIENT = new SoftwareClient();

    /**
     * Set of type software to contain every software received from the server.
     */
    private Set<Software> softwares = new HashSet<Software>();

    /**
     * Instance of the client manager for the entity Shop.
     */
    private final ShopClient SHOPCLIENT = new ShopClient();

    /**
     * Set of type shop to contain every shop received from the server.
     */
    private Set<Shop> shops = new HashSet<Shop>();
    
    /**
     * Image of the software.
     */
    @FXML
    private ImageView imageViewSoftwareImage;
    
    /**
     * Text field to show and edit the name of the software being offered.
     */
    @FXML
    private TextField textFieldSoftwareName;
    
    
    @FXML
    private TextField textFieldDiscountedPrice;
    
    
    @FXML
    private TextField textFieldBasePrice;
    
    
    @FXML
    private TextField textFieldDiscount;
    
    
    @FXML
    private ToggleButton toggleButtonEdit;
    
    
    @FXML
    private TextField textFieldUser;
    
    
    @FXML
    private Button buttonDelete;
    
    
    @FXML
    private TextField textFieldUrl;
    
    
    @FXML
    private Button buttonReport;
    
    
    @FXML
    private ListView listViewComments;
    
    
    @FXML
    private Button buttonComment;
}
