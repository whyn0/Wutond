/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class AdvObject extends GameObject implements Serializable {
  /**
  * 
  */

  private boolean pickable;

  /*
   * 
   * COSTRUTTORI
   * 
   */
  public AdvObject(int id, String name, String description, String look, Set<String> alias, boolean pickable) {
    super(id, name, description, look, alias);
    this.pickable = pickable;
  }
  /*
   *
   * GETTERS
   * 
   */


  public boolean isPickable() {
    return pickable;
  }

  /*
   *
   * SETTERS
   * 
   */

  public void setPickable(boolean pickable) {
    this.pickable = pickable;
  }

  /*
   * EQUALS E HASHCODE
   */

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 89 * hash + this.getId();
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
