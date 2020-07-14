package items;

import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class BluePaint extends Item{
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	public BluePaint () {
		this.setSprite(new Sprite ("resources/sprites/Blue_Paint.png")); 
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "BLUE PAINT";
	}
	@Override
	public String checkEnetry() {
		return "ITS PAINT THATS BLUE USALLY FOUND IN HARDWARE STORES";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
}
