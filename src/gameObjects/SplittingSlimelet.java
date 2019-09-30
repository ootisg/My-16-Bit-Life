package gameObjects;

import java.util.Random;

import main.GameCode;
import map.Room;
import resources.Sprite;

public class SplittingSlimelet extends Enemy {
	Sprite moveingSprite;
	Sprite attackingSprite;
	Sprite splittingSprite;
	Random RNG;
	int WaitForCollsionCheck;
	SplitSlimelet baby;
	SplitSlimelet child;
	int deathTimer;
	boolean attacking;
	int timer;
	boolean moveRight;
	int flipTimer;
	boolean normalMovement;
	boolean moveing;
	public SplittingSlimelet () {
		this.health = 200;
		attacking = false;
		WaitForCollsionCheck = 0;
		flipTimer = 0;
		this.setDeath(false);
		baby = new SplitSlimelet();
		child = new SplitSlimelet ();
		this.setHitboxAttributes(37, 0, 63, 64);
		this.setFalls(true);
		moveingSprite = new Sprite ("resources/sprites/config/splitting_slimelet_moveing.txt");
		attackingSprite = new Sprite ("resources/sprites/config/splitting_slimelet_attacking.txt");
		splittingSprite = new Sprite ("resources/sprites/config/splitting_slimelet_splitting.txt");
		RNG = new Random ();
		normalMovement = true;
		deathTimer = 0;
		moveing = true;
		timer = 0;
		this.getAnimationHandler().setFrameTime(150);
		//if (this.getVariantAttribute("Direction").equals("Right")) {
			//moveRight = false;
			//this.getAnimationHandler().setFlipHorizontal(true);
		//} 
		//if (this.getVariantAttribute("Direction").equals("Left")) {
			//moveRight = true;
		    //this.getAnimationHandler().setFlipHorizontal(false);
		
		//}	
		
		this.setSprite(moveingSprite);
	}
	@Override 
	public void enemyFrame () {
		//boolean overridePlayerInput = false;	
		this.patrol(40, 70, 90, 15, -5, attackingSprite, moveingSprite, -2);
		if (this.health <= 0) {
			if (deathTimer == 0) {
			this.setSpriteChangeing(false);
			this.setSprite(splittingSprite);
			this.getAnimationHandler().setRepeat(false);
			normalMovement = false;
			}
			deathTimer = deathTimer +1;
			if (deathTimer == 6) {
				baby.declare(this.getX() - 10,this.getY());
				child.declare(this.getX()+ 50, this.getY());
			}
			if (deathTimer == 30) {
				this.deathEvent();
			}
		}
		//if ((timer == RNG.nextInt(300) + 100) && normalMovement) {
		//	moveRight = !moveRight;
		//	timer = 0;
		//	this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
		//}
		//}
		//if ((GameCode.testJeffrey.getX() > this.getX() - 105) && (GameCode.testJeffrey.getX() < this.getX() + 145) ) {
		//	normalMovement = false;
		//} else {
		//	normalMovement = true;
		//}
	
		//	if (!this.getSprite().equals(attackingSprite) && !this.getSprite().equals(splittingSprite)) {
		//	this.setSprite(attackingSprite);
		//	attacking =true;
			 //if (GameCode.testJeffrey.getX() >= this.getX()) {
				 //this.moveRight = true;
				//this.getAnimationHandler().setFlipHorizontal(true);
			 //} else {
			//	 this.moveRight = false;
		//		 this.getAnimationHandler().setFlipHorizontal(false);
	//		 }
			//}
			//this.setHitboxAttributes(0, 0, 100, 64);
			//moveing= false;
		//} else {
		//	moveing = true;
		//	if (!this.getSprite().equals(moveingSprite) && !this.getSprite().equals(splittingSprite)) {
		//	this.setSprite(moveingSprite);
		//	}
		//}
		
		//	if (!this.checkWitchWallYourCollidingWith()) {
		//	this.setX(this.getX() + 40);
		//	this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
		//	if (Room.isColliding(this.hitbox())) {
		//		this.getAnimationHandler().setFlipHorizontal(false);
		//		overridePlayerInput = true;
		//		this.setX(this.getX() - 40);
		//		}
		//	} else {
			
		//	if (Room.isColliding(this.hitbox())) {
		//		this.getAnimationHandler().setFlipHorizontal(false);
		//		overridePlayerInput = true;
		//	this.setX(this.getX() + 40);
		//		}
		//	}
			
		//	timer = 1;
		//}
		//if ((GameCode.testJeffrey.getX() > this.getX() ) && attacking && moveRight) {
		//	flipTimer = flipTimer + 1;
		//}
		//if((GameCode.testJeffrey.getX() < this.getX() ) && attacking && !moveRight) {
		//	flipTimer = flipTimer + 1;
		//}
		//if(flipTimer == 30) {
		//	attacking = false;
		//	flipTimer = 0;
			}
	 //if (!normalMovement && !this.checkPlayerPositionRelativeToWalls() && !attacking && !overridePlayerInput) {
		// if (GameCode.testJeffrey.getX() >= this.getX() + 40) {
		//	 this.moveRight = true;
			//	this.getAnimationHandler().setFlipHorizontal(true);
		 //} else {
		//	 if (GameCode.testJeffrey.getX() < this.getX() -40) {
			// this.moveRight = false;
			// this.setX(this.getX() -37);
			 //this.getAnimationHandler().setFlipHorizontal(false);
			// }
		 //}
	 //}
		
	}
