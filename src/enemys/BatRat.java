package enemys;

import map.Room;
import players.Player;
import resources.Sprite;

public class BatRat extends Enemy {
// copy pasted from green blob with shoes but fast
	public static final Sprite rat = new Sprite ("resources/sprites/config/BatRatWalk.txt");
	
	boolean moveRight;
	public BatRat () {
		this.setSprite (rat);
		this.getAnimationHandler ().setFrameTime (166.7);
		this.moveRight = true;
		this.setHitboxAttributes (0, 0, 15, 12);
		this.health = 60;
		this.defence = 0;
	}
	@Override 
	public String checkName () {
		return "BAT RAT";
	}
	@Override
	public String checkEntry () {
		return "DO I REALLY NEED TO EXPLAIN THIS ONE";
	}
	@Override
	public void enemyFrame () {
		if (this.moveRight) {
			setX (getX () + 16);
			setY (getY () + 16);
			setX (getX () + 3);
			if (!Room.isColliding(this)) {
				this.moveRight = false;
				this.getAnimationHandler().setFlipHorizontal(true);
			}
			setX (getX () - 16);
			setY (getY () - 16);
			if (Room.isColliding(this)) {
				this.moveRight = false;
				this.getAnimationHandler().setFlipHorizontal(true);
				setXTheOldFasionWay (getX () - 3);
			}
		} else {
			setX (getX () - 16);
			setY (getY () + 16);
			setX (getX () - 3);
			if (!Room.isColliding(this)) {
				this.moveRight = true;
				this.getAnimationHandler().setFlipHorizontal(false);
			}
			setX (getX () + 16);
			setY (getY () - 16);
			if (Room.isColliding(this)) {
				this.moveRight = true;
				this.getAnimationHandler().setFlipHorizontal(false);
				setXTheOldFasionWay (getX () + 3);
			}
		}
	}
	@Override
	public void attackEvent () {
		Player.getActivePlayer().damage (this.baseDamage);
	}
}
