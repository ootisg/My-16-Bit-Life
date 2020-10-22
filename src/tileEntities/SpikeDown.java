package tileEntities;

import java.util.Arrays;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;

public class SpikeDown extends TileEntitiy{
	public static boolean dontCollide = false;
	public SpikeDown() {
		super();
	}
	@Override 
	public void frameEvent () {
		if (dontCollide) {
			if (Room.getCollidingTiles(Jeffrey.getActiveJeffrey(),this.getType()).length == 0) {
			dontCollide = false;
			}
		}
	}
	@Override
	public void onCollision(GameObject o) {	
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
				Jeffrey j = (Jeffrey) o;
				if (j.vy < -3) {
					j.damage(12);
					dontCollide = true;
					
				}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
				return !dontCollide;
		} else {
			return true;
		}
	}
}
