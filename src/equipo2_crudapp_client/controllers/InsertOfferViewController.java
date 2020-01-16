/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Shop;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_client.clients.ShopClient;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the controller for the fxml view InsertOfferView.
 *
 * @author Iker Lopez
 */
public class InsertOfferViewController {

    /**
     * Logger to show error messages and exceptions.
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.InsertOfferView");

    /**
     * Stage of the controller InsertSoftwareViewController
     */
    private Stage stage;

    /**
     * Instance of the client manager for the entity Software.
     */
    private SoftwareClient softwareClient = new SoftwareClient();

    /**
     * Set of type software to contain every software received from the server.
     */
    private Set<Software> softwares = new HashSet<Software>();

    /**
     * Instance of the client manager for the entity Shop.
     */
    private ShopClient shopClient = new ShopClient();

    /**
     * Set of type shop to contain every shop received from the server.
     */
    private Set<Shop> shops = new HashSet<Shop>();

    /**
     * Context menu to show suggestions when the user starts writing the name of
     * the software or the name of the shop.
     */
    private ContextMenu entriesPopUp = new ContextMenu();

    /**
     * Boolean to check the validity of the text of the fields.
     */
    private boolean checkedFields;

    /**
     * Text field to insert the name of the software being offered.
     */
    @FXML
    private TextField textFieldSoftwareName;

    /**
     * Label to show a warning when the software from textFieldSoftwareName does
     * not exist or it is left empty.
     */
    @FXML
    private Label labelSoftwareNameWarning;

    /**
     * Date picker to insert the date the offer expires.
     */
    @FXML
    private DatePicker datePickerExpirationDate;

    /**
     * Label to show a warning when the date of datePickerExpirationDate is
     * posterior to the actual day or it is left empty..
     */
    @FXML
    private Label labelExpirationDateWarning;

    /**
     * Text field to insert the name of the shop making the offer.
     */
    @FXML
    private TextField textFieldShop;

    /**
     * Label to show a warning when the shop from textFieldShop does not exist
     * or it is left empty.
     */
    @FXML
    private Label labelShopWarning;

    /**
     * Text field to insert the base price of the software offered.
     */
    @FXML
    private TextField textFieldBasePrice;

    /**
     * Label to show a warning when the value of textFieldBasePrice is not valid
     * or it is left empty.
     */
    @FXML
    private Label labelBasePriceWarning;

    /**
     * Text field to insert the discounted price of the software offered.
     */
    @FXML
    private TextField textFieldDiscountedPrice;

    /**
     * Label to show a warning when the value of textFieldDiscountedPrice is not
     * valid or it is left empty.
     */
    @FXML
    private Label labelDiscountedPriceWarning;

    /**
     * Text field to insert the discount percentage of the software offered.
     */
    @FXML
    private TextField textFieldDiscount;

    /**
     * Label to show a warning when the value of textFieldDiscount is not valid
     * or it is left empty.
     */
    @FXML
    private Label labelDiscountWarning;

    /**
     * Text field to insert the url of the page from the shop where the software
     * is offered.
     */
    @FXML
    private TextField textFieldUrl;

    /**
     * Label to show a warning when the url from textFieldUrl is not valid or it
     * is left empty.
     */
    @FXML
    private Label labelUrlWarning;

    /**
     * Button to cancel the creation of a new offer. It will prompt a message
     * asking for confirmation before closing the window.
     */
    @FXML
    private Button buttonCancel;

    /**
     * Button to check the validity of the fields and, if correct, create a new
     * software. It will prompt a message asking for confirmation before
     * creating the new offer and closing the window.
     */
    @FXML
    private Button buttonAccept;

    /**
     * This method initialises the stage, shows the window, sets the components
     * and assigns the listeners.
     *
     * @param root Parent to set the scene.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Create New Offer");
        stage.show();

        //softwares = softwareClient.findAllSoftwares(new GenericType<Set<Software>>() {});
        //shops = shopClient.findAllShops(new GenericType<Set<Shop>>() {};
        
        Software software;
        Shop shop;
        for (int i = 0; i < 10; i++) {
            software = new Software();
            software.setName("software " + i);
            softwares.add(software);
            
            shop = new Shop(i, "shop" + i, "url");
            shops.add(shop);
        }
        
        datePickerExpirationDate.setValue(LocalDate.now());

        labelSoftwareNameWarning.setVisible(false);
        labelShopWarning.setVisible(false);
        labelExpirationDateWarning.setVisible(false);
        labelBasePriceWarning.setVisible(false);
        labelDiscountedPriceWarning.setVisible(false);
        labelDiscountWarning.setVisible(false);
        labelUrlWarning.setVisible(false);

        textFieldSoftwareName.focusedProperty().addListener(this::focusChanged);
        textFieldShop.focusedProperty().addListener(this::focusChanged);
        datePickerExpirationDate.focusedProperty().addListener(this::focusChanged);
        textFieldBasePrice.focusedProperty().addListener(this::focusChanged);
        textFieldDiscountedPrice.focusedProperty().addListener(this::focusChanged);
        textFieldDiscount.focusedProperty().addListener(this::focusChanged);
        textFieldUrl.focusedProperty().addListener(this::focusChanged);

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
        textFieldBasePrice.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelBasePriceWarning.setVisible(false);
            }
        });
        textFieldDiscountedPrice.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelDiscountedPriceWarning.setVisible(false);
            }
        });
        textFieldDiscount.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelDiscountWarning.setVisible(false);
            }
        });
    }

    private void checkFields() {

        checkedFields = true;

        if (!textFieldSoftwareName.isDisabled()) {
            if (softwares.stream().anyMatch(sw -> sw.getName().equalsIgnoreCase(textFieldSoftwareName.getText()))) {
                labelSoftwareNameWarning.setVisible(false);
            } else if (!textFieldSoftwareName.getText().equals("")) {
                labelSoftwareNameWarning.setVisible(true);
                labelSoftwareNameWarning.setText("*Software does not exist");
                checkedFields = false;
            }
        }

        if (datePickerExpirationDate.getValue().isBefore(LocalDate.now().plusDays(1))) {
            labelExpirationDateWarning.setVisible(false);
        } else {
            labelExpirationDateWarning.setVisible(true);
            labelExpirationDateWarning.setText("*Expiration date is not valid");
            checkedFields = false;
        }

        if (shops.stream().anyMatch(sw -> sw.getName().equalsIgnoreCase(textFieldShop.getText()))) {
            labelShopWarning.setVisible(false);
        } else if (!textFieldShop.getText().equals("")){
            labelShopWarning.setVisible(true);
            labelShopWarning.setText("*Shop does not exist");
            checkedFields = false;
        }

        if (textFieldBasePrice.getText().matches("^[0-9]{1,3}([,.][0-9]{1,2})?$")) {
            labelBasePriceWarning.setVisible(false);
        } else if (!textFieldBasePrice.getText().equals("")) {
            labelBasePriceWarning.setVisible(true);
            labelBasePriceWarning.setText("*Base price is not valid");
            checkedFields = false;
        }

        if (textFieldDiscountedPrice.getText().matches("^[0-9]{1,3}([,.][0-9]{1,2})?$")) {
            labelDiscountedPriceWarning.setVisible(false);
        } else if (!textFieldDiscountedPrice.getText().equals("")) {
            labelDiscountedPriceWarning.setVisible(true);
            labelDiscountedPriceWarning.setText("*Discounted price is not valid");
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
            textFieldUrl.setText(textFieldUrl.getText().trim());
        }

        checkFields();
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
