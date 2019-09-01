package projectiles;

import java.util.Random;

import main.GameLoop;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Status;

public class Button extends Projectile {
	
	public static final Sprite button = new Sprite ("resources/sprites/config/Button_L.txt");
	
	Status poison;
	Random rand;
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	public Button () {
		setSprite (button);
		setHitboxAttributes (0,0,8,8);
		setAttributes (128, 128, 1.57, 3);
		poison = new Status();
		rand = new Random();
	}
	@Override
	public void projectileFrame(){
		if (Room.isColliding (this.hitbox())){
			this.forget();
		}
		if (isColliding("players.Jeffrey")){
			int poisonChance = rand.nextInt(4) + 1;
			if ((!poison.IsAffected()) && poisonChance == 1){
				poison.setAttributesPlayerWithTier(player, "Poison", 3);
				poison.ApplyStatus();
			} 
			player.damage(7);
		}
	}
}
