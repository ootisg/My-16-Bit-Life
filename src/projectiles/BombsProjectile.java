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
	public int timer = 0;
	double vx = 0;
	boolean fakeDirection = false;
	double vy = 0;
	boolean inzialaized = false;
	boolean thrown = false;
	Explosion explosion = new Explosion(10, 60, true, true);
	public static Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	
	public BombsProjectile (){
		this.setSprite(newBomb);
		this.getAnimationHandler().setFrameTime(100);
		this.setHitboxAttributes(0, 0, 14, 18);
		this.setSpeed(0.2);
		this.setDirection(direction);
		vx = 12;
		vy = 12;
	}
	@Override
	public void projectileFrame (){
		//Velocity stuff. Currently only works one way, and the bomb stops in ceilings and walls.
		if (thrown) {
			if (fakeDirection) {
				vx = vx - 0.3;
				if (vx < 0) {
					vx = 0;
				}
				if (!inzialaized) {
					vy = -18 * this.getDirection();
					inzialaized = true;
				}
			} else {
				vx = vx + 0.3;
				if (vx > 0) {
					vx = 0;
				}
				if (!inzialaized) {
					vy = 18 * (this.getDirection() - 3.14);
					inzialaized = true;
				}
			}
			if (!goingIntoWall) {
				this.setY(this.getY() - (vy / 3));
				this.setX(this.getX() + (vx / 3));
				vy--;
			}
		}
		//Explosion timer. When switching the sprite, their animations stop. I'm 90% sure config files are good, so idk whats going on.
		timer++;
		if (timer > 40 && timer <= 80) {
			if (!this.getSprite().equals(medBomb)) {
				this.setSprite(medBomb);
			}
		}
		if (timer > 80 && timer <= 120) {
			if (!this.getSprite().equals(kaboomBomb)) {
				this.setSprite(kaboomBomb);
			}
			this.getAnimationHandler().setFrameTime(100);
		}
		if (timer > 120) {
			explosion.declare(this.getX() - 8,this.getY() - 8);
			this.forget();
		}
	}
	public void throwBomb () {
		thrown = true;
	}
	public void setVx (double newVx) {
		vx = newVx;
	}
	public void setFakeDireciton (boolean newDireciton) {
		fakeDirection = newDireciton;
	}
}

