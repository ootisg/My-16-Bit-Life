package gameObjects;

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
	public static String[] enemyList = new String [0];
	public int health = 1;
	int momentum;
	protected double baseDamage = 2.5;
	boolean jumping;
	public int defence;
	DamageText text;
	boolean falls;
	int currentSpeed;
	int timer;
	boolean jumpDone;
	int countdown;
	boolean lockedRight;
	boolean diesNormally;
	public Enemy () {
		momentum = 0;
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
	//sets up a special death if your into that kinda thing
	public void setDeath (boolean death) {
		diesNormally = false;
	}
	public void attackEvent () {
		player.damage (this.baseDamage);
	}
	public void deathEvent () {
		this.forget ();
	}
	//returns true if there is a wall in between the enemy and the player
		public boolean checkPlayerPositionRelativeToWalls () {
				for (int i = 0; true; i++) {
					this.setX(this.getX () + i);
					if (Room.isColliding(this.hitbox())) {
						if (player.getX() > this.getX()) {
						this.setX(this.getX() - i);
						return true;
						}
					}
					if ((int)this.getX() == (int)player.getX()) {
						this.setX(this.getX() - i);
						break;
					}
					this.setX(this.getX() - i*2);
					if (Room.isColliding(this.hitbox())) {
						if (player.getX() < this.getX()) {
							this.setX(this.getX() + i);
						return true;
						}
					}
					if ((int)this.getX() == (int)player.getX()) {
						this.setX(this.getX() + i);
						break;
					}
					this.setX(this.getX() + i);
				}
				return false;
		}
	public void damage (int amount) {
		text = new DamageText (amount, this.getX(), this.getY());
		text.declare(this.getX(), this.getY());
		amount = amount - defence;
		if(amount <= 0){
			amount = 1;
		}
		this.health = health - amount;
	}
	public void setHealth (int health) {
		this.health = health;
	}
	public int getHealth () {
		return this.health;
	}
	// actions you can get your enemy to do (based on the assumption that your enemy falls)
	public void jump (int horizontalBaseSpeed, int verticalBaseSpeed) {
		timer = timer + 1;
		if (lockedRight) {
			if (timer <= 6) {
			if (Room.isColliding(this.hitbox())) {
				if (this.checkIfColidingWithWall(horizontalBaseSpeed)) {
					if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed))){
					this.setY(this.getY() - verticalBaseSpeed);
					}
				} else {
					this.setX(this.getX() +horizontalBaseSpeed);
				}
			} else {
			this.setY(this.getY() - verticalBaseSpeed);
			this.setX(this.getX() + horizontalBaseSpeed);
			}
			}
			if (timer > 6 && timer <=11) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(horizontalBaseSpeed * 1.1)) {
						if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed * 1.1))){
						this.setY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.setX(this.getX() +(horizontalBaseSpeed * 1.1));
					}
				} else {
				this.setY(this.getY() - (verticalBaseSpeed * 1.1));
				this.setX(this.getX() + (horizontalBaseSpeed * 1.1));
				}
			}
			if (timer > 11 && timer <= 16) {
				
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(horizontalBaseSpeed * 1.1)) {
						if (!this.checkIfStuckInCeling(-1 *(verticalBaseSpeed *1.1))){
						this.setY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.setX(this.getX() + (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.setY(this.getY() - (verticalBaseSpeed *1.1 ));
				this.setX(this.getX() + (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 16 && timer <= 21) {
				if (!(this.checkIfColidingWithWall((horizontalBaseSpeed * 1.1)))) {
					this.setX(this.getX() + (horizontalBaseSpeed * 1.1));
			} else {
			this.setX(this.getX() + (horizontalBaseSpeed * 1.1));
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
						this.setY(this.getY() - verticalBaseSpeed);
						}
					} else {
						this.setX(this.getX() -horizontalBaseSpeed);
					}
				} else {
				this.setY(this.getY() - verticalBaseSpeed);
				this.setX(this.getX() - horizontalBaseSpeed);
				}
			}
			if (timer > 6 && timer <=11) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1))) {
						if (!this.checkIfStuckInCeling(-1 * (verticalBaseSpeed * 1.1))){
						this.setY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.setY(this.getY() - (verticalBaseSpeed * 1.1));
				this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
			}
			if (timer > 11 && timer <= 16) {
				if (Room.isColliding(this.hitbox())) {
					if (this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1))) {
						if (!this.checkIfStuckInCeling(-1 * (verticalBaseSpeed * 1.1))){
						this.setY(this.getY() - (verticalBaseSpeed * 1.1));
						}
					} else {
						this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
					}
				} else {
				this.setY(this.getY() - (verticalBaseSpeed * 1.1));
				this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 16 && timer <= 21) {
					if (!(this.checkIfColidingWithWall(-1 * (horizontalBaseSpeed * 1.1)))) {
						this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
				} else {
				this.setX(this.getX() - (horizontalBaseSpeed * 1.1));
				}
				}
			if (timer > 21) {
				timer = 0;
				jumpDone = true;
			}
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
			if (!this.getSprite().equals(jumpSprite)) {
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
	
}