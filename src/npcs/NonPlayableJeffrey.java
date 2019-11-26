package npcs;

import gameObjects.NPC;
import resources.Sprite;

public class NonPlayableJeffrey extends NPC {

	public NonPlayableJeffrey () {
		this.setSprite(new Sprite ("resources/sprites/config/jeffrey_idle.txt"));
	}
	@Override
	public String checkName () {
		return "JEFFREY";
	}
	@Override
	public String checkEntry () {
		return "OUR \"HERO\" THINKS HE IS A LOT COOLER THAN HE ACTUALLY IS ";
	}
}
