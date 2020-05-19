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
public class NTSymbol extends Symbol{
    public NTSymbol(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "{" + this.symbol + "}";
	}

}
