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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JTextArea;


import proj.map.progettoMap1920.adventure.fileInitializer.FileInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GameUtilInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GrammarInit;
import proj.map.progettoMap1920.adventure.main.GameDescription;
import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.parser.Production;
import proj.map.progettoMap1920.adventure.type.*;
import proj.map.progettoMap1920.adventure.utils.LockT;

/**
 *
 * @author whyno
 */
public class Wutond extends GameDescription {

  @Override
  public void init() throws IOException, FileNotFoundException {
    // Initialize all
    String pathName = "res/file_txt/";
    GrammarInit grammarInit = new GrammarInit(pathName + "GRAMMAR.txt");
    FileInit fileInit = new FileInit(pathName + "ADV_OBJ.txt",
      pathName + "LOCK.txt",
      pathName + "ADV_CONT.txt",
      pathName + "DOOR.txt",
      pathName + "DIALOG.txt",
      pathName + "NPC.txt",
      pathName + "ROOM.txt");
    GameUtilInit utilInit = new GameUtilInit(pathName + "ARTICLES.txt",
      pathName + "COMMAND.txt",
      pathName + "PREPOSITION.txt",
      pathName + "PARTICLES.txt");

    // Assign created objs from files to fields in gameDescription---------------------------
    this.getObjects().addAll(fileInit.getObjectList());
    this.getLocks().addAll(fileInit.getLockList());
    this.getContainers().addAll(fileInit.getContainerList());
    this.getDoors().addAll(fileInit.getDoorList());
    this.getDialogs().addAll(fileInit.getDialogList());
    this.getNpcs().addAll(fileInit.getNpcList());
    this.getRooms().addAll(fileInit.getRoomList());
    // Assign grammar in file to the grammar used by the parser
    this.setGrammar(new CFGrammar("START",
      new ArrayList<>(grammarInit.getProductions()),
      new ArrayList<>(grammarInit.getnTerminals()),
      new ArrayList<>(grammarInit.getTerminals())));
    // Assing util types for parsing the command string
    this.getArticles().addAll(utilInit.getArticles_list());
    this.getPrepositions().addAll(utilInit.getPrepositions_list());
    this.getCommands().addAll(utilInit.getCmd_list());
    this.getParticles().addAll(utilInit.getParticles_list());
    // set starting room
    this.setCurrentRoom(this.getRooms().getById(1));
    getInventory().add(getObjects().getByName("Questura"));//Questura
  }
  
  @Override
  public void nextMove(ParserOutput p, JTextArea out, JFrame gui) {
    if(p.getCommand() == null) {

      out.append("Non ho capito cosa dovrei fare! Prova con un altro comando." + '\n');

    } else {

      int wallCounter = 0;
      boolean noroom = false;
      boolean move = false;
      List<AdvObjectContainer> roomContList = new ArrayList<>();
      roomContList.addAll(getCurrentRoom().getObjects_list()
        .stream()
        .filter(o -> o instanceof AdvObjectContainer)
        .map(AdvObjectContainer.class::cast)
        .filter(o -> o.isOpened())
        .collect(Collectors.toList()));




      //------------------------DIREZIONI E MOVIMENTI-------------------------------
      if(p.getCommand().getType() == CommandType.WALK_TO){
          p.setCommand(p.getDirection());
      }
      if(p.getCommand().getType() == CommandType.NORTH) {
        if(getCurrentRoom().getNorth() != null) {
          setCurrentRoom(getCurrentRoom().getNorth());
          move = true;
          out.append(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription() + '\n' + '\n');
        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*" + '\n');
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.SOUTH) {
        if (getCurrentRoom().getSouth() != null) {
          setCurrentRoom(getCurrentRoom().getSouth());
          move = true;
          out.append(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription() + '\n');
        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*" + '\n');
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.EAST) {
        if (getCurrentRoom().getEast() != null) {
          setCurrentRoom(getCurrentRoom().getEast());
          move = true;
          out.append(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription() + '\n');
        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*" + '\n');
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.WEST) {
        if (getCurrentRoom().getWest() != null) {
          setCurrentRoom(getCurrentRoom().getWest());
          move = true;
          out.append(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription() + '\n');
        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*" + '\n');
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.INVENTORY) {
        out.append("Nel tuo inventario ci sono:" + '\n');
        for(AdvObject a : getInventory()) {
          out.append(a.getName());
        }
      } else if(p.getCommand().getType() == CommandType.LOOK_AT) {

        if(getCurrentRoom().getLook() != null) {

          if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) { //Look su stanza

            out.append(getCurrentRoom().getLook() + '\n' + '\n');

            for(AdvObject a : getCurrentRoom().getObjects_list()) {

              out.append(a.getDescription() + '\n' + '\n');
            }

            for(Npc n : getCurrentRoom().getNpc_list()) {

              out.append(n.getDescription() + '\n' + '\n');
            }

          } else if(p.getObject() != null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un oggetto della stanza

            out.append(p.getObject().getLook() + '\n' + '\n');

          } else if(p.getObject() == null 
            && p.getNpc() != null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un npc

            out.append(p.getNpc().getLook() + '\n' + '\n');
          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() != null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un oggetto contenuto in un container aperto

            out.append(p.getContainedObject().getLook() + '\n' + '\n');

          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() != null 
            && p.getInvObject() == null) {//look su un oggetto di tipo container

            out.append(p.getContainer().getLook() + '\n' + '\n');

          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() != null) {//look su un oggetto dell'inventario

            out.append(p.getInvObject().getLook() + '\n' + '\n');

          }

        } else {
          out.append("Non c'è nulla da guardare qui!" + '\n');
        }
      } else if(p.getCommand().getType() == CommandType.PICK_UP) {
        if(p.getObject() != null) {//caso di oggetto nella stanza
          if(p.getObject().isPickable()) {
            getInventory().add(p.getObject());
            getCurrentRoom().getObjects_list().remove(p.getObject());
            out.append("Hai raccolto: " + p.getObject().getName() + '\n');
          } else {
            out.append("Non puoi raccoglierlo!" + '\n');
          }
        } else if(p.getContainedObject() != null) {//caso di oggetto in un contenitore
          List<AdvObjectContainer> templist = getCurrentRoom().getObjects_list().stream()
            .filter(a -> a instanceof AdvObjectContainer)
            .map(AdvObjectContainer.class::cast)
            .collect(Collectors.toList());
          for(AdvObjectContainer a : templist) {
            if(a.getList().contains(p.getContainedObject())) {
              getInventory().add(p.getContainedObject());
              a.remove(p.getContainedObject());
              out.append("Hai raccolto: " + p.getContainedObject().getName() + '\n');
              break;
            }
          }


        } else if (p.isAll() && !p.isExcept()) {
          boolean flag = false;
          for(AdvObject c : getCurrentRoom().getObjects_list()) {
            if(c instanceof AdvObjectContainer) {
              if(((AdvObjectContainer) c).isOpened()) {
                for(AdvObject a : ((AdvObjectContainer) c).getList()) {
                  flag = true;
                  out.append("Hai raccolto: " + a.getName() + '\n');
                  getInventory().add(a);
                }
                ((AdvObjectContainer) c).getList().removeAll(((AdvObjectContainer) c).getList());
              }
            }
          }
          for(AdvObject a : getCurrentRoom().getObjects_list()) {
            if(a.isPickable()) {
              flag = true;
              out.append("Hai raccolto: " + a.getName() + '\n');
              getInventory().add(a);
              getCurrentRoom().getObjects_list().remove(a);
            }
          }
          if(!flag) {
            out.append("Non hai raccolto nulla!" + '\n');
          }
        } else if(p.isAll() && p.isExcept()) {
          boolean flag = false;
          for(AdvObject c : getCurrentRoom().getObjects_list()) {
            if(c instanceof AdvObjectContainer) {
              if(((AdvObjectContainer) c).isOpened()) {
                for(AdvObject a : ((AdvObjectContainer) c).getList()) {
                  if(!p.getExObjects().contains(a)) {
                    flag = true;
                    out.append("Hai raccolto: " + a.getName() + '\n');
                    getInventory().add(a);
                  }
                }
                for(AdvObject b : ((AdvObjectContainer) c).getList()) {
                  if(!p.getExObjects().contains(b)) {
                    ((AdvObjectContainer) c).getList().remove(b);
                  }
                }
              }
            }
          }
          for(AdvObject a : getCurrentRoom().getObjects_list()) {
            if(a.isPickable()) {
              if(!p.getExObjects().contains(a)) {
                flag = true;
                out.append("Hai raccolto: " + a.getName() + '\n');
                getInventory().add(a);
                getCurrentRoom().getObjects_list().remove(a);
              }
            }
          }
          if(!flag) {
            out.append("Non hai raccolto nulla!" + '\n');
          }
        }

      } else if(p.getCommand().getType() == CommandType.OPEN) {
        if(p.getContainer() != null) {//caso di oggetto nella stanza
          if(p.getContainer().getLock() != null) {
            if(getInventory().contains(p.getContainer().getLock().getKey())) {
              out.append("Hai usato " + p.getContainer().getLock().getKey().getName() + " per aprire " + p.getContainer().getName() + " !" + '\n');
              out.append(p.getContainer().getName() + " contiene:" + '\n');
              for(AdvObject a : p.getContainer().getList()) {
                out.append("-" + " " + a.getName() + '\n');
              }
              p.getContainer().setOpened(true);
              p.getContainer().setLock(null);
            } else {
              out.append("Non hai la chiave giusta per aprire " + p.getContainer().getName() + '\n');
            }
          }
        } else if(p.getInvObject() != null && p.getInvObject() instanceof AdvObjectContainer) {//caso di container nell'inventario
          AdvObjectContainer copyObj = (AdvObjectContainer)p.getInvObject();
          if(copyObj.getLock() != null) {
            if(getInventory().contains(copyObj.getLock().getKey())) {
              out.append("Hai usato " + copyObj.getLock().getKey().getName() + " per aprire " + copyObj.getName() + " !" + '\n');
              for(AdvObject a : copyObj.getList()) {
                out.append("Hai raccolto: " + a.getName() + '\n');
                getInventory().add(a);
              }
              copyObj.setOpened(true);
              copyObj.setLock(null);
              out.append(copyObj.getName() + " non ti serve più a nulla, decidi di donarlo alla natura." + '\n');
              getInventory().remove(copyObj);
            } else {
              out.append("Non hai la chiave giusta per aprire " + copyObj.getName() + '\n');
            }
          }
        }
      } else if(wallCounter >= 30) {
        out.append("Hai sbattuto la testa troppe volte contro i muri, ti senti confuso!" + '\n');
      } else if(p.getCommand().getType() == CommandType.TALK_TO) {
        //synchronized(out) {
          
          DialogB d= new DialogB(gui,true);
          d.setDialog(p.getNpc().getDialog());
          d.setVisible(true);
       } else if(p.getCommand().getType() == CommandType.CURSE){
           
           StringBuilder temp = new StringBuilder();
           Random r = new Random();
           for(int i = 0; i < out.getText().length(); i++){
               Character c;
               c = (char)(r.nextInt('z' - 'a') + 'a');
               temp.append(c);
               
           }

           out.setText(temp.toString());
           out.append("\n" + "\n");
           out.append(p.getCommand().getName() + " a me?! Ora sono *!?*** tuoi !");
       }
      

      }



    }

  }
  


