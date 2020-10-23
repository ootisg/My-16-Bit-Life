package mapObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import gameObjects.Point;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import switches.Activateable;

public class MoveingPlatform extends MapObject implements Activateable {
	Stack <Point> pointsToMoveTo = new Stack <Point> ();
	ArrayList<GameObject> objectsToCarry = new ArrayList<GameObject> ();
	private Stack <Point> path = new Stack <Point> ();
	private double slope =0;
	private boolean firstTime = true;
	private boolean inzialized = false;
	private boolean reCheck = false;
	Point nextPoint = new Point();
	int originalX;
	int originalY;
	int hitboxXoffset;
	int hitboxYoffset;
	int hitboxWidth;
	int hitboxHeight;
	private boolean active = true;
	public MoveingPlatform () {
		this.setGameLogicPriority(-1);
		this.setHitboxAttributes(0, 0, 20, 16);
		this.hitboxXoffset = 0;
		this.hitboxYoffset = 0;
		this.hitboxWidth = 20;
		this.hitboxHeight = 16;
	}
	public void frameEvent () {
		super.frameEvent();
		if (!inzialized) {
			if (firstTime) {
					if (this.getVariantAttribute("spritePath") != null) {
						if (!this.getVariantAttribute("spritePath").equals("nv")) {
							this.setSprite(new Sprite ("resources/sprites/" +this.getVariantAttribute("spritePath")));
							this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
							this.hitboxXoffset = 0;
							this.hitboxYoffset = 0;
							this.hitboxWidth =  this.getSprite().getFrame(0).getWidth();
							this.hitboxHeight = this.getSprite().getFrame(0).getHeight();
						} else {
							this.setSprite(new Sprite ("resources/sprites/default_platform.png"));	
						}
					} else {
						this.setSprite(new Sprite ("resources/sprites/default_platform.png"));
					}
				firstTime = false;
				originalX = (int) this.getX();
				originalY = (int) this.getY();
				if (this.getVariantAttribute("active") != null) {
					if (!(this.getVariantAttribute("active").equals("nv") || this.getVariantAttribute("active").equals("false"))) {
						active = true;
					} else {
						active = false;
					}
				}
			}
			inzialized = true;
			try {
				this.getVariantAttribute("wayPoint").charAt(0);
				ArrayList <Point> matchingPoints = new ArrayList <Point> ();
				Iterator <GameObject>iter = ObjectHandler.getObjectsByName("Point").iterator();
				while (iter.hasNext()) {
					Point currentPoint = (Point) iter.next();
					if (currentPoint.getVariantAttribute("wayPoint").equals(this.getVariantAttribute("wayPoint"))) {
					matchingPoints.add(currentPoint);	
					}
				}
				while (!matchingPoints.isEmpty()) {
					int highestNum = 0;
					int index = 0;
					for (int i = 0; i < matchingPoints.size(); i++) {
						if (Integer.parseInt(matchingPoints.get(i).getVariantAttribute("PointNumber")) > highestNum) {
							highestNum = Integer.parseInt(matchingPoints.get(i).getVariantAttribute("PointNumber"));
							index = i;
						}
					}
					pointsToMoveTo.push(matchingPoints.get(index));
					matchingPoints .remove(index);
				}
			} catch (NullPointerException e) {
				pointsToMoveTo.push(new Point (originalX , originalY + 100));
				pointsToMoveTo.push(new Point (originalX , originalY - 100));	
			}
		}
		if (this.active) {
			try {
			this.followPresetPath(pointsToMoveTo, 1);
			}
			catch (NumberFormatException e ) {
				this.followPresetPath(pointsToMoveTo, 1);
			}
		}
	}
	/**
	 * fallows a preset path from the map editor
	 * @param pathToTake the path the enemy takes
	 * @param speed how fast as fuck the enmey is
	 */
	public void followPresetPath (Stack <Point> pathToTake, double speed) {
		//System.out.println(path.isEmpty());
		if (path.isEmpty() && !pathToTake.isEmpty()) {
			Point currentPosition = new Point (this.getX(),this.getY());
			path = pathToTake; 
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		if (Room.isColliding(this)) {
			Point currentPosition = new Point (this.getX(),this.getY());
			slope = currentPosition.getSlope(nextPoint);
			reCheck = false;
	}
		this.setHitboxAttributes(this.hitboxXoffset, this.hitboxYoffset, 1, 1);
		if (!(path.isEmpty() && this.isColliding(nextPoint)) ) {
		if (this.isColliding(nextPoint)) {
			Point currentPosition = new Point (this.getX(),this.getY());
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		this.setHitboxAttributes(this.hitboxXoffset, this.hitboxYoffset, this.hitboxWidth,this.hitboxHeight);
		//System.out.println(slope);
		int slopeMagnatue; 
		if (slope > 0) {
		slopeMagnatue = (int) Math.floor(slope);
		} else {
		slopeMagnatue = (int) Math.round(slope);
		}
		if (slopeMagnatue < 0) {
			slopeMagnatue = slopeMagnatue * -1;
		}
		this.setY(this.getY() - 4);
		this.isCollidingChildren("GameObject");
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		this.setY(this.getY() + 4);
		collidingObjects.addAll(objectsToCarry);
		if (!Double.isInfinite(slope)) {
		//System.out.println(slopeMagnatue);
			
		
			if (slopeMagnatue != 0) {
			if (nextPoint.getX() < this.getX()){
			if (!this.checkXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.setX(-1*((1.0/slopeMagnatue) * speed),collidingObjects);
				this.setY((slope/slopeMagnatue) * speed,collidingObjects);
			}  else {
				this.setX(-1*((1.0/slopeMagnatue) * speed),collidingObjects);
				this.setY((slope/slopeMagnatue) * speed, collidingObjects);
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.checkXandY(this.getX()+ ((1.0/slopeMagnatue) * speed), this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.setX((1.0/slopeMagnatue) * speed,collidingObjects);
				this.setY((slope/slopeMagnatue) * speed,collidingObjects);
			}  else {
				this.setX((1.0/slopeMagnatue) * speed,collidingObjects);
				this.setY((slope/slopeMagnatue) * speed,collidingObjects);
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			}
			} else {
			if (nextPoint.getX() < this.getX()){
			if (!this.checkXandY(this.getX()- (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.setX(-1 * speed,collidingObjects);
				this.setY(slope * speed,collidingObjects);
			}  else {
				this.setX(-1 * speed,collidingObjects);
				this.setY(slope * speed,collidingObjects);
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.checkXandY(this.getX()+ (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.setX(1 * speed,collidingObjects);
				this.setY(slope * speed,collidingObjects);
			} else {
				this.setX(1 * speed,collidingObjects);
				this.setY(slope * speed,collidingObjects);
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			}
		}
		} else {
			if (slope > 0) {
				this.setY(speed,collidingObjects);
			} else {
				this.setY(speed,collidingObjects);
			}
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		} else {
			inzialized = false;
		}
	}
	public void setX(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setX(this.getX() + displacement);
		for (int i = 0; i < objectsToCarry.size(); i++) {
			objectsToCarry.get(i).goX(objectsToCarry.get(i).getX() + displacement);
		}
	}
	public void setY(double displacement,ArrayList <GameObject> objectsToCarry) {
		super.setY(this.getY() + displacement);
		for (int i = 0; i < objectsToCarry.size(); i++) {
			objectsToCarry.get(i).goY(objectsToCarry.get(i).getY() + displacement);
		}
	}
	@Override
	public void activate() {
		active = true;
	}
	@Override
	public void deactivate() {
		active = false;
	}
	@Override
	public boolean isActivated() {
		return active;
	}
	@Override
	public void pair() {
		
	}
	public void addCarryObject (GameObject obj) {
		objectsToCarry.add(obj);
	}
	public void removeCarryObject (GameObject obj) {
		objectsToCarry.remove(obj);
	}
	public ArrayList <GameObject> getCarryObjects (){
		return objectsToCarry;
	}
}
