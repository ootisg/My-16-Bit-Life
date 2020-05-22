package projectiles;

import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class PaintballWeak extends Projectile {
	
	public static final Sprite paintball = new Sprite ("resources/sprites/redblack_ball_weak.png");
	
	//This class is not yet commented
	Random RNG;
	public PaintballWeak () {
		this.setSprite (paintball);
		this.setSpeed (15);
		RNG = new Random ();
		this.declare (0, 0);
		this.setHitboxAttributes (1, 1, 4, 4);
	}
	@Override
	public void projectileFrame () {
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(5) + 5);
				this.forget ();
			}
		}
		if (this.goingIntoWall) {
			this.forget ();
		}
	}
}