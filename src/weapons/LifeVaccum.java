package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;

import enemys.Enemy;
import items.Item;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;
import projectiles.LifeVaccumProjectile;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class LifeVaccum extends Item {
	Random RNG;
	Boolean loseBattary;
	int timer;
	int [] upgradeInfo;
	Sprite vaccumSprite;
	Wind wind = new Wind ();
	int quicknessTimer = 0;
	boolean inzialized = false;
	int projectileCharge;
	Graphics g = RenderLoop.window.getBufferGraphics();
	public final Sprite OUTTA_AMMO = new Sprite ("resources/sprites/Outta_Ammo.png");
	public LifeVaccum () {
		vaccumSprite = new Sprite( "resources/sprites/config/lifeVaccum.txt");
		upgradeInfo = new int [] {0,0,1,0};
		timer = 0;
		projectileCharge = 0;
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
		if (mouseButtonDown (0)) {
			quicknessTimer = quicknessTimer + 1;
		} else {
			if (!mouseButtonDown (0)) {
				if ((quicknessTimer > 0 && quicknessTimer < 9) &&  ((projectileCharge > 45 && this.getTierInfo()[2] >= 1) || (projectileCharge > 30 && this.getTierInfo()[2] >= 3)) ) {
					if (this.getTierInfo()[2] >= 1 && this.getTierInfo()[2] < 3) {
						projectileCharge = projectileCharge - 45;
					} else {
						projectileCharge = projectileCharge - 30;
					}
					LifeVaccumProjectile projectile = new LifeVaccumProjectile ();
					if (this.getAnimationHandler().flipHorizontal()) {
						projectile.setDirection(3.14);
					} else {
						projectile.setDirection(0);
					}
					projectile.declare(this.getX(), this.getY());
				} 
			}
			quicknessTimer = 0;
		}
		// this may need to be a diffrent number
		if (mouseButtonDown (0) && !Jeffrey.getActiveJeffrey().isCrouched()) {
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
					if (this.getTierInfo()[2] == 1) {
						if (projectileCharge < 180) {
							projectileCharge = projectileCharge + 1;
						}
					} else {
						if (this.getTierInfo()[2] >= 1) {
							if (projectileCharge < 275) {
								projectileCharge = projectileCharge + 1;
							}
						}
					}
					Jeffrey.samHealth = Jeffrey.samHealth + ((int)damageDone/4 + .2);
					if (Jeffrey.samHealth >Jeffrey.maxSamHealth) {
						Jeffrey.samHealth = Jeffrey.maxSamHealth;
					}
					}
					}
				}
				wind.show();
		} else {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 10, OUTTA_AMMO);
		}
			Jeffrey.getActiveJeffrey().vx = Jeffrey.getActiveJeffrey().vx/1.25;
	} else {
		wind.hide();
	}
		if (mouseButtonDown (2) && !Jeffrey.getActiveJeffrey().isCrouched()) {
			if (wind.isColliding("Box")) {
				Iterator<GameObject> iter = wind.getCollisionInfo().getCollidingObjects().iterator();
				while (iter.hasNext()) {
					GameObject working = iter.next();
					if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
						working.goX(working.getX() -2);
					} else {
						working.goX(working.getX() +2);
					}
				}
			}
		}
		if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
			wind.setHitboxAttributes(0, 0, 49, 32);
			wind.setX(this.getX() -48);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(true);
		} else {
			wind.setHitboxAttributes(0,0, 49, 32);
			wind.setX(this.getX() + 12);
			wind.setY(this.getY() - 16);
			wind.getAnimationHandler().setFlipHorizontal(false);
		}
	}
	@Override
	public void draw () {
		super.draw();
		if (this.getTierInfo()[2] == 1) {
			g.setColor(new Color (0x800080));
			g.drawRect(0, 24, 180, 16);
			g.fillRect(0, 24, projectileCharge, 16);
		} else {
			if (this.getTierInfo()[2] >= 2) {
				g.setColor(new Color (0x800080));
				g.drawRect(0, 24, 275, 16);
				g.fillRect(0, 24, projectileCharge, 16);
			}
		}
	}
}
class Wind extends GameObject {
	public final Sprite WIND =  new Sprite ("resources/sprites/config/tornado.txt");
	public Wind () {
		this.adjustHitboxBorders();
		this.getAnimationHandler().setFrameTime(50);
		this.setHitboxAttributes(0, 0, 49, 32);
		this.setSprite(WIND);
		
	}
}
