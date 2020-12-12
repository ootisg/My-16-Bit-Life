package enemys;

import resources.Sprite;
import switches.HitableSwitch;

public class AttackSwitch extends Enemy {
	HitableSwitch realSwitch = new HitableSwitch ();
	
	public static final Sprite deactivated = new Sprite ("resources/sprites/orbDeactivated.png");
	public static final Sprite activated = new Sprite ("resources/sprites/orbActivated.png");
	
	public AttackSwitch () {
		this.setSprite(deactivated);
		this.setHitboxAttributes(0,0, 16, 16);
	}
	@Override
	public void declare (double x, double y) {
		super.declare(x, y - 4);
		realSwitch.declare(x, y -4);
	}
	@Override
	public void damage (int damage) {
		if (this.getSprite().equals(deactivated)) {
			this.setSprite(activated);
		} else {
			this.setSprite(deactivated);
		}
		realSwitch.onFlip();
	}
	@Override
	public void attackEvent () {
		
	}
}
