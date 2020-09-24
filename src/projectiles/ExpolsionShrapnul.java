package projectiles;

import java.util.Random;

import items.PopcornKernel;

public class ExpolsionShrapnul extends Explosion {
	boolean sploded;
	int shrapnulExplosion;
	//0 = no explosion
	//1 = regular explosion
	//2 = shrapnul explsoion
	public ExpolsionShrapnul(int playerDamage, int enemyDamage, boolean hitPlayer, boolean hitEnemy,int explosionDepth) {
		super(playerDamage, enemyDamage, hitPlayer, hitEnemy);
		shrapnulExplosion = explosionDepth;
	}
	@Override
	public void projectileFrame () {
		super.projectileFrame();
		if (!sploded) {
			sploded = true;
			Random rand = new Random ();
			int amount = rand.nextInt(8) + 8;
			for (int i = 0; i<amount; i++) {
				double direction = rand.nextInt(6) + rand.nextDouble();
				Shrapnul shrap = new Shrapnul (shrapnulExplosion);
				shrap.setDirection(direction);
				shrap.setSpeed(10);
				shrap.declare(this.getX(),this.getY());
			}
		}
	}
}
