package projectiles;

import main.GameCode;
import map.Room;
import resources.Sprite;

public class PokaDot extends Projectile {
	public boolean hitSomething;
	public PokaDot (double direction) {
	this.setSprite(new Sprite ("resources/sprites/config/PokaDot.txt"));
	this.setDirection(direction);
	hitSomething = false;
	this.setHitboxAttributes(0, 0, 4, 4);
	this.setSpeed(2);
	}
	@Override
	public void projectileFrame (){
		if (this.goingIntoWall){
			this.forget();
		}
		try{
		if (Room.isColliding(this.hitbox()) && !hitSomething){
			hitSomething = true;
			setSpeed (0);
			this.forget();
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.forget();
		}
		if (isColliding(GameCode.testJeffrey) && !hitSomething){
			player.damage(7);
			hitSomething = true;
			this.forget();
			
			}
	}
	
}
