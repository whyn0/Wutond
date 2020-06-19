package proj.map.progettoMap1920.adventure.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;

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
        //ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory());
        if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
            System.out.println("Addio!");
            break;
        } else {
            game.nextMove(p, System.out);
            System.out.println("================================================");
        }
    }
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
