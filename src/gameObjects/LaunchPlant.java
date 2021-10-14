package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class LaunchPlant extends EnterableObject  {
	boolean wasInside = false;
	public LaunchPlant () {
		this.setSprite(new Sprite ("resources/sprites/config/launchFlower.txt"));
		this.setHitboxAttributes(0,0,16,16);
		this.getAnimationHandler().setFrameTime(0);
		this.enablePixelCollisions();
		this.getAnimationHandler().enableAlternate();
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (this.getAnimationHandler().getFrame() == 0 && this.getAnimationHandler().getFrameTime() > 0) {
			this.getAnimationHandler().setFrameTime(0);
			wasInside = false;
		}
		if (this.inside) {
			if (this.getAnimationHandler().getFrame() == 7) {
				this.exit();
				this.wasInside = true;
				Player.getActivePlayer().setVy(-20);
			}
		}
	}
	@Override
	public void onEntry() {
		if (!wasInside) {
			this.getAnimationHandler().setFrameTime(200);
			this.getAnimationHandler().setAnimationFrame(1);
			super.onEntry();
		}
	}
	
}
