package gameObjects;

import java.util.LinkedList;

import main.GameObject;
import main.ObjectHandler;

public class PairingObject extends GameObject {
	LinkedList <GameObject> pairedObject;
	public PairingObject () {
		this.setHitboxAttributes (0,0,16,16);
	
	}
	@Override 
	public void frameEvent () {
		if (pairedObject == null) {
			if (ObjectHandler.checkCollisionChildren("GameObject", this).collisionOccured()) {
				pairedObject = ObjectHandler.checkCollisionChildren("GameObject", this).getCollidingObjects();
			}
		}
	}
	public LinkedList <GameObject> getPairedObjects (){
		return pairedObject;
	}
}
