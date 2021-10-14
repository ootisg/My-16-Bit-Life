package projectiles;

import java.util.Random;

import enemys.Enemy;
import map.Room;
import players.Player;
import resources.Sprite;

public class Explosion extends Projectile {
	
	public static final Sprite kaboom = new Sprite ("resources/sprites/config/Explosion.txt");
	int timer = 0;
	Random RNG;
	int mult = 1;
	int pD;
	int eD;
	boolean hP;
	boolean hE;
	
	public Explosion (int playerDamage, int enemyDamage, boolean hitPlayer, boolean hitEnemy) {
		pD = playerDamage;
		eD = enemyDamage;
		hP = hitPlayer;
		hE = hitEnemy;
		RNG = new Random();
		this.setSprite(kaboom);
		this.setHitboxAttributes(0, 0, 31, 31);
		this.getAnimationHandler().setFrameTime(40);
		this.getAnimationHandler().setAnimationFrame(0);
	}
	
	@Override 
	public void projectileFrame () {
		if (this.isColliding(Player.getActivePlayer()) && hP) {
			Player.getActivePlayer().damage(pD);
		}
		//Calculates enemy damage, and just like the ninja triangle, explosions hit enemies multiple times.
		//Players are only safe because of invincibility frames lol
		//This is something we should try to fix at some point.
		if (hE) {
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i)) && (this.getAnimationHandler().getFrame() % 3) == 0){
					Enemy.enemyList.get(i).damage((RNG.nextInt(10) + eD) * mult);
					mult = 0;
				}
				mult = 1;
			}
		}
		if (this.getAnimationHandler().getFrame() == 6) {
			this.forget();
		}
	}
}
