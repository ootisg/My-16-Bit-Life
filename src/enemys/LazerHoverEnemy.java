package enemys;

import map.Room;
import projectiles.Laser;
import resources.Sprite;

public class LazerHoverEnemy extends Enemy {
	
	//I don't have the sprite, so...
	public static final Sprite LEFT_LASER = new Sprite ("resources/sprites/config/left_laser.txt");
	public static final Sprite RIGHT_LASER = new Sprite ("resources/sprites/config/right_laser.txt");
	public static final Sprite TURRNING_LASER = new Sprite ("resources/sprites/config/turrning_laser.txt");
	boolean moveRight;
	int cooldown;
	boolean moveing;
	Laser attack;
	boolean hasTurned;
	int turrning;
	boolean unamedVariable;
	int cannonBalls;
public LazerHoverEnemy () {
	setSprite (LEFT_LASER);
	setHitboxAttributes (1, 3, 15, 30);
	getAnimationHandler ().setFrameTime (83.3);
	this.health = 60;
	this.defence = 10;
	this.moveRight = false;
	cooldown = 0;
	moveing = true;
	hasTurned = false;
	turrning = 0;
	unamedVariable = false;
	try {
	if (this.getVariantAttribute("flip").equals("true")) {
		setSprite(RIGHT_LASER);
		this.moveRight = true;
	}
	} catch (NullPointerException e) {
		
	}
	}
@Override 
public String checkName () {
	return "LASER HOVER ENEMY";
}
@Override
public String checkEntry () {
	return "COOL MACHINE IT SHOOTS LASERS";
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
			if (!Room.isColliding(this)) {
				unamedVariable = true;
					}
			setX (getX () - 16);
			setY (getY () - 16);
		} else {
			setX (getX () - 16);
			setY (getY () + 16);
			if (!Room.isColliding(this)) {
				unamedVariable = true;	
			}
			setX (getX () + 16);
			setY (getY () - 16);
		}
		if ((Room.isColliding (this) || unamedVariable) && !hasTurned){
			if (!this.getSprite().equals(TURRNING_LASER)) {
			this.setSprite(TURRNING_LASER);
			}
			turrning = turrning + 1;
			moveing = false;
			cooldown = 0;
			
			if (turrning == 9){
				hasTurned = true;
				moveing = true;
				turrning = 0;
			}
		}
		if (unamedVariable && moveing && hasTurned){
			moveRight = !moveRight;
			if(moveRight){
				this.setSprite(RIGHT_LASER);
				hasTurned = false;
				unamedVariable = false;
			}
			else{
				this.setSprite(LEFT_LASER);
				hasTurned = false;
				unamedVariable = false;
			}
		}
		if (Room.isColliding(this) && moveing && hasTurned){
			moveRight = !moveRight;
			if (moveRight){
				setSprite(RIGHT_LASER);
				hasTurned = false;
			}
			else{
				setSprite(LEFT_LASER);
				hasTurned = false;
				
			}
		}	
		cooldown = cooldown + 1;
	}
}
