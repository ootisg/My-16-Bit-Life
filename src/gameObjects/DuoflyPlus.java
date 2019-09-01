package gameObjects;

import resources.Sprite;

public class DuoflyPlus extends Enemy {
	
	public static final Sprite duoflyPlus = new Sprite ("resources/sprites/config/duofly_plus.txt");
	public static final Sprite duoflyPlusDeath = new Sprite ("resources/sprites/config/duofly_plus_death.txt");
	
	
	//This class is not yet commented
	int deathcount;
	Sprite deathSprite;
	public DuoflyPlus () {
		deathcount = -1;
		this.deathSprite = duoflyPlusDeath;
		getAnimationHandler ().setFrameTime (16.7);
		setSprite (duoflyPlus);
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
			setX (getX () - 3);
		} else if (deathcount == 9) {
			forget ();
		}
		if (deathcount > -1) {
			deathcount ++;
		}
		if (isColliding ("DuoflyMinus")) {
			deathcount = 0;
			
		}
	}
}