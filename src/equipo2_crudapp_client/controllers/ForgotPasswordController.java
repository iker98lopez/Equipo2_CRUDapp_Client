/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_client.clients.UserClient;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

/**
 * Controller for the FogotPassword view
 * @author Diego Corral
 */
public class ForgotPasswordController {
    
    /**
     * Stage of the controller
     */
    private Stage stage;
    
    /**
     * Scene of the controller
     */
    private Scene scene;
    
    private User user;
    
    /**
     * Client for communicatng with the server
     */
    private static final UserClient USERCLIENT = new UserClient();
    
    
    /**
     * Logger for SignInViewController class
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ForgotPasswordController");
    
    
    /**
     * Goes to the next step. In this case to the introduction of the code sent 
     * to the user's email
     */
    @FXML
    private Button buttonEmailNext;
    
    /**
     * Goes to the next step. In this case changing the password
     */
    @FXML
    private Button buttonCodeNext;
    
    /**
     * TextField for introducing the user's email account
     */
    @FXML
    private TextField textFieldEmail;
    
    /**
     * TextField for introducing the code sent to the user's email account
     */
    @FXML
    private TextField textFieldCode;
    
    /**
     * The label of the email TextField
     */
    @FXML
    private Label labelEmail;
    
    /**
     * The label of the email TextField
     */
    @FXML
    private Label labelCode;
    
    /**
     * Warning. Email is not valid
     */
    @FXML
    private Label labelEmailNotValid;
    
    /** 
     * Warning. Code is not valid
     */
    @FXML
    private Label labelCodeNotValid;

    /**
     * This method sets the stage
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Forgot password");
        stage.show();
        
        textFieldCode.setDisable(true);
        labelCode.setDisable(true);
        buttonCodeNext.setDisable(true);
        labelCodeNotValid.setVisible(false);
        labelEmailNotValid.setVisible(false);
        
        buttonEmailNext.setOnAction(this::handleButtonEmailNextAction);
        buttonCodeNext.setOnAction(this::handleButtonCodeNextAction);
    }
    
    /**
     * Handles the emails next button action
     * @param event 
     */
    private void handleButtonEmailNextAction(ActionEvent event) {
        if(emailSyntaxIsCorrect()) {
            
            // Send email with recuperation code
            try {
                user = USERCLIENT.findUserByEmail(User.class, textFieldEmail.getText());
                USERCLIENT.getRecoveryCode(textFieldEmail.getText());
            } catch (NotFoundException | InternalServerErrorException exception) {
                LOGGER.warning("There is no user with email: " +  ". " + exception.getMessage());
            } catch(Exception e) {
                LOGGER.severe(e.getMessage());
            }
                
            textFieldEmail.setDisable(true);
            labelEmail.setDisable(true);
            buttonEmailNext.setDisable(true);
            
            textFieldCode.setDisable(false);
            labelCode.setDisable(false);
            buttonCodeNext.setDisable(false);
        }
    }
    
    /**
     * Handles the code next button action
     * @param event 
     */
    private void handleButtonCodeNextAction(ActionEvent event) {
        String password = null;
        String repeatedPassword = null;
        
        if(codeSyntaxIsCorrect()) {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Password recovery");
            textInputDialog.setHeaderText(null);
            textInputDialog.setContentText("Enter new password:");
            Optional<String> result = textInputDialog.showAndWait();

            if (result.isPresent()){
                password = textInputDialog.getEditor().getText();

            }
            
            textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Password recovery");
            textInputDialog.setHeaderText(null);
            textInputDialog.setContentText("Repeat password:");
            result = textInputDialog.showAndWait();

            if (result.isPresent()) {
                repeatedPassword = textInputDialog.getEditor().getText();
            }
            
            if(!password.isEmpty() && password.equalsIgnoreCase(repeatedPassword)) {
                
                USERCLIENT.modifyPassword(user, password);
                
            }
        }
    }
    
    /**
     * Checks the syntax of the email field
     * @return true if corret, otherwise false.
     */
    private boolean emailSyntaxIsCorrect() {
        boolean ret = true;
        
        if (textFieldEmail.getText().length() >= 10
                && textFieldEmail.getText().length() < 128
                && textFieldEmail.getText().
                        matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            ret = true;
            labelEmailNotValid.setVisible(false);
        } else {
            labelEmailNotValid.setVisible(true);
            ret = false;
        }
        
        return ret;
    }
    
    /**
     * Checks if the syntax of the code is correct
     * @return treu if correct, otherwise false
     */
    private boolean codeSyntaxIsCorrect() {
        boolean ret = true;
        
        if(textFieldCode.getText().length() != 6) {
            ret = false;
            labelCodeNotValid.setVisible(true);
        } else {
            labelCodeNotValid.setVisible(false);
            ret = true;
        }
        
        return ret;
    }
}
