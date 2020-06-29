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
import proj.map.progettoMap1920.adventure.type.SyntaxParticlesType;

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
      if (list.get(i).getName().matches(token)) {
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
      if (list.get(i).getName().toLowerCase().equals(token)) {
        index = i;
        break;
      }
    }
    return index;
  }

  @Override
  public ParserOutput parse(String command,
    List<AdvObject> inventory,
    List<AdvObject> room_items,
    List<AdvObject> cont_items,
    List<Npc> npc,
    List<Command> cmd_list,
    List<Article> articles,
    List<Preposition> prepositions,
    List<SyntaxParticles> particles,
    CFGrammar grammar) {

    ParserOutput pOutput = new ParserOutput();
    List<String> tokenlist_type = new ArrayList<>();
    List<Integer> index_list = new ArrayList<>();
    List<AdvObject> all_items = new ArrayList<>();
    boolean isExcept = false;// flag da attivare dopo aver trovato un except
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
        pOutput.setCommand(cmd_list.get(index));
      }
      if (index > -1) {
        index_list.add(index);
        if (token_list.length > 1) {
          for (int i = 1; i < token_list.length; i++) {

            if ((index = checkForArticle(token_list[i], articles)) >= 0) {
              index_list.add(index);
              // tokenlist_type.add("article");
              continue;
            } else if ((index = checkForPrep(token_list[i], prepositions)) >= 0) {
              index_list.add(index);
              // tokenlist_type.add("preposition");
              continue;
            } else if ((index = checkForItem(token_list[i], all_items)) >= 0) {
              index_list.add(index);
              if (all_items.get(index) instanceof AdvObjectContainer) {
                tokenlist_type.add("objcontainer");
                
                if (!isExcept) {
                  pOutput.setContainer((AdvObjectContainer) all_items.get(index));
                } else {
                  pOutput.getExObjects().add(all_items.get(index));
                }

              } else if (all_items.get(index) instanceof Door) {
                tokenlist_type.add("door");
                pOutput.setDoor((Door) all_items.get(index));

              } else if (all_items.get(index) instanceof AdvObject) {
                tokenlist_type.add("obj");
                // se il flag except == false ricerco l'oggetto nella specifica lista
                // e in caso di match, valido il rispettivo campo di ParserOutput
                if (!isExcept) {
                  int indexTemp;
                  if ((indexTemp = checkForItem(token_list[i], inventory)) > -1) {
                    pOutput.setInvObject(all_items.get(index));
                  } else if ((indexTemp = checkForItem(token_list[i], cont_items)) > -1) {
                    pOutput.setContainedObject(all_items.get(index));
                  } else if ((indexTemp = checkForItem(token_list[i], room_items)) > -1) {
                    pOutput.setObject(all_items.get(index));
                  }
                } else {
                  pOutput.getExObjects().add(all_items.get(index));
                }
              }

              continue;
            } else if ((index = checkForCommand(token_list[i], cmd_list)) >= 0) {

              switch (cmd_list.get(index).getType().toString()) {
                case "north":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "east":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "west":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "south":
                  index_list.add(index);
                  tokenlist_type.add("direction");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                default:
                  index_list.add(index);
                  tokenlist_type.add(cmd_list.get(index).getType().toString());
                  //pOutput.(cmd_list.get(index));-------------------------------------Probabile problema
                  break;
              }
              continue;
            } else if ((index = checkForNpc(token_list[i], npc)) >= 0) {
              index_list.add(index);
              tokenlist_type.add("npc");
              pOutput.setNpc(npc.get(index));
              continue;
            } else if ((index = checkForParticles(token_list[i], particles)) >= 0) {
              index_list.add(index);
              if (particles.get(index).getType().equals(SyntaxParticlesType.EXCEPT)) {
               // tokenlist_type.add("except");
                pOutput.setExcept(true);
                isExcept = true;
              } else {
               // tokenlist_type.add("all");
                pOutput.setAll(true);
              }
              tokenlist_type.add(particles.get(index).getType().toString());
              continue;
            }
            //
            // int validInput = 0;
            //
            if (index == -1) {
              try {
                if (token_list[i + 1] != null) {// se ho un oggetto composito di due parole
                  String temp = token_list[i] + " " + token_list[i + 1];
                  if ((index = checkForItem(temp, all_items)) >= 0) {
                    index_list.add(index);
                    if(!isExcept) {
                      if (all_items.get(index) instanceof AdvObjectContainer) {
                        tokenlist_type.add("objcontainer");
                        pOutput.setContainer((AdvObjectContainer) all_items.get(index));
                      } else if (all_items.get(index) instanceof Door) {
                        tokenlist_type.add("door");
                        pOutput.setDoor((Door) all_items.get(index));
                      } else if (all_items.get(index) instanceof AdvObject) {
                        tokenlist_type.add("obj");
                        int indexTemp;
                        if ((indexTemp = checkForItem(temp, inventory)) > -1) {
                          pOutput.setInvObject(all_items.get(index));
                        } else if ((indexTemp = checkForItem(temp, cont_items)) > -1) {
                          pOutput.setContainedObject(all_items.get(index));
                        } else if ((indexTemp = checkForItem(temp, room_items)) > -1) {
                          pOutput.setObject(all_items.get(index));
                      }
                    }
                    
                    i++;
                    continue;
                  } else {
                    tokenlist_type.add("obj");
                    pOutput.getExObjects().add(all_items.get(index));
                  }
                  }
                  if((index = checkForNpc(temp, npc)) >= 0) {
                    index_list.add(index);
                    tokenlist_type.add("npc");
                    pOutput.setNpc(npc.get(index));
                    i++;
                    continue;
                  }
                }
                //
              } catch (ArrayIndexOutOfBoundsException e) {

              }
            }
            if (index == -1) {
              // throw InvalidInputException(e);
            }

          }
        }
      }

    }
    //stampa
    for (String i : tokenlist_type) {
      System.out.println(i);
    }
    //verifica semantica
    Cky cky = new Cky(grammar);
    if(!cky.parse(tokenlist_type)) {
      pOutput = null;
    }
    //
    return pOutput;
  }

}
