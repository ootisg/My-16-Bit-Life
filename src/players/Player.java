package players;

import weapons.AimableWeapon;
import weapons.Weapon;
import items.Inventory;
import main.GameCode;
import main.GameObject;
import main.GameWindow;
import main.InputManager;
import main.ObjectHandler;
import main.RenderLoop;


import java.util.ArrayList;


import gameObjects.DamageText;
import map.MapTile;
import map.Room;
import resources.Sprite;
import statusEffect.Status;
import triggers.Checkpoint;

public class Player extends GameObject {
	
	private Weapon wpn; 
	
	private boolean isWalking;
	public  boolean isJumping;
	
	boolean fallDurringColisionCheck = false;
	private int invulTimer = 0;
	private boolean crouching = false;

	private boolean forceSpacebar = false;
	
	public double health = 100;
	public double maxHealth = 150;
	
	private Party party = null;
	

	public boolean binded = false;
	public boolean bindLeft = false;
	public boolean bindRight = false;	
	private boolean scrolling = true;
	private boolean fallBruh = true;
	public Status status = new Status ();
	
	public Sprite standSprite = new Sprite("resources/sprites/config/jeffrey_idle.txt");
	public Sprite walkSprite = new Sprite("resources/sprites/config/jeffrey_walking.txt");
	public Sprite crouchSprite = new Sprite ("resouces/sprites/config/crouching_Jeffrey.txt");
	
	public  double vx = 0;
	public double vy = 0;
	public double trueVy=0;
	private ArrayList <Double> increments = new ArrayList<Double>();
	private double ax = 0;
	private double ay = 0;
	
	InputManager manager;
	public static Inventory inventory = new Inventory();
	
	public boolean active = false;
	
	int weaponIndex = 0;
	
	public static final double TERMINAL_VELOCITY = 15;
	public static final double JUMP_VELOCITY = 12.15625;
	//note if you ever plan on using the s key and sprites with this class you are gonna have to have your class have a lower game logic priority (witch is actually higher becasue its stupid)
	
	public Player () {
		//This class is not yet commented
		setSprite (standSprite);
		getAnimationHandler ().setFrameTime (50);
		this.setHitboxAttributes(4, 4, 7, 27);
		this.setGameLogicPriority(-2);
		this.setHitboxRounding(true);
		this.adjustHitboxBorders();
		this.setPusablity(true);
		try {
			wpn = Player.getInventory().getPlayerWeapon(this.getPlayerNum()).get(0);
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			wpn = null;
		}
	}
	public boolean isCrouched () {
		return crouching;
	}
	public void activate (Player oldActive) {
		active = true;
		try {
			wpn = inventory.getPlayerWeapon(this.getPlayerNum()).get(weaponIndex);
		} catch (IndexOutOfBoundsException e) {
			
		}
		if (oldActive != null) {
			if (party.getPlayer(oldActive.getPlayerNum()) != null) {
				oldActive.forget();
				this.declare(oldActive.getX(), oldActive.getY());
			}
			if (!GameCode.getPartyManager().sameParty(this, oldActive)) {
				this.inzializeCamera();
			}
		} else {
			this.inzializeCamera();
			this.declare();
		}	
	}
	public void deactivate () {
		active = false;
	}
	@Override
	public void frameEvent () {
		if (active && !binded) {
			
			//crouches and uncrouches
			if (keyDown ('S') && this.getX() - this.getSpriteX() == 0) {
				crouching = true;
				if (this.getSprite() != crouchSprite) {
					this.setSprite(crouchSprite);
				}
			
					this.getAnimationHandler().setRepeat(false);
					this.setHitboxRounding(false);
				if (this.getAnimationHandler().getFrame() == 2) {
					this.setHitboxAttributes(4, 15, 8, 16);
				}
			} else {
				if (crouching) {
					this.setSprite(standSprite);
					this.setHitboxRounding(true);
					this.getAnimationHandler().setRepeat(true);
					this.setHitboxAttributes(4, 4, 7, 27);
				}
				crouching = false;
				
			}
			
			//changes wepons
			if (keyPressed ('Z')) {
				this.switchWeapons();
			}
			
			//jumps
			if (keyDown(32) && !isJumping && vy == 0 && !forceSpacebar) {
				
				isJumping = true;
				trueVy = -JUMP_VELOCITY;
				vy = -JUMP_VELOCITY;
				setSprite (walkSprite);
				getAnimationHandler ().setFrameTime (0);
				getAnimationHandler ().setAnimationFrame (3);
			}
		
			//moves left and right
			if (!bindLeft && keyDown ('A') && (!crouching || vy != 0)) {
				ax = dealWithSpeedDebuffsLeft();
				if (!this.getAnimationHandler().flipHorizontal() && wpn != null) {
					wpn.onFlip();
				}
				this.getAnimationHandler().setFlipHorizontal (true);
				if (vy == 0 && !isWalking && !crouching) {
					isWalking = true;
					setSprite (walkSprite);
				}
			} else if (!bindRight && keyDown ('D') && (!crouching || vy != 0)) {
				
				ax = dealWithSpeedDebuffsRight();
				
				if (this.getAnimationHandler().flipHorizontal() && wpn != null) {
					wpn.onFlip();
				}
				
				this.getAnimationHandler().setFlipHorizontal (false);
				
				if (vy == 0 && !isWalking && !crouching) {
					isWalking = true;
					setSprite (walkSprite);
				}
			} else {
				if (isWalking) {
					isWalking = false;
					setSprite (standSprite);
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
			if (wpn != null) {
				double wpnX = wpn.getX () - Room.getViewX ();
				double wpnY = wpn.getY () - Room.getViewY ();
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
					AimableWeapon weapon = (AimableWeapon) wpn;
					weapon.setRotation (ang);
				}
			}
			} catch (ClassCastException e) {
				
			}
			//Handles invulnerability
			if (invulTimer > 0) {
				invulTimer --;
			}
			if (health <= 0) {
				GameCode.getPartyManager().runCharictarSwitchCode();
				//MainLoop.getConsole ().enable ("You died, and I'm too lazy to put anything in for that. :P");
			}
			
			if (wpn != null) {
				wpn.frameEvent();
				if (this.keyPressed('X')) {
					wpn.onSwitchModes();
				}
			}
			
			if (!isCrouched()) {
				if (wpn != null && this.mouseButtonClicked(0) && !mouseButtonReleased (0)) {
					wpn.onFire();
				}
				
				if (wpn != null && this.mouseButtonReleased(0)) {
					wpn.onRelease();
				}
				
				if (wpn != null && this.mouseButtonDown(0)) {
					wpn.onHold();
				}
				
				if (wpn != null && this.mouseButtonClicked(2) && !mouseButtonReleased (2)) {
					wpn.onSecondaryFire();
				}
				
				if (wpn != null && this.mouseButtonReleased(2)) {
					wpn.onSecondaryRelease();
				}
				
				if (wpn != null && this.mouseButtonDown(2)) {
					wpn.onSecondaryHold();
				}
				
			}
		
	}
		if (scrolling && active) {
			
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
		
		if (vy == 0) {
			fallDurringColisionCheck = false;
		}
		
		//deals with acceleration
		if (fallBruh) {
			if (keyDown(32) || forceSpacebar) {
				vy += Room.getGravity ();
				trueVy += Room.getGravity();
			} else {
				vy += (Room.getGravity () + 0.5);
				trueVy += (Room.getGravity () + 0.5);
			}
		}
		if (vy > TERMINAL_VELOCITY) {
			vy = TERMINAL_VELOCITY;
		}
		
		//actually does the falling
		if (fallBruh) {
			setY (getY () + vy);
		}
		
		//deals with friction and getting stuck in the floor
		if (Room.isColliding(this)) {
			vy = 0;
			trueVy = 0;
			forceSpacebar = false;
			increments.clear();
			double fc = .2; //Friction coefficient
			if (status.checkStatus("Slippery")) {
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
			MapTile[] collidingTiles = Room.getCollidingTiles (this);
			for (int i = 0; i < collidingTiles.length; i++) {
			    if (getY () + 32 >= collidingTiles [i].y && getY () + 32 <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y - 32);
			        this.vy = 0;
			        this.trueVy = 0;
			    	getAnimationHandler ().setFrameTime (50);
			    	if (isJumping) {
			    		this.setSprite(standSprite);
			    		isWalking = false;
			    	}
			        isJumping = false;
			        break;
			    }
			    if (getY () + this.getHitboxYOffset() >= collidingTiles [i].y && getY () + this.getHitboxYOffset() <= collidingTiles [i].y + 16) {
			        this.setY (collidingTiles [i].y + 16);
			        break;
			    }
			}
		}
		fallDurringColisionCheck = true;
		//deals with turning around when the ground is slippery
		if (status.checkStatus("Slippery")) {
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
		//moves Jeffrey and friends on the x axis
		if (!this.goX (this.getX () + vx)) {
			vx = 0;
		}
		
		if (active) {
			status.statusFrame();
		}
		
}
	
	//for use ONLY in tile entitys durring thier does collide or onCollision the variable will not have an acurate value at any other time
		public boolean isFallingDurringCollision() {
			return 	fallDurringColisionCheck;
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
	private double dealWithSpeedDebuffsLeft () {
		if ((vx >= -3.0 && !status.checkStatus("Slowness") && !status.checkStatus("Fast")) 
		|| (vx >= -3.0 && status.checkStatus("Slowness") && status.checkStatus("Fast"))
		|| (vx >= -4.0 && !status.checkStatus("Slowness") && status.checkStatus("Fast"))
		|| (vx >= -2.0 && status.checkStatus("Slowness") && !status.checkStatus("Fast"))) {
			if (status.checkStatus("Slowness") && !status.checkStatus("Fast")) {
				return -.3;
			} else {
			if (status.checkStatus("Fast")) {
				return -.7;	
				} else {
					return -.5;
				}
			}
		}
		return 0;
	}
	private double dealWithSpeedDebuffsRight () {
		if ((vx <= 3.0 && !status.checkStatus("Slowness") && !status.checkStatus("Fast")) 
		|| (vx <= 3.0 && status.checkStatus("Slowness") && status.checkStatus("Fast"))
		|| (vx <= 4.0 && !status.checkStatus("Slowness") && status.checkStatus("Fast"))
		|| (vx <= 2.0 && status.checkStatus("Slowness") && !status.checkStatus("Fast"))) {
			if (status.checkStatus("Slowness") && !status.checkStatus("Fast")) {
				return .3;
			} else {
			if (status.checkStatus("Fast")) {
				return .7;	
				} else {
					return .5;
				}
			}
		}
		return 0;
	}
	
	public Weapon getWeapon () {
		return wpn;
	}
	
	public void switchWeapons () {
		weaponIndex = weaponIndex + 1;
		weaponIndex = weaponIndex % inventory.getPlayerWeapon(this.getPlayerNum()).size();
		wpn = inventory.getPlayerWeapon(this.getPlayerNum()).get(weaponIndex);
	}
	public void setSprites (Sprite[] sprites) {
		walkSprite = sprites[0];
		standSprite = sprites[1];
		crouchSprite = sprites[2];
		if (!this.declared()) {
			this.setSprite(standSprite);
		}
	}
	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}
	/**
	 * returns a list of all the increments to Jeffreys speed durring a fall (note it is reset at the end of a fall so you have to get it at the right time if you want it)
	 * (I can already tell Imma get pissed at myself someday for designing it like that, but its really the only practical way to do it)
	 * @return it says it above
	 */
	public ArrayList<Double> getFallIncrementation(){
		return increments;
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
		GameCode.getPartyManager().resetSwitchTimer ();
		if (invulTimer == 0) {
			if (status.checkStatus("Brittle")) {
				baseDamage = baseDamage *1.2;
			}
			health = health - baseDamage;
			invulTimer = 15;
		}
	}
	public void heal (double amount) {
		GameCode.getPartyManager().resetSwitchTimer ();
		health = health + amount;
		if (health > maxHealth) {
			health = maxHealth;
		}
		DamageText text = new DamageText (Math.ceil(amount), this.getX(), this.getY(), 1);
		
		text.declare(this.getX(), this.getY());
	}
	
	@Override
	public void checkpointCode (Checkpoint checkpoint) {
		this.setX(checkpoint.getX());
		this.setY(checkpoint.getY());
		
	}
	public static Inventory getInventory () {
		return Player.inventory;
	}
	public static void setInventory (Inventory newInventory) {
		Player.inventory = newInventory;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public double getHealth () {
		return health;
	}
	
	//stops the charictar from falling for a bit
	public void stopFall(boolean fall) {
		fallBruh = !fall;
	}
	/**
	 * determines wheater or not jeffrey controls the scrolling of the game
	 */
	public void setScroll(boolean toScrollOrNotToScroll) {
		scrolling = toScrollOrNotToScroll;
	}	

	@Override
	public void pausedEvent () {
		if (wpn != null) {
			wpn.pausedEvent();
		}
	}
	public static Player getActivePlayer () {
		ArrayList<ArrayList<GameObject>> players = ObjectHandler.getChildrenByName("Player");
		if (players != null) {
			for (int i = 0; i < players.size(); i++) {
				for (int j = 0; j < players.get(i).size(); j++) {
					Player p = (Player)players.get(i).get(j);
					if (p.isActive()) {
						return p;
					} 
				}
			}
		}
		return null;
	}
	public boolean isActive () {
		return active;
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
	public int getPlayerNum () {
		return -1;
	}
	@Override
	public void draw () {
		super.draw();
		if (active && wpn != null) {
			wpn.draw();
		}
	}
	@Override
	public void setX (double val) {
		super.setX(val);
		this.onPosChange();
		
	}
	public void onPosChange() {
		if (wpn != null) {
			if (this.getAnimationHandler().flipHorizontal ()) {
				wpn.setX (this.getX() - 5);
				wpn.getAnimationHandler().setFlipHorizontal (true);
			} else {
				wpn.setX (this.getX() + 11);
				wpn.getAnimationHandler().setFlipHorizontal (false);
			}
			if (!isCrouched()) {
				wpn.setY(this.getY() + 16);
			} else {
				wpn.setY(this.getY() + 20);
			}
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
}