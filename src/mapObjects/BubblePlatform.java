package mapObjects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


import main.GameObject;
import main.ObjectHandler;
import map.MapTile;
import map.Room;
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
		this.suffocateObjects(false);
		this.setPersistence(true);
	}
	public void frameEvent() {
		super.frameEvent();
		this.setHitboxAttributes(0, -3, 16, 16);
		this.isCollidingChildren("GameObject");
		this.setHitboxAttributes(0, 0, 16, 16);
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		for (int i = 0; i < collidingObjects.size(); i++) {
			if (!collidingObjects.get(i).isPushable()) {
				collidingObjects.remove(i);
				i = i - 1;
			}
		}
		if (inzialSpeed == 0) {
			if (moveX >= Math.PI * 2) {
				moveX = 0;
			}
			moveX = moveX + (Math.PI / 90);
			this.setX(Math.sin(moveX), collidingObjects);
			this.setY(-.5,collidingObjects);
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
//		//semisolid stuff
//	if (Jeffrey.getActiveJeffrey().getVy() < 0 || ((Jeffrey.getActiveJeffrey().getX() + 7 < this.getX() && Jeffrey.getActiveJeffrey().vx > 0 && Jeffrey.getActiveJeffrey().isColliding(new Rectangle ((int)this.getX() - 6,(int)this.getY(),8,16))) || (Jeffrey.getActiveJeffrey().vx < 0 && Jeffrey.getActiveJeffrey().isColliding(new Rectangle ((int)this.getX() + 14,(int)this.getY(),8,16))))){
//			
//			collide = false;
//		} 
	}
	public boolean doesColide (GameObject o) {

		if (o.getClass().getSimpleName().equals("Jeffrey")) {
		Jeffrey j = (Jeffrey) o;
		if (j.getVy() < 0 || (j.getYPrevious() + j.hitbox().height > this.getY())){
				return false;
			} 
				return true;
		} else {
			if (o.getClass().getPackageName().equals("projectiles")) {
				return false;
			}
			return true;
		}
	}
}
