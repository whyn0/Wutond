/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author whyno
 */
public class Room {
    private final int id;
    
    private String name;

    private String description;

    private String look;

    private final Room south = null;

    private final Room north = null;

    private final Room east = null;

    private final Room west = null;
    
    private final List<AdvObject> objects_list=new ArrayList<>();
    
    private final List<Npc> npc_list = new ArrayList<>();
    /*
    
    
    COSTRUTTORI
    
    
    */
    public Room(int id) {
        this.id = id;
    }

    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    /*
    
    
    GETTERS
    
    
    */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLook() {
        return look;
    }

    public Room getSouth() {
        return south;
    }

    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public List<AdvObject> getObjects_list() {
        return objects_list;
    }

    public List<Npc> getNpc_list() {
        return npc_list;
    }
    /*
    
    
    SETTER
    
    
    
    */

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLook(String look) {
        this.look = look;
    }
    /*
    
    
    EQUALS AND HASH
    
    
    */

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
