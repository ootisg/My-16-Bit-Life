package enemys;

import resources.Sprite;

public class DuoflyMinus extends Enemy {
	
	public static final Sprite duoflyMinus = new Sprite ("resources/sprites/config/duofly_minus.txt");
	public static final Sprite duoflyMinusDeath = new Sprite ("resources/sprites/config/duofly_minus_death.txt");
	
	//This class is not yet commented
	int deathcount;
	Sprite deathSprite;
	public DuoflyMinus () {
		deathcount = -60;
		this.deathSprite = duoflyMinusDeath;
		getAnimationHandler ().setFrameTime (16.7);
		setSprite (duoflyMinus);
		setHitboxAttributes (0, 0, 32, 32);
	}
	@Override
	public void enemyFrame () {
		if (deathcount == 0) {
			setSprite (deathSprite);
			getAnimationHandler ().setAnimationFrame (0);
			useHitbox (false);
		}
		if (deathcount <= 0) {
			setX (getX () + 3);
		} else if (deathcount == 9) {
			forget ();
		}
		if (deathcount > -1) {
			deathcount ++;
		}
		if (isColliding ("DuoflyPlus")) {
			deathcount = 0;
		}
	}
}