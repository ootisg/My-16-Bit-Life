package gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


import main.GameObject;
import main.ObjectHandler;
import map.Room;
<<<<<<< HEAD
=======
import mapObjects.CarryObject;
>>>>>>> 907add13ac78cbc1d53e65bea29ff92ae4a70918
import mapObjects.MapObject;
import players.Jeffrey;
import resources.Sprite;
import switches.Activateable;

public class BubblePlatform extends CarryObject {
	Sprite bubble = new Sprite("resources/sprites/tiles/config/bubblePlatform.txt");
	int momentumX;
	int momentumY = 1;
	double moveX;
	double moveY;
	int timer;
	double inzialSpeed;
	public BubblePlatform () {
		this(0);
	}
	public BubblePlatform (double intzialSpeed) {
		inzialSpeed = intzialSpeed;
		this.setSprite(bubble);
		this.setHitboxAttributes(0, 0, 16, 16);
		this.getAnimationHandler().setFrameTime(0);
		this.setGameLogicPriority(-3);
	}
	public void frameEvent() {
		super.frameEvent();
		this.setY(this.getY() - 3);
		this.isCollidingChildren("GameObject");
		this.setY(this.getY() + 3);
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
<<<<<<< HEAD
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
=======
		if (inzialSpeed == 0) {
			if (moveX >= Math.PI * 2) {
				moveX = 0;
			}
			moveX = moveX + (Math.PI / 90);
			this.setX(Math.sin(moveX), collidingObjects);
			this.setY(-.5,collidingObjects);
>>>>>>> 907add13ac78cbc1d53e65bea29ff92ae4a70918
		} else {
			this.setX(inzialSpeed, collidingObjects);
			this.setY(-.5,collidingObjects);
			if (inzialSpeed > 0) {
				inzialSpeed = inzialSpeed - 1;
				if (inzialSpeed < 0) { 
					inzialSpeed = 0;
					moveX = Math.PI;
				}
			} else {
				inzialSpeed = inzialSpeed + 1;
				if (inzialSpeed > 0) { 
					inzialSpeed = 0;
					moveX = 0;
				}
			}
		}
	}
}
