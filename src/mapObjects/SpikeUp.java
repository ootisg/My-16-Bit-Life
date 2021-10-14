package mapObjects;

import java.util.ArrayList;
import java.util.Arrays;

import gameObjects.CheckpointSystem;
import gameObjects.StickyObject;
import items.PogoStick;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Player;
import resources.Sprite;

public class SpikeUp extends MapObject implements StickyObject{
	boolean inzilized = false;
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
	}
	@Override
	public void onCollision(GameObject o) {	
		if (o instanceof Player) {
			if (!PogoStick.isPogoing()) {
				Player j = (Player) o;
				if (j.getVy() > 3) {
					j.damage(12);
					CheckpointSystem.loadNewestCheckpoint();
				}
			}
		}	
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o instanceof Player) {
			
				if (!PogoStick.isPogoing()) {
					this.onCollision(o);
					return true;
					
				} else {
					return true;
				}
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
