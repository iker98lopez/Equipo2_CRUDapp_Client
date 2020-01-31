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
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Testing class User view
 * @author Diego Corral
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserViewControllerIT extends ApplicationTest{
    
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
        clickOn("#buttonViewUser");
    }
    
    @Test
    public void test_1_initialState() {
        verifyThat("#textFieldLogin", isDisabled());
        verifyThat("#textFieldName", isDisabled());
        verifyThat("#textFieldEmail", isDisabled());
    }
    
    @Test
    public void test_2_editButton() {
        clickOn("#toggleButtonEdit");
        verifyThat("#textFieldLogin", isEnabled());
        verifyThat("#textFieldName", isEnabled());
        verifyThat("#textFieldEmail", isEnabled());
    }

    
}
