package players;

import weapons.AimableWeapon;
import weapons.SlimeSword;
import weapons.Unarmed;
import weapons.redBlackPaintBallGun;
import items.Inventory;
import items.Item;
import items.RedBlackPaintBall;
import main.GameObject;
import main.GameWindow;
import main.InputManager;
import main.RenderLoop;
import gui.Tbox;
import gui.Textbox;
import map.MapTile;
import map.Room;
import projectiles.Paintball;
import resources.Sprite;
import statusEffect.Status;

public class Jeffrey extends GameObject {
	public double jeffreyHealth;
	public double maxHealth;
	private boolean isWalking;
	public  boolean isJumping;
	public Sprite standSprite;
	public Sprite walkSprite;
	private Item wpn;
	public boolean bindLeft;
	public boolean bindRight;
	private int invulTimer;
	private int specialCooldown;
	public  double vx;
	public double vy;
	private double ax;
	private double ay;
	InputManager manager;
	public boolean onLadder;
	public Inventory inventory;
	public boolean standingOnPlatform;
	public boolean binded;
	private int index;
	private boolean newWeapon;
	public boolean canSwitch;
	public int witchCharictar;
	public double samHealth;
	private boolean changeSprite;
	public Sprite samSword;
	public Sprite samWalkingSword;
	Sprite jeffreyWalking;
	public Sprite samIdle;
	public Sprite jeffreyIdle;
	public Sprite samWalking;
	public int switchTimer;
	private Tbox textbox;
	private Tbox weaponBox;
	private boolean activeBox;
	private int boxTimer;
	public Status status;
	public Jeffrey () {
		//This class is not yet commented
		this.declare (0, 0);
		index = 0;
		changeSprite = true;;
		samSword = new Sprite ("resources/sprites/config/sam_idle_with_sword.txt");
		samWalkingSword = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
		samIdle = new Sprite ("resources/sprites/config/sam_idle.txt");
		jeffreyIdle = new Sprite("resources/sprites/config/jeffrey_idle.txt");
		jeffreyWalking = new Sprite ("resources/sprites/config/jeffrey_walking.txt");
		samWalking = new Sprite ("resources/sprites/config/sam_walking.txt");
		inventory = new Inventory();
		standingOnPlatform = false;
		this.standSprite = new Sprite("resources/sprites/config/jeffrey_idle.txt");
		this.walkSprite = new Sprite("resources/sprites/config/jeffrey_walking.txt");
		setSprite (standSprite);
		getAnimationHandler ().setFrameTime (50);
		this.setHitboxAttributes(4, 4, 7, 27);
		this.specialCooldown = 0;
		this.jeffreyHealth = 100;
		this.samHealth = 100;
		bindLeft = false;
		bindRight = false;
		this.maxHealth = 100;
		this.invulTimer = 0;
		this.vx = 0;
		this.vy = 0;
		this.ax = 0;
		this.ay = 0;
		binded = false;
		newWeapon = true;
		witchCharictar = 0;
		status = new Status ();
		switchTimer = 0;
		activeBox = false;
		boxTimer = 0;
	}
	//makes the players sprite only be changed by outside sources not by this class
	public void changeSprite (boolean toChangeOrNotToChange) {
		changeSprite = toChangeOrNotToChange;
	}
public Item getWeapon () {
	if (newWeapon) {
		wpn =  inventory.findWeaponAtIndex(index, witchCharictar);
		wpn.declare(0, 0);
if (activeBox) {
		weaponBox.forget();	
		}
		weaponBox = new Tbox (this.getX(),this.getY()- 10,25,1,wpn.checkName(), false);
		weaponBox.setScrollRate(0);
		newWeapon = false;
		activeBox = true;
	}
	return inventory.findWeaponAtIndex(index, witchCharictar);	
	}
	@Override
	public void frameEvent () {
		if (activeBox) {
		weaponBox.setX(this.getX() - Room.getViewX());
		weaponBox.setY(this.getY() - 10);
		boxTimer = boxTimer + 1;
		}
		if (boxTimer == 30) {
		weaponBox.forget();	
		activeBox = false;
		boxTimer = 0;
		}
		if (keyDown('Q')) {
			switchTimer = switchTimer + 1;
		}
		if (!keyDown('Q')) {
			switchTimer = 0;
		}
		if (switchTimer == 30) {
			switchTimer = 0;
			if (witchCharictar == 1) {
				if (jeffreyHealth <= 0) {
					textbox = new Tbox (this.getX(), 340, 25, 8, "JEFFREY HAS NO HEALTH YOU ABSOLUTE MORRON (SEE ITS FUNNY TO ME THAT YOU THOUGHT YOU COULD SWITCH TO JEFFREY BUT IN REALITY YOUR JUST A HUGE MORON)" , false);
					textbox.declare(0,0);
				} else {
					witchCharictar = 0;
				}
			} else {
				if (samHealth <= 0) {
					textbox = new Tbox (this.getX(), 340, 25, 8, "LUL NO ... SAM HAS NO HP" , false);
					textbox.declare(0,0);
				} else {
					witchCharictar = 1;
				}
			}
			if (index > inventory.amountOfWeapons(witchCharictar)) {
				index = 0;
			}
		}
		if (witchCharictar == 0 && (!this.getSprite().equals(jeffreyIdle)) || !this.getSprite().equals(jeffreyWalking))  {
			if (!status.checkStatus(0, 0)) {
			standSprite = jeffreyIdle;
			walkSprite = jeffreyWalking;
			}
		}
		if (witchCharictar == 1 && (!this.getSprite().equals(samIdle) || !this.getSprite().equals(samWalking))) {
			standSprite = samIdle;
			walkSprite = samWalking;
			if (this.getWeapon().getClass().getSimpleName().equals("SlimeSword")) {
			standSprite = samSword;
			this.getWeapon().frameEvent();
			walkSprite = samWalkingSword;
			}
		}
		
		if (keyPressed ('Z')) {
			wpn.forget();
			newWeapon = true;
			index = index + 1;
			if (index > inventory.amountOfWeapons(witchCharictar)) {
				index = 0;
			}
		}
		//Handles weapon usage
		//Gravity and collision with floor
		if (!binded) {
		if (keyDown(32) && !isJumping && vy == 0 && !onLadder) {
			isJumping = true;
			vy = -10.15625;
			if (changeSprite) {
			setSprite (walkSprite);
			getAnimationHandler ().setFrameTime (0);
			getAnimationHandler ().setAnimationFrame (3);
			}
		}
		}
		if (vy == 0) {
			getAnimationHandler ().setFrameTime (50);
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
		if (keyDown ('A') && !bindLeft) {
			if (vx >= -3.0) {
				ax = -.5;
			}
			this.getAnimationHandler().setFlipHorizontal (true);
			if (vy == 0 && !isWalking) {
				isWalking = true;
				if (changeSprite) {
				setSprite (walkSprite);
				}
			}
		} else if (keyDown ('D') && !bindRight) {
			if (vx <= 3.0) {
				ax = .5;
			}
			this.getAnimationHandler().setFlipHorizontal (false);
			if (vy == 0 && !isWalking) {
				isWalking = true;
				if (changeSprite) {
				setSprite (walkSprite);
				}
			}
		} else {
			if (isWalking) {
				isWalking = false;
				if(changeSprite) {
				setSprite (standSprite);
				}
			}
			if (!isJumping && vy == 0) {
				isJumping = false;
				if (changeSprite) {
				setSprite (standSprite);
				}
			}
		}
		}
		}
		vx = vx + ax;
		vy = vy + ay;
		ax = 0;
		this.setX (this.getX () + vx);
		if (Room.isColliding (this.hitbox ())) {
			//not sure if this is gonna work but whatever m8
			this.setX(this.getXPrevious());
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
		// it went caput for now
		//if (invulTimer != 0) {
			//if ((invulTimer / 2) % 2 == 1) {
				//this.hide ();
			//} else {
				//this.show ();
			//}
		//}
		if (this.getAnimationHandler().flipHorizontal ()) {
			this.getWeapon().setX (this.getX () - 5);
			this.getWeapon().setY (this.getY () + 16);
			this.getWeapon().getAnimationHandler().setFlipHorizontal (true);
		} else {
			this.getWeapon().setX (this.getX () + 11);
			this.getWeapon().setY (this.getY () + 16);
			this.getWeapon().getAnimationHandler().setFlipHorizontal (false);
		}
		try {
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
			GameWindow wind = RenderLoop.window;
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
			AimableWeapon weapon = (AimableWeapon) this.getWeapon();
			weapon.setRotation (ang);
		}
		} catch (ClassCastException e) {
			
		}
		//Handles invulnerability
		if (invulTimer > 0) {
			invulTimer --;
		}
		if (this.jeffreyHealth <= 0 && this.samHealth <= 0) {
			this.jeffreyHealth = this.maxHealth;
			//not sure how to enable console now
			//MainLoop.getConsole ().enable ("You died, and I'm too lazy to put anything in for that. :P");
		}
		if (this.jeffreyHealth <= 0) {
			witchCharictar = 1;
		}
		if (this.samHealth <= 0) {
			witchCharictar = 0;
		}
	}
	public void damage (double baseDamage) {
		switchTimer = 0;
		if (invulTimer == 0) {
			if (witchCharictar == 0) {
			jeffreyHealth -= baseDamage;
			}
			if (witchCharictar == 1) {
			samHealth -= baseDamage;	
			}
			invulTimer = 15;
		}
	}
	public Inventory getInventory () {
		return this.inventory;
	}
	public double getHealth () {
		if (witchCharictar == 0) {
		return this.jeffreyHealth;
		} else {
		return this.samHealth;
		}
	}
	@Override
	public void forget () {
		
	}
}