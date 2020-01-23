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
import equipo2_crudapp_classes.enumerators.UserPrivilege;
import equipo2_crudapp_client.clients.OfferClient;
import equipo2_crudapp_client.clients.ShopClient;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

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
     * Current user of the application.
     */
    private User user;

    /**
     * Instance of the client manager for the entity Offer.
     */
    private final OfferClient OFFERCLIENT = new OfferClient();

    /**
     * Offer to show and edit in this view.
     */
    private Offer offer;

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
     * Context menu to show suggestions when the user starts writing the name of
     * the parent software.
     */
    private ContextMenu entriesPopUp = new ContextMenu();

    /**
     * Boolean to check the validity of the text of the fields.
     */
    private boolean checkedFields;

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

    /**
     * Label to show a warning when the software name is not valid or it does
     * not exist.
     */
    @FXML
    private Label labelSoftwareNameWarning;

    /**
     * Text field to show and edit the shop making the offer.
     */
    @FXML
    private TextField textFieldShop;

    /**
     * Label to show a warning when the shop is not valid or it does not exist.
     */
    @FXML
    private Label labelShopWarning;

    /**
     * Label to show the discounted price of the software. It is automatically
     * calculated when both the textFieldBasePrice and the textFieldDiscount
     * have been filled.
     */
    @FXML
    private Label labelDiscountedPrice;

    /**
     * Text field to show and edit the base price of the software.
     */
    @FXML
    private TextField textFieldBasePrice;

    /**
     * Label to show a warning when the base price is not valid or it is empty.
     */
    @FXML
    private Label labelBasePriceWarning;

    /**
     * Text field to show and edit the discount percentage of the offer.
     */
    @FXML
    private TextField textFieldDiscount;

    /**
     * Label to show a warning when the discount is not valid or it is empty.
     */
    @FXML
    private Label labelDiscountWarning;

    /**
     * Toggle button to make the text fields editable and let the admin users
     * edit the offer. Only available when the current user is an admin.
     */
    @FXML
    private ToggleButton toggleButtonEdit;

    /**
     * Label to show the username of the user who created the offer.
     */
    @FXML
    private Label labelUsername;

    /**
     * Button to delete the offer. It will show an alert asking for the user's
     * confirmation before deleting the offer. Only available when the user is
     * an admin and/or the creator of the offer.
     */
    @FXML
    private Button buttonDelete;

    /**
     * Text field to show and edit the url link to the shop page of the offer.
     */
    @FXML
    private TextField textFieldUrl;

    /**
     * Label to show a warning when the url is not valid or it is empty.
     */
    @FXML
    private Label labelUrlWarning;

    /**
     * Button to report the offer. ! Currently has no functionality.
     */
    @FXML
    private Button buttonReport;

    /**
     * Date picker to edit the expiration date of the offer.
     */
    @FXML
    private DatePicker datePickerExpirationDate;

    /**
     * Label to show a the expiration date of the offer.
     */
    @FXML
    private Label labelExpirationDate;

    /**
     * Label to show a warning when the expiration date is not valid or it is
     * empty;
     */
    @FXML
    private Label labelExpirationDateWarning;

    /**
     * List view to show the comments of the offer.
     */
    @FXML
    private ListView listViewComments;

    /**
     * Button to make a new comment.
     */
    @FXML
    private Button buttonComment;

    /**
     * This method initialises the stage, shows the window, sets the components
     * and assigns the listeners.
     *
     * @param root Parent to set the scene.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/equipo2_crudapp_client/views/textField.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("See Offer");
        stage.show();

        try {
            softwares = SOFTWARECLIENT.findAllSoftwares(new GenericType<Set<Software>>() {
            });
            shops = SHOPCLIENT.findAllShops(new GenericType<Set<Shop>>() {
            });
        } catch (NotFoundException exception) {
            LOGGER.warning("There was a problem fetching information from the server. " + exception.getMessage());
        }

        datePickerExpirationDate.setVisible(false);

        labelSoftwareNameWarning.setVisible(false);
        labelShopWarning.setVisible(false);
        labelBasePriceWarning.setVisible(false);
        labelUrlWarning.setVisible(false);
        labelExpirationDateWarning.setVisible(false);

        textFieldSoftwareName.setEditable(false);
        textFieldShop.setEditable(false);
        textFieldShop.setEditable(false);
        textFieldBasePrice.setEditable(false);
        textFieldDiscount.setEditable(false);
        textFieldUrl.setEditable(false);

        textFieldSoftwareName.setText(null);
        textFieldShop.setText(offer.getShop().getName());
        labelDiscountedPrice.setText(String.valueOf(offer.getDicountedPrice()));
        textFieldBasePrice.setText(String.valueOf(offer.getBasePrice()));
        textFieldDiscount.setText(String.valueOf(offer.getDiscount()));
        labelUsername.setText(null);
        textFieldUrl.setText(offer.getUrl());
        labelExpirationDate.setText(String.valueOf(offer.getExpiringDate()));

        textFieldSoftwareName.focusedProperty().addListener(this::focusChanged);
        textFieldShop.focusedProperty().addListener(this::focusChanged);
        textFieldBasePrice.focusedProperty().addListener(this::focusChanged);
        textFieldDiscount.focusedProperty().addListener(this::focusChanged);
        textFieldUrl.focusedProperty().addListener(this::focusChanged);

        if (user.getPrivilege().equals(UserPrivilege.USER) && user.getUserId() != null) {
            toggleButtonEdit.setVisible(false);
            buttonDelete.setVisible(false);
        } else {
            buttonDelete.setOnAction(this::handleButtonDeleteAction);

            toggleButtonEdit.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (observable.getValue()) {
                        scene.getStylesheets().remove("/equipo2_crudapp_client/views/textField.css");

                        textFieldSoftwareName.setEditable(true);
                        textFieldShop.setEditable(true);
                        textFieldBasePrice.setEditable(true);
                        textFieldDiscount.setEditable(true);
                        textFieldUrl.setEditable(true);

                        datePickerExpirationDate.setVisible(true);
                        datePickerExpirationDate.setValue(LocalDate.parse(labelExpirationDate.getText()));
                        labelExpirationDate.setVisible(false);
                    } else {

                        checkFields();

                        if (textFieldSoftwareName.getText().equals("")) {
                            checkedFields = false;
                            labelSoftwareNameWarning.setVisible(true);
                            labelSoftwareNameWarning.setText("*This field is empty");
                        }

                        if (textFieldShop.getText().equals("")) {
                            checkedFields = false;
                            labelShopWarning.setVisible(true);
                            labelShopWarning.setText("*This field is empty");
                        }

                        if (textFieldBasePrice.getText().equals("")) {
                            checkedFields = false;
                            labelBasePriceWarning.setVisible(true);
                            labelBasePriceWarning.setText("*This field is empty");
                        }

                        if (textFieldDiscount.getText().equals("")) {
                            checkedFields = false;
                            labelDiscountWarning.setVisible(true);
                            labelDiscountWarning.setText("*This field is empty");
                        }

                        Alert alertSave = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save the changes?");
                        alertSave.showAndWait();

                        if (alertSave.getResult() == ButtonType.OK) {
                            if (checkedFields) {
                                OFFERCLIENT.deleteOffer(offer.getOfferId());
                                scene.getStylesheets().add("/equipo2_crudapp_client/views/textField.css");

                                textFieldSoftwareName.setEditable(false);
                                textFieldShop.setEditable(false);
                                textFieldBasePrice.setEditable(false);
                                textFieldDiscount.setEditable(false);
                                textFieldUrl.setEditable(false);

                                datePickerExpirationDate.setVisible(false);
                                labelExpirationDate.setText(datePickerExpirationDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                labelExpirationDate.setVisible(true);
                            } else {
                                Alert alertSyntax = new Alert(Alert.AlertType.ERROR, "Some fields are not valid. Please correct them before saving.");
                                alertSyntax.showAndWait();
                            }
                        }
                    }
                }
            });
        }
        textFieldSoftwareName.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                labelSoftwareNameWarning.setVisible(false);

                String enteredText = textFieldSoftwareName.getText();
                if (enteredText == null || enteredText.isEmpty()) {
                    entriesPopUp.hide();
                } else {
                    List<String> filteredEntries = softwares.stream()
                            .filter(sw -> sw.getName().toLowerCase().contains(enteredText.toLowerCase()))
                            .map(sw -> sw.getName())
                            .collect(Collectors.toList());
                    if (!filteredEntries.isEmpty()) {
                        fillPopUp(filteredEntries, enteredText, textFieldSoftwareName);
                        if (!entriesPopUp.isShowing()) {
                            entriesPopUp.show(textFieldSoftwareName, Side.BOTTOM, 0, 0);
                        }
                    } else {
                        entriesPopUp.hide();
                    }
                }
            }
        });
        textFieldShop.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                labelShopWarning.setVisible(false);

                String enteredText = textFieldShop.getText();
                if (enteredText == null || enteredText.isEmpty()) {
                    entriesPopUp.hide();
                } else {
                    List<String> filteredEntries = shops.stream()
                            .filter(shop -> shop.getName().toLowerCase().contains(enteredText.toLowerCase()))
                            .map(shop -> shop.getName())
                            .collect(Collectors.toList());
                    if (!filteredEntries.isEmpty()) {
                        fillPopUp(filteredEntries, enteredText, textFieldShop);
                        if (!entriesPopUp.isShowing()) {
                            entriesPopUp.show(textFieldShop, Side.BOTTOM, 0, 0);
                        }
                    } else {
                        entriesPopUp.hide();
                    }
                }
            }
        });
        textFieldBasePrice.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                labelBasePriceWarning.setVisible(false);
            }
        });
        textFieldDiscount.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                labelDiscountWarning.setVisible(false);
            }
        });
        textFieldUrl.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                labelUrlWarning.setVisible(false);
            }
        });
    }

    /**
     * Event for focus changes.
     *
     * @param observable
     * @param oldValue
     * @param focused if false means out of focus.
     */
    private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean focused) {

        entriesPopUp.hide();

        if (!focused) {
            textFieldSoftwareName.setText(textFieldSoftwareName.getText().trim());
            textFieldShop.setText(textFieldShop.getText().trim());
            textFieldBasePrice.setText(textFieldBasePrice.getText().trim());
            textFieldDiscount.setText(textFieldDiscount.getText().trim());
            textFieldUrl.setText(textFieldUrl.getText().trim());

            if (Double.valueOf(textFieldBasePrice.getText()) > 0
                    && Integer.valueOf(textFieldDiscount.getText()) > 0) {
                labelDiscountedPrice.setText((String.valueOf(
                        Double.valueOf(textFieldBasePrice.getText())
                        * Integer.valueOf(textFieldDiscount.getText()) / 100)));
            }

            checkFields();
        }
    }

    /**
     * This method checks that the fields have valid values. If a value is not
     * valid, the corresponding warning label is made visible.
     */
    private void checkFields() {

        checkedFields = true;

        if (textFieldSoftwareName.getText().length() >= 3
                && textFieldSoftwareName.getText().length() < 18
                && textFieldSoftwareName.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_]+")) {

            labelSoftwareNameWarning.setVisible(false);
        } else if (!textFieldSoftwareName.getText().equals("")) {
            labelSoftwareNameWarning.setVisible(true);
            labelSoftwareNameWarning.setText("*Software name is not valid");
            checkedFields = false;
        }

        if (textFieldShop.getText().length() >= 3
                && textFieldShop.getText().length() < 18
                && textFieldShop.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_]+")) {

            labelShopWarning.setVisible(false);
        } else if (!textFieldShop.getText().equals("")) {
            labelShopWarning.setVisible(true);
            labelShopWarning.setText("*Shop name is not valid");
            checkedFields = false;
        }

        if (textFieldBasePrice.getText().matches("^[0-9]{1,3}([,.][0-9]{1,2})?$")) {
            labelBasePriceWarning.setVisible(false);
        } else if (!textFieldBasePrice.getText().equals("")) {
            labelBasePriceWarning.setVisible(true);
            labelBasePriceWarning.setText("*Base price is not valid");
            checkedFields = false;
        }

        if (textFieldDiscount.getText().matches("\\d{1,2}")) {
            labelDiscountWarning.setVisible(false);
        } else if (!textFieldDiscount.getText().equals("")) {
            labelDiscountWarning.setVisible(true);
            labelDiscountWarning.setText("*Discount is not valid");
            checkedFields = false;
        }

        if (textFieldUrl.getText().length() >= 3
                && textFieldUrl.getText().length() < 18
                && textFieldUrl.getText().matches("[a-zA-Z0-9\\.\\*\\_\\/\\=\\?\\-\\(\\)\\'\\|\\@\\#\\$\\&]+")) {

            labelUrlWarning.setVisible(false);
        } else if (!textFieldUrl.getText().equals("")) {
            labelUrlWarning.setVisible(true);
            labelUrlWarning.setText("*URL is not valid");
            checkedFields = false;
        }
    }

    /**
     * This method receives all suggestions found and fills the pop up with
     * them.
     *
     * @param filteredEntries
     */
    private void fillPopUp(List<String> filteredEntries, String enteredText, TextField textField) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(filteredEntries.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = filteredEntries.get(i);
            Label entryLabel = new Label();

            int filterIndex = result.toLowerCase().indexOf(enteredText.toLowerCase());
            Text textBefore = new Text(result.substring(0, filterIndex));
            Text textAfter = new Text(result.substring(filterIndex + enteredText.length()));
            Text textFilter = new Text(result.substring(filterIndex, filterIndex + enteredText.length())); //instead of "filter" to keep all "case sensitive"
            textFilter.setFill(Color.ORANGE);
            textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
            TextFlow textFlow = new TextFlow(textBefore, textFilter, textAfter);

            entryLabel.setGraphic(textFlow);
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            item.setOnAction(event -> {
                textField.setText(result);
                textField.positionCaret(result.length());
                entriesPopUp.hide();
            });
        }

        entriesPopUp.getItems().clear();
        entriesPopUp.getItems().addAll(menuItems);
    }

    /**
     * This method handles the action of the Button buttonDelete. It creates an
     * alert window first to make sure that the user wants to delete the offer
     * and, if accepted, removes it from the database and closes the window.
     *
     * @param event Event triggered.
     */
    private void handleButtonDeleteAction(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this offer?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            OFFERCLIENT.deleteOffer(offer.getOfferId());
            stage.hide();
        }
    }

    /**
     * This method sets the user.
     *
     * @param user User to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * This method sets the offer.
     *
     * @param offer Offer to be set.
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
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
