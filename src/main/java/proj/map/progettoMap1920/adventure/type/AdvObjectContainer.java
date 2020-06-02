/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class AdvObjectContainer extends AdvObject implements Serializable {

    final private boolean openable = true;
    private List<AdvObject> list;
    private Lock lock;

    /*
 * 
 * COSTRUTTORI
 * 
     */

    public AdvObjectContainer(List<AdvObject> list, Lock lock, int id, String name, String description, Set<String> alias, boolean pickable) {
        super(id, name, description, alias, pickable);
        this.list = list;
        this.lock = lock;
    }

    /*
 * 
 * GETTERS
 * 
     */

    public boolean isOpenable() {
        return openable;
    }

    public List<AdvObject> getList() {
        return list;
    }

    public Lock getLock() {
        return lock;
    }

    

    /*
     *
     * SETTERS
     *
     */
    public void setLock(Lock lock) {
        this.lock = lock;
    }
    public void setList(List<AdvObject> list) {
        this.list = list;
    }
    /*
     *
     * METHOD
     *
     */
    public void add(AdvObject o) {
        list.add(o);
    }

    public void remove(AdvObject o) {
        list.remove(o);
    }

}
