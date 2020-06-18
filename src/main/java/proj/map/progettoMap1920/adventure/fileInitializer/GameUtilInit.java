package proj.map.progettoMap1920.adventure.fileInitializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import proj.map.progettoMap1920.adventure.type.*;

public class GameUtilInit {
  
  List<Command> cmd_list = new ArrayList<>();
  List<Article> articles_list = new ArrayList<>();
  List<Preposition> prepositions_list = new ArrayList<>();
  List<SyntaxParticles> particles_list = new ArrayList<>();
  public GameUtilInit() {
    // TODO Auto-generated constructor stub
  }
  
  public void commandReader(String filename) throws FileNotFoundException, IOException {
    //-----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    Set<String> alias = new HashSet<>();
    String name;
    CommandType ct;
    //
    String input;
    String[] tokenized;
    //--------------
    while((input = buffer.readLine())!=null) {
      while(!input.equals("}")) {
        if(input.equals("{")) {
          input = buffer.readLine();
        }
        tokenized = input.split(" : ");
        for(String t : tokenized) {
          t = t.trim();
        }
        if(tokenized[0].equals("TYPE")) {
          switch(tokenized[1]) {
            case "pick_up":
              ct = CommandType.PICK_UP;
              break;
            case "walk_to":
              ct = CommandType.WALK_TO;
              break;
            case "north":
              ct = CommandType.NORTH;
              break;
            case "south":
              ct = CommandType.SOUTH;
              break;
            case "east":
              ct = CommandType.EAST;
              break;
            case "west":
              ct = CommandType.WEST;
              break;
            case "end":
              ct = CommandType.END;
              break;
            case "open":
              ct = CommandType.OPEN;
              break;
            case "talk_to":
              ct = CommandType.TALK_TO;
              break;
            case "use":
              ct = CommandType.USE;
              break;
            case "look_at":
              ct = CommandType.LOOK_AT;
              break;
            case "give":
              ct = CommandType.GIVE;
              break;
            case "curse":
              ct = CommandType.CURSE;
              break;
            case "inventory":
              ct = CommandType.INVENTORY;
              break;
            case "kill":
              ct = CommandType.KILL;
              break;
            case "save":
              ct = CommandType.SAVE;
              break;
            case "load":
              ct = CommandType.LOAD;
              break;
          }
        }
        
        else if(tokenized[0].equals("NAME")) {
          name = tokenized[1];
        }
        else if(tokenized[0].equals("ALIAS")) {
          String[] tk1 = tokenized[1].split("\\s");
          alias.addAll(Arrays.asList(tk1));
        }
      }
    }
  }
}
