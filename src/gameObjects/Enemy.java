package gameObjects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.DirectionBullet;
import resources.Sprite;


public abstract class Enemy extends GameObject {
	//Template for enemies
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	// list of delared enemys
	public static ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
	public Boolean [] appliedStatuses;
	public int health = 1;
	int momentum;
	protected double baseDamage = 2.5;
	public Rectangle swarmBox;
	boolean jumping;
	protected int defence;
	private double slope;
	boolean moveRight;
	int circles;
	private int layer;
	int knockbackTime;
	DamageText text;
	private double leadingEnemyYPrevios;
	int xToMove;
	private boolean undecided;
	boolean beingKnockedBack;
	double leadingEnemyXPrevios;
	boolean direction;
	protected boolean pathFallowed;
	private boolean tieBreaker;
	private boolean reCheck;
	double oldDireciton;
	double correctDirection;
	boolean fixedPath;
	Enemy enemyToFallow;
	int yToMove;
	boolean falls;
	Point nextPoint;
	Random RNG;
	int currentSpeed;
	boolean canFuckWithSprite;
	boolean moveable;
	protected boolean leadingEnemy;
	protected boolean fallowingEnemy;
	int chargeTimer;
	int timer;
	boolean adjustedSpeed;
	boolean jumpDone;
	boolean patrolBothWays;
	boolean moveing;
	int countdown;
	boolean lockedRight;
	boolean diesNormally;
	int waitForCollison;
	private Stack <Point> path;
	boolean chargeing;
	public Enemy () {
		enemyList.add(this);
		path = new Stack <Point>();
		momentum = 0;
		swarmBox = new Rectangle ();
		patrolBothWays = false;
		leadingEnemy = false;
		fallowingEnemy = false;
		fixedPath = true;
		tieBreaker = false;
		oldDireciton = 0;
		leadingEnemyXPrevios = 0;
		slope = 0;
		circles = 0;
		nextPoint = new Point();
		adjustedSpeed = false;
		chargeTimer =0;
		correctDirection = 0;
		leadingEnemyYPrevios = 0;
		//status indexes are the same as in status.java so check there for info on that
		appliedStatuses = new Boolean [10];
		Arrays.fill(appliedStatuses, false);
		canFuckWithSprite = true;
		beingKnockedBack = false;
		xToMove = 0;
		knockbackTime = 0;
		undecided = false;
		reCheck = false;
		yToMove = 0;
		moveing = true;
		moveable = false;
		waitForCollison = 0;
		chargeing = false;
		layer = 0;
		RNG = new Random();
		lockedRight = false;
		jumpDone = false;
		falls = false;
		jumping = false;
		currentSpeed = 0;
		timer = 0;
		countdown = 0;
		diesNormally = true;
	}
	@Override
	public void frameEvent () {
		enemyFrame ();
		if (beingKnockedBack) {
			this.falls = true;
			if (knockbackTime == 0) {
				knockbackTime = 0;
				beingKnockedBack = false;
			}
			knockbackTime = knockbackTime - 1;
			if (!direction) {
			this.goX(this.getX() + 3);
			} else {
			this.goX(this.getX() - 3);
			}
			this.goY(this.getY() - 5);
		}
		if ((this.health <= 0) && diesNormally ) {
			
			this.deathEvent();
		}
		if (isColliding (player)) {
			attackEvent ();
		}

		boolean onFloor;
		onFloor = false;
		boolean colidingSide;
		colidingSide = false;
		if (falls) {
			if (!(Room.isColliding(this.hitbox()))){
				this.setX(getX() - 1);
				if (Room.isColliding(this.hitbox())){
					colidingSide = true;
				}
				this.setX(getX() + 1);
				}
			if (!(Room.isColliding(this.hitbox()))){
				this.setX(getX() + 1);
				if (Room.isColliding(this.hitbox())){
					colidingSide = true;
				}
				this.setX(getX() - 1);
				}
			if ((!(Room.isColliding(this.hitbox())) || !colidingSide) && !this.checkIfStuckInCeling(1) ){
				this.setY(getY() + currentSpeed + 1);
				if (Room.isColliding(this.hitbox())){
					onFloor = true;
				}
				this.setY(getY() - (currentSpeed + 1) );
				}
		}

		if (!onFloor && falls) {
			
		momentum = momentum + 1;
		if (momentum < 6){
			this.setY(getY() + 1);
			currentSpeed = 1;
		}
		if (momentum > 6 && momentum < 12){
			this.setY(getY() + 2);
			currentSpeed = 2;
		}
		if (momentum > 6 && momentum < 18){
			this.setY(getY() + 3);
			currentSpeed = 3;
		}
		if (momentum > 18 && momentum < 24){
			this.setY(getY() + 4);
			currentSpeed = 4;
		}
		if (momentum > 24){
			this.setY(getY() + 5);
			currentSpeed = 5;
		}	
	}
		if (onFloor && momentum >= 1){
			momentum = 0;
			if (this.currentSpeed != 1) {
			this.setY(getY()- currentSpeed);
			}
			currentSpeed = 0;
		}
	}
	public void enemyFrame () {
		
	}
	//sets wheater the enemy falls or not
	public void setFalls (boolean doesFall) {
		falls = doesFall;
	}
	
	public void knockback (int amountOfTime, boolean attackSide) {
		beingKnockedBack = true;
		knockbackTime = amountOfTime;
		direction = attackSide;
	}
	//sets up a special death if your into that kinda thing
	public void setDeath (boolean death) {
		diesNormally = death;
	}
	@Override 
	public boolean goX(double val) {
		if (this.appliedStatuses[3] && val - this.getX() != 0) {
		if (val - this.getX()> 1 ) {
			val = val -1;
		} else {
			if ( val - this.getX() < -1 ) {
				val = val + 1;
			} else {
					if (!moveable) {
						val = this.getX();
						moveable = true;
					} else {
						moveable = false;
					}
				}
			}
		}
		this.xprevious = x;
		spriteX =  (spriteX + (val - x));
		x = val;
		if (Room.isColliding(this.hitbox())) {
			x = xprevious;
			spriteX = (spriteX - (val- x));
			return false;
		} else {
			return true;
		}
	}
	public void setXTheOldFasionWay(double val) {
		super.setX(val);
	}
	@Override
	public void setX (double val) {
		if (this.appliedStatuses[3] && val - this.getX() != 0) {
		if (val - this.getX() > 1) {
			val = val -1;
			
		} else {
			if ( val - this.getX() < -1) {
				val = val + 1;
		} else {
			if (!moveable) {
				val = this.getX();
				moveable = true;
			} else {
				moveable = false;
			}
		} 
		}
		}
		xprevious = x;
		spriteX =  (spriteX + (val - x));
		x = val;
	}
	public void attackEvent () {
		player.damage (this.baseDamage);
	}
	public void deathEvent () {
		if (!GameCode.testJeffrey.getInventory().checkKill(this)) {
			GameCode.testJeffrey.getInventory().addKill(this);
		}
		enemyList.remove(this);
		this.forget ();
	}
	//override to set entrys of stuff 
	public String checkEntry () {
		return "";
	}
	//override to set names of stuff
	public String checkName () {
		return "";
	}
	//returns true if there is a celling or floor between the enemy and the player
			public boolean checkPlayerPositionRelativeToCellings () {
				double x = this.getX();
				super.setX(GameCode.testJeffrey.getX());
					for (int i = 0; true; i++) {
						this.setY(this.getY () + i);
						if (Room.isColliding(this.hitbox())) {
							if (player.getY() > this.getY()) {
							this.setY(this.getY() - i);
							super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)player.getY()) {
							this.setY(this.getY() - i);
							break;
						}
						this.setY(this.getY() - i*2);
						if (Room.isColliding(this.hitbox())) {
							if (player.getY() < this.getY()) {
								this.setY(this.getY() + i);
								super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)player.getY()) {
							this.setY(this.getY() + i);
							break;
						}
						this.setY(this.getY() + i);
					}
					super.setX(x);
					return false;
			}
	//returns true if there is a wall between the enemy and the player
		public boolean checkPlayerPositionRelativeToWalls () {
				for (int i = 0; true; i++) {
					super.setX(this.getX () + i);
					if (Room.isColliding(this.hitbox())) {
						if (player.getX() > this.getX()) {
						super.setX(this.getX() - i);
						return true;
						}
					}
					if ((int)this.getX() == (int)player.getX()) {
						super.setX(this.getX() - i);
						break;
					}
					super.setX(this.getX() - i*2);
					if (Room.isColliding(this.hitbox())) {
						if (player.getX() < this.getX()) {
							super.setX(this.getX() + i);
						return true;
						}
					}
					if ((int)this.getX() == (int)player.getX()) {
						super.setX(this.getX() + i);
						break;
					}
					super.setX(this.getX() + i);
				}
				return false;
		}
	public void damage (int amount) {
		if (GameCode.testJeffrey.checkIfPowerful()) {
			amount = (int) ((amount * 1.2) - defence);
			if(amount <= 0){
				amount = 1;
			}
			text = new DamageText (amount * 1.2, this.getX(), this.getY(), false );	
		} else {
			amount = amount - defence;
			if(amount <= 0){
				amount = 1;
			}
		text = new DamageText (amount, this.getX(), this.getY(), false);
		}
		text.declare(this.getX(), this.getY());
		this.health = health - amount;
	}
	public void setHealth (int health) {
		this.health = health;
	}
	//use this to make behaviors not change sprites
	public void setSpriteChangeing (boolean abillity) {
		canFuckWithSprite = abillity;
	}
	public int getHealth () {
		return this.health;
	}
	// changes whereter or not the enemy attacks enemys behind him (if your using patrol that is)
	public void setAttackFromBothSides (boolean attack) {
		patrolBothWays = attack;
	}
	//used in conjunction with charge
	public void getChargeLine () {
		if (((GameCode.testJeffrey.getX() > this.getX()) && GameCode.testJeffrey.getY() > this.getY())) {
			xToMove = RNG.nextInt(3) + 1;
			yToMove = RNG.nextInt(3) + 1;
			this.getAnimationHandler().setFlipHorizontal(false);
		}
if ((GameCode.testJeffrey.getX() < this.getX()) && GameCode.testJeffrey.getY() > this.getY()) {
	xToMove = RNG.nextInt(3) + 1;
	xToMove = xToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(true);
	yToMove = RNG.nextInt(3) + 1;	
		}
if ((GameCode.testJeffrey.getX() > this.getX()) && GameCode.testJeffrey.getY() < this.getY()) {
	xToMove = RNG.nextInt(3) + 1;
	yToMove = RNG.nextInt(3) + 1;
	yToMove = yToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(false);
}
if ((GameCode.testJeffrey.getX() < this.getX()) && GameCode.testJeffrey.getY() < this.getY()) {
	xToMove = RNG.nextInt(3) + 1;
	yToMove = RNG.nextInt(3) + 1;
	yToMove = yToMove * -1;
	this.getAnimationHandler().setFlipHorizontal(true);
	xToMove = xToMove * -1;
}

	}
/**
 * charges in a strate line for a while towards the player than redraws and goes again
 * @param timeToCharge how long the enemy charges before recreating the charge line
 */
	public void keepChargeing (int timeToCharge) {
		if (!chargeing) {
			getChargeLine();
			chargeing = true;
			chargeTimer = 0;
		}
		Charge(timeToCharge);
	}
	/**
	 * charges in a strate line for a while towards the player 
	 * 	@param timeToCharge how long the enemy charges 
	 */
	public void Charge (int timeToCharge) {
		if (!chargeing && !(xToMove == 0) && !(yToMove == 0)) {
			chargeing = true;
		}
		if (!(this.goX(this.getX() + xToMove) && this.goY(this.getY() + yToMove))) {
			this.getModifiedChargeLine();
			chargeTimer = 0;
		}
if (chargeTimer == timeToCharge) {
		xToMove = 0;
		yToMove = 0;
		chargeing = false;
		chargeTimer = 0;
		}
		chargeTimer = chargeTimer + 1;
		/*if (xToMove == 0 && yToMove == 0) {
			chargeTimer = 0;
		}*/
	}
	/**
	 * a method designed to get a charge line that will get the chargeing enemy out of the wall
	 */
	public void getModifiedChargeLine() {
		int i = xToMove + 3;
		int y = yToMove + 3;
		while (true) {	
			if (this.goXandY( this.getX() + ((i % 7) - 3)* 3,this.getY() + ((y%7) - 3) *3)){
				xToMove = (i % 7) - 3;
				yToMove = (y%7) - 3;
				break;
			}
			i = i + 1;
			y = y + 1;
		}
		
	}

	
	//makes the enemy jump towards the player (based on the assumption that your enemy falls)
	public void jump (int horizontalBaseSpeed, int verticalBaseSpeed) {
		timer = timer + 1;
		if (lockedRight) {
			if (timer <= 6) {
			if (Room.isColliding(this.hitbox())) {
				if (this.checkIfColidingWithWall(horizontalBaseSpeed)) {
					if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed))){
					this.goY(this.getY() - verticalBaseSpeed);
					}
				} else {
					this.goX(this.getX() +horizontalBaseSpeed);
				}
			} else {
			this.goY(this.getY() - verticalBaseSpeed);
			this.goX(this.getX() + horizontalBaseSpeed);
			}
			}
			if (timer > 6 && timer <=11) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(horizontalBaseSpeed * 1.1)) {
						if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed * 1.1))){
						this.goY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.goX(this.getX() +(horizontalBaseSpeed * 1.1));
					}
				} else {
				this.goY(this.getY() - (verticalBaseSpeed * 1.1));
				this.goX(this.getX() + (horizontalBaseSpeed * 1.1));
				}
			}
			if (timer > 11 && timer <= 16) {
				
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(horizontalBaseSpeed * 1.1)) {
						if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed *1.1))){
						this.goY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.goX(this.getX() + (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.goY(this.getY() - (verticalBaseSpeed *1.1 ));
				this.goX(this.getX() + (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 16 && timer <= 21) {
				if (!(this.checkIfColidingWithWall((horizontalBaseSpeed * 1.1)))) {
					this.goX(this.getX() + (horizontalBaseSpeed * 1.1));
			}
			}
			if (timer > 21) {
				timer = 0;
				jumpDone = true;
			}
	}
		if (!lockedRight) {
			if (timer <= 6) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(-1 * horizontalBaseSpeed)) {
						if (!this.checkIfStuckInCeling(-1 * (verticalBaseSpeed))){
						this.goY(this.getY() - verticalBaseSpeed);
						}
					} else {
						this.goX(this.getX() -horizontalBaseSpeed);
					}
				} else {
				this.goY(this.getY() - verticalBaseSpeed);
				this.goX(this.getX() - horizontalBaseSpeed);
				}
			}
			if (timer > 6 && timer <=11) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1))) {
						if (!this.checkIfStuckInCeling(-1 * (verticalBaseSpeed * 1.1))){
						this.goY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.goY(this.getY() - (verticalBaseSpeed * 1.1));
				this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
			}
			if (timer > 11 && timer <= 16) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1))) {
						if (!this.checkIfStuckInCeling(-1 * (verticalBaseSpeed * 1.1))){
						this.goY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.goY(this.getY() - (verticalBaseSpeed * 1.1));
				this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 16 && timer <= 21) {
					if (!(this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1)))) {
						this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
				} else {
				this.goX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 21) {
				timer = 0;
				jumpDone = true;
			}
		}
	}
	/**
	 * returns true if the player is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the player is in that box
	 */
	public boolean isNearPlayerX (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - GameCode.testJeffrey.getX()  <= -rangebound1Right) && (this.getX() - GameCode.testJeffrey.getX()  >= -rangebound2Right)) || ((GameCode.testJeffrey.getX() >= this.getX() - rangebound2Left) &&(GameCode.testJeffrey.getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() )) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the player is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the player is in that box
	 */
	public boolean isNearPlayerY (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - GameCode.testJeffrey.getY()  >= rangebound1Top) && (this.getY() - GameCode.testJeffrey.getY()  <= rangebound2Top)) || ((GameCode.testJeffrey.getY() >= this.getY() + rangebound1Bottom) &&(GameCode.testJeffrey.getY() <= this.getY() + rangebound2Bottom)) && !this.checkPlayerPositionRelativeToCellings() ) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the player is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the player is in that box
	 */
	public boolean isNearPlayerXWithoutCheckingWalls (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - GameCode.testJeffrey.getX()  <= -rangebound1Right) && (this.getX() - GameCode.testJeffrey.getX()  >= -rangebound2Right)) || ((GameCode.testJeffrey.getX() >= this.getX() - rangebound2Left) &&(GameCode.testJeffrey.getX() <= this.getX() - rangebound1Left))) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the player is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the player is in that box
	 */
	public boolean isNearPlayerYWithoutCheckingWalls (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - GameCode.testJeffrey.getY()  >= rangebound1Top) && (this.getY() - GameCode.testJeffrey.getY()  <= rangebound2Top)) || ((GameCode.testJeffrey.getY() >= this.getY() + rangebound1Bottom) &&(GameCode.testJeffrey.getY() <= this.getY() + rangebound2Bottom))) {
			return true;
		} else {
			return false;
		}
	}
	// checks to see if actions are finished also resets action (so always check before using it again even if you don't need to) 
	public boolean isJumpDone () {
		boolean truth = jumpDone;
		if (jumpDone) {
			jumpDone = false;
		}
		return truth;
	}
	//preset behaviors you can use for enemys by putting this in enemy frame (or running it a bunch of times another way)
	
	// makes enemy stand idle and then sometimes jump towards the player
	public void jumpyBoi (Sprite idleSprite, Sprite jumpSprite, int horizontalSpeed, int verticalSpeed, int waitTime) {
		if (!this.getSprite().equals(idleSprite) && !jumping) {
			this.setSprite(idleSprite);
		}
		this.setY(this.getY() + 1);
		if ((player.getX() > this.getX() - 200 && player.getX() <=this.getX() + 200) && Room.isColliding(this.hitbox())) {
			if (countdown == 0) {
			jumping = true;
			if (player.getX() > this.getX()) {
				lockedRight = true;
			} else {
				lockedRight = false;
			}
			if ((!this.getSprite().equals(jumpSprite)) && canFuckWithSprite ) {
				this.setSprite(jumpSprite);
			}
			} else {
				countdown = countdown - 1;
			}
			
		}
		this.setY(this.getY() -1);
		if (jumping) {
			this.jump(horizontalSpeed, verticalSpeed);
		}
		if (jumping && this.isJumpDone()) {
			countdown = waitTime;
			jumping = false;
		}
	}
	public void turnAroundDurringAttack(int fatAss) {
		this.moveing = true;
		if (((player.getX() > this.getX()) && !moveRight) || (player.getX() < this.getX()) && moveRight ) {
		if (moveRight) {
			this.setX(this.getX()- fatAss);
		} else {
			this.setX(this.getX() + fatAss);
		}
		this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
		this.moveRight = !moveRight;
		}
		
	}
	//walks back and forth inbetween walls and attacks when faceing the palyer
	//for speed inputing a negative makes it move one every that amount of frames a positive is that amount every frame
	public void patrol(int fatAss, int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left, Sprite attackingSprite, Sprite MoveingSprite, int speed, int xOffsetWhenFlipped, int xOffsetWhenNotFliped, int yOffset, int width, int height) {
		timer = timer + 1;
		if (speed < 0) {
		if (((timer % (speed * -1))  == 0) && moveing) {
			if (moveRight) {
				this.setX(this.getX() + 1);
			} else {
				this.setX(this.getX() - 1);
			}
		}
		} else {
			if(moveRight) {
				this.setX(this.getX() + speed);
			} else {
				this.setX(this.getX() - speed);
			}
		}
		if (this.getAnimationHandler().flipHorizontal()) {
			 if (!(this.getSprite().equals(attackingSprite))){
				this.setHitboxAttributes(xOffsetWhenFlipped, yOffset, width, height); 
			 }
		} else {
			if (!(this.getSprite().equals(attackingSprite))) {
			this.setHitboxAttributes(xOffsetWhenNotFliped, yOffset, width, height);
			}
		}
		waitForCollison = waitForCollison + 1;
		if (moveRight || patrolBothWays) {
			if ( ((this.getX() - GameCode.testJeffrey.getX()  < -rangebound1Right) && (this.getX() - GameCode.testJeffrey.getX()  > -rangebound2Right) ) ) {
				this.moveing = false;
				if (!(this.getSprite().equals(attackingSprite)) && canFuckWithSprite) {
				this.setSprite(attackingSprite);
				} 
				} else {
					if((!patrolBothWays && ((GameCode.testJeffrey.getX() >= this.getX() - rangebound2Left) &&(GameCode.testJeffrey.getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls())) ) {
					moveing = true;
					if (adjustedSpeed) {
						speed = 0;
						adjustedSpeed = false;
					}
					if (!(this.getSprite().equals(MoveingSprite)) && canFuckWithSprite) {
						this.setSprite(MoveingSprite);
					}
			}
				}
		} 
		if (!moveRight || patrolBothWays) {
			if ( (GameCode.testJeffrey.getX() >= this.getX() - rangebound2Left) &&(GameCode.testJeffrey.getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() ) {
				this.moveing = false;
				if (!(this.getSprite().equals(attackingSprite)) && canFuckWithSprite) {
				this.setSprite(attackingSprite);
			}
			} else {
				if (!(patrolBothWays && ((this.getX() - GameCode.testJeffrey.getX()  < -rangebound1Right) && (this.getX() - GameCode.testJeffrey.getX()  > -rangebound2Right)))) {
				moveing = true;
				if (adjustedSpeed) {
					speed = 0;
					adjustedSpeed = false;
				}
				if (!(this.getSprite().equals(MoveingSprite)) && canFuckWithSprite) {
					this.setSprite(MoveingSprite);
				}
				}
			}
		}
		if (patrolBothWays && this.getSprite().equals(attackingSprite)) {
			turnAroundDurringAttack (fatAss);
			if (speed == 0) {
				speed = 1;
				adjustedSpeed = true;
			}
		}
		if (Room.isColliding(this.hitbox()) && waitForCollison > 10) {
			waitForCollison = 0;
			if (moveRight) {
				this.setX(this.getX()- fatAss);
			} else {
				this.setX(this.getX() + fatAss);
			}
			this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
			this.moveRight = !moveRight;
			if (timer % 2 == 0) {
				timer = timer +1;
			}
	}
	}
	public Stack <Point> generateCircularPath(double radius) {
		Stack <Point> working = new Stack <Point>();
		Point point = new Point (this.getX(),this.getY());
		point.declare(point.x,point.y);
		working.push(point);
		for (double i =0; i < 6.28; i = i + 0.4) {
		point = new Point (this.getX()+ Math.sin(i)*radius,this.getY() - Math.cos(i)*radius + radius);
		point.declare(point.x,point.y);
		if (!Room.isColliding(point.hitbox())) {
			working.push(point);	
		} else {
			point.forget();
		}
		}
		
		return working;
	}
	public void followCircularPath (double radius, double speed){
		
		try {
		if (this.isColliding(nextPoint)) {
		if (path.isEmpty()) {
			this.setX(nextPoint.x);
			this.setY(nextPoint.y);
			circles = circles + 1;
			Point currentPosition = new Point (this.getX(),this.getY());
			path = this.generateCircularPath(radius); 
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
			}
		}
		} catch (NullPointerException e) {
			Point currentPosition = new Point (this.getX(),this.getY());
			path = this.generateCircularPath(radius); 
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
			if (this.isColliding(nextPoint)) {
				Point currentPosition = new Point (this.getX(),this.getY());
				nextPoint.forget();
				nextPoint = path.pop();
				slope = currentPosition.getSlope(nextPoint);
			}
			int slopeMagnatue; 
			if (slope > 0) {
			slopeMagnatue = (int) Math.floor(slope);
			} else {
			slopeMagnatue = (int) Math.round(slope);
			}
			if (slopeMagnatue < 0) {
				slopeMagnatue = slopeMagnatue * -1;
			}
			if (slopeMagnatue != 0) {
			if (nextPoint.x < this.x){
			if (!this.goXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.goX(this.getX()- ((1.0/slopeMagnatue) * speed));
				this.goY(this.getY()+(slope/slopeMagnatue) * speed);
			}  else {
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.goXandY(this.getX()+ ((1.0/slopeMagnatue) * speed), this.getY()+(slope/slopeMagnatue) * speed)) {
				reCheck = true;
				this.goX(this.getX()+ ((1.0/slopeMagnatue) * speed));
				this.goY(this.getY()+(slope/slopeMagnatue) * speed);
			}  else {
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			}
			} else {
			if (nextPoint.x < this.x){
			if (!this.goXandY(this.getX()- (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.goX(this.getX()- 1 * speed);
				this.goY(this.getY()+slope * speed);
			}  else {
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
			} else {
			if (!this.goXandY(this.getX()+ (1 * speed), this.getY()+slope * speed)) {
				reCheck = true;
				this.goX(this.getX()+ 1 * speed);
				this.goY(this.getY()+slope * speed);
			} else {
				if (reCheck) {
					Point currentPosition = new Point (this.getX(),this.getY());
					slope = currentPosition.getSlope(nextPoint);
					reCheck = false;
				}
			}
		}
	}
}
		
	/**
	 * uses pathfinding to fallow a pre generated path that trys to avoid walls.
	 * I did my best but its not perfect
	 * @param destanation the endpoint where the enemy should end up
	 * @param speed how fast as fuck the enmey is
	 */
	public void followPath (Point destanation, double speed) {
		if (!this.isColliding(destanation)) {
		if (path.isEmpty()) {
		Point currentPosition = new Point (this.getX(),this.getY());
		path = currentPosition.generatePath(destanation); 
		nextPoint = path.pop();
		slope = currentPosition.getSlope(nextPoint);
		}
		//System.out.println(slope);
		if (this.isColliding(nextPoint)) {
			Point currentPosition = new Point (this.getX(),this.getY());
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		int slopeMagnatue; 
		if (slope > 0) {
		slopeMagnatue = (int) Math.floor(slope);
		} else {
		slopeMagnatue = (int) Math.round(slope);
		}
		//System.out.println(slopeMagnatue);
		if (slopeMagnatue != 0) {
		if (nextPoint.x < this.x){
		if (!this.goXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
			reCheck = true;
			this.goX(this.getX()- ((1.0/slopeMagnatue) * speed));
			this.goY(this.getY()+(slope/slopeMagnatue) * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		} else {
		if (!this.goXandY(this.getX()+ ((1.0/slopeMagnatue) * speed), this.getY()+(slope/slopeMagnatue) * speed)) {
			reCheck = true;
			this.goX(this.getX()+ ((1.0/slopeMagnatue) * speed));
			this.goY(this.getY()+(slope/slopeMagnatue) * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		}
		} else {
		if (nextPoint.x < this.x){
		if (!this.goXandY(this.getX()- (1 * speed), this.getY()+slope * speed)) {
			reCheck = true;
			this.goX(this.getX()- 1 * speed);
			this.goY(this.getY()+slope * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		} else {
		if (!this.goXandY(this.getX()+ (1 * speed), this.getY()+slope * speed)) {
			reCheck = true;
			this.goX(this.getX()+ 1 * speed);
			this.goY(this.getY()+slope * speed);
		} else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		}
		}
		} else {
			pathFallowed = true;
		}
	}
	/**
	 * fallows a preset path from the map editor
	 * @param pathToTake the path the enemy takes
	 * @param speed how fast as fuck the enmey is
	 */
	public void followPresetPath (Stack <Point> pathToTake, double speed) {
		//System.out.println(path.isEmpty());
		if (path.isEmpty() && !pathToTake.isEmpty()) {
			Point currentPosition = new Point (this.getX(),this.getY());
			path = pathToTake; 
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		if (!(path.isEmpty() && this.isColliding(nextPoint)) ) {
		if (this.isColliding(nextPoint)) {
			Point currentPosition = new Point (this.getX(),this.getY());
			nextPoint = path.pop();
			slope = currentPosition.getSlope(nextPoint);
		}
		//System.out.println(slope);
		int slopeMagnatue; 
		if (slope > 0) {
		slopeMagnatue = (int) Math.floor(slope);
		} else {
		slopeMagnatue = (int) Math.round(slope);
		}
		if (slopeMagnatue < 0) {
			slopeMagnatue = slopeMagnatue * -1;
		}
		//System.out.println(slopeMagnatue);
		if (slopeMagnatue != 0) {
		if (nextPoint.getX() < this.getX()){
		if (!this.goXandY(this.getX()- ((1.0/slopeMagnatue) * speed),this.getY()+(slope/slopeMagnatue) * speed)) {
			reCheck = true;
			this.goX(this.getX()- ((1.0/slopeMagnatue) * speed));
			this.goY(this.getY()+(slope/slopeMagnatue) * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		} else {
		if (!this.goXandY(this.getX()+ ((1.0/slopeMagnatue) * speed), this.getY()+(slope/slopeMagnatue) * speed)) {
			reCheck = true;
			this.goX(this.getX()+ ((1.0/slopeMagnatue) * speed));
			this.goY(this.getY()+(slope/slopeMagnatue) * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		}
		} else {
		if (nextPoint.getX() < this.getX()){
		if (!this.goXandY(this.getX()- (1 * speed), this.getY()+slope * speed)) {
			reCheck = true;
			this.goX(this.getX()- 1 * speed);
			this.goY(this.getY()+slope * speed);
		}  else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		} else {
		if (!this.goXandY(this.getX()+ (1 * speed), this.getY()+slope * speed)) {
			reCheck = true;
			this.goX(this.getX()+ 1 * speed);
			this.goY(this.getY()+slope * speed);
		} else {
			if (reCheck) {
				Point currentPosition = new Point (this.getX(),this.getY());
				slope = currentPosition.getSlope(nextPoint);
				reCheck = false;
			}
		}
		}
		}
		} else {
			pathFallowed = true;
		}
	}
	/** 
	 * makes a group of this enemy move as a unit with one enmey acting as the leader while the rest fallow for more info please check the method in code
	 * @param radius how far the fallowing enemy can be from the leading enmey before the stop acting as a unit 
	 */
	//note the leading enemy with this AI will not move unless you move it all this AI does is comand the non leading enemies to fallow the leading enemy
	public void swarm (double radius) {
		this.diesNormally = false;
		if (leadingEnemy) {
			swarmBox.x = (int) (this.getX() - radius);
			swarmBox.y = (int) (this.getY() - radius);
			swarmBox.width = (int)(radius*2);
			swarmBox.height = (int)(radius*2);
		}
		if (!fallowingEnemy) {
		Iterator iter;
		iter = enemyList.iterator();
		boolean found = false;
		while (iter.hasNext()) {
			Enemy enemyToCheck = (Enemy) iter.next();
			if (enemyToCheck.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
				if (enemyToCheck.leadingEnemy && !enemyToCheck.equals(this)) {
				if (this.isColliding(enemyToCheck.swarmBox)) {
				found = true;
				enemyToFallow = enemyToCheck;
				break;
				}
				}
			}
		}
		if (found) {
			this.fallowingEnemy = true;
			this.leadingEnemy = false;
		} else {
			this.leadingEnemy = true;
			this.fallowingEnemy = false;
			swarmBox.x = (int) (this.getX() - radius);
			swarmBox.y = (int) (this.getY() - radius);
			swarmBox.width = (int)(radius*2);
			swarmBox.height = (int)(radius*2);
		}
		} else {
			
		/*		if (this.getX() > enemyToFallow.swarmBox.x +radius + radius/2) {
					this.goX(this.getX() - 1);
				}
				if (this.getX() < enemyToFallow.swarmBox.x + radius - radius/2) {
					this.goX(this.getX() + 1);
				}
				if (this.getY() > enemyToFallow.swarmBox.y + radius + radius/2) {
					this.goY(this.getY() - 1);
				}
				if (this.getY() < enemyToFallow.swarmBox.y +radius - radius/2) {
					this.goY(this.getY() + 1);
				}*/
				if (leadingEnemyXPrevios != enemyToFallow.getXPrevious() || leadingEnemyYPrevios != enemyToFallow.getYPrevious()) {
					leadingEnemyXPrevios = enemyToFallow.getXPrevious();
					leadingEnemyYPrevios = enemyToFallow.getYPrevious();
					BuggyBoi boiToFollow = (BuggyBoi) enemyToFallow;
					this.requestMovement(boiToFollow.getSpeed(),boiToFollow.getDirection());
				}
				if ( !this.isColliding(enemyToFallow.swarmBox)) {
					this.fallowingEnemy = false;
				}
		}
		if (this.health <= 0) {
			if (this.leadingEnemy) {
				Iterator iter;
				iter = enemyList.iterator();
				while(iter.hasNext()) {
					Enemy enemyToCheck = (Enemy) iter.next();
					if (enemyToCheck.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
						if (((this.getY() - enemyToCheck.getY() < radius && this.getY() - enemyToCheck.getY () > 0) || (enemyToCheck.getY() - this.getY() < radius && enemyToCheck.getY() - this.getY () > 0))&& ((this.getX() - enemyToCheck.getX() < radius && this.getX() - enemyToCheck.getX() > 0  ) || (enemyToCheck.getX() - this.getX() < radius && enemyToCheck.getX() - this.getX() > 0))) {
						enemyToCheck.fallowingEnemy =false;
						enemyToCheck.leadingEnemy = true;
						enemyToCheck.swarmBox.x = (int) (this.getX() - radius);
						enemyToCheck.swarmBox.y = (int) (this.getY() - radius);
						enemyToCheck.swarmBox.width = (int)(radius*2);
						enemyToCheck.swarmBox.height = (int)(radius*2);
						break;
						}
					}
				}
			}
			this.deathEvent();
		}
	}
	/**
	 * runs the suggested movement through a vector collision system and does the cloeset one that does not make it go towards a wall.  Note it has a built in system to make sure the enemy keeps that path instead of reverting back to the original plan after a couple of frames
	 * @param speed how fast to move
	 * @param direction the direction to move (in radians)
	 */
	public void requestMovement (int speed, double direction) {
	//	System.out.println(this.tryMovement(xToMove, yToMove)[0]);
		double [] coordinates = this.tryMovement(speed, direction );
		double xto = coordinates [0];
		double yto = coordinates[1];
		layer = 0;
		//System.out.println(yto);
		this.goX(this.getX() + xto);
		this.goY(this.getY() + yto);
		
	/*	if (this.tryMovement(speed, direction)[0] ==  xToMove && this.tryMovement(xToMove, yToMove)[1] == yToMove ) {
			this.goX(this.getX() + xto);
			this.goY(this.getY() + yto);
			double newXto = this.tryMovement(speed, direction)[0];
			double newYto = this.tryMovement(speed, direction)[1];
			if (newXto != xToMove && newYto != yToMove) {
				this.goX(this.getX() - xto);
				this.goY(this.getY() - yto);
				this.goX(this.getX() + priviosSuccessfulMovementX);
				this.goY(this.getY() + preiviosSuccessfulMovementY);
			
			} else {
				priviosSuccessfulMovementX = xto;
				preiviosSuccessfulMovementY = yto;
			}
			
		} else {
			if (!this.goX(this.getX() + xto)) {
				this.goX(this.getX() + this.priviosSuccessfulMovementX);
				
			}
			if (!this.goY(this.getY() + yto)) {
				this.goY(this.getY() + this.preiviosSuccessfulMovementY);
			}
			this.priviosSuccessfulMovementX = xto;
			this.preiviosSuccessfulMovementY = yto;
		}*/
	}
	/**
	 * runs the suggested movement through a vector collision system and returns the closest set of coordinates that forms a path that is not going into a wall.  When its current path will lead it into a wall of couse 
	 * note returns your original arguments if you are good to go from the start
	 * @param speed the distance to go
	 * @param direction the direction to move (in radians)
	 */
	public double [] tryMovement(int speed, double direction) {
		double [] bruh = new double [2];
		bruh [0] = 2;
		bruh [1] =2;
		return bruh;
	/*	int deviation1 = 0;
		int  deviation2 = 0;
		double direction3 = direction + 1.57;
		double direction2 = direction + 1.57;
		while (Room.doHitboxVectorCollison(this.hitbox(), this.getX() + Math.sin(direction3)*50,this.getY() +Math.cos(direction3)*50) != null) {
			deviation1 = deviation1 + 1;
			direction3 = direction3 + 0.1;
		}
		while (Room.doHitboxVectorCollison(this.hitbox(), this.getX() + Math.sin(direction2)*50,this.getY() +Math.cos(direction2)*50) != null) {
			deviation2 = deviation2 + 1;
			direction2 = direction2 - 0.1;
		}
		if (deviation2 == 0 && deviation1 == 0) {
			undecided = false;
			if (layer == 9) {
				double [] bruh = new double [2];
				if (Math.sin(direction + 1.57) > Math.cos(direction + 1.57)) {
				if (direction % 6.28 > 1.57  && direction % 6.28 < 4.71) {
				bruh [0] = Math.sin(direction + 1.57 + 0.9)* speed;
				bruh [1] = Math.cos(direction + 1.57 + 0.9)* speed;
				} else {
					bruh [0] = Math.sin(direction + 1.57 - 0.9)* speed;
					bruh [1] = Math.cos(direction + 1.57 - 0.9)* speed;	
				}
				layer = 0;
				return bruh;
				} else {
					if (direction % 6.28 > 4.71 || direction %6.28 < 1.57) {
					bruh [0] = Math.sin(direction + 1.57 - 0.9)* speed;
					bruh [1] = Math.cos(direction + 1.57 - 0.9)* speed;
					} else {
						bruh [0] = Math.sin(direction + 1.57 + 0.9)* speed;
						bruh [1] = Math.cos(direction + 1.57 + 0.9)* speed;	
					}
					layer = 0;
					return bruh;
				}
			} else {
			layer = layer + 1;
			if (Math.sin(direction + 1.57) > Math.cos(direction + 1.57)) {
			if (direction % 6.28 > 1.57  && direction % 6.28 < 4.71) {
			return this.tryMovement(speed, direction  - 0.1);
			} else {
			return this.tryMovement(speed, direction  + 0.1);	 	
			}
			} else {
				if (direction % 6.28 > 4.71 || direction %6.28 < 1.57) {
					return this.tryMovement(speed, direction  + 0.1);
					} else {
					return this.tryMovement(speed, direction  - 0.1);	 	
					}	
			}
			}
		} else {
			if (!undecided) {
				undecided = true;
				if (deviation1 < deviation2) {
					tieBreaker = true;
				} else {
					tieBreaker = false;
				}
			}
			if (this.closeTo(deviation1, deviation2, 2)) {
				if (tieBreaker) {
					deviation1 = 0;
				} else {
					deviation2 = 0;
				}
			}
		}
	//	System.out.println("1 " + deviation1);
	//	System.out.println("2 " + deviation2);
		if (deviation1 < deviation2) {
		tieBreaker = true;
		double [] bruh = new double [2];
		bruh [0] = Math.sin(direction3)* speed;
		bruh [1] = Math.cos(direction3)* speed;
		correctDirection = direction3;
		if (direction != oldDireciton) {
			oldDireciton = direction;
		}
		//System.out.println("debug");
		return bruh;
		} else {
			tieBreaker = false;
			double [] bruh = new double [2];
			bruh [0] = Math.sin(direction2)* speed;
			bruh [1] = Math.cos(direction2)* speed;
			correctDirection = direction2;
			if (direction != oldDireciton) {
				oldDireciton = direction;
			}
			//System.out.println(Math.sin(direction2) * speed);
		//	System.out.println("debug");
			return bruh;
		}*/
	}
	/** returns true if a number is within a certian distance from antoher number
	 * 
	 * @param numToCheck the first number to check
	 * @param secondNum the second number to check
	 * @param degree the amount they can be from each other
	 * @return true if they are close false if not
	 */
	public boolean closeTo (int numToCheck, int secondNum, int degree) {
		if (numToCheck >= secondNum && numToCheck - degree <= secondNum) {
			return true;
		}
		if (numToCheck <= secondNum && numToCheck + degree >= secondNum) {
			return true;
		}
		return false;
	}
}