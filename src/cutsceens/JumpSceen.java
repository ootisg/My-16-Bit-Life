package cutsceens;

import gui.Textbox;
import main.GameCode;

public class JumpSceen {
	static Textbox nathanBox;
	static Textbox jeffreyBox;
	static Textbox nathanBox2;
	static Textbox jeffreyBox2;
	static boolean firstBox = true;
	static boolean secondBox = false;
	static boolean lameJump = false;
	static int timer = 0;
	static boolean thirdBox = false;
	static boolean lastBox = false;
	static boolean intizalStuff = true;
	public static void play() {
		if (intizalStuff) {
			intizalStuff = false;
			jeffreyBox = new Textbox ("Dang it how am I gonna get up there");
			nathanBox = new Textbox ("Just jump by pressing the spacebar");
			nathanBox2 = new Textbox ("OH wait crap I forgot to turn the video game physics                                                          Ok try now");
			jeffreyBox2 = new Textbox ("Cool lets try");
			jeffreyBox.remember(true);
			nathanBox.remember(true);
			nathanBox2.remember(true);
			jeffreyBox2.remember(true);
		}
		if (lameJump) {
			if (timer <5) {
			GameCode.testJeffrey.setY(GameCode.testJeffrey.getY() -1 );
			}
			timer = timer + 1;
			if (timer >5) {
				GameCode.testJeffrey.setY(GameCode.testJeffrey.getY() +1 );
			}
			if (timer == 10) {
				lameJump = false;
				thirdBox = true;
				timer = 0;
			}
		}
		try {
			if (jeffreyBox.isDone) {
				jeffreyBox.forget();
				secondBox = true;
			}
		} catch (NullPointerException e) {
			
		}
		try {
			if (nathanBox.isDone) {
				nathanBox.forget();
				lameJump = true;
			}
		} catch (NullPointerException e) {
			
		}
		try {
			if (jeffreyBox2.isDone) {
				jeffreyBox2.forget();
				lastBox = true;
			}
		} catch (NullPointerException e) {
			
		}
		try {
			if (nathanBox2.isDone) {
				nathanBox2.forget();
				Cutsceen.playing = false;
			}
		} catch (NullPointerException e) {
			
		}
		if (firstBox) {
			jeffreyBox.declare(0, 400);
			firstBox = false;
		}
		if (secondBox) {
			nathanBox.declare(0, 400);
			secondBox = false;
		}
		if (thirdBox) {
			jeffreyBox2.declare(0, 400);
			thirdBox = false;
		}
		if (lastBox) {
			nathanBox2.declare(0, 400);
			lastBox = false;
		}
	}
	
	
}
