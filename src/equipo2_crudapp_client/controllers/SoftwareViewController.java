/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.enumerators.SoftwareType;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Adrián García
 */
public class SoftwareViewController {

    /**
     * Software client
     */
    private static final SoftwareClient SOFTWARE_CLIENT = new SoftwareClient();
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.SoftwareViewController");
    /**
     * Stage of the controller
     */
    private Stage stage;
    /**
     * Software id
     */
    private String softwareId;
    /**
     * Scene of the controller
     */
    private Scene scene;
    /**
     * The image of the Software
     */
    @FXML
    private ImageView imageView;
    /**
     * The title of the Sotware
     */
    @FXML
    private TextField textFieldTitle;
    /**
     * The publisher of the Software
     */
    @FXML
    private TextField textFieldPublisher;
    /**
     * The date of release of the Software
     */
    @FXML
    private DatePicker datePicker;
    /**
     * The desceription of the Software
     */
    @FXML
    private TextArea textAreaDescription;
    /**
     * ListView containing all the offers for the Software
     */
    @FXML
    private ListView listViewOffer;
    /**
     * ListView containing all the offers for the Software
     */
    @FXML
    private ListView listViewExtensions;
    /**
     * ComboBox to select software type
     */
    @FXML
    private ComboBox comboBoxType;
    /**
     * CheckBox to allow edit info
     */
    @FXML
    private CheckBox checkBoxEdit;

    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {

        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Software");

        stage.show();

        checkBoxEdit.setOnAction(this::handleCheckBoxEditAction);

        textFieldTitle.setEditable(false);
        textFieldPublisher.setEditable(false);
        textAreaDescription.setEditable(false);
        datePicker.setEditable(false);
        comboBoxType.setEditable(false);

        populateData();
    }

    private void populateData() {
        Software software = new Software();
        try {
            software = SOFTWARE_CLIENT.findSoftware(Software.class, softwareId);
        } catch (ClientErrorException clientErrorException) {
        }
        textFieldTitle.setText(software.getName());
        textFieldPublisher.setText(software.getPublisher());
        textAreaDescription.setText(software.getDescription());
        datePicker.setValue(software.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comboBoxType.valueProperty().set(software.getSoftwareType());
        //Populate offers list
        ObservableList<Offer> observableOffers = FXCollections.observableArrayList(software.getOffers());
        listViewOffer.setItems(observableOffers);
        listViewOffer.setCellFactory(param -> new ListCell<Offer>() {
            @Override
            protected void updateItem(Offer offer, boolean empty) {
                super.updateItem(offer, empty);

                if (empty || offer == null || offer.getUrl() == null) {
                    setText(null);
                } else {
                    setText(offer.getUrl());
                }
            }
        });
        //Get extensions filtered from all the software
        List<Software> softwares = new ArrayList<>();
        
        try {
            softwares = SOFTWARE_CLIENT.findAllSoftwares(new GenericType<List<Software>>() {
            });
        } catch (ClientErrorException clientErrorException) {
            clientErrorException.printStackTrace();
        }
        
        for (Software item : softwares) {
            if (item.getSoftwareId() != Integer.parseInt(softwareId)) {
                softwares.remove(item);
            }
        }
        //Populate extensions list
        ObservableList<Software> observableExtensions = FXCollections.observableArrayList(softwares);
        listViewOffer.setItems(observableExtensions);
        listViewExtensions.setCellFactory(param -> new ListCell<Software>() {
            @Override
            protected void updateItem(Software software, boolean empty) {
                super.updateItem(software, empty);

                if (empty || software == null || software.getName() == null) {
                    setText(null);
                } else {
                    setText(software.getName());
                }
            }
        });

    }

    /**
     * Method that allows to edit the Software info
     */
    public void handleCheckBoxEditAction(ActionEvent event) {
        if (checkBoxEdit.isSelected()) {
            checkBoxEdit.setText("Save");
            textFieldTitle.setEditable(true);
            textFieldPublisher.setEditable(true);
            textAreaDescription.setEditable(true);
            datePicker.setEditable(true);
            comboBoxType.setEditable(true);
        } else {
            checkBoxEdit.setText("Edit");
            textFieldTitle.setEditable(false);
            textFieldPublisher.setEditable(false);
            textAreaDescription.setEditable(false);
            datePicker.setEditable(false);
            comboBoxType.setEditable(false);

            Software software = new Software();
            software.setName(textFieldTitle.getText());
            software.setPublisher(textFieldPublisher.getText());
            software.setDescription(textAreaDescription.getText());
            software.setReleaseDate(new Date(datePicker.getValue().toEpochDay()));
            software.setSoftwareType((SoftwareType) comboBoxType.valueProperty().get());

            SOFTWARE_CLIENT.editSoftware(software, softwareId);
        }
    }

    /**
     * This method sets the stage
     *
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSoftwareId(String id) {
        this.softwareId = id;
    }
}
