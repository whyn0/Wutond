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
    ParsePossibilities[][] matrix = new ParsePossibilities[wordLength][wordLength];

    for (int y = 0; y < wordLength; y++) {
      for (int x = 0; x <= y; x++) {
        matrix[y][x] = new ParsePossibilities();
      }
    }

    for (int x = 0; x < wordLength; x++) {
      ParsePossibilities possibilities = matrix[wordLength - 1][x];
      List<Production> newPossibilities = findProductions(string.get(x));

      newPossibilities.forEach(production -> possibilities.addPossibility(
        new ParseTreeNode(null, null, production)));
    }

    for (int y = wordLength - 2; y >= 0; y--) {

      for (int x = 0; x <= y; x++) {

        ParsePossibilities possibilities = matrix[y][x];

        for (int combination = 1; combination < (wordLength - y); combination++) {

          int firstY = wordLength - combination;

          int firstX = x;

          int secondY = y + combination;

          int secondX = x + combination;

          ParsePossibilities firstPossibilities = matrix[firstY][firstX];
          ParsePossibilities secondPossibilities = matrix[secondY][secondX];

          for (ParseTreeNode first : firstPossibilities.getPossibilities()) {

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
    boolean reType = false;
    List<ParseTreeNode> p = matrix[0][0].getPossibilities().stream()
      .filter(node -> node.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart()))
      .collect(Collectors.toList());
    for (ParseTreeNode pn : p) {
      if (pn.getValue().getRightSide().get().size() == 1 && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {
        reType = true;
      } else if (pn.getValue().getRightSide().get().size() > 1 && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {
        reType = true;
      }
    }
    return reType;
  }

  private static class ParsePossibilities {

    private final List<ParseTreeNode> possibilities = new ArrayList<>();

    public void addPossibility(ParseTreeNode possibility) {
      this.possibilities.add(possibility);
    }

    public List<ParseTreeNode> getPossibilities() {
      return new ArrayList<>(this.possibilities);
    }

  }

  private static class Index {

    int a;
    int b;
    int c;
  }

  private int findIndex(String a) {
    int index = 0;

    for (String a1 : this.grammar.getNonTerminals()) {
      if (a1.equals(a)) {
        index = this.grammar.getNonTerminals().indexOf(a1);
      }

    }
    return index;
  }

  private List<Production> findProductions(String first, String second) {

    return this.grammar.getProductions()
      .stream()
      .filter(
        production -> production.getRightSide().get().size() > 1 && production.getRightSide().get().get(0).equals(first) && production.getRightSide().get().get(1).equals(second))
      .collect(Collectors.toList());

  }

  private List<Production> findProductions(String first) {

    return this.grammar.getProductions()
      .stream()
      .filter(production -> production.getRightSide().get().size() == 1 && production.getRightSide().get().get(0).equals(first))
      .collect(Collectors.toList());

  }
}
