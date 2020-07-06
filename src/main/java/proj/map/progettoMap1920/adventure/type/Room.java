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
public class Room extends GameObject implements Serializable {
  private Room south = null;
  private Room north = null;
  private Room east = null;
  private Room west = null;
  private List<AdvObject> objects_list = new ArrayList<>();
  private List<Npc> npc_list = new ArrayList<>();

  /*
   * 
   * 
   * COSTRUTTORI
   * 
   * 
   */

  public Room(int id, String name, String description, String look, Set<String> alias) {
    super(id, name, description, look, alias);
  }

  /*
   * 
   * 
   * GETTERS
   * 
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
  public String getDescription() {
    return description;
  }

  @Override
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
   * 
   * 
   * SETTER
   * 
   * 
   * 
   */

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setLook(String look) {
    this.look = look;
  }

  public void setEast(Room east) {
    this.east = east;
  }

  public void setNorth(Room north) {
    this.north = north;
  }

  public void setSouth(Room south) {
    this.south = south;
  }

  public void setWest(Room west) {
    this.west = west;
  }

  public void setObjects_list(List<AdvObject> objects_list) {
    this.objects_list = objects_list;
  }

  public void setNpc_list(List<Npc> npc_list) {
    this.npc_list = npc_list;
  }
  /*
   * 
   * 
   * EQUALS AND HASH
   * 
   * 
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
