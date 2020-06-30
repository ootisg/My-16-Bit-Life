package gameObjects;

import java.util.Iterator;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import switches.Activateable;

public class CameraObject extends GameObject implements Activateable {
	boolean inControl = false;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public CameraObject () {
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (inControl) {
			Room.setView((int)this.getX(),(int) this.getY());
		}
	}
	@Override
	public void pausedEvent () {
		this.frameEvent();
	}
	
	public boolean inControl () {
		return inControl;
	}
	public void takeControl() {
		Iterator <GameObject> iter = ObjectHandler.getObjectsByName("CameraObject").iterator();
		while (iter.hasNext()) {
			CameraObject working = (CameraObject) iter.next();
			working.giveUpControl();
		}
		j.setScroll(false);
		inControl = true;
	}
	public void giveUpControl () {
		j.setScroll(true);
		inControl = false;
	}
	@Override
	public void activate() {
		if (!this.inControl) {
			this.takeControl();
		}
	}
	@Override
	public void deactivate() {
		if (this.inControl) {
			this.giveUpControl();
		}
	}
	@Override 
	public boolean isActivated () {
		return this.inControl;
	}
	@Override
	public void pair() {

	}
}
