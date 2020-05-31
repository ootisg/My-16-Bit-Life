package gameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import enemys.Enemy;
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
		this.isCollidingChildren("GameObject");
		LinkedList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		Iterator <GameObject> iter = collidingObjects.iterator();
		while(iter.hasNext()) {
			GameObject currentObject = iter.next();
			if(currentObject.getClass().getPackage().getName().equals("enemys") ||currentObject.getClass().getPackage().getName().equals("players") ) {
				if (currentObject.getY() < this.getY() + this.hitbox().height - 5) {
					int workingNum = this.hitbox().x - currentObject.hitbox().x;
						if (workingNum < 0) {
							if (!currentObject.goX(currentObject.getX() + this.hitbox().getWidth() + workingNum + 1)) {
								if (currentObject.getClass().getPackage().getName().equals("enemys")) {
									Enemy currentEnmey = (Enemy) currentObject;
									currentEnmey.suffocate();
									currentEnmey.blackList();
								} else {
									//TODO put some code here for jeffrey once I figure out what Im gonna do with him
								}
							}
						} else {
							if (!currentObject.goX(currentObject.getX() - (currentObject.hitbox().width - workingNum) - 1)) {
								if (currentObject.getClass().getPackage().getName().equals("enemys")) {
									Enemy currentEnmey = (Enemy) currentObject;
									currentEnmey.suffocate();
									currentEnmey.blackList();
								} else {
									//TODO put some code here for jeffrey once I figure out what Im gonna do with him
								}
								}
						}
					} else {
						int workingNum = this.hitbox().y - currentObject.hitbox().y;
						if (!currentObject.goY(currentObject.getY() + this.hitbox().getWidth() - workingNum + 1)) {
							if (currentObject.getClass().getPackage().getName().equals("enemys")) {
								Enemy currentEnmey = (Enemy) currentObject;
								currentEnmey.deathEvent();
							} else {
								//TODO put some code here for jeffrey once I figure out what Im gonna do with him
							}
							}
					}
				}
		}
		}
	}