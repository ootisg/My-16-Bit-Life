package enemys;

import projectiles.Disk;
import resources.Sprite;

public class Marshan extends Enemy {

	public Marshan() {
		int timer = 0;
		this.setHealth(60);
		defence = 0;
		this.setSprite(new Sprite ("resources/sprites/config/Marshan.txt"));
		this.getAnimationHandler().setFrameTime(125);
		this.setHitboxAttributes(0, 0, 16, 19);
		this.adjustHitboxBorders();
	}
	@Override 
	public String checkName () {
		return "MARSH-AN";
	}
	@Override
	public String checkEntry () {
		return "THE ONLY RETURNING ENEMY (YOU SHOULD GET AUTOMOBILES AND THE EISENHOWER HIGHWAY SYTEM THE GAME, ITS FREE)";
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
			if (timer % 9 == 8 ) {
				this.getAnimationHandler().setAnimationFrame(12);
				Disk d = new Disk (5.239);
				d.declare(this.getX(),this.getY() - 6);
				Disk e = new Disk (4.1);
				e.declare(this.getX() - 10,this.getY() - 6);
			} else {
				this.getAnimationHandler().setAnimationFrame(11);
			}
			timer = timer + 1;
		}
		if (timer == 35) {
			timer = 0;
			this.getAnimationHandler().setFrameTime(125);
			this.getAnimationHandler().resetImage(getSprite());
		}
	}
}
