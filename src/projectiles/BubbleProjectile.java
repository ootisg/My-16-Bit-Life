package projectiles;

import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class BubbleProjectile extends Projectile {
	
	private static final Sprite bubble = new Sprite ("resources/sprites/config/bubble_projectile.txt");
	
	boolean isPopped = false;
	
	//Each bubble has a 6 second timer
	int timer = 0;
	
	//Used for momentum-based velocity
	double jeffVx = 0;
	double jeffVy = 0;
	
	//This class is not yet commented
	Random RNG;
	public BubbleProjectile (double d) {
		RNG = new Random ();
		this.declare(0, 0);
		this.setSprite (bubble);
		this.setSpeed (d);
		this.setHitboxAttributes (2, 2, 12, 12);
		jeffVx = Jeffrey.getActiveJeffrey().vx;
		jeffVy = Jeffrey.getActiveJeffrey().vy;
	}
	@Override
	public void projectileFrame () {
		
		//6 second timer
		timer++;
		if (timer == 10) {
			this.getAnimationHandler().setAnimationFrame(1);
		}
		if (timer == 20) {
			this.getAnimationHandler().setAnimationFrame(0);
		}
		if (timer == 180) {
			pop();
		}
		
		//Makes a bubble disappear is it's popped.
		if (this.getAnimationHandler().getFrame() == 6) {
			this.forget();
			isPopped = false;
		}
		
		// Calculates speed using direction, the player's X and Y velocity at time of shooting, and a bit of RNG.
		double xTo = this.getX () + Math.cos (direction) * speed * jeffVx + RNG.nextInt(4) - 2;
		double yTo = this.getY () + Math.sin (direction) * speed * jeffVy + RNG.nextInt(4) - 2;
		speed -= (Math.pow(speed, 2)) / 30;
		if (speed <= 0) {
			speed = 0;
		}
		
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (!isPopped && this.isColliding(Enemy.enemyList.get(i))){
				Enemy.enemyList.get(i).damage (RNG.nextInt(10) + 20);
				pop();
			}
		}
		/*if ((this.goingIntoWall || (this.isCollidingChildren("Projectile") && !this.isColliding())) && !isPopped) {
			pop();
		}*/
		
		//Pops when touching a wall
		if (this.goingIntoWall && !isPopped) {
		pop();
		}
		
		//Pops when touching Jeffrey
		if (!isPopped && this.isColliding(Jeffrey.getActiveJeffrey())) {
			Jeffrey.getActiveJeffrey().vy = -10;
			pop();
		}
	}
	
	private void pop() {
		isPopped = true;
		this.setSpeed(0);
		this.getAnimationHandler().setAnimationFrame(2);
		this.getAnimationHandler().setFrameTime(60);
	}
}