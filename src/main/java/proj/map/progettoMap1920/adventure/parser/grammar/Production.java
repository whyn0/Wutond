/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser.grammar;

import java.io.Serializable;

/**
 *
 * @author whyno
 */
public class Production implements Serializable {
  private final ProductionSide leftSide;
  private final ProductionSide rightSide;

  public Production(ProductionSide leftSide, ProductionSide rightSide) {
    this.leftSide = leftSide;
    this.rightSide = rightSide;
  }

  public ProductionSide getLeftSide() {
    return leftSide;
  }

  public ProductionSide getRightSide() {
    return rightSide;
  }

}
