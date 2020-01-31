/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_client.clients.SoftwareClient;
import equipo2_crudapp_classes.enumerators.SoftwareType;
import java.util.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

/**
 * This is the controller for the fxml view InsertSoftwareView.
 *
 * @author Iker Lopez
 */
public class InsertSoftwareViewController {

    /**
     * Logger to show error messages and exceptions.
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.InsertSoftwareView");

    /**
     * Stage of the controller InsertSoftwareViewController
     */
    private Stage stage;

    /**
     * Instance of the client manager for the entity Software.
     */
    private final SoftwareClient SOFTWARECLIENT = new SoftwareClient();

    /**
     * Set of type software to contain every software received from the server.
     */
    private Set<Software> softwares = new HashSet<Software>();

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
    private Label labelSoftwareNameWarning;

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
     * Button to cancel the creation of a new software. It will prompt a message
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
        stage.setTitle("Create New Software");
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();

        textFieldParentSoftware.setDisable(true);
        datePickerReleaseDate.setValue(LocalDate.now());

        labelSoftwareNameWarning.setVisible(false);
        labelSoftwareTypeWarning.setVisible(false);
        labelPublisherWarning.setVisible(false);
        labelParentSoftwareWarning.setVisible(false);
        labelReleaseDateWarning.setVisible(false);
        labelDescriptionWarning.setVisible(false);

        choiceBoxSoftwareType.getItems().add(SoftwareType.PROGRAM);
        choiceBoxSoftwareType.getItems().add(SoftwareType.GAME);
        choiceBoxSoftwareType.getItems().add(SoftwareType.EXTENSION);
        choiceBoxSoftwareType.getSelectionModel().selectFirst();

        textFieldSoftwareName.focusedProperty().addListener(this::focusChanged);
        textFieldParentSoftware.focusedProperty().addListener(this::focusChanged);
        textAreaDescription.focusedProperty().addListener(this::focusChanged);
        textFieldPublisher.focusedProperty().addListener(this::focusChanged);

        textFieldParentSoftware.textProperty().addListener(this::popUpSuggestions);

        choiceBoxSoftwareType.valueProperty().addListener(this::valueChanged);

        buttonCancel.setOnAction(this::handleButtonCancelAction);
        buttonAccept.setOnAction(this::handleButtonAcceptAction);

        textFieldSoftwareName.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelSoftwareNameWarning.setVisible(false);
            }
        });
        textFieldPublisher.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelPublisherWarning.setVisible(false);
            }
        });
        textFieldParentSoftware.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelParentSoftwareWarning.setVisible(false);
            }
        });
        textAreaDescription.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                labelDescriptionWarning.setVisible(false);
            }
        });

        try {
            softwares = SOFTWARECLIENT.findAllSoftwares(new GenericType<Set<Software>>() {
            });
        } catch (NotFoundException exception) {
            LOGGER.warning("There are no softwares to be found. " + exception.getMessage());
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to cancel the creation of a new software?");
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Cancel Creation");

        if (alert.getResult() == ButtonType.YES) {
            stage.hide();
        }
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
            labelSoftwareNameWarning.setVisible(true);
            labelSoftwareNameWarning.setText("*This field is empty");
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
            labelSoftwareNameWarning.setVisible(true);
            labelSoftwareNameWarning.setText("*This field is empty");
        }

        if (checkedFields) {

            Software software = new Software();
            software.setName(textFieldSoftwareName.getText());
            software.setDescription(textAreaDescription.getText());
            software.setPublisher(textFieldPublisher.getText());
            software.setSoftwareType(SoftwareType.valueOf(choiceBoxSoftwareType.getValue().toString()));
            software.setReleaseDate(new Date(datePickerReleaseDate.getValue().toEpochDay()));

            if (software.getSoftwareType().equals(SoftwareType.EXTENSION)) {
                software.setParentSoftware(softwares.stream().filter(sw -> sw.getName().equals(textFieldParentSoftware.getText())).findFirst().get());
            }

            SOFTWARECLIENT.createSoftware(software);

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
                && textFieldSoftwareName.getText().length() < 32
                && textFieldSoftwareName.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_\\s]+")) {

            labelSoftwareNameWarning.setVisible(false);
        } else if (!textFieldSoftwareName.getText().equals("")) {
            labelSoftwareNameWarning.setVisible(true);
            labelSoftwareNameWarning.setText("*Software name is not valid");
            checkedFields = false;
        }

        if (textFieldPublisher.getText().length() >= 3
                && textFieldPublisher.getText().length() < 24
                && textFieldPublisher.getText().matches("[a-zA-Z0-9\\.\\-\\*\\_\\s]+")) {

            labelPublisherWarning.setVisible(false);
        } else if (!textFieldPublisher.getText().equals("")) {
            labelPublisherWarning.setVisible(true);
            labelPublisherWarning.setText("*Publisher is not valid");
            checkedFields = false;
        }

        if (!textFieldParentSoftware.isDisabled()) {
            if (softwares.stream().anyMatch(sw -> sw.getName().equalsIgnoreCase(textFieldParentSoftware.getText()))) {
                labelParentSoftwareWarning.setVisible(false);
            } else {
                labelParentSoftwareWarning.setVisible(true);
                labelParentSoftwareWarning.setText("*Parent software does not exist");
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
     * Event triggered when the text of the textFieldSoftwareName or
     * textFieldShop is changed. It uses a list containing all the softwares
     * from the database to create a pop up menu with name suggestions.
     *
     * @param observable
     */
    private void popUpSuggestions(Observable observable) {
        String enteredText = textFieldParentSoftware.getText();
        if (enteredText == null || enteredText.isEmpty()) {
            entriesPopUp.hide();
        } else {
            List<String> filteredEntries = softwares.stream()
                    .filter(sw -> sw.getName().toLowerCase().contains(enteredText.toLowerCase()))
                    .map(sw -> sw.getName())
                    .collect(Collectors.toList());
            if (!filteredEntries.isEmpty()) {
                fillPopUp(filteredEntries, enteredText);
                if (!entriesPopUp.isShowing()) {
                    entriesPopUp.show(textFieldParentSoftware, Side.BOTTOM, 0, 0);
                }
            } else {
                entriesPopUp.hide();
            }
        }
    }

    /**
     * This method receives all suggestions found and fills the pop up with
     * them.
     *
     * @param filteredEntries
     */
    private void fillPopUp(List<String> filteredEntries, String enteredText) {
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
                textFieldParentSoftware.setText(result);
                textFieldParentSoftware.positionCaret(result.length());
                entriesPopUp.hide();
            });
        }

        entriesPopUp.getItems().clear();
        entriesPopUp.getItems().addAll(menuItems);
    }

    /**
     * Event handler for the change of the value of choiceBoxSoftwareType. It
     * disables textFieldParentSoftware when the software type is extension or
     * enables it if not.
     *
     * @param observable
     */
    private void valueChanged(Observable observable) {
        if (choiceBoxSoftwareType.getValue().equals(SoftwareType.EXTENSION)) {
            textFieldParentSoftware.setDisable(false);
        } else {
            textFieldParentSoftware.setText("");
            textFieldParentSoftware.setDisable(true);
        }
    }

    /**
     * Event to show an alert when the user presses the close button and ask for
     * confirmation before closing.
     *
     * @param event Event launched by the window.
     */
    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Close window");
        alert.setContentText(String.format("Close without saving?"));
        alert.showAndWait();

        if (alert.getResult() == ButtonType.CANCEL) {
            event.consume();
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
