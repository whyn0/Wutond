package proj.map.progettoMap1920.adventure.type;

public abstract class GameElement {
  protected int id;
  
  protected GameElement(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }
}
