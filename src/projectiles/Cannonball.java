package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;


public class Cannonball extends Projectile{
	
	public static final Sprite cannonball = new Sprite ("resources/sprites/config/tank_cannonball.txt");
	public static final Sprite explosion = new Sprite ("resources/sprites/config/tank_explosion.txt");
	
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	boolean hitSomething = false;
	int animation = 0;
	public Cannonball (boolean direction){
		setSprite (cannonball);
		setHitboxAttributes (0,0,8,8);
		setAttributes (128, 128, 3.14, 5);
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
			animation = animation + 1;
		}
		if (animation == 20){
			this.forget();
		}
		try{
		if (this.goingIntoWall && !hitSomething){
			setSprite (explosion);
			hitSomething = true;
			setSpeed (0);
			setY (getY());
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.forget();
		}
		if (isColliding(player) && !hitSomething){
			player.damage(7);
			setSprite (explosion);
			hitSomething = true;
			setSpeed (0);
			setY (getY());
			
			}
	}
}

