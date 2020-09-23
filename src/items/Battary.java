package items;

import resources.Sprite;
import weapons.LifeVaccum;

public class Battary extends Item {

	public Battary () {
		this.setSprite(new Sprite ("resources/sprites/Battary.png")); 
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public String checkName () {
		return "BATTARY";
	}
	@Override
	public String checkEnetry() {
		return "A BATTARY FOR THE LIFE VACUME HOW NEAT ";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
	@Override
	public void onPickup () {
		LifeVaccum.addBattary(100);
	}
}