/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.List;

import proj.map.progettoMap1920.adventure.exceptions.NullOutputException;
import proj.map.progettoMap1920.adventure.parser.grammar.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.grammar.Cyk;
import proj.map.progettoMap1920.adventure.types.AdvObject;
import proj.map.progettoMap1920.adventure.types.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.types.Article;
import proj.map.progettoMap1920.adventure.types.Command;
import proj.map.progettoMap1920.adventure.types.Door;
import proj.map.progettoMap1920.adventure.types.GameObject;
import proj.map.progettoMap1920.adventure.types.GameUtil;
import proj.map.progettoMap1920.adventure.types.Npc;
import proj.map.progettoMap1920.adventure.types.Preposition;
import proj.map.progettoMap1920.adventure.types.SyntaxParticles;
import proj.map.progettoMap1920.adventure.types.SyntaxParticlesType;

/**
 *
 * @author whyno
 */
public class ItParser implements Parser {
  @Override
  public int checkGrammar(String token, List<? extends GameUtil> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (list.get(i).getName().toLowerCase().equals(token)
          || list.get(i).getName().equals(token)
          || list.get(i).getAlias().contains(token)
          || list.get(i).getAlias().contains(token.toLowerCase())) {
          index = i;
          break;
        }
      }
    }

    return index;
  }

  @Override
  public int checkElement(String token, List<? extends GameObject> list) {
    int index = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (list.get(i).getName().toLowerCase().equals(token)
          || list.get(i).getName().equals(token)
          || list.get(i).getAlias().contains(token)
          || list.get(i).getAlias().contains(token.toLowerCase())) {
          index = i;
          break;
        }
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
    CFGrammar grammar) throws NullOutputException {

    ParserOutput pOutput = new ParserOutput();
    List<String> tokenlist_type = new ArrayList<>();
    List<Integer> index_list = new ArrayList<>();
    List<AdvObject> all_items = new ArrayList<>();
    boolean isExcept = false;// flag da attivare dopo aver trovato un except
    if (inventory != null) {//creo una lista di tutti gli oggetti nella stanza
      all_items.addAll(inventory);
    }
    if (cont_items != null) {
      all_items.addAll(room_items);
    }
    if (cont_items != null) {
      all_items.addAll(cont_items);
    }

    String cmd = command.trim();
    String[] token_list = cmd.split("\\s+");
    int index;

    if (token_list.length > 0) {//la prima cosa da fare è assicurarsi che il primo token sia effettivamente un comando
      index = checkGrammar(token_list[0], cmd_list);
      if (index > -1) {
        tokenlist_type.add(cmd_list.get(index).getType().toString());
        pOutput.setCommand(cmd_list.get(index));
      }
      if (index > -1) {
        index_list.add(index);
        if (token_list.length > 1) {
          for (int i = 1; i < token_list.length; i++) {

            if ((index = checkGrammar(token_list[i], articles)) >= 0) {//articoli e preposizioni vengono ignorati
              index_list.add(index);
              continue;
            } else if ((index = checkGrammar(token_list[i], prepositions)) >= 0) {
              index_list.add(index);
              continue;
            } else if ((index = checkElement(token_list[i], all_items)) >= 0) {//se l'oggetto è un item bisogna distinguerlo e classificarlo
              index_list.add(index);
              if (all_items.get(index) instanceof AdvObjectContainer) {

                tokenlist_type.add("objcontainer");
                if (!isExcept) {

                  if (checkElement(token_list[i], inventory) > -1) {
                    pOutput.setInvObject(all_items.get(index));
                  } else if (checkElement(token_list[i], cont_items) > -1) {
                    pOutput.setContainedObject(all_items.get(index));
                  } else if (checkElement(token_list[i], room_items) > -1) {
                    pOutput.setContainer((AdvObjectContainer) all_items.get(index));
                  }

                } else {
                  List<AdvObject> temp = new ArrayList<>();
                  temp.addAll(all_items);
                  for (AdvObject a : all_items) {
                    if (inventory.contains(a)) {
                      temp.remove(a);
                    }
                  }
                  if (temp.contains(all_items.get(index))) {
                    pOutput.getExObjects().add(all_items.get(index));
                  }
                }

              } else if (all_items.get(index) instanceof Door) {
                tokenlist_type.add("door");
                pOutput.setDoor((Door) all_items.get(index));

              } else if (all_items.get(index) instanceof AdvObject) {
                tokenlist_type.add("obj");
                // se il flag except == false ricerco l'oggetto nella specifica lista
                // e in caso di match, valido il rispettivo campo di ParserOutput
                if (!isExcept) {

                  if (checkElement(token_list[i], inventory) > -1) {

                    pOutput.setInvObject(all_items.get(index));

                  } else if (checkElement(token_list[i], cont_items) > -1) {

                    pOutput.setContainedObject(all_items.get(index));

                  } else if (checkElement(token_list[i], room_items) > -1) {

                    pOutput.setObject(all_items.get(index));
                  }
                } else {
                  List<AdvObject> temp = new ArrayList<>();
                  temp.addAll(all_items);
                  for (AdvObject a : all_items) {
                    if (inventory.contains(a)) {
                      temp.remove(a);
                    }
                  }
                  if (temp.contains(all_items.get(index))) {
                    pOutput.getExObjects().add(all_items.get(index));
                  }
                }
              }

              continue;
            } else if ((index = checkGrammar(token_list[i], cmd_list)) >= 0) {

              switch (cmd_list.get(index).getType().toString()) {
                case "north":
                  index_list.add(index);
                  tokenlist_type.add("north");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "east":
                  index_list.add(index);
                  tokenlist_type.add("east");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "west":
                  index_list.add(index);
                  tokenlist_type.add("west");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                case "south":
                  index_list.add(index);
                  tokenlist_type.add("south");
                  pOutput.setDirection(cmd_list.get(index));
                  break;
                default:
                  index_list.add(index);
                  tokenlist_type.add(cmd_list.get(index).getType().toString());
                  break;
              }
              continue;
            } else if ((index = checkElement(token_list[i], npc)) >= 0) {
              index_list.add(index);
              tokenlist_type.add("npc");
              pOutput.setNpc(npc.get(index));
              continue;
            } else if ((index = checkGrammar(token_list[i], particles)) >= 0) {
              index_list.add(index);
              if (particles.get(index).getType().equals(SyntaxParticlesType.EXCEPT)) {
                pOutput.setExcept(true);
                isExcept = true;
              } else {
                pOutput.setAll(true);
              }
              tokenlist_type.add(particles.get(index).getType().toString());//aggiungo all / except a tokenlisttype
              continue;
            }
            if (index == -1) {
              try {
                if (token_list[i + 1] != null) {// se ho un oggetto composito di due parole
                  String temp = token_list[i] + " " + token_list[i + 1];
                  if ((index = checkElement(temp, all_items)) >= 0) {
                    index_list.add(index);
                    if (!isExcept) {
                      if (all_items.get(index) instanceof AdvObjectContainer) {
                        tokenlist_type.add("objcontainer");
                        pOutput.setContainer((AdvObjectContainer) all_items.get(index));
                      } else if (all_items.get(index) instanceof Door) {
                        tokenlist_type.add("door");
                        pOutput.setDoor((Door) all_items.get(index));
                      } else if (all_items.get(index) instanceof AdvObject) {
                        tokenlist_type.add("obj");

                        if (checkElement(temp, inventory) > -1) {
                          pOutput.setInvObject(all_items.get(index));
                        } else if (checkElement(temp, cont_items) > -1) {
                          pOutput.setContainedObject(all_items.get(index));
                        } else if (checkElement(temp, room_items) > -1) {
                          pOutput.setObject(all_items.get(index));
                        }
                      }

                      i++;
                      continue;
                    } else {
                      tokenlist_type.add("obj");
                      List<AdvObject> tempList = new ArrayList<>();
                      tempList.addAll(all_items);
                      for (AdvObject a : all_items) {
                        if (inventory.contains(a)) {
                          tempList.remove(a);
                        }
                      }
                      if (tempList.contains(all_items.get(index))) {
                        pOutput.getExObjects().add(all_items.get(index));
                      }
                    }
                  }
                  if ((index = checkElement(temp, npc)) >= 0) {
                    index_list.add(index);
                    tokenlist_type.add("npc");
                    pOutput.setNpc(npc.get(index));
                    i++;
                    continue;
                  }
                }
                //
              } catch (ArrayIndexOutOfBoundsException e) {
                throw new NullOutputException(pOutput);
              }
            }

          }
        }
      }

    }
    // verifica sintattica
    Cyk cky = new Cyk(grammar);
    if (!cky.parse(tokenlist_type)) {
      throw new NullOutputException(pOutput);
    }
    //
    return pOutput;
  }

}
