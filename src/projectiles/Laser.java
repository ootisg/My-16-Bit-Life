package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;


public class Laser extends Projectile{
	
	public static final Sprite laser = new Sprite ("resources/sprites/config/laser.txt");
	
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	boolean hitSomething = false;
	int animation = 0;
	
	public Laser (boolean direction){
		setSprite (laser);
		setHitboxAttributes (0,0,8,5);
		setAttributes (128, 128, 3.14, 1);
		if (direction){
			setDirection (0);
		}
		else{
			setDirection (3.14);
		}
	}
	@Override
	public void projectileFrame (){
		if (hitSomething){
			this.forget();
		}
		try{
		if (Room.isColliding(this) && !hitSomething){
			hitSomething = true;
			setSpeed (0);
			setY (getY());
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.forget();
		}
		if (isColliding(ObjectHandler.getObjectsByName("Jeffrey").get(0)) && !hitSomething){
			player.damage(7);
			hitSomething = true;
			setSpeed (0);
			setY (getY());
			
			}
	}
}

