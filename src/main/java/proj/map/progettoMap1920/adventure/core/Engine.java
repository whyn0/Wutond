package proj.map.progettoMap1920.adventure.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import proj.map.progettoMap1920.adventure.exceptions.EOGameException;
import proj.map.progettoMap1920.adventure.exceptions.NullOutputException;
import proj.map.progettoMap1920.adventure.games.Wutond;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.types.AdvObject;
import proj.map.progettoMap1920.adventure.types.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.types.CommandType;
import proj.map.progettoMap1920.adventure.gui.Gui;


import proj.map.progettoMap1920.adventure.utils.LockT;

public class Engine {

  private final GameDescription game;
  private final Parser parser;
  private final Gui gui;

  public Engine(GameDescription game) {
    gui = new Gui();
    this.game = game;
    try {
      this.game.init();
    } catch (FileNotFoundException e) {
      System.err.print(e);
    } catch (IOException e) {
      System.err.print(e);

    }
    parser = new ItParser();// italian parser

    gui.setVisible(true);
  }

  public void run() {//metodo principale
    boolean restart = true; //viene settato false in caso di termine gioco con risposta negativa
    while (restart) {
      int ending = -1;
      JTextArea output = gui.getOutputArea();
      output.append(intro());
      separator();
      output.append(game.getCurrentRoom().getName().toUpperCase());
      separator();
      output.append(game.getCurrentRoom().getDescription());
      separator();

      while (true) {
        boolean flag = true;

        ((DefaultCaret) output.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        synchronized (LockT.lock) {//lock per garantire sequenzialità
          try {
            LockT.lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        String command = gui.getString();
        command = clearInput(command);//pulizia preliminare dell'input dalle virgole e dalle congiunzioni

        List<AdvObject> containerItems = new ArrayList<>();//lista degli oggetti contenuti nei container della stanza
        for (AdvObject a : game.getCurrentRoom().getObjects_list()) {
          if (a instanceof AdvObjectContainer) {
            if (((AdvObjectContainer) a).isOpened()) {
              containerItems.addAll(((AdvObjectContainer) a).getList());
            }
          }
        }
        ParserOutput p = new ParserOutput();
        try {
          p = parser.parse(command,
            game.getInventory().getList(),
            game.getCurrentRoom().getObjects_list(),
            containerItems,
            game.getCurrentRoom().getNpc_list(),
            game.getCommands(),
            game.getArticles(),
            game.getPrepositions(),
            game.getParticles(),
            game.getGrammar());
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          System.out.print(e);
        } catch (NullOutputException e1) {
          output.append(e1.getMsg());
          separator();
          flag = false;
        }

        if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
          output.append("Addio!");
          break;
        } else {
          if (flag) {
            try {
              game.nextMove(p, gui);
            } catch (EOGameException e) {//eccezione che triggera la fine del gioco
              separator();
              separator();
              ending = e.getCode();
              break;
            }
            output.append("\n");
            separator();
          }

          gui.setString(null);
        }
      }
      if (ending == 0) {
        output.append("Hai terminato il gioco da morto, che peccato!");
        separator();
      } else if (ending == 1) {
        output.append("Complimenti, hai vinto!");
        separator();
      }
      output.append("Vuoi ricominciare? (scrivi si/no)" + "\n");
      synchronized (LockT.lock) {
        try {
          LockT.lock.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      String ans = gui.getString();
      ans = ans.trim();
      ans = ans.strip();
      if (ans.matches("no")) {
        restart = false;
      } else {
        try {
          output.setText("");
          game.clearList();
          game.init();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }
    gui.setVisible(false);
    System.exit(0);
  }

  public String intro() {
    String intro = "Sei Alfonso Paccagnello, un detective padovano. " + "\n"
      + "Sei stato inviato nell'apparententemente tranquilla cittadina di Wutond " + "\n"
      + "per risolvere il mistero dell'omicidio del proprietario del più famoso bar cittadino: " + "\n"
      + "il bar Castello .Ti trovi in questura completamente solo." + "\n"
      + "Non hai nulla con te se non il tuo intuito, inizi a guardarti intorno...";
    return intro;
  }

  private void separator() {
    this.gui.getOutputArea().append("\n");
    for (int i = 0; i < 61; i++) {
      this.gui.getOutputArea().append("=");
    }
    this.gui.getOutputArea().append("\n");
  }
  private String clearInput(String s) {
    StringBuilder blank = new StringBuilder();
    s = s.replaceAll(",", "");   
    s = s.replaceAll("l'","");
    String[] token = s.split("\\s+");

    if(token.length > 1) {
      for(int i = 0; i < token.length; i++){
        if(!token[i].equals("e")){
          if(i != token.length -1){
            blank.append(token[i] + " ");
          } else {
            blank.append(token[i]);
          }
        }
      }
    } else {
      blank.append(token[0]);
    }


    return blank.toString();
  }


  public static void main(String[] args) {
    Engine engine = new Engine(new Wutond());
    engine.run();
  }


}
