package cutsceens;

public class Cutsceen {
	public static boolean playing  = false;
	public static void play (int cutsceenNumber) {
		//mainloop.pauseEvent equivlant
		switch (cutsceenNumber) {
		case 1: {
			JumpSceen.play();
			playing = true;
			break;
		}
		case 2: {
			break;
		}
		case 3:{
			break;
		}
		}
	}
}
