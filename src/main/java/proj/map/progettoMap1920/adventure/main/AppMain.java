/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import proj.map.progettoMap1920.adventure.fileInitializer.FileInit;
import proj.map.progettoMap1920.adventure.parser.Alphabet;
import proj.map.progettoMap1920.adventure.parser.CFGrammar;
import proj.map.progettoMap1920.adventure.parser.Cky;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.parser.Production;
import proj.map.progettoMap1920.adventure.parser.ProductionSide;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.AdvObjectContainer;
import proj.map.progettoMap1920.adventure.type.Lock;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Door;
import proj.map.progettoMap1920.adventure.type.Article;
import proj.map.progettoMap1920.adventure.type.ArticleType;
import proj.map.progettoMap1920.adventure.type.Command;
import proj.map.progettoMap1920.adventure.type.CommandType;
import proj.map.progettoMap1920.adventure.type.Dialog;
import proj.map.progettoMap1920.adventure.type.DialogBox;
import proj.map.progettoMap1920.adventure.type.Preposition;
import proj.map.progettoMap1920.adventure.type.PrepositionType;
import proj.map.progettoMap1920.adventure.type.Room;

/**
 *
 * @author whyno
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*produzioni
        
        
        */
        Production s1 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.DIRECTION));
        Production s2 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.VERB));
        Production s3 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.A,Alphabet.D));
        Production s4 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.B,Alphabet.E));
        Production s5 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.F,Alphabet.G));
        Production s6 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.N,Alphabet.L));
        Production s7 = new Production(new ProductionSide(Alphabet.START),new ProductionSide(Alphabet.B,Alphabet.Q));
        Production s8 = new Production(new ProductionSide(Alphabet.A),new ProductionSide(Alphabet.B,Alphabet.C));
        Production s9 = new Production(new ProductionSide(Alphabet.B),new ProductionSide(Alphabet.VERB));
        Production s10 = new Production(new ProductionSide(Alphabet.C),new ProductionSide(Alphabet.PREPOSITION));
        Production s11= new Production(new ProductionSide(Alphabet.D),new ProductionSide(Alphabet.DIRECTION));
        Production s12 = new Production(new ProductionSide(Alphabet.D),new ProductionSide(Alphabet.NPC));
        Production s13 = new Production(new ProductionSide(Alphabet.E),new ProductionSide(Alphabet.OBJECT));
        Production s14 = new Production(new ProductionSide(Alphabet.F),new ProductionSide(Alphabet.B,Alphabet.E));
        Production s15 = new Production(new ProductionSide(Alphabet.F),new ProductionSide(Alphabet.B,Alphabet.Q));
        Production s16 = new Production(new ProductionSide(Alphabet.Q),new ProductionSide(Alphabet.NPC));
        Production s17 = new Production(new ProductionSide(Alphabet.G),new ProductionSide(Alphabet.C,Alphabet.E));
        Production s18 = new Production(new ProductionSide(Alphabet.G),new ProductionSide(Alphabet.C,Alphabet.Q));
        Production s19 = new Production(new ProductionSide(Alphabet.H),new ProductionSide(Alphabet.I,Alphabet.M));
        Production s20 = new Production(new ProductionSide(Alphabet.I),new ProductionSide(Alphabet.ALL));
        Production s21 = new Production(new ProductionSide(Alphabet.M),new ProductionSide(Alphabet.EXCEPT));
        Production s22 = new Production(new ProductionSide(Alphabet.N),new ProductionSide(Alphabet.B,Alphabet.H));
        Production s23 = new Production(new ProductionSide(Alphabet.N),new ProductionSide(Alphabet.B,Alphabet.I));
        Production s24 = new Production(new ProductionSide(Alphabet.L),new ProductionSide(Alphabet.OBJECT));
        Production s25 = new Production(new ProductionSide(Alphabet.L),new ProductionSide(Alphabet.E,Alphabet.L));
        List<Production> prod_list = new ArrayList<>();
        prod_list.add(s1);
        prod_list.add(s2);
        prod_list.add(s3);
        prod_list.add(s4);
        prod_list.add(s5);
        prod_list.add(s6);
        prod_list.add(s7);
        prod_list.add(s8);
        prod_list.add(s9);
        prod_list.add(s10);
        prod_list.add(s11);
        prod_list.add(s12);
        prod_list.add(s13);
        prod_list.add(s14);
        prod_list.add(s15);
        prod_list.add(s16);
        prod_list.add(s17);
        prod_list.add(s18);
        prod_list.add(s19);
        prod_list.add(s20);
        prod_list.add(s21);
        prod_list.add(s22);
        prod_list.add(s23);
        prod_list.add(s24);
        prod_list.add(s25);
        List<Alphabet> nonTerminals = new ArrayList<>();
        nonTerminals.add(Alphabet.START);
        nonTerminals.add(Alphabet.A);
        nonTerminals.add(Alphabet.B);
        nonTerminals.add(Alphabet.C);
        nonTerminals.add(Alphabet.D);
        nonTerminals.add(Alphabet.E);
        nonTerminals.add(Alphabet.F);
        nonTerminals.add(Alphabet.G);
        nonTerminals.add(Alphabet.H);
        nonTerminals.add(Alphabet.I);
        nonTerminals.add(Alphabet.L);
        nonTerminals.add(Alphabet.M);
        nonTerminals.add(Alphabet.N);
        nonTerminals.add(Alphabet.O);
        nonTerminals.add(Alphabet.P);
        nonTerminals.add(Alphabet.Q);        
        List<Alphabet> terminals = new ArrayList<>();
        terminals.add(Alphabet.ALL);
        terminals.add(Alphabet.ARTICLE);
        terminals.add(Alphabet.EXCEPT);
        terminals.add(Alphabet.DIRECTION);
        terminals.add(Alphabet.NPC);
        terminals.add(Alphabet.PREPOSITION);
        terminals.add(Alphabet.OBJECT);
        terminals.add(Alphabet.VERB);
        CFGrammar grammar = new CFGrammar(Alphabet.START,prod_list,nonTerminals,terminals);
        
        /*
        
        
        
        */
        Set<String> the_alias = new HashSet<>();
        String[] list_alias = {"lo","la"};
        the_alias.addAll(Arrays.asList(list_alias));
        List<Command> command_list = new ArrayList<>();
        List<Article> article_list = new ArrayList<>();
        List<Preposition> prep_list = new ArrayList<>();
        List<AdvObject> inventory_list = new ArrayList<>();
        Command talk_to = new Command(CommandType.TALK_TO,"parla",null);
        Article the = new Article(ArticleType.THE,"il",the_alias);
        Preposition to = new Preposition(PrepositionType.TO,"con",null);
        AdvObject sasso = new AdvObject(1,"sasso","un sasso di merda","mario giordano",null,true);
        command_list.add(talk_to);
        article_list.add(the);
        prep_list.add(to);
        inventory_list.add(sasso);
        Parser parser = new ItParser();
        List<Alphabet> po = new ArrayList<>();
        po = parser.parse("parla",inventory_list,null,null,null,command_list,article_list,prep_list);
        Cky c = new Cky(grammar);
        Alphabet[] a_t = {Alphabet.VERB,Alphabet.ALL,Alphabet.EXCEPT,Alphabet.OBJECT};
        List<Alphabet> temp_test = new ArrayList<>();
        temp_test.add(Alphabet.VERB);
        temp_test.add(Alphabet.ALL);
        temp_test.add(Alphabet.EXCEPT);
        temp_test.add(Alphabet.OBJECT);
        temp_test.add(Alphabet.OBJECT);
     //   temp_test.add(Alphabet.OBJECT);
     //   temp_test.add(Alphabet.OBJECT);
        System.out.print(c.parse(temp_test));
        
        FileInit fi = new FileInit();
        try {
          fi.objReader("res/file_txt/ADV_OBJ.txt");
          fi.lockReader("res/file_txt/LOCK.txt");
          fi.contReader("res/file_txt/ADV_CONT.txt");
          fi.doorReader("res/file_txt/DOOR.txt");
          fi.dialogReader("res/file_txt/DIALOG.txt");
          fi.npcReader("res/file_txt/NPC.txt");
          fi.roomReader("res/file_txt/ROOM.txt");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      
        DialogBox d= new DialogBox(  fi.npcList.getList().get(2).getDialog());
        d.main(null);
        /*/stampa oggetti
        for(AdvObject i : fi.objectList) {
          System.out.println(i.getId() + '\n' + i.getName() + '\n' + i.getDescription() + '\n' + i.getLook());
        }
        for(Lock i : fi.lockList) {
          System.out.println(i.getId() );
        }
        for(AdvObjectContainer i : fi.containerList) {
          System.out.println(i.getId() + '\n' + i.getName() + '\n' + i.getDescription() + '\n' + i.getLook());
          if(i.getList().size() > 0) {
            for(AdvObject j : i.getList()) {
              System.out.println(j.getId() + '\n' + j.getName() + '\n' + j.getDescription() + '\n' + j.getLook());
            }
          }
        }
        for(Door i : fi.doorList) {
          System.out.println(i.getId() + '\n' + i.getName() + '\n' + i.getDescription() + '\n' + i.getLook());
          if(i.getLock() != null) {
            
            System.out.println(i.getLock().getId() +  + '\n' + i.getLock().getKey().getId() + '\n' + i.getLock().getKey().getName());
          }
        }
        for(Dialog i : fi.dialogList) {
          System.out.println(i.getId() + '\n' + i.getText() + '\n' + i.getOpt_1() + '\n' + i.getOpt_2() + '\n' + i.getOpt_3() + i.getNext_1() + i.getNext_2() + i.getNext_3());
          
        }
        for(Npc i : fi.npcList) {
          System.out.println(i.getId() + '\n' + i.getName() + '\n' + i.getDescription() + '\n' + i.getLook());
          if(i.getNpc_inventory().size() > 0) {
            System.out.print("Inventario : " );
            for(AdvObject j : i.getNpc_inventory()) {
              System.out.println(j.getId() + '\n' + j.getName() + '\n' + j.getDescription() + '\n' + j.getLook() );
            }
            
          }
          if(i.getDialog() != null) {
            System.out.println(i.getDialog().getId() + '\n' + i.getDialog().getText() + '\n' + i.getDialog().getOpt_1() + '\n' + i.getDialog().getOpt_2() + '\n' + i.getDialog().getOpt_3() + i.getDialog().getNext_1() + i.getDialog().getNext_2() + i.getDialog().getNext_3());
          }
        }
        for(Room i : fi.roomList) {
          System.out.println(i.getId() + '\n' + i.getName() + '\n' + i.getDescription() + '\n' + i.getLook());
          if(i.getObjects_list().size() > 0) {
            for(AdvObject j : i.getObjects_list()) {
              System.out.println(j.getId() + '\n' + j.getName() + '\n' + j.getDescription() + '\n' + j.getLook());
            }
          }
          if(i.getNpc_list().size() > 0) {
            for(Npc j : i.getNpc_list()) {
              System.out.println(j.getId() + '\n' + j.getName() + '\n' + j.getDescription() + '\n' +
                j.getLook() + '\n' );
            }
          }
          System.out.println(i.getNorth().getId() + '\n' + i.getSouth().getId() + '\n' + i.getEast().getId() + '\n'  + i.getWest().getId());
        }*/
    }
    
}
