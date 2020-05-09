/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class AdvObjectContainer extends AdvObject{
	
  private List<AdvObject> list = new ArrayList<>();
  
/*
 * 
 * COSTRUTTORI
 * 
 */
  
	public AdvObjectContainer(int id, String name, String description, Set<String> alias, boolean openable,
			boolean pickupable, boolean pushable, boolean throwable, boolean open, boolean push, boolean can_heal,
			boolean can_dmg, boolean can_kill) {
		super(id, name, description, alias, openable, pickupable, pushable, throwable, open, push, can_heal, can_dmg, can_kill);
		// TODO Auto-generated constructor stub
	}

	public AdvObjectContainer(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
/*
 * 
 * GETTERS
 * 
 */
	
	public List<AdvObject> getList() {
		return list;
	}
	
/*
 * 
 * SETTERS
 * 
 */
	
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
