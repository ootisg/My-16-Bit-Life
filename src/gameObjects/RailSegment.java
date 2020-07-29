package gameObjects;

import resources.Sprite;

public class RailSegment {
	
	private Point start;
	private Point end;
	private double startPos;
	private double endPos;
	private double length;
	
	private RailSegment prev;
	private RailSegment next;
	
	private static final int STRATE_RAIL_DIRCTION = 0;
	private static final int UPRIGHT_RAIL_DIRECTION = 1;
	private static final int UPLEFT_RIGHT_DIRECTION  = 2;
	private static final int DOWNRIGHT_RAIL_DIRECTION = 3;
	private static final int DOWNLEFT_RAIL_DIRECTION = 4;
	
	public RailSegment (RailSegment previous, Point end) {
		this.prev = previous;
		prev.next = this;
		start = prev.getEnd ();
		this.end = end;
		this.startPos = prev.getEndPos ();
		this.length = distance (start, end);
		this.endPos = this.startPos + this.length;

	}
	
	public RailSegment (Point start, Point end) {
		this.start = start;
		this.end = end;
		this.startPos = 0;
		this.length = distance (start, end);
		this.endPos = this.length;
	}
	
	public RailSegment getPrevious () {
		return prev;
	}
	
	public RailSegment getNext () {
		return next;
	}
	public void setStart (Point start) {
		this.start = start;
	}
	public Point getStart () {
		return start;
	}
	public void setEnd (Point end) {
		this.end = end;
	}
	public Point getEnd () {
		return end;
	}
	
	public double getStartPos () {
		return startPos;
	}
	
	public double getEndPos () {
		return endPos;
	}
	
	public boolean hasPrevious () {
		return prev != null;
	}
	
	public boolean hasNext () {
		return next != null;
	}
	public void setNext (RailSegment next) {
		this.next = next;
	}
	
	public Point traverse (double dist,Point startPoint) {
		double slope = (end.y - start.y)/(end.x - start.x);
		switch (this.getDirection()){
		case STRATE_RAIL_DIRCTION:
			if (start.x < end.x) {
				return new Point (startPoint.x + dist, startPoint.y);
			} else {
				return new Point (startPoint.x - dist,startPoint.y);
			}
		case UPLEFT_RIGHT_DIRECTION:
			return new Point (startPoint.x - dist, startPoint.y - (dist*slope));
		case UPRIGHT_RAIL_DIRECTION:
			return new Point (startPoint.x + dist, startPoint.y - (dist*slope));
		case DOWNLEFT_RAIL_DIRECTION:
			return new Point (startPoint.x - dist, startPoint.y + (dist*slope));
		case DOWNRIGHT_RAIL_DIRECTION:
			return new Point (startPoint.x + dist, startPoint.y + (dist*slope));
			
		}
		return null;
	}
	
	public int getMinecartHeightOffset () {
		return 0; //TODO return the height offset for this track
	}
	
	public void moveCart (Minecart minecart) {
		moveCart (minecart, minecart.getYxplusbPls ());
	}
	
	public void moveCart (Minecart minecart, double dist) {
		//THIS ONE IS RECURSIVE BOIIIII
		double minePos = minecart.getTrackPositionPls () - this.getStartPos();
		if (minePos + dist > length) {
			if (next != null) {
			minecart.setCurrentRailPosition(next);
			switch (next.getDiffrentKindOfDirection()) {
			case STRATE_RAIL_DIRCTION:
				minecart.setSprite(new Sprite ("resources/sprites/Minecart.png"));
				break;
			case UPLEFT_RIGHT_DIRECTION:
				minecart.setSprite(new Sprite ("resources/sprites/MinecartUpLeft.png"));
				break;
			case UPRIGHT_RAIL_DIRECTION:
				minecart.setSprite(new Sprite ("resources/sprites/MinecartUpRight.png"));
				break;
			}
			next.moveCart (minecart, dist - (length - minePos));
			} else {
				minecart.takeOffRails();
				minecart.setTrackPosition(endPos);
			}
		} else {
			Point newPt = traverse (dist,minecart.getMidpoint());
			newPt.y = newPt.y + getMinecartHeightOffset ();
			minecart.setTrackPosition(minecart.getTrackPositionPls() + dist);
			minecart.setPosition (newPt);
		}
	}
	
	private double distance (Point a, Point b) {
		return Math.sqrt((a.y - b.y) * (a.y - b.y) + (a.x - b.x) * (a.x - b.x));
	}
	public int getDiffrentKindOfDirection() {
		if (end.y == start.y) {
			return STRATE_RAIL_DIRCTION;
		} else if ( end.x<start.x){
			return UPLEFT_RIGHT_DIRECTION;
		} else  {
			return UPRIGHT_RAIL_DIRECTION;
		}
	}
	private int getDirection () {
		if (end.y == start.y) {
			return STRATE_RAIL_DIRCTION;
		} else if ((end.y > start.y && end.x>start.x)){
			return DOWNRIGHT_RAIL_DIRECTION;
		} else if (end.y> start.y) {
			return DOWNLEFT_RAIL_DIRECTION;
		} else if (end.x>start.x){
			return UPRIGHT_RAIL_DIRECTION;
		} else {
			return UPLEFT_RIGHT_DIRECTION;
		}
	}
}
