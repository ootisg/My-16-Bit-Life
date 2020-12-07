package triggers;

import java.util.ArrayList;

import gameObjects.PairingObject;
import main.GameObject;
import switches.Activateable;

public class ActivateTrigger extends RessesiveTrigger {
	ArrayList <Object> objectsToActivate = new ArrayList <Object> ();
	boolean inzialized = false;
	public ActivateTrigger () {
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			inzialized = true;
			if (this.isColliding("PairingObject")) {
				PairingObject pairedBoys = (PairingObject)this.getCollisionInfo().getCollidingObjects().get(0);
				objectsToActivate.addAll(pairedBoys.getPairedPairedObjects());
			} else {
				System.out.println("your activate Trigger is misconfigured it has no pairing object");
			}
		}
	}
	@Override
	public void triggerEvent () {
		for (int i = 0; i < objectsToActivate.size(); i++) {
			Object working = objectsToActivate.get(i);
			try {
					Activateable working2 = (Activateable) working;
					working2.activate();
			} catch (ClassCastException e) {
			}
		}
		this.eventFinished = true;
	}
}
