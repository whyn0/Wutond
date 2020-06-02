/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;

/**
 *
 * @author whyno
 */
public class Npc implements Serializable {

    private int id;
    private String name;
    private String description;
    private String look;
    private boolean understandable;
    private boolean spoken = false;
    private boolean killable;

    private Dialog dialog;
    private List<AdvObject> npc_inventory;

    /*
	 * 
	 * COSTRUTTORI
	 * 
     */
    public Npc(int id) {
        super();
        this.id = id;
    }

    public Npc(int id, String name, String look, boolean understandable, boolean spoken, boolean killable,
            ArrayList<Dialog> dialogs_list, ArrayList<AdvObject> npc_inventory_list) {
        super();
        this.id = id;
        this.name = name;
        this.look = look;
        this.understandable = understandable;
        this.killable = killable;

    }

    /*
	 * 
	 * GETTERS
	 * 
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLook() {
        return look;
    }

    public boolean isUnderstandable() {
        return understandable;
    }

    public boolean isSpoken() {
        return spoken;
    }

    public boolean isKillable() {
        return killable;
    }

    /*
	 * 
	 * SETTERS
	 * 
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public void setUnderstandable(boolean understandable) {
        this.understandable = understandable;
    }

    public void setSpoken(boolean spoken) {
        this.spoken = spoken;
    }

    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    /*
	 * 
	 * METHODS
	 * 
     */
}
