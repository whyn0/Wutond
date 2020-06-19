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
  
  private List<Command> cmd_list = new ArrayList<>();
  private List<Article> articles_list = new ArrayList<>();
  private List<Preposition> prepositions_list = new ArrayList<>();
  private List<SyntaxParticles> particles_list = new ArrayList<>();
  
  public GameUtilInit(String... paths) throws FileNotFoundException, IOException{
    articleReader(paths[0]);
    commandReader(paths[1]);
    prepositionReader(paths[2]);
    particleReader(paths[3]);
  }
  
  private void commandReader(String filename) throws FileNotFoundException, IOException {
    //-----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    Set<String> alias = new HashSet<>();
    String name = null;
    CommandType ct = null;
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
        input = buffer.readLine();
      }

      cmd_list.add(new Command(ct,name,new HashSet<String>(alias)));
      alias.clear();
    }
    file.close();
  }
  private void articleReader(String filename) throws FileNotFoundException, IOException {
    //-----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    Set<String> alias = new HashSet<>();
    String name = null;
    ArticleType at = null;
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
            case "the":
              at = ArticleType.THE;
          }
        }
        else if(tokenized[0].equals("NAME")) {
          name = tokenized[1];
        }
        else if(tokenized[0].equals("ALIAS")) {
          String[] tk1 = tokenized[1].split("\\s");
          alias.addAll(Arrays.asList(tk1));
        }
        input = buffer.readLine();
      }
      articles_list.add(new Article(at,name,new HashSet<String>(alias)));
      alias.clear();
    }
    file.close();
  }
  private void prepositionReader(String filename) throws FileNotFoundException,IOException {
  //-----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    Set<String> alias = new HashSet<>();
    String name = null;
    PrepositionType pt = null;
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
            case "with":
              pt = PrepositionType.WITH;
              break;
            case "through":
              pt = PrepositionType.THROUGH;
              break;
            case "towards":
              pt = PrepositionType.TOWARDS;
              break;
            case "on":
              pt = PrepositionType.ON;
              break;
            case "in":
              pt = PrepositionType.IN;
              break;
            case "to":
              pt = PrepositionType.TO;
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
        input = buffer.readLine();
      }
      prepositions_list.add(new Preposition(pt,name,new HashSet<String>(alias)));
      alias.clear();
    }
    file.close();
  }
  private void particleReader(String filename) throws FileNotFoundException,IOException {
  //-----------------------------
    FileReader file;
    BufferedReader buffer;
    file = new FileReader(filename);
    buffer = new BufferedReader(file);
    //
    Set<String> alias = new HashSet<>();
    String name = null;
    SyntaxParticlesType st = null;
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
            case "all":
              st = SyntaxParticlesType.ALL;
              break;
            case "except":
              st = SyntaxParticlesType.EXCEPT;
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
        input = buffer.readLine();
      }
      
      particles_list.add(new SyntaxParticles(st,name,new HashSet<>(alias)));
      alias.clear();
    }
    file.close();
  }

  
  public List<Command> getCmd_list() {
    return cmd_list;
  }

  public List<Article> getArticles_list() {
    return articles_list;
  }

  public List<Preposition> getPrepositions_list() {
    return prepositions_list;
  }

  public List<SyntaxParticles> getParticles_list() {
    return particles_list;
  }

  
}

