package gameObjects;

import java.util.Iterator;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;
import switches.Activateable;

public class Firefly extends GameObject implements Activateable{
	Light light;
	boolean inizialized;
	public Firefly () {
		this.setSprite(new Sprite ("resources/sprites/config/firefly.txt"));
		this.getAnimationHandler().setFrameTime(50);
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override 
	public void frameEvent () {
		if (!inizialized) {
			boolean active = true;
			if (this.getVariantAttribute("active") != null) {
				if (!(this.getVariantAttribute("active").equals("nv") || this.getVariantAttribute("active").equals("false"))) {
					active = true;
				} else {
					active = false;
				}
			}
			try {
			Iterator <GameObject> iter = ObjectHandler.getObjectsByName("Point").iterator();
			while (iter.hasNext()) {
				Point working = (Point)iter.next();
				if (working.getVariantAttribute("PointNumber").equals(this.getVariantAttribute("attachedPoint"))) {
					light = new Light (working,active);
					light.declare(this.getX() -88, this.getY());
					break;
				}
			}
			} catch (NullPointerException e) {
				light = new Light (new Point (0,0),active);
				light.declare(this.getX() -88, this.getY());
			}
			
			inizialized = true;
		}
	}
	private class Light extends GameObject {
		int height = 240;
		double returnX;
		double returnY;
		boolean active = true;
		public Light (Point returnPoint, boolean active) {
			this.adjustHitboxBorders();
			this.setSprite(new Sprite ("resources/sprites/config/fireflyLight.txt"));
			this.enablePixelCollisions();
			this.getAnimationHandler().setFrameTime(250);
			this.active = active;
			this.getAnimationHandler().enableAlternate();
			this.setHitboxAttributes(0, 27, 208, height);
			returnX = returnPoint.getX();
			returnY = returnPoint.getY();
		}
		@Override
		public void frameEvent () {
			if (this.active) {
				this.show();
			
				//makes the light level with the floor
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
				if (this.isColliding(Player.getActivePlayer())) {
					Player.getActivePlayer().setX(returnX);
					Player.getActivePlayer().setY(returnY);
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
			} else {
				this.hide();
			}
		}
	}
	@Override
	public void activate() {
		light.active = true;
	}
	@Override
	public void deactivate() {
		light.active = false;
	}
	@Override
	public boolean isActivated() {
		return light.active;
	}
	@Override
	public void pair() {
	}
	
}
