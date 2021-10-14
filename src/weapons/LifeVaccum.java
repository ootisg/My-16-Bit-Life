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
import players.Player;
import projectiles.LifeVaccumProjectile;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class LifeVaccum extends Weapon {
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
	@Override
	public void onSwitch() {
		wind.hide();
	}
	public void frameEvent () {
		if (!wind.declared()) {
			wind.declare();
		}
		timer = timer + 1;
	
		if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
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
	public void onSecondaryHold () {
		if (wind.isColliding("Box")) {
			Iterator<GameObject> iter = wind.getCollisionInfo().getCollidingObjects().iterator();
			while (iter.hasNext()) {
				GameObject working = iter.next();
				if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
					working.goX(working.getX() -2);
				} else {
					working.goX(working.getX() +2);
				}
			}
		}
	}
	
	@Override
	public void onRelease () {
		wind.hide();
		
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
	quicknessTimer = 0;
	}
	
	@Override
	public void onHold () {
		
		quicknessTimer = quicknessTimer + 1;
		
		if (loseBattary) {
			this.fireAmmo(1);
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
				GameCode.getPartyManager().getPlayer(1).heal(damageDone/4 + 0.2);
			}
		}
	}
			wind.show();
		Player.getActivePlayer().vx = Player.getActivePlayer().vx/1.25;
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
