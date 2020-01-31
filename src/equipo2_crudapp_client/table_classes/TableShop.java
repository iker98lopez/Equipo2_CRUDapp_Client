/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.table_classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class representing a shop to be able to enter its name in a table.
 *
 * @author iker lopez carrillo
 */
public class TableShop {

    /**
     * The name of the shop
     */
    private final SimpleStringProperty name;

    /**
     * No-args constructor.
     */
    public TableShop() {
        name = null;
    }

    /**
     * Constructor that receives a String as an argument.
     *
     * @param name String with the name of the shop.
     */
    public TableShop(String name) {
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Returns the name of the shop.
     *
     * @return name of the shop.
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Sets the name of the shop.
     *
     * @param name String with the name of the shop.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns a SimpleStringProperty representing the name of the shop.
     *
     * @return SimpleStringProperty with the name of the shop.
     */
    public SimpleStringProperty getNameProperty() {
        return name;
    }
}
