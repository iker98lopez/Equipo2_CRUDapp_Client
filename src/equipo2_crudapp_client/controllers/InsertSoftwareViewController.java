/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import java.time.LocalDate;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the controller for the fxml view InsertSoftwareView.
 *
 * @author Iker Lopez
 */
public class InsertSoftwareViewController {

    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.InsertSoftwareView");

    private Stage stage;

    private boolean checkedFields;

    /**
     * Image of the new software.
     */
    @FXML
    private ImageView imageViewSoftwareImage;

    /**
     * TextField to insert the name of the new software.
     */
    @FXML
    private TextField textFieldSoftwareName;

    /**
     * Label to show a warning when the text of textFieldSoftwareName is not
     * valid or it is empty.
     */
    @FXML
    private Label labelSoftwareWarning;

    /**
     * ChoiceBox to choose the type of the new software. It can be Program, Game
     * or extension.
     */
    @FXML
    private ChoiceBox choiceBoxSoftwareType;

    /**
     * Label to show a warning when the choiceBoxSoftwareType is left empty.
     */
    @FXML
    private Label labelSoftwareTypeWarning;

    /**
     * TextField to insert the publisher of the new software.
     */
    @FXML
    private TextField textFieldPublisher;

    /**
     * Label to show a warning when the text of textFieldPublisher is not valid
     * or it is empty.
     */
    @FXML
    private Label labelPublisherWarning;

    /**
     * DatePicker to insert the release date of the new software.
     */
    @FXML
    private DatePicker datePickerReleaseDate;

    /**
     * Label to show a warning when the release date of datePickerReleaseDate is
     * posterior to the actual date or it is left empty.
     */
    @FXML
    private Label labelReleaseDateWarning;

    /**
     * TextField only available when the value in choiceBoxSoftwareType is
     * Extension. It is used to insert the software extended by this one.
     */
    @FXML
    private TextField textFieldParentSoftware;

    /**
     * Label to show a warning when the text in textFieldParentSoftware is not
     * valid or it is empty;
     */
    @FXML
    private Label labelParentSoftwareWarning;

    /**
     * TextArea to insert a description of the new software.
     */
    @FXML
    private TextArea textAreaDescription;

    /**
     * Label to show a warning when the textAreaDescription is left empty.
     */
    @FXML
    private Label labelDescriptionWarning;

    /**
     * Button to cancel the creation of a new software.
     */
    @FXML
    private Button buttonCancel;

    /**
     * Button to check the validity of the fields and, if correct, create a new
     * software.
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
        stage.setTitle("Create New Software");
        stage.show();

        textFieldParentSoftware.setDisable(true);
        datePickerReleaseDate.setValue(LocalDate.now());
        
        labelSoftwareWarning.setVisible(false);
        labelSoftwareTypeWarning.setVisible(false);
        labelPublisherWarning.setVisible(false);
        labelParentSoftwareWarning.setVisible(false);
        labelReleaseDateWarning.setVisible(false);
        labelDescriptionWarning.setVisible(false);

        textFieldSoftwareName.focusedProperty().addListener(this::focusChanged);
        textAreaDescription.focusedProperty().addListener(this::focusChanged);
        textFieldPublisher.focusedProperty().addListener(this::focusChanged);

        buttonCancel.setOnAction(this::handleButtonCancelAction);
        buttonAccept.setOnAction(this::handleButtonAcceptAction);
    }

    /**
     * Event for focus changes.
     *
     * @param observable
     * @param oldValue
     * @param focused if false means out of focus.
     */
    private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean focused) {
        if (!focused) {
            textFieldSoftwareName.setText(textFieldSoftwareName.getText().trim());
            textFieldPublisher.setText(textFieldPublisher.getText().trim());
            textAreaDescription.setText(textAreaDescription.getText().trim());

            if (!textFieldParentSoftware.isDisabled()) {
                textFieldParentSoftware.setText(textFieldParentSoftware.getText().trim());
            }

            checkFields();
        }
    }

    /**
     * This method handles the action of the Button buttonCancel. It creates an
     * alert window first to make sure that the user wants to cancel the 
     * creation of a new software and, if accepted, closes the window.
     *
     * @param event Event triggered.
     */
    public void handleButtonCancelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel the creation of a new software?");
        alert.showAndWait();
        
        stage.hide();
    }

    /**
     * This method handles the action of the Button buttonAccept. It fires the
     * method checkFields() first and then makes sure that there are no empty
     * fields, then it creates the new software and closes the window.
     *
     * @param event Event triggered.
     */
    public void handleButtonAcceptAction(ActionEvent event) {

        checkFields();

        if (textFieldSoftwareName.getText().equals("")) {
            checkedFields = false;
            labelSoftwareWarning.setVisible(true);
            labelSoftwareWarning.setText("*This field is empty");
        }

        if (textFieldPublisher.getText().equals("")) {
            checkedFields = false;
            labelPublisherWarning.setVisible(true);
            labelPublisherWarning.setText("*This field is empty");
        }

        if (textAreaDescription.getText().equals("")) {
            checkedFields = false;
            labelDescriptionWarning.setVisible(true);
            labelDescriptionWarning.setText("*This field is empty");
        }

        if (textFieldParentSoftware.getText().equals("") && !textFieldParentSoftware.isDisabled()) {
            checkedFields = false;
            labelSoftwareWarning.setVisible(true);
            labelSoftwareWarning.setText("*This field is empty");
        }

        if (checkedFields) {
            stage.hide();
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

            labelSoftwareWarning.setVisible(false);
        } else if (!textFieldSoftwareName.getText().equals("")) {
            labelSoftwareWarning.setVisible(true);
            labelSoftwareWarning.setText("*Software name is not valid");
            checkedFields = false;
        }

        if (textFieldPublisher.getText().length() >= 3
                && textFieldPublisher.getText().length() < 18
                && textFieldPublisher.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_]+")) {

            labelPublisherWarning.setVisible(false);
        } else if (!textFieldPublisher.getText().equals("")) {
            labelPublisherWarning.setVisible(true);
            labelPublisherWarning.setText("*Publisher is not valid");
            checkedFields = false;
        }

        if (!textFieldParentSoftware.isDisabled()) {
            if (textFieldParentSoftware.getText().length() >= 3
                    && textFieldParentSoftware.getText().length() < 18
                    && textFieldParentSoftware.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_]+")) {

                labelParentSoftwareWarning.setVisible(false);
            } else if (!textFieldParentSoftware.getText().equals("")) {
                labelParentSoftwareWarning.setVisible(true);
                labelParentSoftwareWarning.setText("*Parent software is not valid");
                checkedFields = false;
            }
        }
        
        if (datePickerReleaseDate.getValue().isBefore(LocalDate.now().plusDays(1))) {
            labelReleaseDateWarning.setVisible(false);
        } else {
            labelReleaseDateWarning.setVisible(true);
            labelReleaseDateWarning.setText("*Choose a valid date");
            checkedFields = false;
        }
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
