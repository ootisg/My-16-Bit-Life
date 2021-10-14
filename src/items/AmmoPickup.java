package items;

import players.Player;
import resources.Sprite;

public class AmmoPickup extends Item {

	double amount = 100;
	String weaponName;
	
	public AmmoPickup () {
		
	}
	
	public void setAmount (double amount) {
		this.amount = amount;
	}
	
	public void setWeaponType (String weaponType) {
		weaponName = weaponType;
	}
	
	@Override
	public void setSprite (Sprite newSprite) {
		super.setSprite(newSprite);
		this.setHitboxAttributes(0, 0, this.getSprite().getWidth(), this.getSprite().getHeight());
	}
	
	@Override
	public void onPickup () {
		Player.getInventory().getWeapon(weaponName).giveAmmo(amount);
	}
}
