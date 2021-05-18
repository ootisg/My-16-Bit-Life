package resources;


import java.awt.Point;
import java.awt.Rectangle;

import main.GameObject;

public class ColidableVector {
	public Point startPoint;
	public Point endPoint;
	double slope = 0;
	
	public ColidableVector () {
		
	}
	public ColidableVector (Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		slope = ((double)(endPoint.y-startPoint.y))/((double)(endPoint.x-startPoint.x));
	}
	
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		if (this.endPoint != null) {
			slope = ((double)(endPoint.y-startPoint.y))/((double)(endPoint.x-startPoint.x));
		}
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		if (this.startPoint != null) {
			slope = ((double)(endPoint.y-startPoint.y))/((double)(endPoint.x-startPoint.x));
		}
	}

	
	public boolean isCollidng (GameObject obj) {
		Rectangle hitbox = obj.hitbox();
		Rectangle bigBox;
		if (startPoint.x < endPoint.x) {
			if (startPoint.y < endPoint.y) {
				bigBox = new Rectangle (startPoint.x,startPoint.y,endPoint.x  - startPoint.x,endPoint.y - startPoint.y);
			} else {
				bigBox = new Rectangle (startPoint.x, endPoint.y, endPoint.x - startPoint.x, startPoint.y - endPoint.y);
			}
		} else {
			if (startPoint.y < endPoint.y) {
				bigBox = new Rectangle (endPoint.x,startPoint.y,startPoint.x - endPoint.x,endPoint.y - startPoint.y);
			} else {
				bigBox = new Rectangle (endPoint.x, endPoint.y, startPoint.x - endPoint.x, startPoint.y - endPoint.y);
			}
		}
		
		
		if (bigBox.contains(hitbox)) {
			Point pt1 = new Point (hitbox.x,hitbox.y);
			Point pt2 = new Point (hitbox.x+hitbox.width,hitbox.y);
			Point pt3 = new Point (hitbox.x,hitbox.y + hitbox.height);
			Point pt4 = new Point (hitbox.x + hitbox.width, hitbox.y + hitbox.height);
			
			boolean aboveOrBelow; // below = true above = false;
			
			Point linePoint1 = new Point (pt1.x, (int)(startPoint.y + ((pt1.x - startPoint.x) * slope)));
			
			aboveOrBelow = linePoint1.y > pt1.y;
			
			Point linePoint2 = new Point (pt2.x, (int)(startPoint.y + ((pt2.x - startPoint.x) * slope)));
			
			if ((aboveOrBelow && linePoint2.y < pt2.y) || (linePoint2.y > pt2.y && !aboveOrBelow)) {
				return true;
			}
			
			Point linePoint3 = new Point (pt3.x, (int)(startPoint.y + ((pt3.x - startPoint.x) * slope)));
			
			if ((aboveOrBelow && linePoint3.y < pt3.y) || (linePoint3.y > pt3.y && !aboveOrBelow)) {
				return true;
			}
			
			Point linePoint4 = new Point (pt4.x, (int)(startPoint.y + ((pt4.x - startPoint.x) * slope)));
			
			if ((aboveOrBelow && linePoint4.y < pt4.y) || (linePoint4.y > pt4.y && !aboveOrBelow)) {
				return true;
			}
			
		} 
		return false;
	}
	public double getSlope() {
		return slope;
	}
}
