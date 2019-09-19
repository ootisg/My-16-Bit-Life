package cutsceens;

import gui.Textbox;

public class JumpSceen {
	static Textbox nathanBox;
	static Textbox jeffreyBox;
	static Textbox nathanBox2;
	static Textbox jeffreyBox2;
	static boolean firstBox = true;
	public static void play() {
		try {
			
		} catch (NullPointerException e) {
			
		}
		if (firstBox) {
			jeffreyBox = new Textbox ("Dang it how am I gonna get up there");
		}
		//nathanBox = new Textbox ("Just jump by pressing the spacebar");
		
		//nathanBox2 = new Textbox ("OH wait crap I forgot to turn the video game physics                                                          Ok try now");
		//jeffreyBox2 = new Textbox ("Cool lets try");
	}
	
	
}
