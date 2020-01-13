/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Iker Lopez
 */
public class MyOffersViewController {
    
    @FXML
    private TableView tableOffers;
    
    @FXML
    private TableColumn tableColumnSoftware;
    
    @FXML
    private TableColumn tableColumnBasePrice;
    
    @FXML
    private TableColumn tableColumnDiscountedPrice;
    
    @FXML
    private TableColumn tableColumnDiscount;
    
    @FXML
    private TableColumn tableColumnShop;
    
    @FXML
    private TableColumn tableColumnUrl;
    
    public void initStage (Stage stage) {
        
    }
}
