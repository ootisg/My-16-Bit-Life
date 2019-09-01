package players;

import weapons.AimableWeapon;
import weapons.Unarmed;
import weapons.redBlackPaintBallGun;
import items.Inventory;
import items.RedBlackPaintBall;
import main.GameObject;
import main.GameWindow;
import main.GameLoop;
import map.MapTile;
import map.Room;
import projectiles.Paintball;
import resources.Sprite;

public class Jeffrey extends GameObject {
	
	public static final Sprite jeffreyIdle = new Sprite ("resources/sprites/config/jeffrey_idle.txt");
	public static final Sprite jeffreyWalking = new Sprite ("resources/sprites/config/jeffrey_walking.txt");
	public static final Sprite jeffreyIdlePoisoned = new Sprite ("resources/sprites/config/jeffrey_idle_poisoned.txt");
	public static final Sprite jeffreyWalkingPoisoned = new Sprite ("resources/sprites/config/jeffrey_walking_poisoned.txt");
	
	public double health;
	public double maxHealth;
	private boolean isWalking;
	public  boolean isJumping;
	public Sprite standSprite;
	public Sprite walkSprite;
	private AimableWeapon wpn;
	private int invulTimer;
	private int specialCooldown;
	public static double vx;
	public static double vy;
	private double ax;
	private double ay;
	public static boolean onLadder;
	public Inventory inventory;
	public static boolean standingOnPlatform;
	public boolean binded;
	private int index;
	private boolean newWeapon;
	public Jeffrey () {
		//This class is not yet commented
		this.declare (0, 0);
		index = 0;
		inventory = new Inventory();
		inventory.addWeapon (new redBlackPaintBallGun (redBlackPaintBallGun.gunSprite));
		standingOnPlatform = false;
		this.standSprite = jeffreyIdle;
		this.walkSprite = jeffreyWalking;
		setSprite (walkSprite);
		getAnimationHandler ().setFrameTime (47.62);
		setHitboxAttributes (4, 4, 7, 27);
		this.specialCooldown = 0;
		this.health = 100;
		this.maxHealth = 100;
		this.invulTimer = 0;
		this.vx = 0;
		this.vy = 0;
		this.ax = 0;
		this.ay = 0;
		binded = false;
		newWeapon = true;
	}
public AimableWeapon getWeapon () {
	if (newWeapon) {
		wpn = (AimableWeapon) inventory.findWeaponAtIndex(index);
		wpn.declare(0, 0);
		newWeapon = false;
	}
	return (AimableWeapon) inventory.findWeaponAtIndex(index);	
	}
	@Override
	public void frameEvent () {
		if (keyPressed ('Z')) {
			wpn.forget();
			newWeapon = true;
			index = index + 1;
			if (index > inventory.amountOfWeapons()) {
				System.out.println (index);
				index = 0;
			}
			//System.out.println (getWeapon ());
		}
		//Handles weapon usagewh
		//Gravity and collision with floor
		if (!binded) {
		if (keyDown (32) && !isJumping && vy == 0 && !onLadder) {
			isJumping = true;
			vy = -10.15625;
			setSprite (walkSprite);
			getAnimationHandler ().setFrameTime (0);
			getAnimationHandler ().setAnimationFrame (3);
		}
		}
		if (vy == 0) {
			getAnimationHandler ().setFrameTime (47.62);
		}
		if (!onLadder) {
			if (!standingOnPlatform) {
		vy += Room.getGravity ();
			}
		}
		if (vy > 15.0) {
			vy = 15.0;
		}
		setY (getY () + (int) Math.ceil (vy));
		if (Room.isColliding (this.hitbox ())) {
			vy = 0;
			double fc = .2; //Friction coefficient
			if (vx > 0) {
				vx -= fc;
				if (vx < 0) {
					vx = 0;
				}
			} else if (vx < 0) {
				vx += fc;
				if (vx > 0) {
					vx = 0;
				}
			}
			MapTile[] collidingTiles = Room.getCollidingTiles (this.hitbox ());
			this.setY (this.getY() + vy);
			for (int i = 0; i < collidingTiles.length; i ++) {
			    if (getY () + 32 >= collidingTiles [i].y && getY () + 32 <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y - 32);
			        this.vy = 0;
			        isJumping = false;
			        break;
			    }
			    if (getY () >= collidingTiles [i].y && getY () <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y + 16 - this.getHitboxYOffset ());
			        break;
			    }
			}
		}
		if (!onLadder) {
		if (!binded) {
		if (keyDown ('A')) {
			if (vx >= -3.0) {
				ax = -.5;
			}
			getAnimationHandler ().setFlipHorizontal (true);
			if (vy == 0 && !isWalking) {
				isWalking = true;
				setSprite (walkSprite);
			}
		} else if (keyDown ('D')) {
			if (vx <= 3.0) {
				ax = .5;
			}
			getAnimationHandler ().setFlipHorizontal (false);
			if (vy == 0 && !isWalking) {
				isWalking = true;
				setSprite (walkSprite);
			}
		} else {
			if (isWalking) {
				isWalking = false;
				setSprite (standSprite);
			}
			if (!isJumping && vy == 0) {
				isJumping = false;
				setSprite (standSprite);
			}
		}
		}
		}
		vx = vx + ax;
		vy = vy + ay;
		ax = 0;
		this.setX (this.getX () + vx);
		if (Room.isColliding (this.hitbox ())) {
			this.setX (getXPrevious ());
			vx = 0;
		}
		double x = this.getX ();
		double y = this.getY ();
		int viewX = Room.getViewX ();
		int viewY = Room.getViewY ();
		if (y - viewY >= 320 && y - 320 < Room.getHeight () * 16 - 480) {
			viewY = (int) y - 320;
			Room.setView (Room.getViewX (), viewY);
		}
		if (y - viewY <= 160 && y - 160 > 0) {
			viewY = (int) y - 160;
			Room.setView (Room.getViewX (), viewY);
		}
		if (x - viewX >= 427 && x - 427 < Room.getWidth () * 16 - 640) {
			viewX = (int) x - 427;
			Room.setView (viewX, Room.getViewY ());
		}
		if (x - viewX <= 213 && x - 213 > 0) {
			viewX = (int) x - 213;
			Room.setView (viewX, Room.getViewY ());
		}
	
		//Damage animation
		if (invulTimer != 0) {
			if ((invulTimer / 2) % 2 == 1) {
				//TODO these methods are missing
				//this.hide ();
			} else {
				//this.show ();
			}
		}
		if (getAnimationHandler ().flipHorizontal ()) {
			this.getWeapon().setX (this.getX () - 5);
			this.getWeapon().setY (this.getY () + 16);
			this.getWeapon().getAnimationHandler ().setFlipHorizontal (true);
		} else {
			this.getWeapon().setX (this.getX () + 11);
			this.getWeapon().setY (this.getY () + 16);
			this.getWeapon().getAnimationHandler ().setFlipHorizontal (false);
		}
		//Handles weapon aiming
		double wpnX = this.getWeapon().getX () - Room.getViewX ();
		double wpnY = this.getWeapon().getY () - Room.getViewY ();
		int mouseX = this.getCursorX ();
		int mouseY = this.getCursorY ();
		/*int wpnX = 32;
		int wpnY = 32;
		int mouseX = 64;
		int mouseY = 48;*/
		if (wpnX - mouseX != 0) {
			double ang = Math.atan ((wpnY - mouseY) / (wpnX - mouseX));
			if (mouseX < wpnX) {
				ang *= -1;
				if (ang < -Math.PI / 4) {
					ang = - Math.PI / 4;
				}
				if (ang > Math.PI / 4) {
					ang = Math.PI / 4;
				}
			} else {
				if (ang < -Math.PI / 4) {
					ang = - Math.PI / 4;
				}
				if (ang > Math.PI / 4) {
					ang = Math.PI / 4;
				}
			}
			this.getWeapon().setRotation (ang);
		}
		//Handles invulnerability
		if (invulTimer > 0) {
			invulTimer --;
		}
		if (this.health <= 0) {
			this.health = this.maxHealth;
			//We miiiiight need a consle. Maybe.
			//GameLoop.getConsole ().enable ("You died, and I'm too lazy to put anything in for that. :P");
		}
	}
	public void damage (double baseDamage) {
		if (invulTimer == 0) {
			health -= baseDamage;
			invulTimer = 15;
		}
	}
	public Inventory getInventory () {
		return this.inventory;
	}
	public double getHealth () {
		return this.health;
	}
	@Override
	public void forget () {
		
	}
}