package mapObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import enemys.Enemy;
import gameObjects.CheckpointSystem;
import main.GameObject;
import map.MapTile;
import map.Room;
import players.Jeffrey;


public class MapObject  extends GameObject {
	ArrayList <MapTile> affectedTiles = new ArrayList <MapTile> ();
	boolean suffocateObjects = true;
	public MapObject () {
		this.setGameLogicPriority(420);
		
	}
	@Override
	public void frameEvent () {
		MapTile[] working = Room.getAllCollidingTiles(this);
		for (int i = 0; i < working.length; i++) {
			if (!affectedTiles.contains(working[i])) {
				affectedTiles.add(working[i]);
				if (Room.getMapObjects().get(Room.toPackedLong((int)working[i].x/16,(int)working[i].y/16)) == null) {
					Room.getMapObjects().put((Room.toPackedLong((int)working[i].x/16,(int)working[i].y/16)),new ArrayList <GameObject> ());
				}
				
				Room.getMapObjects().get(Room.toPackedLong((int)working[i].x/16,(int)working[i].y/16)).add(this);
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
				if (Room.getMapObjects().get(Room.toPackedLong((int)affectedTiles.get(j).x/16,(int)affectedTiles.get(j).y/16)) == null) {
					Room.getMapObjects().put((Room.toPackedLong((int)affectedTiles.get(j).x/16,(int)affectedTiles.get(j).y/16)),new ArrayList <GameObject> ());
				}
				Room.getMapObjects().get(Room.toPackedLong((int)affectedTiles.get(j).x/16,(int)affectedTiles.get(j).y/16)).remove(this);
				affectedTiles.remove(j);
			}
		}
		/*if (suffocateObjects) {
			this.suffocationCode();
		} */
	}
	// unused old suffocation method that caused problems
	private void suffocationCode () {
		this.isCollidingChildren("GameObject");
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		Iterator <GameObject> iter = collidingObjects.iterator();
		while(iter.hasNext()) {
			GameObject currentObject = iter.next();
			if(currentObject.getClass().getPackage().getName().equals("enemys") ||currentObject.getClass().getPackage().getName().equals("players") ) {
				int xPossiblity1 = 0;
				int xPossiblity2 = 0;
				int yPossibility1 = 0;
				int yPossibility2 = 0;
				xPossiblity1 = (this.hitbox().x + this.hitbox().width) - currentObject.hitbox().x;
				xPossiblity2 = this.hitbox().x -(currentObject.hitbox().x + currentObject.hitbox().width);
				yPossibility1 = this.hitbox().y -(currentObject.hitbox().y + currentObject.hitbox().height);
				yPossibility2 = (this.hitbox().y + this.hitbox().height) - currentObject.hitbox().y;
				int [] workingList = {xPossiblity1,xPossiblity2,yPossibility1,yPossibility2};
				int [] distanceList = new int [4];
				for (int i = 0; i < distanceList.length; i++) {
					int biggestIndex = 0;
					int biggestValue = 0;
					for (int j = 0; j < workingList.length; j++) {
						if (Math.abs(workingList[j]) > biggestValue) {
							biggestValue = Math.abs(workingList[j]);
							biggestIndex = j;
						}
					}
					distanceList[i] = workingList[biggestIndex];
					workingList[biggestIndex] = 0;
				}
				boolean broken = false;
				for (int i = 3; i < distanceList.length; i--) {
					if (distanceList[i] == xPossiblity1 || distanceList [i] == xPossiblity2) {
							if (currentObject.goX(currentObject.getX() + distanceList[i])) {
								broken = true;
								break;
							}
						} else {
							if (currentObject.goY(currentObject.getY() + distanceList[i])) {
								broken = true;
								break;
							}
						}
					}
				if (!broken) {
					if (currentObject.getClass().getPackage().getName().equals("enemys")) {
						Enemy currentEnmey = (Enemy) currentObject;
						currentEnmey.suffocate();
						currentEnmey.blackList();
					} else {
						Jeffrey.getActiveJeffrey().damage(10);
						CheckpointSystem.loadNewestCheckpoint();
					}
					}
				}
			}
		}
		public void reverseCollision () {
			for (int j = 0; j<affectedTiles.size(); j++) {
				Room.getMapObjects().get(Room.toPackedLong((int)affectedTiles.get(j).x/16,(int)affectedTiles.get(j).y/16)).remove(this);
				affectedTiles.remove(j);
			}
		}
		@Override
		public boolean isColliding (GameObject withWhat) {
			if (this.doesColide(withWhat)) {
				boolean workint = super.isColliding(withWhat);
				if (workint) {
					this.onCollision(withWhat);
				}
					return workint;
			} else {
				return false;
			}
		}
		@Override
		public void setX(double val) {
			this.isCollidingChildren("GameObject");
			ArrayList <GameObject> oldCollisions =  this.getCollisionInfo().getCollidingObjects();
			super.setX(val);
			if (this.isCollidingChildren("GameObject")) {
				ArrayList<GameObject> working = this.getCollisionInfo().getCollidingObjects();
				working.removeAll(oldCollisions);
				for (int i = 0; i < working.size(); i++) {
						if (working.get(i).isPushable()) {
							if (val - this.xprevious> 0) {
								working.get(i).setX(this.hitbox().x + this.hitbox().width - working.get(i).getHitboxXOffset());
							} else {
								working.get(i).setX(this.hitbox().x - (working.get(i).hitbox().width + working.get(i).getHitboxXOffset()));
							}	
						}
					}
				}
			}
		@Override
		public void setY(double val) {
			this.isCollidingChildren("GameObject");
			ArrayList <GameObject> oldCollisions =  this.getCollisionInfo().getCollidingObjects();
			super.setY(val);
			if (this.isCollidingChildren("GameObject")) {
				ArrayList<GameObject> working = this.getCollisionInfo().getCollidingObjects();
				working.removeAll(oldCollisions);
				for (int i = 0; i < working.size(); i++) {
						if (working.get(i).isPushable()) {
							if (val - this.yprevious> 0) {
								working.get(i).setY(this.hitbox().y + this.hitbox().height - working.get(i).getHitboxYOffset());
							} else {
								working.get(i).setY(this.hitbox().y - (working.get(i).hitbox().height + working.get(i).getHitboxYOffset()));
							}	
						}
					}
				}
			}
		@Override
		public boolean goX(double val) {
			this.isCollidingChildren("GameObject");
			ArrayList <GameObject> oldCollisions =  this.getCollisionInfo().getCollidingObjects();
			boolean answer = super.goX(val);
			if (answer) {
				if (this.isCollidingChildren("GameObject")) {
					ArrayList<GameObject> working = this.getCollisionInfo().getCollidingObjects();
					working.removeAll(oldCollisions);
					for (int i = 0; i < working.size(); i++) {
							if (working.get(i).isPushable()) {
								if (val - this.xprevious> 0) {
									working.get(i).goX(this.hitbox().x + this.hitbox().width - working.get(i).getHitboxXOffset());
								} else {
									working.get(i).goX(this.hitbox().x - (working.get(i).hitbox().width + working.get(i).getHitboxXOffset()));
								}	
							}
						}
					}
			}
			return answer;
		}
		@Override
		public boolean goY(double val) {
			this.isCollidingChildren("GameObject");
			ArrayList <GameObject> oldCollisions =  this.getCollisionInfo().getCollidingObjects();
			boolean answer = super.goY(val);
			if (answer) {
				if (this.isCollidingChildren("GameObject")) {
					ArrayList<GameObject> working = this.getCollisionInfo().getCollidingObjects();
					working.removeAll(oldCollisions);
					for (int i = 0; i < working.size(); i++) {
							if (working.get(i).isPushable()) {
								if (val - this.yprevious> 0) {
									working.get(i).goY(this.hitbox().y + this.hitbox().height - working.get(i).getHitboxYOffset());
								} else {
									working.get(i).goY(this.hitbox().y - (working.get(i).hitbox().height + working.get(i).getHitboxYOffset()));
								}	
							}
						}
					}
		}
			return answer;
		}
		public boolean isCollidingBEEG (GameObject withWhat) {
			if (this.doesColide(withWhat)) {
				withWhat.setHitboxAttributes((int)withWhat.getHitboxXOffset() - 2, (int)withWhat.getHitboxYOffset() -2 , withWhat.hitbox().width + 4, withWhat.hitbox().height + 4);
				boolean workint = super.isColliding(withWhat);
				withWhat.setHitboxAttributes((int)withWhat.getHitboxXOffset() + 2, (int)withWhat.getHitboxYOffset() +2 , withWhat.hitbox().width - 4, withWhat.hitbox().height - 4);
				if (workint) {
					this.onCollision(withWhat);
				}
				return workint;
			} else {
				return false;
			}
		}
		@Override
		public void forget () {
			super.forget();
			for (int i = 0; i < affectedTiles.size(); i++) {
				Room.getMapObjects().remove(Room.toPackedLong((int)affectedTiles.get(i).x/16,(int)affectedTiles.get(i).y/16));
			}
		}
		public void onCollision (GameObject o) {
			
		}
		public boolean doesColide (GameObject o) {
			return true;
		}
		public void suffocateObjects (boolean suffocate) {
			this.suffocateObjects = suffocate;
		}
	}
