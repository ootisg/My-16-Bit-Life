package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import main.GameLoop;
import players.Jeffrey;

public class Deathblock extends GameObject {
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
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