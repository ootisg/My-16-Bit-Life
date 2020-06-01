package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import main.GameLoop;
import players.Jeffrey;
import resources.Sprite;


public class BombsProjectile extends Projectile{
	
	double direction;
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	public static final Sprite medBomb = new Sprite ("resources/sprites/config/bomb_active_red.txt");
	public static final Sprite kaboomBomb = new Sprite ("resources/sprites/config/bomb_active_superred.txt");
	int timer = 0;
	int vx = 0;
	int vy = 0;
	Explosion explosion = new Explosion(10, 60, true, true);
	public static Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	
	public BombsProjectile (){
		this.setSprite(newBomb);
		this.getAnimationHandler().setFrameTime(100);
		this.setHitboxAttributes(0, 0, 14, 18);
		this.setSpeed(2);
		this.setDirection(direction);
		vx = 12;
		vy = 12;
	}
	@Override
	public void projectileFrame (){
		//Velocity stuff. Currently only works one way, and the bomb stops in ceilings and walls.
		vx--;
		if (vx < 0) {
			vx = 0;
		}
		if (!goingIntoWall) {
			this.setY(this.getY() - (vy / 3));
			this.setX(this.getX() + (vx / 3));
			vy--;
		}
		
		//Explosion timer. When switching the sprite, their animations stop. I'm 90% sure config files are good, so idk whats going on.
		timer++;
		if (timer > 40 && timer <= 80) {
			this.setSprite(medBomb);
		}
		if (timer > 80 && timer <= 120) {
			this.setSprite(kaboomBomb);
			this.getAnimationHandler().setFrameTime(100);
		}
		if (timer > 120) {
			explosion.declare(this.getX() - 8,this.getY() - 8);
			this.forget();
		}
	}
}

