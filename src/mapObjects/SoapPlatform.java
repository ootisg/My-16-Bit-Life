package mapObjects;

import java.util.ArrayList;

import main.GameObject;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class SoapPlatform extends MapObject {
	int speedX = 0; 
	double vy;
	boolean doneFor = false;
	public SoapPlatform () {
		this.setSprite(new Sprite ("resources/sprites/Soap_Tile_1.png"));
		this.setHitboxAttributes(0,0, 64, 32);
		this.adjustHitboxBorders();
	}
	@Override
	public void frameEvent ()
	{
	
		if (doneFor) {
			this.despawnAllCoolLike(20);
		} else {
			super.frameEvent();
			if (!this.goX(this.getX() + speedX)) {
				doneFor = true;
			}
			if (!this.isColliding("BubbleWater")) {
				if (vy < 15) {
					vy = vy + Room.getGravity();
				} else {
					vy = 15;
				}
				this.goY(this.getY() + vy);
			} else {
				vy = 0;
				this.setY(this.getCollisionInfo().getCollidingObjects().get(0).getY() - 10);
			}
		}
	}
	public void setSpeed (int speedX) {
		this.speedX = speedX;
	}
	
	@Override
	public boolean doesColide (GameObject o) {
		return !doneFor;
	}
}
