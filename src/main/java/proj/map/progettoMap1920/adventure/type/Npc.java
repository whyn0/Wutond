/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;

/**
 *
 * @author whyno
 */
public class Npc {


	

	private String id;
	private String name;
	private String look;
	private boolean understandable;
	private boolean spoken;
	private boolean killable;
	private List<Dialog> dialogs_list= new ArrayList<Dialog>();




/*
 * 
 * COSTRUTTORI
 * 
 */
	public Npc(String id) {
		super();
		this.id = id;
	}



	public Npc(String id, String name, String look, boolean understandable, boolean spoken, boolean killable,
			ArrayList<Dialog> dialogs_list) {
		super();
		this.id = id;
		this.name = name;
		this.look = look;
		this.understandable = understandable;
		this.spoken = spoken;
		this.killable = killable;
		this.dialogs_list = dialogs_list;
	}


/*
 * 
 * GETTERS
 * 
 */
	public String getId() {
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



	public List<Dialog> getDialogs_list() {
		return dialogs_list;
	}


/*
 * 
 * SETTERS
 * 
 */
	public void setId(String id) {
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



	public void setDialogs_list(List<Dialog> dialogs_list) {
		this.dialogs_list = dialogs_list;
	}


/*
 * 
 * METHODS
 * 
 */
	public boolean add(Dialog d) {
		return dialogs_list.add(d);
	}



	public boolean remove(Dialog d) {
		return dialogs_list.remove(d);
	}



	








}
