package gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


import main.GameObject;
import main.ObjectHandler;
import map.Room;
import mapObjects.MapObject;
import players.Jeffrey;
import resources.Sprite;
import switches.Activateable;

public class BubblePlatform extends MapObject {
	ArrayList<GameObject> objectsToCarry = new ArrayList<GameObject> ();
	Sprite bubble = new Sprite("resources/sprites/tiles/config/bubblePlatform.txt");
	int momentumX;
	int momentumY = 1;
	double moveX;
	double moveY;
	int timer;
	
	public BubblePlatform () {
		this.setSprite(bubble);
		this.setHitboxAttributes(0, 0, 16, 16);
		this.getAnimationHandler().setFrameTime(0);
	}
	
	public void frameEvent() {
		super.frameEvent();
		this.setY(this.getY() - 5);
		this.isCollidingChildren("GameObject");
		this.setY(this.getY() + 5);
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		collidingObjects.addAll(objectsToCarry);
		if (moveX >= Math.PI * 2) {
			moveX = 0;
		}
		moveX = moveX + (Math.PI / 90);
		this.setX(this.getX() +  Math.sin(moveX));
		if (Jeffrey.getActiveJeffrey().getSprite() == Jeffrey.getActiveJeffrey().standSprite && this.isColliding(Jeffrey.getActiveJeffrey())) {
			System.out.println("I think it's working");
		}
		/*if () {
			Jeffrey.getActiveJeffrey().setX(Jeffrey.getActiveJeffrey().getX() + Math.sin(moveX));
			Jeffrey.getActiveJeffrey().setY(this.getY() - 30);
			this.setY(this.getY() - .2);
			this.getAnimationHandler().setAnimationFrame(1);
			this.setHitboxAttributes(0, 3, 16, 13);
		} else {
			this.setY(this.getY() - .5);
			this.getAnimationHandler().setAnimationFrame(0);
			this.setHitboxAttributes(0, 0, 16, 16);
		}*/
		this.setY(this.getY() - .5);
	}
	
	public void addCarryObject (GameObject obj) {
		objectsToCarry.add(obj);
	}
	public void removeCarryObject (GameObject obj) {
		objectsToCarry.remove(obj);
	}
}
