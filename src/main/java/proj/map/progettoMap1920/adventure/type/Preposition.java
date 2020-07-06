/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class Preposition extends GameUtil implements Serializable {
  private PrepositionType type;

  public Preposition(PrepositionType type, String name, Set<String> alias) {
    this.type = type;
    this.name = name;
    this.alias = alias;
  }

  public PrepositionType getType() {
    return type;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Set<String> getAlias() {
    return alias;
  }

  public void setType(PrepositionType type) {
    this.type = type;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void setAlias(Set<String> alias) {
    this.alias = alias;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.type);
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
    final Preposition other = (Preposition) obj;
    if (this.type != other.type) {
      return false;
    }
    return true;
  }

  public Preposition(PrepositionType type) {
    this.type = type;
  }

  public Preposition(PrepositionType type, String name) {
    this.type = type;
    this.name = name;
  }

}
