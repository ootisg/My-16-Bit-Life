package projectiles;

import main.GameObject;

// a class used for finding the direction a projectile needs to go in order to hit a cetain object
public class DirectionBullet extends Projectile {
	double originalX;
	double originalY;
	public DirectionBullet (double x, double y) {
		originalX = x;
		originalY = y;
		this.setX(x);
		this.setY(y);
		this.setHitboxAttributes(0, 0, 1, 1);
	}
	/**
	 * WARNING CAN CAUSE IMENSE LAG IF OVERUSED use with caution
	 * @param objectToCheck the object to find the direction to 
	 * @return the direction required to go towards that object 
	 */
	public double findDirection (GameObject objectToCheck) {
		double workingX = objectToCheck.getX() - this.getX();
		double workingY = Math.abs(objectToCheck.getY() - this.getY());
		double hype = workingX*workingX + workingY*workingY;
		hype = Math.sqrt(hype);
		if (this.getY() > objectToCheck.getY()) {
			return Math.PI + Math.acos(-workingX/hype); 
		} else {
			return Math.acos(workingX/hype);
		}
	}
	
}
