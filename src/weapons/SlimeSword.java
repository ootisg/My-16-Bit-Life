package weapons;

import java.util.ArrayList;
import java.util.Random;

import gameObjects.Enemy;
import items.Item;
import main.GameCode;
import resources.Sprite;

public class SlimeSword extends Item {
	Sprite samSwingSprite;
	Random RNG;
	public Sprite samWalkingSword;
	boolean coolDown;
	ArrayList <Enemy> hitEnemys = new ArrayList ();
	int damageCoolDown;
	int [] upgradeInfo;
	public SlimeSword () {
		this.setHitboxAttributes(11, 0, 0, 0);
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
		RNG = new Random ();
		upgradeInfo = new int [] {0,0,0,0};
		samWalkingSword = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
		damageCoolDown = 20;
		coolDown = false;
	samSwingSprite = new Sprite ("resources/sprites/config/slime_swing.txt");
	}
	@Override
	public String checkName () {
		return "SLIME SWORD";
	}
	@Override
	public String checkEnetry() {
		return "ALITERATION IS FUN";
	}
	@Override
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"ARIAL ATTACKS", "SWORD THROW", "SLOWNESS OR SOMETHING", "HOOKSHOT"};
		return returnArray;
	}
	@Override 
	public String getItemType() {
		return "WeaponSam";
	}
@Override
	public int [] getTierInfo () {
		return upgradeInfo;
	}
	@Override
	public void frameEvent() {
		if (this.mouseButtonClicked(0) && !GameCode.testJeffrey.getSprite().equals(samSwingSprite) ) {
			GameCode.testJeffrey.getAnimationHandler().setRepeat(false);
			GameCode.testJeffrey.setSprite(samSwingSprite);
			GameCode.testJeffrey.changeSprite(false);
			GameCode.testJeffrey.binded = true;
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() -34);
				GameCode.testJeffrey.stopFall(true);
			}
		}
		if (GameCode.testJeffrey.getSprite().equals(samSwingSprite) && GameCode.testJeffrey.getAnimationHandler().getFrame() ==  11) {
			GameCode.testJeffrey.binded = false;
			GameCode.testJeffrey.changeSprite(true);
			GameCode.testJeffrey.setSprite(samWalkingSword);
			GameCode.testJeffrey.getAnimationHandler().setRepeat(true);
			hitEnemys.removeAll(hitEnemys);
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() + 34);
				GameCode.testJeffrey.stopFall(false);
			}
		}
		if (GameCode.testJeffrey.getSprite().equals(samSwingSprite)) {
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
			this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { 0, 0, 0, 0,12,15,10,12,10, 10,0,0}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , GameCode.testJeffrey);
			} else {
				this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { 0, 0, 0, 45,0,0,0,0,0, 36,0,0}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , GameCode.testJeffrey);
			}
		}
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i)) && !hitEnemys.contains(Enemy.enemyList.get(i)) ){
				hitEnemys.add(Enemy.enemyList.get(i));
				Enemy.enemyList.get(i).damage (RNG.nextInt(50) + 20);
			}
		}
	}
}
