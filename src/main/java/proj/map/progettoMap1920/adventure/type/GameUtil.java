package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.Set;

public abstract class GameUtil implements Serializable {
  String name;
  Set<String> alias;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Set<String> getAlias() {
    return alias;
  }

  
  
}
