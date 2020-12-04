package mapObjects;

import java.util.ArrayList;
import java.util.Arrays;

import gameObjects.CheckpointSystem;
import items.PogoStick;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;
import resources.Sprite;

public class SpikeUp extends MapObject{
	double JeffVy = 0;
	boolean inzilized = false;
	MoveingPlatform platform;
	boolean added = false;
	boolean checkpoint;
	public SpikeUp() {
		this.setSprite(new Sprite ("resources/sprites/SpikeUp.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
		this.suffocateObjects(false);
	}
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("checkpoint") != null) {
			if (this.getVariantAttribute("checkpoint").equals("true")) {
				checkpoint = true;
			} else {
				checkpoint = false;
			}
		}
	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		JeffVy = Jeffrey.getActiveJeffrey().getVy();
		if (!inzilized) {
			if (ObjectHandler.getObjectsByName("MoveingPlatform") != null) {
				for (int i = 0; i < ObjectHandler.getObjectsByName("MoveingPlatform").size(); i++) {
					if (this.isCollidingBEEG(ObjectHandler.getObjectsByName("MoveingPlatform").get(i))) {
						platform = (MoveingPlatform) ObjectHandler.getObjectsByName("MoveingPlatform").get(i);
						while(true) {
							if (this.isColliding(platform)) {
								this.setY(this.getY() - 1);
							} else {
								break;
							}
						}
						platform.addCarryObject(this);
						break;
					}
				}
			}
			inzilized = true;
		}
		if (platform != null ) {
			Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() + 3);
			if (Jeffrey.getActiveJeffrey().isColliding(this)){
				if (!platform.objectsToCarry.contains(Jeffrey.getActiveJeffrey())) {
					platform.addCarryObject(Jeffrey.getActiveJeffrey());
					added = true;
				}
			 } else {
				 if (added) {
					 platform.removeCarryObject(Jeffrey.getActiveJeffrey());
					 added = false;
				 }
			}
			Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() - 3);
		}
	}
	@Override
	public void onCollision(GameObject o) {	
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			if (!PogoStick.isPogoing()) {
				Jeffrey j = (Jeffrey) o;
				if (JeffVy > 3) {
					j.damage(12);
					CheckpointSystem.loadNewestCheckpoint();
				}
			}
		}	
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
				if (!PogoStick.isPogoing()) {
					this.onCollision(o);
					return true;
					
				} else {
					return true;
				}
		} else {
			if (o.getClass().getSimpleName().equals("MoveingPlatform")) {
				return !inzilized;
			} else {
			return false;
			}
		}
	}
}
