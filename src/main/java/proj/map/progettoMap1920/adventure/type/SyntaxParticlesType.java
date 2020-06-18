/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;


/**
 *
 * @author whyno
 */
public enum SyntaxParticlesType {
   ALL("all"),
   EXCEPT("except");
   
   private String name;
   
   private SyntaxParticlesType(String name) {
     this.name = name;
   }
   @Override
   public String toString() {
     return name;
   }
}
/*
public enum Types{
    VERB,
    ARTICLE,
    NAME,
    PREPOSITION,
    CONJUNCTION,
    ALL,
    EXCEPT
}
prendi tutto 
prendi tutto eccetto la birra,
prendi il coltello e ucciditi
parla a mario 
parla con mario
s -> verbo particella nome
usa la bottiglia con la 
verbo particella direzione

VERB ARTICOLO OBJECT CONGIUNZIONE VERBO
S -> [VERBO][A_OBJECT][CONJUNCTION][S]
*/