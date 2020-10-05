package triggers;

import java.util.ArrayList;

import gameObjects.PairingObject;
import main.GameObject;
import switches.Activateable;

public class DeactivateTrigger extends Trigger {
	ArrayList <GameObject> objectsToActivate = new ArrayList <GameObject> ();
	boolean inzialized = false;
	public DeactivateTrigger () {
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
				System.out.println("your deactivate Trigger is misconfigured it has no pairing object");
			}
		}
	}
	@Override
	public void triggerEvent () {
		for (int i = 0; i < objectsToActivate.size(); i++) {
			GameObject working = objectsToActivate.get(i);
			try {
				Activateable working2 = (Activateable) working;
				working2.deactivate();
			} catch (ClassCastException e) {
				
			}
		}
		this.eventFinished = true;
	}
}
