package equipo2_crudapp_client.controllers;

import equipo2_crudapp_ciphering.ClientCipher;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_client.clients.UserClient;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.ClientErrorException;
import javax.xml.bind.DatatypeConverter;

/**
 * This class controls the SignUpView
 *
 * @author Adrián García
 */
public class SignUpViewController {

    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.SignUpController");
    private static final UserClient CLIENT = new UserClient();
    
    /**
     * Stage of the controller
     */
    private Stage stage;
    /**
     * Modification Adrián García 14/11/2019
     */
    private Scene scene;
    /**
     * Attribute that represents if there's and error on user input
     */
    private boolean checkedSyntax;
    /**
     * Label that shows when there's an error on Login field
     */
    @FXML
    private Label labelLoginWarning;
    /**
     * TextField where users input their username
     */
    @FXML
    private TextField textFieldLogin;
    /**
     * Label that shows when there's an error on Password field
     */
    @FXML
    private Label labelPasswordWarning;
    /**
     * Field where users input their password
     */
    @FXML
    private PasswordField textFieldPassword;
    /**
     * Field used to input password when "Show password" is selected
     */
    @FXML
    private TextField textFieldPasswordShow;
    /**
     * Label that shows when there's an error on Password Repeat field
     */
    @FXML
    private Label labelPasswordRepeatWarning;
    /**
     * Field to confirm the password introduced by repeating it
     */
    @FXML
    private PasswordField textFieldPasswordRepeat;
    /**
     * Checkbox to alternate between make password readable or not
     */
    @FXML
    private CheckBox checkBoxShowPassword;
    /**
     * Button that goes backward to SignIn
     */
    @FXML
    private Button buttonBack;
    /**
     * Button that sends all information to server to complete the sign up
     */
    @FXML
    private Button buttonSignUp;
    /**
     * Label that shows when there's an error on Full Name field
     */
    @FXML
    private Label labelFullNameWarning;
    /**
     * Field where users input their full name
     */
    @FXML
    private TextField textFieldFullName;
    /**
     * Label that shows when there's an error on Email field
     */
    @FXML
    private Label labelEmailWarning;
    /**
     * Field where users input their email
     */
    @FXML
    private TextField textFieldEmail;
    
    
    /**
     * Default constructor for SignUpController
     */
    public SignUpViewController() {
    }
    
    
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
        stage.setTitle("Sign Up");

        labelLoginWarning.setVisible(false);
        labelPasswordWarning.setVisible(false);
        labelEmailWarning.setVisible(false);
        labelFullNameWarning.setVisible(false);
        labelPasswordRepeatWarning.setVisible(false);

        textFieldPasswordShow.setVisible(false);
        textFieldPassword.setVisible(true);

        textFieldLogin.focusedProperty().addListener(this::focusChanged);
        textFieldFullName.focusedProperty().addListener(this::focusChanged);
        textFieldPassword.focusedProperty().addListener(this::focusChanged);
        textFieldPasswordRepeat.focusedProperty().addListener(this::focusChanged);
        textFieldEmail.focusedProperty().addListener(this::focusChanged);
        
        /**
         * Modification Adrián García 14/11/2019
         */
        textFieldLogin.textProperty().addListener(this::checkUserChangedWrongText);
        textFieldFullName.textProperty().addListener(this::checkUserChangedWrongText);
        textFieldEmail.textProperty().addListener(this::checkUserChangedWrongText);
        textFieldPassword.textProperty().addListener(this::checkUserChangedWrongText);
        textFieldPasswordRepeat.textProperty().addListener(this::checkUserChangedWrongText);

        checkBoxShowPassword.setOnAction(this::handleCheckBoxShowPasswordAction);
        buttonBack.setOnAction(this::handleButtonAction);
        buttonSignUp.setOnAction(this::handleButtonAction);

        stage.show();
    }

    /**
     * Event handler for buttons in the SignUp
     *
     * @param e Event to be handled
     */
    public void handleButtonAction(ActionEvent e) {

        if (e.getSource() == buttonBack) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/SignInView.fxml"));
                Parent root = (Parent) loader.load();
                SignInViewController controller = ((SignInViewController) loader.getController());
                controller.setStage(new Stage());
                controller.initStage(root);
                stage.hide();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error opening the Sign In window.", ButtonType.OK);
                alert.showAndWait();

                LOGGER.info("There was an error from server side.");
                LOGGER.warning("There was an error trying to open SignInView.");
            }

        } else if (e.getSource() == buttonSignUp) {
            syntaxCheck();
            emptyFieldsCheck();
            /**
             * Modification Adrián García 15/11/2019
             */
            if (labelLoginWarning.isVisible())
                textFieldLogin.requestFocus();
            
            else if (labelFullNameWarning.isVisible())
                textFieldFullName.requestFocus();
            
            else if (labelEmailWarning.isVisible())
                textFieldEmail.requestFocus();
            
            else if (labelPasswordWarning.isVisible())
                textFieldPassword.requestFocus();
            
            else if (labelPasswordRepeatWarning.isVisible())
                textFieldPasswordRepeat.requestFocus();
            
            if (checkedSyntax) {
                String login = textFieldLogin.getText();
                String password = DatatypeConverter.printHexBinary(ClientCipher.cipherText(textFieldPassword.getText().getBytes()));
                String email = textFieldEmail.getText();
                String fullName = textFieldFullName.getText();
                User user = new User(login, password, fullName, email);

                try {
                    CLIENT.createUser(user);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User sign up completed successfully", ButtonType.OK);
                    alert.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/SignInView.fxml"));
                    Parent root = (Parent) loader.load();
                    SignInViewController controller = ((SignInViewController) loader.getController());
                    controller.setStage(new Stage());
                    controller.initStage(root);
                    stage.hide();
                } catch (ClientErrorException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error on Client side.\nPlease try again later.", ButtonType.OK);
                    alert.showAndWait();

                    LOGGER.info("There was an error from server side.");
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error opening the Sign In window.", ButtonType.OK);
                    alert.showAndWait();

                    LOGGER.info("There was an error from server side.");
                    LOGGER.warning("There was an error trying to open SignInView.");
                }
            }
        }
    }

    /**
     * This method sets the stage
     *
     * @param stage Stage to be setted
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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

    /**
     * This method checks the syntax of the fields.
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
            labelLoginWarning.setText("*Login is not valid");
            labelLoginWarning.setVisible(true);
            checkedSyntax = false;
        }
        if (textFieldPassword.getText().length() >= 3
                && textFieldPassword.getText().length() < 18
                && textFieldPassword.getText().matches("[a-zA-Z0-9\\.\\-\\*]+")) {

            labelPasswordWarning.setVisible(false);
        } else if (!textFieldPassword.getText().equals("")) {
            labelPasswordWarning.setText("*Password is not valid");
            labelPasswordWarning.setVisible(true);
            checkedSyntax = false;
        }
        if (textFieldPasswordRepeat.getText().equals(textFieldPassword.getText())
                && !textFieldPassword.getText().equals("")) {
            labelPasswordRepeatWarning.setVisible(false);
        } else if (!textFieldPasswordRepeat.getText().equals("")) {
            labelPasswordRepeatWarning.setText("*Passwords do not match");
            labelPasswordRepeatWarning.setVisible(true);
            checkedSyntax = false;
        }
        if (textFieldEmail.getText().length() >= 10
                && textFieldEmail.getText().length() < 128
                && textFieldEmail.getText().
                        matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            labelEmailWarning.setVisible(false);
        } else if (!textFieldEmail.getText().equals("")) {
            labelEmailWarning.setText("*Email is not valid");
            labelEmailWarning.setVisible(true);
            checkedSyntax = false;
        }
        if (textFieldFullName.getText().length() >= 3 && labelFullNameWarning.getText().length() < 64) {
            labelFullNameWarning.setVisible(false);
        } else if (!textFieldFullName.getText().equals("")) {
            labelFullNameWarning.setText("*Name is not valid");
            labelFullNameWarning.setVisible(true);
            checkedSyntax = false;
        }
    }

    /**
     * This method checks if there's any empty field before sending SingUp
     */
    private void emptyFieldsCheck() {
        final String EMPTY_WARNING = "*This field is empty";

        if (textFieldLogin.getText().equals("")) {
            checkedSyntax = false;
            labelLoginWarning.setText(EMPTY_WARNING);
            labelLoginWarning.setVisible(true);
        }
        if (textFieldFullName.getText().equals("")) {
            checkedSyntax = false;
            labelFullNameWarning.setText(EMPTY_WARNING);
            labelFullNameWarning.setVisible(true);
        }
        if (textFieldEmail.getText().equals("")) {
            checkedSyntax = false;
            labelEmailWarning.setText(EMPTY_WARNING);
            labelEmailWarning.setVisible(true);
        }

        if (textFieldPassword.getText().equals("")) {
            checkedSyntax = false;
            labelPasswordWarning.setText(EMPTY_WARNING);
            labelPasswordWarning.setVisible(true);
        }

        if (textFieldPasswordRepeat.getText().equals("")) {
            checkedSyntax = false;
            labelPasswordRepeatWarning.setText(EMPTY_WARNING);
            labelPasswordRepeatWarning.setVisible(true);
        }
    }

    /**
     * Method that calls syntaxCheck when focus turns out of a field
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void focusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            syntaxCheck();
            
        }

    }

    /**
     * Modification Adrián García 14/11/2019
     */
    private void checkUserChangedWrongText(ObservableValue observable, String oldValue, String newValue) {
        //To set not visible label warning when user is updating a TextField
        TextField focusedTextField = (TextField) scene.focusOwnerProperty().get();
        if (focusedTextField.equals(textFieldLogin)
                && labelLoginWarning.isVisible()) {
            labelLoginWarning.setVisible(false);
            
        } else if (focusedTextField.equals(textFieldFullName)
                && labelFullNameWarning.isVisible()) {
            labelFullNameWarning.setVisible(false);
            
        } else if (focusedTextField.equals(textFieldEmail)
                && labelEmailWarning.isVisible()) {
            labelEmailWarning.setVisible(false);
            
        } else if (focusedTextField.equals(textFieldPassword)
                && labelPasswordWarning.isVisible()) {
            labelPasswordWarning.setVisible(false);
            
        } else if (focusedTextField.equals(textFieldPasswordRepeat)
                && labelPasswordRepeatWarning.isVisible()) {
            labelPasswordRepeatWarning.setVisible(false);
        }
    }

}
