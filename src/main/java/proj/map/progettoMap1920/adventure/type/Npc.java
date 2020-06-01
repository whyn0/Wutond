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
public class Npc implements Serializable{




	private int id;
	private String name;
  private String description;
	private String look;
	private boolean understandable;
	private boolean spoken;
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
		this.spoken = spoken;
		this.killable = killable;
		this.dialogs_list = dialogs_list;
		this.npc_inventory_list = npc_inventory_list;
	}



	public Npc(int id, String name, String look, boolean understandable, boolean spoken, boolean killable,
			List<Dialog> dialogs_list) {
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



	public List<Dialog> getDialogs_list() {
		return dialogs_list;
	}



	public List<AdvObject> getNpc_inventory_list() {
		return npc_inventory_list;
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



	public void setDialogs_list(List<Dialog> dialogs_list) {
		this.dialogs_list = dialogs_list;
	}




	public void setNpc_inventory_list(List<AdvObject> npc_inventory_list) {
		this.npc_inventory_list = npc_inventory_list;
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



	public boolean add(AdvObject e) {
		return npc_inventory_list.add(e);
	}



	public boolean remove(AdvObject e) {
		return npc_inventory_list.remove(e);
	}


















}
