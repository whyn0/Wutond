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
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;


import proj.map.progettoMap1920.adventure.fileInitializer.FileInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GameUtilInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GrammarInit;
import proj.map.progettoMap1920.adventure.main.GameDescription;
import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.parser.Production;
import proj.map.progettoMap1920.adventure.type.*;

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
  public void nextMove(ParserOutput p, PrintStream out) {
    if(p.getCommand() == null) {

      out.println("Non ho capito cosa dovrei fare! Prova con un altro comando.");

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
      if(p.getCommand().getType() == CommandType.NORTH) {
        if(getCurrentRoom().getNorth() != null) {
          setCurrentRoom(getCurrentRoom().getNorth());
          move = true;
          out.println(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription());
        } else {
          noroom = true;
          out.println("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.SOUTH) {
        if (getCurrentRoom().getSouth() != null) {
          setCurrentRoom(getCurrentRoom().getSouth());
          move = true;
          out.println(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription());
        } else {
          noroom = true;
          out.println("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.EAST) {
        if (getCurrentRoom().getEast() != null) {
          setCurrentRoom(getCurrentRoom().getEast());
          move = true;
          out.println(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription());
        } else {
          noroom = true;
          out.println("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.WEST) {
        if (getCurrentRoom().getWest() != null) {
          setCurrentRoom(getCurrentRoom().getWest());
          move = true;
          out.println(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription());
        } else {
          noroom = true;
          out.println("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.INVENTORY) {
        out.println("Nel tuo inventario ci sono:");
        for(AdvObject a : getInventory()) {
          out.println(a.getName());
        }
      } else if(p.getCommand().getType() == CommandType.LOOK_AT) {

        if(getCurrentRoom().getLook() != null) {

          if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) { //Look su stanza

            out.println(getCurrentRoom().getLook());

            for(AdvObject a : getCurrentRoom().getObjects_list()) {

              out.println(a.getDescription());
            }

            for(Npc n : getCurrentRoom().getNpc_list()) {

              out.println(n.getDescription());
            }

          } else if(p.getObject() != null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un oggetto della stanza

            out.println(p.getObject().getLook());

          } else if(p.getObject() == null 
            && p.getNpc() != null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un npc

            out.println(p.getNpc().getLook());
          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() != null 
            && p.getContainer() == null 
            && p.getInvObject() == null) {//look su un oggetto contenuto in un container aperto

            out.println(p.getContainedObject().getLook());

          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() != null 
            && p.getInvObject() == null) {//look su un oggetto di tipo container

            out.println(p.getContainer().getLook());

          } else if(p.getObject() == null 
            && p.getNpc() == null 
            && p.getContainedObject() == null 
            && p.getContainer() == null 
            && p.getInvObject() != null) {//look su un oggetto dell'inventario

            out.println(p.getInvObject().getLook());

          }

        } else {
          out.println("Non c'è nulla da guardare qui!");
        }
      } else if(p.getCommand().getType() == CommandType.PICK_UP) {
        if(p.getObject() != null) {//caso di oggetto nella stanza
          if(p.getObject().isPickable()) {
            getInventory().add(p.getObject());
            getCurrentRoom().getObjects_list().remove(p.getObject());
            out.println("Hai raccolto: " + p.getObject().getName());
          } else {
            out.println("Non puoi raccoglierlo!");
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
              out.println("Hai raccolto: " + p.getContainedObject().getName());
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
                  out.println("Hai raccolto: " + a.getName());
                  getInventory().add(a);
                }
                ((AdvObjectContainer) c).getList().removeAll(((AdvObjectContainer) c).getList());
              }
            }
          }
          for(AdvObject a : getCurrentRoom().getObjects_list()) {
            if(a.isPickable()) {
              flag = true;
              out.println("Hai raccolto: " + a.getName());
              getInventory().add(a);
              getCurrentRoom().getObjects_list().remove(a);
            }
          }
          if(!flag) {
            out.println("Non hai raccolto nulla!");
          }
        } else if(p.isAll() && p.isExcept()) {
          boolean flag = false;
          for(AdvObject c : getCurrentRoom().getObjects_list()) {
            if(c instanceof AdvObjectContainer) {
              if(((AdvObjectContainer) c).isOpened()) {
                for(AdvObject a : ((AdvObjectContainer) c).getList()) {
                  if(!p.getExObjects().contains(a)) {
                    flag = true;
                    out.println("Hai raccolto: " + a.getName());
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
                out.println("Hai raccolto: " + a.getName());
                getInventory().add(a);
                getCurrentRoom().getObjects_list().remove(a);
              }
            }
          }
          if(!flag) {
            out.println("Non hai raccolto nulla!");
          }
        }

      } else if(p.getCommand().getType() == CommandType.OPEN) {
        if(p.getContainer() != null) {//caso di oggetto nella stanza
          if(p.getContainer().getLock() != null) {
            if(getInventory().contains(p.getContainer().getLock().getKey())) {
              out.println("Hai usato " + p.getContainer().getLock().getKey().getName() + " per aprire " + p.getContainer().getName() + " !");
              out.println(p.getContainer().getName() + " contiene:");
              for(AdvObject a : p.getContainer().getList()) {
                out.println("-" + " " + a.getName());
              }
              p.getContainer().setOpened(true);
              p.getContainer().setLock(null);
            } else {
              out.println("Non hai la chiave giusta per aprire " + p.getContainer().getName());
            }
          }
        } else if(p.getInvObject() != null && p.getInvObject() instanceof AdvObjectContainer) {//caso di container nell'inventario
          AdvObjectContainer copyObj = (AdvObjectContainer)p.getInvObject();
          if(copyObj.getLock() != null) {
            if(getInventory().contains(copyObj.getLock().getKey())) {
              out.println("Hai usato " + copyObj.getLock().getKey().getName() + " per aprire " + copyObj.getName() + " !");
              for(AdvObject a : copyObj.getList()) {
                out.println("Hai raccolto: " + a.getName());
                getInventory().add(a);
              }
              copyObj.setOpened(true);
              copyObj.setLock(null);
              out.println(copyObj.getName() + " non ti serve più a nulla, decidi di donarlo alla natura.");
              getInventory().remove(copyObj);
            } else {
              out.println("Non hai la chiave giusta per aprire " + copyObj.getName());
            }
          }
        }
      } else if(wallCounter >= 30) {
        out.println("Hai sbattuto la testa troppe volte contro i muri, ti senti confuso!");
      } else if(p.getCommand().getType() == CommandType.TALK_TO) {

        DThread t = new DThread(p.getNpc());
        try {
          System.in.close();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        t.start();
        synchronized(t) {
          try {
            t.wait();
            //System.in.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }



    }

  }
  
}
class DThread extends Thread{
  Npc npc;
  boolean lock = false;
  public DThread(Npc n) {
    this.npc = n;
  }
  public void run() {
    synchronized(this){
      DialogBox d= new DialogBox(npc.getDialog(),lock);
      d.main(null);
      while(!lock) {
        try {
          System.in.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      System.in.
    }

  }
}

