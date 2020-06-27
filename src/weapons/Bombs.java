package weapons;

import java.util.Random;

import enemys.Enemy;
import gui.ListTbox;
import items.Item;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;
import statusEffect.Power;
import statusEffect.Regeneration;
import projectiles.BombsProjectile;
import projectiles.MarkerPaint;

public class Bombs extends AimableWeapon {

	private Random RNG;
	int timer;
	int cooldown;
	int [] upgradeInfo;
	boolean itsOver = false;
	boolean inHand = false;
	BombsProjectile bomb = null;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	private Sprite bombIconSprite;
	boolean firstRun = true;
	private int power = 0;
	public static final Sprite INDICATOR_SPRITE = new Sprite ("resources/sprites/x.png");
	public  Bombs (Sprite sprite) {
		super(sprite);
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(newBomb);
		bombIconSprite = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
		RNG = new Random ();
		timer = 0;
	}
	
	@Override
	public String checkName () {
		return "BOMBS";
	}
	@Override
	public String checkEnetry() {
		return "WHAT MORE CAN I SAY, THEY'RE BOMBS";
	}
	@Override
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"KABOOM", "HAHA", "FUNNY FUNNY", "HAHA"};
		return returnArray;
	}
	@Override 
	public String getItemType() {
		return "WeaponRyan";
	}
	@Override
	public int [] getTierInfo () {
		return upgradeInfo;
	}
	@Override 
	public Sprite getUnrotatedSprite () {
		return newBomb;
	}
	@Override
	public void onSwitch () {
		AfterRenderDrawer.removeElement(bombIconSprite, 350, 0);
		firstRun = true;
		itsOver = true;
	}
	@Override
	public void frameEvent () {
		if (firstRun) {
			if (!itsOver) {
			AfterRenderDrawer.drawAfterRender(350, 0, bombIconSprite, 0, true);
			firstRun = false;
			} else {
				itsOver = false;
			}
		}
		if (cooldown > 0) {
			cooldown--;
		}
		if (mouseButtonPressed(0) && !j.isCrouched() && cooldown == 0) {
			bomb = new BombsProjectile();
			inHand = true;
		}
		if (inHand) {
			bomb.projectileFrame();
			power = power + 1;
			if (!this.getAnimationHandler().flipHorizontal()) {
				bomb.setX(this.getX()- 8);
				bomb.setY(this.getY() - 27);
				AfterRenderDrawer.drawAfterRender((int)this.getX()- 8, (int)this.getY() - 27, bomb.getSprite(),bomb.getAnimationHandler().getFrame());
			} else {
				bomb.setX(this.getX()+ 8);
				bomb.setY(this.getY() - 27);
				AfterRenderDrawer.drawAfterRender((int)this.getX()+ 8, (int)this.getY() - 27, bomb.getSprite(),bomb.getAnimationHandler().getFrame());
			}
			double [] landPoint = new double [2]; 
			double [] landInfo = this.simulateShot(bomb, this.getRotation());
			if (!this.getAnimationHandler().flipHorizontal()) {
				landPoint = bomb.computeLandPoint(landInfo[0],landInfo[1],landInfo[2], 12*(1 + power/60.0), 18*(1 + power/60.0), true);
			} else {
				landPoint = bomb.computeLandPoint(landInfo[0],landInfo[1],landInfo[2], -1*12*(1 + power/60.0), 18*(1 + power/60.0), false);
			}
			AfterRenderDrawer.drawAfterRender((int)landPoint[0] - Room.getViewX(), (int)landPoint[1] - Room.getViewY(), INDICATOR_SPRITE);
			if (bomb.timer > 120) {
				inHand = false;
				power = 0;
			}
		}
		if ( inHand && !mouseButtonDown(0)) {
			
			bomb.throwBomb();
			if (!this.getAnimationHandler().flipHorizontal()) {
				bomb.setFakeDireciton(true);
				bomb.setVx(12*(1 + power/60.0));
			} else {
				bomb.setVx( -1*12*(1 + power/60.0));
				bomb.setFakeDireciton(false);
			}
			bomb.setVy(18*(1 + power/60.0));
			this.shoot(bomb);
			cooldown = 5;
			inHand = false;
			power = 0;
		}	
	}
}
