package weapons;




import java.util.Arrays;

import main.ObjectHandler;
import players.Jeffrey;
import projectiles.Triangle;
import resources.Sprite;

public class NinjaTriangle extends AimableWeapon{
	public int [] upgradeInfo;
	public static boolean inHand = true;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get (0);
	public NinjaTriangle(Sprite sprite) {
		super(sprite);
		upgradeInfo = new int [4];
		Arrays.fill(upgradeInfo, 2);
	}
	@Override
	public String checkName () {
		return "NINJA TRIANGLE";
	}
	@Override
	public String checkEnetry() { 
		return "TRULLY THE MOST INVATIVE APPLICATION OF CREATIVE ENGNEERING";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"BULLET SPRAY", "TRACKY TRIANGLE", "BOUNCY PAPER", "ANOTHER NINJA TRIANGLE"};
			return returnArray;
		}
	@Override 
	public String getItemType() {
		return "WeaponJeffrey";
	}
	@Override
		public int [] getTierInfo () {
			return upgradeInfo;
		}
	@Override 
	public Sprite getUnrotatedSprite () {
		return  new Sprite ("resources/sprites/config/stationary_ninja_triangle.txt");
	}
	@Override 
	public void frameEvent () {
		if (!NinjaTriangle.inHand) {
			this.getAnimationHandler().hide();
		}
		if (NinjaTriangle.inHand && mouseButtonPressed (0) && !j.isCrouched()) {
			NinjaTriangle.inHand = false;
			Triangle projectileForme;
			if (this.getAnimationHandler().flipHorizontal()) {
			if (this.getRotation() >= 0) {
			projectileForme = new Triangle (this.getRotation() + 1.57, 10, 0, this);	
			} else {
				projectileForme = new Triangle (this.getRotation() - 1.57, 10, 0, this);		
			}
			} else {
			projectileForme = new Triangle (this.getRotation(), 10, 0, this);
			}
			projectileForme.declare(this.getX(),this.getY());
		}
	}
}
