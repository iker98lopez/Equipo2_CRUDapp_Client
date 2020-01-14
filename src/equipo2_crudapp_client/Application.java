/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client;

import equipo2_crudapp_client.clients.UserClient;
import equipo2_crudapp_client.controllers.MainViewController;
import equipo2_crudapp_client.controllers.SignInController;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Diego Corral
 * 
 */
public class Application extends javafx.application.Application{
    
    public static void main(String[] args) {
        
        launch(args);
        //UserClient userClient = new UserClient();
        //Object response = userClient.findAllUsers();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/SignInView.fxml"));
        Parent root = (Parent) loader.load();
        SignInController controller = ((SignInController) loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);   
    }
}
