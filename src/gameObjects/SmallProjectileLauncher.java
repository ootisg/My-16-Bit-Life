package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import projectiles.SmallProjectile;
import resources.Sprite;

public class SmallProjectileLauncher extends GameObject {
	
	public static final Sprite sprite = new Sprite ("resources/sprites/circleLauncher.png");
	Jeffrey jeffrey;
	int timer = 0;
	
	public SmallProjectileLauncher () {
		this.setSprite(sprite);
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	
	@Override
	public void onDeclare () {
		jeffrey = Jeffrey.getActiveJeffrey();
	}
	
	public  void frameEvent () {
		if (this.isColliding(jeffrey)) {
			jeffrey.damage(15);
		}
		timer++;
		if (timer == 210) {
			//right
			SmallProjectile projectile = new SmallProjectile(0, 6, 150);
			projectile.declare(this.getX() + 16, this.getY() + 8);
			//left
			projectile = new SmallProjectile(Math.PI, 6, 150);
			projectile.declare(this.getX(), this.getY() + 8);
			//top
			projectile = new SmallProjectile(Math.PI / 2, 6, 150);
			projectile.declare(this.getX() + 8, this.getY() + 16);
			//bottom
			projectile = new SmallProjectile(3 * Math.PI / 2, 6, 150);
			projectile.declare(this.getX() + 8, this.getY());
			//top right
			projectile = new SmallProjectile(Math.PI / 4, 1, 150);
			projectile.declare(this.getX() + 16, this.getY() + 16);
			//top left
			projectile = new SmallProjectile(3 * Math.PI / 4, 1, 150);
			projectile.declare(this.getX(), this.getY() + 16);
			//bottom left
			projectile = new SmallProjectile(5 * Math.PI / 4, 1, 150);
			projectile.declare(this.getX(), this.getY());
			//bottom right
			projectile = new SmallProjectile(7 * Math.PI / 4, 1, 150);
			projectile.declare(this.getX() + 16, this.getY());
			timer = 0;
		}
		
		
	}
}
