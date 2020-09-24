package projectiles;

import main.GameObject;
import main.ObjectHandler;
import map.Room;

import java.util.Random;

import items.PopcornKernel;
import main.GameLoop;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;
import weapons.Bombs;


public class BombsProjectile extends Projectile{
	
	double direction;
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	public static final Sprite medBomb = new Sprite ("resources/sprites/config/bomb_active_red.txt");
	public static final Sprite kaboomBomb = new Sprite ("resources/sprites/config/bomb_active_superred.txt");
	boolean popcorn = false;
	public int timer = 0;
	double vx = 0;
	boolean fakeDirection = false;
	double vy = 0;
	boolean inzialaized = false;
	boolean thrown = false;
	public static Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	public BombsProjectile (boolean popCorn){
		popcorn = popCorn;
		if (!popcorn){
			this.setSprite(newBomb);
		} else {
			this.setSprite(new Sprite("resources/sprites/config/Popcorn_bag.txt"));
		}
		this.getAnimationHandler().setFrameTime(100);
		this.setHitboxAttributes(0, 0, 14, 18);
		this.setSpeed(0);
		this.setDirection(direction);
		vx = 12;
		vy = 12;
	}
	@Override
	public void projectileFrame (){
		//Velocity stuff. 

		if (thrown) {
			if (fakeDirection) {
				vx = vx - 0.3;
				if (vx < 0) {
					vx = 0;
				}
				if (!inzialaized) {
					vy = -vy * this.getDirection();
					inzialaized = true;
				}
			} else {
				vx = vx + 0.3;
				if (vx > 0) {
					vx = 0;
				}
				if (!inzialaized) {
					vy = vy * (this.getDirection() - 3.14);
					inzialaized = true;
				}
			}
			if (this.checkXandY(this.getX() + (vx / 3), this.getY() - (vy / 3)) ) {
				this.goY(this.getY() - (vy / 3));
				this.goX(this.getX() + (vx / 3));
				vy--;
			} else {
				if (this.checkX(this.getX() + (vx / 3))) {
					if (vy > 0) {
						vy = vy * -1;
						vx = vx * -1;
						fakeDirection = !fakeDirection;
					}
				}
				if (this.checkY(this.getY() - (vy / 3))) {
					vx = vx * -1;
					fakeDirection = !fakeDirection;
				}
			}
		}
		if (popcorn) {
			int luck = 120 - timer;
			if (luck <= 0) {
				luck = 1;
			}
			for (int i = 0; i <10; i++) {
			Random rand = new Random ();
			if (rand.nextInt(luck) == 0) {
				double direction = rand.nextInt(3) + rand.nextDouble() + 3;
				PopcornKernel kernel = new PopcornKernel ();
				kernel.setDirection(direction);
				kernel.setSpeed(10);
				kernel.declare(this.getX() - 8,this.getY() - 8);
			}
		}
		}
		//Explosion timer. When switching the sprite, their animations stop. I'm 90% sure config files are good, so idk whats going on.
		timer++;
		if (!popcorn) {
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
		}
		if (timer > 120) {
			if (popcorn) {
				ExpolsionPopcorn explosionPopcorn = new ExpolsionPopcorn(10, 60, true, true);
				 explosionPopcorn.declare(this.getX() - 8,this.getY() - 8);
			} else {
				switch (Bombs.getTierInfoStaticly()[2]) {
				case 0:
					Explosion explosion = new Explosion(10, 60, true, true);
					explosion.declare(this.getX() - 8,this.getY() - 8);
					break;
				case 1:
					ExpolsionShrapnul shrap = new ExpolsionShrapnul(10, 60, true, true,0);
					shrap.declare(this.getX() - 8,this.getY() - 8);
					break;
				case 2:
					ExpolsionShrapnul shraps = new ExpolsionShrapnul(10, 60, true, true,1);
					shraps.declare(this.getX() - 8,this.getY() - 8);
					break;
				case 3:
					ExpolsionShrapnul shrapss = new ExpolsionShrapnul(10, 60, true, true,2);
					shrapss.declare(this.getX() - 8,this.getY() - 8);
					break;
				}
				
			}
			this.forget();
		}
	}
	public double [] computeLandPoint (double positionX, double positionY, double direction, double workingVx, double workingVy, boolean workingFakeDireciton) {
		int fakeTimer = timer;
		double fakeVx = workingVx;
		double fakeVy;
		double workingX = positionX;
		double workingY = positionY;
		Sprite lightBall = new Sprite ("resources/sprites/lightBall.png");
		Sprite lightBallRed = new Sprite ("resources/sprites/lightBall_red.png");
		Sprite lightBallOrange = new Sprite ("resources/sprites/lightBall_orange.png");
		Sprite lightBallBlue = new Sprite ("resources/sprites/lightBall_blue.png");
		Sprite lightBallGreen = new Sprite ("resources/sprites/lightBall_green.png");
		Sprite activeLightBall = lightBall;
		if (workingFakeDireciton) {
			fakeVy = -workingVy * direction;
		} else {
			fakeVy = workingVy * (direction - 3.14);
		}
		while (fakeTimer < 120) {
			if (workingFakeDireciton) {
				fakeVx = fakeVx - 0.3;
				if (fakeVx < 0) {
					fakeVx = 0;
				}
			} else {
				fakeVx = fakeVx + 0.3;
				if (fakeVx > 0) {
					fakeVx = 0;
				}
			}

			if (this.checkXandY(workingX,workingY)) {
				
				workingY = workingY - (fakeVy / 3);
				workingX = workingX + (fakeVx / 3);
				AfterRenderDrawer.drawAfterRender((int)workingX - Room.getViewX(), (int)workingY - Room.getViewY(), activeLightBall);
				fakeVy--;
			} else {
				double oldX = this.getX();
				double oldY = this.getY();
				this.setY(workingY + (fakeVy / 3));
				if (this.checkX(workingX)) {
					if (fakeVy > 0) {
						fakeVy = fakeVy * -1;
						workingY = workingY - (fakeVy / 3);
						fakeVx = fakeVx * -1;
						workingFakeDireciton = !workingFakeDireciton;
						if (activeLightBall.equals(lightBallGreen)) {
							activeLightBall = lightBallBlue;
							}
						if (activeLightBall.equals(lightBallRed)) {
							activeLightBall = lightBallGreen;
							}
						if (activeLightBall.equals(lightBallOrange)) {
							activeLightBall = lightBallRed;
							}
						if (activeLightBall.equals(lightBall)) {
							activeLightBall = lightBallOrange;
							}
						}
					AfterRenderDrawer.drawAfterRender((int)workingX - Room.getViewX(), (int)workingY - Room.getViewY(), activeLightBall);
				}
				this.setY(oldY);
				this.setX(workingX - (fakeVx / 3));
				if (this.checkY(workingY)) {
					fakeVx = fakeVx * -1;
					workingX = workingX + (fakeVx / 3);
					workingFakeDireciton = !workingFakeDireciton;
					if (activeLightBall.equals(lightBallGreen)) {
						activeLightBall = lightBallBlue;
						}
					if (activeLightBall.equals(lightBallRed)) {
						activeLightBall = lightBallGreen;
						}
					if (activeLightBall.equals(lightBallOrange)) {
						activeLightBall = lightBallRed;
						}
					if (activeLightBall.equals(lightBall)) {
						activeLightBall = lightBallOrange;
						}
					AfterRenderDrawer.drawAfterRender((int)workingX - Room.getViewX(), (int)workingY - Room.getViewY(), activeLightBall);
				}
				this.setX(oldX);
			}
			fakeTimer = fakeTimer + 1;
		}
		double [] results = new double [] {workingX,workingY};
		return results;
	}
	public void throwBomb () {
		thrown = true;
	}
	public void setVx (double newVx) {
		vx = newVx;
	}
	public void setVy (double newVy) {
		vy = newVy;
	}
	public void setFakeDireciton (boolean newDireciton) {
		fakeDirection = newDireciton;
	}
}

