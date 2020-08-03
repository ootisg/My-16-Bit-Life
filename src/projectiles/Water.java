package projectiles;

import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Water extends Projectile {
	public Water (boolean direction) {
		if (direction) {
			this.setDirection(0);
		} else {
			this.setDirection(3.14);
			this.getAnimationHandler().setFlipHorizontal(true);
		}
		this.setSprite(new Sprite ("resources/sprites/config/water.txt"));
		this.setSpeed(3);
		this.setHitboxAttributes(0, 0, 16, 8);
		this.getAnimationHandler().setFrameTime(30);
	}
	@Override 
	public void projectileFrame () {
		if (this.isColliding(Jeffrey.getActiveJeffrey())) {
			if (this.getDirection() == 3.14) {
				Jeffrey.getActiveJeffrey().goX(Jeffrey.getActiveJeffrey().getX() - (this.speed * 1.2));
			} else {
				Jeffrey.getActiveJeffrey().goX(Jeffrey.getActiveJeffrey().getX() + (this.speed * 1.2));
			}
		}
		if (this.goingIntoWall) {
			this.forget();
		}
	}
}
