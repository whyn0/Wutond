/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.List;

import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.Command;
import proj.map.progettoMap1920.adventure.type.Door;
import proj.map.progettoMap1920.adventure.type.Npc;

/**
 *
 * @author whyno
 */
public class ParserOutput {
  private Command command;
  private AdvObject object;
  private AdvObjectContainer container;
  private Door door;
  private AdvObject invObject;
  private AdvObject containedObject;
  private Command direction;
  private Npc npc;
  private boolean all;
  private boolean except;
  private List<AdvObject> exObjects = new ArrayList<>();// oggetti esclusi

  //////////// Costruttori////////////

  public ParserOutput() {
    super();
  }

  public ParserOutput(Command command,
    AdvObject object,
    AdvObjectContainer container,
    Door door,
    AdvObject invObject,
    AdvObject containedObject,
    Command direction,
    Npc npc,
    boolean all,
    boolean except,
    List<AdvObject> exObjects) {
    this.door = door;
    this.container = container;
    this.command = command;
    this.object = object;
    this.invObject = invObject;
    this.containedObject = containedObject;
    this.direction = direction;
    this.npc = npc;
    this.all = all;
    this.except = except;
    this.exObjects = exObjects;
  }

  public ParserOutput(Command command, AdvObject object) {
    this.command = command;
    this.object = object;
  }

  public ParserOutput(Command command) {
    this.command = command;
  }

  public ParserOutput(Command command, Command direction) {
    this.command = command;
    this.direction = direction;
  }

  public ParserOutput(Command command, Npc npc) {
    this.command = command;
    this.npc = npc;
  }

  public ParserOutput(Command command, boolean all, boolean except, List<AdvObject> exObjects) {
    this.command = command;
    this.all = all;
    this.except = except;
    this.exObjects = exObjects;
  }

  public ParserOutput(Command command, boolean all) {
    this.command = command;
    this.all = all;
  }

  // Get-set

  public Command getCommand() {
    return command;
  }

  public Door getDoor() {
    return door;
  }

  public void setDoor(Door door) {
    this.door = door;
  }

  public AdvObjectContainer getContainer() {
    return container;
  }

  public void setContainer(AdvObjectContainer container) {
    this.container = container;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  public AdvObject getObject() {
    return object;
  }

  public void setObject(AdvObject object) {
    this.object = object;
  }

  public AdvObject getInvObject() {
    return invObject;
  }

  public void setInvObject(AdvObject invObject) {
    this.invObject = invObject;
  }

  public AdvObject getContainedObject() {
    return containedObject;
  }

  public void setContainedObject(AdvObject containedObject) {
    this.containedObject = containedObject;
  }

  public Command getDirection() {
    return direction;
  }

  public void setDirection(Command direction) {
    this.direction = direction;
  }

  public Npc getNpc() {
    return npc;
  }

  public void setNpc(Npc npc) {
    this.npc = npc;
  }

  public boolean isAll() {
    return all;
  }

  public void setAll(boolean all) {
    this.all = all;
  }

  public boolean isExcept() {
    return except;
  }

  public void setExcept(boolean except) {
    this.except = except;
  }

  public List<AdvObject> getExObjects() {
    return exObjects;
  }

  public void setExObjects(List<AdvObject> exObjects) {
    this.exObjects = exObjects;
  }

}
