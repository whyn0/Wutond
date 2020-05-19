/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

/**
 *
 * @author whyno
 */
public abstract class Symbol {
    protected final String symbol;
    protected Symbol(String name){
        this.symbol = name;
    }
    @Override
    public abstract String toString();
}
