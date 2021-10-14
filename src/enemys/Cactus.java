package enemys;

import gameObjects.ItemDropRate;
import items.Coin;
import items.Waffle;
import main.ObjectHandler;
import players.Player;
import projectiles.DirectionBullet;
import projectiles.MiniCactus;
import resources.Sprite;

public class Cactus extends Enemy { 
	private MiniCactus partner;
	private int coolDown = 0;
	public Cactus () {
		this.setSprite(new Sprite ("resources/sprites/cactus.png"));
		partner = new MiniCactus();
		this.setHealth(400);
		this.setHitboxAttributes(0, 0, 16, 32);
		this.setDrops(new ItemDropRate [] {new ItemDropRate (new Coin(),50,3,20), new ItemDropRate (new Waffle(),10)} );
	}
	@Override
	public void enemyFrame () {
		if (!partner.declared() && coolDown > 90) {
			coolDown = 0;
			partner = new MiniCactus();
			partner.declare(this.getX(),this.getY());
			DirectionBullet bullet = new DirectionBullet(this.getX(),this.getY());
			partner.setDirection(bullet.findDirection(Player.getActivePlayer()));
		} else {
			if (!partner.declared()) {
				coolDown = coolDown + 1;
			}
		}
	}
}
