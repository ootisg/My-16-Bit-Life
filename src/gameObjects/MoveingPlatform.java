package gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class MoveingPlatform extends MapObject {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	Stack <Point> pointsToMoveTo = new Stack <Point> ();
	private Stack <Point> path = new Stack <Point> ();
	private double slope =0;
	private boolean firstTime = true;
	private boolean inzialized = false;
	private boolean reCheck = false;
	Point nextPoint = new Point();
	int originalX;
	int originalY;
	public MoveingPlatform () {
		this.setHitboxAttributes(0, 0, 20, 16);
	}
	public void frameEvent () {
		super.frameEvent();
		if (!inzialized) {
			if (firstTime) {
				try {
					this.setSprite(new Sprite ("resources/sprites/" +this.getVariantAttribute("spritePath")));
					this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
				} catch (NullPointerException e) {
					this.setSprite(new Sprite ("resources/sprites/default_platform.png"));
				}
				firstTime = false;
				originalX = (int) this.getX();
				originalY = (int) this.getY();
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
		try {
		this.followPresetPath(pointsToMoveTo, 1);
		}
		catch (NumberFormatException e ) {
			this.followPresetPath(pointsToMoveTo, 1);
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
		if (!Double.isInfinite(slope)) {
		//System.out.println(slopeMagnatue);
		if (slopeMagnatue != 0) {
		if (nextPoint.getX() < this.getX()){
		if (!this.goXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
			reCheck = true;
			this.goX(this.getX()- ((1.0/slopeMagnatue) * speed));
			this.goY(this.getY()+(slope/slopeMagnatue) * speed);
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
				j.goX(j.getX()- ((1.0/slopeMagnatue) * speed));	
						j.goY(this.getY() - 31);
				j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
		}  else {
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
			j.goX(j.getX()- ((1.0/slopeMagnatue) * speed));
					j.goY(this.getY() - 31);
			j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
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
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
				j.goX(j.getX()+ ((1.0/slopeMagnatue) * speed));	
						j.goY(this.getY() - 31);
				j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
		}  else {
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
			j.goX(j.getX()+ ((1.0/slopeMagnatue) * speed));
					j.goY(this.getY() - 31);
			j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
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
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
				j.goX(j.getX()- 1 * speed);
						j.goY(this.getY() - 31);
				j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
		}  else {
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
			j.goX(j.getX()- (1 * speed));
					j.goY(this.getY() - 31);
			j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
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
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
				j.goX(j.getX()+ 1 * speed);
						j.goY(this.getY() - 31);
				j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
		} else {
			j.setY(j.getY() + 4);
			if (this.isColliding(j)) {
				j.setY(j.getY() - 4);
			j.goX(j.getX()+ (1 * speed));
					j.goY(this.getY() - 31);
			j.setY(j.getY() + 4);
			}
			j.setY(j.getY() - 4);
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
				j.setY(j.getY() + 4);
				if (this.isColliding(j)) {
					j.setY(j.getY() - 4);
				j.setY(this.getY() - 31);
				j.setY(j.getY() + 4);
				}
				j.setY(j.getY() - 4);
			} else {
				this.goY(this.getY()- speed);
				j.setY(j.getY() + 4);
				if (this.isColliding(j)) {
					j.setY(j.getY() - 4);
						j.goY(this.getY() - 31);
				j.setY(j.getY() + 4);
				}
				j.setY(j.getY() - 4);
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
}
