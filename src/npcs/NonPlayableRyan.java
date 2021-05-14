package npcs;

import resources.Sprite;

public class NonPlayableRyan extends LegacyNPC {

	public NonPlayableRyan () {
		this.setSprite(new Sprite ("resources/sprites/config/ryan_idle.txt"));
	}
	@Override
	public String checkName () {
		return "RYAN";
	}
	@Override
	public String checkEntry () {
		return "LIKES TO USE SONG POWER. KNOWN TO FALL DOWN SOMETIMES";
	}
}
