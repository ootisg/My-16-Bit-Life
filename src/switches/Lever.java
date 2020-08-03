package switches;

import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Lever extends Switch {
	private static final Sprite SWITCH_SPRITE = new Sprite ("resources/sprites/config/switch.txt");
	public Lever () {
		this.setSprite(SWITCH_SPRITE);
		this.adjustHitboxBorders();
		this.setHitboxAttributes(0, 0, 32, 15);
	}
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (Jeffrey.getActiveJeffrey().isColliding(this) && keyPressed(10)) {
			if (this.isActivated()) {
				this.getAnimationHandler().setAnimationFrame(0);
				this.onFlip();
			} else {
				this.getAnimationHandler().setAnimationFrame(1);
				this.onFlip();	
			}
		}
	}
}
