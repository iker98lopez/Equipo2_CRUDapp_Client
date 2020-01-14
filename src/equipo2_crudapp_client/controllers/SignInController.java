/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Diego Corral
 */
public class SignInController {
    
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.SignInController");
    
    private Stage stage;
    
    private boolean checkedSyntax;
    
    @FXML
    private Label labelLoginWarning;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private Label labelPasswordWarning;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private TextField textFieldPasswordShow;
    @FXML
    private CheckBox checkBoxShowPassword;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Hyperlink hyperLinkSignUp;
    @FXML
    private Button buttonExit;
    @FXML
    private Hyperlink hyperLinkForgotPassword;
    
    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign in");
        stage.show();

        labelLoginWarning.setVisible(false);
        labelPasswordWarning.setVisible(false);
        textFieldPasswordShow.setVisible(false);

        checkBoxShowPassword.setOnAction(this::handleCheckBoxShowPasswordAction);
        buttonSignIn.setOnAction(this::handleButtonSignInAction);
        hyperLinkSignUp.setOnAction(this::handleHyperlinkSignUpAction);
        buttonExit.setOnAction(this::handleButtonExitAction);

        textFieldLogin.focusedProperty().addListener(this::focusChanged);
        textFieldPassword.focusedProperty().addListener(this::focusChanged);
        textFieldPasswordShow.focusedProperty().addListener(this::focusChanged);
    }
    
    /**
     * Event for buttonSignIn. It is called when the button is clicked and it
     * sends the credentials entered by the user to the server through the
     * client function login().
     *
     * @param event Event to be handled by this method.
     */
    public void handleButtonSignInAction(ActionEvent event){
        
        syntaxCheck();
        
        textFieldLogin.setText(textFieldLogin.getText().trim());
        textFieldPassword.setText(textFieldPassword.getText().trim());
        textFieldPasswordShow.setText(textFieldPasswordShow.getText().trim());
        
        if (textFieldLogin.getText().equals("")) {
            checkedSyntax = false;
            labelLoginWarning.setVisible(true);
            labelLoginWarning.setText("*This field is empty");
        }
        if (textFieldPassword.getText().equals("")) {
            checkedSyntax = false;
            labelPasswordWarning.setVisible(true);
            labelPasswordWarning.setText("*This field is empty");
        }
        
        /*if (checkedSyntax) {
            User user = new User(textFieldLogin.getText().trim(), textFieldPassword.getText().trim());
            try {
                user = CLIENT.signIn(user);

                FXMLLoader loader;
                Parent root;
                loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/MainView.fxml"));
                root = (Parent) loader.load();
                MainViewController controller = ((MainViewController) loader.getController());
                controller.setUser(user);
                controller.initStage(root);
                stage.hide();
                
            } catch (ServerException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error connecting to the server.\nPlease try again later.", ButtonType.OK);
                alert.showAndWait();
                
                textFieldPassword.setText("");
                textFieldPasswordShow.setText("");
                LOGGER.warning("There was an error connecting to the server.");
            } catch (UserDisabledException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The user trying to log in has been disabled.", ButtonType.OK);
                alert.showAndWait();
                
                labelLoginWarning.setVisible(true);
                
                textFieldPassword.setText("");
                textFieldPasswordShow.setText("");
                LOGGER.warning("The user has been disabled.");
            } catch (IncorrectUserException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The user is not correct.\nPlease try again.", ButtonType.OK);
                alert.showAndWait();
                
                labelLoginWarning.setVisible(true);
                
                textFieldPassword.setText("");
                textFieldPasswordShow.setText("");
                LOGGER.warning("The user is not correct.");
            } catch (IncorrectPasswordException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The password is not correct.\nPlease try again.", ButtonType.OK);
                alert.showAndWait();
                
                labelPasswordWarning.setVisible(true);
                
                textFieldPassword.setText("");
                textFieldPasswordShow.setText("");
                LOGGER.warning("The password is not correct.");
            } catch (IOException ex) {
                LOGGER.warning("There was an error trying to open LogOutView");
            }
        }*/
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/MainView.fxml"));
            Parent root = (Parent) loader.load();
            MainViewController controller = ((MainViewController) loader.getController());
            //controller.setUser(user);
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        
    }
    
    /**
     * Event for hyperlinkSignUp. It is called when the hyperlink is clicked and
     * it sends the user to the window SignUpView.
     *
     * @param event Event to be handled by this method.
     */
    public void handleHyperlinkSignUpAction(ActionEvent event) {

        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupsigninapp/views/SignUpView.fxml"));
            Parent root = (Parent) loader.load();
            SignUpController controller = ((SignUpController) loader.getController());
            controller.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.warning("There was an error trying to open SignUpView");
        }*/
    }
    
    /**
     * Event for checkBoxShowPassword. It is called when the state of the
     * checkbox changes to checked or unchecked and it makes the password field
     * visible or invisible.
     *
     * @param event Event to be handled by this method.
     */
    public void handleCheckBoxShowPasswordAction(ActionEvent event) {
        if (checkBoxShowPassword.isSelected()) {
            textFieldPassword.setVisible(false);
            textFieldPasswordShow.setVisible(true);

            textFieldPasswordShow.setText(textFieldPassword.getText());
        } else {
            textFieldPassword.setVisible(true);
            textFieldPasswordShow.setVisible(false);

            textFieldPassword.setText(textFieldPasswordShow.getText());
        }
    }
    
    public void handleHyperlinkForgotPassword(ActionEvent event){
        
    }
    
    /**
     * Event for buttonExit. It si called when the button is clicked or when ESC
     * is pressed and it closes the application.
     * 
     * @param event Event to be handled by this method.
     */
    public void handleButtonExitAction(ActionEvent event) {
        stage.hide();
    }
    
    /**
     * This method checks the syntax of the field received.
     *
     * @param object Textfield or PasswordField to be checked
     * @param code Number 0-3 to signal which kind of field has been sent
     * @return True if the syntax is correct, False otherwise
     */
    private void syntaxCheck() {

        checkedSyntax = true;

        if (textFieldPassword.isVisible()) {
            textFieldPasswordShow.setText(textFieldPassword.getText());
        } else {
            textFieldPassword.setText(textFieldPasswordShow.getText());
        }

        if (textFieldLogin.getText().length() >= 3
                && textFieldLogin.getText().length() < 18
                && textFieldLogin.getText().matches("[a-zA-Z0-9\\.\\-\\*]+")) {

            labelLoginWarning.setVisible(false);
        } else if (!textFieldLogin.getText().equals("")) {
            labelLoginWarning.setVisible(true);
            labelLoginWarning.setText("*Login is not valid");
            checkedSyntax = false;
        }

        if (textFieldPassword.getText().length() >= 3
                && textFieldPassword.getText().length() < 18
                && textFieldPassword.getText().matches("[a-zA-Z0-9\\.\\-\\*]+")) {

            labelPasswordWarning.setVisible(false);
        } else if (!textFieldPassword.getText().equals("")) {
            labelPasswordWarning.setVisible(true);
            labelPasswordWarning.setText("*Password is not valid");
            checkedSyntax = false;
        }
    }
    
    /**
     * Event for focus changes.
     * 
     * @param observable
     * @param oldValue
     * @param newValue if false means out of focus.
     */
    private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            textFieldLogin.setText(textFieldLogin.getText().trim());
            textFieldPassword.setText(textFieldPassword.getText().trim());
            textFieldPasswordShow.setText(textFieldPasswordShow.getText().trim());

            syntaxCheck();
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
    
}
