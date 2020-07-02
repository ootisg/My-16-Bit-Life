package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import switches.Activateable;

public class DashPad extends GameObject implements Activateable{
	boolean direction = false;
	boolean inzialized = false;
	boolean activated = true;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public DashPad () {
		this.setHitboxAttributes(0, 0, 48, 8);
		this.adjustHitboxBorders();
		this.setSprite(new Sprite ("resources/sprites/config/dash_pad.txt"));
		this.getAnimationHandler().setFrameTime(100);
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			if (this.getVariantAttribute("Direction") != null) {
				if (this.getVariantAttribute("Direction").equals("Right")) {
					direction = true;
				} else {
					direction = false;
				}
			}
			if (this.getVariantAttribute("active") != null) {
				if (!(this.getVariantAttribute("active").equals("nv") || this.getVariantAttribute("active").equals("false"))) {
					activated = true;
				} else {
					activated = false;
					this.getAnimationHandler().setFrameTime(0);
				}
			}
			inzialized = true;
		}
		if (direction) {
			this.getAnimationHandler().setFlipHorizontal(true);
		} 

		if (j.isColliding(this) && activated) {
			if (direction) {
				this.getAnimationHandler().setFlipHorizontal(true);
				j.vx = -15.999999;
			} else {
				j.vx = 15.99999;
			}
		}
	}
	@Override
	public void activate() {
		activated = true;
		this.getAnimationHandler().setFrameTime(100);
	}
	@Override
	public void deactivate() {
		activated = false;
		this.getAnimationHandler().setFrameTime(0);
	}
	@Override
	public boolean isActivated() {
		return activated;
	}
	@Override
	public void pair() {
	
	}

}
