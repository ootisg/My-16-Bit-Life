package projectiles;

import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class LifeVaccumProjectile extends Projectile {
	
	public static final Sprite paintball = new Sprite ("resources/sprites/redblack_ball.png");
	
	//This class is not yet commented
	Random RNG;
	public LifeVaccumProjectile () {
		this.setSprite (paintball);
		this.setSpeed (10);
		RNG = new Random ();
		this.setHitboxAttributes (0, 0, 4, 4);
	}
	@Override
	public void projectileFrame () {
		
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(20) + 50);
				this.forget ();
			}
		}
		if (this.goingIntoWall) {
			this.forget ();
		}
	}
}