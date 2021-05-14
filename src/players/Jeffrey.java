package players;

import weapons.AimableWeapon;
import weapons.MagicMicrophone;
import weapons.SlimeSword;
import weapons.Unarmed;
import weapons.redBlackPaintBallGun;
import items.Inventory;
import items.Item;
import items.RedBlackPaintBall;
import main.GameCode;
import main.GameObject;
import main.GameWindow;
import main.InputManager;
import main.ObjectHandler;
import main.RenderLoop;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import gui.Tbox;
import gui.Textbox;
import map.MapTile;
import map.Room;
import mapObjects.MapObject;
import projectiles.Paintball;
import resources.Sprite;
import statusEffect.Status;
import triggers.Checkpoint;

public class Jeffrey extends GameObject {
	long lastFrame;
	public static double jeffreyHealth = 100;
	public static double samHealth = 100;
	public static double ryanHealth = 100;
	public static double maxJeffreyHealth = 100;
	public static double maxSamHealth = 150;
	public static double maxRyanHealth = 100;
	
	private Item wpn; 
	private boolean newWeapon = true;
	private Tbox weaponBox;
	private static int index = 0;
	
	private boolean isWalking;
	public  boolean isJumping;
	private int invulTimer = 0;
	public boolean onLadder;
	public boolean standingOnPlatform = false;
	private boolean crouching = false;
	private boolean crouchElegable = true;
	private boolean constantSpeed = false;
	private boolean forceSpacebar = false;
	
	public boolean bindLeft = false;
	public boolean bindRight = false;
	public boolean binded = false;
	private  static boolean scrolling = true;
	private boolean changeSprite = true;
	boolean messWithFrameTime  = true;
	private boolean fallBruh = true;
	public static Status status = new Status ();
	
	
	public Sprite standSprite = new Sprite("resources/sprites/config/jeffrey_idle.txt");
	public Sprite walkSprite = new Sprite("resources/sprites/config/jeffrey_walking.txt");
	
	public boolean keepAdding = true;
	
	public  double vx = 0;
	public double vy = 0;
	public double trueVy=0;
	private ArrayList <Double> increments = new ArrayList<Double>();
	private double ax = 0;
	private double ay = 0;
	
	InputManager manager;
	public static Inventory inventory = new Inventory();
	
	public boolean canSwitch;
	public static int witchCharictar = 0;
	public int switchTimer = 0;
	public int nextCharacter = 0;
	private boolean [] party = new boolean [] {true,true,true};
	private static boolean [] fullParty = new boolean [] {false,false,false};
	public boolean active = false;
	private Tbox textbox;
	
	private boolean activeBox = false;
	private int boxTimer = 0;
	
	public boolean falldurringCollisionCheck = true;
	
	private boolean izialized = false;
	
	//Jeffrey Sprites
	public static final Sprite JEFFREY_WALKING = new Sprite ("resources/sprites/config/jeffrey_walking.txt");
	public static final Sprite JEFFREY_IDLE = new Sprite("resources/sprites/config/jeffrey_idle.txt");
	public static final Sprite JEFFREY_CROUCHING = new Sprite ("resources/sprites/config/crouching_Jeffrey.txt");
	public static final Sprite JEFFREY_WALKING_POGO = new Sprite ("resources/sprites/config/jeffrey_walking_pogo.txt");
	//Sam Sprites
	public static final Sprite SAM_IDLE =new Sprite ("resources/sprites/config/sam_idle.txt");
	public static final Sprite SAM_WALKING = new Sprite ("resources/sprites/config/sam_walking.txt");
	public static final Sprite SAM_CROUCHING = new Sprite ("resources/sprites/config/crouching_Sam.txt");
	public static final Sprite SAM_WALKING_POGO = new Sprite ("resources/sprites/config/sam_walking_pogo.txt");
	public static final Sprite SAM_SWORD = new Sprite ("resources/sprites/config/sam_idle_with_sword.txt");
	public static final Sprite SAM_WALKING_SWORD = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
	//Ryan Sprites
	public static final Sprite RYAN_IDLE = new Sprite ("resources/sprites/config/ryan_idle.txt");
	public static final Sprite RYAN_WALKING = new Sprite ("resources/sprites/config/ryan_walking.txt");
	public static final Sprite RYAN_CROUCHING = new Sprite ("resources/sprites/config/crouching_Ryan.txt");
	public static final Sprite RYAN_WALKING_POGO = new Sprite ("resources/sprites/config/ryan_walking_pogo.txt");	
	public static final Sprite RYAN_MICROPHONE_IDLE = new Sprite ("resources/sprites/config/ryan_idle_microphone.txt");
	public static final Sprite RYAN_MICROPHONE_WALKING = new Sprite("resources/sprites/config/ryan_walking_microphone.txt");
	public static final Sprite RYAN_WHIPPING = new Sprite ("resources/sprites/config/microphoneWhip.txt");
	public static final Sprite WHIP_LENGTH = new Sprite ("resources/sprites/config/microphoneWhipVariableFrame.txt");
	
	public static final double TERMINAL_VELOCITY = 15;
	public static final double JUMP_VELOCITY = 12.15625;
	//note if you ever plan on using the s key and sprites with this class you are gonna have to have your class have a lower game logic priority (witch is actually higher becasue its stupid)
	
	public Jeffrey () {
		//This class is not yet commented
		setSprite (standSprite);
		getAnimationHandler ().setFrameTime (50);
		this.setHitboxAttributes(4, 4, 7, 27);
		this.setGameLogicPriority(-2);
		this.setHitboxRounding(true);
		this.adjustHitboxBorders();
		this.setPusablity(true);
	}
	//makes the players sprite only be changed by outside sources not by this class
	public void changeSprite (boolean toChangeOrNotToChange) {
		changeSprite = toChangeOrNotToChange;
	}
	public void changeFrameTime (boolean toChangeOrNotToChange) {
		messWithFrameTime = toChangeOrNotToChange;
	}
	public void allowSpriteEdits (boolean allowOrNot) {
		changeSprite = allowOrNot;
		messWithFrameTime = allowOrNot;
	}
	public boolean isCrouched () {
		return crouching;
	}
	public Item getWeapon () {
		if (newWeapon) {
			wpn =  inventory.findWeaponAtIndex(index, witchCharictar);
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
public void setWeapon (Item weapon) {
	wpn = weapon;
}
	
	public void inzialize () {
		wpn = this.getWeapon();
		if (this.getVariantAttribute("Jeffrey") != null) {
			if (this.getVariantAttribute("Jeffrey").equals("no")) {
				party[0] = false;
			}
		}
		if (this.getVariantAttribute("Sam") != null) {
			if (this.getVariantAttribute("Sam").equals("no")) {
				party[1] = false;
			}
		}
		if (this.getVariantAttribute("Ryan") != null) {
			if (this.getVariantAttribute("Ryan").equals("no")) {
				party[2] = false;
			}
		}
		if (this.getVariantAttribute("Active") != null) {
			if (this.getVariantAttribute("Active").equals("yes")) {
				active = true;
				this.inzializeCamera();
			}
		} else {
			if ( ObjectHandler.getObjectsByName("Jeffrey").size() == 1) {
				active = true;
				this.inzializeCamera();
			}
		}
		for (int i = 0; i < party.length; i++) {
			if (party[i]) {
				fullParty[i] = true;
			}
		}
		
		if (!party[witchCharictar]) {
			this.switchToAPartyMember();
		}
		if (witchCharictar == 0) {
			this.setSprite(JEFFREY_IDLE);
		} else if (witchCharictar == 1) {
				this.setSprite(SAM_IDLE);
			} else {
				this.setSprite(RYAN_IDLE);
		}
		izialized = true;
	}
	@Override
	public void frameEvent () {
		if (!izialized) {
			this.inzialize();
		}
		if (active) {
			if (keyDown ('S') && !onLadder && crouchElegable && this.getX() - this.getSpriteX() == 0) {
				crouching = true;
				if (this.changeSprite) {
				if (!(this.getSprite().equals(JEFFREY_CROUCHING) || this.getSprite().equals(SAM_CROUCHING) || this.getSprite().equals(RYAN_CROUCHING))) {
					switch (witchCharictar){
					case 0:
						this.setSprite(JEFFREY_CROUCHING);
						break;
					case 1:
						this.setSprite(SAM_CROUCHING);
						break;
					case 2:
						this.setSprite(RYAN_CROUCHING);
						break;
					}
				}
					this.changeSprite(false);
					this.getAnimationHandler().setRepeat(false);
					this.setHitboxRounding(false);
				}
				if (this.getAnimationHandler().getFrame() == 2) {
					this.setHitboxAttributes(4, 16, 8, 15);
				}
			} else {
				if (this.changeSprite) {
				if ((this.getSprite().equals(JEFFREY_CROUCHING) || this.getSprite().equals(SAM_CROUCHING) || this.getSprite().equals(RYAN_CROUCHING))) {
					switch (witchCharictar){
					case 0:
						this.setSprite(JEFFREY_WALKING);
						break;
					case 1:
						this.setSprite(SAM_WALKING);
						break;
					case 2:
						this.setSprite(RYAN_WALKING);
						break;
					}
				}
				}
				if (crouching) {
				this.changeSprite(true);
				this.setHitboxRounding(true);
				this.getAnimationHandler().setRepeat(true);
				this.setHitboxAttributes(4, 4, 7, 27);
				}
				crouching = false;
				
			}
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
			
			//Switch meter stuff
			if (keyDown('Q')) {
				if (this.checkSwitch()) {
					switchTimer++;
				}
			}
			/*if (!keyDown('Q')) {
				switchTimer = 0;
			}*/
			
			
		
			//The "tap Q to toggle" mechanic.
			if (switchTimer > 0 && switchTimer <=5 && !keyDown('Q')) {
				if (keepAdding) {
					keepAdding = false;
					nextCharacter++;
					switchTimer = 0;
					if (nextCharacter > 2) {
						nextCharacter = 0;
					}
					if (jeffreyHealth <= 0 && nextCharacter == 0) {
						nextCharacter++;
					}
					if (samHealth <= 0 && nextCharacter == 1) {
						nextCharacter++;
					}
					if (ryanHealth <= 0 && nextCharacter == 2) {
						nextCharacter++;
					}
					//Sets max number of characters to 3
					if (nextCharacter > 2) {
						nextCharacter = 0;
					}
					this.runSwitchCode();
				}
			} else {
				keepAdding = true;
				if (switchTimer > 5 && switchTimer < 30 && !keyDown('Q')) {
					switchTimer = 0;
				}
			}
			
			//Makes it so you cannot switch into the same character
			switch (nextCharacter) {
				case 0:
					if (witchCharictar == 0) {
						this.runSwitchCode();
					}
					break;
				case 1:
					if (witchCharictar == 1) {
						this.runSwitchCode();
					}
					break;
				case 2:
					if (witchCharictar == 2) {
						this.runSwitchCode();
					}
			}
			
			//System.out.println(nextCharacter);
			
			//Decides what character to switch into.
			if (switchTimer == 30) {
				this.runCharictarSwitchCode(nextCharacter);
				if (witchCharictar == 0) {
					if ((samHealth <= 0) && (ryanHealth <= 0)) {
						textbox = new Tbox (this.getX(), 340, 25, 8, "LUL NO ... SAM AND RYAN HAVE NO HP" , false);
						//the Room.getVeiwY may need to be minus
						textbox.declare(this.getX() - Room.getViewX(),this.getY() - 10 + Room.getViewY());
						switchTimer = 0;
					}
				}
				
				if ((witchCharictar == 1) && (switchTimer != 0)) {
					if ((jeffreyHealth <= 0) && (ryanHealth <= 0)) {
						textbox = new Tbox (this.getX(), 340, 25, 8, "JEFFREY AND RYAN HAVE NO HEALTH YOU ABSOLUTE MORRON (SEE ITS FUNNY TO ME THAT YOU THOUGHT YOU COULD SWITCH TO JEFFREY OR RYAN BUT IN REALITY YOUR JUST A HUGE MORON)" , false);
						//the Room.getVeiwY may need to be minus
						textbox.declare(this.getX() - Room.getViewX(),this.getY() - 10 + Room.getViewY());
						switchTimer = 0;
					}
				}
				
				if ((witchCharictar == 2) && (switchTimer != 0)) {
					if ((jeffreyHealth <= 0) && (samHealth <= 0)) {
						textbox = new Tbox (this.getX(), 340, 25, 8, "SAM AND JEFFREY GOT NOSCOPED ITS UP TO RYAN NOW" , false);
						//the Room.getVeiwY may need to be minus
						textbox.declare(this.getX() - Room.getViewX(),this.getY() - 10 + Room.getViewY());
						switchTimer = 0;
					}
				}
				
				if (index > inventory.amountOfWeapons(witchCharictar)) {
					index = 0;
				}
			}
			if (active) {
			if (witchCharictar == 0 && (!this.getSprite().equals(JEFFREY_IDLE)) || !this.getSprite().equals(JEFFREY_WALKING))  {
				if (!status.checkStatus(0, 0)) {
				standSprite = JEFFREY_IDLE;
				walkSprite = JEFFREY_WALKING;
				}
			}
			if (witchCharictar == 2 && ((!this.getSprite().equals(RYAN_IDLE)) || !this.getSprite().equals(RYAN_WALKING)) )  {
				standSprite = RYAN_IDLE;
				walkSprite = RYAN_WALKING;
				if (isWalking && !this.getSprite().equals(walkSprite) && !this.getWeapon().getClass().getSimpleName().equals("MagicMicrophone")) {
					this.setSprite(walkSprite);
				}
				if (this.getWeapon().getClass().getSimpleName().equals("MagicMicrophone")) {
					standSprite = RYAN_MICROPHONE_IDLE;
					if (!crouching) {
					walkSprite = RYAN_MICROPHONE_WALKING;
					if (isWalking && !this.getSprite().equals(walkSprite) && changeSprite) {
						this.setSprite(walkSprite);
					}
					}
					}
			}
			if (witchCharictar == 1 && (!this.getSprite().equals(SAM_IDLE) || !this.getSprite().equals(SAM_WALKING))) {
				standSprite = SAM_IDLE;
				walkSprite = SAM_WALKING;
				if (!crouching) {
				if (isWalking && !this.getSprite().equals(walkSprite) && !this.getWeapon().getClass().getSimpleName().equals("SlimeSword")) {
					this.setSprite(walkSprite);
				}
				if (this.getWeapon().getClass().getSimpleName().equals("SlimeSword")) {
				standSprite = SAM_SWORD;
				
				walkSprite = SAM_WALKING_SWORD;
				if (isWalking && !this.getSprite().equals(walkSprite) && changeSprite) {
					this.setSprite(walkSprite);
				}
				}
				}
			}
			
			if (keyPressed ('Z')) {
				wpn.onSwitch();
				newWeapon = true;
				index = index + 1;
				if (index > inventory.amountOfWeapons(witchCharictar)) {
					index = 0;
				}
			}
			//Handles weapon usage
			//Gravity and collision with floor
			if (!binded) {
			if (keyDown(32) && !isJumping && vy == 0 && !forceSpacebar) {
				
				isJumping = true;
				trueVy = -JUMP_VELOCITY;
				vy = -JUMP_VELOCITY;
				if (changeSprite) {
				setSprite (walkSprite);
				}
				if (messWithFrameTime && this.getSprite().equals(walkSprite)) {
					getAnimationHandler ().setFrameTime (0);
				}
				if (this.getSprite().equals(walkSprite)) {
					getAnimationHandler ().setAnimationFrame (3);
				}
			}
			}
		
			if (!onLadder) {
			if (!binded) {
			if (keyDown ('A') && !bindLeft && (!crouching || vy != 0)) {
				if ((vx >= -3.0 && !this.checkIfSlow() && !this.checkIfFast()) || (vx >= -3.0 && this.checkIfSlow() && this.checkIfFast())|| (vx >= -4.0 && !this.checkIfSlow() && this.checkIfFast())|| (vx >= -2.0 && this.checkIfSlow() && !this.checkIfFast())) {
					if (this.checkIfSlow() && !this.checkIfFast()) {
						ax = -.3;
					} else {
					if (this.checkIfFast()) {
					ax = -.7;	
					} else {
					ax = -.5;
					}
					}
				}
			
				if (!this.getAnimationHandler().flipHorizontal()) {
					this.getWeapon().onFlip();
				}
				this.getAnimationHandler().setFlipHorizontal (true);
				if (vy == 0 && !isWalking) {
					isWalking = true;
					if (changeSprite) {
					setSprite (walkSprite);
					}
				}
			} else if (keyDown ('D') && !bindRight && (!crouching || vy != 0)) {
				if ((vx <= 3.0 && !this.checkIfSlow() && !this.checkIfFast()) || (vx <= 3.0 && this.checkIfSlow() && this.checkIfFast())|| (vx <= 4.0 && !this.checkIfSlow() && this.checkIfFast())|| (vx <= 2.0 && this.checkIfSlow() && !this.checkIfFast())) {
					if (this.checkIfSlow() && !this.checkIfFast()) {
						ax = .3;
					} else {
					if (this.checkIfFast()) {
					ax = .7;	
					} else {
					ax = .5;
					}
					}
				}
				if (this.getAnimationHandler().flipHorizontal()) {
					this.getWeapon().onFlip();
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
			if (scrolling) {
				double x = this.getX ();
				double y = this.getY ();
				
				int viewX = Room.getViewX ();
				int viewY = Room.getViewY ();
				
				double edgeRight = RenderLoop.window.getResolution()[0] * 0.4447916666; // 427/960
				double edgeLeft = RenderLoop.window.getResolution()[0] * 0.221875; // 213/960
				double edgeTop = RenderLoop.window.getResolution()[1] * 0.296296296; // 160/540
				double edgeBottom = RenderLoop.window.getResolution()[1] * 0.592592592; // 320/540
				
				
				double drawBoundY = RenderLoop.window.getResolution()[1] * 0.888888888; //480/540
				double drawBoundX = RenderLoop.window.getResolution()[0] * 0.666666666; //640/960
				
				
				if (y - viewY >= edgeBottom || y - edgeBottom > (Room.getHeight () * 16) - drawBoundY) {
					viewY = (int) (y - edgeBottom);
					Room.setView (Room.getViewXAcurate (), viewY);
				}
				if (y - viewY <= edgeTop && y - edgeTop > 0) {
					viewY = (int) (y - edgeTop);
					Room.setView (Room.getViewXAcurate (), viewY);
				}
				if (x - viewX >= edgeRight && x - edgeRight < (Room.getWidth () * 16) - drawBoundX) {
					viewX = (int) (x - edgeRight);
					Room.setView (viewX, Room.getViewYAcurate ());
				}
				
				if (x - viewX <= edgeLeft && x - edgeLeft  > 0) {
					viewX = (int) (x - edgeLeft);
					Room.setView (viewX, Room.getViewYAcurate ());
				}
			}
			//Damage animation
			if (invulTimer != 0) {
			if ((invulTimer / 2) % 2 == 1) {
					this.getAnimationHandler().hide ();
				} else {
					this.getAnimationHandler().show ();
				}
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
			if (Jeffrey.jeffreyHealth <= 0 && Jeffrey.samHealth <= 0 && Jeffrey.ryanHealth <= 0) {
				Jeffrey.jeffreyHealth = Jeffrey.maxJeffreyHealth;
				//MainLoop.getConsole ().enable ("You died, and I'm too lazy to put anything in for that. :P");
			}
			if (Jeffrey.jeffreyHealth <= 0 && witchCharictar == 0) {
				newWeapon = true;
				if (Jeffrey.samHealth > 0) {
				this.runCharictarSwitchCode(1);
				} else {
				if (Jeffrey.ryanHealth > 0) {
					this.runCharictarSwitchCode(2);
				}
				}
			}
			if (Jeffrey.samHealth <= 0 && witchCharictar == 1) {
				newWeapon = true;
				if (Jeffrey.ryanHealth > 0) {
					this.runCharictarSwitchCode(2);
				} else {
				if (Jeffrey.jeffreyHealth > 0) {
					this.runCharictarSwitchCode(0);
				}
				}
			}
			if (Jeffrey.ryanHealth <= 0 && witchCharictar == 2) {
				newWeapon = true;
				if (Jeffrey.jeffreyHealth > 0) {
					this.runCharictarSwitchCode(0);
				} else {
				if (Jeffrey.samHealth > 0) {
					this.runCharictarSwitchCode(1);
				}
				}
			}
		}
	}
		if (vy == 0) {
			falldurringCollisionCheck = false;
			if (messWithFrameTime) {
			getAnimationHandler ().setFrameTime (50);
			}
		}
		if (!onLadder) {
			if (!standingOnPlatform) {
		if (keyDown(32) || forceSpacebar) {
			vy += Room.getGravity ();
			trueVy += Room.getGravity();
		} else {
			vy += (Room.getGravity () + 0.5);
			trueVy += (Room.getGravity () + 0.5);
		}
			}
		}
		if (vy > TERMINAL_VELOCITY) {
			vy = TERMINAL_VELOCITY;
		}
		if (fallBruh) {
		setY (getY () + vy);
	}
		if (this.isActive()) {
			wpn.frameEvent();
		}
		
			if (Room.isColliding(this)) {
			vy = 0;
			trueVy = 0;
			forceSpacebar = false;
			increments.clear();
			double fc = .2; //Friction coefficient
			if (this.checkIfSlippery()) {
				fc = 0;
			}
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
			if (fallBruh) {
			MapTile[] collidingTiles = Room.getCollidingTiles (this);
			for (int i = 0; i < collidingTiles.length; i++) {
			    if (getY () + 32 >= collidingTiles [i].y && getY () + 32 <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y - 32);
			        this.vy = 0;
			        this.trueVy = 0;
			        isJumping = false;
			        break;
			    }
			    if (getY () >= collidingTiles [i].y && getY () <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y + 16 - this.getHitboxYOffset ());
			        break;
			    }
			}
		}
	}
		falldurringCollisionCheck = true;
		if (constantSpeed) {
			ax = 0;
			if (keyDown ('D') && !keyDown ('A')) {
				vx = 2;
			} 
			if (keyDown ('A') && !keyDown ('D')) {
				vx = -2;
			}
			if (!keyDown ('A') && !keyDown ('D')) {
				vx = 0;
			}
		}
		if (this.checkIfSlippery()) {
			if (ax < 0 && vx > 0) {
				vx = -vx;
			}
			if (ax > 0 && vx < 0) {
				vx = -vx;
			}
		}
		vx = vx + ax;
		vy = vy + ay;
		trueVy = trueVy + ay;
		ax = 0;
		if (vy >= 0) {
			increments.add(vy);
		}
		if (!this.goX (this.getX () + vx)) {
			vx = 0;
		}
	
}
	/**
	 * forces Jeffrey to act as if the spacebar is being held down until he lands on stable ground
	 */
	public void forceSpacebar () {
		forceSpacebar = true;
	}
	public void setVy (double newVy) {
		vy = newVy;
		trueVy = newVy;
	}
	public double getVy() {
		return vy;
	}
	/**
	 * returns a list of all the increments to Jeffreys speed durring a fall (note it is reset at the end of a fall so you have to get it at the right time if you want it)
	 * (I can already tell Imma get pissed at myself someday for designing it like that, but its really the only practical way to do it)
	 * @return it says it above
	 */
	public ArrayList<Double> getFallIncrementation(){
		return increments;
	}
	//for use ONLY in tile entitys durring thier does collide or onCollision the variable will not have an acurate value at any other time
	public boolean isFallingDurringCollision() {
		return 	falldurringCollisionCheck;
	}
	//returns the downward speed not effected by terminal velocity
	public double getTrueSpeedY() {
		return trueVy;
	}
	/**
	 * returns the speed you would need to give Jeffrey to make him reach the same height he started from (for use after a fall)
	 * @param a list of the incrementation to Jeffreys speed during the fall
	 * @return the speed to give Jeffrey if you want him to be able to reach his original height
	 */
	public static double downToUpSpeed (ArrayList<Double> incremenation) {
		double simulatedSpeed = 0;
		double simulatedDistance = 0;
		for (int i = 0; i < incremenation.size(); i++) {
			simulatedSpeed = simulatedSpeed += Room.getGravity();
			if (simulatedSpeed > 15) {
				simulatedSpeed = 15;
			}
			simulatedDistance = simulatedDistance + simulatedSpeed;
		}
		simulatedSpeed = 0;
		while (true) {
			simulatedSpeed = simulatedSpeed - (Room.getGravity());
			if (simulatedDistance <= 0) {
				break;
			}
			simulatedDistance = simulatedDistance + simulatedSpeed;
		}
		return simulatedSpeed;
	}
	/**
	 * use to find out how far jeffrey fell
	 * @return
	 */
	public static double getFallDistance (ArrayList<Double> incremenation) {
			double simulatedSpeed = 0;
			double simulatedDistance = 0;
			for (int i = 0; i < incremenation.size(); i++) {
				simulatedSpeed = simulatedSpeed += Room.getGravity();
				if (simulatedSpeed > 15) {
					simulatedSpeed = 15;
				}
				simulatedDistance = simulatedDistance + simulatedSpeed;
			}
			return simulatedDistance;
	}
	
	
	public void damage (double baseDamage) {
		switchTimer = 0;
		if (invulTimer == 0) {
			if (checkIfBrittle()) {
				baseDamage = baseDamage *1.2;
			}
			if (witchCharictar == 0) {
			jeffreyHealth -= baseDamage;
			}
			if (witchCharictar == 1) {
			samHealth -= baseDamage;	
			}
			if (witchCharictar == 2) {
			ryanHealth = ryanHealth - baseDamage;
			}
			invulTimer = 15;
		}
	}
	@Override
	public void checkpointCode (Checkpoint checkpoint) {
		this.setX(checkpoint.getX());
		this.setY(checkpoint.getY());
		
	}
	public static Inventory getInventory () {
		return Jeffrey.inventory;
	}
	public static void setInventory (Inventory newInventory) {
		Jeffrey.inventory = newInventory;
	}
	/** 
	 * make true to make Jeffrey move at a constant speed
	 */
	public void constantSpeed () {
		constantSpeed = true;
	}
	/**
	 * makes Jeffrey move at his normal speed
	 */
	public void normalSpeed () {
		constantSpeed = false;
	}
	public boolean checkIfBrittle() {
		if (witchCharictar == 0) {
			 return status.statusAppliedOnJeffrey[4];
		}
		if (witchCharictar == 1) {
			 return status.statusAppliedOnSam[4];
		}
		if (witchCharictar == 2) {
			 return status.statusAppliedOnRyan[4];
		}
		return false;
	}
	public boolean checkIfSlippery () {
		if (witchCharictar == 0) {
			 return status.statusAppliedOnJeffrey[8];
		}
		if (witchCharictar == 1) {
			 return status.statusAppliedOnSam[8];
		}
		if (witchCharictar == 2) {
			 return status.statusAppliedOnRyan[8];
		}
		return false;
		
	}
	public boolean checkIfSlow() {
		if (witchCharictar == 0) {
			 return status.statusAppliedOnJeffrey[3];
		}
		if (witchCharictar == 1) {
			 return status.statusAppliedOnSam[3];
		}
		if (witchCharictar == 2) {
			 return status.statusAppliedOnRyan[3];
		}
		return false;
	}
	public boolean checkIfFast() {
		if (witchCharictar == 0) {
			 return status.statusAppliedOnJeffrey[5];
		}
		if (witchCharictar == 1) {
			 return status.statusAppliedOnSam[5];
		}
		if (witchCharictar == 2) {
			 return status.statusAppliedOnRyan[5];
		}
		return false;
	}
	public boolean checkIfPowerful() {
		if (witchCharictar == 0) {
			 return status.statusAppliedOnJeffrey[6];
		}
		if (witchCharictar == 1) {
			 return status.statusAppliedOnSam[6];
		}
		if (witchCharictar == 2) {
			 return status.statusAppliedOnRyan[6];
		}
		return false;
	}
	public boolean checkIfSomeoneIsLemoney(int who) {
		if (who == 0) {
			return status.statusAppliedOnJeffrey[2];
		}
		if (who == 1) {
			return status.statusAppliedOnSam[2];
		}
		if (who == 2) {
			return status.statusAppliedOnRyan[2];
		}
		return false;
	}
	
	public double getHealth (int CharictarToCheck) {
		if (CharictarToCheck == 0) {
			return jeffreyHealth;
			} 
			if (CharictarToCheck == 1) {
			return samHealth;
			}
			return ryanHealth;
		}
	public double getHealth () {
		if (witchCharictar == 0) {
		return Jeffrey.jeffreyHealth;
		} 
		if (witchCharictar == 1) {
		return Jeffrey.samHealth;
		}
		return Jeffrey.ryanHealth;
	}
	
	//stops the charictar from falling for a bit
	public void stopFall(boolean fall) {
		fallBruh = !fall;
	}
	/**
	 * determines wheater or not jeffrey controls the scrolling of the game
	 */
	public static void setScroll(boolean toScrollOrNotToScroll) {
		scrolling = toScrollOrNotToScroll;
	}
	
	public static void refreshFullParty() {
		boolean [] working = new boolean [] {false,false,false};
		ArrayList<GameObject> jeffreys = ObjectHandler.getObjectsByName("Jeffrey");
		for (int i = 0; i < jeffreys.size(); i++) {
			Jeffrey jeff = (Jeffrey) jeffreys.get(i);
			for (int j = 0; j < jeff.party.length;j++) {
				if (jeff.party[j]) {
					working[j] = true;
				}
			}
		}
		fullParty = working;
	}
	
	
	
	/**
	 * runs the correct code to have the player switch to controling any charictar 
	 * @param witchCharacter the charictar to switch too
	 */
	public void runCharictarSwitchCode(int witchCharacter) {
		if (party[witchCharacter]) {
			switch (witchCharacter) {
			case 0:
				wpn.onSwitch();
				newWeapon = true;
				if (jeffreyHealth > 0) {
					nextCharacter++;
					witchCharictar = 0;
					if (isCrouched()) {
						this.setSprite(JEFFREY_CROUCHING);
					}
				}
				switchTimer = 0;
				break;
			case 1:
				wpn.onSwitch();
				newWeapon = true;
				if (samHealth > 0) {
					nextCharacter++;
					witchCharictar = 1;
					if (isCrouched()) {
						this.setSprite(SAM_CROUCHING);
					}
				}
				switchTimer = 0;
				break;
			case 2:
				wpn.onSwitch();
				newWeapon = true;
				if (ryanHealth > 0) {
					nextCharacter++;
					witchCharictar = 2;
					if (isCrouched()) {
						this.setSprite(RYAN_CROUCHING);
					}
				}
				switchTimer = 0;
				break;
		}
			if (this.nextCharacter > 2) {
				this.nextCharacter = 0;
			}
			this.runSwitchCode();
		} else {
			if (Jeffrey.getJeffreyWithCharacter(witchCharacter) != null) {
				Jeffrey.getJeffreyWithCharacter(witchCharacter).active = true;
				Jeffrey.getJeffreyWithCharacter(witchCharacter).inzializeCamera();
				this.active = false;
				if (witchCharictar == 0) {
					this.setSprite(JEFFREY_IDLE);
				} else if (witchCharictar == 1) {
						this.setSprite(SAM_IDLE);
					} else {
						this.setSprite(RYAN_IDLE);
				}
				Jeffrey.getJeffreyWithCharacter(witchCharacter).runCharictarSwitchCode(witchCharacter);
				
			}
		}
	}
	@Override
	public void pausedEvent () {
		this.getWeapon().pausedEvent();
	}
	public static Jeffrey getActiveJeffrey () {
		ArrayList <GameObject> jeffreys = ObjectHandler.getObjectsByName("Jeffrey");
		if (jeffreys != null) {
			for (int i = 0; i < jeffreys.size(); i++) {
				Jeffrey j = (Jeffrey)jeffreys.get(i);
				if (j.isActive()) {
					return j;
				} 
			}
		}
		return null;
	}
	/**
	 * creates a new jeffrey with party 2 and sets this jeffrey to only have party 1
	 * note having no charictars in either party WILL cause an infinte loop
	 * @param party1 the group you want for the first party expressed as a boolean array (example: {true,false,true} would be a party of Jeffrey and Ryan)
	 * @param party2 the group you want for the first party expressed as a boolean array (example: {false,true,false} would be a party of only sam)
	 * @return the new Jeffrey
	 */
	public Jeffrey splitParty (boolean [] party1, boolean [] party2) {
		this.party = party1;
		Jeffrey j = new Jeffrey ();
		j.party = party2;
		j.active = false;
		while (true) {
			if (!party1[witchCharictar]) {
				if (party2[witchCharictar]) {
					j.active = true;
					j.inzializeCamera();
					this.active = false;
					break;
				} else {
					witchCharictar = witchCharictar + 1;
				}
			} else {
				break;
			}
		}
		refreshFullParty();
		return j;
	}
	/**
	 * sets the party that is shared across all Jeffreys
	 * @param party the entire party that is currently being used expressed as a boolean array (example: {true,true,true} represents a party consisting of jeffrey, sam and ryan)
	 */
	public static void setFullParty (boolean [] party) {
		fullParty = party;
	}
	/**
	 * destroys all jeffreys that have a portion of the party and gives all of the party members to this jeffrey
	 * @param party every party member that should be in this new party
	 */
	public void joinPartys (boolean [] party) {
		ArrayList <GameObject> working = ObjectHandler.getObjectsByName("Jeffrey");
		for (int i = 0; i < working.size(); i++) {
			Jeffrey j = (Jeffrey) working.get(i);
			if (!j.equals(this)) {
				boolean destroy = false;
				for (int b = 0; b < party.length; b++) {
					if (j.party[b] && party[b]) {
						this.party[b] = true;
						destroy = true;
						if (j.active) {
							active = true;
						}
					}
				}
				if (destroy) {
					j.forget();
				}
			}
		}
		refreshFullParty();
	}
	public boolean isActive () {
		return active;
	}
	private void runSwitchCode () {
		if (nextCharacter > 2) {
			nextCharacter = 0;
		}
		wpn.onSwitch();
		int startMemer = nextCharacter;
		while (!(fullParty[nextCharacter]) || witchCharictar == nextCharacter) {
			nextCharacter = nextCharacter + 1;
			
			if (nextCharacter > 2) {
				nextCharacter = 0;
			}
			if (startMemer == nextCharacter) {
				break;
			}
		}
	}
	private void switchToAPartyMember () {
		int startMemer = nextCharacter;
		while (!(party[nextCharacter]) || witchCharictar == nextCharacter) {
			nextCharacter = nextCharacter + 1;	
			if (nextCharacter > 2) {
				nextCharacter = 0;
			}
			if (startMemer == nextCharacter) {
				break;
			}
		}
		this.runCharictarSwitchCode(nextCharacter);
		while (!(party[nextCharacter]) || witchCharictar == nextCharacter) {
			nextCharacter = nextCharacter + 1;	
			if (nextCharacter > 2) {
				nextCharacter = 0;
			}
			if (startMemer == nextCharacter) {
				break;
			}
		}
	}
	/**
	 * @return true if it is posible to switch to another charictar false otherwise
	 * 
	 */
	public boolean checkSwitch () {
		switch (witchCharictar) {
		case 0:
			if ((fullParty[1] && samHealth > 0) || (fullParty[2] && ryanHealth > 0)) {
				return true;
			} else {
				return false;
			}
		case 1: 
			if ((fullParty[0] && jeffreyHealth > 0) || (fullParty[2] && ryanHealth > 0)) {
				return true;
			} else {
				return false;
			}
		case 2: 
			if ((fullParty[1] && samHealth > 0) || (fullParty[0] && jeffreyHealth > 0)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	public static Jeffrey getJeffreyWithCharacter (int witchCharacter) {
		for (int i = 0; i < ObjectHandler.getObjectsByName("Jeffrey").size(); i++) {
			Jeffrey j = (Jeffrey)ObjectHandler.getObjectsByName("Jeffrey").get(i);
			if (j.party[witchCharacter]) {
				return j;
			}
		}
		return null;
	}
	/**
	 * removes a character from the party
	 * @param witchCharacter the character to remove (0 for Jeffrey 1 for Ryan 2 for Sam anything else will do nothing)
	 */
	public void removeCharacter (int witchCharacter) {
		try {
			party[witchCharacter] = false;
			if (witchCharictar == witchCharacter) {
				int lol = witchCharacter;
				int startMemer = lol;
				while (!(party[lol])) {
					lol = lol + 1;
					if (lol > 2) {
						lol = 0;
					}
					if (startMemer == lol) {
						break;
					}	
				}
				this.runCharictarSwitchCode(lol);
			 }
			refreshFullParty();
		} catch (IndexOutOfBoundsException e) {
			
		}
	
	}	
	/**
	 * sets the camera up to the right inzial condition when you switch out your Jeffrey
	 */
	public void inzializeCamera() {
		if (scrolling) {
			
			double edgeLeft = RenderLoop.window.getResolution()[0] * 0.221875;
			double edgeTop = RenderLoop.window.getResolution()[1] * 0.296296296;
			
			double drawBoundY = RenderLoop.window.getResolution()[1] * 0.888888888;
			double drawBoundX = RenderLoop.window.getResolution()[0] * 0.666666666;
			
			int veiwX = (int)(this.getX() -this.getX()%edgeLeft);
			int veiwY = (int)(this.getY() -this.getY()%edgeTop);
			if (veiwY + drawBoundY > Room.getHeight()*16) {
				veiwY = (int)((Room.getHeight()*16) - drawBoundY);
				if (veiwY < 0) {
					veiwY = 0;
				}
			}
			if (veiwX + drawBoundX > Room.getWidth()*16) {
				veiwX = (int)((Room.getWidth()*16) - drawBoundX);
				if (veiwX < 0) {
					veiwX = 0;
				}
			}
			Room.setView(veiwX, veiwY);
		}
	}
	/**
	 * adds a character to the party 
	 * @param witchCharacter the character to add (0 for Jeffrey 1 for Ryan 2 for Sam anything else will do nothing)
	 */
	public void addCharacter (int witchCharacter) {
		party [witchCharacter] = true;
		refreshFullParty();
	}
	public void setParty (boolean [] party) {
		this.party = party;
		refreshFullParty();
	}
	@Override
	public void draw () {
		super.draw();
		if (active) {
			wpn.draw();
		}
	}
	@Override
	public void setX (double val) {
		super.setX(val);
		this.onPosChange();
		
	}
	public void onPosChange() {
		if (this.getAnimationHandler().flipHorizontal ()) {
			this.getWeapon().setX (this.getX() - 5);
			this.getWeapon().getAnimationHandler().setFlipHorizontal (true);
		} else {
			this.getWeapon().setX (this.getX() + 11);
			this.getWeapon().getAnimationHandler().setFlipHorizontal (false);
		}
		if (!isCrouched()) {
			this.getWeapon().setY(this.getY() + 16);
		} else {
			this.getWeapon().setY(this.getY() + 20);
		}
		GameObject.onJeffreyPosChangeReal();
	}
	@Override
	public void setY(double val) {
		super.setY(val);
		this.onPosChange();
		
	}
	@Override 
	public boolean goY (double val) {
		boolean working = super.goY(val);
		this.onPosChange();
		return working;
	}
	@Override
	public boolean goX (double val) {
		boolean working = super.goX(val);
		this.onPosChange();
		return working;
	}
	public void crouchElegable (boolean elegable) {
		crouchElegable = elegable;
	}
}