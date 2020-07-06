package proj.map.progettoMap1920.adventure.exceptions;

public class EOGameException extends Exception {
  int code;

  public EOGameException(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
