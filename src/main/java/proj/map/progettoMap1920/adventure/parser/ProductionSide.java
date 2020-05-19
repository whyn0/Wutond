/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author whyno
 */
public class ProductionSide {
    private final List<Alphabet> list = new ArrayList<>();
  
    public ProductionSide(Alphabet... symbol){
        this.list.addAll(Arrays.asList(symbol));
    }
    public List<Alphabet> get(){
        return new ArrayList<>(this.list);
    }
}
