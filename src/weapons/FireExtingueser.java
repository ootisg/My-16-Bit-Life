package weapons;

import java.awt.Point;

import main.ObjectHandler;
import players.Jeffrey;
import projectiles.Foam;
import resources.Sprite;

public class FireExtingueser extends AimableWeapon {
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get (0);
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
		// cause cunncurrent stuff if it works properly
		int numParticles = 10;
		if (mouseButtonDown (0) && !j.isCrouched()) {
			for (int i = 0; i < numParticles; i++) {
				Point newPoint = new Point ();
				double [] working = this.simulateShot();
				double sweepAngle = 6 * Math.PI / 180;
				double initialDistance = 20;
				double newAng = working[2] - ((Math.random() - .5) * 2)*sweepAngle;
				double newDist = Math.random () * initialDistance;
				int offsX = (int)(Math.cos (newAng) * newDist);
				int offsY = (int)(Math.sin (newAng) * newDist);
				Foam f = new Foam ();
				shoot (f);
				f.setX(f.getX() + offsX);
				f.setY(f.getY() + offsY);
				f.setDirection(newAng);
			}
		}
	}
}
