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
	public double findDirection (GameObject objectToCheck) {
		for (double temporaryDirection = 0; temporaryDirection <= 6.28; temporaryDirection = temporaryDirection + 0.001) {
			for (int smallDistance = 0; smallDistance <= 500; smallDistance = smallDistance + 1) {
				if (this.isColliding(objectToCheck)) {
					return temporaryDirection;
				}
				this.setX (this.getX () + Math.cos (temporaryDirection));
				this.setY (this.getY () + Math.sin (temporaryDirection));
			}
			this.setX(originalX);
			this.setY(originalY);
		}
		return 420;
	}

}
