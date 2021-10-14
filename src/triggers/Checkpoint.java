package triggers;

import java.util.ArrayList;

import gameObjects.CheckpointSystem;
import items.Inventory;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;

public class Checkpoint extends RessesiveTrigger {
	Inventory savedItems; 
	public Checkpoint () {
		this.setHitboxAttributes(0,0, 16,16);
	}

	public void load () {
		
		double [] savedHealth = saveHealths();
		
		Room.loadRoom(Room.getRoomName());
		Player.setInventory(savedItems);
		ArrayList <ArrayList <GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
		for (int i = 0; i <working.size(); i++) {
			for (int j = 0; j < working.get(i).size(); j++) {
				working.get(i).get(j).checkpointCode(this);
			}
		}
		
		loadHealths(savedHealth);
	}
	
	private void loadHealths (double [] healths) {
		
		try {
			GameCode.getPartyManager().getPlayer(0).health = healths[0];
		} catch (NullPointerException e) {
		
		}
		
		try {
			GameCode.getPartyManager().getPlayer(1).health = healths[1];
		} catch (NullPointerException e) {
			
		}
		try {
			GameCode.getPartyManager().getPlayer(2).health = healths[2];
		} catch (NullPointerException e) {
			
		}
		
	}
	private double [] saveHealths () {
		double jHealth = -1;
		double sHealth = -1;
		double rHealth = -1;
		try {
			jHealth = GameCode.getPartyManager().getPlayer(0).health;
		} catch (NullPointerException e) {
		
		}
		
		try {
			sHealth = GameCode.getPartyManager().getPlayer(1).health;
		} catch (NullPointerException e) {
			
		}
		try {
			rHealth = GameCode.getPartyManager().getPlayer(2).health;
		} catch (NullPointerException e) {
			
		}
		
		double [] returnArray = {jHealth,sHealth,rHealth};
		
		return returnArray;
		
	}
	@Override
	public void triggerEvent () {
		CheckpointSystem.setNewestCheckpoint(this);
		this.eventFinished = true;
		this.save();
	}
	public void save () {
		savedItems = Player.getInventory().clone();
	}
}
