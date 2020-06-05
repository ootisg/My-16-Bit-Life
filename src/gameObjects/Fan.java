package gameObjects;

import java.util.Iterator;
import java.util.LinkedList;


import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
/**
 * NOTE THESE USE VECOR COLLISIONS WHITCH SO IF THEY ARE BUGGY THAT IS WHY
 * also may cause lag so check back here cuz it iterates through all objects
 * @author Jeffrey Marsh
 *
 */
public class Fan extends MapObject {
	Sprite VERTICAL = new Sprite ("resources/sprites/fanVeritacal.png");
	Sprite HORIZONTAL = new Sprite ("resources/sprites/fanHorizontal.png");
	double power;
	double range;
	boolean inzialized = false;
	public Fan () {
		this.adjustHitboxBorders();
		
	}
	
	public void frameEvent () {
		if (!inzialized) {
			try {
				switch (this.getVariantAttribute("Direction")) {
				case "Up":
					this.setHitboxAttributes(0, 0,60, 16);
					this.getAnimationHandler().setFlipVertical(true);
					this.setSprite(VERTICAL);
					break;
				case "Down":	
					this.setSprite(VERTICAL);
					this.setHitboxAttributes(0, 0,60, 16);
					break;
				case "Left":
					this.setSprite(HORIZONTAL);
					this.setHitboxAttributes(0, 0,16, 60);
					break;
				case "Right":
					this.setSprite(HORIZONTAL);
					this.setHitboxAttributes(0, 0,16, 60);
					this.getAnimationHandler().setFlipHorizontal(true);
					break;
				}
			} catch (NullPointerException e) {
				this.setSprite(VERTICAL);
				this.setHitboxAttributes(0, 0,60, 16);
				this.getAnimationHandler().setFlipVertical(true);
			}
			try {
				power = Double.parseDouble(this.getVariantAttribute("power"));
			} catch (NullPointerException e) {
				if (this.getSprite().equals(VERTICAL)) {
				if (this.getAnimationHandler().flipVertical()) {
					power = 3;
				} else {
				
					power = 1;
				}
				} else {
					power = 1;
				}
			}
			inzialized =true;
		}
		super.frameEvent();
		if (this.getSprite().equals(VERTICAL)) {
			if (this.getAnimationHandler().flipVertical()) {
				// code for upwards fan
				if (Room.getCollisionInfo(this.getX(), this.getY(), this.getX(), 0) != null) {
					range = this.getY() - Room.getCollisionInfo(this.getX(), this.getY(), this.getX(), 0).getTileY()*Room.TILE_HEIGHT;
				} else {
					range = this.getY();
				}
				LinkedList<LinkedList<GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
				Iterator<LinkedList <GameObject>> iter = working.iterator();
				Iterator <GameObject> innerIter;
				while (iter.hasNext()) {
					innerIter = iter.next().iterator();
					while (innerIter.hasNext()) {
						GameObject reallyWorkin = innerIter.next();
						if (reallyWorkin.hitbox() != null) {
							if (reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() + reallyWorkin.hitbox().getWidth() > this.getX() && reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() < this.getX() + this.hitbox().width && reallyWorkin.getY() +reallyWorkin.hitbox().getHeight() + reallyWorkin.getHitboxYOffset()  < this.getY() && reallyWorkin.getY() + reallyWorkin.getHitboxYOffset()  > this.getY()-range) {
								double distance = this.getY() - reallyWorkin.getY();
								if (reallyWorkin.getClass().getSimpleName().equals("Jeffrey")) {
									if (keyDown (32)) {
									if (!reallyWorkin.goY(reallyWorkin.getY() - (power/(distance/400) + 1/(distance/400)))) {
									
									}
									
									} else {
										reallyWorkin.goY(reallyWorkin.getY() - (power/(distance/400)));
									}
									Jeffrey lazyWorkin = (Jeffrey) reallyWorkin;
									if (lazyWorkin.vy > 5) {
									lazyWorkin.vy = 5;
									}
								} else {
									reallyWorkin.goY(reallyWorkin.getY() - power/(distance/400));
								}
							}
						}
					}
				}
			} else {
				//code for downwards fan
				if (Room.getCollisionInfo(this.getX(), this.getY(), this.getX(), Room.getHeight()*Room.TILE_HEIGHT) != null) {
					range = Room.getCollisionInfo(this.getX(), this.getY(), this.getX(), Room.getHeight()*Room.TILE_HEIGHT).getTileY()*Room.TILE_HEIGHT - this.getY();
				} else {
					range = Room.getHeight()*Room.TILE_HEIGHT - this.getY();
				}
				LinkedList<LinkedList<GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
				Iterator<LinkedList <GameObject>> iter = working.iterator();
				Iterator <GameObject> innerIter;
				while (iter.hasNext()) {
					innerIter = iter.next().iterator();
					while (innerIter.hasNext()) {
						GameObject reallyWorkin = innerIter.next();
						if (reallyWorkin.hitbox() != null) {
							if (reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() + reallyWorkin.hitbox().getWidth() > this.getX() && reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() < this.getX() + this.hitbox().width && reallyWorkin.getY() + reallyWorkin.getHitboxYOffset() > this.getY() && reallyWorkin.getY() + reallyWorkin.getHitboxYOffset() < this.getY()+range) {
								double distance = reallyWorkin.getY() - this.getY();
									reallyWorkin.goY(reallyWorkin.getY() + power/(distance/400));
							}
						}
					}
				}
			}
		} else {
			if (!this.getAnimationHandler().flipHorizontal()) {
				//code for left faceing fan
				if (Room.getCollisionInfo(this.getX(), this.getY(), 0,this.getY()) != null) {
					range =  this.getX() -Room.getCollisionInfo(this.getX(), this.getY(), 0,this.getY()).getTileX()*Room.TILE_WIDTH;
				} else {
					range = this.getX();
				}
				LinkedList<LinkedList<GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
				Iterator<LinkedList <GameObject>> iter = working.iterator();
				Iterator <GameObject> innerIter;
				while (iter.hasNext()) {
					innerIter = iter.next().iterator();
					while (innerIter.hasNext()) {
						GameObject reallyWorkin = innerIter.next();
						if (reallyWorkin.hitbox() != null) {
							if (reallyWorkin.getY() + reallyWorkin.getHitboxYOffset() < this.getY() + this.hitbox().height && reallyWorkin.getY() + reallyWorkin.getHitboxYOffset()  + reallyWorkin.hitbox().height > this.getY()&& reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() < this.getX() && reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() > this.getX()-range) {
								double distance = this.getX() - reallyWorkin.getX();
									reallyWorkin.goX(reallyWorkin.getX() - power/(distance/400));
							}
						}
					}
				}			
			} else {
				//code for right faceing fan
				if (Room.getCollisionInfo(this.getX(), this.getY(), Room.getWidth()*Room.TILE_WIDTH,this.getY()) != null) {
					range =  Room.getCollisionInfo(this.getX(), this.getY(), Room.getWidth()*Room.TILE_WIDTH,this.getY()).getTileX() * Room.TILE_WIDTH - this.getX();
				} else {
					range = Room.getWidth()*Room.TILE_WIDTH - this.getX();
				}
				LinkedList<LinkedList<GameObject>> working = ObjectHandler.getChildrenByName("GameObject");
				Iterator<LinkedList <GameObject>> iter = working.iterator();
				Iterator <GameObject> innerIter;
				while (iter.hasNext()) {
					innerIter = iter.next().iterator();
					while (innerIter.hasNext()) {
						GameObject reallyWorkin = innerIter.next();
						if (reallyWorkin.hitbox() != null) {
							if (reallyWorkin.getY() + reallyWorkin.getHitboxYOffset() < this.getY() + this.hitbox().height && reallyWorkin.getY() + reallyWorkin.getHitboxYOffset() > this.getY()&& reallyWorkin.getX() + reallyWorkin.getHitboxXOffset() > this.getX() + this.hitbox().getWidth() && reallyWorkin.getX() + reallyWorkin.getHitboxXOffset()  < this.getX() + this.hitbox().width+range) {
								double distance = reallyWorkin.getX() - this.getX();
									reallyWorkin.goX(reallyWorkin.getX() + power/(distance/400));
							}
						}
					}
				}
			}
		}
	}
}
