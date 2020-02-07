/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Wish;
import equipo2_crudapp_client.clients.WishClient;
import equipo2_crudapp_client.clients.WishClientFactory;
import equipo2_crudapp_client.clients.WishClientImplementation;
import equipo2_crudapp_client.table_classes.TableSoftware;
import java.util.ArrayList;
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
    private final WishClient CLIENT = WishClientFactory.getWishClient();

    /**
     * Set of wishes used to recieve the wishes
     */
    private Set<Wish> wishes = new HashSet<Wish>();
    /**
     * Modificacion Adrian garcia 06/02/2020
     */
    private Set<Wish> updatedWishes = new HashSet<Wish>();
    private ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
    private List<Wish> checkedItems = new ArrayList();
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
        
        try {
        wishes.addAll(user.getWishList());
        } catch (Exception e) {
        e.printStackTrace();
        }
    
        observableWishes.addAll(wishes);
        setTableData(observableWishes);
    }

    /**
     * Modificacion Adrian 06/02/2020
     */

    /**
     * Method that deletes selected wishes from wishList
     */
    public void handleButtonDeleteAction(ActionEvent event) {
        checkedItems = checkedRows.entrySet().stream()
                .filter(e -> e.getValue().get())
                .map(Entry::getKey)
                .collect(Collectors.toList());

        for (int i = 0; i < checkedItems.size(); i++) {
            if (wishes.contains(checkedItems.get(i))) {
                try {
                    wishes.remove(checkedItems.get(i));
                } catch (ClientErrorException clientErrorException) {
                    clientErrorException.printStackTrace();
                }
            }
        }
        ObservableList<Wish> observableWishes = FXCollections.observableArrayList();
        observableWishes.addAll(wishes);
        setTableData(observableWishes);
    }

    /**
     * Method that allows to edit the table
     */
    public void handleCheckBoxEditAction(ActionEvent event) {
        if (checkBoxEdit.isSelected()) {
            checkBoxEdit.setText("Save");
            tableViewWishList.setEditable(true);
            buttonDelete.setDisable(false);

        } else {
            checkBoxEdit.setText("Edit");
            tableViewWishList.setEditable(true);
            if(!updatedWishes.isEmpty()) {
                for(Wish wish: updatedWishes) {
                    try {
                    CLIENT.modifyWish(wish, ""+wish.getWishId());
                } catch (ClientErrorException clientErrorException) {
                    clientErrorException.printStackTrace();
                }
                }
            }
            if(!checkedItems.isEmpty()) {
                for(Wish wish: checkedItems)
                    CLIENT.removeWish(""+wish.getWishId());
                user.setWishList(wishes);
            }
            checkedItems.clear();
        }
    }

    /**
     * Method that filters by name in the wishlist
     *
     * @param event
     */
    public void handleButtonFilterAction(ActionEvent event) {
        if (textFieldFilter.getText() != null || textFieldFilter.getText() != "") {
            ObservableList<Wish> filteredWishes = FXCollections.observableArrayList();
            filteredWishes.addAll(wishes);
            //Filter results
            filteredWishes.removeIf(w -> !w.getSoftware().getName().toLowerCase().contains(textFieldFilter.getText().toLowerCase()));
            setTableData(filteredWishes);
        }
    }

    /**
     * Method that populates tableView with user wishes
     */
    private void setTableData(ObservableList<Wish> data) {
        checkedRows = new HashMap<>();
        updatedWishes.clear();
        
        //Column checkbox
        tableColumnDelete.setCellFactory(CheckBoxTableCell.forTableColumn(
                //This lambda ables to know wich element is selected to delete
                i -> checkedRows.computeIfAbsent(((Wish) tableViewWishList.getItems()
                        .get(i)), p -> new SimpleBooleanProperty())));

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

        tableViewWishList.setItems(data);

         /**
          * Modificacion Adrian Garcia 06/02/2020
          */
        //Save minimum price changes
        tableColumnMinPrice.setOnEditCommit(
                new EventHandler<CellEditEvent<Wish, Double>>() {
            @Override
            public void handle(CellEditEvent<Wish, Double> t) {
                Wish wish = observableWishes.get(t.getTablePosition().getRow());
               // Wish wish = ((Wish) t.getTableView().getItems().get(t.getTablePosition().getRow()));
               Double newPrice = t.getNewValue();
               wish.setMinPrice(newPrice);
               updatedWishes.add(wish);
            }
        }
        );

    }

    /**
     * Modificacion Adrian Garcia 06/02/2020
     */
}
