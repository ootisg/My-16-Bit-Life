package items;

import gameObjects.UseableJetpack;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;

public class Jetpack extends Item {

	public Jetpack () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public String checkName () {
		return "JETPACK";
	}
	@Override
	public String checkEnetry() {
		return "HONESTLY THESE THINGS A SO COOL IN MOVIES THAT I ALMOST WONDER WHY THESE ARENT MORE COMMONPLACE LIKE THINK HOW FRIKEN SWEET IT WOULD BE TO JUST HAVE A JETPACK ";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public void onPickup () {
		UseableJetpack useable = new UseableJetpack ();
		useable.declare();
	}
	@Override
	public void onRemove () {
		ObjectHandler.getObjectsByName("UseableJetpack").get(0).forget();
	}
}
