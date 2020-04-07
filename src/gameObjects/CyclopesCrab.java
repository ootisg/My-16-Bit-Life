package gameObjects;

import map.Room;
import resources.Sprite;
import weapons.AimableWeapon;
import weapons.CrabGun;

public class CyclopesCrab extends Enemy {
	
	public static final Sprite crabLeft = new Sprite ("resources/sprites/config/Cyclops_Crab_1_Left.txt");
	public static final Sprite crabRight = new Sprite ("resources/sprites/config/Cyclops_Crab_1_Right.txt");
	public static final Sprite crabTurning = new Sprite ("resources/sprites/config/Cyclops_Crab_1_Turning.txt");
	public static final Sprite crabGun = new Sprite ("resources/sprites/crab_gun.png");
	public static final Sprite crabGunFiring = new Sprite ("resources/sprites/config/Cyclops_Crab_Gun_Firing.txt");
	public static final Sprite crabGunBullet = new Sprite ("resources/sprites/config/Cyclops_Crab_Gun_Bullet.txt");
	private boolean firstRun = true;
	private boolean moveRight;
	private boolean turrning;
	private boolean chillForASecond;
	private int finishedAnimationFrames;
	private int momentum;
	private boolean stuck;
	private boolean notStuck;
	CrabGun gun;
	CrabGun otherGun;
	public CyclopesCrab(){
		this.setSprite(crabLeft);
		this.getAnimationHandler().setFrameTime (166.7);
		this.setHitboxAttributes(0, 0, 24, 12);
		this.health = 38;
		chillForASecond = false;
		turrning = false;
		finishedAnimationFrames = 0;
		this.setDeath(false);
		moveRight = false;
		this.defence = 6;
		momentum = 0;
		stuck = false;
		notStuck = true;
		try {
		if (this.getVariantAttribute("Gun").equals("No")) {
			firstRun = false;
		}
		} catch (NullPointerException e) {
			
		}
		otherGun = new CrabGun (crabGun);
		gun = new CrabGun (crabGun);
		
	}
	@Override 
	public String checkName () {
		return "CYCLOPSE CRAB";
	}
	@Override
	public String checkEntry () {
		return "ITS UNCLEAR WHERE THESE CRABS GET THERE BULLETS FROM JUST LIKE IMAGINE OWNING A GUN SHOP AND A BUNCH OF CRABS WALK IN AND PURCHASE YOU ENTIRE STOCK LIKE THAT WOULD BE WEIRD RIGHT ITS NOT JUST ME";
	}
	@Override
	public void enemyFrame(){
		if (this.getHealth() <= 0) {
			gun.forget();
			otherGun.forget();
			this.deathEvent();
		}
		if (firstRun) {
			firstRun = false;
			gun.declare(this.getX() + 20,this.getY());
			otherGun.declare(this.getX(),this.getY());
			otherGun.getAnimationHandler().setFlipHorizontal(true);
		}
		gun.setX(this.getX() + 20);
		gun.setY(this.getY());
		otherGun.setX(this.getX());
		otherGun.setY(this.getY());
		boolean lowered;
		lowered = false;
		boolean onFloor;
		onFloor = false;
		boolean moved;
		moved = false;
		int height;
		height = 0;
		boolean toClimbOrNotToClimb;
		toClimbOrNotToClimb = false;
		if (player.getX() - this.getX() < 75 && player.getY() - this.getY() < 370){
			if (player.getX() >= this.getX()){
				if (player.getX() - this.getX() > 4){
					if (!stuck){
					this.setX(getX() + 2);
					}
					if (!moveRight){
					moveRight = true;
					turrning = true;
					if (stuck){
						notStuck = false;
					}
					stuck = false;
				}
			}		
		} else {
				if (player.getX() - this.getX() < 4){
				if (!stuck){
				this.setX(getX() - 2);
				}
				if (moveRight){
					turrning = true;
					moveRight = false;
					if (stuck){
						notStuck = false;
					}
					stuck = false;
				}
			}
			}
		} else {
			if (moveRight){
				setY(getY() - 1);
				setX(getX() + 1);
			} else {
				setX(getX() - 1);
				setY(getY() - 1);
			}
			if (Room.isColliding(this)){
				chillForASecond = true;
				turrning = true;
			}
			if (moveRight){
				setX(getX() - 1);
				setY(getY() + 1);
			} else {
				setX(getX() + 1);
				setY(getY() + 1);
			}
			if (chillForASecond){
				moveRight = !moveRight;
				chillForASecond = false;
			}
			if (moveRight){
				this.setX(getX() + 2);
			} else {
				this.setX(getX()-2);
			}
		}
		if (turrning){
			if(!(this.getSprite() == crabTurning)){
				setSprite(crabTurning);
			}
			finishedAnimationFrames = finishedAnimationFrames + 1;
			if(finishedAnimationFrames == 10){
				turrning = false;
				finishedAnimationFrames = 0;
				if (moveRight){
					setSprite (crabRight);
				} else {
					setSprite (crabLeft);
				}
			}
		}
		if (Room.isColliding(this)){
			this.setY(getY() - 1);
			lowered = true;
			if (!(Room.isColliding(this))){
				this.setY(getY() + 1);
				lowered = false;
				if (moveRight){
					this.setX(getX() - 1);
					moved = true;
				} else {
					this.setX(getX() + 1);
					moved = true;
				}
				if (Room.isColliding(this)){
					onFloor = true;
				}
				}
			}
		if (lowered){
			this.setY(getY() + 1);
		}
		this.setX(getX() + 1);
		
		this.setX(getX() - 1);
		this.setY(getY() + 1);
		if (moved){
			if (moveRight){
				this.setX(getX() + 1);
			} else {
				this.setX(getX() - 1);
			}
		}
		if (!onFloor){
			momentum = momentum + 1;
			if (momentum < 6){
				this.setY(getY() + 1);
			}
			if (momentum > 6 && momentum < 12){
				this.setY(getY() + 2);
			}
			if (momentum > 6 && momentum < 18){
				this.setY(getY() + 3);
			}
			if (momentum > 18 && momentum < 24){
				this.setY(getY() + 4);
			}
			if (momentum > 24){
				this.setY(getY() + 5);
			}	
		}
		if (onFloor && momentum >= 1){
			momentum = 0;
		}
	if (Room.isColliding(this)){
		while (!toClimbOrNotToClimb){
			this.setY(getY() - 1);
			height = height + 1;
			if ((height >= 24)){
				this.setY(getY() + 18);
				if (!notStuck){
				notStuck = true;
				} else {
					stuck = true;
				}
			}
			if((!(Room.isColliding(this)) || height >= 24)){
				toClimbOrNotToClimb = true;
			}
			
		}
		}
	}
}