package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class LaunchPlant extends EnterableObject  {
	boolean wasInside = false;
	public LaunchPlant () {
		this.setSprite(new Sprite ("resources/sprites/config/Plant/J_Idle.txt"));
		this.setHitboxAttributes(0,0,16,48);
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (wasInside && !this.isColliding(j)) {
			wasInside = false;
		}
		if (this.inside) {
			if (mouseButtonDown(0) || keyDown (32)) {
				this.exit();
				this.wasInside = true;
				j.vy = -20;
			}
		}
	}
	@Override
	public void onEntry() {
		if (!wasInside) {
			super.onEntry();
			inside = true;
		}
	}
}
