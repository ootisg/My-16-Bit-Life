package projectiles;

import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class Disk extends Projectile {
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
		try {
		if (this.isColliding(Player.getActivePlayer())) {
			Player.getActivePlayer().damage(20);
		}
		} catch (NullPointerException e) {
			
		}
		if (this.outsideTheMap) {
			this.forget();
		}
	}
}
