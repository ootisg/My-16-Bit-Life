package actions;

import gui.Textbox;
import main.GameCode;

public class MakeText {

	Textbox box;
	public MakeText () {
		
	}
	public boolean makeText(String text) {
		if (box == null) {
			box = new Textbox (text);
			box.changeUnpause();
			box.declare(100,300);
		}
		if (box.isDone) {
			box.forget();
			box = null;
			return true;
		} else {
			return false;
		}
	}
}
