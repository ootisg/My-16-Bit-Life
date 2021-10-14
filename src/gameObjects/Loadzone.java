package gameObjects;

import java.io.FileNotFoundException;

import main.GameObject;
import main.ObjectHandler;
import main.GameLoop;
import map.Room;
import players.Player;

public class Loadzone extends GameObject {
	String destination;
	public Loadzone () {
		this.destination = "";
	}
	public void loadMap () {
		Room.loadRoom (destination);
	}
}
