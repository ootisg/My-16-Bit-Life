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
	SplitSlimelet baby;
	SplitSlimelet child;
	int deathTimer;
	int timer;
	boolean moveLeft;
	boolean overridePlayerInput;
	boolean normalMovement;
	boolean moveing;
	public SplittingSlimelet () {
		this.health = 200;
		this.setDeath(false);
		baby = new SplitSlimelet();
		child = new SplitSlimelet ();
		overridePlayerInput = true;
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
			//moveLeft = false;
			//this.getAnimationHandler().setFlipHorizontal(true);
		//} 
		//if (this.getVariantAttribute("Direction").equals("Left")) {
			//moveLeft = true;
		    //this.getAnimationHandler().setFlipHorizontal(false);
		
		//}	
		
		this.setSprite(moveingSprite);
	}
	@Override 
	public void enemyFrame () {
		if (this.health <= 0) {
			if (deathTimer == 0) {
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
		timer = timer + 1;
		if ((timer == RNG.nextInt(100) + 100) && normalMovement) {
			moveLeft = !moveLeft;
			timer = 0;
			this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
		}
		if (((timer % 2)  == 0) && moveing) {
			if (moveLeft) {
				this.setX(this.getX() + 1);
			} else {
				this.setX(this.getX() - 1);
			}
		}
		if ((GameCode.testJeffrey.getX() > this.getX() - 105) && (GameCode.testJeffrey.getX() < this.getX() + 155) ) {
			normalMovement = false;
		} else {
			normalMovement = true;
		}
		
		if ((GameCode.testJeffrey.getX() > this.getX() - 45) && (GameCode.testJeffrey.getX() < this.getX() + 65) && !this.checkPlayerPositionRelativeToWalls() ) {
			if (!this.getSprite().equals(attackingSprite) && !this.getSprite().equals(splittingSprite)) {
			this.setSprite(attackingSprite);
			}
			this.setHitboxAttributes(0, 0, 100, 64);
			moveing= false;
		} else {
			moveing = true;
			this.setHitboxAttributes(37, 0, 63, 64);
			if (!this.getSprite().equals(moveingSprite) && !this.getSprite().equals(splittingSprite)) {
			this.setSprite(moveingSprite);
			}
		}
		
		if (Room.isColliding(this.hitbox())) {
			this.getAnimationHandler().setFlipHorizontal(!this.getAnimationHandler().flipHorizontal());
			this.moveLeft = !moveLeft;
			timer = 1;
		}
	 if (!normalMovement && !this.checkPlayerPositionRelativeToWalls()) {
		 if (GameCode.testJeffrey.getX() > this.getX()) {
			 this.moveLeft = true;
				this.getAnimationHandler().setFlipHorizontal(true);
		 } else {
			 this.moveLeft = false;
			 this.getAnimationHandler().setFlipHorizontal(false);
		 }
	 }
		
	}
}
