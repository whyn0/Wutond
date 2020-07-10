/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.events;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import proj.map.progettoMap1920.adventure.exceptions.EOGameException;
import proj.map.progettoMap1920.adventure.exceptions.NullOutputException;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.parser.grammar.CFGrammar;
import proj.map.progettoMap1920.adventure.types.AdvObject;
import proj.map.progettoMap1920.adventure.types.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.types.Article;
import proj.map.progettoMap1920.adventure.types.Command;
import proj.map.progettoMap1920.adventure.types.CommandType;
import proj.map.progettoMap1920.adventure.types.Dialog;
import proj.map.progettoMap1920.adventure.types.Door;
import proj.map.progettoMap1920.adventure.types.Lock;
import proj.map.progettoMap1920.adventure.types.Npc;
import proj.map.progettoMap1920.adventure.types.Preposition;
import proj.map.progettoMap1920.adventure.types.Room;
import proj.map.progettoMap1920.adventure.types.SyntaxParticles;
import proj.map.progettoMap1920.adventure.utils.GameList;
import proj.map.progettoMap1920.adventure.gui.Gui;
import proj.map.progettoMap1920.adventure.utils.LockT;

/**
 *
 * @author whyno
 */
public class EventHandler implements EventInterface, Serializable {
  private GameList<AdvObject> inventory;
  private GameList<AdvObject> objList;
  private GameList<AdvObjectContainer> contList;
  private GameList<Npc> npcList;
  private GameList<Door> doorList;
  private GameList<Lock> lockList;
  private GameList<Room> roomList;
  private GameList<Dialog> dialogList;

  private List<Article> articles;
  private List<Command> commands;
  private List<Preposition> preposition;
  private List<SyntaxParticles> particles;
  private CFGrammar grammar;

  private int beerCounter = 0;
  private boolean[] flags = new boolean[10];

  public EventHandler(GameList<AdvObject> inventory,
    GameList<AdvObject> objList,
    GameList<AdvObjectContainer> contList,
    GameList<Npc> npcList,
    GameList<Door> doorList,
    GameList<Lock> lockList,
    GameList<Room> roomList,
    GameList<Dialog> dialogList,
    List<Article> articles,
    List<Command> commands,
    List<Preposition> preposition,
    List<SyntaxParticles> particles,
    CFGrammar grammar) {
    this.inventory = inventory;
    this.objList = objList;
    this.contList = contList;
    this.npcList = npcList;
    this.doorList = doorList;
    this.lockList = lockList;
    this.roomList = roomList;
    this.dialogList = dialogList;
    this.articles = articles;
    this.commands = commands;
    this.preposition = preposition;
    this.particles = particles;
    this.grammar = grammar;
  }

  @Override
  public void check(ParserOutput p, Room currentRoom, boolean move, Gui gui) throws EOGameException {
    JTextArea out = gui.getOutputArea();
    JTextArea in = gui.getInputArea();
    // se possiedo dizionario - > npc comprensibili
    if (!flags[0]) {
      if (inventory.contains(objList.getById(53))) { // quando l'inventario contiene il dizionario sei in grado di comprendere tutti
        for (Iterator<Npc> it = npcList.iterator(); it.hasNext();) {
          Npc n = it.next();
          if (!n.isUnderstandable()) {
            n.setUnderstandable(true);
          }
          if (n.getId() == 67) {
            n.setDialog(null);
          }
        }
        out.append("\n" + "Ora puoi comprendere tutti i cittadini!" + "\n");
        ((DefaultCaret) out.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        flags[0] = true;
      }
    }
    if (!flags[1]) {
      if (inventory.contains(objList.getById(52)) && currentRoom.equals(roomList.getById(5)) && npcList.getById(68).isSpoken()) {// se
                                                                                                                                 // l'inventario
                                                                                                                                 // contiene
                                                                                                                                 // la
                                                                                                                                 // pistola
                                                                                                                                 // e la
                                                                                                                                 // currentroom
                                                                                                                                 // ==
                                                                                                                                 // orologio
        roomList.getById(7).getObjects_list().add(objList.getById(61));// aggiungo alla lista degli oggetti del ponte il portafogli
        out.append("Vedi un uomo vestito con una tutina nera e un cappotto scappare dentro il torrione."
          + " Provi ad inseguirlo ma ti ricordi di non aver fatto palestra ultimamente, "
          + "abbandoni subito l'idea. Ti sembra che l'uomo abbia lasciato cadere qualcosa sul ponte." + "\n");
        flags[1] = true;
      }
    }
    if (!flags[2]) {
      if (inventory.contains(objList.getById(58)) && move == true) {
        beerCounter++;
        if (beerCounter > 6) {// nel refrigeratore aggiungi una birra
          beerCounter = 0;
          contList.getById(59).add(objList.getById(58));
          inventory.remove(objList.getById(58));
          out.append("La birra ha sudato troppo. Ti sembra che non abbia più portarla con te. Chissà se al bar ne hanno qualcun'altra fresca!" + "\n");
        }
      }
    }
    if (!flags[3]) {
      if (p.getObject() != null) {
        if (p.getCommand().getType() == CommandType.USE
          && p.getObject().getId() == objList.getById(55).getId()
          && !inventory.contains(objList.getById(61))) {// usa il pc ti spedisce una pala
          out.append("Il pc sembra bloccato su una pagina di un noto rivenditore online raffigurante una vanga di lusso. *Pensi che se avessi dei soldi la compreresti*");
        } else if (p.getCommand().getType() == CommandType.USE && p.getObject().getId() == objList.getById(55).getId() && inventory.contains(objList.getById(61))) {
          out.append("Ti sembra il caso di approfittare dell'offerta sul sito e guarda caso hai anche trovato un portafoglio ricolmo di soldi. "
            + "\n" + "Ti sembra di esserti integrato per bene nell'ambiente cittadino dal momento che a Wutond nessuno si fa problemi nell'utilizzare soldi altrui."
            + "\n" + "Non appena compri la vanga, abbandoni il portafoglio per non destare sospetti."
            + "\n" + "Ti avvii verso l'uscita e alle tue spalle compare il corriere con la pala."
            + "\n" + "Non ti fai troppe domande e la prendi");
          inventory.add(objList.getById(80));// add pala all'inventario
          flags[3] = true;
        }
      }
    }
    if (!flags[4]) {
      if (currentRoom.getId() == 42) {// la prima volta che si entra in piazza cattedrale
        roomList.getById(5).setSouth(roomList.getById(42)); // Una volta percorso il labirinto esso viene annullato per consentire uno
                                                            // spostamento agevolato
        currentRoom.setNorth(roomList.getById(5));
        out
          .append("Grazie alla tua memoria straordinaria, senti di aver imparato il percorso del labirinto." + "\n" + "Sai che la prossima volta lo farai ad occhi chiusi!" + "\n");
        flags[4] = true;
      }
    }
    if (!flags[5]) {
      if (p.getNpc() != null) {
        if (p.getCommand().getType() == CommandType.TALK_TO &&
          p.getNpc().getId() == 68 && p.getNpc().isUnderstandable()) {// la prima volta che si parla con ciccio in piazza orologio
          currentRoom.getNpc_list().remove(p.getNpc()); // rimuovo ciccio dalla stanza
          p.getNpc().setDialog(dialogList.getById(124));
          p.getNpc().setDescription("Vedi il tipo della piazza dell'orologio scomparso improvvisamente, ora molto più agitato" + "\n");
          p.getNpc().setLook("E' l'uomo della piazza di prima, sembra stia cercando qualcosa con evidente impazienza" + "\n");
          flags[5] = true;
        }
      }

    }
    if (!flags[6]) {
      if (p.getInvObject() != null) {
        if (p.getCommand().getType() == CommandType.USE && p.getInvObject().getId() == 51) {// usa polvere magica
          out.append("Oh no! Come ti è venuto in mente di usare quella robaccia?" + "\n" + "Il tuo cervello si è appena spappolato," + "\n" + "Addio!");
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
          }
          System.exit(0);
        }
      }
    }
    if (!flags[7]) {
      if (p.getInvObject() != null && p.getNpc() != null) {
        if (p.getCommand().getType() == CommandType.GIVE
          && p.getNpc().getId() == 68
          && p.getInvObject().getId() == 51
          && currentRoom.getId() == 9) {// give polvere to ciccio e ti trovi nel torrione
          inventory.remove(p.getInvObject());
          doorList.getById(77).setLock(null);// sblocco la porta
          out.append("Senti un rumore alle tue spalle, ti distrai un attimo" + "\n" + "Vedi la porta aprirsi ma Ciccio è scomparso...");
          currentRoom.getNpc_list().remove(p.getNpc());
          flags[7] = false;
        }
      }
    }
    if (!flags[8]) {
      if (p.getDoor() != null) {
        if (p.getCommand().getType() == CommandType.LOOK_AT &&
          p.getDoor().getId() == 76) {
          out.append("Inciso nella pietra riesci a scorgere una frase : " + "\n"
            + "C'nama scì ..... " + "\n"
            + "*il resto sembra sbiadito*" + "\n"
            + "(Capisci che devi scrivere la parola mancante per completare la frase)" + "\n");
          synchronized (LockT.lock) {
            try {
              LockT.lock.wait();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          String input = gui.getString();
          input = input.trim();
          input.strip();
          if (input.equals("sciamaninn")) {
            p.getDoor().setLock(null);
            out.append("Senti gli ingranaggi della porta muoversi!" + "\n" + "Ora è aperta!" + "\n");
            flags[8] = false;
          } else {
            out.append("Il marchingegno non si muove !" + "\n");
          }
        }
      }
    }
    if (!flags[9]) {
      if (p.getNpc() != null) {
        if (p.getCommand().getType() == CommandType.TALK_TO && p.getNpc().getId() == 64) {//parla al commissario
          out.append("*Hai dieci secondi per decidere cosa fare, sbrigati!*" + "\n");
          synchronized (LockT.lock) {
            try {
              LockT.lock.wait();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          String input = gui.getString();
          input = input.trim();
          input.strip();
          Parser parse = new ItParser();
          try {
            ParserOutput pout;
            try {
              pout = parse.parse(input,
                inventory.getList(),
                currentRoom.getObjects_list(),
                null,
                currentRoom.getNpc_list(),
                commands,
                articles,
                preposition,
                particles,
                grammar);

              if (pout.getNpc() != null && pout.getCommand().getType() == CommandType.KILL) { //uccidi il commissario
                if (pout.getCommand().getType() == CommandType.KILL && pout.getNpc().getId() == 64) {
                  out.append("Estrai la tua pistola e con prontezza abbatti il minaccioso Commissario" + "\n"
                    + "Forse non sarai ricordato come eroe da questa città ma per fortuna hai eliminato l'assassino!" + "\n"
                    + "Ti aspetta una bella promozione al grado di Commissario del distretto di polizia di Wutond" + "\n"
                    + "In bocca al lupo!");
                  resetFlags();
                  throw new EOGameException(1);
                }
              } else {
                out.append("Non hai fatto in tempo a salvarti, muori tra le più atroci sofferenze e nessuno lo verrà a sapere!");
                resetFlags();
                throw new EOGameException(0);
              }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
              out.append("Non hai fatto in tempo a salvarti, muori tra le più atroci sofferenze e nessuno lo verrà a sapere!");
              resetFlags();
              throw new EOGameException(0);
            }

          } catch (NullOutputException e) {

          }

        }
      }
    }
  }

  void resetFlags() {
    for (int i = 0; i < this.flags.length; i++) {
      this.flags[i] = false;
    }
  }
}
