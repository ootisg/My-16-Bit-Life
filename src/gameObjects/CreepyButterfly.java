package gameObjects;

import java.util.Random;

import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class CreepyButterfly extends Enemy {
	
	public static final Sprite butterflySprite = new Sprite ("resources/sprites/config/creepy_butterfly.txt");
	
	//This class is not yet commented
	private double x;
	private double y;
	private Random RNG;
	public CreepyButterfly () {
		setSprite (butterflySprite);
		setHitboxAttributes (0, 0, 16, 16);
		getAnimationHandler ().setFrameTime (55.5);
		player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		x = -1;
		y = -1;
		RNG = new Random ();
		this.health = 1;
		this.defence = 0;
	}
	@Override
	public void enemyFrame () {
		if (x == -1) {
			x = this.getX ();
			y = this.getY ();
		}
		double targetX = player.getX ();
		double targetY = player.getY ();
		if (((targetX > this.getX() && targetX < this.getX() + 22) || (targetX < this.getX() && targetX> this.getX() - 22) &&  ((targetY > this.getY() && targetY < this.getY() + 22) || (targetY < this.getY() && targetY> this.getY() - 22))) || Room.isColliding(this.hitbox()) ) {
			int gayBabyJail;
			double xCopy = this.getX();
			double yCopy = this.getY();
			gayBabyJail =RNG.nextInt(359);
			x = x + Math.cos (gayBabyJail) * 3;
			y = y + Math.sin (gayBabyJail) * 3;
			setX ((int) x);
			setY ((int) y);
			while (Room.isColliding(this.hitbox())) {
				this.setX(xCopy);
				this.setY(yCopy);
				x = x - Math.cos(gayBabyJail) * 3;
				y = y - Math.sin(gayBabyJail) * 3;
				gayBabyJail = RNG.nextInt (359);
				x = x + Math.cos (gayBabyJail) * 3;
				y = y + Math.sin (gayBabyJail) * 3;
				setX ((int) x);
				setY ((int) y);
			}
		} else {
		if (Math.sqrt ((x - targetX) * (x - targetX) + (y - targetY) * (y - targetY)) <= 128) {
			double slope = (y - targetY) / (x - targetX);
			double angle = Math.atan (slope);
			if (x > targetX) {
				angle -= Math.PI;
			}
			x = x + Math.cos (angle) * 3;
			y = y + Math.sin (angle) * 3;
			setX ((int) x);
			setY ((int) y);
		}
		}
	}
}