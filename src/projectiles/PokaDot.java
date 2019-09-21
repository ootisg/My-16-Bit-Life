package projectiles;

import map.Room;
import resources.Sprite;

public class PokaDot extends Projectile {
	public boolean hitSomething;
	public PokaDot (double direction) {
	this.setSprite(new Sprite ("resources/sprites/config/PokaDot.txt"));
	this.setDirection(direction);
	hitSomething = false;
	this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public void projectileFrame (){
	}
	
}
