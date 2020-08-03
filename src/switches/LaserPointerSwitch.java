package switches;

import java.util.ArrayList;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import weapons.LaserPointer;

public class LaserPointerSwitch extends Switch {
	boolean activated = false;
	ArrayList <LaserPointerSwitch> commerads = new ArrayList <LaserPointerSwitch> ();
	boolean inzialized = false;
	public LaserPointerSwitch () {
		this.setSprite(new Sprite ("resources/sprites/orb.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (!inzialized) {
			ArrayList <GameObject> working = ObjectHandler.getObjectsByName("LaserPointerSwitch");
			for (int i =0; i <working.size();i++) {
				if (working.get(i).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner")) && !working.get(i).equals(this)) {
					commerads.add((LaserPointerSwitch) working.get(i));
				}
			}
			inzialized = true;
		}
		if (!this.isActivated()) {
			if (Jeffrey.getActiveJeffrey().getWeapon().getClass().getSimpleName().equals("LaserPointer")) {
				LaserPointer thePointer = (LaserPointer)Jeffrey.getActiveJeffrey().getWeapon();
				if (thePointer.isCollidingLaser(this)) {
					this.activated = true;
				} else {
					this.activated = false;
				}
			} else {
				this.activated = false;
			}
			boolean allActivated = this.activated;
			for (int i = 0; i < commerads.size(); i++) {
				if (!commerads.get(i).activated) {
					allActivated = false;
				}
			}
			if (allActivated) {
				this.onFlip();
				for (int i =0; i < commerads.size(); i++) {
					commerads.get(i).onFlip();
				}
			}
		}
	}
}
