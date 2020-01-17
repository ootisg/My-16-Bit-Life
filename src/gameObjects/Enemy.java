package gameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
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
	boolean jumping;
	public int defence;
	boolean moveRight;
	int knockbackTime;
	DamageText text;
	int xToMove;
	boolean beingKnockedBack;
	boolean direction;
	int yToMove;
	boolean falls;
	Random RNG;
	int currentSpeed;
	boolean canFuckWithSprite;
	boolean moveable;
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
	boolean chargeing;
	public Enemy () {
		enemyList.add(this);
		momentum = 0;
		patrolBothWays = false;
		adjustedSpeed = false;
		chargeTimer =0;
		//status indexes are the same as in status.java so check there for info on that
		appliedStatuses = new Boolean [10];
		Arrays.fill(appliedStatuses, false);
		canFuckWithSprite = true;
		beingKnockedBack = false;
		xToMove = 0;
		knockbackTime = 0;
		yToMove = 0;
		moveing = true;
		moveable = false;
		waitForCollison = 0;
		chargeing = false;
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
	// actions you can get your enemy to do 
	public void keepChargeing (int timeToCharge) {
		if (!chargeing) {
			getChargeLine();
			chargeing = true;
		}
		Charge(timeToCharge);
	}
	public void Charge (int timeToCharge) {
		if (!chargeing && !(xToMove == 0) && !(yToMove == 0)) {
			chargeing = true;
		}
		if (!(this.goX(this.getX() + xToMove) && this.goY(this.getY() + yToMove))) {
			xToMove = 0;
			yToMove = 0;
			chargeing = false;
		}
if (chargeTimer == timeToCharge) {
		xToMove = 0;
		yToMove = 0;
		chargeing = false;
		}
		chargeTimer = chargeTimer + 1;
		if (xToMove == 0 && yToMove == 0) {
			chargeTimer = 0;
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
	public boolean isNearPlayerX (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - GameCode.testJeffrey.getX()  <= -rangebound1Right) && (this.getX() - GameCode.testJeffrey.getX()  >= -rangebound2Right)) || ((GameCode.testJeffrey.getX() >= this.getX() - rangebound2Left) &&(GameCode.testJeffrey.getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() )) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNearPlayerY (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - GameCode.testJeffrey.getY()  >= rangebound1Top) && (this.getY() - GameCode.testJeffrey.getY()  <= rangebound2Top)) || ((GameCode.testJeffrey.getY() >= this.getY() + rangebound1Bottom) &&(GameCode.testJeffrey.getY() <= this.getY() + rangebound2Bottom)) && !this.checkPlayerPositionRelativeToCellings() ) {
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
}