package projectiles;

import java.util.Random;

import gameObjects.Enemy;
import map.Room;
import resources.Sprite;

public class Paintball extends Projectile {
	
	public static final Sprite paintball = new Sprite ("resources/sprites/redblack_ball.png");
	
	//This class is not yet commented
	Random RNG;
	public Paintball () {
		this.setSprite (paintball);
		this.setSpeed (20);
		RNG = new Random ();
		this.declare (0, 0);
		this.setHitboxAttributes (0, 0, 4, 4);
	}
	@Override
	public void projectileFrame () {
		//Need a high speed collision solution TODO (probably subdivisions)
		double xTo = this.getX () + Math.cos (direction) * speed;
		double yTo = this.getY () + Math.sin (direction) * speed;
		for (int i = 0; i < Enemy.enemyList.length; i ++) {
			if (this.isColliding(Enemy.enemyList[i])){
				System.out.println("degg");
			}
				//Enemy target = (Enemy) getCollisionInfo ().getCollidingObjects ().getFirst ();
				//target.damage (RNG.nextInt(10) + 10);
				//this.forget ();
		}
		if (Room.isColliding (this.hitbox (), xTo, yTo)) {
			this.forget ();
		}
	}
}