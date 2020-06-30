package switches;

import java.util.ArrayList;

import gameObjects.MapObject;
import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Switch extends MapObject {
	protected boolean isActivated = false;
	
	private boolean inzialized = false;
	
	private boolean solid = false;
	
	ArrayList <GameObject> activateableObjects = new ArrayList <GameObject> ();
	
	public Switch () {
	}

	public boolean isActivated () {
		return isActivated;
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			this.isColliding("PairingObject");
			if (this.getCollisionInfo().collisionOccured()) {
				PairingObject working = (PairingObject) this.getCollisionInfo().getCollidingObjects().get(0);
				activateableObjects = working.getPairedPairedObjects();
			}
			for (int i = 0; i < activateableObjects.size(); i++) {
				if (activateableObjects.get(i) instanceof Activateable) {
					Activateable working = (Activateable) activateableObjects.get(i);
					working.pair();
				} else {
					activateableObjects.remove(i);
					i = i - 1;
				}
			}
			inzialized =true;
		}
		if (solid) {
			super.frameEvent();
		}
	}
	public void onFlip() {
		isActivated = !isActivated;
		for (int i = 0; i < activateableObjects.size(); i++) {
			Activateable working = (Activateable) activateableObjects.get(i);
			if (working.isActivated()) {
				working.deactivate();
			} else {
				working.activate();
			}
		}
	}
	public void makeSolid () {
		solid = true;
	}
}
