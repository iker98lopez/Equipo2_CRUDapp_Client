/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_ciphering.ClientCipher;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_client.clients.UserClient;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javax.ws.rs.InternalServerErrorException;
import javax.xml.bind.DatatypeConverter;

/**
 * Controller for the user view
 * @author Diego Corral
 */
public class UserViewController extends GenericSideBarController{
    
    /**
     * Logger for UserViewController class
     */
    private static final Logger LOGGER = Logger
            .getLogger("equipo2_crudapp_client.controllers.UserViewController");
    
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    
    private static final UserClient USERCLIENT = new UserClient();
    
    /**
     * True if the edit toggle button is pressed, otherwise false
     */
    private boolean toggleButtonEditIsPressed;
    
    @FXML
    private TextField textFieldLogin;
    
    @FXML
    private TextField textFieldName;
    
    @FXML
    private TextField textFieldEmail;
    
    @FXML
    private ToggleButton toggleButtonEdit;
    
    @FXML
    private PasswordField passwordFieldNewPassword;
    
    @FXML
    private PasswordField passwordFieldRepeatPassword;
    
    @FXML
    private Button buttonChangePassword;
    
    @FXML
    private Label labelLoginNotValid;
    
    @FXML
    private Label labelNameNotValid;
    
    @FXML
    private Label labelEmailNotValid;
    
    @FXML
    private Label labelNewPasswordNotValid;
    
    @FXML
    private Label labelRepeatPasswordNotValid;
    
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
        stage.setTitle("User");
        stage.show();
        
        // Initialize elements of the view
        toggleButtonEditIsPressed = false;
        labelLoginNotValid.setVisible(false);
        labelNameNotValid.setVisible(false);
        labelEmailNotValid.setVisible(false);
        labelNewPasswordNotValid.setVisible(false);
        labelRepeatPasswordNotValid.setVisible(false);
        
        if(user!=null){
            textFieldLogin.setText(user.getLogin());
            textFieldName.setText(user.getFullName());
            textFieldEmail.setText(user.getEmail());
        }
        
        
        // Set handlers
        buttonChangePassword.setOnAction(this::handleChangePasswordButtonAction);
        toggleButtonEdit.setOnAction(this::handleToggleButtonEditAction);
        textFieldLogin.textProperty().addListener(this::handleTextChangeLogin);
        textFieldName.textProperty().addListener(this::handleTextChangeName);
        textFieldEmail.textProperty().addListener(this::handleTextChangeEmail);
        passwordFieldNewPassword.textProperty()
                .addListener(this::handleTextChangeNewPassword);
        passwordFieldRepeatPassword.textProperty()
                .addListener(this::handleTextChangeRepeatPassword);
    }
    
    private void handleToggleButtonEditAction(ActionEvent event){
        if(!toggleButtonEditIsPressed){
            textFieldLogin.setDisable(false);
            textFieldName.setDisable(false);
            textFieldEmail.setDisable(false);
            
            toggleButtonEditIsPressed = true;
        } else{
            if(userDataIsModified()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Do you want to save the changes?"
                        , ButtonType.NO, ButtonType.YES
                        , ButtonType.CANCEL);
                Button yesButton = (Button) alert.getDialogPane()
                        .lookupButton(ButtonType.YES);
                yesButton.setId("yesButton");
                Button noButton = (Button) alert.getDialogPane()
                        .lookupButton(ButtonType.NO);
                noButton.setId("noButton");
                Button cancelButton = (Button) alert.getDialogPane()
                        .lookupButton(ButtonType.CANCEL);
                cancelButton.setId("cancelButton");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get()==ButtonType.YES) {
                    if(userDataSyntaxIsCorrect()){
                        textFieldLogin.setDisable(true);
                        textFieldName.setDisable(true);
                        textFieldEmail.setDisable(true);

                        toggleButtonEditIsPressed = false;
                    } else {
                        toggleButtonEdit.setSelected(true);
                        toggleButtonEditIsPressed = true;
                    }
                } else if(result.isPresent() && result.get()==ButtonType.NO) {
                    textFieldLogin.setText(user.getLogin());
                    textFieldName.setText(user.getFullName());
                    textFieldEmail.setText(user.getEmail());

                    textFieldLogin.setDisable(true);
                    textFieldName.setDisable(true);
                    textFieldEmail.setDisable(true);

                    labelLoginNotValid.setVisible(false);
                    labelNameNotValid.setVisible(false);
                    labelEmailNotValid.setVisible(false);
                    
                    toggleButtonEditIsPressed = false;
                } else {
                    toggleButtonEdit.setSelected(true);
                    toggleButtonEditIsPressed = true;
                }
            } else {
                textFieldLogin.setDisable(true);
                textFieldName.setDisable(true);
                textFieldEmail.setDisable(true);
                toggleButtonEditIsPressed = false;
            }
        }
    }
    
    private void handleTextChangeLogin(ObservableValue observable
            , String oldValue
            , String newValue){
        labelLoginNotValid.setVisible(false);
    }
    
    private void handleTextChangeName(ObservableValue observable
            , String oldValue
            , String newValue){
        labelNameNotValid.setVisible(false);
    }
    
    private void handleTextChangeEmail(ObservableValue observable
            , String oldValue
            , String newValue){
        labelEmailNotValid.setVisible(false);
    }
    
    private void handleTextChangeNewPassword(ObservableValue observable
            , String oldValue
            , String newValue){
        labelNewPasswordNotValid.setVisible(false);
    }
    
    private void handleTextChangeRepeatPassword(ObservableValue observable
            , String oldValue
            , String newValue){
        labelRepeatPasswordNotValid.setVisible(false);
    }
    
    /**
     * Checks the syntax of the user's data fields.
     * @return true if correct, otherwise false.
     */
    private boolean userDataSyntaxIsCorrect() {
        boolean ret = true;
        final String NECESSARY_CHARS = "[a-zA-Z0-9\\.\\-\\*\\s]+";
        
        // Validation of the login field
        if (textFieldLogin.getText().length() >= 3
                && textFieldLogin.getText().length() < 18
                && textFieldLogin.getText().matches(NECESSARY_CHARS)) {

            ret = true;
        } else {
            labelLoginNotValid.setVisible(true); 
            ret = false;
        }

        // Validation of the name field
        if (textFieldName.getText().length() >= 3
                && textFieldName.getText().length() < 64) {

            ret = true;
        } else {
            labelNameNotValid.setVisible(true);
            ret = false;
        }
        
        // Validation of the email field
        if (textFieldEmail.getText().length() >= 10
                && textFieldEmail.getText().length() < 128
                && textFieldEmail.getText().
                        matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            ret = true;
        } else {
            labelEmailNotValid.setVisible(true);
            ret = false;
        }
        
        return ret;
    }
    
    private void handleChangePasswordButtonAction(ActionEvent event) {
        Boolean checkedSyntax = true;
        
        if (passwordFieldNewPassword.getText().length() >= 3
                && passwordFieldNewPassword.getText().length() < 18
                && passwordFieldNewPassword.getText().matches("[a-zA-Z0-9\\.\\-\\*]+")) {

            labelNewPasswordNotValid.setVisible(false);
        } else if (!passwordFieldNewPassword.getText().equals("")) {
            labelNewPasswordNotValid.setText("*Password is not valid");
            labelNewPasswordNotValid.setVisible(true);
            checkedSyntax = false;
        }
        if (passwordFieldRepeatPassword.getText().length() >= 3
                && passwordFieldRepeatPassword.getText().length() < 18
                && passwordFieldRepeatPassword.getText().matches("[a-zA-Z0-9\\.\\-\\*]+")) {

            labelRepeatPasswordNotValid.setVisible(false);
        } else if (!passwordFieldRepeatPassword.getText().equals("") && !passwordFieldNewPassword.getText().equals(passwordFieldRepeatPassword.getText())) {
            labelNewPasswordNotValid.setText("*Passwords do not match");
            labelNewPasswordNotValid.setVisible(true);
            checkedSyntax = false;
        }
        
        if (checkedSyntax == true) {
            try {
                String password = DatatypeConverter.printHexBinary(ClientCipher.cipherText(passwordFieldNewPassword.getText().getBytes()));
                USERCLIENT.modifyPassword(user, password);
                LOGGER.warning("Password changed successfully.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password changed successfully.", ButtonType.OK);
                alert.showAndWait();
            } catch (InternalServerErrorException exception) {
                LOGGER.warning("There was an error trying to connect to the server. " + exception.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING, "There was an error trying to connect to the server.\nPlease try again later.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Checks if the data of the user has been modified
     * @return true if modified, otherwise false
     */
    private boolean userDataIsModified(){
        boolean ret = false;
        
        if(!textFieldLogin.getText().equalsIgnoreCase(user.getLogin()) 
                || !textFieldName.getText().equalsIgnoreCase(user.getFullName())
                || !textFieldEmail.getText().equalsIgnoreCase(user.getEmail())) {
            ret = true;
        }
        
        return ret;
    }
}
