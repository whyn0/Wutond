/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

/**
 *
 * @author whyno
 */
public class Dialog {
    
	
	
	private final String id;
    private String text;
    private String opt_1;
    private String opt_2;
    private String opt_3;
    private final String end=">";
    private Dialog next_1;
    private Dialog next_2;
    private Dialog next_3;
    
  /*
   * 
   *   COSTRUTTORI
   *   
   */
    public Dialog(String id, String text) {
		super();
		this.id = id;
		this.text = text;
    }


	public Dialog(String id) {
		super();
		this.id = id;
	}


	
	/*
	 * 
	 * GETTERS
	 * 
	 */
	public String getId() {
		return id;
	}


	public String getText() {
		return text;
	}


	public String getOpt_1() {
		return opt_1;
	}


	public String getOpt_2() {
		return opt_2;
	}


	public String getOpt_3() {
		return opt_3;
	}


	public Dialog getNext_1() {
		return next_1;
	}


	public Dialog getNext_2() {
		return next_2;
	}


	public Dialog getNext_3() {
		return next_3;
	}

/*
 * 
 * SETTERS
 * 
 */
	public void setText(String text) {
		this.text = text;
	}


	public void setOpt_1(String opt_1) {
		this.opt_1 = opt_1;
	}


	public void setOpt_2(String opt_2) {
		this.opt_2 = opt_2;
	}


	public void setOpt_3(String opt_3) {
		this.opt_3 = opt_3;
	}


	public void setNext_1(Dialog next_1) {
		this.next_1 = next_1;
	}


	public void setNext_2(Dialog next_2) {
		this.next_2 = next_2;
	}


	public void setNext_3(Dialog next_3) {
		this.next_3 = next_3;
	}
    
	
}
