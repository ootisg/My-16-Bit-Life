package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;

public abstract class Projectile extends GameObject {
	//Template for projectiles
	protected double direction = 0;
	protected double speed = 0;
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
	@Override
	public void frameEvent () {
		projectileFrame ();
		this.setX (this.getX () + Math.cos (direction) * speed);
		this.setY (this.getY () + Math.sin (direction) * speed);
		if (getX () < 0 || getY () < 0 || getX () > Room.getWidth () *16 || getY () > Room.getHeight () * 16) {
			this.forget ();
		}
	}
	public double getDirection () {
		return this.direction;
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
	public void projectileFrame () {
		
	}
}