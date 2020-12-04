package gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Portal extends GameObject {
	
	private int direction; //corrisponds to the direction of the portal (1 for up) (2 for down) (3 for left) (4 for right)(5 for down again (not realy))
	
	private boolean wait = false;
	
	private int timer = 0;
	private boolean set = false;
	
	private boolean intizalize = true;
	
	private boolean takenCareOfIt = false; //determines is this portal is TAKIN CARE of the scrolling
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	
	double oldVy;

	public Portal () {
	}
	public int getDirection () {
		return direction;
	}
	@Override 
	public void frameEvent () {
		Jeffrey j = Jeffrey.getActiveJeffrey();
		if (takenCareOfIt) {
		double x = j.getX ();
		double y = j.getY ();
		int viewX = Room.getViewX ();
		int viewY = Room.getViewY ();
		viewY = (int) y - 320;
		endY = viewY;	
		viewX = (int) x - 427;
		endX = viewX;
		if (endX < 0) {
			endX = 0;
		}
		if (endY < 0) {
			endY = 0;
		}
		if (endX + RenderLoop.window.getResolution()[0] > Room.getWidth() * Room.TILE_WIDTH) {
			endX = Room.getWidth() * Room.TILE_WIDTH - RenderLoop.window.getResolution()[0];
			
		}
		if (endY + RenderLoop.window.getResolution()[1] > Room.getHeight() * Room.TILE_HEIGHT) {
			endY = (Room.getHeight() * Room.TILE_HEIGHT) - RenderLoop.window.getResolution()[1];
			
		}
		if (startX != endX) {
			if (startX > endX) {
				if (((startX - endX)/8) > 15) {
					startX = startX - ((startX - endX)/8);
				} else {
					startX = startX - 15;
				}
				if (startX <= endX) {
				
					startX = endX;
				}
			} else {
				if (((endX - startX)/8) > 15) {
					startX = startX + ((endX - startX)/8);
				} else {
					startX = startX + 15;
				}
				if (startX >= endX) {
					startX = endX;
				}
			}
			Room.setView(startX, Room.getViewY());
		} else {
			if (startY == endY) {
				takenCareOfIt = false;
				Jeffrey.setScroll(true);
			}
		}
		if (startY != endY) {
			if (startY > endY) {
				if (((startY - endY)/8) > 15) {
					startY = startY - ((startY - endY)/8);
				} else {
					startY = startY - 15;
				}
				if (startY <= endY) {
					startY = endY;
				}
			} else {
				if (((startY - endY)/8) > 15) {
					startY = startY+ ((endY - startY)/8);
				} else {
					startY = startY + 15;
				}
				if (startY >= endY) {
					startY = endY;
				}
			}
			Room.setView(Room.getViewX(), startY);
		}
		}
		j.setY(j.getY() + 1);
		if (Room.isColliding(j)) {
			timer = timer + 1;
		} else {
			set = false;
			timer = 0;
		}
		j.setY(j.getY() - 1);
		if (timer > 10) {
			set = true;
		}
		if (intizalize) {
			switch (this.getVariantAttribute("direction")){
			case "up":
				direction = 1;
				this.setSprite(new Sprite("resources/sprites/config/PortalUp.txt"));
				this.setHitboxAttributes(0, 9, 32, 6);
				this.desyncSpriteY( 10);
				break;
			case "down":
				direction = 2;
				this.setSprite(new Sprite("resources/sprites/config/PortalDown.txt"));
				this.desyncSpriteY(- 10);
				this.setHitboxAttributes(0, 0, 32, 6);
				break;
			case "left":
				direction = 3;
				this.setSprite(new Sprite("resources/sprites/config/PortalRight.txt"));
				this.desyncSpriteX(-10);
				this.setHitboxAttributes(0, 0, 6, 32);
				break;
			case "right":
				direction = 4;
				this.setSprite(new Sprite("resources/sprites/config/PortalLeft.txt"));
				this.desyncSpriteX( 10);
				this.setHitboxAttributes(9, 0, 6, 32);
				break;
			}
			this.getAnimationHandler().setFrameTime(130);
			intizalize = false;
		}
		if (!wait) {
			if (this.isColliding(j)) {
				for (int i = 0; i < ObjectHandler.getObjectsByName("Portal").size(); i++) {
					Portal currentPortal = (Portal)ObjectHandler.getObjectsByName("Portal").get(i);
					if (currentPortal.getVariantAttribute("partner").equals(this.getVariantAttribute("partner")) && !currentPortal.equals(this)) {
						currentPortal.doPortalAction(this);
					}
				}
			}
		} else {
			if (!this.isColliding(j)) {
				wait = false;
			}
		}
		if (j.getVy() != 0 || oldVy == 0 || set) {
			oldVy = j.getVy();
		} 
	}
	public void doPortalAction(Portal entrence) {
		Jeffrey j = Jeffrey.getActiveJeffrey();
		j.setX(this.getX());
		j.setY(this.getY());
		endX = (int) this.getX();
		endY = (int) this.getY();
		startX = Room.getViewX();
		startY = Room.getViewY();
		takenCareOfIt = true;
		Jeffrey.setScroll(false);
		switch (direction){
		case 1:
			j.setY(this.getY() - 15);
			switch (entrence.getDirection()) {
				case 1: 
					j.setVy(-oldVy);
					break;
				case 2:
					j.setVy(oldVy - 2);
					break;
				case 3:
					j.setVy(j.vx);
					j.vx = 0;
					break;
				case 4:
					j.setVy(-j.vx);
					j.vx = 0;
					break;
				}
			break;
		case 2:
			switch (entrence.getDirection()) {
				case 1: 
					j.setVy(oldVy + 2);
					break;
				case 2:
					j.setVy(-oldVy);
					break;
				case 3:
					j.setVy(-j.vx);
					j.vx = 0;
					break;
				case 4:
					j.setVy(j.vx);
					j.vx = 0;
					break;
				}
			break;
		case 3:
			
			switch (entrence.getDirection()) {
			case 1: 
				j.vx = oldVy;
				j.setVy(0);
				break;
			case 2:
				j.vx = -oldVy;
				j.setVy(0);
				break;
			case 3:
				j.vx = -j.vx;
				break;
			}
		break;
		case 4:
			switch (entrence.getDirection()) {
			
			case 1: 
				j.vx = -oldVy;
				j.setVy(0);
				break;
			case 2:
				j.vx = oldVy;
				j.setVy(0);
				break;
			case 4:
				j.vx = -j.vx;
				break;
			}
		}
		wait = true;
		entrence.wait = true;
	}
}
