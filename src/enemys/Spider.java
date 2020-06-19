package enemys;

import java.util.Arrays;

import map.Room;
import resources.Sprite;

public class Spider extends Enemy {
	public static final Sprite LANDING_SPRITE = new Sprite ("resources/sprites/config/landingSpider.txt");
	public static final Sprite WOBBLING_SPRITE = new Sprite ("resources/sprites/config/wobblingSpider.txt");
	public static final Sprite JUMPING_SPRITE = new Sprite ("resources/sprites/config/jumpingSpider.txt");
	public static final Sprite FALLING_SPRITE = new Sprite ("resources/sprites/config/fallingSpidery.txt");
	public Spider () {
		this.setSprite(LANDING_SPRITE);
		this.setHitboxAttributes(0,0, 16, 16);
		this.setFalls(true);
		this.changeJumpTiemMultiplyer(.5);
		this.health = 45;
		this.getAnimationHandler().setFrameTime(12);
		this.setMomentum(6);
	}
	@Override 
	public void enemyFrame () {
		this.jump(2,5);
		if (Room.isColliding(this)) {
			if (!this.getSprite().equals(LANDING_SPRITE)) {
				this.setSprite(LANDING_SPRITE);
			} else {
				if (this.getAnimationHandler().getFrame() == 3 && !this.getSprite().equals(WOBBLING_SPRITE)) {
					this.setSprite(WOBBLING_SPRITE);
				}
			}
		} else {
			if (!this.getSprite().equals(JUMPING_SPRITE)) {
				this.setSprite(JUMPING_SPRITE);
			}
			if (this.getAnimationHandler().getFrame() == 4) {
				this.getAnimationHandler().setFrameTime(10000);
			}
			if (!this.getSprite().equals(FALLING_SPRITE) && this.timer > 8 ) {
				this.setSprite(FALLING_SPRITE);
				this.getAnimationHandler().setFrameTime(6);
			}
		}
		this.setY(this.getY () - 5);
		if (this.lockedRight) {
			if (!checkX(this.getX() + 4)) {
				this.lockedRight = false;
				this.getAnimationHandler().setFlipHorizontal(false);
			}
		} else {
			if (!checkX(this.getX() - 4)) {
				this.lockedRight = true;
				this.getAnimationHandler().setFlipHorizontal(true);
			}
		}
		this.setY(this.getY () + 5);
	}

}
