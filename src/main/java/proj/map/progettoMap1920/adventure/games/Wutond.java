/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.games;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import proj.map.progettoMap1920.adventure.events.EventHandler;
import proj.map.progettoMap1920.adventure.exceptions.EOGameException;
import proj.map.progettoMap1920.adventure.fileInitializer.FileInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GameUtilInit;
import proj.map.progettoMap1920.adventure.fileInitializer.GrammarInit;
import proj.map.progettoMap1920.adventure.main.GameDescription;
import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.CommandType;
import proj.map.progettoMap1920.adventure.type.Dialog;
import proj.map.progettoMap1920.adventure.type.DialogB;
import proj.map.progettoMap1920.adventure.type.Door;
import proj.map.progettoMap1920.adventure.type.Lock;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Room;
import proj.map.progettoMap1920.adventure.utils.GameList;
import proj.map.progettoMap1920.adventure.utils.Gui;

/**
 *
 * @author whyno
 */
public class Wutond extends GameDescription implements Serializable{

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
    // getInventory().add(getObjects().getByName("Questura"));//Questura
    //getInventory().add(getObjects().getById(79));
    //getInventory().add(this.getObjects().getById(53));
    this.setEvent(new EventHandler(this.getInventory(),
      this.getObjects(),
      this.getContainers(),
      this.getNpcs(),
      this.getDoors(),
      this.getLocks(),
      this.getRooms(),
      this.getDialogs(),
      this.getArticles(),
      this.getCommands(),
      this.getPrepositions(),
      this.getParticles(),
      this.getCnfGrammar()));
  }
  public void clearList() {
	  this.getInventory().clear();
      this.getObjects().clear();
      this.getContainers().clear();
      this.getNpcs().clear();
      this.getDoors().clear();
      this.getLocks().clear();
      this.getRooms().clear();
      this.getDialogs().clear();
      this.getArticles().clear();
      this.getCommands().clear();
      this.getPrepositions().clear();
      this.getParticles().clear();
  }
  private void separator(JTextArea out){
        out.append("\n");
        for(int i = 0; i < 88 ; i++){
             out.append("=");
        }
        out.append("\n");
    } 
  
  @Override
  public void nextMove(ParserOutput p, JTextArea out,JTextArea in, Gui gui) throws EOGameException {
    if (p.getCommand() == null) {

      out.append("Non ho capito cosa dovrei fare! Prova con un altro comando.");
      separator(out);

    } else {
      
      int wallCounter = 0;
      boolean checkHit = false;
      boolean noroom = false;
      boolean move = false;
      List<Room> thisLockedRoom = new ArrayList<>();
      List<AdvObjectContainer> roomContList = new ArrayList<>();
      roomContList.addAll(getCurrentRoom().getObjects_list()
        .stream()
        .filter(o -> o instanceof AdvObjectContainer)
        .map(AdvObjectContainer.class::cast)
        .filter(o -> o.isOpened())
        .collect(Collectors.toList()));
      if (getCurrentRoom().getObjects_list().size() > 0) {
        for (AdvObject a : getCurrentRoom().getObjects_list()) {
          if (a instanceof Door) {
            if (((Door) a).getLock() != null) {
              thisLockedRoom.add(((Door) a).getLockedRoom());
            }
          }
        }
      }

      // ------------------------DIREZIONI E MOVIMENTI-------------------------------
      if (p.getCommand().getType() == CommandType.WALK_TO) {
        p.setCommand(p.getDirection());
      }
      if (p.getCommand().getType() == CommandType.NORTH) {
        if (getCurrentRoom().getNorth() != null) {
          if (!thisLockedRoom.contains(getCurrentRoom().getNorth())) {
            setCurrentRoom(getCurrentRoom().getNorth());
            move = true;
            out.append(getCurrentRoom().getName().toUpperCase());
            separator(out);
            out.append(getCurrentRoom().getDescription());
          } else {
            noroom = true;
            out.append("*Sbatti la testa contro la porta*");
            wallCounter++;
          }

        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.SOUTH) {
        if (getCurrentRoom().getSouth() != null) {
          if (!thisLockedRoom.contains(getCurrentRoom().getSouth())) {
            setCurrentRoom(getCurrentRoom().getSouth());
            move = true;
            out.append(getCurrentRoom().getName().toUpperCase());
            separator(out);
            out.append(getCurrentRoom().getDescription());
          } else {
            noroom = true;
            out.append("*Sbatti la testa contro la porta*");
            wallCounter++;
          }

        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.EAST) {
        if (getCurrentRoom().getEast() != null) {
          if (!thisLockedRoom.contains(getCurrentRoom().getEast())) {
            setCurrentRoom(getCurrentRoom().getEast());
            move = true;
            out.append(getCurrentRoom().getName().toUpperCase());
            separator(out);
            out.append(getCurrentRoom().getDescription());
          } else {
            noroom = true;
            out.append("*Sbatti la testa contro la porta*");
            wallCounter++;
          }

        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*");
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.WEST) {
        if (getCurrentRoom().getWest() != null) {
          if (!thisLockedRoom.contains(getCurrentRoom().getWest())) {
            setCurrentRoom(getCurrentRoom().getWest());
            move = true;
            out.append(getCurrentRoom().getName().toUpperCase());
            separator(out);
            out.append(getCurrentRoom().getDescription());
          } else {
            noroom = true;
            out.append("*Sbatti la testa contro la porta*");
            wallCounter++;
          }

        } else {
          noroom = true;
          out.append("*Sbatti la testa contro un muro*" );
          wallCounter++;
        }
      } else if (p.getCommand().getType() == CommandType.INVENTORY) {
        if (getInventory().getList().size() > 0) {
          out.append("Nel tuo inventario ci sono:" + '\n');
          for (AdvObject a : getInventory()) {
            out.append(a.getName() + "\n");
          }
        } else {
          out.append("Non hai oggetti nell'inventario!" );
        }

      } else if (p.getCommand().getType() == CommandType.LOOK_AT) {

        if (getCurrentRoom().getLook() != null) {

          if (p.getObject() == null
            && p.getNpc() == null
            && p.getContainedObject() == null
            && p.getContainer() == null
            && p.getInvObject() == null
            && p.getDoor() == null) { // Look su stanza

            out.append(getCurrentRoom().getLook() );
            if (getCurrentRoom().getObjects_list().size() > 0) {
              for (AdvObject a : getCurrentRoom().getObjects_list()) {

                out.append(a.getDescription() );
              }
            }

            for (Npc n : getCurrentRoom().getNpc_list()) {

              out.append(n.getDescription() );
            }

          } else if (p.getObject() != null
            && p.getNpc() == null
            && p.getContainedObject() == null
            && p.getContainer() == null
            && p.getInvObject() == null
            && p.getDoor() == null) {// look su un oggetto della stanza

              out.append(p.getObject().getLook() );

            } else if (p.getObject() == null
              && p.getNpc() != null
              && p.getContainedObject() == null
              && p.getContainer() == null
              && p.getInvObject() == null
              && p.getDoor() == null) {// look su un npc

                out.append(p.getNpc().getLook() );
              } else if (p.getObject() == null
                && p.getNpc() == null
                && p.getContainedObject() != null
                && p.getContainer() == null
                && p.getInvObject() == null
                && p.getDoor() == null) {// look su un oggetto contenuto in un container aperto

                  out.append(p.getContainedObject().getLook() );

                } else if (p.getObject() == null
                  && p.getNpc() == null
                  && p.getContainedObject() == null
                  && p.getContainer() != null
                  && p.getInvObject() == null
                  && p.getDoor() == null) {// look su un oggetto di tipo container

                    out.append(p.getContainer().getLook() );
                    if(p.getContainer().isOpened()){
                        if(p.getContainer().getList() != null){
                            if(p.getContainer().getList().size() > 0){
                                out.append(p.getContainer().getName() + " contiene: " + "\n");
                                p.getContainer().getList().forEach((a) -> {
                                    out.append("-" + a.getName() + "\n");
                                });
                            }
                        }
                    }

                  } else if (p.getObject() == null
                    && p.getNpc() == null
                    && p.getContainedObject() == null
                    && p.getContainer() == null
                    && p.getInvObject() != null
                    && p.getDoor() == null) {// look su un oggetto dell'inventario

                      out.append(p.getInvObject().getLook() );

                    } else if(p.getDoor() != null
                      && p.getObject() == null
                      && p.getNpc() == null
                      && p.getContainedObject() == null
                      && p.getContainer() == null
                      && p.getInvObject() == null) {
                      out.append(p.getDoor().getLook() );
                    }
          

        } else {
          out.append("Non c'è nulla da guardare qui!" );
        }
      } else if (p.getCommand().getType() == CommandType.PICK_UP) {
        if (p.getObject() != null) {// caso di oggetto nella stanza
          if (p.getObject().isPickable()) {
            getInventory().add(p.getObject());
            getCurrentRoom().getObjects_list().remove(p.getObject());
            out.append("Hai raccolto: " + p.getObject().getName() );
          } else {
            out.append("Non puoi raccoglierlo!" );
          }
        }if (p.getContainedObject() != null) {// caso di oggetto in un contenitore
          List<AdvObjectContainer> templist = getCurrentRoom().getObjects_list().stream()
            .filter(a -> a instanceof AdvObjectContainer)
            .map(AdvObjectContainer.class::cast)
            .collect(Collectors.toList());
          for (AdvObjectContainer a : templist) {
            if (a.getList().contains(p.getContainedObject())) {
              getInventory().add(p.getContainedObject());
              a.remove(p.getContainedObject());
              out.append("Hai raccolto: " + p.getContainedObject().getName() + "\n");
              break;
            }
          }

        }if (p.isAll() && !p.isExcept()) {
          boolean flag = false;
          for (AdvObject c : getCurrentRoom().getObjects_list()) {
            if (c instanceof AdvObjectContainer) {
              if (((AdvObjectContainer) c).isOpened()) {
                for (AdvObject a : ((AdvObjectContainer) c).getList()) {
                  flag = true;
                  out.append("Hai raccolto: " + a.getName() + '\n');
                  getInventory().add(a);
                }
                ((AdvObjectContainer) c).getList().removeAll(((AdvObjectContainer) c).getList());
              }
            }
          }
          List<AdvObject> tempList = new ArrayList<>();
          for (AdvObject a : getCurrentRoom().getObjects_list()) {
            if (a.isPickable()) {
              flag = true;
              out.append("Hai raccolto: " + a.getName() + '\n');
              getInventory().add(a);
              tempList.add(a);
            }
          }
          getCurrentRoom().getObjects_list().removeAll(tempList);
          if (!flag) {
            out.append("Non hai raccolto nulla!" );
          }
        }if (p.isAll() && p.isExcept()) {
          boolean flag = false;
          for (AdvObject c : getCurrentRoom().getObjects_list()) {
            if (c instanceof AdvObjectContainer) {
              if (((AdvObjectContainer) c).isOpened()) {
                for (AdvObject a : ((AdvObjectContainer) c).getList()) {
                  if (!p.getExObjects().contains(a)) {
                    flag = true;
                    out.append("Hai raccolto: " + a.getName() + '\n');
                    getInventory().add(a);
                  }
                }
                List<AdvObject> tempList = new ArrayList<>();
                for (AdvObject b : ((AdvObjectContainer) c).getList()) {
                  if (!p.getExObjects().contains(b)) {
                    //((AdvObjectContainer) c).getList().remove(b);
                     tempList.add(b);
                  }
                }
                ((AdvObjectContainer) c).getList().removeAll(tempList);
              }
            }
          }
          for (AdvObject a : getCurrentRoom().getObjects_list()) {
            if (a.isPickable()) {
              if (!p.getExObjects().contains(a)) {
                flag = true;
                out.append("Hai raccolto: " + a.getName() + '\n');
                getInventory().add(a);
                getCurrentRoom().getObjects_list().remove(a);
              }
            }
          }
          if (!flag) {
            out.append("Non hai raccolto nulla!" );
          }
        }

      } else if (p.getCommand().getType() == CommandType.OPEN) {
        if (p.getContainer() != null) {// caso di oggetto nella stanza
          if (p.getContainer().getLock() != null) {
            if (getInventory().contains(p.getContainer().getLock().getKey())) {
              out.append("Hai usato " + p.getContainer().getLock().getKey().getName() + " per aprire " + p.getContainer().getName() + " !" + "\n");
              out.append(p.getContainer().getName() + " contiene:" + '\n');
              for (AdvObject a : p.getContainer().getList()) {
                out.append("-" + " " + a.getName() + '\n');
              }
              p.getContainer().setOpened(true);
              getInventory().remove(p.getContainer().getLock().getKey());//rimuovo la chiave dall'inventario
              p.getContainer().setLock(null);
              
            } else {
              out.append("Non hai la chiave giusta per aprire " + p.getContainer().getName());
            }            
          }
          else if(p.getContainer().getLock() == null) {
            out.append(p.getContainer().getName() + " contiene:" + '\n');
            p.getContainer().setOpened(true);
            for (AdvObject a : p.getContainer().getList()) {
              out.append("-" + " " + a.getName() + '\n');
            }
          }
        } else if (p.getInvObject() != null && p.getInvObject() instanceof AdvObjectContainer) {// caso di container nell'inventario
          AdvObjectContainer copyObj = (AdvObjectContainer) p.getInvObject();
          if (copyObj.getLock() != null) {
            if (getInventory().contains(copyObj.getLock().getKey())) {
              out.append("Hai usato " + copyObj.getLock().getKey().getName() + " per aprire " + copyObj.getName() + " !" + "\n");
              for (AdvObject a : copyObj.getList()) {
                out.append("Hai raccolto: " + a.getName() + '\n');
                getInventory().add(a);
              }
              copyObj.setOpened(true);
              getInventory().remove(copyObj.getLock().getKey());
              copyObj.setLock(null);
              
              out.append(copyObj.getName() + " non ti serve più a nulla, decidi di donarlo alla natura." );
              getInventory().remove(copyObj);
            } else {
              out.append("Non hai la chiave giusta per aprire " + copyObj.getName() + '\n');
            }
          }
        } else if (p.getDoor() != null) {
          if (p.getDoor().getLock() != null) {
            if (getInventory().contains(p.getDoor().getLock().getKey())) {
              out.append("Hai usato " + p.getDoor().getLock().getKey().getName() + " per aprire la porta !" + '\n');
              getInventory().remove(p.getDoor().getLock().getKey());
              getCurrentRoom().getObjects_list().remove(p.getDoor());
              
            } else {
              out.append("Non hai la chiave giusta per aprire la porta" + "\n");
            }
          }
        }
      } else if (wallCounter >= 30) {
        out.append("Hai sbattuto la testa troppe volte contro i muri, ti senti confuso!" + '\n');
      } else if (p.getCommand().getType() == CommandType.TALK_TO) {
        // synchronized(out) {
        if (p.getNpc().isUnderstandable() && p.getNpc().getDialog() != null) {
          DialogB d = new DialogB(gui, true);
          d.setDialog(p.getNpc().getDialog());
          p.getNpc().setSpoken(true);
          d.setVisible(true);
          if(p.getNpc().getNpc_inventory() != null) {
            getInventory().addAll(p.getNpc().getNpc_inventory());
            out.append(p.getNpc().getName() + " ti ha dato:" + "\n");
            for(AdvObject a : p.getNpc().getNpc_inventory()) {
              out.append("-" + a.getName() + "\n");
            }
            p.getNpc().getNpc_inventory().removeAll(p.getNpc().getNpc_inventory());
            p.getNpc().setDialog(null);
            p.getNpc().setKillable(true);
          }
        } else if(!p.getNpc().isUnderstandable() && p.getNpc().getDialog() != null){
          out.append(p.getNpc().getName() + " dice:" + "\n");
          Random r = new Random();
          for (int i = 0; i < 40; i++) {
            Character c;
            c = (char) (r.nextInt('z' - 'a') + 'a');
            out.append(c.toString());

          }
          out.append("\n" + "Ti sembra che parli in dialetto strettissimo, ti allontani sorridendo.");
        } else if(p.getNpc().getDialog() == null) {
          out.append(p.getNpc().getName() + " non ha nulla da dirti" + "\n");
        }

        ((DefaultCaret)out.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
      } else if (p.getCommand().getType() == CommandType.CURSE) {

        StringBuilder temp = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < out.getText().length(); i++) {
          Character c;
          c = (char) (r.nextInt('z' - 'a') + 'a');
          temp.append(c);

        }

        out.setText(temp.toString());
        out.append("\n" + "\n");
        out.append(p.getCommand().getName() + " a me?! Ora sono *!?*** tuoi !");
      } else if (p.getCommand().getType() == CommandType.USE) {
        try {
          getEvent().check(p, this.getCurrentRoom(), move, out , in , gui);
        } catch(NullPointerException e) {
          out.append("Non puoi usare quest'oggetto" + "\n");
        }
        checkHit = true;
      } else if(p.getCommand().getType() == CommandType.GIVE) {
        
        getEvent().check(p, this.getCurrentRoom(), move, out, in , gui);
        checkHit = true;
      } else if(p.getCommand().getType() == CommandType.SAVE) {
        try {
          save("res/file_txt/out.dat");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        out.append("Salvataggio avvenuto con successo" + "\n");
      } else if(p.getCommand().getType() == CommandType.LOAD) {
        try {
          load("res/file_txt/out.dat");
        } catch (ClassNotFoundException | IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        out.setText("");
        out.append("Caricamento avvenuto con successo!" + "\n" + "====================================" + "\n");
        out.append(getCurrentRoom().getName() + '\n' + getCurrentRoom().getDescription() + '\n' + '\n');
      } else if(p.getCommand().getType() == CommandType.KILL) {
        if(p.getNpc() != null && p.getNpc().isKillable()) {
          if(getInventory().contains(this.getObjects().getById(52))) {
            out.append("A malincuore hai posto fine alle sofferenze di " + p.getNpc().getName() + "." + "\n" + "Non pensi che qualcuno ne sentirà la mancanza" + "\n");
            this.getCurrentRoom().getNpc_list().remove(p.getNpc());
          } else {
            out.append("Non sei così barbaro da uccidere qualcuno a mani nude!" + "\n");
          }
        } else {
          out.append("Non ti sembra il caso di uccidere " + p.getNpc().getName() + " almeno per il momento...");
        }
      }
      if(!checkHit) {
        try {
          this.getEvent().check(p, this.getCurrentRoom(), move, out, in , gui);
        } catch(NullPointerException e) {
        }
      }
    }

  }
  private void save(String filename) throws IOException{
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));

    out.writeObject(this.getObjects());
    out.writeObject(this.getInventory());
    out.writeObject(this.getContainers());
    out.writeObject(this.getDoors());
    out.writeObject(this.getDialogs());
    out.writeObject(this.getNpcs());
    out.writeObject(this.getInventory());
    out.writeObject(this.getLocks());
    out.writeObject(this.getRooms());
    out.writeObject(this.getCurrentRoom());
    out.writeObject(this.getEvent());
    out.close();
  }
  private void load(String filename) throws IOException, ClassNotFoundException{
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
    
    this.setObjects((GameList<AdvObject>) in.readObject());
    this.setInventory((GameList<AdvObject>) in.readObject());
    this.setContainers((GameList<AdvObjectContainer>) in.readObject());
    this.setDoors((GameList<Door>) in.readObject());
    this.setDialogs((GameList<Dialog>) in.readObject());
    this.setNpcs((GameList<Npc>) in.readObject());
    this.setInventory((GameList<AdvObject>) in.readObject());
    this.setLocks((GameList<Lock>) in.readObject());
    this.setRooms((GameList<Room>) in.readObject());
    this.setCurrentRoom((Room) in.readObject());
    this.setEvent((EventHandler) in.readObject());
    in.close();
  }

}
