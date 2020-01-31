/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.controllers;

import equipo2_crudapp_client.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


/**
 * Testing class Main view
 * @author Diego Corral
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainViewControllerIT extends ApplicationTest{
    
    /**
     * Starts the test
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception{
        new Application().start(stage);
    }
    
    /**
     * Stop. Does nothing.
     */
    @Override 
    public void stop() {}
    
    /**
     * Set the view
     */
    @Before
    public void setUp() {
        clickOn("#textFieldLogin");
        write("test");
        clickOn("#textFieldPassword");
        write("test");
        clickOn("#buttonSignIn");
        clickOn("#buttonOk");
    }
    
    /**
     * Test the initial values of the view
     */
    @Test
    public void test_1_InitialState() {
        verifyThat("#textFieldSearch", isVisible());
        verifyThat("#buttonSearch", isVisible());
        
    }
    
    /**
     * Test that the search button sends the user to results view
     */
    @Test
    public void test_2_search() {
        clickOn("#textFieldSearch");
        write("example");
        clickOn("#buttonSearch");
        verifyThat("#listViewResults", isVisible());
    }
    
}
