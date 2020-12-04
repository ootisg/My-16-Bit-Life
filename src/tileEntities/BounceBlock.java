package tileEntities;

import main.GameObject;
import map.TileEntitiy;
import players.Jeffrey;
import switches.Activateable;

public class BounceBlock extends TileEntitiy implements Activateable {
	static boolean activated = true;
	@Override 
	public void onCollision(GameObject o) {
		if (activated) {
			if (o.getClass().getSimpleName().equals("Jeffrey")) {
				if (Jeffrey.getFallDistance(Jeffrey.getActiveJeffrey().getFallIncrementation()) > 112 ) {
					Jeffrey.getActiveJeffrey().setVy(Jeffrey.downToUpSpeed(Jeffrey.getActiveJeffrey().getFallIncrementation()));
					Jeffrey.getActiveJeffrey().getFallIncrementation().clear();
					Jeffrey.getActiveJeffrey().forceSpacebar();
				} else {
					if (Jeffrey.getActiveJeffrey().getVy() > 0) {
						Jeffrey.getActiveJeffrey().setVy(-14);
						Jeffrey.getActiveJeffrey().getFallIncrementation().clear();
						Jeffrey.getActiveJeffrey().forceSpacebar();
					}
				}
			}
		} 
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (activated) {
			if (!o.getClass().getSimpleName().equals("Jeffrey")) {
				return true;
			} else {
				if (Jeffrey.getActiveJeffrey().isFallingDurringCollision()) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			return true;
		}
	}
	@Override
	public void activate() {
		activated = true;
	}

	@Override
	public void deactivate() {
		activated = false;
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

	@Override
	public void pair() {
	}

}
