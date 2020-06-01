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
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	public static final Sprite newBomb = new Sprite ("resources/sprites/config/bomb_active_blue.txt");
	private Sprite bombIconSprite;
	boolean firstRun = true;
	
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
	public void frameEvent () {
		if (firstRun) {
			AfterRenderDrawer.drawAfterRender(350, 0, bombIconSprite, 0, true);
			firstRun = false;
		}
		if (cooldown > 0) {
			cooldown--;
		}
		if (mouseButtonClicked (0) && !j.isCrouched() && !mouseButtonReleased (0)) {
			this.shoot(new BombsProjectile());
			//The two lines below are for test throwing, and it always throws them in the same angle.
			//The this.shoot doesn't actually shoot and I have no clue why
			BombsProjectile bomb = new BombsProjectile();
			bomb.declare(j.getX() + 8, j.getY() + 8);
			
			cooldown = 5;
		}
	}	
}
