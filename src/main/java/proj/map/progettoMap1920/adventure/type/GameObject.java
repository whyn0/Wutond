package proj.map.progettoMap1920.adventure.type;

import java.util.Set;

public abstract class GameObject extends GameElement{
  String name;
  String description;
  String look;
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
  public void setAlias(Set<String> alias) {
    this.alias = alias;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getLook() {
    return look;
  }
  public void setLook(String look) {
    this.look = look;
  }
  
  
}
