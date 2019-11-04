package weapons;

import java.util.Random;

import gameObjects.Enemy;
import main.GameCode;
import map.Room;
import resources.Sprite;

public class LifeVaccum extends AimableWeapon {
	Sprite wind;
	Random RNG;
	Boolean loseBattary;
	Sprite outtaAmmo;
	int timer;
	int [] upgradeInfo;
	Sprite vaccumSprite;
	public LifeVaccum (Sprite sprite) {
		super (sprite);
		upgradeInfo = new int [] {0,0,0,0};
		vaccumSprite = new Sprite ("resources/sprites/config/lifeVaccum.txt");
		timer = 0;
		RNG = new Random ();
		outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
		this.setSprite(vaccumSprite);
		wind = new Sprite ("resources/sprites/config/wind.txt");
		this.setHitboxAttributes(16, 0, 45, 32);
		loseBattary = false;
	}
	@Override
	public String checkName() {
		return ("LIFE VACUUM");
	}
	@Override 
	public String checkEnetry() {
		return "SUCKS THE LIFE OUTTA EM";
	}
	@Override
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"PROJECTILES", "FASTER SUCKING", "EXTRA HEATLH", "SPEED"};
		return returnArray;
	}
@Override
	public int [] getTierInfo () {
		return upgradeInfo;
	}
	@Override 
	public Sprite getUnrotatedSprite () {
		return vaccumSprite;
	}
	public void frameEvent () {
		timer = timer + 1;
		// this may need to be a diffrent number
		if (mouseButtonDown (0)) {
			if (GameCode.testJeffrey.getInventory().checkLifeVaccumBattary() > 0) {
			if (loseBattary) {
				GameCode.testJeffrey.getInventory().subtractLifeVaccumBattary(1);
			}
			loseBattary = !loseBattary;
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					// no clue if this works
					int damageDone = RNG.nextInt(2) + 2;
					if (timer == 8) {
						timer = 0;
					Enemy.enemyList.get(i).damage (damageDone);
					GameCode.testJeffrey.samHealth = GameCode.testJeffrey.samHealth + ((int)damageDone/10 + 1);
					if (GameCode.testJeffrey.samHealth >100) {
						GameCode.testJeffrey.samHealth = 100;
					}
					}
					}
				}
			boolean flipHorizontal;
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				flipHorizontal = true;
			} else {
				flipHorizontal = false;
			}
			if (flipHorizontal) {
			wind.draw((int) this.getX() - (RNG.nextInt(21) + 1) -(Room.getViewX()), (int)this.getY() - RNG.nextInt(31) + 1, flipHorizontal, false, 0);
			} else {
				wind.draw(((int) this.getX() + (RNG.nextInt(21) + 1)) -(Room.getViewX()), (int)this.getY() - RNG.nextInt(31) + 1, flipHorizontal, false, 0);	
			}
			
		} else {
			outtaAmmo.draw((int)this.getX() - Room.getViewX(), (int)this.getY() - 10);
		}
			GameCode.testJeffrey.vx = GameCode.testJeffrey.vx/2;
	}
	}
}
