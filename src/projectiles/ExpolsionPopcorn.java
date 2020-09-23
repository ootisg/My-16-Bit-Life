package projectiles;

import java.util.Random;

import items.PopcornKernel;

public class ExpolsionPopcorn extends Explosion {

	public ExpolsionPopcorn(int playerDamage, int enemyDamage, boolean hitPlayer, boolean hitEnemy) {
		super(playerDamage, enemyDamage, hitPlayer, hitEnemy);
	}
	@Override
	public void projectileFrame () {
		super.projectileFrame();
		Random rand = new Random ();
		int amount = rand.nextInt(5) + 5;
		for (int i = 0; i <amount; i++) {
			double direction = rand.nextInt(6) + rand.nextDouble();
			PopcornKernel kernel = new PopcornKernel ();
			kernel.setDirection(direction);
			kernel.setSpeed(10);
			kernel.declare(this.getX(),this.getY());
		}
	}

}
