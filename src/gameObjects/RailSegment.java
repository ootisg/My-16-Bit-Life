package gameObjects;

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
	
	public Point getStart () {
		return start;
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
	
	public Point traverse (double dist) {
		double slope = (end.y - start.y)/(end.x - start.x);
		switch (this.getDirection()){
		case STRATE_RAIL_DIRCTION:
			if (start.x < end.x) {
				return new Point (start.x + dist, start.y);
			} else {
				return new Point (start.x - dist,start.y);
			}
		case UPLEFT_RIGHT_DIRECTION:
			return new Point (start.x - dist, start.y - (dist*slope));
		case UPRIGHT_RAIL_DIRECTION:
			return new Point (start.x + dist, start.y - (dist*slope));
		case DOWNLEFT_RAIL_DIRECTION:
			return new Point (start.x - dist, start.y + (dist*slope));
		case DOWNRIGHT_RAIL_DIRECTION:
			return new Point (start.x + dist, start.y + (dist*slope));
			
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
			next.moveCart (minecart, dist - length);
			} else {
				minecart.takeOffRails();
				minecart.setPosition (end);
			}
		} else {
			Point newPt = traverse (dist);
			newPt.y = newPt.y + getMinecartHeightOffset ();
			minecart.setPosition (newPt);
		}
	}
	
	private double distance (Point a, Point b) {
		return (a.y - b.y) * (a.y - b.y) + (a.x - b.x) * (a.x - b.x);
	}
	private int getDirection () {
		if (end.y == start.y) {
			return STRATE_RAIL_DIRCTION;
		} else if ((end.y > start.y && end.x>start.x)){
			return DOWNRIGHT_RAIL_DIRECTION;
		} else if (end.y> start.y) {
			return DOWNRIGHT_RAIL_DIRECTION;
		} else if (end.x>start.x){
			return UPRIGHT_RAIL_DIRECTION;
		} else {
			return UPLEFT_RIGHT_DIRECTION;
		}
	}
}
