/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client;

import equipo2_crudapp_client.clients.UserClient;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Diego Corral
 * 
 */
public class Application extends javafx.application.Application{
    
    public static void main(String[] args) {
        //launch(args);
        UserClient userClient = new UserClient();
        //Object response = userClient.findAllUsers();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
