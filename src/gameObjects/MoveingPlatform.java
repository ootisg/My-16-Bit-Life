package gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

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
	private boolean active;
	public MoveingPlatform () {
		this.setHitboxAttributes(0, 0, 20, 16);
	}
	public void frameEvent () {
		super.frameEvent();
		if (!inzialized) {
			if (firstTime) {
					if (this.getVariantAttribute("spritePath") != null) {
						if (!this.getVariantAttribute("spritePath").equals("nv")) {
							this.setSprite(new Sprite ("resources/sprites/" +this.getVariantAttribute("spritePath")));
							this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
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
		if (!(path.isEmpty() && this.isColliding(nextPoint)) ) {
		if (this.isColliding(nextPoint)) {
			Point currentPosition = new Point (this.getX(),this.getY());
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		
		
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
			if (!this.goXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.goX(this.getX()- ((1.0/slopeMagnatue) * speed));
				this.goY(this.getY()+(slope/slopeMagnatue) * speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()- ((1.0/slopeMagnatue) * speed));	
					collidingObjects.get(i).goY(this.getY());
				}
			}  else {
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()- ((1.0/slopeMagnatue) * speed));
					collidingObjects.get(i).goY(this.getY() );
				}
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.goXandY(this.getX()+ ((1.0/slopeMagnatue) * speed), this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.goX(this.getX()+ ((1.0/slopeMagnatue) * speed));
				this.goY(this.getY()+(slope/slopeMagnatue) * speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()+ ((1.0/slopeMagnatue) * speed));	
					collidingObjects.get(i).goY(this.getY() );
				}
			}  else {
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()+ ((1.0/slopeMagnatue) * speed));
					collidingObjects.get(i).goY(this.getY() );
				}
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			}
			} else {
			if (nextPoint.getX() < this.getX()){
			if (!this.goXandY(this.getX()- (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.goX(this.getX()- 1 * speed);
				this.goY(this.getY()+slope * speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()- 1 * speed);
					collidingObjects.get(i).goY(this.getY() );
				}
			}  else {
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()- (1 * speed));
					collidingObjects.get(i).goY(this.getY() );
				}
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.goXandY(this.getX()+ (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.goX(this.getX()+ 1 * speed);
				this.goY(this.getY()+slope * speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()+ 1 * speed);
					collidingObjects.get(i).goY(this.getY() );
				}
			} else {
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goX(collidingObjects.get(i).getX()+ (1 * speed));
					collidingObjects.get(i).goY(this.getY() );
				}
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
				this.setY(this.getY()+ speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).setY(this.getY() );
				}
			} else {
				this.goY(this.getY()- speed);
				for (int i = 0; i < collidingObjects.size(); i++) {
					collidingObjects.get(i).goY(this.getY() );
				}
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
}
