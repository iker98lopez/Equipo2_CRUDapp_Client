/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Software;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Custom ListView cell adapter for results view
 *
 * @author Adrian García
 */
public class ListViewResultsController extends ListCell<Software> {

    /**
     * HBox from ListViewCellResults
     */
    @FXML
    private HBox hBox;
    /**
     * Image from software
     */
    @FXML
    private ImageView imageView;
    /**
     * Software name
     */
    @FXML
    private Label labelName;

    @Override
    protected void updateItem(Software software, boolean empty) {
        super.updateItem(software, empty);
        if (empty || software == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/ListViewCellResults.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            labelName.setText(software.getName());
            File file = new File("src/foto.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            setText(null);
            setGraphic(hBox);
        }

    }

}
