package gameObjects;

import map.Room;
import projectiles.Cannonball;
import resources.Sprite;

public class CannonTankEnemy extends Enemy {
	
	public static final Sprite cannonRight = new Sprite ("resources/sprites/config/tank_right.txt");
	public static final Sprite cannonLeft = new Sprite ("resources/sprites/config/tank_left.txt");
	public static final Sprite cannonTurning = new Sprite ("resources/sprites/config/tank_turning.txt");
	
	boolean moveRight;
	int cooldown;
	boolean moveing;
	Cannonball attack;
	boolean hasTurned;
	int turrning;
	boolean Variable;
	int cannonBalls;
public CannonTankEnemy () {
	setSprite (cannonRight);
	setHitboxAttributes (0, 3, 15, 28);
	getAnimationHandler ().setFrameTime(83.3);
	this.health = 60;
	this.defence = 10;
	this.moveRight = true;
	cooldown = 0;
	moveing = true;
	hasTurned = false;
	turrning = 0;
	Variable = false;
	}
	@Override 
	public String checkName () {
		return "CANNON TANK ENEMY";
	}
	@Override
	public String checkEntry () {
		return "COOL MACHINE IT SHOOTS CANNONBALLS";
	}
	@Override
	public void enemyFrame(){
		if ((getY () - player.getY() <= 16 && getY () - player.getY() >= -16) && cooldown >= 20 && ((player.getX () > getX() && moveRight) || (player.getX() < getX() && !moveRight)) ){
			moveing = true;
			cooldown = 0;
			cannonBalls = cannonBalls + 1;
			if (cannonBalls == 1) {
				cooldown = -30;
				cannonBalls = 0;
			}
			attack = new Cannonball (moveRight);
			attack.declare (getX(), getY() + 5);
		}
		if (cooldown == 20){
			moveing = true;
		}
		if (moveRight && moveing){
			setX (getX ()+ 1.5);
		}
		if(!moveRight && moveing){
			setX(getX () - 1.5);
		}
		if (this.moveRight) {
			setX (getX () + 16);
			setY (getY () + 16);
			if (!Room.isColliding (this.hitbox ())) {
				Variable = true;
					}
			setX (getX () - 16);
			setY (getY () - 16);
		} else {
			setX (getX () - 16);
			setY (getY () + 16);
			if (!Room.isColliding (this.hitbox ())) {
				Variable = true;	
			}
			setX (getX () + 16);
			setY (getY () - 16);
		}
		if ((Room.isColliding (this.hitbox()) || Variable) && !hasTurned){
			setSprite(cannonTurning);
			turrning = turrning + 1;
			moveing = false;
			cooldown = 0;
			
			if (turrning == 6){
				hasTurned = true;
				moveing = true;
				turrning = 0;
			}
		}
		if (Variable && moveing && hasTurned){
			moveRight = !moveRight;
			if(moveRight){
				setSprite(cannonRight);
				hasTurned = false;
				Variable = false;
			}
			else{
				setSprite(cannonLeft);
				hasTurned = false;
				Variable = false;
			}
		}
		if (Room.isColliding (this.hitbox()) && moveing && hasTurned){
			moveRight = !moveRight;
			if (moveRight){
				setSprite(cannonRight);
				hasTurned = false;
			}
			else{
				setSprite(cannonLeft);
				hasTurned = false;
				
			}
		}	
		cooldown = cooldown + 1;
	}
}
