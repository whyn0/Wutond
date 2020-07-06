/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class Npc extends GameObject implements Serializable {
  private boolean understandable;
  private boolean spoken = false;
  private boolean killable;

  private Dialog dialog = null;
  private List<AdvObject> npc_inventory = null;

  /*
   * 
   * COSTRUTTORI
   * 
   */
  public Npc(int id, String name, String look, String description, Set<String> alias, boolean understandable, boolean killable) {
    super(id, name, look, description, alias);
    this.understandable = understandable;
    this.killable = killable;

  }

  /*
   * 
   * GETTERS
   * 
   */
  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
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

  public List<AdvObject> getNpc_inventory() {
    return npc_inventory;
  }

  public void setNpc_inventory(List<AdvObject> npc_inventory) {
    this.npc_inventory = npc_inventory;
  }

  public Dialog getDialog() {
    return dialog;
  }

  /*
   * 
   * SETTERS
   * 
   */
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
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

  public void setDialog(Dialog dialog) {
    this.dialog = dialog;
  }

  public void setInventory(List<AdvObject> list) {
    this.npc_inventory = list;
  }
  /*
   * 
   * METHODS
   * 
   */
}
