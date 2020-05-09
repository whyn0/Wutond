/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.Set;

/**
 *
 * @author whyno
 */
public class AdvObject {
	
	private final int id;
	
	private String name;
	
	private String description;
    
    private Set<String> alias;
    
    private boolean openable = false;

    private boolean pickupable = true;

    private boolean pushable = false;
    
    private boolean throwable = true; 
    
    private boolean open = false;
    
	private boolean push = false;
    
    private boolean can_heal = false;
    
    private boolean can_dmg= false;
    
    private boolean can_kill= false;

    /*
     * 
     * COSTRUTTORI
     * 
     */
        
	public AdvObject(int id, String name, String description, Set<String> alias, boolean openable, boolean pickupable,
			boolean pushable, boolean throwable, boolean open, boolean push, boolean can_heal, boolean can_dmg,
			boolean can_kill) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.alias = alias;
		this.openable = openable;
		this.pickupable = pickupable;
		this.pushable = pushable;
		this.throwable = throwable;
		this.open = open;
		this.push = push;
		this.can_heal = can_heal;
		this.can_dmg = can_dmg;
		this.can_kill = can_kill;
	}

	public AdvObject(int id) {
		super();
		this.id = id;
	}


	/*
	 * 
	 *GETTERS 
	 * 
	 */
    

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<String> getAlias() {
		return alias;
	}

	public boolean isOpenable() {
		return openable;
	}

	public boolean isPickupable() {
		return pickupable;
	}

	public boolean isPushable() {
		return pushable;
	}

	public boolean isThrowable() {
		return throwable;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isPush() {
		return push;
	}

	public boolean isCan_heal() {
		return can_heal;
	}

	public boolean isCan_dmg() {
		return can_dmg;
	}

	public boolean isCan_kill() {
		return can_kill;
	}
/*
 * 
 * SETTERS
 * 
 */
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAlias(Set<String> alias) {
		this.alias = alias;
	}

	public void setOpenable(boolean openable) {
		this.openable = openable;
	}

	public void setPickupable(boolean pickupable) {
		this.pickupable = pickupable;
	}

	public void setPushable(boolean pushable) {
		this.pushable = pushable;
	}

	public void setThrowable(boolean throwable) {
		this.throwable = throwable;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void setPush(boolean push) {
		this.push = push;
	}

	public void setCan_heal(boolean can_heal) {
		this.can_heal = can_heal;
	}

	public void setCan_dmg(boolean can_dmg) {
		this.can_dmg = can_dmg;
	}

	public void setCan_kill(boolean can_kill) {
		this.can_kill = can_kill;
	}
	
	
}
