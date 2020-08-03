package projectiles;

import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class MiniCactus extends Projectile {
	public MiniCactus () {
		this.setSpeed(10);
		this.setSprite(new Sprite("resources/sprites/miniCactus.png"));
		this.setHitboxAttributes(0, 0, 8, 16);
	}
	@Override
	public void projectileFrame () {
		if (this.goingIntoWall) {
			this.setSpeed(0);
			double direction = 0;
			while (direction < Math.PI*2) {
				PokaDot dot = new PokaDot (direction,new Sprite ("resources/sprites/spike.png"));
				dot.declare(this.getX(), this.getY());
				dot.setSpeed(5);
				direction = direction + Math.PI/4;
			}
			this.forget();
		}
	}
}
