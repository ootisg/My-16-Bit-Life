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
import players.Jeffrey;
import projectiles.DirectionBullet;
import resources.Sprite;


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
	}
	@Override
	public void frameEvent () {
		enemyFrame ();
		super.frameEvent();
		timer = timer + 1;
		if (timer == 2) {
			if (this.isCollidingChildren("Item")) {
				for (int i = 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++ ){
					drops.add(new ItemDropRate ((Item)this.getCollisionInfo().getCollidingObjects().get(i),100));
					this.getCollisionInfo().getCollidingObjects().get(i).forget();
				}
			}
		}
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
		if (isColliding (Jeffrey.getActiveJeffrey())) {
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
	public void setDrops (ItemDropRate [] drops) {
		for (int i = 0; i < drops.length; i++) {
			this.drops.add(drops[i]);
		}
	}
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
		Jeffrey.getActiveJeffrey().damage (this.baseDamage);
	}
	public void deathEvent () {
		if (!Jeffrey.getInventory().checkKill(this)) {
			Jeffrey.getInventory().addKill(this);
		}
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
	 * returns true if the Jeffrey.getActiveJeffrey() is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the Jeffrey.getActiveJeffrey() is in that box
	 */
	public boolean isNearPlayerX (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - Jeffrey.getActiveJeffrey().getX()  <= -rangebound1Right) && (this.getX() - Jeffrey.getActiveJeffrey().getX()  >= -rangebound2Right)) || ((Jeffrey.getActiveJeffrey().getX() >= this.getX() - rangebound2Left) &&(Jeffrey.getActiveJeffrey().getX() <= this.getX() - rangebound1Left) && !this.checkPlayerPositionRelativeToWalls() )) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the Jeffrey.getActiveJeffrey() is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the Jeffrey.getActiveJeffrey() is in that box
	 */
	public boolean isNearPlayerY (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - Jeffrey.getActiveJeffrey().getY()  >= rangebound1Top) && (this.getY() - Jeffrey.getActiveJeffrey().getY()  <= rangebound2Top)) || ((Jeffrey.getActiveJeffrey().getY() >= this.getY() + rangebound1Bottom) &&(Jeffrey.getActiveJeffrey().getY() <= this.getY() + rangebound2Bottom)) && !this.checkPlayerPositionRelativeToCellings() ) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the Jeffrey.getActiveJeffrey() is within a certain box that is centered at the enemy
	 * @param rangebound1Right how far from the enmey is the left corner of the box that is to the right of the enmey
	 * @param rangebound2Righthow far from the enmey is the right corner of the box that is to the right of the enmey
	 * @param rangebound1Left how far from the enmey is the right corner of the box that is to the left of the enmey
	 * @param rangebound2Lefthow far from the enmey is the left corner of the box that is to the left of the enmey
	 * @return if the Jeffrey.getActiveJeffrey() is in that box
	 */
	public boolean isNearPlayerXWithoutCheckingWalls (int rangebound1Right, int rangebound2Right, int rangebound1Left, int rangebound2Left) {
		if ( ((this.getX() - Jeffrey.getActiveJeffrey().getX()  <= -rangebound1Right) && (this.getX() - Jeffrey.getActiveJeffrey().getX()  >= -rangebound2Right)) || ((Jeffrey.getActiveJeffrey().getX() >= this.getX() - rangebound2Left) &&(Jeffrey.getActiveJeffrey().getX() <= this.getX() - rangebound1Left))) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * returns true if the Jeffrey.getActiveJeffrey() is within a certain box that is centered at the enemy
	 * @param rangebound1Top how far from the enmey is the bottom corner of the box that is above the enmey
	 * @param rangebound2Top how far from the enmey is the top corner of the box that is above the enmey
	 * @param rangebound1Bottom how far from the enmey is the top corner of the box that is below the enmey
	 * @param rangebound2Bottom how far from the enmey is the bottom corner of the box that is below the enmey
	 * @return if the Jeffrey.getActiveJeffrey() is in that box
	 */
	public boolean isNearPlayerYWithoutCheckingWalls (int rangebound1Top, int rangebound2Top, int rangebound1Bottom, int rangebound2Bottom) {
		if ( ((this.getY() - Jeffrey.getActiveJeffrey().getY()  >= rangebound1Top) && (this.getY() - Jeffrey.getActiveJeffrey().getY()  <= rangebound2Top)) || ((Jeffrey.getActiveJeffrey().getY() >= this.getY() + rangebound1Bottom) &&(Jeffrey.getActiveJeffrey().getY() <= this.getY() + rangebound2Bottom))) {
			return true;
		} else {
			return false;
		}
	}
	//returns true if there is a celling or floor between the enemy and the Jeffrey.getActiveJeffrey()
			public boolean checkPlayerPositionRelativeToCellings () {
				double x = this.getX();
				super.setX(Jeffrey.getActiveJeffrey().getX());
					for (int i = 0; true; i++) {
						this.setY(this.getY () + i);
						if (Room.isColliding(this)) {
							if (Jeffrey.getActiveJeffrey().getY() > this.getY()) {
							this.setY(this.getY() - i);
							super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)Jeffrey.getActiveJeffrey().getY()) {
							this.setY(this.getY() - i);
							break;
						}
						this.setY(this.getY() - i*2);
						if (Room.isColliding(this)) {
							if (Jeffrey.getActiveJeffrey().getY() < this.getY()) {
								this.setY(this.getY() + i);
								super.setX(x);
							return true;
							}
						}
						if ((int)this.getY() == (int)Jeffrey.getActiveJeffrey().getY()) {
							this.setY(this.getY() + i);
							break;
						}
						this.setY(this.getY() + i);
					}
					super.setX(x);
					return false;
			}
	//returns true if there is a wall between the enemy and the Jeffrey.getActiveJeffrey()
		public boolean checkPlayerPositionRelativeToWalls () {
				for (int i = 0; true; i++) {
					super.setX(this.getX () + i);
					if (Room.isColliding(this)) {
						if (Jeffrey.getActiveJeffrey().getX() > this.getX()) {
						super.setX(this.getX() - i);
						return true;
						}
					}
					if ((int)this.getX() == (int)Jeffrey.getActiveJeffrey().getX()) {
						super.setX(this.getX() - i);
						break;
					}
					super.setX(this.getX() - i*2);
					if (Room.isColliding(this)) {
						if (Jeffrey.getActiveJeffrey().getX() < this.getX()) {
							super.setX(this.getX() + i);
						return true;
						}
					}
					if ((int)this.getX() == (int)Jeffrey.getActiveJeffrey().getX()) {
						super.setX(this.getX() + i);
						break;
					}
					super.setX(this.getX() + i);
				}
				return false;
		}
	public void damage (int amount) {
		if (Jeffrey.getActiveJeffrey().checkIfPowerful()) {
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