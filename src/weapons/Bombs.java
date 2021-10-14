package weapons;

import java.util.Random;

import enemys.Enemy;
import gui.ListTbox;
import gui.Tbox;
import items.Item;
import items.PopcornKernel;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.AfterRenderDrawer;
import resources.Sprite;
import projectiles.BombsProjectile;
import projectiles.MarkerPaint;

public class Bombs extends AimableWeapon {

	int timer;
	int cooldown;
	static int [] upgradeInfo;
	boolean inHand = false;
	BombsProjectile bomb = null;
	boolean popcorn = false;
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	private int power = 0;
	Tbox ammoAmount;
	Tbox box;
	private static final Sprite BOMB_ICON_SPRITE = new Sprite ("resources/sprites/config/bomb_Icon.txt");
	private static final Sprite POPCORN_ICON_SPRITE = new Sprite ("resources/sprites/config/popcorn_Icon.txt");
	public static final Sprite INDICATOR_SPRITE = new Sprite ("resources/sprites/x.png");
	public Bombs () {
		super(new Sprite ("resources/sprites/blank.png"));
		upgradeInfo = new int [] {0,0,3,1};
		ammoAmount = new Tbox ();
		ammoAmount.setX(340);
		ammoAmount.setY(10);
		ammoAmount.setWidth(24);
		ammoAmount.setHeight(1);
		ammoAmount.keepOpen(true);
		ammoAmount.renderBox(false);
		ammoAmount.setPlace();
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
	public void frameEvent () {
		if (cooldown > 0) {
			cooldown--;
		}
		
		if (inHand) {
			
			bomb.projectileFrame();
			if (mouseButtonDown(2)) {
				power++;
			}
			if (!this.getAnimationHandler().flipHorizontal()) {
				bomb.setX(this.getX() - 8);
				bomb.setY(this.getY() - 27);
				AfterRenderDrawer.drawAfterRender((int)(this.getX()- 8) - Room.getViewX(), (int)(this.getY() - 27) - Room.getViewY() , bomb.getSprite(),bomb.getAnimationHandler().getFrame());
			} else {
				bomb.setX(this.getX() + 8);
				bomb.setY(this.getY() - 27);
				AfterRenderDrawer.drawAfterRender((int)(this.getX()+ 8) - Room.getViewX(), (int)(this.getY() - 27) - Room.getViewY(), bomb.getSprite(),bomb.getAnimationHandler().getFrame());
			}
			double [] landPoint = new double [2]; 
			double [] landInfo = this.simulateShot();
			if (!this.getAnimationHandler().flipHorizontal()) {
				landPoint = bomb.computeLandPoint(landInfo[0],landInfo[1],landInfo[2], 12*(1 + power/60.0), 18*(1 + power/60.0), true);
			} else {
				landPoint = bomb.computeLandPoint(landInfo[0],landInfo[1],landInfo[2], -1*12*(1 + power/60.0), 18*(1 + power/60.0), false);
			}
			AfterRenderDrawer.drawAfterRender((int)landPoint[0] - Room.getViewX() -7, (int)landPoint[1] - Room.getViewY(), INDICATOR_SPRITE);
			if (bomb.timer > 120) {
				inHand = false;
				power = 0;
			}
		}	
	}
	
	@Override
	public void onFire () {
		if (cooldown == 0) {
			if ((!popcorn && canFire()) || (popcorn && canFireSecondary())) {
				bomb = new BombsProjectile(popcorn);
				inHand = true;
			}			
		}
	}
	
	@Override
	public void onRelease () {
		if (inHand) {
			bomb.throwBomb();
			if (popcorn) {
				this.fireSecondaryAmmo(1);
			} else {
				this.fireAmmo(1);
			}
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
	
	@Override
	public void onSwitchModes () {
		if (upgradeInfo [3] >= 1 && !inHand) {
			if (!popcorn) {
					popcorn = true;
				  //box = new Tbox (this.getX() - Room.getViewX() ,this.getY(), 20, 2, "POPCORN TIME", false);
				} else {
					popcorn = false;
					//box = new Tbox (this.getX() - Room.getViewX(),this.getY(), 20, 2, "REAL BOMB HOURS", false);
				}
				
				//box.setScrollRate(0);
				//box.configureTimerCloseing(30);
		}
	}
	
	@Override 
	public void draw () {
		super.draw();
		if (this.getTierInfo()[3] == 1) {
			ammoAmount.setContent(Double.toString(this.getSecondaryAmmoCount()));
			ammoAmount.draw();
			if (popcorn) {
				POPCORN_ICON_SPRITE.draw(350, 0);
			} else {
				BOMB_ICON_SPRITE.draw(350, 0);
			}
		}
	}
}
