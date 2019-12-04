package weapons;

import java.util.Random;

import gameObjects.Enemy;
import items.Item;
import main.GameCode;
import resources.Sprite;

public class MagicMicrophone extends Item {

	private Random RNG;
	boolean whippingLeft;
	int timer;
	int [] upgradeInfo;
	boolean addTime;
	public  MagicMicrophone () {
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
		RNG = new Random ();
		whippingLeft = false;
		timer = 0;
		addTime = false;
	}
	@Override
	public String checkName () {
		return "MAGIC MICROPHONE";
	}
	@Override
	public String checkEnetry() {
		return "RUNS ON MYSICAL SONG POWER OR AT LEAST THATS WHAT IT SAYS ON THE BOX";
	}
	@Override
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"MUSIC NOTES", "EXTRA MOVES", "WHIP UPGRADE", "SONG POWER"};
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
	public void frameEvent () {
		if (this.mouseButtonDown(0) && !GameCode.testJeffrey.getSprite().equals(GameCode.testJeffrey.ryanWhipping)) {
			GameCode.testJeffrey.getAnimationHandler().setRepeat(false);
			GameCode.testJeffrey.setSprite(GameCode.testJeffrey.ryanWhipping);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(30);
			GameCode.testJeffrey.changeSprite(false);
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal() ) {
				whippingLeft = true;
				GameCode.testJeffrey.desyncSpriteX(-34);
			}
		}
		this.setX(GameCode.testJeffrey.getX());
		this.setY(GameCode.testJeffrey.getY());
		if (GameCode.testJeffrey.getSprite().equals(GameCode.testJeffrey.ryanWhipping) && GameCode.testJeffrey.getAnimationHandler().getFrame() == 7 && !addTime) {
			GameCode.testJeffrey.setSprite(GameCode.testJeffrey.whipLength);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(0);
			GameCode.testJeffrey.changeFrameTime(false);
			GameCode.testJeffrey.getAnimationHandler().setAnimationFrame(this.dealWithWhipFrame());
		}
		if (addTime) {
			timer = timer + 1;
			if (timer == 5) {
				GameCode.testJeffrey.getAnimationHandler().setAnimationFrame(7);
			}
			if (timer == 10) {
				timer = 0;
				addTime =false;
				GameCode.testJeffrey.getAnimationHandler().setRepeat(true);
				GameCode.testJeffrey.changeFrameTime(true);
				GameCode.testJeffrey.changeSprite(true);
				GameCode.testJeffrey.setSprite(GameCode.testJeffrey.ryanMicrophoneWalking);
				if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
					GameCode.testJeffrey.desyncSpriteX(0);
					whippingLeft = false;
				}
			}
		}
		
	}
	int dealWithWhipFrame () {
		int length = 0;
		for (int v = 0; v <= 7; v = v + 1 ) {
			length = length + 4;
			if (!GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				this.setHitboxAttributes(13, 17, length, 10);	
			} else {
				this.setHitboxAttributes(-34, 17, length, 10);
			}
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					Enemy.enemyList.get(i).damage (RNG.nextInt(20) + 20);
					addTime =true;
					return(v);
				}
			}
		}
		addTime =true;
		return(7);
	}
}
