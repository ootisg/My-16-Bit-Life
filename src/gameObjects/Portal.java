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
	
	private Jeffrey j = (Jeffrey)ObjectHandler.getObjectsByName("Jeffrey").get(0);
	
	double oldVy;

	public Portal () {
	}
	public int getDirection () {
		return direction;
	}
	@Override 
	public void frameEvent () {
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
		if (endX > Room.getWidth() * Room.TILE_WIDTH) {
			endX = Room.getWidth() * Room.TILE_WIDTH;
			
		}
		if (endY > Room.getHeight() * Room.TILE_HEIGHT) {
			endY = Room.getHeight() * Room.TILE_HEIGHT;
			
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
				j.setScroll(true);
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
		if (j.vy != 0 || oldVy == 0 || set) {
			oldVy = j.vy;
		} 
	}
	public void doPortalAction(Portal entrence) {
		j.setX(this.getX());
		j.setY(this.getY());
		endX = (int) this.getX();
		endY = (int) this.getY();
		startX = Room.getViewX();
		startY = Room.getViewY();
		takenCareOfIt = true;
		j.setScroll(false);
		switch (direction){
		case 1:
			j.setY(this.getY() - 15);
			switch (entrence.getDirection()) {
				case 1: 
					j.vy = -oldVy;
					break;
				case 2:
					j.vy = oldVy - 2;
					break;
				case 3:
					j.vy = j.vx;
					j.vx = 0;
					break;
				case 4:
					j.vy = -j.vx;
					j.vx = 0;
					break;
				}
			break;
		case 2:
			switch (entrence.getDirection()) {
				case 1: 
					j.vy = oldVy + 2;
					break;
				case 2:
					j.vy = -oldVy;
					break;
				case 3:
					j.vy = -j.vx;
					j.vx = 0;
					break;
				case 4:
					j.vy = j.vx;
					j.vx = 0;
					break;
				}
			break;
		case 3:
			
			switch (entrence.getDirection()) {
			case 1: 
				j.vx = oldVy;
				j.vy = 0;
				break;
			case 2:
				j.vx = -oldVy;
				j.vy = 0;
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
				j.vy = 0;
				break;
			case 2:
				j.vx = oldVy;
				j.vy = 0;
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
