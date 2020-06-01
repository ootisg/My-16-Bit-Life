package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;
import projectiles.Explosion;


public class Cannonball extends Projectile{
	
	public static final Sprite cannonball = new Sprite ("resources/sprites/config/tank_cannonball.txt");
	Explosion explosion = new Explosion(50, 0, true, false);
	
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
			explosion.declare(this.getX() - 12,this.getY() - 12);
			this.forget();
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.forget();
		}
		if (isColliding(player) && !hitSomething){
			explosion.declare(this.getX() - 12,this.getY() - 12);
			this.forget();
			}
	}
}

