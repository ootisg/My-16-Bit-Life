package mapObjects;


import java.util.ArrayList;

import gameObjects.CheckpointSystem;
import gameObjects.StickyObject;
import items.PogoStick;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Player;
import resources.Sprite;

public class SpikeLeft extends MapObject implements StickyObject{
	boolean inzilized = false;
	boolean checkpoint = false;
	CarryObject platform;
	public SpikeLeft() {
		this.setSprite(new Sprite ("resources/sprites/SpikeLeft.png"));
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
			for (int i = 0; i < working.size(); i++) {
				if (this.isCollidingBEEG(working.get(i))) {
					platform = (CarryObject) working.get(i);
					while(true) {
						if (this.isColliding(working.get(i))) {
							this.setX(this.getX() - 1);
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
		if (o instanceof Player) {
				Player j = (Player) o;
				if (j.vx > 3 && (j.getY() + 20 > this.getY())) {
					j.damage(12);
					if (checkpoint) {
					CheckpointSystem.loadNewestCheckpoint();	
				}
			}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		boolean correct = false;
		for (int i = 0; i < o.getClass().getInterfaces().length; i++) {
			if (o.getClass().getSimpleName().equals("CarryObject")) {
				correct = true;
			}
		}
		if (correct) {
			return !inzilized;
		} 
		this.onCollision(o);
		if (o instanceof Player) {
			if (PogoStick.isPogoing()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
