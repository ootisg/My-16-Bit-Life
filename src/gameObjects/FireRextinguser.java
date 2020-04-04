package gameObjects;

import main.AnimationHandler;
import projectiles.Fire;
import resources.Sprite;

public class FireRextinguser extends Enemy {
	public Sprite idleSprite;
	public Sprite attackingSprite;
	Fire fire;
	boolean attacked;
	int attackTimer;
	public  FireRextinguser() {
		this.health = 80;
		attackTimer = 0;
		this.defence = 25;
		idleSprite = new Sprite ("resources/sprites/config/Fire_Rextinguisher_Idle.txt");
		attackingSprite = new Sprite ("resources/sprites/config/Fire_Rextinguisher_Attacking.txt");
		this.setSprite (idleSprite);
		this.setAttackFromBothSides(true);
		this.setHitboxAttributes(2, 0, 19, 24);
		this.moveRight = false;
		this.getAnimationHandler().setFrameTime(250);
		this.setFalls(true);
	}

	@Override 
	public String checkName () {
		return "FIRE REEXTINGUSER";
	}
	@Override
	public String checkEntry () {
		return "OH IRONY HOW YOU TAKE US TO THE LANDS OF DISTOPIA";
	}
	@Override
	public void enemyFrame () {
		this.patrol(2, 0, 60, 0, 60, attackingSprite, idleSprite, 0, 2, 0, 0, 19, 24);
		if (attacked && this.isNearPlayerX(0, 60, 0, 60)) {
			if (this.moveRight) {
				this.goX(this.getX() - 1);
			} else {
				this.goX(this.getX()  +1);
			}
		}
		if (attacked) {
			attackTimer = attackTimer + 1;
			if (attackTimer == 60) {
				attacked = false;
				attackTimer = 0;
			}
		}
		if ( !attacked && (this.isNearPlayerX(0, 200, 0, 200)) && !(this.getSprite().equals(attackingSprite)) && !this.checkPlayerPositionRelativeToWalls()) {
			if (this.moveRight) {
				this.goX(this.getX() + 1);
			} else {
				this.goX(this.getX() -1);
			}
		}
		if (this.getSprite().equals(attackingSprite) && !attacked) {
			attacked = true;
			if (this.moveRight) {
				fire = new Fire (true);
			fire.declare(this.getX() + 20 , this.getY());
			} else {
				fire = new Fire (false);
			fire.declare(this.getX() - 46, this.getY());
			}
		}
	}
}
