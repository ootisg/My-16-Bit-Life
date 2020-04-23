package gameObjects;

import projectiles.Disk;
import resources.Sprite;

public class Marshan extends Enemy {

	public Marshan() {
		int timer = 0;
		this.setHealth(60);
		defence = 0;
		this.setSprite(new Sprite ("resources/sprites/config/Marshan.txt"));
		this.getAnimationHandler().setFrameTime(125);
	}
	@Override 
	public void frameEvent () {
		
		if (this.getAnimationHandler().getFrame() == 12) {
			if (timer < 1) {
				this.getAnimationHandler().setFrameTime(0);
				timer = timer + 1;
			}
		}

		if (timer != 0) {
			if (timer % 2 == 1 ) {
				this.getAnimationHandler().setAnimationFrame(12);
				Disk d = new Disk (5.4);
				d.declare(this.getX(),this.getY());
				Disk e = new Disk (3.9);
				e.declare(this.getX(),this.getY());
			} else {
				this.getAnimationHandler().setAnimationFrame(11);
			}
			timer = timer + 1;
		}
		if (timer == 5) {
			timer = 0;
			this.getAnimationHandler().setFrameTime(125);
			this.getAnimationHandler().resetImage(getSprite());
		}
	}
}
