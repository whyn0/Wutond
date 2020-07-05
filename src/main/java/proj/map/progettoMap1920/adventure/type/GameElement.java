package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;

public abstract class GameElement implements Serializable{
  protected int id;
  
  protected GameElement(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }
}
