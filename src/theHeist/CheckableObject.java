package theHeist;

import gui.Textbox;
import main.GameObject;

public class CheckableObject extends GameObject{
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
}
