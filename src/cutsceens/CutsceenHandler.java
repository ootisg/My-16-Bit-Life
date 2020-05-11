package cutsceens;

import main.ObjectHandler;

public class CutsceenHandler {
	public static boolean playing  = false;
	public static void playSceen (int cutsceenNumber) {
		ObjectHandler.pause(true);
		//inculde a Cutsceen.playing = true and Cutsceen.playing = false for when your cutseen starts and ends
		switch (cutsceenNumber) {
		case 1: {
			JumpSceen.play();
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
