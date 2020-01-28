/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.classes.Wish;
import equipo2_crudapp_client.clients.WishClient;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
     * Set of wishes used to recieve the wishes
     */
    private Set<Wish> wishes = new HashSet<Wish>();
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
        checkBoxEdit.setOnAction(this::handleCheckBoxEditAction);

        setTableData();
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
     * Method that allows to edit the table
     */
    public void handleCheckBoxEditAction(ActionEvent event) {

    }

    /**
     * Method that filters by name in the wishlist
     *
     * @param event
     */
    public void handleButtonFilterAction(ActionEvent event) {

    }

    /**
     * Method that populates tableView with user wishes
     */
    public void setTableData() {
        /*
        // wishes = user.getWishList();          
        wishes.add(new Wish(3, new Software("s3"), 3.0));
        wishes.add(new Wish(2, new Software("s2"), 2.0));
        wishes.add(new Wish(1, new Software("s1"), 1.0));

        tableColumnSoftware.setCellValueFactory(new Callback<
        CellDataFeatures<Wish, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    CellDataFeatures<Wish, String> data) {
                return data.getValue().getSoftware().getNameProperty();
            }
        });
        tableColumnMinPrice.setCellValueFactory(new PropertyValueFactory("minPrice"));

        ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
        observableWishes.addAll(wishes);
        tableViewWishList.setItems(observableWishes);
         */
    }

    public void setUser() {

    }

}
