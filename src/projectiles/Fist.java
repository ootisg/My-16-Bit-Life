package projectiles;

import java.awt.Point;
import java.util.Random;

import enemys.Enemy;
import map.Room;
import resources.Sprite;

public class Fist extends Projectile {
	Enemy guyToFuckUp;
	boolean FUCKEMUP;
	int timer = 0;
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
		this.setHitboxAttributes (0, 0, 24, 14);
		this.useLookingMode2();
	}
	@Override 
	public void frameEvent () {
		projectileFrame ();
		try {
		RNG = new Random ();
		this.setX(this.getX () + Math.cos (direction) * speed);
		this.setY(this.getY () + Math.sin (direction) * speed);
		if (getX () < 0 || getY () < 0 || getX () > Room.getWidth () *16 || getY () > Room.getHeight () * 16) {
			if (keep) {
			outsideTheMap = true;
			} else {
			this.forget ();
			}
		}
		} catch (IndexOutOfBoundsException e) {
		this.crashActions();
		}
	}
	@Override
	public void projectileFrame () {
		if (firstRun) {
			firstRun = false;
		}
		if (guyToFuckUp != null) {
			
			
			DirectionBullet bullet = new DirectionBullet(this.getX(), this.getY());
			this.setDirection(bullet.findDirection(guyToFuckUp));
			
			this.lookTowards(new Point ((int)guyToFuckUp.getX(),(int)guyToFuckUp.getY()));
			
			if (!guyToFuckUp.declared()) {
				guyToFuckUp = null;
			}
			
		} else {
			double distance = 42000000;
			for (int i = 0;  i <Enemy.enemyList.size(); i++) {
				if ((Math.abs(Enemy.enemyList.get(i).getX() - this.getX()) + Math.abs(Enemy.enemyList.get(i).getY() - this.getY())) < distance ) {
					guyToFuckUp = Enemy.enemyList.get(i);
					distance = (Math.abs(Enemy.enemyList.get(i).getX() - this.getX()) + Math.abs(Enemy.enemyList.get(i).getY() - this.getY()));
				}
			}
		}
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(10) + 30);
				this.forget ();
			}
		}
		/*if (Room.isColliding (this.hitbox (), xTo, yTo) || this.goingIntoWall) {
			guyToFuckUp = this.findGuyToFuckUp(true);
			if (FUCKEMUP) {
				guyToFuckUp.damage (RNG.nextInt(10) + 30);
			}
			this.forget ();
		}*/
	}
	/**
	 * returns the closeest enemy in the direction that the fist is going
	 * @param checkFuckEmUp changes FUCKEMUP to true if the enemy is really really close
	 * @return the closeest enemy in the direction that the fist is going
	 */
	/*public Enemy findGuyToFuckUp (boolean checkFuckEmUp) {
		if (!Enemy.enemyList.isEmpty()) {
			double distanceToNearestEnemy = 420069;
			double distanceToNearestEnemyFromCursor = 420069;
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
					if (getCursorY() + Room.getViewY() > Enemy.enemyList.get(i).getY()) {
						yDist = getCursorY() + Room.getViewY() - Enemy.enemyList.get(i).getY();
					} else {
						yDist = Enemy.enemyList.get(i).getY() - (getCursorY() + Room.getViewY());
					}
					if (getCursorX() + Room.getViewX() > Enemy.enemyList.get(i).getX()) {
						xDist = getCursorX() + Room.getViewX() - Enemy.enemyList.get(i).getX();
					} else {
						xDist = Enemy.enemyList.get(i).getX() - (getCursorX() + Room.getViewX());
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
	}*/
}
