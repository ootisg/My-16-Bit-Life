package gameObjects;

import main.GameObject;
import resources.Sprite;

public class Warning extends GameObject{
	boolean warned = false;
	long elapsedTime;
	double maxTime;
	long startTime;
	/**
	 * basic ish constructer
	 * @param time the amout of time the warning goes off for (in seconds using decimals if nesssary)
	 * @param sprite the sprite for the warning (usally animated)
	 */
	public Warning (double time, Sprite sprite) {
		this.setSprite(sprite);
		maxTime = time;
	}
	@Override
	public void frameEvent () {
		elapsedTime = System.currentTimeMillis() - startTime;
		this.getAnimationHandler().setFrameTime(maxTime*1000 - elapsedTime);
		if (elapsedTime >= maxTime*1000) {
			this.forget();
			warned = true;
		}
	}
	@Override
	public void onDeclare () {
		startTime = System.currentTimeMillis();
	}
	public boolean isWarned () {
		return warned;
	}
}
