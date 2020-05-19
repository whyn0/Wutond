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
    private final Alphabet start;
    private final List<Production> productions;
    private final List<Alphabet> nonTerminals;
    private final List<Alphabet> terminals;
    
    public CFGrammar(Alphabet start,List<Production> productions,List<Alphabet> nonTerminals,List<Alphabet> terminals){
        this.start = start;
        this.productions = productions;
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        
    }

    public Alphabet getStart() {
        return start;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<Alphabet> getNonTerminals() {
        return nonTerminals;
    }

    public List<Alphabet> getTerminals() {
        return terminals;
    }
    
}
