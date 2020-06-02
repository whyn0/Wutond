/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.List;

/**
 *
 * @author whyno
 */
public class Inventory {
    private List<AdvObject> inventory;

    public Inventory(List<AdvObject> inventory) {
        this.inventory = inventory;
    }

    public List<AdvObject> getInventory() {
        return inventory;
    }

    public void setInventory(List<AdvObject> inventory) {
        this.inventory = inventory;
    }
    
}
