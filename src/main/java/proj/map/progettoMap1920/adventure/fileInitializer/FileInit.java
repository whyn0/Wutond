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
import proj.map.progettoMap1920.adventure.type.Door;
import proj.map.progettoMap1920.adventure.type.Lock;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Room;
import proj.map.progettoMap1920.adventure.utils.GameList;

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
  private GameList<Door> doorList = new GameList<>(new ArrayList<Door>());
  
  public FileInit(String... paths) throws IOException, FileNotFoundException{
    objReader(paths[0]);
    lockReader(paths[1]);
    contReader(paths[2]);
    doorReader(paths[3]);
    dialogReader(paths[4]);
    npcReader(paths[5]);
    roomReader(paths[6]);
  }

  /*
   * ---------METHODS---------------
   */

  

  private void objReader(String filename) throws FileNotFoundException, IOException {

    // -----------------------------attributi

    int id = 0;
    String objName = "";
    String description = "";
    String onLook = "";
    Set<String> alias = new HashSet<>();
    boolean pickable = false;

    // -----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    String str;
    String[] tokenized;
    //
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
            objName = String.valueOf(tokenized[1]);
          }
          if (tokenized[0].equals("DESCRIPTION")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            description= String.valueOf(tokenized[1]);
          }
          if (tokenized[0].equals("LOOK")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            onLook= String.valueOf(tokenized[1]);
          }
          if (tokenized[0].equals("ALIAS")) {
            if (!tokenized[1].equals("null")) {
              String[] aliasList = tokenized[1].split("\\s");
              alias.addAll(Arrays.asList(aliasList));
            }
          }
          if (tokenized[0].equals("PICKABLE")) {
            if (tokenized[1].equals("t")) {
              pickable = true;
            }
          }
          str = buffer.readLine();
        }
        // --costruzione oggetti ed inserzione nella lista

        objectList.add(new AdvObject(id, objName, description, onLook, new HashSet<String>(alias), pickable));
        alias.clear();
      }

    } catch (EOFException e) {

    }
    file.close();
  }

  private void roomReader(String filename) throws FileNotFoundException, IOException {

    // ---------------------------attributi delle stanze

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
              objectMap.put(id, Arrays.asList(idTokens).stream()
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
            } else {
              objectMap.put(id, null);
            }
          }
          if (tokenized[0].equals("NPC_ID")) {
            if (!tokenized[1].equals("null")) {
              String[] idTokens = tokenized[1].split("\\s");
              npcMap.put(id, Arrays.asList(idTokens).stream()
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
            } else {
              npcMap.put(id, null);
            }
          }
          str = buffer.readLine();
        }
        List<Integer> copyList = new ArrayList<>();
        for (Integer i : adjacentRooms) {
          if (i != null) {
            copyList.add(i);
          } else {
            copyList.add(null);
          }
        }
        roomMap.put(id, copyList);
        adjacentRooms.removeAll(adjacentRooms);

        // costruisco l'oggetto

        roomList.add(new Room(id, name, description, look, null));
      }
    } catch (EOFException e) {
    }
    // file close

    file.close();

    // una volta costruiti tutti gli oggetti è necessario linkarli tra loro

    Iterator<Room> roomListIter = roomList.iterator();

    // room Linker

    while (roomListIter.hasNext()) {

      Room tempRoom = roomListIter.next();
      List<Room> adjRoomTemp = new ArrayList<>();
      List<Integer> objMapTempList = objectMap.get(tempRoom.getId());
      List<Integer> roomMapTempList = roomMap.get(tempRoom.getId());
      List<Integer> npcMapTempList = npcMap.get(tempRoom.getId());

      /*
       * for(Integer i : roomMap.get(tempRoom.getId())) {
       * adjRoomTemp.add(roomList.getById(i)); }
       */
      for (int i = 0; i < roomMapTempList.size(); i++) {
        Integer roomId;
        if ((roomId = roomMapTempList.get(i)) != null) {
          adjRoomTemp.add(roomList.getById(roomId));
        } else {
          adjRoomTemp.add(null);
        }
      }
      // set stanze adiacenti neglio oggetti istanziati

      tempRoom.setNorth(adjRoomTemp.get(0));
      tempRoom.setSouth(adjRoomTemp.get(1));
      tempRoom.setEast(adjRoomTemp.get(2));
      tempRoom.setWest(adjRoomTemp.get(3));

      // set item in room

      if (objMapTempList != null) {
        for (Integer i : objMapTempList) {
          if (i != null) {
            try {
              tempRoom.getObjects_list().add(objectList.getById(i));// riempire anche con container list
            } catch (NullPointerException e) {
              tempRoom.getObjects_list().add(containerList.getById(i));
            }
          }

        }
      }
      if (npcMapTempList != null) {
        for (Integer i : npcMapTempList) {
          if (i != null) {
            tempRoom.getNpc_list().add(npcList.getById(i));
          }
        }
      }

    }
  }

  private void npcReader(String filename) throws FileNotFoundException, IOException {
    // attributi delle stanze
    int id = 0;
    String name = "";
    String description = "";
    String look = "";
    Map<Integer, List<Integer>> inventoryMap = new HashMap<>();
    Map<Integer, Integer> dialogId = new HashMap<>();
    Set<String> alias = new HashSet<>();
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
          if (tokenized[0].equals("KILLABLE")) {
            if (tokenized[1].equals("t")) {
              killable = true;
            }
          }
          if (tokenized[0].equals("ALIAS")) {
            if (!tokenized[1].equals("null")) {
              String[] aliasList = tokenized[1].split("\\s");
              alias.addAll(Arrays.asList(aliasList));
            } else {
              alias.add(null);
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
              inventoryMap.put(id, Arrays.asList(idTokens).stream()
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
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

        npcList.add(new Npc(id, name, description, look, new HashSet<String>(alias), understandable, killable));
        alias.clear();
      }
    } catch (EOFException e) {

    }
    file.close();
    /*
     * linkare inventario e dialogo
     */

    // link dialogo
    Iterator<Npc> npcIter = npcList.iterator();

    while (npcIter.hasNext()) {
      Npc tempNpc = npcIter.next();
      tempNpc.setDialog(dialogList.getById(dialogId.get(tempNpc.getId())));
    }

    // linking oggetti inventario npc
    npcIter = npcList.iterator();
    while (npcIter.hasNext()) {
      Npc tempNpc = npcIter.next();
      List<AdvObject> inventory = new ArrayList<>();
      if(inventoryMap.get(tempNpc.getId()) != null) {
        for (Integer i : inventoryMap.get(tempNpc.getId())) {
          inventory.add(objectList.getById(i));
        }
      }
      
    }
  }

  private void dialogReader(String filename) throws FileNotFoundException, IOException {
    // attributi
    int id = 0;
    String text = "";
    // Map<Integer, List<String>> optionMap = new HashMap<>();
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
          if (tokenized[0].equals("TEXT")) {
            tokenized[1] = tokenized[1].replace("\"", "");
            text = tokenized[1];
          }
          if (tokenized[0].equals("OPT_1")) {
            if (!tokenized[1].equals("null")) {
              optionStr.add(tokenized[1]);
            } else {
              optionStr.add(null);
            }
          }
          if (tokenized[0].equals("OPT_2")) {
            if (!tokenized[1].equals("null")) {
              optionStr.add(tokenized[1]);
            } else {
              optionStr.add(null);
            }
          }
          if (tokenized[0].equals("OPT_3")) {
            if (!tokenized[1].equals("null")) {
              optionStr.add(tokenized[1]);
            } else {
              optionStr.add(null);
            }
          }
          if (tokenized[0].equals("NEXT_1")) {
            if (!tokenized[1].equals("null")) {
              dialogId.add(Integer.parseInt(tokenized[1]));
            } else {
              dialogId.add(null);
            }
          }
          if (tokenized[0].equals("NEXT_2")) {
            if (!tokenized[1].equals("null")) {
              dialogId.add(Integer.parseInt(tokenized[1]));
            } else {
              dialogId.add(null);
            }
          }
          if (tokenized[0].equals("NEXT_3")) {
            if (!tokenized[1].equals("null")) {
              dialogId.add(Integer.parseInt(tokenized[1]));
            } else {
              dialogId.add(null);
            }
          }
          str = buffer.readLine();
        }
        List<Integer> copyList = new ArrayList<>();
        for (Integer i : dialogId) {
          if (i != null) {
            copyList.add(i);
          } else {
            copyList.add(null);
          }
        }
        dialogMap.put(id, copyList);
        dialogId.removeAll(dialogId);
        /*
         * costruire oggetto in questione
         */
        dialogList.add(new Dialog(id, text, optionStr.get(0), optionStr.get(1), optionStr.get(2), null, null, null));
        optionStr.removeAll(optionStr);
      }

    } catch (EOFException e) {

    }
    file.close();
    /*
     * linkare dialoghi
     */
    Iterator<Dialog> dialogListIter = dialogList.iterator();
    while (dialogListIter.hasNext()) {
      Dialog tempDialog = dialogListIter.next();
      List<Integer> tempIdDialog = dialogMap.get(tempDialog.getId());
      try {
        tempDialog.setNext_1(dialogList.getById(tempIdDialog.get(0)));
      } catch (NullPointerException e) {
        tempDialog.setNext_1(null);
      }
      try {
        tempDialog.setNext_2(dialogList.getById(tempIdDialog.get(1)));
      } catch (NullPointerException e) {
        tempDialog.setNext_2(null);
      }
      try {
        tempDialog.setNext_3(dialogList.getById(tempIdDialog.get(2)));
      } catch (NullPointerException e) {
        tempDialog.setNext_3(null);
      }
    }
  }

  private void contReader(String filename) throws FileNotFoundException, IOException {
    Map<Integer, List<Integer>> itemRefContainers;
    itemRefContainers = new HashMap<>();
    int id = 0;
    String contName = "";
    String description = "";
    String onLook = "";
    Integer lock = null;
    Map<Integer, Integer> lockMap = new HashMap<>();
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
            if (tokenized[1].equals("null")) {
              String[] aliasList = tokenized[1].split("\\s");
              alias.addAll(Arrays.asList(aliasList));
            }else {
              alias.add(null);
            }
          }
          if (tokenized[0].equals("PICKABLE")) {
            if (tokenized[1].equals("t")) {
              pickable = true;
            }
          }
          if (tokenized[0].equals("LIST_ID")) {
            String[] idTokens = tokenized[1].split("\\s");
            itemRefContainers.put(id, Arrays.asList(idTokens).stream()
              .map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
          }
          if (tokenized[0].equals("LOCK")) {
            if (!tokenized[1].equals("null")) {
              lock = Integer.parseInt(tokenized[1]);
            } else {
              lock = null;
            }
          }
          // da testare
          str = buffer.readLine();
        }
        lockMap.put(id, lock);
        /*
         * costruire l'oggetto in questione
         */

        containerList
          .add(new AdvObjectContainer(null, null, id, contName, description, onLook, new HashSet<String>(alias), pickable));
        alias.clear();
      }

    } catch (EOFException e) {

    }
    file.close();
    /*
     * dopo aver costruire l'oggetto è necessario linkare per ogni id contenuto in
     * itemRefContainers la rispettiva lista deglio oggetti contenuti
     */
    Iterator<AdvObjectContainer> containerListIter = containerList.iterator();
    while (containerListIter.hasNext()) {
      AdvObjectContainer tempCont = containerListIter.next();
      for (Integer i : itemRefContainers.get(tempCont.getId())) {
        try {
          tempCont.getList().add(objectList.getById(i));
        }
        catch(NullPointerException e) {
          tempCont.getList().add(null);
        }
      }
      try {
        tempCont.setLock(lockList.getById(lockMap.get(tempCont.getId())));
      }
      catch(NullPointerException e) {
        tempCont.setLock(null);
      }
    }
  }

  private void lockReader(String filename) throws FileNotFoundException, IOException {

    Map<Integer, Integer> lockMap = new HashMap<>();
    Integer key = 0;
    int id = 0;
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
          if (tokenized[0].equals("ADV_OBJ")) {
            if (!tokenized[1].equals("null")) {
              key = Integer.parseInt(tokenized[1]);
            } else {
              key = null;
            }
          }

          // da testare
          str = buffer.readLine();
        }
        lockMap.put(id, key);
        /*
         * costruire l'oggetto in questione
         */
        lockList.add(new Lock(id, null));
      }

    } catch (EOFException e) {

    }
    file.close();

    /*
     * link keys
     */
    Iterator<Lock> lockListIter = lockList.iterator();
    while (lockListIter.hasNext()) {
      Lock tempLock = lockListIter.next();
      try {
      tempLock.setKey(objectList.getById(lockMap.get(tempLock.getId())));
      } catch(NullPointerException e) {
        tempLock.setKey(null);
      }
    }
  }

  private void doorReader(String filename) throws FileNotFoundException, IOException {

    int id = 0;
    String doorName = "";
    String doorDesc = "";
    String onLook = "";
    Set<String> alias = new HashSet<>();
    int lock = 0;
    int room = 0;
    Map<Integer, Integer> lockMap = new HashMap<>();
    Map<Integer, Integer> roomMap = new HashMap<>();
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
            if (!tokenized[1].equals("null")) {
              String[] aliasList = tokenized[1].split("\\s");
              alias.addAll(Arrays.asList(aliasList));
            } else {
              alias.add(null);
            }
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
        lockMap.put(id, lock);
        roomMap.put(id, room);
        /*
         * costruire l'oggetto in questione
         */

        doorList.add(new Door(null, null, id, doorName, doorDesc, onLook, new HashSet<String>(alias), false));

        alias.clear();
      }

    } catch (EOFException e) {

    }
    file.close();

    /*
     * linking
     */
    Iterator<Door> doorListIter = doorList.iterator();
    while (doorListIter.hasNext()) {
      Door tempDoor = doorListIter.next();
      tempDoor.setLockedRoom(roomList.getById(roomMap.get(tempDoor.getId())));
      tempDoor.setLock(lockList.getById(lockMap.get(tempDoor.getId())));
    }
  }

 
  //Getter
  
  public GameList<AdvObject> getObjectList() {
    return objectList;
  }

  public GameList<AdvObjectContainer> getContainerList() {
    return containerList;
  }

  public GameList<Lock> getLockList() {
    return lockList;
  }

  public GameList<Room> getRoomList() {
    return roomList;
  }

  public GameList<Dialog> getDialogList() {
    return dialogList;
  }

  public GameList<Npc> getNpcList() {
    return npcList;
  }

  public GameList<Door> getDoorList() {
    return doorList;
  }

  
}
