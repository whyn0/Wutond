/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.fileInitializer;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.Dialog;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Room;
import proj.map.progettoMap1920.adventure.utils.GameList;
import proj.map.progettoMap1920.adventure.type.Lock;

/**
 *
 * @author whyno
 */
public class FileInit { // probabile singleton
  private GameList<AdvObject> objectList = new GameList<>(new ArrayList<AdvObject>());
  private GameList<AdvObjectContainer> containerList = new GameList<>(new ArrayList<AdvObjectContainer>());
  private GameList<Lock> lockList = new GameList<>(new ArrayList<Lock>());
  private GameList<Room> roomList = new GameList<>(new ArrayList<Room>());
  private GameList<Dialog> dialogList = new GameList<>(new ArrayList<Dialog>());
  private GameList<Npc> npcList = new GameList<>(new ArrayList<Npc>());
 
  /*
  public List<AdvObject> getObjectList() {
    return this.objectList;
  }*/
  public void objReader(String filename) throws FileNotFoundException, IOException {
    int id = 0;
    String objName = "";
    String description = "";
    String onLook = "";
    Set<String> alias = new HashSet<>();
    boolean pickable = false;
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;
    try {
      while ((str = buffer.readLine()) != null) {
        while (!"}".equals(str)) {// finchè non trovo la parentesi chiusa

          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");
          tokenized[1] = tokenized[1].trim();

          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("NAME")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            objName = tokenized[1];
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            description = tokenized[1];
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            onLook = tokenized[1];
          }
          if (tokenized[0].equals("ALIAS")) {
            String[] aliasList = tokenized[1].split("\\s");
            alias.addAll(Arrays.asList(aliasList));
          }
          if (tokenized[0].equals("PICKABLE")) {
            if (tokenized[1].equals("t")) {
              pickable = true;
            }
          }
          // da testare
          str = buffer.readLine();
        }
        /*
         * 
         * costruire l'oggetto in questione
         */
        objectList.add(new AdvObject(id,objName,description,onLook,alias,pickable));
      }

    } catch (EOFException e) {

    }
    file.close();
  }

  public void roomReader(String filename) throws FileNotFoundException, IOException {
    // attributi delle stanze
    int id = 0;
    String name = "";
    String description = "";
    String look = "";
    List<Integer> adjacentRooms = new ArrayList<>();
    Map<Integer, List<Integer>> roomMap = new HashMap<>();
    Map<Integer, List<Integer>> objectMap = new HashMap<>();
    Map<Integer, List<Integer>> npcMap = new HashMap<>();
    // file buffer
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;
    try {
      while ((str = buffer.readLine()) != null) {
        while (!"}".equals(str)) {
          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");
          tokenized[1] = tokenized[1].trim();

          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("NAME")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            name = tokenized[1];
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            description = tokenized[1];
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            look = tokenized[1];
          }
          if (tokenized[0].equals("NORTH")) {
            if (!tokenized[1].equals("null")) {
              adjacentRooms.add(Integer.parseInt(tokenized[1]));
            } else {
              adjacentRooms.add(null);
            }
          }
          if (tokenized[0].equals("SOUTH")) {
            if (!tokenized[1].equals("null")) {
              adjacentRooms.add(Integer.parseInt(tokenized[1]));
            } else {
              adjacentRooms.add(null);
            }
          }
          if (tokenized[0].equals("EAST")) {
            if (!tokenized[1].equals("null")) {
              adjacentRooms.add(Integer.parseInt(tokenized[1]));
            } else {
              adjacentRooms.add(null);
            }
          }
          if (tokenized[0].equals("WEST")) {
            if (!tokenized[1].equals("null")) {
              adjacentRooms.add(Integer.parseInt(tokenized[1]));
            } else {
              adjacentRooms.add(null);
            }
          }
          if (tokenized[0].equals("OBJ_ID")) {
            if (!tokenized[1].equals("null")) {
              String[] idTokens = tokenized[1].split("\\s");
              objectMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
            } else {
              objectMap.put(id, null);
            }
          }
          if (tokenized[0].equals("NPC_ID")) {
            if (!tokenized[1].equals("null")) {
              String[] idTokens = tokenized[1].split("\\s");
              npcMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
            } else {
              npcMap.put(id, null);
            }
          }
          str = buffer.readLine();
        }
        roomMap.put(id, adjacentRooms);
        adjacentRooms.removeAll(adjacentRooms);
        /*
         * costruire l'oggetto in questione
         */
        roomList.add(new Room(id,name,description,look));
      }
    } catch (EOFException e) {

    }
    file.close();
    // una volta costruiti tutti gli oggetti è necessario linkarli tra loro
    Iterator<Room> roomListIter = roomList.iterator();
    while(roomListIter.hasNext()) {
      Room tempRoom = roomListIter.next();
      List<Room> adjRoomTemp = new ArrayList<>();
      for(Integer i : roomMap.get(tempRoom.getId())) {
        adjRoomTemp.add(roomList.getById(i));
      }
      tempRoom.setNorth(adjRoomTemp.get(0));
      tempRoom.setSouth(adjRoomTemp.get(1));
      tempRoom.setEast(adjRoomTemp.get(2));
      tempRoom.setWest(adjRoomTemp.get(3));
    }
    System.out.print("cioa");
  }

  public void npcReader(String filename) throws FileNotFoundException, IOException {
    // attributi delle stanze
    int id = 0;
    String name = "";
    String description = "";
    String look = "";
    Map<Integer, List<Integer>> inventoryMap = new HashMap<>();
    Map<Integer, Integer> dialogId = new HashMap<>();
    boolean understandable = false;
    boolean killable = false;
    // file buffer
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;
    try {
      while ((str = buffer.readLine()) != null) {
        while (!"}".equals(str)) {
          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");
          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("NAME")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            name = tokenized[1];
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            description = tokenized[1];
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            look = tokenized[1];
          }
          if (tokenized[0].equals("KILLABLE")) {
            if (tokenized[1].equals("t")) {
              killable = true;
            }
          }
          if (tokenized[0].equals("UNDERSTANDABLE")) {
            if (tokenized[1].equals("t")) {
              understandable = true;
            }
          }
          if (tokenized[0].equals("DIALOG")) {
            dialogId.put(id, Integer.parseInt(tokenized[1]));
          }
          if (tokenized[0].equals("INVENTORY")) {
            if (!tokenized[1].equals("null")) {
              String[] idTokens = tokenized[1].split("\\s");
              inventoryMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)));
            } else {
              inventoryMap.put(id, null);
            }
          }
          str = buffer.readLine();
        }
        /*
         * 
         * costruire l'oggetto in questione
         */
        npcList.add(new Npc(id,name,description,look,understandable, killable));
      }
    } catch (EOFException e) {

    }
    file.close();
    /*
     * linkare inventario e dialogo
     */
    //link dialogo
    Iterator<Npc> npcIter = npcList.iterator();
    while(npcIter.hasNext()) {
      Npc tempNpc = npcIter.next();
      tempNpc.setDialog(dialogList.getById(dialogId.get(tempNpc.getId())));//setDialog accedo alla lista dei dialoghi,
                                                                           //ricerco per id corrispondente al get della mappa dell'id dell'npc
    }
    //linking oggetti inventario npc
    npcIter = npcList.iterator();
    while(npcIter.hasNext()) {
      Npc tempNpc = npcIter.next();
      List<AdvObject> inventory = new ArrayList<>();
      for(Integer i : inventoryMap.get(tempNpc.getId())) {
        inventory.add(objectList.getById(i));
      }
    }
  }

  public void dialogReader(String filename) throws FileNotFoundException, IOException {
    // attributi
    int id = 0;
    String text;
    Map<Integer, List<String>> optionMap = new HashMap<>();
    Map<Integer, List<Integer>> dialogMap = new HashMap<>();
    List<String> optionStr = new ArrayList<>();
    List<Integer> dialogId = new ArrayList<>();
    // file buffer
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;
    try {
      while (true) {
        str = buffer.readLine();
        while (!"}".equals(str)) {
          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");
          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("TEXT_1")) {
            optionStr.add(tokenized[1]);
          }
          if (tokenized[0].equals("TEXT_2")) {
            optionStr.add(tokenized[1]);
          }
          if (tokenized[0].equals("TEXT_3")) {
            optionStr.add(tokenized[1]);
          }
          if (tokenized[0].equals("DIALOG_1")) {
            dialogId.add(Integer.parseInt(tokenized[1]));
          }
          if (tokenized[0].equals("DIALOG_2")) {
            dialogId.add(Integer.parseInt(tokenized[1]));
          }
          if (tokenized[0].equals("DIALOG_3")) {
            dialogId.add(Integer.parseInt(tokenized[1]));
          }
          str = buffer.readLine();
        }
        optionMap.put(id, optionStr);
        dialogMap.put(id, dialogId);
        /*
         * costruire oggetto in questione
         */
      }

    } catch (EOFException e) {

    }
    file.close();
    /*
     * linkare dialoghi e opzioni
     */
  }

  public void contReader(String filename) throws FileNotFoundException, IOException {
    Map<Integer, List<Integer>> itemRefContainers;
    itemRefContainers = new HashMap<>();
    int id = 0;
    String contName = "";
    String description = "";
    String onLook = "";
    int lock = 0;
    Set<String> alias = new HashSet<>();
    boolean pickable = false;
    // List<AdvObject> containedItems = null;
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;
    try {
      while (true) {
        str = buffer.readLine();
        while (!"}".equals(str)) {// finchè non trovo la parentesi chiusa

          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");

          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("NAME")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            contName = tokenized[1];
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            description = tokenized[1];
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            onLook = tokenized[1];
          }
          if (tokenized[0].equals("ALIAS")) {
            String[] aliasList = tokenized[1].split("\\s");
            alias.addAll(Arrays.asList(aliasList));
          }
          if (tokenized[0].equals("PICKABLE")) {
            if (tokenized[1].equals("t")) {
              pickable = true;
            }
          }
          if (tokenized[0].equals("LIST_ID")) {
            String[] idTokens = tokenized[1].split("\\s");
            itemRefContainers.put(id, (List<Integer>) Arrays.asList(idTokens)
              .stream()
              .map(s -> Integer.parseInt(s)));
          }
          if (tokenized[0].equals("LOCK")) {
            if (!tokenized[1].equals("null")) {
              lock = Integer.parseInt(tokenized[1]);
            }
          }
          // da testare
          str = buffer.readLine();
        }
        /*
         * costruire l'oggetto in questione
         */
        containerList.add(new AdvObjectContainer(null,null,id,contName,description,onLook,alias,pickable));
      }

    } catch (EOFException e) {

    }
    file.close();
    /*
     * dopo aver costruire l'oggetto è necessario linkare per ogni id contenuto in itemRefContainers la rispettiva lista deglio oggetti
     * contenuti
     */
    Iterator<AdvObjectContainer> containerListIter= containerList.iterator();
    while(containerListIter.hasNext()) {
      AdvObjectContainer tempCont = containerListIter.next();
      List<AdvObject> advObjTemp = new ArrayList<>();
      for(Integer i : itemRefContainers.get(tempCont.getId())) {
        for(AdvObject r : containerList) {
          if(i.equals(r.getId())) {
            advObjTemp.add(r);
          }
        }
      }
      tempCont.getList().addAll(advObjTemp);
    }
  }

  public void lockReader(String filename) throws FileNotFoundException, IOException {

    int id = 0;
    int key = 0;
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;

    try {
      while (true) {
        str = buffer.readLine();
        while (!"}".equals(str)) {// finchè non trovo la parentesi chiusa

          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");

          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("ADV_OBJ")) {
            key = Integer.parseInt(tokenized[1]);
          }

          // da testare
          str = buffer.readLine();
        }
        /*
         * costruire l'oggetto in questione
         */

      }

    } catch (EOFException e) {

    }
    file.close();
  }

  public void doorReader(String filename) throws FileNotFoundException, IOException {

    int id = 0;
    String doorName;
    String doorDesc;
    String onLook;
    List<String> alias = new ArrayList<>();
    int lock = 0;
    int room = 0;
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    String str;
    String[] tokenized;

    try {
      while (true) {
        str = buffer.readLine();
        while (!"}".equals(str)) {// finchè non trovo la parentesi chiusa

          if ("{".equals(str)) {// se trovo una parentesi graffa aperta la skippo
            str = buffer.readLine();
          }
          tokenized = str.split(":");

          if (tokenized[0].equals("ID")) {
            id = Integer.parseInt(tokenized[1]);
          }
          if (tokenized[0].equals("NAME")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            doorName = tokenized[1];
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            doorDesc = tokenized[1];
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            onLook = tokenized[1];
          }
          if (tokenized[0].equals("ALIAS")) {
            String[] aliasList = tokenized[1].split("\\s");
            alias.addAll(Arrays.asList(aliasList));
          }
          if (tokenized[0].equals("LOCK")) {
            if (!tokenized[1].equals("null")) {
              lock = Integer.parseInt(tokenized[1]);
            }
          }
          if (tokenized[0].equals("ROOM")) {
            room = Integer.parseInt(tokenized[1]);
          }

          // da testare
          str = buffer.readLine();
        }
        /*
         * costruire l'oggetto in questione
         */

      }

    } catch (EOFException e) {

    }
    file.close();
  }

}
