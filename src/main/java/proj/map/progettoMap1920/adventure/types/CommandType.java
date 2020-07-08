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
public enum CommandType {
  WALK_TO("walk_to"),
  END("end"),
  NORTH("north"),
  SOUTH("south"),
  WEST("west"),
  EAST("east"),
  OPEN("open"),
  TALK_TO("talk_to"),
  PICK_UP("pick_up"),
  USE("use"),
  GIVE("give"),
  LOOK_AT("look_at"),
  KILL("kill"),
  SAVE("save"),
  LOAD("load"),
  HELP("help"),
  CURSE("curse"),
  INVENTORY("inventory");

  private String string;

  private CommandType(String name) {
    string = name;
  }

  @Override
  public String toString() {
    return string;
  }
}
