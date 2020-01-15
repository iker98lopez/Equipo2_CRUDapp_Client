/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Diego Corral
 */
public class InsertOfferViewController {

    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.InsertOfferView");

    private SoftwareClient softwareClient = new SoftwareClient();

    private Stage stage;

    private Set<Software> softwares = new HashSet<Software>();
    private ContextMenu entriesPopUp = new ContextMenu();
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

        datePickerExpirationDate.setValue(LocalDate.now());

        labelSoftwareNameWarning.setVisible(false);
        labelShopWarning.setVisible(false);
        labelExpirationDateWarning.setVisible(false);
        labelBasePriceWarning.setVisible(false);
        labelDiscountedPriceWarning.setVisible(false);
        labelDiscountWarning.setVisible(false);
        labelUrlWarning.setVisible(false);

        //"\\d[1-3]([\\.,][\\d{1,2}])?" REGEX para comprobar que el valor esta en formato 000.00 o 000,00

        textFieldSoftwareName.focusedProperty().addListener(this::focusChanged);
        textFieldBasePrice.focusedProperty().addListener(this::focusChanged);
    }

    private void checkFields() {
        
        if (!textFieldBasePrice.getText().matches("\\d[1-3]([\\.,][\\d{1,2}])?")) {
            textFieldBasePrice.setText("");
            labelBasePriceWarning.setVisible(true);
            labelBasePriceWarning.setText("*Base price is not valid");
        } else if (!textFieldBasePrice.getText().contentEquals("")) {
            labelBasePriceWarning.setVisible(false);
        }
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
