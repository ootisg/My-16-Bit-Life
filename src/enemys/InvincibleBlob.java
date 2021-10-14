package enemys;

import map.Room;
import players.Player;
import resources.Sprite;

public class InvincibleBlob extends Enemy {
	
	public static final Sprite blob = new Sprite ("resources/sprites/config/blob_with_shoes.txt");
	
	boolean moveRight;
	public InvincibleBlob () {
		this.setSprite (blob);
		this.getAnimationHandler ().setFrameTime (166.7);
		this.moveRight = true;
		this.setHitboxAttributes (1, 3, 13, 12);
		this.health = 100000;
		this.defence = 0;
	}
	@Override 
	public String checkName () {
		return "INVINCIBLE BLOB";
	}
	@Override
	public String checkEntry () {
		return "WHY ISN'T IT BURNING";
	}
	@Override
	public void enemyFrame () {
		if (this.moveRight) {
			setX (getX () + 16);
			setY (getY () + 16);
			setX (getX () + 1);
			if (!Room.isColliding(this)) {
				this.moveRight = false;
			}
			setX (getX () - 16);
			setY (getY () - 16);
			if (Room.isColliding(this)) {
				this.moveRight = false;
				setXTheOldFasionWay (getX () - 1);
			}
		} else {
			setX (getX () - 16);
			setY (getY () + 16);
			setX (getX () - 1);
			if (!Room.isColliding(this)) {
				this.moveRight = true;
			}
			setX (getX () + 16);
			setY (getY () - 16);
			if (Room.isColliding(this)) {
				this.moveRight = true;
				setXTheOldFasionWay (getX () + 1);
			}
		}
	}
	@Override
	public void attackEvent () {
		Player.getActivePlayer().damage (this.baseDamage);
	}
}
