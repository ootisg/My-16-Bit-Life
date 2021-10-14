package projectiles;


import java.util.Random;

import enemys.Enemy;
import gui.Gui;
import main.GameCode;
import players.Player;
import resources.Sprite;
import weapons.Bombs;

public class Shrapnul extends Projectile{
	boolean hit = false;
	int explodeType = 0;
	int timer = 0;
	//0 = no explosion
	//1 = regular explosion
	//2 = shrapnul explsoion
	public Shrapnul (int explodeType) {
		Random rand = new Random ();
		this.explodeType = explodeType;
		int spriteNumber = (rand.nextInt(4) + 1);
		this.setSprite(new Sprite ("resources/sprites/Shrapnel " + spriteNumber + ".png")) ;
		this.setHitboxAttributes(0, 0, 8, 8);
	}
	@Override 
	public void projectileFrame () {
		if (timer > 8) {
			switch (explodeType) {
			case 0:
				this.forget();
				break;
			case 1:
				Explosion explosion = new Explosion(0, 10, false, true);
				explosion.declare(this.getX() - 8,this.getY() - 8);
				this.forget();
				break;
			case 2:
				if(Player.getInventory().getWeapon("Bombs").getTierInfo()[1] == 3) {
					ExpolsionShrapnul sprap = new ExpolsionShrapnul(0, 10, false, true,1);
					sprap.declare(this.getX() - 8,this.getY() - 8);
				} else {
					ExpolsionShrapnul sprap = new ExpolsionShrapnul(0, 10, false, true,0);
					sprap.declare(this.getX() - 8,this.getY() - 8);
				}
				this.forget();
				break;
			}
		}
		timer = timer + 1;
		if (!hit) {
			for (int i = 0; i < Enemy.enemyList.size(); i++) {
				if (this.isColliding(Enemy.enemyList.get(i))) {
					switch (explodeType) {
					case 0:
						Enemy.enemyList.get(i).damage(5);
						this.forget();
						break;
					case 1:
						Explosion explosion = new Explosion(0, 10, false, true);
						explosion.declare(this.getX() - 8,this.getY() - 8);
						this.forget();
						break;
					case 2:
						if(Player.getInventory().getWeapon("Bombs").getTierInfo()[1] == 3) {
							ExpolsionShrapnul sprap = new ExpolsionShrapnul(0, 10, false, true,1);
							sprap.declare(this.getX() - 8,this.getY() - 8);
						} else {
							ExpolsionShrapnul sprap = new ExpolsionShrapnul(0, 10, false, true,0);
							sprap.declare(this.getX() - 8,this.getY() - 8);
						}
						this.forget();
						break;
					}
					hit = true;
					break;
				}
			}
		}
		
	}
}