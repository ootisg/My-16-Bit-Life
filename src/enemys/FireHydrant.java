package enemys;

import projectiles.Water;
import resources.Sprite;

public class FireHydrant extends Enemy {
	int timer = 0;
	public FireHydrant () {
		this.setHealth(40);
		this.defence = 40;
		this.setSprite(new Sprite ("resources/sprites/config/hydrant.txt"));
		this.setHitboxAttributes(0, 0, 16, 23);
	}
	@Override 
	public String checkName () {
		return "FIRE HYDRANT";
	}
	@Override
	public String checkEntry () {
		return "THIS BREAKS SO MANY SAFTY CODES I DONT EVEN KNOW WHERE TO START LIKE SERIOSLY THIS WATER HAS TO BE INCREADBY HIGH PRESSURE I CANT BELIVE THE CITY HASENT MADE FIXING THESE A PRIORITY SERIOSLY WHERE ARE MY TAX DOLLERS EVEN GOING?";
	}
	@Override 
	public void enemyFrame () {
		if (timer > 25) {
			this.getAnimationHandler().setAnimationFrame(1);
		} else {
			this.getAnimationHandler().setAnimationFrame(0);
		}
		if (timer > 30) {
			timer = 0;
			Water water = new Water (true);
			Water oil = new Water (false);
			water.declare(this.getX() + 16,this.getY());
			oil.declare(this.getX(), this.getY());
		}
		timer = timer + 1;
	}
}
