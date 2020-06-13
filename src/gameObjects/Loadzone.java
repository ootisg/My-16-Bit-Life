package gameObjects;

import java.io.FileNotFoundException;

import main.GameObject;
import main.ObjectHandler;
import main.GameLoop;
import map.Room;
import players.Jeffrey;

public class Loadzone extends GameObject {
	String destination;
	boolean inzialized = false;
	public Loadzone () {
		this.setHitboxAttributes (0, 0, 16, 16);
		this.destination = "";
	}
	@Override 
	public void frameEvent () {
		if (!inzialized) {
			Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
			j.setX(this.getX());
			j.setY(this.getY());
			inzialized = true;
		}
	}
	public void loadMap () {
		Room.loadRoom (destination);
	}
}
