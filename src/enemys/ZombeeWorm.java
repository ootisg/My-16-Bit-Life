package enemys;

import java.util.Random;

import players.Player;
import resources.Sprite;

public class ZombeeWorm extends Enemy {
	long spawnTime = 0;
	int waitTime = 0;
	
	public ZombeeWorm () {
		this.setSprite(new Sprite ("resources/sprites/worm.png"));	
		this.useSpriteHitbox();
		this.setRenderPriority(2);
	}
	@Override
	public void enemyFrame () {
		if (System.currentTimeMillis() > spawnTime + waitTime) {
			if (!this.goY(this.getY() + 10)) {
				this.deathEvent();
			}
		}
		if (this.closeTooX(Player.getActivePlayer(),3)) {
			waitTime = 0;
		}
	}
	@Override
	public void onDeclare () {
		spawnTime = System.currentTimeMillis();
		Random rand = new Random ();
		waitTime = (rand.nextInt(500) + 1) * 10;
	}
	
}
