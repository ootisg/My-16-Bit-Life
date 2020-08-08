package gameObjects;

import main.GameObject;
import players.Jeffrey;

public class UsablePogo extends GameObject {
	boolean pogoing = false;
	double previosVy = 0;
	public UsablePogo () {
		
	}
	public void frameEvent () {
		if (Jeffrey.getActiveJeffrey().vy != 0 && keyDown ('S')) {
			pogoing = true;
			previosVy = Jeffrey.getActiveJeffrey().vy;
		} else {
			if (keyDown ('S') && pogoing) {
				Jeffrey.getActiveJeffrey().vy = -previosVy - 8;
			} else {
				pogoing = false;
			}
		}
	}
}
