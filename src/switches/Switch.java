package switches;

import java.util.ArrayList;

import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;
import mapObjects.MapObject;
import players.Player;
import resources.Sprite;

public class Switch extends MapObject {
	protected boolean isActivated = false;
	
	private boolean inzialized = false;
	
	private boolean solid = false;
	
	ArrayList <Object> activateableObjects = new ArrayList <Object> ();
	ArrayList <Switch> neededSwitches = new ArrayList <Switch> ();
	
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
				for (int i = 0; i < activateableObjects.size(); i++) {
					try {
						Switch currentSwitch = (Switch) activateableObjects.get(i);
						neededSwitches.add(currentSwitch);
						activateableObjects.remove(i);
						i = i -1;
					} catch (ClassCastException e) {
						
					}
				}
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
		System.out.println(activateableObjects);
		isActivated = !isActivated;
		boolean good = true;
		for (int i = 0; i < neededSwitches.size(); i++) {
			Switch curSwitch = neededSwitches.get(i);
			if (!curSwitch.isActivated()) {
				good = false;
				break;
			}
		}
		if (good) {
			for (int i = 0; i < activateableObjects.size(); i++) {
				try {
				Activateable working = (Activateable) activateableObjects.get(i);
				if (working.isActivated()) {
					working.deactivate();
				} else {
					working.activate();
				}
				} catch (ClassCastException e) {
					
				}
			}
		}
	}
	public void makeSolid () {
		solid = true;
	}
}
