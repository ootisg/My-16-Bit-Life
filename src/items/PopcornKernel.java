package items;


import java.util.Random;

import enemys.Enemy;
import gui.Gui;
import players.Player;
import resources.Sprite;

public class PopcornKernel extends Item{
	int timer = 0;
	boolean hit = false;
	boolean ready = false;
	public PopcornKernel () {
		Random rand = new Random ();
		int spriteNumber = (rand.nextInt(4) + 1);
		this.setSprite(new Sprite ("resources/sprites/Popcorn kernel " + spriteNumber + ".png")) ;
		this.setHitboxAttributes(0, 0, 8, 8);
	}
	@Override 
	public void projectileFrame () {
		if (!ready) {
			if (timer > 5) {
				ready = true;
			}
			timer = timer + 1;
		} else {
			super.projectileFrame();
		}
		if (!hit) {
			for (int i = 0; i < Enemy.enemyList.size(); i++) {
				if (this.isColliding(Enemy.enemyList.get(i))) {
					Enemy.enemyList.get(i).damage(1);
					hit = true;
					break;
				}
			}
		}
	}
	@Override
	public void useItem(Player toUse) {
		toUse.heal(1);
		Gui.getGui().menu.frozen = false;
		this.forget();
	}	
	@Override
	public String checkEnetry() {
		return "ONLY A BIT BURNT";
	}
	public String checkName () {
		return "POPCORN";
	}
	@Override 
	public String getItemType() {
		return "Consumable";
	}
}