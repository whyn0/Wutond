/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.events;

import javax.swing.JTextArea;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;
import proj.map.progettoMap1920.adventure.type.Room;

/**
 *
 * @author whyno
 */
public interface EventInterface {
    public void check(ParserOutput p,Room currentRoom, boolean move,JTextArea out);
    public void execute();
}
