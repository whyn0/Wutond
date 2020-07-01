package proj.map.progettoMap1920.adventure.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import proj.map.progettoMap1920.adventure.games.Wutond;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.CommandType;

public class Engine {
  
  private final GameDescription game;
  private final Parser parser;
  
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
  }
  
  public void run() {
    System.out.println(game.getCurrentRoom().getName());
    System.out.println("================================================");
    System.out.println(game.getCurrentRoom().getDescription());
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()) {
        String command = scanner.nextLine();
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
            System.out.println("Addio!");
            scanner.close();
            break;
        } else {
            game.nextMove(p, System.out);
            System.out.println("================================================");
        }
    }
  }

  public static void main(String[] args) {
    Engine engine = new Engine(new Wutond());
    engine.run();
  }

}
