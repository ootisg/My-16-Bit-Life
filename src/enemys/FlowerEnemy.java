package enemys;

import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class FlowerEnemy extends Enemy{
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	
	private static final Sprite FLOWER_LEFT = new Sprite ("resources/sprites/config/flowerLeft.txt");
	private static final Sprite FLOWER_RIGHT = new Sprite ("resources/sprites/config/flowerRight.txt");
	private static final Sprite FLOWER_IDLE = new Sprite ("resources/sprites/config/flowerIdle.txt");
	
	private int timer = 0;
	public FlowerEnemy () {
		this.setHealth(150);
		this.enablePixelCollisions();
		this.setSprite(FLOWER_IDLE);
		this.setHitboxAttributes(0, 0, 160, 96);
		this.getAnimationHandler().setFrameTime(90);
		this.getAnimationHandler().enableAlternate();
	}
	@Override
	public void enemyFrame () {
		if (j.isColliding(this.hitbox())) {
			timer = timer + 1;
				if (j.getX() > this.getX() + 80) {
					if (!this.getSprite().equals(FLOWER_RIGHT)) {
						this.setSprite(FLOWER_RIGHT);
					}
				} else {
					if (!this.getSprite().equals(FLOWER_LEFT)) {
						this.setSprite(FLOWER_LEFT);
					}
				}
			
		} else {
			timer = 0;
			if (this.getAnimationHandler().getFrame() == 0) {
				this.setSprite(FLOWER_IDLE);
			}
		}
	}
}
