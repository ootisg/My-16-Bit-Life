package projectiles;

import java.util.Random;

import gameObjects.Enemy;
import main.ObjectHandler;
import map.Room;
import resources.Sprite;
import weapons.NinjaTriangle;

public class Triangle extends Projectile{
	public Sprite triangle; 
	int amountOfBounces;
	boolean stopped;
	double oldDirection;
	int timer;
	int pickupTimer;
	boolean stuckInRoom;
	double oldSpeed;
	Random RNG;
	NinjaTriangle copyTriangle;
	public Triangle(double throwDirection, double speed, int bounces, NinjaTriangle spawnerThing) {
		triangle = new Sprite ("resources/sprites/config/ninja_triangle.txt");
		this.setDirection(throwDirection);
		this.setSprite(triangle);
		this.setSpeed(speed);
		stopped = false;
		stuckInRoom = false;
		RNG = new Random ();
		pickupTimer = 0;
		this.keep();
		this.getAnimationHandler().setFrameTime(50);
		timer = 0;
		oldDirection = throwDirection;
		oldSpeed = speed;
		this.setHitboxAttributes(0, 0, 8, 8);
		amountOfBounces = bounces;
		copyTriangle = spawnerThing;
		this.SpinAwayFromWall();
	}
	@Override
	public void crashActions() {
		DirectionBullet bullet;
		bullet = new DirectionBullet (this.getX(), this.getY());
		Triangle returningTriangle = null;
		double directionToGo = bullet.findDirection(player);
		bullet.forget();
		if (copyTriangle.getTierInfo()[2] == 1) {
			if (amountOfBounces == 2) {
				returningTriangle = new Triangle (directionToGo, speed - 1, amountOfBounces, copyTriangle);
			} else {
		returningTriangle = new Triangle (directionToGo, speed - 1, amountOfBounces + 1, copyTriangle);
			}
			}
		if (copyTriangle.getTierInfo()[2] == 2) {
			if (amountOfBounces == 5) {
				returningTriangle = new Triangle (directionToGo, speed - .5, amountOfBounces, copyTriangle);
			} else {
		returningTriangle = new Triangle (directionToGo, speed - .5, amountOfBounces + 1, copyTriangle);
			}
			}
		if (copyTriangle.getTierInfo()[2] == 3) {
			if (amountOfBounces == 5) {
				returningTriangle = new Triangle (directionToGo, speed, amountOfBounces, copyTriangle);
			} else {
		returningTriangle = new Triangle (directionToGo, speed, amountOfBounces + 1, copyTriangle);
			}
			}
		returningTriangle.declare(this.getX(),this.getY());	
		this.forget();
	}
	@Override
	public void projectileFrame () {
		try {
		ObjectHandler.getObjectsByName(this.getClass().getSimpleName()).get(1).forget();
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			pickupTimer = pickupTimer + 1;
			// deals with damage
			if (!stopped) {
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					if (pickupTimer % 2 == 0) {
					Enemy.enemyList.get(i).damage (RNG.nextInt(5));
					}
				}
			}
			}
			//deals with the recalling of the triangle
			if (mouseButtonPressed (2)) {
				DirectionBullet bullet;
				bullet = new DirectionBullet (this.getX(), this.getY());
				Triangle returningTriangle;
				double directionToGo = bullet.findDirection(player);
				bullet.forget();
				returningTriangle = new Triangle (directionToGo, 10, 0, copyTriangle);
				returningTriangle.declare(this.getX(),this.getY());	
				this.forget();
			}
			if (outsideTheMap) {
				DirectionBullet bullet;
				bullet = new DirectionBullet (this.getX(), this.getY());
				Triangle returningTriangle = null;
				double directionToGo = bullet.findDirection(player);
				bullet.forget();
				if (copyTriangle.getTierInfo()[2] == 1) {
					if (amountOfBounces == 2) {
						returningTriangle = new Triangle (directionToGo, speed - 1, amountOfBounces, copyTriangle);
					} else {
				returningTriangle = new Triangle (directionToGo, speed - 1, amountOfBounces + 1, copyTriangle);
					}
					}
				if (copyTriangle.getTierInfo()[2] == 2) {
					if (amountOfBounces == 5) {
						returningTriangle = new Triangle (directionToGo, speed - .5, amountOfBounces, copyTriangle);
					} else {
				returningTriangle = new Triangle (directionToGo, speed - .5, amountOfBounces + 1, copyTriangle);
					}
					}
				if (copyTriangle.getTierInfo()[2] == 3) {
					if (amountOfBounces == 5) {
						returningTriangle = new Triangle (directionToGo, speed, amountOfBounces, copyTriangle);
					} else {
				returningTriangle = new Triangle (directionToGo, speed, amountOfBounces + 1, copyTriangle);
					}
					}
				returningTriangle.declare(this.getX(),this.getY());	
				this.forget();
			}
			//deals with the bounceyness
			if (this.goingIntoWall) {
			Triangle nextTriangle;
			if (copyTriangle.getTierInfo()[2] == 1 && amountOfBounces < 2 ) {
			nextTriangle = new Triangle (oldDirection + 1.57, speed - 1, amountOfBounces + 1, copyTriangle);
			nextTriangle.declare(this.getX(),this.getY());	
			this.forget();
			}
			if (copyTriangle.getTierInfo()[2] == 2 && amountOfBounces < 5 ) {
				nextTriangle = new Triangle (oldDirection + 1.57, speed - .5, amountOfBounces + 1, copyTriangle);
				nextTriangle.declare(this.getX(),this.getY());	
				this.forget();
				}
			if (copyTriangle.getTierInfo()[2] == 3 && amountOfBounces < 5 ) {
				nextTriangle = new Triangle (oldDirection + 1.57, speed, amountOfBounces + 1, copyTriangle);
				nextTriangle.declare(this.getX(),this.getY());	
				this.forget();
				}
			if (copyTriangle.getTierInfo()[2] == 0 ||( amountOfBounces == 2 && copyTriangle.getTierInfo()[2] == 1) || (copyTriangle.getTierInfo()[2] >=2 && amountOfBounces >= 5)) {
				this.setSpeed(0);
				if (copyTriangle.getTierInfo()[0] == 0) {
				this.getAnimationHandler().setFrameTime(0);
				stopped = true;
				}
				stuckInRoom = true;
			}
			}
			//deals with stoping the animation
			if (timer == 150 && (copyTriangle.getTierInfo()[0] == 1 || copyTriangle.getTierInfo()[0] == 2) ) {
				this.getAnimationHandler().setFrameTime(0);
				stopped = true;
			}
			if (timer == 450 && copyTriangle.getTierInfo()[0] == 3) {
				this.getAnimationHandler().setFrameTime(0);
				stopped = true;
			}
			//moves the triangle when its on the ground
			if (stuckInRoom && ((timer < 150 && (copyTriangle.getTierInfo()[0] == 1 || copyTriangle.getTierInfo()[0] == 2)) || (copyTriangle.getTierInfo()[0] == 3 && timer < 450))) {
				timer = timer + 1;
				if (getCursorX()> this.getX()- Room.getViewX()) {
					this.setY(this.getY() + 9);
					if (Room.isColliding(this.hitbox())) {
						this.setX(this.getX() + 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() - 9);
						if (Room.isColliding(this.hitbox())) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() + 9);
					} 
					this.setY(this.getY() - 18);
					if (Room.isColliding(this.hitbox())) {
						this.setX(this.getX() + 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() + 9);
						if (Room.isColliding(this.hitbox())) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() - 9);
					}
					this.setY(this.getY() + 9);
				} else {
					this.setY(this.getY() + 9);
					if (Room.isColliding(this.hitbox())) {
						this.setX(this.getX() - 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() - 9);
						if (Room.isColliding(this.hitbox())) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() + 9);
					} 
					this.setY(this.getY() - 18);
					if (Room.isColliding(this.hitbox())) {
						this.setX(this.getX() - 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() + 9);
						if (Room.isColliding(this.hitbox())) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() - 9);
					} 
					this.setY(this.getY() + 9);
				}
				if (getCursorY()> this.getY() - Room.getViewY()) {
				this.setX(this.getX() + 9);
				if (Room.isColliding(this.hitbox())) {
					this.setY(this.getY() + 2);
					if (!Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() - 9);
					if (Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() + 9);
				}
				this.setX(this.getX() - 18);
				if (Room.isColliding(this.hitbox())) {
					this.setY(this.getY() + 2);
					if (!Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() + 9);
					if (Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() - 9);
				}
				this.setX(this.getX() + 9);
				} else {
					this.setX(this.getX() + 9);
					if (Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() - 9);
						if (Room.isColliding(this.hitbox())) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() + 9);
					}
					this.setX(this.getX() - 18);
					if (Room.isColliding(this.hitbox())) {
						this.setY(this.getY() - 2);
						if (!Room.isColliding(this.hitbox())) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() + 9);
						if (Room.isColliding(this.hitbox())) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() - 9);
					}
					this.setX(this.getX() + 9);
				}
			}
		if (this.isColliding(player) && (amountOfBounces != 0 || pickupTimer > 2)) {
			NinjaTriangle.inHand = true;
			this.forget();
		}
		} catch (ArrayIndexOutOfBoundsException e){
			Triangle nextTriangle;
			nextTriangle = new Triangle (oldDirection + 3.14, speed, amountOfBounces + 1, copyTriangle);
			nextTriangle.declare(this.getX() - 10,this.getY());
			this.forget();
		}
	}
}
