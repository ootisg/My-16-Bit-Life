package gameObjects;

import java.io.FileNotFoundException;

import main.GameObject;
import main.GameLoop;
import main.ObjectMatrix;
import map.Room;

public class Loadzone extends GameObject {
	String destination;
	public Loadzone () {
		this.setHitboxAttributes (0, 0, 16, 16);
		this.destination = "";
	}
	public void loadMap () {
		Room.loadRoom (destination);
	}
}
