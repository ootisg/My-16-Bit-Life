package triggers;

import java.util.ArrayList;

import gameObjects.CheckpointSystem;
import items.Inventory;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;

public class Checkpoint extends RessesiveTrigger {
	Inventory savedItems; 
	public Checkpoint () {
		this.setHitboxAttributes(0,0, 16,16);
	}

	public void load () {
		Room.loadRoom(Room.getRoomName());
		ArrayList <ArrayList <GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
		for (int i = 0; i <working.size(); i++) {
			for (int j = 0; j < working.get(i).size(); j++) {
				working.get(i).get(j).checkpointCode(this);
			}
		}
		Jeffrey.setInventory(savedItems);
	}
	@Override
	public void triggerEvent () {
		CheckpointSystem.setNewestCheckpoint(this);
		this.eventFinished = true;
		this.save();
	}
	public void save () {
		savedItems = Jeffrey.getInventory().clone();
	}
}
