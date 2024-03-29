package projectiles;

import enemys.LivingPeriod;
import map.Room;
import players.Player;
import resources.Sprite;

public class Period extends Projectile {
	int timer;
	LivingPeriod period;
	public Period (double direction) {
		this.direction = direction;
		this.setSpeed(3);
		this.setSprite(new Sprite ("resources/sprites/config/Period.txt"));
		this.setHitboxAttributes(0, 0, 5, 4);
		timer = 0;
	}
	@Override
		public void projectileFrame () {
		timer = timer + 1;
		this.makeDamageingProjectile(30, 15);
		if (timer == 40) {
			period = new LivingPeriod();
			period.declare(this.getX(),this.getY());
			this.forget();
		}
		if (this.isColliding(Player.getActivePlayer())) {
			period = new LivingPeriod();
			period.declare(this.getX(),this.getY());
			this.forget();
		}
		if (Room.isColliding(this)) {
			period = new LivingPeriod();
			period.declare(this.getX(),this.getY());
			this.forget();
		}
	}
}
