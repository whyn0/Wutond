/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import proj.map.progettoMap1920.adventure.utils.ParseTreeNode;

/**
 *
 * @author whyno
 */
public class Cyk {

  private final CFGrammar grammar;

  public Cyk(CFGrammar grammar) {
    this.grammar = grammar;
  }

  public boolean parse(List<String> string) {
    int wordLength = string.size();
    ParsePossibilities[][] matrix = new ParsePossibilities[wordLength][wordLength];//matrice quadrata triangolare inferiore dove lunghezza = string.lenght()

    for (int y = 0; y < wordLength; y++) {
      for (int x = 0; x <= y; x++) {
        matrix[y][x] = new ParsePossibilities();
      }
    }//per ogni cella della matrice viene creata una lista di ParseTreeNode in maniera 
    /*
     * x o o o
     * x x o o
     * x x x o
     * x x x x
     * dove x sono le celle da considerare e inizializzate
     */
    for (int x = 0; x < wordLength; x++) {//analizzo la riga più bassa, è necessario trovare solo produzioni del tipo : START -> terminale
      ParsePossibilities possibilities = matrix[wordLength - 1][x];
      List<Production> newPossibilities = findProductions(string.get(x));//trovo le produzioni del tipo START -> string.get(x)

      newPossibilities.forEach(production -> possibilities.addPossibility(//per ogni produzione in newPossibilities, aggiungo nella cella, un nuovo ParseTreeNode con la produzione
        new ParseTreeNode(null, null, production)));
    }

    for (int y = wordLength - 2; y >= 0; y--) {//per y che va dalla seconda riga fino all'ultima(quella in cima alla matrica e composta da una cella)

      for (int x = 0; x <= y; x++) {//per x che va da 0 a y (poichè devo cosiderare sottostringhe di grandezza incrementale)

        ParsePossibilities possibilities = matrix[y][x]; //matrix[y][x] da sinistra a destra sulla riga y dove x incrementa verso destra

        for (int combination = 1; combination < (wordLength - y); combination++) {//ci dice la lunghezza delle sottostringhe da cosiderare

          int firstY = wordLength - combination;

          int firstX = x;

          int secondY = y + combination;

          int secondX = x + combination;

          ParsePossibilities firstPossibilities = matrix[firstY][firstX];//ad esempio [2,0]
          ParsePossibilities secondPossibilities = matrix[secondY][secondX];//ad esempio [2,1] quando la wordlenght è 3 e la x = 0

          for (ParseTreeNode first : firstPossibilities.getPossibilities()) {//lista di parse tree node

            for (ParseTreeNode second : secondPossibilities.getPossibilities()) {

              String firstNonTerminal = first.getValue().getLeftSide().get().get(0);
              String secondNonTerminal = second.getValue().getLeftSide().get().get(0);

              List<Production> newPossibilities = findProductions(firstNonTerminal, secondNonTerminal);
              newPossibilities.forEach(production -> possibilities.addPossibility(
                new ParseTreeNode(first, second, production)));

            }

          }

        }

      }

    }
    boolean reType = false;//return type falso se non trovo alcuna produzione
    List<ParseTreeNode> p = matrix[0][0].getPossibilities().stream()
      .filter(node -> node.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart()))//prendo dalla cella 0 0 tutti quei nodi il cui left side sia uguale al simbolo di start
      .collect(Collectors.toList());
    for (ParseTreeNode pn : p) {
      if (pn.getValue().getRightSide().get().size() == 1 
        && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {//se il right side è terminale e il left side è start
        reType = true;
      } else if (pn.getValue().getRightSide().get().size() > 1 
        && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {
        reType = true;
      }
    }
    return reType;
  }

  private static class ParsePossibilities {//wrapper di una lista di parsetreenode(ovvero un albero binario i cui noti contengono produzioni)

    private final List<ParseTreeNode> possibilities = new ArrayList<>();

    public void addPossibility(ParseTreeNode possibility) {
      this.possibilities.add(possibility);
    }

    public List<ParseTreeNode> getPossibilities() {
      return new ArrayList<>(this.possibilities);
    }

  }


  private List<Production> findProductions(String first, String second) {

    return this.grammar.getProductions()
      .stream()
      .filter(
        production -> production.getRightSide().get().size() > 1 //almeno due simboli (per le produzioni in CNF solo due nonterminali)
        && production.getRightSide().get().get(0).equals(first) 
        && production.getRightSide().get().get(1).equals(second))
      .collect(Collectors.toList());
  }

  private List<Production> findProductions(String first) {

    return this.grammar.getProductions()
      .stream()
      .filter(production -> production.getRightSide().get().size() == 1 //right side terminale
      && production.getRightSide().get().get(0).equals(first))
      .collect(Collectors.toList());

  }
}
