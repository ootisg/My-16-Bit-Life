package gameObjects;

import map.Room;
import projectiles.Laser;

public class LazerHoverEnemy extends Enemy {
	
	//I don't have the sprite, so...
	
	boolean moveRight;
	int cooldown;
	boolean moveing;
	Laser attack;
	boolean hasTurned;
	int turrning;
	boolean nathansVariable;
	int cannonBalls;
public LazerHoverEnemy () {
	//setSprite (sprites.leftLaser);
	setHitboxAttributes (1, 3, 15, 30);
	getAnimationHandler ().setFrameTime (83.3);
	this.health = 60;
	this.defence = 10;
	this.moveRight = false;
	cooldown = 0;
	moveing = true;
	hasTurned = false;
	turrning = 0;
	nathansVariable = false;
	}
	@Override
	public void enemyFrame(){
		if ((getY () - player.getY() <= 16 && getY () - player.getY() >= -16) && cooldown >= 20 && ((player.getX () > getX() && moveRight) || (player.getX() < getX() && !moveRight)) ){
			moveing = false;
			cooldown = 0;
			cannonBalls = cannonBalls + 1;
			if (cannonBalls == 2) {
				cooldown = -30;
				cannonBalls = 0;
			}
			attack = new Laser (moveRight);
			attack.declare (getX(), getY() + 5);
		}
		if (cooldown == 20){
			moveing = true;
		}
		if (moveRight && moveing){
			setX (getX ()+ 1);
		}
		if(!moveRight && moveing){
			setX(getX () - 1);
		}
		if (this.moveRight) {
			setX (getX () + 16);
			setY (getY () + 16);
			if (!Room.isColliding (this.hitbox ())) {
				nathansVariable = true;
					}
			setX (getX () - 16);
			setY (getY () - 16);
		} else {
			setX (getX () - 16);
			setY (getY () + 16);
			if (!Room.isColliding (this.hitbox ())) {
				nathansVariable = true;	
			}
			setX (getX () + 16);
			setY (getY () - 16);
		}
		if ((Room.isColliding (this.hitbox()) || nathansVariable) && !hasTurned){
			//if (!this.getSprite().equals(sprites.turrningLaser)) {
			//this.setSprite(sprites.turrningLaser);
			//}
			turrning = turrning + 1;
			moveing = false;
			cooldown = 0;
			
			if (turrning == 9){
				hasTurned = true;
				moveing = true;
				turrning = 0;
			}
		}
		if (nathansVariable && moveing && hasTurned){
			moveRight = !moveRight;
			if(moveRight){
				//this.setSprite(sprites.rightLaser);
				hasTurned = false;
				nathansVariable = false;
			}
			else{
				//this.setSprite(sprites.leftLaser);
				hasTurned = false;
				nathansVariable = false;
			}
		}
		if (Room.isColliding (this.hitbox()) && moveing && hasTurned){
			moveRight = !moveRight;
			if (moveRight){
				//setSprite(sprites.rightLaser);
				hasTurned = false;
			}
			else{
				//setSprite(sprites.leftLaser);
				hasTurned = false;
				
			}
		}	
		cooldown = cooldown + 1;
	}
}
