package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.ObjectHandler;
import mapObjects.MoveingPlatform;
import players.Jeffrey;
import resources.Sprite;

public class CelingVine extends GameObject {
	boolean inzilized;
	static boolean grabbed;
	MoveingPlatform attachedPlatform;
	public CelingVine () {
		this.setHitboxAttributes(0, 0, 16, 16);
		this.enablePixelCollisions();
		this.setSprite(new Sprite ("resources/sprites/VineTemp.png"));
	}
	@Override 
	public void frameEvent  () {
		if (!inzilized) {
			this.setY(this.getY() - 1);
			for (int i = 0; i < ObjectHandler.getObjectsByName("MoveingPlatform").size(); i++) {
				if (this.isColliding(ObjectHandler.getObjectsByName("MoveingPlatform").get(i))) {
					MoveingPlatform platform;
					platform = (MoveingPlatform) ObjectHandler.getObjectsByName("MoveingPlatform").get(i);
					attachedPlatform = platform;
					while(true) {
						if (this.isColliding(platform)) {
							this.setY(this.getY() + 1);
						} else {
							break;
						}
					}
					platform.addCarryObject(this);
					break;
				}
			}
			inzilized = true;
		}
		if (this.isColliding(Jeffrey.getActiveJeffrey()) && keyDown (32)) {
			Jeffrey.getActiveJeffrey().stopFall(true);	
			Jeffrey.getActiveJeffrey().constantSpeed();
			if (attachedPlatform != null) {
				ArrayList <GameObject> platforms = ObjectHandler.getObjectsByName("MoveingPlatform");
				for (int i = 0; i <platforms.size(); i++) {
					MoveingPlatform platform = (MoveingPlatform) platforms.get(i);
					if (platform.getCarryObjects().contains(Jeffrey.getActiveJeffrey())) {
						platform.removeCarryObject(Jeffrey.getActiveJeffrey());
					}
				}
				if (!attachedPlatform.getCarryObjects().contains(Jeffrey.getActiveJeffrey())) {
					attachedPlatform.addCarryObject(Jeffrey.getActiveJeffrey());
				}
			}
			if (!grabbed) {
				grabbed = true;
			}
		}
	}
	@Override
	public void staticLogic () {
		if (grabbed && (!Jeffrey.getActiveJeffrey().isColliding("CelingVine") || !keyDown(32))) {
			grabbed = false;
			Jeffrey.getActiveJeffrey().setVy(0);
			Jeffrey.getActiveJeffrey().normalSpeed();
			ArrayList <GameObject> platforms = ObjectHandler.getObjectsByName("MoveingPlatform");
			for (int i = 0; i <platforms.size(); i++) {
				MoveingPlatform platform = (MoveingPlatform) platforms.get(i);
				if (platform.getCarryObjects().contains(Jeffrey.getActiveJeffrey())) {
					platform.removeCarryObject(Jeffrey.getActiveJeffrey());
				}
			}
			Jeffrey.getActiveJeffrey().stopFall(false);
		}
	}
}
