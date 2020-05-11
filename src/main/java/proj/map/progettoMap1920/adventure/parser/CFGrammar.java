/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author whyno
 */
public class CFGrammar {
    private final Terminal start;
    private final List<Production> productions;
    private final List<Terminal> nonTerminals;
    private final List<String> terminals;
    
    protected CFGrammar(Terminal start,List<Production> productions){
        this.start = start;
        this.productions = productions;
        
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
    }
}
