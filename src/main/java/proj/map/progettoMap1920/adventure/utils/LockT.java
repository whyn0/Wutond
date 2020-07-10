package proj.map.progettoMap1920.adventure.utils;

public class LockT {//classe pubblica che contiene un oggetto statico che funge da lock per la sequenzialità delle operazioni nei blocchi synchronized
  public static final Object lock = new Object();
}
