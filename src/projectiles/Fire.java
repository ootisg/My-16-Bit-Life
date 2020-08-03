package projectiles;

import java.util.Random;

import players.Jeffrey;
import resources.Sprite;

public class Fire extends Projectile {
	Sprite fire;
	Random RNG;
	public Fire (boolean flipped) {
		fire = new Sprite ("resources/sprites/config/Fire.txt");
		this.setSpeed(0);
		this.getAnimationHandler().setFlipHorizontal(flipped);
		this.setSprite(fire);
		RNG = new Random();
		this.getAnimationHandler().setFrameTime(100);
		this.getAnimationHandler().setRepeat(false);
	}
	@Override
	public void projectileFrame () {
		if (this.isColliding(Jeffrey.getActiveJeffrey())) {
			Jeffrey.getActiveJeffrey().damage(RNG.nextInt(50) + 45);
		}
		if (this.getAnimationHandler().getFrame() ==  9) {
			this.forget();
		}
	}
	
}
