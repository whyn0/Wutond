/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;


import java.util.Set;
import java.io.*;

/**
 *
 * @author whyno
 */
public class AdvObject implements Serializable {

    private final int id;
    private String name;
    private String description;
    private Set<String> alias;
    private boolean pickable;


    /*
     * 
     * COSTRUTTORI
     * 
     */
    public AdvObject(int id, String name, String description, Set<String> alias, boolean pickable) {    
        this.id = id;
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.pickable = pickable;
    }
    /*
     *
     *GETTERS
     * 
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

    public Set<String> getAlias() {
        return alias;
    }

    
    public boolean isPickable() {    
        return pickable;
    }

    /*
     *
     * SETTERS
     * 
     */

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }
    /*
    EQUALS E HASHCODE
    */

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
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
        final AdvObject other = (AdvObject) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
