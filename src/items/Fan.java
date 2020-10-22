package items;

import gameObjects.UseableCarpet;
import gameObjects.UseableFan;
import gameObjects.UseableJetpack;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Fan extends Item {

	public Fan () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public String checkName () {
		return "FAN";
	}
	@Override
	public String checkEnetry() {
		return "IT GOES BLOWY BLOW ";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public void onPickup () {
		UseableFan useable = new UseableFan ();
		useable.declare();
	}
	@Override
	public void onRemove () {
		ObjectHandler.getObjectsByName("UseableFan").get(0).forget();
	}
}
