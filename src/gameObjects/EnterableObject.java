package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;

public class EnterableObject extends BreakableObject {
	protected boolean inside = false;
	protected boolean isBroken = false;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	public EnterableObject () {
		
	}
	@Override 
	public void frameEvent () {
		if (j.isColliding(this) && !inside && !this.isBroken ) {
			this.onEntry();
			
		}
		if (inside) {
			double x = this.getX ();
			double y = this.getY ();
			int viewX = Room.getViewX ();
			int viewY = Room.getViewY ();
			if (y - viewY >= 320 && y - 320 < Room.getHeight () * 16 - 480) {
				viewY = (int) y - 320;
				Room.setView (Room.getViewX (), viewY);
			}
			if (y - viewY <= 160 && y - 160 > 0) {
				viewY = (int) y - 160;
				Room.setView (Room.getViewX (), viewY);
			}
			if (x - viewX >= 427 && x - 427 < Room.getWidth () * 16 - 640) {
				viewX = (int) x - 427;
				Room.setView (viewX, Room.getViewY ());
			}
			if (x - viewX <= 213 && x - 213 > 0) {
				viewX = (int) x - 213;
				Room.setView (viewX, Room.getViewY ());
			}
			j.setX(this.getX());
			j.setY(this.getY());
			try {
				if (this.isCollidingChildren("Enemy") || this.isCollidingChildren("Projectile")) {
					this.onBreak();
				}
			} catch (NullPointerException e) {
			
			}
		}
		
	}
	public void onEntry () {
		j.getAnimationHandler().hide();
		j.getWeapon().blackList();
		j.getWeapon().hide();
		j.blackList();
		inside = true;
	}
	public void onBreak () {
		isBroken = true;
	}
	public void exit () {
		inside = false;
		j.getWeapon().whiteList();
		j.getWeapon().show();
		j.getAnimationHandler().show();
		j.whiteList();
		}
	}
