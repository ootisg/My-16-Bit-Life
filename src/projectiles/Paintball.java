package projectiles;

import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class Paintball extends Projectile {
	
	public static final Sprite paintball = new Sprite ("resources/sprites/redblack_ball.png");
	
	//This class is not yet commented
	Random RNG;
	public Paintball () {
		this.setSprite (paintball);
		this.setSpeed (16);
		RNG = new Random ();
		this.setHitboxAttributes (0, 0, 4, 4);
	}
	@Override
	public void projectileFrame () {
		double xTo = this.getX () + Math.cos (direction) * speed;
		double yTo = this.getY () + Math.sin (direction) * speed;
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(10) + 10);
				this.forget ();
			}
		}
		if (this.goingIntoWall) {
			this.forget ();
		}
	}
}