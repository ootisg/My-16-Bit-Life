package enemys;

import mapObjects.SoapPlatform;

public class SoapThrower extends Enemy {
	int timer = 0;
	boolean flipped;
	public SoapThrower () {
		this.setHealth(100);
		this.adjustHitboxBorders();
	}
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("direction") != null && !this.getVariantAttribute("direction").equals("left")) {
			flipped = true;
			this.getAnimationHandler().setFlipHorizontal(true);
		} else {
			flipped = false;
		}
	}
	@Override
	public void enemyFrame () {
		timer = timer + 1;
		if (timer > 20) {
			timer = 0;
			SoapPlatform working = new SoapPlatform ();
			if (!flipped) {
				working.setSpeed(-5);
			} else {
				working.setSpeed(5);
			}
			working.declare(this.getX(), this.getY());
		}
	}
}
