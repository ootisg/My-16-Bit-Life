package triggers;

import java.util.ArrayList;
import gameObjects.PairingObject;
import main.GameObject;
import main.ObjectHandler;

public class ForgetTrigger extends RessesiveTrigger {
	ArrayList <GameObject> objectsToDelete = new ArrayList <GameObject>();
	int timer;
	 public ForgetTrigger() {
		 this.setHitboxAttributes(0, 0, 16, 16);
		 timer = 0;
	}
	@Override
	public void frameEvent () {
		if (timer == 0) {
			if (this.isColliding("PairingObject")) { 
				PairingObject working = (PairingObject) this.getCollisionInfo().getCollidingObjects().get(0);
				objectsToDelete = working.getPairedPairedObjects();
			}
		timer = timer + 1;
	}
}
	public void triggerEvent () {
		for (int i = 0; i != objectsToDelete.size(); i++) {
			objectsToDelete.get(i).forget();
		}
		this.eventFinished = true;
	}
}
