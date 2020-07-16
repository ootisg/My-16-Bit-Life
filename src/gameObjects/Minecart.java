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
	public Minecart () {
		
	}
	
	public double getTrackPositionPls () {
		return trackPosition;
	}
	
	public double getYxplusbPls () {
		return yxplusb;
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
	}
	
	@Override
	public void frameEvent () {
		super.frameEvent();
		if (this.inside) {
			if (keyDown('D')) {
				yxplusb = yxplusb + 1;
			}
			if (keyDown('A')) {
				yxplusb = yxplusb -1;
			}
			if (!keyDown('A') && !keyDown('D')) {
				yxplusb = 0;
			}
		}
	}
}
