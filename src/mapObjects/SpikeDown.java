package mapObjects;

import java.util.ArrayList;
import java.util.Arrays;

import gameObjects.CheckpointSystem;
import gameObjects.StickyObject;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;
import resources.Sprite;

public class SpikeDown extends MapObject implements StickyObject{
	boolean inzilized = false;
	boolean checkpoint = false;
	public SpikeDown() {
		this.setSprite(new Sprite ("resources/sprites/SpikeDown.png"));
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
		if (!inzilized) {
			ArrayList <MapObject> working = new ArrayList <MapObject> ();
			ArrayList<ArrayList<GameObject>> fullList = ObjectHandler.getChildrenByName("MapObject");
			if (fullList != null) {
				for (int i = 0; i < fullList.size(); i++) {
					for (int j = 0; j <fullList.get(i).size(); j++) {
						try {
							CarryObject lame = (CarryObject) fullList.get(i).get(j);
							working.add((MapObject)fullList.get(i).get(j));
						} catch (ClassCastException e) {
							
						}
					}
				}
			}
			for (int i = 0; i <working.size(); i++) {
				if (this.isCollidingBEEG(working.get(i))) {
					CarryObject platform;
					platform = (CarryObject)working.get(i);
					while(true) {
						if (this.isColliding(working.get(i))) {
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
				if (j.getVy() < -3) {
					j.damage(12);
					if (checkpoint) {
						CheckpointSystem.loadNewestCheckpoint();
					}
				}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			this.onCollision(o);
			return false;
		} else {
			boolean correct = false;
			for (int i = 0; i < o.getClass().getInterfaces().length; i++) {
				if (o.getClass().getSimpleName().equals("CarryObject")) {
					correct = true;
				}
			}
			if (correct) {
				return !inzilized;
			} else {
				return false;
			}
		}
	}
}
