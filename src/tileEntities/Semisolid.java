package tileEntities;

import java.util.Arrays;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;

public class Semisolid extends TileEntitiy{
	public static boolean dontCollide = false;
	public Semisolid() {
		super();
	}
	@Override 
	public void frameEvent () {
		if (dontCollide) {
			Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
			if (Room.getCollidingTiles(j,this.getType()).length == 0) {
			dontCollide = false;
			}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			Jeffrey j = (Jeffrey) o;
			if (j.vy < 0) {
				dontCollide = true;
			}
				return !dontCollide;
		} else {
			return true;
		}
	}
}
