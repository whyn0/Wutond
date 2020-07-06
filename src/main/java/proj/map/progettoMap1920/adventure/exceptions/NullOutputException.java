/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.exceptions;

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
        msg = "La persona che cerchi non è qui!" + '\n' + "================================================" + '\n';
        break;
      case PICK_UP:
        msg = "L'oggetto che cerchi non è qui!" + '\n' + "================================================" + '\n';
        break;
      case OPEN:
        msg = "Ciò che vuoi aprire non è qui!" + '\n' + "================================================" + '\n';
        break;
      case USE:
        msg = "Non puoi usare questo oggetto|" + '\n' + "================================================" + '\n';
        break;
      case LOOK_AT:
        msg = "Provi a guardare l'oggetto ma non è qui presente" + '\n' + "================================================" + '\n';
        break;
      case WALK_TO:
        msg = "Non ho capito dove andare!" + '\n' + "================================================" + '\n';
        break;
      case KILL:
        msg = "Non ho capito chi dovrei uccidere!" + '\n' + "================================================" + '\n';
        break;
    }
    return msg;
  }

  public String getMsg() {
    return msg;
  }

}
