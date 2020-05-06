package enemys;

import projectiles.Water;
import resources.Sprite;

public class FireHydrant extends Enemy {
	int timer = 0;
	public FireHydrant () {
		this.setHealth(40);
		this.defence = 40;
		this.setSprite(new Sprite ("resources/sprites/config/hydrant.txt"));
		this.setHitboxAttributes(1, 9, 14, 31);
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
		if (timer > 63) {
			this.getAnimationHandler().setAnimationFrame(1);
		} else {
			this.getAnimationHandler().setAnimationFrame(0);
		}
		if (timer > 65) {
			timer = 0;
			Water water = new Water (true);
			Water oil = new Water (false);
			water.declare(this.getX() + 13,this.getY() + 16);
			oil.declare(this.getX() - 11, this.getY() + 16);
		}
		timer = timer + 1;
	}
}
