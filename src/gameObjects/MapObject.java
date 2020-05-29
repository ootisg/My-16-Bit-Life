package gameObjects;

import java.util.ArrayList;
import java.util.Arrays;

import main.GameObject;
import map.MapTile;
import map.Room;


public class MapObject  extends GameObject {
	ArrayList <MapTile> affectedTiles = new ArrayList <MapTile> ();
	public MapObject () {
		
	}
	@Override
	public void frameEvent () {
		MapTile[] working = Room.getAllCollidingTiles(this);
		for (int i = 0; i < working.length; i++) {
			if (!affectedTiles.contains(working[i])) {
				
				affectedTiles.add(working[i]);
				Room.getMapObjects().put(Room.toPackedLong(working[i].x/16,working[i].y/16), this);
				}
		}
		for (int j = 0; j <affectedTiles.size(); j++) {
			boolean safe = false;
			for (int i = 0; i<working.length; i++) {
				if (affectedTiles.get(j).x == working[i].x && affectedTiles.get(j).y == working[i].y) {
					safe = true;
					}
				}
			if (!safe) {
				Room.getMapObjects().remove(Room.toPackedLong(affectedTiles.get(j).x/16,affectedTiles.get(j).y/16));
				affectedTiles.remove(j);
			}
		}
	}
}
