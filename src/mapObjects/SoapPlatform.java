package mapObjects;


import java.util.ArrayList;
import java.util.Random;

import main.GameObject;
import map.Room;
import resources.Sprite;

public class SoapPlatform extends CarryObject {
	int speedX = 0; 
	double vy;
	boolean doneFor = false;
	int waterDisplace = 0;
	int bobCount = 1;
	public SoapPlatform () {
		Random rng = new Random();
		int color = rng.nextInt(4);
		switch(color) {
		case 0:
			this.setSprite(new Sprite ("resources/sprites/Soap_Tile_Blue.png"));
			break;
		case 1:
			this.setSprite(new Sprite ("resources/sprites/Soap_Tile_Pink.png"));
			break;
		case 2:
			this.setSprite(new Sprite ("resources/sprites/Soap_Tile_Green.png"));
			break;
		case 3:
			this.setSprite(new Sprite ("resources/sprites/Soap_Tile_White.png"));
			break;
		}
		this.setHitboxAttributes(0,0, 64, 32);
		this.adjustHitboxBorders();
		this.suffocateObjects(true);
		ArrayList <String> excludeList = new ArrayList<String> ();
		excludeList.add("BubblePlatform");
		this.setExcludeList(excludeList);
		}
	@Override
	public void frameEvent ()
	{
		this.setHitboxAttributes(0, -3, 64, 32);
		this.isCollidingChildren("GameObject");
		this.setHitboxAttributes(0, 0, 64, 32);
		ArrayList <GameObject> collidingObjects = this.getCollisionInfo().getCollidingObjects();
		for (int i = 0; i < collidingObjects.size(); i++) {
			if (!collidingObjects.get(i).isPushable()) {
				collidingObjects.remove(i);
				i = i - 1;
			}
		}
		if (doneFor) {
			this.despawnAllCoolLike(20);
		} else {
			super.frameEvent();
			if (!this.goX(speedX,collidingObjects)) {
				doneFor = true;
			}
			if (!this.isColliding("BubbleWater")) {
				if (vy < 15) {
					vy = vy + Room.getGravity();
				} else {
					vy = 15;
				}
				this.goY(vy, collidingObjects);
			} else {
				vy = 0;
				if(collidingObjects.size() >= 1) {
					if (bobCount % 2 == 1) {
						if (waterDisplace < 5 * collidingObjects.size()/bobCount) {
							Random rand = new Random ();
							waterDisplace = waterDisplace + rand.nextInt(3);
						} else {
							waterDisplace = 5 * collidingObjects.size()/bobCount;
							
							bobCount = bobCount + 1;
						}
					} else {
						if (waterDisplace > -5 * collidingObjects.size()/bobCount) {
							Random rand = new Random ();
							waterDisplace = waterDisplace - rand.nextInt(3);
						} else {
							waterDisplace = -5 * collidingObjects.size()/bobCount;
							bobCount = bobCount + 1;
						}
					}
				} else {
					waterDisplace = waterDisplace - 2;
					if (waterDisplace < 0) {
						waterDisplace = 0;
					}
					bobCount = 1;
				}
				this.setY(this.getCollisionInfo().getCollidingObjects().get(0).getY() + waterDisplace - 10);
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
