package npcs;

import resources.Sprite;

public class NonPlayableSam extends LegacyNPC {

	public NonPlayableSam () {
		this.setSprite(new Sprite ("resources/sprites/config/sam_idle.txt"));
	}
	@Override
	public String checkName () {
		return "SAM";
	}
	@Override
	public String checkEntry () {
		return "ENTRY HAS NOT BEEN DECIDED YET ";
	}
}
