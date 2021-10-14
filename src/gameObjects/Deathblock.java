package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import main.GameLoop;
import players.Player;

public class Deathblock extends GameObject {
	public static Player player = (Player) ObjectHandler.getObjectsByName ("Player").get (0);
public Deathblock (){
	this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (player.isColliding(this)){
			player.damage(Double.POSITIVE_INFINITY);
		}
	}
}