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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import proj.map.progettoMap1920.adventure.type.AdvObject;

/**
 *
 * @author whyno
 */
public class FileInit {
    public void objReader(String filename) throws FileNotFoundException, IOException{
        Map<Integer,List<Integer>> itemRefContainers;
        itemRefContainers = new HashMap<>();
        int id = 0;
        String objName;
        String description;
        String onLook;
        List<String> alias = new ArrayList<>();
        boolean isContainer = false;
        boolean pickable = false;
        boolean openable = false;
        boolean open = false;
        int lock = 0; // se lock != 0 && isContainer = false istanziare una porta
        int locked_room = 0;
      //  List<AdvObject> containedItems = null;
        FileReader file;
        BufferedReader buffer;
        file = new FileReader(filename);
        buffer = new BufferedReader(file);
        String str ;
        String[] tokenized;
        try{
            while(true){
                str = buffer.readLine();
                while(!"}".equals(str)){//finchè non trovo la parentesi chiusa
                    
                    if("{".equals(str)){//se trovo una parentesi graffa aperta la skippo
                        str = buffer.readLine();
                    }
                    tokenized = str.split(":");
                    
                    if(tokenized[0].equals("ID")){
                        id = Integer.parseInt(tokenized[1]);
                    }
                    if(tokenized[0].equals("NOME")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        objName = tokenized[1];
                    }
                    if(tokenized[0].equals("DESCRIZIONE")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        description = tokenized[1];
                    }
                    if(tokenized[0].equals("LOOK")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        onLook = tokenized[1];
                    }
                    if(tokenized[0].equals("ALIAS")){
                        String[] aliasList = tokenized[1].split("\\s");
                        alias.addAll(Arrays.asList(aliasList));
                    }
                    if(tokenized[0].equals("PICKABLE")){
                        if(tokenized[1].equals("t"))pickable=true;
                    }
                    if(tokenized[0].equals("OPENABLE")){
                        if(tokenized[1].equals("t"))openable=true;
                    }
                    if(tokenized[0].equals("LIST_ID")){
                        if(!tokenized[1].equals("null")){
                            String[] idTokens = tokenized[1].split("\\s");
                            itemRefContainers.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)));
                            isContainer = true;
                        }
                        
                    }
                    if(tokenized[0].equals("LOCK")){
                        if(!tokenized[1].equals("null")){
                           lock = Integer.parseInt(tokenized[1]);
                        }
                    }
                    if(tokenized[0].equals("LOCKED_ROOM")){
                        if(!tokenized[1].equals("null")){
                            locked_room = Integer.parseInt(tokenized[1]);
                        }
                    }
                    //da testare
                    str = buffer.readLine();
                }
                /*
                costruire l'oggetto in questione
                */
                
                
            }
           
        }
        catch (EOFException e){
            
        }
        file.close();
         /*
                dopo aver costruire l'oggetto è necessario linkare per ogni id contenuto in itemRefContainers la rispettiva lista deglio oggetti contenuti
                */
    }
    public void roomReader(String filename) throws FileNotFoundException, IOException{
        //attributi delle stanze
        int id = 0;
        String name;
        String description;
        String look;
        List<Integer> adjacentRooms = new ArrayList<>();
        Map<Integer,List<Integer>> roomMap = new HashMap<>();
        Map<Integer,List<Integer>> objectMap = new HashMap<>();
        Map<Integer,List<Integer>> npcMap = new HashMap<>();
        //file buffer
        FileReader file;
        BufferedReader buffer;
        file = new FileReader(filename);
        buffer = new BufferedReader(file);
        String str ;
        String[] tokenized;
        try{
            while(true){
                str = buffer.readLine();
                while(!"}".equals(str)){
                    if("{".equals(str)){//se trovo una parentesi graffa aperta la skippo
                        str = buffer.readLine();
                        }
                    tokenized = str.split(":");
                    
                    if(tokenized[0].equals("ID")){
                        id = Integer.parseInt(tokenized[1]);
                         }
                    if(tokenized[0].equals("NOME")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        name = tokenized[1];
                    }
                    if(tokenized[0].equals("DESCRIZIONE")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        description = tokenized[1];
                    }
                    if(tokenized[0].equals("LOOK")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        look = tokenized[1];
                    }
                    if(tokenized[0].equals("NORD")){
                        if(!tokenized[1].equals("null")){
                            adjacentRooms.add(Integer.parseInt(tokenized[1]));
                        }
                        else{
                            adjacentRooms.add(null);
                        }
                    }
                    if(tokenized[0].equals("SUD")){
                        if(!tokenized[1].equals("null")){
                            adjacentRooms.add(Integer.parseInt(tokenized[1]));
                        }
                        else{
                            adjacentRooms.add(null);
                        }
                    }
                    if(tokenized[0].equals("EST")){
                        if(!tokenized[1].equals("null")){
                            adjacentRooms.add(Integer.parseInt(tokenized[1]));
                        }
                        else{
                            adjacentRooms.add(null);
                        }
                    }
                    if(tokenized[0].equals("OVEST")){
                        if(!tokenized[1].equals("null")){
                            adjacentRooms.add(Integer.parseInt(tokenized[1]));
                        }
                        else{
                            adjacentRooms.add(null);
                        }
                    }
                    if(tokenized[0].equals("OBJ_ID")){
                        if(!tokenized[1].equals("null")){
                            String[] idTokens = tokenized[1].split("\\s");
                            objectMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)));
                        }
                        else{
                            objectMap.put(id, null);
                        }
                    }
                    if(tokenized[0].equals("NPC_ID")){
                        if(!tokenized[1].equals("null")){
                            String[] idTokens = tokenized[1].split("\\s");
                            npcMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)));
                        }
                        else{
                            npcMap.put(id, null);
                        }
                    }
                    str = buffer.readLine();
                    }
                roomMap.put(id, adjacentRooms);
                /*
                costruire l'oggetto in questione
                */
                }
            }
         catch(EOFException e){
            
          }
        file.close();
        //una volta costruiti tutti gli oggetti è necessario linkarli tra loro
        }
     public void npcReader(String filename) throws FileNotFoundException, IOException{
          //attributi delle stanze
        int id = 0;
        String name;
        String description;
        String look;
        Map<Integer,List<Integer>> inventoryMap = new HashMap<>();
        Map<Integer,Integer> dialogId = new HashMap<>();
        boolean understandable = false;
        boolean killable = false;
        //file buffer
        FileReader file;
        BufferedReader buffer;
        file = new FileReader(filename);
        buffer = new BufferedReader(file);
        String str ;
        String[] tokenized;
        try{
            while(true){
                str = buffer.readLine();
                 while(!"}".equals(str)){
                    if("{".equals(str)){//se trovo una parentesi graffa aperta la skippo
                        str = buffer.readLine();
                    }
                    tokenized = str.split(":");
                    if(tokenized[0].equals("ID")){
                        id = Integer.parseInt(tokenized[1]);
                         }
                    if(tokenized[0].equals("NOME")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        name = tokenized[1];
                    }
                    if(tokenized[0].equals("DESCRIZIONE")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        description = tokenized[1];
                    }
                    if(tokenized[0].equals("LOOK")){
                        tokenized[1] = tokenized[1].replace("\"", "");
                        look = tokenized[1];
                    }
                    if(tokenized[0].equals("KILLABLE")){
                        if(tokenized[1].equals("t"))killable = true;
                    }
                    if(tokenized[0].equals("UNDERSTANDABLE")){
                        if(tokenized[1].equals("t"))understandable = true;
                    }
                    if(tokenized[0].equals("DIALOG")){
                        dialogId.put(id, Integer.parseInt(tokenized[1]));
                    }
                    if(tokenized[0].equals("INVENTORY")){
                        if(!tokenized[1].equals("null")){
                            String[] idTokens = tokenized[1].split("\\s");
                            inventoryMap.put(id, (List<Integer>) Arrays.asList(idTokens).stream().map(s -> Integer.parseInt(s)));
                        }
                        else{
                            inventoryMap.put(id, null);
                        }
                    }
                    str = buffer.readLine();
                 }
                 /*
                 costruire l'oggetto in questione
                 */
            }
        }
        catch(EOFException e){
            
        }
        file.close();
        /*
        linkare inventario e dialogo
        */
     }
     public void dialogReader(String filename) throws FileNotFoundException, IOException{
        //attributi
        int id = 0;
        String text;
        Map<Integer,List<String>> optionMap = new HashMap<>();
        Map<Integer,List<Integer>> dialogMap = new HashMap<>();
        List<String> optionStr = new ArrayList<>();
        List<Integer> dialogId = new ArrayList<>();
        //file buffer
        FileReader file;
        BufferedReader buffer;
        file = new FileReader(filename);
        buffer = new BufferedReader(file);
        String str ;
        String[] tokenized;
        try{
            while(true){
                str = buffer.readLine();
                while(!"}".equals(str)){
                    if("{".equals(str)){//se trovo una parentesi graffa aperta la skippo
                        str = buffer.readLine();
                    }
                    tokenized = str.split(":");
                    if(tokenized[0].equals("ID")){
                        id = Integer.parseInt(tokenized[1]);
                    }
                    if(tokenized[0].equals("TEXT_1")){
                        optionStr.add(tokenized[1]);
                    }
                    if(tokenized[0].equals("TEXT_2")){
                        optionStr.add(tokenized[1]);
                    }
                    if(tokenized[0].equals("TEXT_3")){
                        optionStr.add(tokenized[1]);
                    }
                    if(tokenized[0].equals("DIALOG_1")){
                        dialogId.add(Integer.parseInt(tokenized[1]));
                    }
                    if(tokenized[0].equals("DIALOG_2")){
                        dialogId.add(Integer.parseInt(tokenized[1]));
                    }
                    if(tokenized[0].equals("DIALOG_3")){
                        dialogId.add(Integer.parseInt(tokenized[1]));
                    }
                    str = buffer.readLine();
            }
                optionMap.put(id, optionStr);
                dialogMap.put(id, dialogId);
                /*
                costruire oggetto in questione
                */
        }
            
        }
        catch(EOFException e){
            
        }
        file.close();
        /*
        linkare dialoghi e opzioni
        */
     }
     
}

        
    
    

