/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.main;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import proj.map.progettoMap1920.adventure.events.EventHandler;

import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.*;
/**
 *
 * @author whyno
 */
import proj.map.progettoMap1920.adventure.utils.GameList;
public abstract class GameDescription {
  
  //FIELDS
  
  private GameList<AdvObject> objects = new GameList<>(new ArrayList<AdvObject>());
  private GameList<AdvObjectContainer> containers = new GameList<>(new ArrayList<AdvObjectContainer>());
  private GameList<Lock> locks = new GameList<>(new ArrayList<Lock>());
  private GameList<Room> rooms = new GameList<>(new ArrayList<Room>());
  private GameList<Dialog> dialogs = new GameList<>(new ArrayList<Dialog>());
  private GameList<Npc> npcs = new GameList<>(new ArrayList<Npc>());
  private GameList<Door> doors = new GameList<>(new ArrayList<Door>());
  private GameList<AdvObject> inventory = new GameList<>(new ArrayList<AdvObject>());
  private EventHandler event;

  private List<Article> articles = new ArrayList<>();
  private List<Command> commands = new ArrayList<>();
  private List<Preposition> prepositions = new ArrayList<>();
  private List<SyntaxParticles> particles = new ArrayList<>();
  
  private Room currentRoom;
  private CFGrammar cnfGrammar;
  
  
  
  //--------------------------------------------SETTER---------------
  public void setInventory(GameList<AdvObject> inventory) {
    this.inventory = inventory;
  }
  
  
  public void setGrammar(CFGrammar grammar) {
    this.cnfGrammar = grammar;
  }
  public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
  }
  
  public void setObjects(GameList<AdvObject> objects) {
    this.objects = objects;
  }

  public void setContainers(GameList<AdvObjectContainer> containers) {
    this.containers = containers;
  }

  public void setLocks(GameList<Lock> locks) {
    this.locks = locks;
  }

  public void setRooms(GameList<Room> rooms) {
    this.rooms = rooms;
  }

  public void setDialogs(GameList<Dialog> dialogs) {
    this.dialogs = dialogs;
  }

  public void setNpcs(GameList<Npc> npcs) {
    this.npcs = npcs;
  }

  public void setDoors(GameList<Door> doors) {
    this.doors = doors;
  }
  public void setEvent(EventHandler e){
      this.event = e;
  }
  //--------------------------------------------GETTER---------------
  
  public Room getCurrentRoom() {
    return currentRoom;
  }

  public GameList<AdvObject> getInventory() {
    return inventory;
  }


  public List<Article> getArticles() {
    return articles;
  }
  public List<Command> getCommands() {
    return commands;
  }
  public List<Preposition> getPrepositions() {
    return prepositions;
  }
  public List<SyntaxParticles> getParticles() {
    return particles;
  }
  public CFGrammar getCnfGrammar() {
    return cnfGrammar;
  }
  public GameList<AdvObject> getObjects() {
    return objects;
  }

  public GameList<AdvObjectContainer> getContainers() {
    return containers;
  }

  public GameList<Lock> getLocks() {
    return locks;
  }

  public GameList<Room> getRooms() {
    return rooms;
  }

  public GameList<Dialog> getDialogs() {
    return dialogs;
  }

  public GameList<Npc> getNpcs() {
    return npcs;
  }

  public GameList<Door> getDoors() {
    return doors;
  }
  public CFGrammar getGrammar() {
    return cnfGrammar;
  }
  public EventHandler getEvent(){
      return event;
  }
  //----------------------------------------------METHODS------------
  public abstract void init() throws IOException, FileNotFoundException;

  public abstract void nextMove(ParserOutput p, JTextArea out, JFrame gui);


  public void run(ParserOutput p, PrintStream out) {
    // TODO Auto-generated method stub
    
  }
}
