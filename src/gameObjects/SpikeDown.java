package gameObjects;

import java.util.Arrays;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;
import resources.Sprite;

public class SpikeDown extends MapObject{
	boolean inzilized = false;
	MoveingPlatform platform;
	public SpikeDown() {
		this.setSprite(new Sprite ("resources/sprites/SpikeDown.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
		this.suffocateObjects(false);
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (!inzilized) {
			for (int i = 0; i < ObjectHandler.getObjectsByName("MoveingPlatform").size(); i++) {
				if (this.isCollidingBEEG(ObjectHandler.getObjectsByName("MoveingPlatform").get(i))) {
					platform = (MoveingPlatform) ObjectHandler.getObjectsByName("MoveingPlatform").get(i);
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
	}
	@Override
	public void onCollision(GameObject o) {	
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
				Jeffrey j = (Jeffrey) o;
				if (j.vy < -3) {
					j.damage(12);
				}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			this.onCollision(o);
			return false;
		} else {
			if (o.getClass().getSimpleName().equals("MoveingPlatform")) {
				return !inzilized;
			} else {
				return false;
			}
		}
	}
}
