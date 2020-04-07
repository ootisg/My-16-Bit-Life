package gameObjects;

import java.util.Random;

import map.Room;
import resources.Sprite;

public class ImpatientCar extends Enemy {
	boolean direction = true;
	Random rand = new Random();
	public ImpatientCar () {
		this.getAnimationHandler().setFrameTime(45);
		this.setFalls(true);
	this.setHitboxAttributes(0, 0, 73, 28);	
	int working = rand.nextInt(3);
	try {
	if (this.getVariantAttribute("color").equals("grey")) {
		working = 0;
	}
	if (this.getVariantAttribute("color").equals("red")) {
		working = 1;
	}
	if (this.getVariantAttribute("color").equals("black")) {
		working = 2;
	}
	}catch (Exception e) {
		
	}
	if (working == 0) {
	this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsGrey.txt"));
	}
	if (working == 1) {
	this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsRed.txt"));
	}
	if (working == 2) {
		this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsBlack.txt"));
		}
	try {
	if (this.getVariantAttribute("flip").equals("true")) {
		this.getAnimationHandler().flipHorizontal();
		this.direction = true;
	} else {
		this.direction =false;
	}
	} catch (Exception e) {
		
	}
	this.setHealth(30);
	this.defence = 60;
	}
	public ImpatientCar (boolean directionToGo) {
		this.getAnimationHandler().setFrameTime(45);
		this.setFalls(true);
		this.setHitboxAttributes(0, 0, 73, 28);	
		int working = rand.nextInt(3);
		if (working == 0) {
			this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsGrey.txt"));
		}
		if (working == 1) {
			this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsRed.txt"));
		}
		if (working == 2) {
			this.setSprite(new Sprite ("resources/sprites/config/ImpatentCarsBlack.txt"));
		}

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
				if (!Room.isColliding(this)) {
				this.deathEvent();
				}
			}
		} else {
			if (!this.goX(this.getX() + 3)) {
				if (!Room.isColliding(this)) {
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
