package projectiles;

import java.util.ArrayList;
import java.util.Random;

import enemys.Enemy;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import weapons.NinjaTriangle;

public class Triangle extends Projectile{
	public Sprite triangle; 
	int amountOfBounces;
	boolean stopped;
	double oldDirection;
	int timer;
	boolean homeing = false;
	int pickupTimer;
	int recallTimer = 0;
	boolean stuckInRoom;
	boolean consectuive;
	double oldSpeed;
	Random RNG;
	NinjaTriangle copyTriangle;
	ArrayList <Enemy> blacklist ;
	public Triangle(double throwDirection, double speed, int bounces, NinjaTriangle spawnerThing) {
		triangle = new Sprite ("resources/sprites/config/ninja_triangle.txt");
		this.setDirection(throwDirection);
		this.setSprite(triangle);
		this.setSpeed(speed);
		stopped = false;
		blacklist = new ArrayList<Enemy>();
		stuckInRoom = false;
		this.adjustHitboxBorders();
		consectuive = true;
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
		double directionToGo = bullet.findDirection(Jeffrey.getActiveJeffrey());
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
		this.replace();
	}
	@Override
	public void projectileFrame () {
		if (this.homeing) {
			DirectionBullet bullet;
			bullet = new DirectionBullet (this.getX(), this.getY());
			double directionToGo = bullet.findDirection(Jeffrey.getActiveJeffrey());
			bullet.forget();
			this.setDirection(directionToGo);
		}
		try {
		ObjectHandler.getObjectsByName(this.getClass().getSimpleName()).get(1).forget();
		} catch (IndexOutOfBoundsException e) {
			
		}
		try {
			pickupTimer = pickupTimer + 1;
			// deals with damage
			if (!stopped) {
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i)) && !this.blacklist.contains(Enemy.enemyList.get(i))){
					Enemy.enemyList.get(i).damage (30 + RNG.nextInt(20));
					blacklist.add(Enemy.enemyList.get(i));
				}
			}
			}
			//deals with the recalling of the triangle
			recallTimer = recallTimer + 1;
			if (mouseButtonPressed (2) && recallTimer > 10) {
				DirectionBullet bullet;
				recallTimer = 0;
				bullet = new DirectionBullet (this.getX(), this.getY());
				Triangle returningTriangle;
				double directionToGo = bullet.findDirection(Jeffrey.getActiveJeffrey());
				returningTriangle = new Triangle (directionToGo, 10, 0, copyTriangle);
				returningTriangle.home();
				returningTriangle.declare(this.getX(),this.getY());	
				this.replace();
			}
			if (outsideTheMap) {
				DirectionBullet bullet;
				bullet = new DirectionBullet (this.getX(), this.getY());
				Triangle returningTriangle = null;
				double directionToGo = bullet.findDirection(Jeffrey.getActiveJeffrey());
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
				if (this.homeing) {
					returningTriangle.home();
				}
				returningTriangle.declare(this.getX(),this.getY());	
				this.replace();
			}
			//deals with the bounceyness
			if (this.goingIntoWall) {
			Triangle nextTriangle;
			if (!consectuive) {
			if (copyTriangle.getTierInfo()[2] == 1 && amountOfBounces < 2 ) {
			nextTriangle = new Triangle (oldDirection + 1.57, speed - 1, amountOfBounces + 1, copyTriangle);
			if (this.homeing) {
				nextTriangle.home();
			}
			nextTriangle.declare(this.getX(),this.getY());	
			this.replace();
			}
			if (copyTriangle.getTierInfo()[2] == 2 && amountOfBounces < 5 ) {
				nextTriangle = new Triangle (oldDirection + 1.57, speed - .5, amountOfBounces + 1, copyTriangle);
				if (this.homeing) {
					nextTriangle.home();
				}
				nextTriangle.declare(this.getX(),this.getY());	
				this.replace();
				}
			if (copyTriangle.getTierInfo()[2] == 3 && amountOfBounces < 5 ) {
				nextTriangle = new Triangle (oldDirection + 1.57, speed, amountOfBounces + 1, copyTriangle);
				if (this.homeing) {
					nextTriangle.home();
				}
				nextTriangle.declare(this.getX(),this.getY());	
				this.replace();
				}
			if (copyTriangle.getTierInfo()[2] == 0 ||( amountOfBounces == 2 && copyTriangle.getTierInfo()[2] == 1) || (copyTriangle.getTierInfo()[2] >=2 && amountOfBounces >= 5)) {
				this.setSpeed(0);
				if (copyTriangle.getTierInfo()[0] == 0) {
				this.getAnimationHandler().setFrameTime(0);
				stopped = true;
				}
				stuckInRoom = true;
			}
				} else {
				this.SpinAwayFromWall();
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
					if (Room.isColliding(this)) {
						this.setX(this.getX() + 2);
						if (!Room.isColliding(this)) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() - 9);
						if (Room.isColliding(this)) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() + 9);
					} 
					this.setY(this.getY() - 18);
					if (Room.isColliding(this)) {
						this.setX(this.getX() + 2);
						if (!Room.isColliding(this)) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() + 9);
						if (Room.isColliding(this)) {
							this.setX(this.getX() - 2);
						}
						this.setY(this.getY() - 9);
					}
					this.setY(this.getY() + 9);
				} else {
					this.setY(this.getY() + 9);
					if (Room.isColliding(this)) {
						this.setX(this.getX() - 2);
						if (!Room.isColliding(this)) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() - 9);
						if (Room.isColliding(this)) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() + 9);
					} 
					this.setY(this.getY() - 18);
					if (Room.isColliding(this)) {
						this.setX(this.getX() - 2);
						if (!Room.isColliding(this)) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() + 9);
						if (Room.isColliding(this)) {
							this.setX(this.getX() + 2);
						}
						this.setY(this.getY() - 9);
					} 
					this.setY(this.getY() + 9);
				}
				if (getCursorY()> this.getY() - Room.getViewY()) {
				this.setX(this.getX() + 9);
				if (Room.isColliding(this)) {
					this.setY(this.getY() + 2);
					if (!Room.isColliding(this)) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() - 9);
					if (Room.isColliding(this)) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() + 9);
				}
				this.setX(this.getX() - 18);
				if (Room.isColliding(this)) {
					this.setY(this.getY() + 2);
					if (!Room.isColliding(this)) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() + 9);
					if (Room.isColliding(this)) {
						this.setY(this.getY() - 2);
					}
					this.setX(this.getX() - 9);
				}
				this.setX(this.getX() + 9);
				} else {
					this.setX(this.getX() + 9);
					if (Room.isColliding(this)) {
						this.setY(this.getY() - 2);
						if (!Room.isColliding(this)) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() - 9);
						if (Room.isColliding(this)) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() + 9);
					}
					this.setX(this.getX() - 18);
					if (Room.isColliding(this)) {
						this.setY(this.getY() - 2);
						if (!Room.isColliding(this)) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() + 9);
						if (Room.isColliding(this)) {
							this.setY(this.getY() + 2);
						}
						this.setX(this.getX() - 9);
					}
					this.setX(this.getX() + 9);
				}
			}
		if (this.isColliding(Jeffrey.getActiveJeffrey()) && (amountOfBounces != 0 || pickupTimer > 2)) {
			copyTriangle.inHand = true;
			this.replace();
		}
		} catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			Triangle nextTriangle;
			nextTriangle = new Triangle (oldDirection + 3.14, speed, amountOfBounces + 1, copyTriangle);
			if (this.homeing) {
				nextTriangle.home();
			}
			nextTriangle.declare(this.getX() - 10,this.getY());
			this.replace();
		}
		if (pickupTimer > 3) {
		consectuive = false;
		}
	}
	public void home () {
		homeing = true;
	}
	private void replace() {
		super.forget();
	}
	@Override
	public void forget () {
		super.forget();
		copyTriangle.inHand = true;
	}
}
