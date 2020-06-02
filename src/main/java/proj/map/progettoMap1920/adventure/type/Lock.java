/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

/**
 *
 * @author whyno
 */
public class Lock {
    private int id;
    private AdvObject key;
    private boolean locked;
    //Costruttore
    public Lock(int id, AdvObject key, boolean locked) {
        this.id = id;
        this.key = key;
        this.locked = locked;
    }
    //getters

    public int getId() {
        return id;
    }

    public AdvObject getKey() {
        return key;
    }

    public boolean isLocked() {
        return locked;
    }
    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(AdvObject key) {
        this.key = key;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    
}
