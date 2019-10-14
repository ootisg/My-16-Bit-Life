package gameObjects;

import resources.Sprite;

public class LivingPeriod extends Enemy{
	public Sprite period;
	public LivingPeriod () {
	period = new Sprite ("resources/sprites/config/Period.txt");
	this.setSprite(period);
	this.setHitboxAttributes(0, 0, 5, 4);
	this.setHealth(20);
	this.setFalls(true);
	}
	@Override
	public void enemyFrame () {
		this.jumpyBoi(period, period, 3, 6, 2);
	}
	
}
