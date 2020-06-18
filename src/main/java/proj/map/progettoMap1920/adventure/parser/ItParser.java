/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.List;

import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.Article;
import proj.map.progettoMap1920.adventure.type.Command;
import proj.map.progettoMap1920.adventure.type.Door;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Preposition;
import proj.map.progettoMap1920.adventure.type.SyntaxParticles;

/**
 *
 * @author whyno
 */
public class ItParser implements Parser {

  @Override
  public int checkForParticles(String token, List<SyntaxParticles> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public int checkForArticle(String token, List<Article> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  // || list.get(i).getAlias().contains(token)
  @Override
  public int checkForPrep(String token, List<Preposition> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public int checkForNpc(String token, List<Npc> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public int checkForCommand(String token, List<Command> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public int checkForItem(String token, List<AdvObject> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getName().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public List<String> parse(String command,
    List<AdvObject> inventory,
    List<AdvObject> room_items,
    List<AdvObject> cont_items,
    List<Npc> npc,
    List<Command> cmd_list,
    List<Article> articles,
    List<Preposition> prepositions,
    List<SyntaxParticles> particles) {

    List<String> tokenlist_type = new ArrayList<>();
    List<Integer> index_list = new ArrayList<>();
    List<AdvObject> all_items = new ArrayList<>();
    if (inventory != null) {
      all_items.addAll(inventory);
    }
    if (cont_items != null) {
      all_items.addAll(room_items);
    }
    if (cont_items != null) {
      all_items.addAll(cont_items);
    }

    String cmd = command.trim();
    String[] token_list = cmd.split("\\s+");// vai a nord
    int index;

    if (token_list.length > 0) {
      index = checkForCommand(token_list[0], cmd_list);
      if (index > -1) {
        tokenlist_type.add(cmd_list.get(index).getType().toString());
      }
      if (index > -1) {
        index_list.add(index);
        if (token_list.length > 1) {
          for (int i = 1; i < token_list.length; i++) {

            if ((index = checkForArticle(token_list[i], articles)) >= 0) {
              index_list.add(index);
              tokenlist_type.add("article");
              continue;
            } else if ((index = checkForPrep(token_list[i], prepositions)) >= 0) {
              index_list.add(index);
              tokenlist_type.add("preposition");
              continue;
            } else if ((index = checkForItem(token_list[i], all_items)) >= 0) {
              index_list.add(index);
              if (all_items.get(index) instanceof AdvObjectContainer) {
                tokenlist_type.add("objcontainer");

              } else if (all_items.get(index) instanceof Door) {
                tokenlist_type.add("door");

              } else if (all_items.get(index) instanceof AdvObject) {
                tokenlist_type.add("object");

              }
              
              continue;
            } else if ((index = checkForCommand(token_list[i], cmd_list)) >= 0) {

              switch (cmd_list.get(index).getType().toString()) {
                case "north":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  break;
                case "east":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  break;
                case "west":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  break;
                case "south":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  break;
                default:
                  index_list.add(index);
                  tokenlist_type.add(cmd_list.get(index).getType().toString());
                  break;
              }
              continue;
            } else if ((index = checkForNpc(token_list[i], npc)) >= 0) {
              index_list.add(index);
              tokenlist_type.add("npc");
              continue;
            } else if ((index = checkForParticles(token_list[i], particles)) >= 0) {
              index_list.add(index);
              tokenlist_type.add(particles.get(index).getType().toString());
              continue;
            }
            //
            //int validInput = 0;
            //
            if(index == -1) {
              try {
                if(token_list[i+1] != null) {//se ho un oggetto composito di due parole 
                  String temp = token_list[i] + " " + token_list[i+1];
                  if ((index = checkForItem(temp, all_items)) >= 0) {
                    index_list.add(index);
                    if (all_items.get(index) instanceof AdvObjectContainer) {
                      tokenlist_type.add("objcontainer");

                    } else if (all_items.get(index) instanceof Door) {
                      tokenlist_type.add("door");

                    } else if (all_items.get(index) instanceof AdvObject) {
                      tokenlist_type.add("object");

                    }
                    i++;
                    continue;
                  }
                }
                //
              }
              catch(ArrayIndexOutOfBoundsException e) {
                
              }
            }
            if(index == -1) {
             // throw InvalidInputException(e);
            }
            
          }
        }
      }

    }
    for (String i : tokenlist_type) {
      System.out.println(i);
    }
    return tokenlist_type;
  }

}
