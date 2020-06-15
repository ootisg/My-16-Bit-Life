package gameObjects;

import java.util.Iterator;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Firefly extends GameObject {
	Light light;
	boolean inizialized;
	public Firefly () {
		this.setSprite(new Sprite ("resources/sprites/config/firefly.txt"));
		this.getAnimationHandler().setFrameTime(50);
	}
	@Override 
	public void frameEvent () {
		if (!inizialized) {
			try {
			Iterator <GameObject> iter = ObjectHandler.getObjectsByName("Point").iterator();
			while (iter.hasNext()) {
				Point working = (Point)iter.next();
				if (working.getVariantAttribute("PointNumber").equals(this.getVariantAttribute("attachedPoint"))) {
					light = new Light (working);
					light.declare(this.getX() -88, this.getY());
					break;
				}
			}
			} catch (NullPointerException e) {
				light = new Light (new Point (0,0));
				light.declare(this.getX() -88, this.getY());
			}
			inizialized = true;
		}
	}
	private class Light extends GameObject {
		int height = 240;
		Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
		double returnX;
		double returnY;
		public Light (Point returnPoint) {
			this.adjustHitboxBorders();
			this.setSprite(new Sprite ("resources/sprites/config/fireflyLight.txt"));
			this.enablePixelCollisions();
			this.getAnimationHandler().setFrameTime(250);
			this.getAnimationHandler().enableAlternate();
			this.setHitboxAttributes(0, 27, 208, height);
			returnX = returnPoint.getX();
			returnY = returnPoint.getY();
		}
		@Override
		public void frameEvent () {
			this.setHitboxAttributes(104, 27, 4, height);
			if (Room.isColliding(this)) {
				while (Room.isColliding(this) && height != 0) {
					height = height - 1;
					this.setHitboxAttributes(104, 27, 4, height);
				}
			} else {
				while (!Room.isColliding(this) && height != 240) {
					height = height + 1;
					this.setHitboxAttributes(104, 27, 4, height);
				}
				height = height -1;
			}
			height = height + 27;
			this.setHitboxAttributes(0, 16, 208, height);
			this.getAnimationHandler().setHeight(height);
			this.getAnimationHandler().scale(208, height);
			if (this.isColliding(j)) {
				j.setX(returnX);
				j.setY(returnY);
			}
			if (this.isColliding("Plant")) {
				Iterator<GameObject> iter = this.getCollisionInfo().getCollidingObjects().iterator();
				while (iter.hasNext()) {
					GameObject working = iter.next();
					if (working.getClass().getSimpleName().equals("Plant")) {
						Plant workingPlant = (Plant) working;
						if (workingPlant.isExposed()) {
							workingPlant.makeBroken();	
						}
					}
				}
			}
		}
	}
}
