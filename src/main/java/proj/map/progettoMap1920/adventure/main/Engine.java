package proj.map.progettoMap1920.adventure.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import proj.map.progettoMap1920.adventure.games.Wutond;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.CommandType;
import proj.map.progettoMap1920.adventure.utils.Gui;
import proj.map.progettoMap1920.adventure.utils.LockT;

import javax.swing.*;

public class Engine {

  private final GameDescription game;
  private final Parser parser;
  private final Gui gui;
  //private final Object lock = new Object();

  public Engine(GameDescription game) {
    this.game = game;
    try {
      this.game.init();
    }
    catch(FileNotFoundException e) {
      System.err.print(e);
    }
    catch(IOException e) {
      System.err.print(e);

    }
    parser = new ItParser();//italian parser
    gui = new Gui();
    gui.setVisible(true);
  }

  public void run() {
    JTextArea output = gui.getOutputArea();
    JTextArea input = gui.getInputArea();
    output.append(game.getCurrentRoom().getName() + '\n');
    output.append("================================================" + '\n');
    output.append(game.getCurrentRoom().getDescription() + '\n');
    //System.out.println(game.getCurrentRoom().getName());
    //System.out.println("================================================");
    //System.out.println(game.getCurrentRoom().getDescription());
    Scanner scanner = new Scanner(System.in);

    while (true) {
      synchronized(LockT.lock) {
        try {
          LockT.lock.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      String command = gui.getString();
      List<AdvObject> containerItems = new ArrayList<>();
      for(AdvObject a : game.getCurrentRoom().getObjects_list()) {
        if(a instanceof AdvObjectContainer) {
          if(((AdvObjectContainer) a).isOpened()) {
            containerItems.addAll(((AdvObjectContainer) a).getList());
          }
        }
      }
      ParserOutput p = parser.parse(command, game.getInventory().getList(), game.getCurrentRoom().getObjects_list(), containerItems,game.getCurrentRoom().getNpc_list(),game.getCommands(),game.getArticles(),game.getPrepositions(),game.getParticles(),game.getGrammar());
      if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
        output.append("Addio!");
        //scanner.close();
        break;
      } else {
        game.nextMove(p, output);

        output.append("================================================" + '\n');
        gui.setString(null);
      }
    }
  }

  public static void main(String[] args) {
    Engine engine = new Engine(new Wutond());
    engine.run();
  }

}
