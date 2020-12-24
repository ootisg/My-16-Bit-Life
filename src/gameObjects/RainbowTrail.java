package gameObjects;

import resources.Sprite;

public class RainbowTrail extends Trail {

	public RainbowTrail(int frame, double despawnTime) {
		super(frame, despawnTime, 1);
		this.setLoopLength(5);
		this.setSprite(new Sprite ("resources/sprites/RainbowTrail.png"));
		this.getAnimationHandler().setFrameTime(10);
	}

}
