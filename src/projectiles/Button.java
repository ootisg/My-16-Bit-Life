package projectiles;

import java.util.Random;

import main.GameCode;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Poison;
import statusEffect.Status;

public class Button extends Projectile {
	Poison poison;
	Random rand;
	Sprite button;
	public Button () {
		button = new Sprite ("resources/sprites/config/Button_L.txt");
		setSprite (button);
		setHitboxAttributes (0,0,8,8);
		setAttributes (128, 128, 1.57, 3);
		poison = new Poison(GameCode.testJeffrey, 1);
		rand = new Random();
	}
	@Override
	public void projectileFrame(){
		if (Room.isColliding(this)){
			this.forget();
		}
		if (isColliding("players.Jeffrey")){
			int poisonChance = rand.nextInt(4) + 1;
			if ((!GameCode.testJeffrey.status.checkStatus(0, GameCode.testJeffrey.witchCharictar) && poisonChance == 1)){
				poison.declare(0, 0);
			} 
			player.damage(7);
		}
	}
}
