package projectiles;

import main.ObjectHandler;
import map.Room;
import players.Player;
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
	public PokaDot (double direction, Sprite sprite) {
		this.setSprite(sprite);
		this.setDirection(direction);
		hitSomething = false;
		this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
		this.setSpeed(2);
	}
	@Override
	public void projectileFrame (){
		if (this.goingIntoWall){
			this.forget();
		}
		try{
		if (Room.isColliding(this) && !hitSomething){
			hitSomething = true;
			setSpeed (0);
			this.forget();
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.forget();
		}
		if (this.outsideTheMap) {
			this.forget();
		}
		if (isColliding(Player.getActivePlayer()) && !hitSomething){
			Player.getActivePlayer().damage(7);
			hitSomething = true;
			this.forget();
			
			}
	}
	
}
