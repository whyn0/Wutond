/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import proj.map.progettoMap1920.adventure.parser.ItParser;
import proj.map.progettoMap1920.adventure.parser.Parser;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.Article;
import proj.map.progettoMap1920.adventure.type.ArticleType;
import proj.map.progettoMap1920.adventure.type.Command;
import proj.map.progettoMap1920.adventure.type.CommandType;
import proj.map.progettoMap1920.adventure.type.Preposition;
import proj.map.progettoMap1920.adventure.type.PrepositionType;

/**
 *
 * @author whyno
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        AdvObject sasso = new AdvObject(1,"sasso");
        command_list.add(talk_to);
        article_list.add(the);
        prep_list.add(to);
        inventory_list.add(sasso);
        Parser parser = new ItParser();
        ParserOutput po = new ParserOutput();
        po = parser.parse("",inventory_list,null,null,null,command_list,article_list,prep_list);
    }
    
}
