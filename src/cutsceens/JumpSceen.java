package cutsceens;

import gui.Textbox;
import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;

public class JumpSceen {
	static Textbox nathanBox;
	static Textbox jeffreyBox;
	static Textbox nathanBox2;
	static Textbox jeffreyBox2;
	static boolean firstBox = true;
	static boolean secondBox = false;
	static boolean lameJump = false;
	static int timer = 0;
	static Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	static boolean thirdBox = false;
	static boolean lastBox = false;
	static boolean intizalStuff = true;
	public static void play() {
		if (intizalStuff) {
			intizalStuff = false;
			jeffreyBox = new Textbox ("DANG IT HOW AM I GONNA GET UP THERE");
			nathanBox = new Textbox ("JUST JUMP BY PRESSING THE SPACEBAR");
			nathanBox2 = new Textbox ("OH WAIT CRAP I FORGOT TO TURN THE VIDEO GAME PHISICS                                                          OK TRY NOW");
			jeffreyBox2 = new Textbox ("COOL LETS TRY");
			jeffreyBox.remember(true);
			nathanBox.remember(true);
			nathanBox2.remember(true);
			CutsceenHandler.playing = true;
			jeffreyBox2.remember(true);
		}
		if (lameJump) {
			if (timer <20) {
			}
			timer = timer + 1;
			if (timer >20 && timer <=40) {
				j.setY(j.getY() -1 );
			}
			if (timer > 40) {
				j.setY(j.getY() +1 );
			}
			if (timer == 61) {
				lameJump = false;
				lastBox = true;
				timer = 0;
			}
		}
			if (jeffreyBox.isDone) {
				jeffreyBox.forget();
				jeffreyBox.isDone = false;
				secondBox = true;
			}
			if (nathanBox.isDone) {
				nathanBox.forget();
				nathanBox.isDone = false;
				thirdBox = true;
			}
			if (jeffreyBox2.isDone) {
				jeffreyBox2.forget();
				jeffreyBox2.isDone = false;
				lameJump = true;
			}
			if (nathanBox2.isDone) {
				nathanBox2.forget();
				nathanBox2.isDone = false;
				CutsceenHandler.playing = false;
			}
			
		if (firstBox) {
			jeffreyBox.declare(0, 0);
			firstBox = false;
		}
		if (secondBox) {
			nathanBox.declare(0, 0);
			secondBox = false;
		}
		if (thirdBox) {
			jeffreyBox2.declare(0, 0);
			thirdBox = false;
		}
		if (lastBox) {
			nathanBox2.declare(0, 0);
			lastBox = false;
		}
	}
	
	
}
