package gameObjects;

import map.Room;
import resources.Sprite;

public class ImpatientCar extends Enemy {
	boolean direction = true;
	public ImpatientCar () {
		this.setFalls(true);
	this.setHitboxAttributes(0, 0, 32, 8);	
	this.setSprite(new Sprite ("resources/sprites/impatientCar.png"));
	this.setHealth(30);
	this.defence = 60;
	}
	public ImpatientCar (boolean directionToGo) {
		this.setFalls(true);
		this.setHitboxAttributes(0, 0, 32, 8);	
		this.setSprite(new Sprite ("resources/sprites/impatientCar.png"));
		this.setHealth(30);
		this.defence = 60;
		direction = directionToGo;
		if (directionToGo) {
			this.getAnimationHandler().flipHorizontal();
		}
	}
	@Override 
	public void enemyFrame () {
		try {
		if (direction) {
			if (!this.goX(this.getX()- 3)) {
				if (!Room.isColliding(this.hitbox())) {
				this.deathEvent();
				}
			}
		} else {
			if (!this.goX(this.getX() + 3)) {
				if (!Room.isColliding(this.hitbox())) {
				this.deathEvent();
				}
			}
		}
		} catch (IndexOutOfBoundsException e) {
			this.deathEvent();
		}
	}
	@Override 
	public String checkName () {
		return "IMPATIENT CAR";
	}
	@Override
	public String checkEntry () {
		return "THIS GUY REALLY SHOULDENT HAVE A DRIVERS LICENCE";
	}
}
