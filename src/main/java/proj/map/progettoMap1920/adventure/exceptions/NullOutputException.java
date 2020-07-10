/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.exceptions;

import javax.swing.JTextArea;
import proj.map.progettoMap1920.adventure.parser.ParserOutput;

/**
 *
 * @author whyno
 */
public class NullOutputException extends Exception {
  private ParserOutput p;
  private String msg;

  public NullOutputException(ParserOutput p) {
    this.p = p;
    this.msg = choser();
  }

  private String choser() {
    String msg = null;
    switch (p.getCommand().getType()) {
      case TALK_TO:
        msg = "La persona che cerchi non è qui!";
        break;
      case PICK_UP:
        msg = "L'oggetto che cerchi non è qui!" ;
        break;
      case OPEN:
        msg = "Ciò che vuoi aprire non è qui!";
        break;
      case USE:
        msg = "Non puoi usare questo oggetto!" ;
        break;
      case LOOK_AT:
        msg = "Provi a guardare l'oggetto ma non è qui presente" ;
        break;
      case WALK_TO:
        msg = "Non ho capito dove andare!" ;
        break;
      case KILL:
        msg = "Non ho capito chi dovrei uccidere!";
        break;
      case GIVE:
        msg = "Non puoi dare questo oggetto!";
        break;
    }
    return msg;
  }

  public String getMsg() {
    return msg;
  }
  
}
