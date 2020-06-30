package gameObjects;

import enemys.ImpatientCar;
import main.GameObject;
import resources.Sprite;
import switches.Activateable;

public class CarSpawner extends GameObject implements Activateable {
	boolean direction = true;
	int timer = 0;
	ImpatientCar car;
	boolean active = true;
	boolean inzialized = false;
	public CarSpawner () {
		this.setSprite(new Sprite ("resources/sprites/garage.png"));
		
	}
	public CarSpawner (boolean directionToSpawn) {
		this.setSprite(new Sprite ("resources/sprites/garage.png"));
		direction = directionToSpawn;
		if (directionToSpawn) {
			this.getAnimationHandler().setFlipHorizontal(true);
		}
		active = true;
	}
	@Override 
	public void frameEvent () {
		if (!inzialized ) {
			try {
				if (this.getVariantAttribute("flip").equals("true")) {
					this.getAnimationHandler().setFlipHorizontal(true);
				} else {
					direction = false;
				}
				} catch (NullPointerException e) {
					
				}
			if (this.getVariantAttribute("active") != null) {
				if (!(this.getVariantAttribute("active").equals("nv") || this.getVariantAttribute("active").equals("false"))) {
					active = true;
				} else {
					active = false;
				}
			}
			inzialized = true;
		}
		if (active) {
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
	@Override
	public void activate() {
		active = true;
	}
	@Override
	public void deactivate() {
		active = false;
	}
	@Override
	public boolean isActivated () {
		return active;
	}
	@Override
	public void pair() {
		
	}
}
