package items;

import gameObjects.UseableCarpet;
import gameObjects.UseableJetpack;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class Carpet extends Item {

	public Carpet () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public String checkName () {
		return "MAGIC CARPET";
	}
	@Override
	public String checkEnetry() {
		return "ITS ACTUALLY A MAGIC RUG BECAUSE WE COULDEN'T AFFORRD A CARPET ";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public void onPickup () {
		UseableCarpet useable = new UseableCarpet ();
		useable.declare();
	}
	@Override
	public void onRemove () {
		ObjectHandler.getObjectsByName("UseableCarpet").get(0).forget();
	}
}
