/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_client.clients.UserClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    /**
     * Client for the communication with the server
     */
    private static final UserClient USER_CLIENT = new UserClient();
    
    /**
     * The recovery code sent to the user's email account
     */
    private String recoveryCode;
    
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
            
            recoveryCode = USER_CLIENT.getRecoveryCode(textFieldEmail.getText());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "We've sent a "
                    + "recuperation code to your email account", ButtonType.OK);
            alert.showAndWait();
            
            // Send email with recuperation code
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
        if(codeSyntaxIsCorrect()) {
            
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
