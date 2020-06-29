package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;

import java.util.Random;

import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;

public abstract class Projectile extends GameObject {
	//Template for projectiles
	protected double direction = 0;
	protected double speed = 0;
	Random RNG;
	boolean keep = false;
	boolean goingIntoWall;
	boolean outsideTheMap = false;
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	@Override
	public void frameEvent () {
		projectileFrame ();
		//implemnt quartersteps
		try {
		RNG = new Random ();
		double quarterstepX =  (Math.cos (direction) * speed)/4;
		double quarterstepY =  (Math.sin (direction) * speed)/4;
		for (int i = 0; i < 4; i++) {
			if (!this.goX (this.getX () + quarterstepX) || !this.goY (this.getY () + quarterstepY)) {
				goingIntoWall = true;
			} else {
				goingIntoWall = false;
			}
		}
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
	//override in a projectile class to configure what the projectile does when it would cause a crash (forgets by defult)
	public void crashActions() {
		this.forget();
	}
	public double getDirection () {
		return this.direction;
	}
	//decides weather or not to keep the object around when it goes outside the map
	// ps I don't know how to spell weather
	public void keep () {
		keep = !keep;
	}
	public boolean outsideTheMap() {
		return outsideTheMap;
	}
	public boolean checkIfGoingIntoWall () {
		return goingIntoWall;
	}
	public double getSpeed () {
		return this.speed;
	}
	public void setDirection (double direction) {
		this.direction = direction;
	}
	public void setSpeed (double speed) {
		this.speed = speed;
	}
	public void setAttributes (double x, double y, double direction, double speed) {
		this.setX (x);
		this.setY (y);
		this.direction = direction;
		this.speed = speed;
	}
	public void setAttributes (double x, double y, double direction) {
		this.setX (x);
		this.setY (y);
		this.direction = direction;
	}
	//makes player get damaged by projectile if put in projectileFrame
	public void makeDamageingProjectile (int damageRandom, int garentiedDamage) {
		if (this.isColliding(player)) {
			player.damage(RNG.nextInt(damageRandom) + garentiedDamage);
		}
	}
	public void SpinAwayFromWall () {
		while (!this.goXandY (this.getX () + Math.cos (direction) * speed,this.getY () + Math.sin (direction) * speed)) {
			direction = direction + 0.1;
		}
	}
	public void projectileFrame () {
		
	}
}