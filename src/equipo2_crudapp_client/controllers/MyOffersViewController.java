/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.classes.Wish;
import equipo2_crudapp_client.clients.SoftwareClient;
import equipo2_crudapp_client.table_classes.TableShop;
import equipo2_crudapp_client.table_classes.TableSoftware;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.GenericType;

/**
 * This is the controller for the fxml view MyOffersView.
 *
 * @author Iker Lopez
 */
public class MyOffersViewController {

    /**
     * Logger to show error messages and exceptions.
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.MyOffersViewController");

    /**
     * Stage of the controller MyOffersViewController
     */
    private Stage stage;

    /**
     * List of type Offer to contain every offer of the user.
     */
    private List<Offer> offers;

    /**
     * Instance of the client manager for the entity Software.
     */
    private final SoftwareClient SOFTWARECLIENT = new SoftwareClient();

    /**
     * Set of type software to contain every software received from the server.
     */
    private Set<Software> softwares = new HashSet<>();

    /**
     * Current user of the application.
     */
    private User user;

    /**
     * Table containing all the offers.
     */
    @FXML
    private TableView tableViewOffers;

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
     * Button to clear the filters applied to the table.
     */
    @FXML
    private Button buttonClearFilters;

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
    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/equipo2_crudapp_client/views/table.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("My Offers");
        stage.show();

        offers = user.getOffers();

        try {
            softwares = SOFTWARECLIENT.findAllSoftwares(new GenericType<Set<Software>>() {
            });
        } catch (NotFoundException exception) {
            LOGGER.warning("There are no softwares to be found. " + exception.getMessage());
        } catch (ServerErrorException exception) {
            LOGGER.warning("There was an error connecting to the server.\nPlease try again later. " + exception.getMessage());
        }

        buttonApplyFilter.setOnAction(this::handleButtonApplyFilterAction);
        buttonClearFilters.setOnAction(this::handleButtonClearFiltersAction);
        buttonClose.setOnAction(this::handleButtonCloseAction);

        if (offers != null) {
            ObservableList<Offer> observableOffers = FXCollections.observableArrayList();
            observableOffers.addAll(offers);
            setTableData(observableOffers);
        }
    }

    /**
     * This method handles the action of the Button buttonClose.
     *
     * @param event Event triggered.
     */
    public void handleButtonApplyFilterAction(ActionEvent event) {
        if (!textFieldFilterByName.getText().isEmpty()) {
            String nameFilter = textFieldFilterByName.getText();
            softwares = softwares.stream()
                    .filter(software -> software
                    .getName().toLowerCase()
                    .contains(nameFilter.toLowerCase()))
                    .collect(Collectors.toSet());

            offers = offers.stream()
                    .filter(offer -> offer.equals(softwares.stream()
                    .anyMatch(software -> software.getOffers() != null
                    && software.getOffers().contains(offer))))
                    .collect(Collectors.toList());
        }

        if (!textFieldFilterByShop.getText().isEmpty()) {
            String shopFilter = textFieldFilterByShop.getText();
            offers = offers.stream()
                    .filter(offer -> offer
                    .getShop().getName().toLowerCase()
                    .contains(shopFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (!textFieldMinimumDiscount.getText().isEmpty()) {
            String minimumDiscountFilter = textFieldMinimumDiscount.getText();
            offers = offers.stream()
                    .filter(offer -> offer
                    .getDiscount() >= Double.valueOf(minimumDiscountFilter))
                    .collect(Collectors.toList());
        }

        ObservableList<Offer> observableOffers = FXCollections.observableArrayList();
        observableOffers.addAll(offers);
        setTableData(observableOffers);
    }

    /**
     * This method handles the action of the Button buttonClearFilters.
     *
     * @param event Event triggered.
     */
    public void handleButtonClearFiltersAction(ActionEvent event) {
        offers = user.getOffers();

        try {
            softwares = SOFTWARECLIENT.findAllSoftwares(new GenericType<Set<Software>>() {
            });
        } catch (NotFoundException exception) {
            LOGGER.warning("There are no softwares to be found. " + exception.getMessage());
        } catch (ServerErrorException exception) {
            LOGGER.warning("There was an error connecting to the server.\nPlease try again later. " + exception.getMessage());
        }

        ObservableList<Offer> observableOffers = FXCollections.observableArrayList();
        observableOffers.addAll(offers);
        setTableData(observableOffers);
    }

    /**
     * Method that sets the data of the table using an ObservableList of offers.
     */
    private void setTableData(ObservableList<Offer> offers) {
        tableColumnSoftware.setCellValueFactory(new Callback<
        TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Offer, String> data) {

                TableSoftware tableSoftware = new TableSoftware(softwares.stream()
                        .filter(software -> software.getOffers() != null && software.getOffers().stream()
                        .anyMatch(softwareOffer -> softwareOffer.getOfferId()
                        .equals(data.getValue().getOfferId())))
                        .findAny()
                        .get().getName());

                return tableSoftware.getNameProperty();
            }
        });
        tableColumnSoftware.setCellFactory(tc -> {
            TableCell<Object, String> cell = new TableCell<Object, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnMouseClicked(event -> {
                try {
                    Offer offer = offers.stream().findFirst().orElse(null);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/OfferView.fxml"));
                    Parent root = (Parent) loader.load();
                    OfferViewController controller = ((OfferViewController) loader.getController());
                    controller.setStage(new Stage());
                    controller.setOffer(offer);
                    controller.setSoftware(softwares.stream()
                            .filter(software -> software.getOffers() != null
                            && software.getOffers().contains(offer))
                            .findFirst().get());
                    controller.setUser(user);
                    controller.initStage(root);
                    stage.hide();
                } catch (IOException exception) {
                    LOGGER.warning("Error opening the offer view. " + exception.getMessage());
                }
            });
            return cell;
        });

        tableColumnBasePrice.setCellValueFactory(new PropertyValueFactory("basePrice"));
        tableColumnDiscountedPrice.setCellValueFactory(new PropertyValueFactory("dicountedPrice"));
        tableColumnDiscount.setCellValueFactory(new PropertyValueFactory("discount"));

        tableColumnShop.setCellValueFactory(new Callback<
        TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Offer, String> data) {

                TableShop tableShop = new TableShop(data.getValue().getShop().getName());

                return tableShop.getNameProperty();
            }
        });

        tableColumnUrl.setCellValueFactory(new PropertyValueFactory("url"));

        ObservableList<Offer> observableOffers = FXCollections.observableArrayList();
        observableOffers.addAll(offers);
        tableViewOffers.setItems(observableOffers);
    }

    /**
     * Method to handle the action of the Button buttonClose.
     *
     * @param event Event triggered.
     */
    private void handleButtonCloseAction(ActionEvent event) {
        stage.hide();
    }

    /**
     * This method set the user.
     *
     * @param user the user to set.
     */
    public void setUser(User user) {
        this.user = user;
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
