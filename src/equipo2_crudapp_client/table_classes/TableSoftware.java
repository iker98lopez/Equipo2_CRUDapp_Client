/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.table_classes;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author leioa
 */
public class TableSoftware {
    private final SimpleStringProperty name;
    
    
    public TableSoftware() {
        name = null;
    }
    public TableSoftware(String name) {
        this.name = new SimpleStringProperty(name);
    }
    
    
    public String getName() {
        return this.name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public SimpleStringProperty getNameProperty () {
        return name;
    }
    
}
