package items;

import players.Player;
import resources.Sprite;

public class Coin extends Item {
	public Coin () {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Coin.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	
	}
	@Override
	public String checkName () {
		return "COIN";
	}
	@Override
	public String checkEnetry() {
		return "MONEY MONEY ";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public void onPickup () {
		Player.getInventory().addMoney(1);
	}
}
