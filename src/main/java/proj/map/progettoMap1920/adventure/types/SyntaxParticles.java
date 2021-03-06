/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.types;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class SyntaxParticles extends GameUtil implements Serializable {
  private final SyntaxParticlesType particle;

  /*
   * 
   * 
   * COSTRUTTORI
   * 
   * 
   */
  public SyntaxParticles(SyntaxParticlesType particle, String name, Set<String> alias) {
    this.particle = particle;
    this.name = name;
    this.alias = alias;
  }

  public SyntaxParticles(SyntaxParticlesType particle, String name) {
    this.particle = particle;
    this.name = name;
  }

  /*
   * 
   * 
   * GETTERS
   * 
   * 
   */
  public SyntaxParticlesType getType() {
    return particle;
  }


  /*
   * 
   * 
   * SETTERS
   * 
   * 
   */

  public void setAlias(Set<String> alias) {
    this.alias = alias;
  }

  public void setAlias(String[] alias) {
    this.alias = new HashSet<>(Arrays.asList(alias));
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
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.particle);
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
    final SyntaxParticles other = (SyntaxParticles) obj;
    if (!Objects.equals(this.particle, other.particle)) {
      return false;
    }
    return true;
  }

}
