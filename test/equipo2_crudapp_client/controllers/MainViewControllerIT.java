/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Test class for the Main view
 * @author Diego Corral
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainViewControllerIT extends ApplicationTest{
    
    /**
     * Starts application
     * @param stage stage of the view
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainView.fxml"));
        Parent root = (Parent) loader.load();
        MainViewController controller = ((MainViewController) loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
    }
    
    /**
     * Stops the application to be tested
     */
    @Override
    public void stop() {}
    
    /**
     * Test initial state of the view elements
     */
    @Test
    public void test_1_InitialState() {
        verifyThat("#labelUsername", hasText("test"));
        verifyThat("#textFieldSearch", hasText(""));
    }
    
    /**
     * Test that the search button is enabled
     */
    @Test
    public void test_2_SearchButtonIsEnabled() {
        verifyThat("#buttonSearch", isEnabled());
    }
    
    /**
     * Test that the results view is opened when pressing the search button
     */
    @Test
    public void test_3_ResultsViewIsOpened() {
        clickOn("#textFieldSearch");
        write("a");
        verifyThat("")
        
    }
}
