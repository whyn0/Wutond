/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.events;

import java.io.Serializable;
import java.util.Iterator;
import javax.swing.JTextArea;
import proj.map.progettoMap1920.adventure.type.*;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.utils.GameList;

/**
 *
 * @author whyno
 */
public class EventHandler implements EventInterface,Serializable{
  private GameList<AdvObject> inventory;
  private GameList<AdvObject> objList;
  private GameList<AdvObjectContainer> contList;
  private GameList<Npc> npcList;
  private GameList<Door> doorList;
  private GameList<Lock> lockList;
  private GameList<Room> roomList;
  private GameList<Dialog> dialogList;
  private int beerCounter = 0;
  private boolean[] flags = new boolean[10];

  public EventHandler(GameList<AdvObject> inventory, GameList<AdvObject> objList, GameList<AdvObjectContainer> contList, GameList<Npc> npcList, GameList<Door> doorList, GameList<Lock> lockList,GameList<Room> roomList,GameList<Dialog> dialogList) {
    this.inventory = inventory;
    this.objList = objList;
    this.contList = contList;
    this.npcList = npcList;
    this.doorList = doorList;
    this.lockList = lockList;
    this.roomList = roomList;
    this.dialogList = dialogList;
  }



  @Override
  public void check(ParserOutput p, Room currentRoom, boolean move, JTextArea out) {
    //se possiedo dizionario - > npc comprensibili
    System.out.print(inventory.contains(objList.getById(53)));
    if(!flags[0]){
      if(inventory.contains(objList.getById(53))){ //quando l'inventario contiene il dizionario sei in grado di comprendere tutti
        for (Iterator<Npc> it = npcList.iterator(); it.hasNext();) {
          Npc n = it.next();
          if(!n.isUnderstandable()){
            n.setUnderstandable(true);
          }
          if(n.getId() == 67){
            n.setDialog(dialogList.getById(-1));
          }
        }
        out.append("Ora puoi comprendere tutti i cittadini!");
        flags[0] = true;
      }
    } 
    if(!flags[1]){
      if(inventory.contains(objList.getById(52)) && currentRoom.equals(roomList.getById(5))){//se l'inventario contiene la pistola e la currentroom == orologio
        roomList.getById(7).getObjects_list().add(objList.getById(61));//aggiungo alla lista degli oggetti del ponte il portafogli
        out.append("Vedi un uomo vestito con una tutina nera e un cappotto scappare dentro il torrione."
          + " Provi ad inseguirlo ma ti ricordi di non aver fatto palestra ultimamente, "
          + "abbandoni subito l'idea. Ti sembra che l'uomo abbia lasciato cadere qualcosa sul ponte." + "\n");
        flags[1] = true;
      }
    }
    if(!flags[2]){
      if(inventory.contains(objList.getById(58)) && move == true){
        beerCounter++;
        if(beerCounter > 6){//nel refrigeratore aggiungi una birra
          beerCounter = 0;
          contList.getById(59).add(objList.getById(58));
          inventory.remove(objList.getById(58));
          out.append("La birra ha sudato troppo. Ti sembra che non abbia più portarla con te. Chissà se al bar ne hanno qualcun'altra fresca!" + "\n");
        }
      }
    }
    if(!flags[3]){
      if(p.getCommand().getType() == CommandType.USE 
        && p.getObject().getId() == objList.getById(55).getId() 
        && !inventory.contains(objList.getById(61))){//usa il pc ti spedisce una pala
        out.append("Il pc sembra bloccato su una pagina di un noto rivenditore online raffigurante una vanga di lusso. *Pensi che se avessi dei soldi la compreresti*");
      } else if(p.getCommand().getType() == CommandType.USE && p.getObject().getId() == objList.getById(55).getId() && inventory.contains(objList.getById(61))){
        out.append("Ti sembra il caso di approfittare dell'offerta sul sito e guarda caso hai anche trovato un portafoglio ricolmo di soldi. "
          + "Ti sembra di esserti integrato per bene nell'ambiente cittadino dal momento che a Wutond nessuno si fa problemi nell'utilizzare soldi altrui."
          + "Non appena compri la vanga, abbandoni il portafoglio per non destare sospetti."
          + "Ti avvii verso l'uscita e alle tue spalle compare il corriere con la pala."
          + "Non ti fai troppe domande e la prendi");
        inventory.add(objList.getById(80));//add pala all'inventario
        flags[3] = true;
      }

    } 

    System.out.print(inventory.contains(objList.getById(53)));
  }

  @Override
  public void execute() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
