package actions;

import gui.Textbox;
import main.GameCode;

public class MakeText {

	static Textbox box;
	public static boolean makeText(String text) {
		if (box == null) {
			box = new Textbox (text);
			box.declare(100,300);
		}
		if (box.isDone) {
			return true;
		} else {
			return false;
		}
	}
}
