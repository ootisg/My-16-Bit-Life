package weapons;

import items.Item;
import map.Room;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class Weapon extends Item {
	
	double remainingAmmo;
	double secondaryAmmo = -1;
	public final Sprite OUTTA_AMMO = new Sprite ("resources/sprites/Outta_Ammo.png");
	public Weapon () {
		
	}

	//override in weapon classes
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"DEFULT", "DEFULT", "DEFULT", "DEFULT"};
		return returnArray;
	}
	//override in weapon classes
	public int [] getTierInfo () {
		int [] returnArray;
		returnArray = new int [] {0,0,0,0};
		return returnArray;
	}
	//overriden in AimableWeapon
	public Sprite getUnrotatedSprite () {
		return this.getSprite();
	}
	
	public double getAmmoCount () {
		return remainingAmmo;
	}
	
	public boolean fireAmmo (double amount) {
		remainingAmmo = remainingAmmo - amount;
		if (remainingAmmo == 0) {
			return false;
		}
		if (remainingAmmo < 0) {
			remainingAmmo = 0;
			//AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 10, OUTTA_AMMO);
		}
		return true;
	}
	
	public void giveAmmo (double amount) {
		remainingAmmo = remainingAmmo + amount;
	}
	
	public boolean canFire () {
		return remainingAmmo > 0;
	}
	public void giveSecondaryAmmo (double amount) {
		secondaryAmmo = secondaryAmmo + amount;
	}
	
	public double getSecondaryAmmoCount () {
		return secondaryAmmo;
	}
	
	public boolean fireSecondaryAmmo (double amount) {
		secondaryAmmo = secondaryAmmo - amount;
		if (secondaryAmmo == 0) {
			return false;
		}
		if (secondaryAmmo < 0) {
			secondaryAmmo = 0;
		}
		return true;
	}
	
	public boolean canFireSecondary () {
		return secondaryAmmo > 0;
	}
	
	public void onFire() {
		
	}
	
	public void onHold() {
		
	}
	
	public void onRelease () {
		
	}
	
	public void onSwitchModes() {
		
	}
	
	public void onSecondaryHold () {
		
	}
	
	public void onSecondaryFire () {
		
	}
	
	public void onSecondaryRelease () {
		
	}
	
	//dont run the frame event from item
	@Override
	public void frameEvent () {
		
	}
	
	/**
	 * run whenver weapons are switch overriden in wepon classes (if nessasary)
	 */
	public void onSwitch () {
		
	}
	
	public void onFlip() {
		
	}
}
