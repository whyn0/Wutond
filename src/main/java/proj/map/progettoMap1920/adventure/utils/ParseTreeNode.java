/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.utils;

import proj.map.progettoMap1920.adventure.parser.grammar.Production;

/**
 *
 * @author whyno
 */
public class ParseTreeNode extends BinaryTreeNode<Production> {

  public ParseTreeNode(BinaryTreeNode<Production> firstChild, BinaryTreeNode<Production> secondChild, Production value) {
    super(firstChild, secondChild, value);
  }

  @Override
  public ParseTreeNode getFirstChild() {
    return (ParseTreeNode) super.getFirstChild();
  }

  @Override
  public ParseTreeNode getSecondChild() {
    return (ParseTreeNode) super.getSecondChild();
  }
}
