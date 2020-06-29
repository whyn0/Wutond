/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.games;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import proj.map.progettoMap1920.adventure.fileInitializer.FileInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GameUtilInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GrammarInit;
import proj.map.progettoMap1920.adventure.main.GameDescription;
import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.parser.Production;

/**
 *
 * @author whyno
 */
public class Wutond extends GameDescription {

  @Override
  public void init() throws IOException ,FileNotFoundException{
    //Initialize all
    String pathName = "res/file_txt/";
    GrammarInit grammarInit = new GrammarInit(pathName + "GRAMMAR.txt") ;
    FileInit fileInit = new FileInit(pathName + "ADV_OBJ.txt",
                                       pathName + "LOCK.txt",
                                       pathName + "ADV_CONT.txt",
                                       pathName + "DOOR.txt",
                                       pathName + "DIALOG.txt",
                                       pathName + "NPC.txt",
                                       pathName + "ROOM.txt") ;
     GameUtilInit utilInit = new GameUtilInit(pathName + "ARTICLES.txt",
                                               pathName + "COMMAND.txt",
                                               pathName + "PREPOSITION.txt",
                                               pathName + "PARTICLES.txt");
     
    //Assign created objs from files to fields in gameDescription---------------------------
     this.getObjects().addAll(fileInit.getObjectList());
     this.getLocks().addAll(fileInit.getLockList());
     this.getContainers().addAll(fileInit.getContainerList());
     this.getDoors().addAll(fileInit.getDoorList());
     this.getDialogs().addAll(fileInit.getDialogList());
     this.getNpcs().addAll(fileInit.getNpcList());
     this.getRooms().addAll(fileInit.getRoomList());
     //Assign grammar in file to the grammar used by the parser
     this.setGrammar(new CFGrammar("START",
                                   new ArrayList<Production>(grammarInit.getProductions()),
                                   new ArrayList<String>(grammarInit.getnTerminals()),
                                   new ArrayList<String>(grammarInit.getTerminals())));
     //Assing util types for parsing the command string
     this.getArticles().addAll(utilInit.getArticles_list());
     this.getPrepositions().addAll(utilInit.getPrepositions_list());
     this.getCommands().addAll(utilInit.getCmd_list());
     this.getParticles().addAll(utilInit.getParticles_list());
    //set starting room
     this.setCurrentRoom(this.getRooms().getById(1));
  }

  @Override
  public void nextMove(ParserOutput p, PrintStream out) {
    // TODO Auto-generated method stub

  }

}
