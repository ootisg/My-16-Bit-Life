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

public class SpikeRight extends MapObject{
	boolean inzilized = false;
	CarryObject platform;
	boolean checkpoint;
	public SpikeRight() {
		this.setSprite(new Sprite ("resources/sprites/SpikeRight.png"));
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
						if (this.isColliding (working.get(i))) {
							this.setX(this.getX() + 1);
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
				if (j.vx < -3 && (j.getY() + 20 > this.getY())) {
					j.damage(12);
					CheckpointSystem.loadNewestCheckpoint();
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
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			if (PogoStick.isPogoing()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
