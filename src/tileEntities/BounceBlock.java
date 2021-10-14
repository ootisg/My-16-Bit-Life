package tileEntities;

import main.GameObject;
import map.TileEntitiy;
import players.Player;
import switches.Activateable;

public class BounceBlock extends TileEntitiy implements Activateable {
	static boolean activated = true;
	@Override 
	public void onCollision(GameObject o) {
		if (activated) {
			if (o.getClass().getSimpleName().equals("Player")) {
				if (Player.getFallDistance(Player.getActivePlayer().getFallIncrementation()) > 112 ) {
					Player.getActivePlayer().setVy(Player.downToUpSpeed(Player.getActivePlayer().getFallIncrementation()));
					Player.getActivePlayer().getFallIncrementation().clear();
					Player.getActivePlayer().forceSpacebar();
				} else {
					if (Player.getActivePlayer().getVy() > 0) {
						Player.getActivePlayer().setVy(-14);
						Player.getActivePlayer().getFallIncrementation().clear();
						Player.getActivePlayer().forceSpacebar();
					}
				}
			}
		} 
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (activated) {
			if (!o.getClass().getSimpleName().equals("Player")) {
				return true;
			} else {
				if (Player.getActivePlayer().isFallingDurringCollision()) {
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
