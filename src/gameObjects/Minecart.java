package gameObjects;

import java.util.ArrayList;
import java.util.Arrays;

import main.GameObject;
import main.XposComparator;
import resources.Sprite;

public class Minecart extends EnterableObject{
	private double yxplusb = 0;
	private double trackPosition = 0;
	private RailSegment currentRailSegment;
	private boolean onRails = false;
	private Point currentMidpoint = new Point (0,0);
	boolean wDown = false;
	boolean dDown = false;
	boolean direction = false;
	public Minecart () {
		this.setHitboxAttributes(0, 0, 32, 16);
		this.setSprite(new Sprite ("resources/sprites/Minecart.png"));
	}
	
	public double getTrackPositionPls () {
		return trackPosition;
	}
	
	public double getYxplusbPls () {
		return Math.abs(yxplusb);
	}
	
	public RailSegment getCurrentRailSegmentPls () {
		/*double r = Math.random () * 15;
		if (r > 14) {
			return "no";
		}*/
		return currentRailSegment;
	}
	
	public void setTrackPosition (double pos) {
		this.trackPosition = pos;
	}
	public void takeOffRails() {
		onRails = false;
	}
	public void setCurrentRailPosition (RailSegment r) {
		currentRailSegment =  r;
	}
	
	public void setPosition (Point p) {
		this.setX(p.x - (this.getSprite().getFrame(this.getAnimationHandler().getFrame()).getWidth()/2.0));
		this.setY(p.y - (this.getSprite().getFrame(this.getAnimationHandler().getFrame()).getHeight()/2.0));
		currentMidpoint = p;
	}
	public Point getMidpoint () {
		return currentMidpoint;
	}
	@Override
	public boolean isCollidingChildren (String parentName) {
		this.setY(this.getY() + 10);
		boolean truth = super.isColliding(parentName);
		this.setY(this.getY() - 10);
		return truth;
	}
	private void retrack () {
		Rail rail = new Rail ();
		Rail.railFinder find = rail.new railFinder(currentRailSegment.getDiffrentKindOfDirection(), direction, 10);
		find.trackRail(currentRailSegment);
	}
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (onRails) {
			currentRailSegment.moveCart(this);
			if ((direction && yxplusb > 0) || (!direction && yxplusb < 0) ) {
				direction = !direction;
				Rail rail = new Rail ();
				Rail.railFinder find = rail.new railFinder(rail.getDirection(), direction, 10);
				currentRailSegment = find.getFirstRail(this.getX(),this.getY());
				find.trackRail(currentRailSegment);
			}
			if ((!wDown && keyDown('W')) || (wDown && !keyDown('W'))) {
				wDown = !wDown;
				this.retrack();
			}
			if ((!dDown && keyDown('D')) || (dDown && !keyDown('D'))) {
				dDown = !dDown;
				this.retrack();
			}
			RailSegment working = currentRailSegment.getNext();
			if (working == null) {
				this.retrack();
			} else if (working.getNext() == null) {
				
				this.retrack();
			}
		} else {
			this.goX(this.getX() + yxplusb);
			this.goY(this.getY() + 4);
			if (this.isCollidingChildren("Rail")) {
				this.onRails = true;
				Rail rail = (Rail) this.getCollisionInfo().getCollidingObjects().get(0);
				switch (rail.getDirection()) {
				case 0:
					this.setSprite(new Sprite ("resources/sprites/Minecart.png"));
					break;
				case 1:
					this.setSprite(new Sprite ("resources/sprites/MinecartUpLeft.png"));
					break;
				case 2:
					this.setSprite(new Sprite ("resources/sprites/MinecartUpRight.png"));
					break;
				}
				currentMidpoint  = new Point (this.getX() + (this.getSprite().getFrame(this.getAnimationHandler().getFrame()).getWidth()/2.0), this.getY() + (this.getSprite().getFrame(this.getAnimationHandler().getFrame()).getHeight()/2.0) + 10);
				Rail.railFinder find = rail.new railFinder(rail.getDirection(), yxplusb < 0, 10);
				currentRailSegment = find.getFirstRail(rail.getX(),rail.getY());
				find.trackRail(currentRailSegment);
			}
		}
		
		if (this.inside) {
			if (keyDown('D')) {
				yxplusb = yxplusb + .5;
			}
			if (keyDown('A')) {
				yxplusb = yxplusb -.5;
			}
		}
	}
}
