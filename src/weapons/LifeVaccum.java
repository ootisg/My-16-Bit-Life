package weapons;

import java.util.Random;

import enemys.Enemy;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class LifeVaccum extends AimableWeapon {
	Random RNG;
	Boolean loseBattary;
	int timer;
	int [] upgradeInfo;
	Sprite vaccumSprite;
	Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
	Wind wind = new Wind ();
	public final Sprite OUTTA_AMMO = new Sprite ("resources/sprites/Outta_Ammo.png");
	public LifeVaccum (Sprite sprite) {
		super (sprite);
		vaccumSprite = new Sprite( "resources/sprites/config/lifeVaccum.txt");
		upgradeInfo = new int [] {0,0,0,0};
		timer = 0;
		RNG = new Random (); 
		this.setSprite(vaccumSprite);
		this.setHitboxAttributes(16, -16, 45, 32);
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
	public String getItemType() {
		return "WeaponSam";
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
			if (jeffrey.getInventory().checkLifeVaccumBattary() > 0) {
			if (loseBattary) {
				jeffrey.getInventory().subtractLifeVaccumBattary(1);
			}
			loseBattary = !loseBattary;
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					// no clue if this works
					int damageDone = RNG.nextInt(2) + 2;
					if (timer % 2 == 0) {
						timer = 0;
					Enemy.enemyList.get(i).damage (damageDone);
				
					jeffrey.samHealth = jeffrey.samHealth + ((int)damageDone/4 + .2);
					if (jeffrey.samHealth >jeffrey.maxSamHealth) {
						jeffrey.samHealth = jeffrey.maxSamHealth;
					}
					}
					}
				}
				wind.draw();
		} else {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 10, OUTTA_AMMO);
		}
			jeffrey.vx = jeffrey.vx/1.25;
	}
		if (jeffrey.getAnimationHandler().flipHorizontal()) {
			this.setHitboxAttributes(-48, -16, 49, 32);
			wind.setX(this.getX() -48);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(true);
		} else {
			this.setHitboxAttributes(12, -16, 49, 32);
			wind.setX(this.getX() + 12);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(false);
		}
	}
	
}
class Wind extends GameObject {
	public final Sprite WIND =  new Sprite ("resources/sprites/config/tornado.txt");
	public Wind () {
		this.getAnimationHandler().setFrameTime(50);
		this.setSprite(WIND);
	}
}
