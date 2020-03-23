package gameObjects;

import main.GameObject;
import resources.Sprite;

public class CarSpawner extends GameObject {
	boolean direction = true;
	int timer = 0;
	ImpatientCar car;
	public CarSpawner () {
		this.setSprite(new Sprite ("resources/sprites/garage.png"));
		try {
		if (this.getVariantAttribute("flip").equals("true")) {
			this.getAnimationHandler().setFlipHorizontal(true);
		} else {
			direction = false;
		}
		} catch (NullPointerException e) {
			
		}
	}
	public CarSpawner (boolean directionToSpawn) {
		this.setSprite(new Sprite ("resources/sprites/garage.png"));
		direction = directionToSpawn;
		if (directionToSpawn) {
			this.getAnimationHandler().setFlipHorizontal(true);
		}
	}
	@Override 
	public void frameEvent () {
		timer = timer + 1;
		if (timer == 90) {
			timer = 0;
			if (direction) {
				car = new ImpatientCar (true);
				car.declare(this.getX(),this.getY());
			} else {
				car = new ImpatientCar (false);
				car.declare(this.getX(),this.getY());
			}
		}
	}
}
