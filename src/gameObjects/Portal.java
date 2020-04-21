package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Portal extends GameObject {
	
	private int direction; //corrisponds to the direction of the portal (1 for up) (2 for down) (3 for left) (4 for right)(5 for down again (not realy))
	
	private boolean wait = false;
	private int timer = 0;
	
	private boolean intizalize = true;
	
	private Jeffrey j = (Jeffrey)ObjectHandler.getObjectsByName("Jeffrey").get(0);
	
	double oldVy;
	
	public Portal () {
		
	}
	public int getDirection () {
		return direction;
	}
	@Override 
	public void frameEvent () {
		//System.out.println(j.vy);
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
				this.setHitboxAttributes(0, 9, 32, 6);
				break;
			case "left":
				direction = 3;
				this.setSprite(new Sprite("resources/sprites/config/PortalRight.txt"));
				this.desyncSpriteX(-10);
				this.setHitboxAttributes(9, 0, 6, 32);
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
		oldVy = j.vy;
	}
	public void doPortalAction(Portal entrence) {
		j.setX(this.getX());
		j.setY(this.getY());
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
