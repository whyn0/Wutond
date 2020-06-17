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
    private final String start;
    private final List<Production> productions;
    private final List<String> nonTerminals;
    private final List<String> terminals;
    
    public CFGrammar(String start,List<Production> productions,List<String> nonTerminals,List<String> terminals){
        this.start = start;
        this.productions = productions;
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        
    }

    public String getStart() {
        return start;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }
    
}
