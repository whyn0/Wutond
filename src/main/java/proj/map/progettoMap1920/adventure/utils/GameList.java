package proj.map.progettoMap1920.adventure.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import proj.map.progettoMap1920.adventure.type.GameElement;
import proj.map.progettoMap1920.adventure.type.GameObject;

public class GameList <T extends GameElement> implements Iterable<T>{
  private List<T> list;
  
  /*
   * costruttori
   */
  public GameList(List<T> list) {
    this.list = list;
  }
  /*
   * Getter
   */

  public List<T> getList() {
    return list;
  }
  /*
   * Setter
   */
  public void setList(List<T> list) {
    this.list = list;
  }
  /*
   * metodi
   */
  //il metodo restituisce l'oggetto richiesto mediante id
  public T getById(int id) {
    T obj = null;
    for(T elem : list) {
      if(((GameElement) elem).getId() == id) {
        obj = elem;
        break;
      }
    }
    return obj;
  }
  public T getByName(String name) {
    T obj = null;
    if(obj instanceof GameObject) {
      for(T elem : list) {
        if(((GameObject) elem).getName().equals(name)) {
          obj = elem;
        }
      }
    }
    return obj;
  }
  public void add(T obj) {
    this.list.add(obj);
  }
  public void addAll(T... objs) {
    this.list.addAll(Arrays.asList(objs));
  }
  public void addAll(List<T> obj_list) {
    for(T t : obj_list) {
      this.list.add(t);
    }
  }
  public void addAll(GameList<T> obj_game_list) {
    addAll(obj_game_list.getList());
  }
  public void remove(T obj) {
    this.list.remove(obj);
  }
  public void clear() {
    this.list.clear();
  }
  public Iterator<T> iterator() {
    return list.iterator();
  }
  public boolean contains(T obj) {
    return list.contains(obj);
  }
}
