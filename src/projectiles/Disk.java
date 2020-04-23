package projectiles;

import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Disk extends Projectile {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public Disk(double direction) {
		this.setDirection(direction);
		this.setSpeed(2);
		this.setSprite(new Sprite ("resources/sprites/config/disk.txt"));
		this.getAnimationHandler().setFrameTime(40);
		this.getAnimationHandler().setRepeat(false);
		this.setHitboxAttributes(0, 0, 32, 9);
	}
	@Override 
	public void frameEvent () {
		this.setX (this.getX () + Math.cos (direction) * speed); 
		this.setY (this.getY () + Math.sin (direction) * speed);
		if (this.isColliding(j)) {
			j.damage(20);
		}
		if (this.outsideTheMap) {
			this.forget();
		}
	}
}
