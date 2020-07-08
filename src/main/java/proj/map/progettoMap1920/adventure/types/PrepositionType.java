/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.types;

/**
 *
 * @author whyno
 */
public enum PrepositionType {
  WITH("with"),
  THROUGH("through"),
  TOWARDS("towards"),
  TO("to"),
  ON("on"),
  IN("in");

  private String name;

  private PrepositionType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
