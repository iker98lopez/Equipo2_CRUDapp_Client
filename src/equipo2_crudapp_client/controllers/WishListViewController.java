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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
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
     * List of wishes that will be deleted
     */
    private Map<Wish, BooleanProperty> checkedRows;
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
    private TableColumn<Wish, Double> tableColumnMinPrice;
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
     * Button that deletes selected wishes
     */
    @FXML
    private Button buttonDelete;

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

        buttonDelete.setDisable(true);

        buttonDelete.setOnAction(this::handleButtonDeleteAction);
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
     * Method that deletes selected wishes from wishList
     */
    public void handleButtonDeleteAction(ActionEvent event) {
        List<Wish> checkedItems = checkedRows.entrySet().stream()
                .filter(e -> e.getValue().get())
                .map(Entry::getKey)
                .collect(Collectors.toList());
        
        for (int i = 0; i < checkedItems.size(); i++) {
            if (wishes.contains(checkedItems.get(i))) {
                try {
                    CLIENT.removeWish("" + checkedItems.get(i).getId());
                } catch (ClientErrorException clientErrorException) {
                    clientErrorException.printStackTrace();
                }
            }
        }
        setTableData();
    }

    /**
     * Method that allows to edit the table
     */
    public void handleCheckBoxEditAction(ActionEvent event) {
        if (checkBoxEdit.isSelected()) {
            tableViewWishList.setEditable(true);
            buttonDelete.setDisable(false);

        } else {
            tableViewWishList.setEditable(false);
            buttonDelete.setDisable(true);

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
        /*try {
        wishes = user.getWishList();
        } catch (Exception e) {
        e.printStackTrace();
        }*/
        checkedRows = new HashMap<>();
        
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
        tableColumnDelete.setCellFactory(CheckBoxTableCell.forTableColumn(
            //This lambda ables to know wich element is selected to delete
                i-> checkedRows.computeIfAbsent(((Wish) tableViewWishList.getItems()
                        .get(i)), p -> new SimpleBooleanProperty())));
                

        /* Possible method to access a row item when table checkbox is selected
        
        ((CheckBoxTableCell) tableColumnDelete.getCellFactory()).setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer index) {
                newWishes.add((Wish)(tableViewWishList.getItems().get(index)));
                return ?;
            }
        });
         */
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
        tableColumnMinPrice.setCellFactory(TextFieldTableCell.<Wish, Double>forTableColumn(new DoubleStringConverter()));
        tableColumnMinPrice.setCellValueFactory(new PropertyValueFactory("minPrice"));

        ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
        observableWishes.addAll(wishes);
        tableViewWishList.setItems(observableWishes);

        //Save minimum price changes
        tableColumnMinPrice.setOnEditCommit(
                new EventHandler<CellEditEvent<Wish, Double>>() {
            @Override
            public void handle(CellEditEvent<Wish, Double> t) {
                Wish wish = ((Wish) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                Double newPrice = t.getNewValue();
                wish.setMinPrice(newPrice);
                CLIENT.modifyWish(wish, "" + wish.getId());
            }
        }
        );

    }

    /**
     * Method to set the active user
     */
    public void setUser() {

    }
}
