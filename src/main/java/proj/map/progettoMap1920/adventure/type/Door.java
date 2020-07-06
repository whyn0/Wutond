/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.Set;

/**
 *
 * @author whyno
 */
public class Door extends AdvObject {
  private Lock lock;
  private Room lockedRoom;
  private boolean openable = true;
  /*
   * 
   * COSTRUTTORI
   * 
   */

  public Door(Lock lock, Room lockedRoom, int id, String name, String description, String look, Set<String> alias, boolean pickable) {
    super(id, name, description, look, alias, pickable);
    this.lock = lock;
    this.lockedRoom = lockedRoom;
  }

  /*
   * 
   * GETTER
   * 
   */
  public Lock getLock() {
    return lock;
  }

  public Room getLockedRoom() {
    return lockedRoom;
  }

  public boolean isOpenable() {
    return openable;
  }

  /*
   * 
   * SETTER
   * 
   */
  public void setLock(Lock lock) {
    this.lock = lock;
  }

  public void setLockedRoom(Room lockedRoom) {
    this.lockedRoom = lockedRoom;
  }

}
