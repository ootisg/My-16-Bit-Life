package theHeist;

import gui.ListTbox;
import gui.Textbox;
import main.GameObject;

public class CheckableObject extends GameObject{
	ListTbox t;
	public CheckableObject () {
		
	}
	
	public void onCheck() {
		
	}
	/** 
	 * writes text to the screen using a textbox
	 * @param text the message to write
	 */
	public void writeText (String text) {
		Textbox t = new Textbox (text);
		t.chagePause();
		t.declare(50, 200);
	}
	/**
	 * writes options to the screen runs the runOption1 runOption2 runOption3 or runOption 4 method if any of those options are selected
	 * @param options the options the player has to chose from
	 */
	public void makeOptions (String [] options) {
		t = new ListTbox ( 50, 400, options);
	}
	@Override 
	//overriding this in child classes could cause problems for options
	public void frameEvent () {
		if (t != null) {
			switch (t.getSelected()) {
			case 0:
				this.runOption1();
				t.close();
				t = null;
				break;
			case 1:
				this.runOption2();
				t.close();
				t = null;
				break;
			case 2:
				this.runOption3();
				t.close();
				t = null;
				break;
			case 3:
				this.runOption4();
				t.close();
				t = null;
				break;
			case 4:
				this.runOption5();
				t.close();
				t = null;
				break;
			}
		}
	}
	public void runOption1() {
		
	}
	public void runOption2() {
		
	}
	public void runOption3() {
		
	}
	public void runOption4() {
		
	}
	public void runOption5() {
		
	}
}
