package proj.map.progettoMap1920.adventure.types;

import java.io.Serializable;
import java.util.Set;

public abstract class GameObject extends GameElement implements Serializable {
  protected String name;
  protected String description;
  protected String look;
  protected Set<String> alias;

  /*
   * Costruttori
   */
  protected GameObject(int id, String name, String description, String look, Set<String> alias) {
    super(id);
    this.name = name;
    this.description = description;
    this.look = look;
    this.alias = alias;
  }

  /*
   * Getter e setter
   */
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
