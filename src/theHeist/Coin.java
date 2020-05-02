package theHeist;

import main.ObjectHandler;
import resources.Sprite;

public class Coin extends CheckableObject{

	
	public Coin () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void onCheck () {	
		Xavier x = (Xavier)ObjectHandler.getObjectsByName("Xavier").get(0);
		x.giveItem(new Item ("Coin", new Sprite ("resources/sprites/the-heist/coin.png")));
		this.writeText("WOW A COIN HOW LUCKY");
		this.forget();
	}
}
