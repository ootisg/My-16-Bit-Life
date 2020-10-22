package projectiles;

import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class SmallProjectile extends Projectile {
	
	public boolean hitSomething;
	int timer = 0;
	int endTime = 0;
	
	public SmallProjectile (double direction, int speed, int timer) {
	this.setSprite(new Sprite ("resources/sprites/smallProjectile.png"));
	this.setDirection(direction);
	hitSomething = false;
	endTime = timer;
	this.setHitboxAttributes(0, 0, 8, 8);
	this.setSpeed(speed * 3);
	}
	
	@Override
	public void projectileFrame (){
		timer++;
		double xTo = this.getX () + Math.cos (direction) * speed;
		double yTo = this.getY () + Math.sin (direction) * speed;
		speed -= (Math.pow(speed, 2)) / 30;
		
		if (endTime - timer == 60) {
			this.setSpeed(0);
		}
		
		if (timer == endTime) {
			this.forget();;
		}
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
		if (isColliding(Jeffrey.getActiveJeffrey()) && !hitSomething){
			Jeffrey.getActiveJeffrey().damage(5);
			hitSomething = true;
			this.forget();
			
			}
	}
	
}
