package weapons;

import main.ObjectHandler;
import players.Jeffrey;
import projectiles.Foam;
import resources.Sprite;

public class FireExtingueser extends AimableWeapon {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").getFirst();
	public FireExtingueser(Sprite sprite) {
		super(sprite);
	}
	@Override 
	public Sprite getUnrotatedSprite() {
		return new Sprite ("resources/sprites/config/Fire_Rextinguisher_Idle.txt");
	}
	@Override
	public String checkName () {
		return "FIRE EXTINGUSER";
	}
	@Override
	public String checkEnetry() {
		return "A DEVICE USED FOR EXTINUSING FIRES";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"", "", "", ""};
			return returnArray;
		}
	@Override 
	public String getItemType() {
		return "WeaponJeffrey";
	}
	@Override
		public int [] getTierInfo () {
			return new int [] {0,0,0,0};
		}
	@Override
	public void frameEvent () {
		if (mouseButtonDown (0) && !j.isCrouched()) {
			this.shoot(new Foam());
		}
	}
}
