package gameObjects;

import main.GameObject;

public class BubbleSpawner extends GameObject {
	double speed;
	double spawnRate;
	long timeBetween;
	BubbleWater water = null;
	public BubbleSpawner () {
		this.setHitboxAttributes(0, 0,16, 16);
		this.adjustHitboxBorders();
	}
	public void onDeclare () {
		if (this.getVariantAttribute("speed") != null && !this.getVariantAttribute("speed").equals("nv")) {
			speed = Double.parseDouble(this.getVariantAttribute("speed"));
		} else {
			speed = 0;
		}
		if (this.getVariantAttribute("spawnRate") != null && !this.getVariantAttribute("spawnRate").equals("nv")) {
			spawnRate = Double.parseDouble(this.getVariantAttribute("spawnRate"));
		} else {
			spawnRate = 5;
		}
		timeBetween = System.currentTimeMillis();
	}
	@Override
	public void frameEvent () {
		if (System.currentTimeMillis() - timeBetween > spawnRate * 1000) {
			BubblePlatform working = new BubblePlatform (speed);
			working.declare(this.getX(), this.getY());
			timeBetween = System.currentTimeMillis();
		}
		if (water == null) {
			if (this.isColliding("BubbleWater")) {
				water = (BubbleWater)this.getCollisionInfo().getCollidingObjects().get(0);
			}
		} else {
			this.setY(water.getY());
		}
	}
}
