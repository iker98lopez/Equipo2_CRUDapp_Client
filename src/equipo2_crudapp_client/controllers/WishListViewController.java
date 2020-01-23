/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.classes.Wish;
import equipo2_crudapp_client.clients.WishClient;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Adrián García
 */
public class WishListViewController extends GenericSideBarController {
    
    /**
     * Client to make petitions
     */
    private static final WishClient CLIENT = new WishClient();
    
    /**
     * Set of wished used to recieve the wishes 
     */
    private Set<Wish> wishes;
    /**
     * The user that is logged
     */
    private User user;
    
    /**
     * Logger to output messages to the console
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.WishListViewController");
   
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    /**
     * Table that contains wishes
     */
    @FXML
    private TableView tableViewWishList;
    /**
     * TableColumn that shows softwares
     */
    @FXML
    private TableColumn tableColumnSoftware;
    /**
     * TableColumn that shows minimun prices to be notified
     */
    @FXML
    private TableColumn tableColumnMinPrice;
    /**
     * TextField that recieves a text for search
     */
    @FXML 
    private TextField textFieldFilter;
    /**
     * CheckBox that makes the WishList table editable
     */
    @FXML
    private CheckBox checkBoxEdit;
    /**
     * Button that starts searching for wishes by name
     */
    @FXML
    private Button buttonFilter;
    
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
        stage.setTitle("WishList");
        stage.show();
        
        buttonFilter.setOnAction(this::handleButtonFilterAction);     
    }
    
    /**
     * This method sets the stage
     *
     * @param stage Stage to be setted
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Method that filters by name in the wishlist
     * @param event
     */
    public void handleButtonFilterAction (ActionEvent event) {
        
    }
    
    public void setTableData() {
        
        // wishes = user.getWishList();
        Wish w = new Wish();
        Software s = new Software();
        s.setName("s1");
        w.setSoftware(s);
        w.setMinPrice(1.0);
        
        Wish w2 = new Wish();
        Software s2 = new Software();
        s.setName("s2");
        w.setSoftware(s2);
        w.setMinPrice(2.0);
        
        Wish w3 = new Wish();
        Software s3 = new Software();
        s.setName("s3");
        w.setSoftware(s3);
        w.setMinPrice(3.0);
        
        wishes.add(w);
        wishes.add(w2);
        wishes.add(w3);
        
        tableColumnSoftware.setCellValueFactory(new PropertyValueFactory("software.name"));
        tableColumnMinPrice.setCellFactory(new PropertyValueFactory("minPrice"));
        
        ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
        observableWishes.addAll(wishes);
        tableViewWishList.setItems(observableWishes);
        
        
    }
    
    public void setUser() {
        
    }
}
