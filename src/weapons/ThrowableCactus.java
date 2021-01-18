package weapons;

import resources.Sprite;

public class ThrowableCactus extends AimableWeapon {

	public ThrowableCactus() {
		super(new Sprite ("resources/sprites/throwable_cactus.png"));
		this.setHitboxAttributes(4, 2, 8, 14);
	}
	@Override
	public void projectileFrame () {
		 
		
	}
	@Override
	public void onSwitch () {
		
	}
}
