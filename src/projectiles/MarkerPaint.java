package projectiles;

import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;

public class MarkerPaint extends Projectile {
	
	public static final Sprite red_paint = new Sprite ("resources/sprites/config/marker_paint_red.txt");
	public static final Sprite yellow_paint = new Sprite ("resources/sprites/config/marker_paint_yellow.txt");
	public static final Sprite blue_paint = new Sprite ("resources/sprites/config/marker_paint_blue.txt");
	public static final Sprite orange_paint = new Sprite ("resources/sprites/config/marker_paint_orange.txt");
	public static final Sprite green_paint = new Sprite ("resources/sprites/config/marker_paint_green.txt");
	public static final Sprite purple_paint = new Sprite ("resources/sprites/config/marker_paint_purple.txt");
	
	int minDamage = 0;
	int maxDamage = 0;
	
	//This class is not yet commented
	Random RNG;
	public MarkerPaint (int color) {
		RNG = new Random ();
		this.declare(0, 0);
		switch(color) {
			case 1:
				this.setSprite (red_paint);
				this.setSpeed (8);
				this.setHitboxAttributes (0, 1, 7, 7);
				minDamage = 5;
				maxDamage = 5;
				break;
			case 2:
				this.setSprite (yellow_paint);
				this.setSpeed (8);
				this.setHitboxAttributes (0, 0, 8, 8);
				minDamage = 0;
				maxDamage = 0;
				break;
			case 3:
				this.setSprite (blue_paint);
				this.setSpeed (14);
				this.setHitboxAttributes (0, 1, 7, 7);
				minDamage = 0;
				maxDamage = 0;
				break;
			case 4:
				this.setSprite (orange_paint);
				this.setSpeed (8);
				this.setHitboxAttributes (0, 0, 8, 8);
				minDamage = 5;
				maxDamage = 5;
				break;
			case 5:
				this.setSprite (green_paint);
				this.setSpeed (14);
				this.setHitboxAttributes (0, 0, 8, 8);
				minDamage = 0;
				maxDamage = 0;
				break;
			case 6:
				this.setSprite (purple_paint);
				this.setSpeed (14);
				this.setHitboxAttributes (0, 1, 7, 7);
				minDamage = 5;
				maxDamage = 5;
				break;
			default:
				this.setSprite (red_paint);
				this.setSpeed (8);
				this.setHitboxAttributes (0, 1, 7, 7);
				minDamage = 5;
				maxDamage = 5;
				break;
		}
	}
	@Override
	public void projectileFrame () {
		double xTo = this.getX () + Math.cos (direction) * speed;
		double yTo = this.getY () + Math.sin (direction) * speed;
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(10 + maxDamage) + minDamage + 5);
				this.forget ();
			}
		}
		if (this.goingIntoWall) {
			this.forget ();
		}
	}
}