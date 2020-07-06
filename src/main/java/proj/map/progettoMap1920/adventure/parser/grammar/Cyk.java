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
      System.out.print(pn.getValue().getLeftSide().get().get(0) + "->" + pn.getValue().getRightSide().get().get(0));
      if (pn.getValue().getRightSide().get().size() > 1) {
        System.out.println(pn.getValue().getRightSide().get().get(1));
      }
      if (pn.getValue().getRightSide().get().size() == 1 && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {
        reType = true;
      } else if (pn.getValue().getRightSide().get().size() > 1 && pn.getValue().getLeftSide().get().get(0).equals(this.grammar.getStart())) {
        reType = true;
      }
    }
    return reType;
    /*
     * int stringSize = string.size();
     * int nonTerminals_size = this.grammar.getNonTerminals().size();
     * boolean isGenFromStart = false;
     * Alphabet[] nonTerminal_array = new Alphabet[nonTerminals_size];
     * int counter = 0;
     * List<Alphabet> nonTerminal_list = this.grammar.getNonTerminals();
     * for(Alphabet a : this.grammar.getNonTerminals()){
     * nonTerminal_array[counter] = a;
     * counter++;
     * }
     * Boolean[][][] possibilities;
     * possibilities = new Boolean[stringSize+1][stringSize+1][nonTerminals_size+1];
     * //inizializzazione a false dell'arraytridimensionale
     * for(int i = 0;i<=nonTerminals_size;i++){
     * for(int j=0;j<=stringSize;j++){
     * for(int k=0;k<=stringSize;k++){
     * possibilities[k][j][i] = false;
     * }
     * }
     * }
     * 
     * ArrayList<Production> g_prods = (ArrayList<Production>) this.grammar.getProductions();
     * List<Production> unary_prod = new ArrayList<>();
     * List<Production> dup_prod = new ArrayList<>();
     * //divido le produzioni della grammatica in unitarie e duplici
     * g_prods.forEach((prod) -> {
     * if(prod.getRightSide().get().size() > 1){
     * dup_prod.add(prod);
     * }
     * else{
     * unary_prod.add(prod);
     * }
     * });
     * 
     * 
     * for(int s=0;s<stringSize;s++){
     * for(int r=0;r<unary_prod.size();r++){
     * System.out.println("unary prod at 0 index: "+unary_prod.get(r).getRightSide().get().get(0));
     * System.out.println("String at index: " + string.get(s));
     * if(unary_prod.get(r).getRightSide().get().get(0).equals(string.get(s))){
     * for(int t=0;t<nonTerminal_list.size();t++){
     * if(unary_prod.get(r).getRightSide().get().get(0).equals(nonTerminal_list.get(t)))
     * System.out.println("Equals!!!!!!!!!!!!!!!!!!!!!");
     * System.out.println("Value of s = " + s + '\n' + "Value of r = " + r);
     * possibilities[1][s][t] = true;
     * if(unary_prod.get(r).getLeftSide().get().get(0).equals(Alphabet.START)){
     * isGenFromStart = true;
     * }
     * break;
     * }
     * 
     * }
     * }
     * }
     * for(int l=2;l<=stringSize;l++){
     * for(int s=1;s<=stringSize-l+1;s++){
     * for(int p=1;p<=l-1;p++){
     * for(Production prods : dup_prod){
     * Index id = new Index();
     * //counter = 0;
     * /* for(Alphabet a1 : this.grammar.getNonTerminals()){
     * //System.out.println("Examining: " + a1);
     * if(prods.getLeftSide().get().get(0).equals(a1) ){
     * id.a = counter;
     * System.out.println("id.a = " + id.a);
     * }else if(prods.getRightSide().get().get(0).equals(a1)){
     * id.b = counter;
     * System.out.println("id.b = " + id.b);
     * }else if(prods.getRightSide().get().get(1).equals(a1)){
     * id.c = counter;
     * System.out.println("id.c = " + id.c);
     * }
     * counter++;
     * if(id.a != 0 && id.b != 0 && id.c != 0){
     * break;
     * }
     * }
     */
    /*
     * System.out.println("Evaluating: " + prods.getLeftSide().get().get(0) + "->" + prods.getRightSide().get().get(0) +
     * prods.getRightSide().get().get(1));
     * id.a = findIndex(prods.getLeftSide().get().get(0));
     * id.b = findIndex(prods.getRightSide().get().get(0));
     * id.c = findIndex(prods.getRightSide().get().get(1));
     * if(possibilities[p][s][id.b] && possibilities[l-p][s+p][id.c]){
     * possibilities[l][s][id.a] = true;
     * }
     * }
     * }
     * }
     * }
     * if(string.size() == 1){
     * if(isGenFromStart && possibilities[stringSize][0][0]){
     * return true;
     * }
     * else{
     * return false;
     * }
     * }
     * return possibilities[stringSize][0][0];
     */
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
