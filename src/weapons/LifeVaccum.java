package weapons;

import java.util.Iterator;
import java.util.Random;

import enemys.Enemy;
import items.Item;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class LifeVaccum extends Item {
	Random RNG;
	Boolean loseBattary;
	int timer;
	int [] upgradeInfo;
	Sprite vaccumSprite;
	Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
	Wind wind = new Wind ();
	boolean inzialized = false;
	public final Sprite OUTTA_AMMO = new Sprite ("resources/sprites/Outta_Ammo.png");
	public LifeVaccum () {
		vaccumSprite = new Sprite( "resources/sprites/config/lifeVaccum.txt");
		upgradeInfo = new int [] {0,0,0,0};
		timer = 0;
		wind.declare ();
		wind.hide();
		RNG = new Random (); 
		this.setSprite(vaccumSprite);
		this.setHitboxAttributes(16, -16, 45, 32);
		loseBattary = false;
		//this.adjustHitboxBorders();
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
		if (!wind.declared()) {
			wind.declare();
		}
		timer = timer + 1;
		// this may need to be a diffrent number
		wind.setX(this.getX());
		wind.setY(this.getY());
		if (mouseButtonDown (0) && !jeffrey.isCrouched()) {
			if (Jeffrey.getInventory().checkLifeVaccumBattary() > 0) {
			if (loseBattary) {
				Jeffrey.getInventory().subtractLifeVaccumBattary(1);
			}
			loseBattary = !loseBattary;
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (wind.isColliding(Enemy.enemyList.get(i))){
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
				wind.show();
		} else {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 10, OUTTA_AMMO);
		}
			jeffrey.vx = jeffrey.vx/1.25;
	} else {
		wind.hide();
	}
		if (mouseButtonDown (2) && !jeffrey.isCrouched()) {
			if (this.isColliding("Box")) {
				Iterator<GameObject> iter = this.getCollisionInfo().getCollidingObjects().iterator();
				while (iter.hasNext()) {
					GameObject working = iter.next();
					if (jeffrey.getAnimationHandler().flipHorizontal()) {
						working.goX(working.getX() -2);
					} else {
						working.goX(working.getX() +2);
					}
				}
			}
		}
		if (jeffrey.getAnimationHandler().flipHorizontal()) {
			wind.setHitboxAttributes(0, 0, 49, 32);
			wind.setX(this.getX() -48);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(true);
		} else {
			wind.setHitboxAttributes(0, 0, 49, 32);
			wind.setX(this.getX() + 12);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(false);
		}
	}
	
}
class Wind extends GameObject {
	public final Sprite WIND =  new Sprite ("resources/sprites/config/tornado.txt");
	public Wind () {
		this.adjustHitboxBorders();
		this.getAnimationHandler().setFrameTime(50);
		this.setHitboxAttributes(-48, 0, 49, 32);
		this.setSprite(WIND);
		
	}
}
