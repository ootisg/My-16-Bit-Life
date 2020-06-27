package npcs;

import resources.Sprite;

public class NonPlayableJeffrey extends LegacyNPC {

	public NonPlayableJeffrey () {
		this.setSprite(new Sprite ("resources/sprites/config/jeffrey_idle.txt"));
	}
	@Override
	public String checkName () {
		return "JEFFREY";
	}
	@Override
	public String checkEntry () {
		return "THINKS HE IS A LOT COOLER THAN HE ACTUALLY IS ";
	}
}
