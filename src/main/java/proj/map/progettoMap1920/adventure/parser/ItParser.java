/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.parser;

import java.util.ArrayList;
import java.util.List;
import proj.map.progettoMap1920.adventure.type.AdvObject;
import proj.map.progettoMap1920.adventure.type.Article;
import proj.map.progettoMap1920.adventure.type.Command;
import proj.map.progettoMap1920.adventure.type.Npc;
import proj.map.progettoMap1920.adventure.type.Preposition;

/**
 *
 * @author whyno
 */
public class ItParser implements Parser{


    @Override
    public int checkForArticle(String token, List<Article> list) {
        int index = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(token) ){
                index = i;
                break;
            }
        }
        return index;
    }
    //|| list.get(i).getAlias().contains(token)
    @Override
    public int checkForPrep(String token, List<Preposition> list) {
        int index = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(token) ){
                index = i;
                break;
            }
        }
        return index;
    }
    @Override
    public int checkForNpc(String token,List<Npc> list){
        return 1;
    }
/*
    @Override
    public int checkForNpc(String token, List<Npc> list) {
        int index = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(token) || list.get(i).getAlias().contains(token)){
                index = i;
                break;
            }
        }
        return index;
    }
*/
    @Override
    public int checkForCommand(String token, List<Command> list) {
        int index = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(token) ){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int checkForItem(String token, List<AdvObject> list) {
        int index = -1;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(token) ){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public ParserOutput parse(String command, List<AdvObject> inventory, List<AdvObject> room_items, List<AdvObject> cont_items, List<Npc> npc,List<Command> cmd_list,List<Article> articles,List<Preposition> prepositions) {
        List<Terminal> tokenlist_type = new ArrayList<>();
        List<Integer> index_list = new ArrayList<>();
        List<AdvObject> all_items = new ArrayList<>();
        all_items.addAll(inventory);
//        all_items.addAll(room_items);
//        all_items.addAll(cont_items);
        String cmd = command.toLowerCase().trim();
        String[] token_list = cmd.split("\\s+");
        int index;
        if(token_list.length > 0){
            index = checkForCommand(token_list[0],cmd_list);
            tokenlist_type.add(Terminal.VERB);
            if(index > -1){
                index_list.add(index);
                if(token_list.length > 1){
                    for(int i=1;i<token_list.length;i++){
                        
                        if((index = checkForArticle(token_list[i],articles))>=0){
                            index_list.add(index);
                            tokenlist_type.add(Terminal.ARTICLE);
                        }else if((index = checkForPrep(token_list[i],prepositions))>=0){
                            index_list.add(index);
                            tokenlist_type.add(Terminal.PREPOSITION);
                        }else if((index = checkForItem(token_list[i],all_items))>=0){
                            index_list.add(index);
                            tokenlist_type.add(Terminal.OBJECT);
                        }/*else if((index = checkForNpc(token_list[i],npc))>0){
                            index_list.add(index);
                            tokenlist_type.add(Terminal.NPC);
                        }*/
              
                    }
                }
            }
        }
        for(Terminal i : tokenlist_type){
            System.out.print(i);
        }
        return null;
    }
    
}
