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
import equipo2_crudapp_client.table_classes.TableSoftware;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javax.ws.rs.ClientErrorException;
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
     * TableColumn that shows a control to erase software from wishlist
     */
    @FXML
    private TableColumn tableColumnDelete;
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
     * Button that cancels the changes to the table
     */
    @FXML 
    private Button buttonCancel;

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
        
        buttonCancel.setDisable(true);
        
        buttonCancel.setOnAction(this::handleButtonCancelAction);
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
     * Method that cancels table edit and discard the changes
     */
    public void handleButtonCancelAction (ActionEvent event) {
        checkBoxEdit.setSelected(false);
        handleCheckBoxEditAction(event);
       
    }
    /**
     * Method that allows to edit the table
     */
    public void handleCheckBoxEditAction(ActionEvent event) {
        if (checkBoxEdit.isSelected()) {
            tableViewWishList.setEditable(true);
            buttonCancel.setDisable(false);
            
        } else {
            tableViewWishList.setEditable(false);
            buttonCancel.setDisable(true);
            
        }
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

       /* try {
            wishes = user.getWishList();            
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       Wish w1 = new Wish();
       Wish w2 = new Wish();
       Software s1 = new Software();
       Software s2 = new Software();
       s1.setName("s1");
       s2.setName("s2");
       w1.setSoftware(s1);
       w1.setMinPrice(1.0);
       w2.setMinPrice(2.0);
       w2.setSoftware(s2);
       wishes.add(w1);
       wishes.add(w2);
        //Column checkbox
        tableColumnDelete.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumnDelete) );

        //Column software name
        tableColumnSoftware.setCellValueFactory(new Callback<
        CellDataFeatures<Wish, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    CellDataFeatures<Wish, String> data) {
                TableSoftware software = new TableSoftware(data.getValue().getSoftware().getName());
                return software.getNameProperty();
            }
        });
        //Column minimum price
        tableColumnMinPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); 
        tableColumnMinPrice.setCellValueFactory(new PropertyValueFactory("minPrice"));
        
        ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
        observableWishes.addAll(wishes);
        tableViewWishList.setItems(observableWishes);

    }

    /**
     * Method to set the active user
     */
    public void setUser() {

    }
}
