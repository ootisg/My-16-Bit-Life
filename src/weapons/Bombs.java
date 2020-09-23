package weapons;

import java.util.Random;

import enemys.Enemy;
import gui.ListTbox;
import gui.Tbox;
import items.Bomb;
import items.Item;
import items.PopcornBag;
import items.PopcornKernel;
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

	int timer;
	int cooldown;
	int [] upgradeInfo;
	boolean inHand = false;
	BombsProjectile bomb = null;
	boolean popcorn = false;
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	private int power = 0;
	Tbox ammoAmount;
	PopcornBag bag = new PopcornBag();
	Tbox box;
	private static final Sprite BOMB_ICON_SPRITE = new Sprite ("resources/sprites/config/bomb_Icon.txt");
	private static final Sprite POPCORN_ICON_SPRITE = new Sprite ("resources/sprites/config/popcorn_Icon.txt");
	public static final Sprite INDICATOR_SPRITE = new Sprite ("resources/sprites/x.png");
	public  Bombs (Sprite sprite) {
		super(sprite);
		upgradeInfo = new int [] {0,0,0,1};
		this.setSprite(newBomb);
		ammoAmount = new Tbox ();
		ammoAmount.setX(340);
		ammoAmount.setY(10);
		ammoAmount.setWidth(24);
		ammoAmount.setHeight(1);
		ammoAmount.keepOpen(true);
		ammoAmount.renderBox(false);
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
	public Item getAmmoType () {
		return new Bomb();
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
		if (mouseButtonPressed(0) && !Jeffrey.getActiveJeffrey().isCrouched() && cooldown == 0) {
			bomb = new BombsProjectile(popcorn);
			inHand = true;
		}
		if (upgradeInfo [3] >= 1 && mouseButtonPressed(2) && !inHand) {
			if (!popcorn) {
				popcorn = true;
				this.changeSprite(new Sprite ("resources/sprites/popcorn_bag(no fire).png"));
				box = new Tbox (this.getX() - Room.getViewX() ,this.getY(), 20, 2, "POPCORN TIME", false);
				} else {
				popcorn = false;
				this.changeSprite(newBomb);
				box = new Tbox (this.getX() - Room.getViewX(),this.getY(), 20, 2, "REAL BOMB HOURS", false);
				}
				
				box.setScrollRate(0);
				box.configureTimerCloseing(30);
		}
		if (inHand) {
			if (popcorn) {
				int luck = 120 - bomb.timer;
				if (luck <= 0) {
					luck = 1;
				}
				for (int i = 0; i <10; i++) {
				Random rand = new Random ();
				if (rand.nextInt(luck) == 0) {
					double direction = rand.nextInt(3) + rand.nextDouble() + 3;
					PopcornKernel kernel = new PopcornKernel ();
					kernel.setDirection(direction);
					kernel.setSpeed(10);
					kernel.declare(this.getX() - 8,this.getY() - 8);
				}
			}
			}
			bomb.projectileFrame();
			if (mouseButtonDown(2)) {
				power++;
			}
			if (!this.getAnimationHandler().flipHorizontal()) {
				bomb.setX(this.getX()- 8);
				bomb.setY(this.getY() - 27);
				AfterRenderDrawer.drawAfterRender((int)(this.getX()- 8) - Room.getViewX(), (int)(this.getY() - 27) - Room.getViewY() , bomb.getSprite(),bomb.getAnimationHandler().getFrame());
			} else {
				bomb.setX(this.getX()+ 8);
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
		if ( inHand && !mouseButtonDown(0)) {
			
			bomb.throwBomb();
			if (popcorn) {
				Jeffrey.getInventory().removeItem(bag);
			} else {
				Bomb bom = new Bomb();
				Jeffrey.getInventory().removeItem(bom);
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
	public void draw () {
		super.draw();
		if (this.getTierInfo()[3] == 1) {
			ammoAmount.setContent(Integer.toString(Jeffrey.getInventory().checkItemAmount(bag)));
			ammoAmount.draw();
			if (popcorn) {
				POPCORN_ICON_SPRITE.draw(350, 0);
			} else {
				BOMB_ICON_SPRITE.draw(350, 0);
			}
		}
	}
}
