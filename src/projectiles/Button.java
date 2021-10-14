package projectiles;

import java.util.Random;

import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;
import statusEffect.Status;

public class Button extends Projectile {
	Random rand;
	Sprite button;
	public Button () {
		button = new Sprite ("resources/sprites/config/Button_L.txt");
		setSprite (button);
		setHitboxAttributes (0,0,8,8);
		setAttributes (128, 128, 1.57, 3);
		rand = new Random();
	}
	@Override
	public void projectileFrame(){
		if (this.goingIntoWall){
			this.forget();
		}
		if (isColliding(Player.getActivePlayer())){
			int poisonChance = rand.nextInt(4) + 1;
			if ((!Player.getActivePlayer().status.checkStatus("Poison1") && poisonChance == 1)){
				Player.getActivePlayer().getStatus().applyStatus("Poison1", 1560);
			} 
			Player.getActivePlayer().damage(7);
		}
	}
}
