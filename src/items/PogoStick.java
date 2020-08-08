package items;

import gameObjects.UsablePogo;
import gameObjects.UseableJetpack;
import main.ObjectHandler;
import resources.Sprite;

public class PogoStick extends Item {

	public PogoStick () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public String checkName () {
		return "POGO STICK";
	}
	@Override
	public String checkEnetry() {
		return "A LITTLE LAME IN COMPARISON TO ALL THE OTHER MAFIA STUFF BUT ITS STILL COOL I GUESS ";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public void onPickup () {
		UsablePogo useable = new UsablePogo ();
		useable.declare();
	}
	@Override
	public void onRemove () {
		ObjectHandler.getObjectsByName("UsablePogo").get(0).forget();
	}
}