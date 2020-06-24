package enemys;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import gameObjects.DamageText;
import gameObjects.Point;
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
	protected int defence;
	int knockbackTime;
	DamageText text;
	boolean beingKnockedBack;
	boolean direction;
	double oldDireciton;
	double correctDirection;
	boolean falls;
	Random RNG;
	int currentSpeed;
	boolean moveable;
	boolean adjustedSpeed;
	boolean diesNormally;
	int setmomentum= 420;
	public Enemy () {
		enemyList.add(this);
		momentum = 0;
		adjustedSpeed = false;
		correctDirection = 0;
		//status indexes are the same as in status.java so check there for info on that
		appliedStatuses = new Boolean [10];
		Arrays.fill(appliedStatuses, false);
		beingKnockedBack = false;
		knockbackTime = 0;
		moveable = false;
		RNG = new Random();
		falls = false;
		currentSpeed = 0;
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
		
			if (!(Room.isColliding(this))){
				this.setX(getX() - 1);
				if (Room.isColliding(this)){
					colidingSide = true;
				}
				this.setX(getX() + 1);
				}
			if (!(Room.isColliding(this))){
				this.setX(getX() + 1);
				if (Room.isColliding(this)){
					colidingSide = true;
				}
				this.setX(getX() - 1);
				}
			if ((!(Room.isColliding(this)) || !colidingSide) && !this.checkIfStuckInCeling(1) ){
				this.setY(getY() + currentSpeed + 1);
				if (Room.isColliding(this)){
					onFloor = true;
				}
				this.setY(getY() - (currentSpeed + 1) );
				}
		}

		if (!onFloor && falls) {
			if (this.setmomentum == 420) {
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
	} else {
		this.setY(this.getY() + setmomentum );
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
	public void suffocate () {
		this.damage(this.getHealth()/10);
		if (this.health <= 0) {
			this.deathEvent();
		}
	}
	/**
	 * allows you to set the moment of the falling to a consont
	 * @parm momentumConstant the new momenutm
	 * (i suspect it may cause a bug with enemys being stuck in the floor but it has not happened yet)
	 */
	public void setMomentum (int momentumConstant ) {
		setmomentum = momentumConstant;
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
		if (Room.isColliding(this)) {
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
		if (!Jeffrey.getInventory().checkKill(this)) {
			Jeffrey.getInventory().addKill(this);
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
	/**
	 * returns true if the player is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the player is in that box
	 */
	public boolean isNearPlayerX (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - player.getX()  <= -rangebound1Right) && (this.getX() - player.getX()  >= -rangebound2Right)) || ((player.getX() >= this.getX() - rangebound2Left) &&(player.getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() )) {
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
		if ( ((this.getY() - player.getY()  >= rangebound1Top) && (this.getY() - player.getY()  <= rangebound2Top)) || ((player.getY() >= this.getY() + rangebound1Bottom) &&(player.getY() <= this.getY() + rangebound2Bottom)) && !this.checkPlayerPositionRelativeToCellings() ) {
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
		if ( ((this.getX() - player.getX()  <= -rangebound1Right) && (this.getX() - player.getX()  >= -rangebound2Right)) || ((player.getX() >= this.getX() - rangebound2Left) &&(player.getX() <= this.getX() - rangebound1Left))) {
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
		if ( ((this.getY() - player.getY()  >= rangebound1Top) && (this.getY() - player.getY()  <= rangebound2Top)) || ((player.getY() >= this.getY() + rangebound1Bottom) &&(player.getY() <= this.getY() + rangebound2Bottom))) {
			return true;
		} else {
			return false;
		}
	}
	//returns true if there is a celling or floor between the enemy and the player
			public boolean checkPlayerPositionRelativeToCellings () {
				double x = this.getX();
				super.setX(player.getX());
					for (int i = 0; true; i++) {
						this.setY(this.getY () + i);
						if (Room.isColliding(this)) {
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
						if (Room.isColliding(this)) {
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
					if (Room.isColliding(this)) {
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
					if (Room.isColliding(this)) {
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
		if (player.checkIfPowerful()) {
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
	
	public int getHealth () {
		return this.health;
	}
	

}