package weapons;

import java.util.Random;

import enemys.Enemy;
import gui.ListTbox;
import items.Item;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Power;
import statusEffect.Regeneration;

public class MagicMicrophone extends Item {

	private Random RNG;
	boolean whippingLeft;
	int timer;
	int [] upgradeInfo;
	boolean addTime;
	ListTbox box;
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
	public  MagicMicrophone () {
		upgradeInfo = new int [] {0,0,0,1};
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
		if (this.mouseButtonClicked(2) && this.getTierInfo()[3] == 1) {
			// + room.getViewY may need to be - Room.getVeiwY()
			box = new ListTbox (this.getX() - Room.getViewX(),this.getY() + Room.getViewY(),new String [] {"JEFFREY","SAM","RYAN"});
			ObjectHandler.pause(true);
		}
		if (this.mouseButtonDown(0) && !GameCode.testJeffrey.getSprite().equals(GameCode.testJeffrey.ryanWhipping)) {
			GameCode.testJeffrey.getAnimationHandler().setRepeat(false);
			GameCode.testJeffrey.setSprite(GameCode.testJeffrey.ryanWhipping);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(10);
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
	@Override 
	public void pausedEvent () {
		try {
		if (box.getSelected() == 0) {
			player.status.statusAppliedOnJeffrey[6] = true;
			Power power = new Power (0);
			power.declare();
			player.status.statusAppliedOnJeffrey[7] = true;
			Regeneration regen = new Regeneration (0);
			regen.declare();
			ObjectHandler.pause(false);
			box.close();
		} else {
			if (box.getSelected() == 1) {
				player.status.statusAppliedOnSam[6] = true;
				Power power = new Power (1);
				power.declare();
				player.status.statusAppliedOnSam[7] = true;
				Regeneration regen = new Regeneration (1);
				regen.declare();
				ObjectHandler.pause(false);
				box.close();
			} else {
				if (box.getSelected() == 2) {
				player.status.statusAppliedOnRyan[6] = true;
				Power power = new Power (2);
				power.declare();
				player.status.statusAppliedOnRyan[7] = true;
				Regeneration regen = new Regeneration (2);
				regen.declare();
				ObjectHandler.pause(false);
				box.close();
			}
			}
		}
		} catch (NullPointerException e) {
			
		}
	}
	int dealWithWhipFrame () {
		int length = 0;
		for (int v = 0; v <= 7; v = v + 1 ) {
			length = length + 4;
			if (!GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				this.setHitboxAttributes(13, 14, length, 13);	
			} else {
				this.setHitboxAttributes(-34, 14, length, 13);
			}
			boolean good = false;
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					Enemy.enemyList.get(i).damage (RNG.nextInt(20) + 50);
					addTime =true;
					good = true;
				}
			}
			if (good) {
				return v;
			}
		}
		addTime =true;
		return(7);
	}
}
