package projectiles;

import java.util.Random;

import gameObjects.Enemy;
import map.Room;
import resources.Sprite;

public class Fist extends Projectile {
	Enemy guyToFuckUp;
	boolean FUCKEMUP;
	boolean defult;
	boolean firstRun;
	public Fist () {
		this.setSprite (new Sprite ("resources/sprites/config/homeingfist.txt"));
		this.setSpeed (10);
		FUCKEMUP = false;
		firstRun = true;
		defult = true;
		RNG = new Random ();
		this.getAnimationHandler().setFrameTime(10);
		this.declare (0, 0);
		this.setHitboxAttributes (0, 0, 24, 14);
	}
	@Override
	public void projectileFrame () {
		if (firstRun) {
			if (this.getDirection() > 1.57 && this.getDirection() < 4.71) {
				this.getAnimationHandler().setFlipHorizontal(true);
			}
			firstRun = false;
		}
		if (guyToFuckUp != null) {
			if (defult) {
			DirectionBullet bullet = new DirectionBullet(this.getX(), this.getY());
			this.setDirection(bullet.findDirection(guyToFuckUp));
			defult = false;
			}
		} else {
			guyToFuckUp = this.findGuyToFuckUp(false);
		}
		double xTo = this.getX () + Math.cos (direction) * speed;
		double yTo = this.getY () + Math.sin (direction) * speed;
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(10) + 30);
				this.forget ();
			}
		}
		if (Room.isColliding (this.hitbox (), xTo, yTo) || this.goingIntoWall) {
			guyToFuckUp = this.findGuyToFuckUp(true);
			if (FUCKEMUP) {
				guyToFuckUp.damage (RNG.nextInt(10) + 30);
			}
			this.forget ();
		}
	}
	/**
	 * returns the closeest enemy in the direction that the fist is going
	 * @param checkFuckEmUp changes FUCKEMUP to true if the enemy is really really close
	 * @return the closeest enemy in the direction that the fist is going
	 */
	public Enemy findGuyToFuckUp (boolean checkFuckEmUp) {
		if (!Enemy.enemyList.isEmpty()) {
			double distanceToNearestEnemy = 420000;
			double distanceToNearestEnemyFromCursor = 420000;
			int index = 420;
			int fuckemupIndex = 420;
			for (int i = 0; i < Enemy.enemyList.size(); i++) {
				if (Enemy.enemyList.get(i).getX() > this.getX() && (this.getDirection() < 1) || Enemy.enemyList.get(i).getX() < this.getX() && (this.getDirection() > 1)){
					double yDist = 0;
					double xDist = 0;
					
					if (this.getY() > Enemy.enemyList.get(i).getY()) {
						yDist = this.getY() - Enemy.enemyList.get(i).getY();
					} else {
						yDist = Enemy.enemyList.get(i).getY() - this.getY();
					}
					if (this.getX() > Enemy.enemyList.get(i).getX()) {
						xDist = this.getX() - Enemy.enemyList.get(i).getX();
					} else {
						xDist = Enemy.enemyList.get(i).getX() - this.getX();
					}
					if (distanceToNearestEnemy > xDist + yDist) {
						distanceToNearestEnemy = xDist + yDist;
						fuckemupIndex = i;
					}
					if (getCursorY() > Enemy.enemyList.get(i).getY()) {
						yDist = getCursorY() - Enemy.enemyList.get(i).getY();
					} else {
						yDist = Enemy.enemyList.get(i).getY() - getCursorY();
					}
					if (getCursorX() > Enemy.enemyList.get(i).getX()) {
						xDist = getCursorX() - Enemy.enemyList.get(i).getX();
					} else {
						xDist = Enemy.enemyList.get(i).getX() - getCursorX();
					}
					if (distanceToNearestEnemyFromCursor > xDist + yDist) {
						distanceToNearestEnemyFromCursor = xDist + yDist;
						index = i;
					}
				}
			}
			if (index != 420) {
				if (distanceToNearestEnemy < 80 && checkFuckEmUp) {
					FUCKEMUP = true;
				}
				if (!FUCKEMUP) {
				return Enemy.enemyList.get(index);
				} else {
				return Enemy.enemyList.get(fuckemupIndex);	
				}
			} else {
				return null;
			}
		}
		return null;
	}
}
