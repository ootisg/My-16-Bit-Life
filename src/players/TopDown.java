package players;

import main.GameObject;

import java.io.FileNotFoundException;

import gameObjects.KinematicObject;
import gameObjects.Loadzone;

public class TopDown extends KinematicObject {
	//TODO I'll deal with this one later
	public TopDown () {
		this.declare (0, 0);
		this.setHitboxAttributes (0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		double xprev = getX ();
		double yprev = getY ();
		if (!(keyDown ('W') || keyDown ('A') || keyDown ('S') || keyDown ('D'))) {
			if (keyReleased ('W')) {
				this.setSprite (sprites.tdIdle);
				this.getAnimationHandler ().setFrame (0);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
			if (keyReleased ('A')) {
				this.setSprite (sprites.tdIdle);
				this.getAnimationHandler ().setFrame (1);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
			if (keyReleased ('S')) {
				this.setSprite (sprites.tdIdle);
				this.getAnimationHandler ().setFrame (2);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
			if (keyReleased ('D')) {
				this.setSprite (sprites.tdIdle);
				this.getAnimationHandler ().setFrame (3);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
		} else {
			if (keyDown ('W')) {
				if (this.getAnimationHandler ().getSprite () != sprites.tdWalk [0]) {
					this.setSprite (sprites.tdWalk [0]);
				}
				this.setVelocityY (-10.15625);
			}
			if (keyDown ('S')) {
				if (this.getAnimationHandler ().getSprite () != sprites.tdWalk [2]) {
					this.setSprite (sprites.tdWalk [2]);
				}
				this.setVelocityY (3);
			}
			if (keyDown ('A')) {
				if (this.getAnimationHandler ().getSprite () != sprites.tdWalk [1]) {
					this.setSprite (sprites.tdWalk [1]);
				}
				this.setVelocityX (-3);
			}
			if (keyDown ('D')) {
				if (this.getAnimationHandler ().getSprite () != sprites.tdWalk [3]) {
					this.setSprite (sprites.tdWalk [3]);
				}
				this.setVelocityX (3);
			}
			this.getAnimationHandler ().setAnimationSpeed (.25);
		}
		this.physicsFrame ();
		/*if (room.isColliding (this.getHitbox ())) {
			this.setX (xprev);
			this.setY (yprev);
		}*/
		GameObject obj = this.getCollidingObject ("gameObjects.Loadzone");
		if (obj != null) {
			((Loadzone) obj).loadMap ();
		}
	}
}
