package items;

import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class FairUseKey extends Item {
	Sprite FUKey = new Sprite("resources/sprites/key.png");
	public FairUseKey () {
		this.setSprite(FUKey); 
		this.setHitboxAttributes(0, 0, 9, 5);
	}
	@Override
	public String checkName () {
		return "FAIR USE KEY";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public String checkEnetry() {
		return "THIS IS A KEY THAT ALLOWS YOU TO ENTER COPYWRIGHTED AREAS SOMETIMES IT DOESEN'T WORK BUT HEY ITS SOMETHING";
	}
}
