package enemys;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import gameObjects.BreakableObject;
import gameObjects.DamageText;
import gameObjects.ItemDropRate;
import gameObjects.Point;
import items.Item;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import projectiles.DirectionBullet;
import resources.Sprite;
import weapons.Weapon;


public abstract class Enemy extends BreakableObject {
	//Template for enemies
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
	int timer = 0;
	ArrayList <ItemDropRate> drops = new ArrayList<ItemDropRate>();
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
		this.setPusablity(true);
	}
	@Override
	public void frameEvent () {
		
		//Call the enemy's frame event
		enemyFrame ();
		super.frameEvent();
		
		//'pick up' dropped items (this is incorrect)
		timer = timer + 1;
		if (timer == 2) {
			if (this.isCollidingChildren("Item")) {
				for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++ ){
					if (!((Item)this.getCollisionInfo().getCollidingObjects().get(i) instanceof Weapon)) {
						drops.add(new ItemDropRate ((Item)this.getCollisionInfo().getCollidingObjects().get(i),100));
						this.getCollisionInfo().getCollidingObjects().get(i).forget();
					}
				}
			}
		}
		
		//Handle knockback
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
		
		//Check for death
		if ((this.health <= 0) && diesNormally ) {
			this.deathEvent();
		}
		
		//Deal contact damage
		try {
			if (isColliding (Player.getActivePlayer())) {
				attackEvent ();
			}
		} catch (NullPointerException e) {
			//wtf?
		}
		
		//Check for collision with floors/(walls?)
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

		//Accelerate the object as it falls, and adjust its position
		if (!onFloor && falls) {
			if (this.setmomentum == 420) {
				momentum = momentum + 1;
				if (momentum < 6) {
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
		
		//Cancel momentum if on the ground
		if (onFloor && momentum >= 1){
			momentum = 0;
			if (this.currentSpeed != 1) {
			this.setY(getY()- currentSpeed);
			}
			currentSpeed = 0;
		}
	}
	
	//Set the drops for this enemy; perhaps we should use JSON files for this? (ehh probably not)
	public void setDrops (ItemDropRate [] drops) {
		for (int i = 0; i < drops.length; i++) {
			this.drops.add(drops[i]);
		}
	}
	
	//Sets the enemy's base damage
	public void setBasePower (int power) {
		baseDamage = power;
	}
	
	//Override this for your convenience
	public void enemyFrame () {
		
	}
	
	//override to set code that runs on drop
	public void dropStuff() {
		ArrayList<Item> working = new ArrayList <Item> ();
		for (int i = 0; i < drops.size(); i++) {
			Random rand = new Random ();
			for (int j = 0; j <drops.get(i).getMaxDrop(); j ++) {
				if (j < drops.get(i).getMinDrop()){
					working.add(drops.get(i).getItem());
				} else {
					int dropChance = rand.nextInt(100) + 1;
					if (dropChance < drops.get(i).getDropRate()) {
						working.add(drops.get(i).getItem());
					}
				}
			}
		}
		Item [] moreWorking = new Item [working.size()];
		for (int i = 0; i < working.size(); i++) {
			moreWorking[i] = working.get(i);
		}
		this.Break(moreWorking, 0, 1, 7*3.1415926535/6, 11*Math.PI/6);
	}
	
	//Suffocate the enemy
	public void suffocate () {
		this.damage(this.getHealth()/10);
		if (this.health <= 0) {
			this.deathEvent();
		}
	}
	
	/**
	 * allows you to set the moment of the falling to a constant
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
	
	//Knockbackkkkk
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
	public boolean goX (double val) {
		if (this.appliedStatuses[3] && val - this.getX() != 0) {
			if (val - this.getX() > 1) {
				//Remove 1 from val if going in a positive direction
				val = val -1;
			} else {
				if ( val - this.getX() < -1 ) {
					//Add 1 to val if going in a negative direction
					val = val + 1;
				} else {
					//Prevents goX from being called more than once a frame (?)
					if (!moveable) {
						val = this.getX();
						moveable = true;
					} else {
						moveable = false;
					}
				}
			}
		}
		
		//Move the Enemy with the new val from the above logic (maybe use super.goX (val))
		this.xprevious = x;
		spriteX = (spriteX + (val - x));
		x = val;
		if (Room.isColliding(this)) {
			x = xprevious;
			spriteX = (spriteX - (val- x));
			return false;
		} else {
			return true;
		}
	}
	
	//The pleb way to set x
	public void setXTheOldFasionWay(double val) {
		super.setX(val);
	}
	
	@Override
	public void setX (double val) {
		//goX, but doesn't check for wall collision
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
	
	//Override to attack special ways or something like that
	public void attackEvent () {
		Player.getActivePlayer().damage (this.baseDamage);
	}
	
	//Handle death
	public void deathEvent () {
		//Add to registered kills
		if (!Player.getInventory().checkKill(this)) {
			Player.getInventory().addKill(this);
		}
		//All the other stuff
		enemyList.remove(this);
		this.dropStuff();
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
	 * returns true if the Jeffrey.getActivePlayer() is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the Jeffrey.getActivePlayer() is in that box
	 */
	public boolean isNearPlayerX (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - Player.getActivePlayer().getX()  <= -rangebound1Right) && (this.getX() - Player.getActivePlayer().getX()  >= -rangebound2Right)) || ((Player.getActivePlayer().getX() >= this.getX() - rangebound2Left) &&(Player.getActivePlayer().getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() )) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * returns true if the Jeffrey.getActivePlayer() is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the Jeffrey.getActivePlayer() is in that box
	 */
	public boolean isNearPlayerY (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - Player.getActivePlayer().getY()  >= rangebound1Top) && (this.getY() - Player.getActivePlayer().getY()  <= rangebound2Top)) || ((Player.getActivePlayer().getY() >= this.getY() + rangebound1Bottom) &&(Player.getActivePlayer().getY() <= this.getY() + rangebound2Bottom)) && !this.checkPlayerPositionRelativeToCellings() ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * returns true if the Jeffrey.getActivePlayer() is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the Jeffrey.getActivePlayer() is in that box
	 */
	public boolean isNearPlayerXWithoutCheckingWalls (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - Player.getActivePlayer().getX()  <= -rangebound1Right) && (this.getX() - Player.getActivePlayer().getX()  >= -rangebound2Right)) || ((Player.getActivePlayer().getX() >= this.getX() - rangebound2Left) &&(Player.getActivePlayer().getX() <= this.getX() - rangebound1Left))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * returns true if the Jeffrey.getActivePlayer() is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the Jeffrey.getActivePlayer() is in that box
	 */
	public boolean isNearPlayerYWithoutCheckingWalls (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - Player.getActivePlayer().getY()  >= rangebound1Top) && (this.getY() - Player.getActivePlayer().getY()  <= rangebound2Top)) || ((Player.getActivePlayer().getY() >= this.getY() + rangebound1Bottom) &&(Player.getActivePlayer().getY() <= this.getY() + rangebound2Bottom))) {
			return true;
		} else {
			return false;
		}
	}
	//returns true if there is a celling or floor between the enemy and the Jeffrey.getActivePlayer()
			public boolean checkPlayerPositionRelativeToCellings () {
				double x = this.getX();
				super.setX(Player.getActivePlayer().getX());
					for (int i = 0; true; i++) {
						this.setY(this.getY () + i);
						if (Room.isColliding(this)) {
							if (Player.getActivePlayer().getY() > this.getY()) {
							this.setY(this.getY() - i);
							super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)Player.getActivePlayer().getY()) {
							this.setY(this.getY() - i);
							break;
						}
						this.setY(this.getY() - i*2);
						if (Room.isColliding(this)) {
							if (Player.getActivePlayer().getY() < this.getY()) {
								this.setY(this.getY() + i);
								super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)Player.getActivePlayer().getY()) {
							this.setY(this.getY() + i);
							break;
						}
						this.setY(this.getY() + i);
					}
					super.setX(x);
					return false;
			}
	//returns true if there is a wall between the enemy and the Jeffrey.getActivePlayer()
		public boolean checkPlayerPositionRelativeToWalls () {
				for (int i = 0; true; i++) {
					super.setX(this.getX () + i);
					if (Room.isColliding(this)) {
						if (Player.getActivePlayer().getX() > this.getX()) {
						super.setX(this.getX() - i);
						return true;
						}
					}
					if ((int)this.getX() == (int)Player.getActivePlayer().getX()) {
						super.setX(this.getX() - i);
						break;
					}
					super.setX(this.getX() - i*2);
					if (Room.isColliding(this)) {
						if (Player.getActivePlayer().getX() < this.getX()) {
							super.setX(this.getX() + i);
						return true;
						}
					}
					if ((int)this.getX() == (int)Player.getActivePlayer().getX()) {
						super.setX(this.getX() + i);
						break;
					}
					super.setX(this.getX() + i);
				}
				return false;
		}
		
	/**
	 * Damage the player by the given amount, taking weakness and defense into account
	 * @param amount the amount of damage to deal
	 */
	public void damage (int amount) {
		
		if (Player.getActivePlayer().status.checkStatus("Powerful")) {
			amount = (int) ((amount * 1.2) - defence);
			if(amount <= 0){
				amount = 1;
			}
			text = new DamageText (amount * 1.2, this.getX(), this.getY(), 0 );	
		} else {
			amount = amount - defence;
			if(amount <= 0){
				amount = 1;
			}
		text = new DamageText (amount, this.getX(), this.getY(), 0);
		}
		//System.out.println(amount);
		text.declare(this.getX(), this.getY());
		this.health = health - amount;
	}
	
	/**
	 * Set the health of this enemy to the given amount
	 * @param health the amount to set the health to
	 */
	public void setHealth (int health) {
		this.health = health;
	}
	
	/**
	 * Returns the current health of this enemy
	 * @return this enemy's current health
	 */
	public int getHealth () {
		return this.health;
	}
	
}