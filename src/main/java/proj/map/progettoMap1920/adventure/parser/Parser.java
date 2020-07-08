/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.List;

import proj.map.progettoMap1920.adventure.exceptions.NullOutputException;
import proj.map.progettoMap1920.adventure.parser.grammar.CFGrammar;
import proj.map.progettoMap1920.adventure.types.AdvObject;
import proj.map.progettoMap1920.adventure.types.Article;
import proj.map.progettoMap1920.adventure.types.Command;
import proj.map.progettoMap1920.adventure.types.GameObject;
import proj.map.progettoMap1920.adventure.types.GameUtil;
import proj.map.progettoMap1920.adventure.types.Npc;
import proj.map.progettoMap1920.adventure.types.Preposition;
import proj.map.progettoMap1920.adventure.types.SyntaxParticles;

/**
 *
 * @author whyno
 * @param <E>
 */
public interface Parser {
  int checkGrammar(String token, List<? extends GameUtil> list);

  int checkElement(String token, List<? extends GameObject> list);

  /*
   * int checkForArticle(String token,List<Article> list);
   * int checkForPrep(String token,List<Preposition> list);
   * int checkForNpc(String token,List<Npc> list);
   * int checkForCommand(String token,List<Command> list);
   * int checkForItem(String token,List<AdvObject> list);
   * int checkForParticles(String token, List<SyntaxParticles> list);
   */
  public ParserOutput parse(String command,
    List<AdvObject> inventory,
    List<AdvObject> room_items,
    List<AdvObject> cont_items,
    List<Npc> npc,
    List<Command> cmd_list,
    List<Article> articles,
    List<Preposition> prepositions,
    List<SyntaxParticles> particles,
    CFGrammar grammar) throws NullOutputException;
}
