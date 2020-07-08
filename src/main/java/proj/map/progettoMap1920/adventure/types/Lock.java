/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.types;

import java.io.Serializable;

/**
 *
 * @author whyno
 */
public class Lock extends GameElement implements Serializable {
  private AdvObject key;
  private boolean locked = true;

  // Costruttore
  public Lock(int id, AdvObject key) {
    super(id);
    this.key = key;
  }

  // getters

  @Override
  public int getId() {
    return id;
  }

  public AdvObject getKey() {
    return key;
  }

  public boolean isLocked() {
    return locked;
  }
  // setter

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
