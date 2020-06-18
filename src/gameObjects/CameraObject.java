package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;

public class CameraObject extends GameObject {
	boolean inControl = false;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public CameraObject () {
		
	}
	@Override
	public void frameEvent () {
		if (inControl) {
			Room.setView((int)this.getX(),(int) this.getY());
		}
	}
	public void takeControl() {
		j.setScroll(false);
		inControl = true;
	}
	public void giveUpControl () {
		j.setScroll(true);
		inControl = false;
	}
}
