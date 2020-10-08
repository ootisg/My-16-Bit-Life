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
		if (this.mouseButtonDown(0) && !Jeffrey.getActiveJeffrey().getSprite().equals(Jeffrey.RYAN_WHIPPING) && !Jeffrey.getActiveJeffrey().isCrouched()) {
			Jeffrey.getActiveJeffrey().getAnimationHandler().setRepeat(false);
			Jeffrey.getActiveJeffrey().setSprite(Jeffrey.RYAN_WHIPPING);
			Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(10);
			Jeffrey.getActiveJeffrey().changeSprite(false);
			Jeffrey.getActiveJeffrey().crouchElegable(false);
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal() ) {
				whippingLeft = true;
				Jeffrey.getActiveJeffrey().desyncSpriteX(-34);
			}
		}
		this.setX(Jeffrey.getActiveJeffrey().getX());
		this.setY(Jeffrey.getActiveJeffrey().getY());
		if (Jeffrey.getActiveJeffrey().getSprite().equals(Jeffrey.RYAN_WHIPPING) && Jeffrey.getActiveJeffrey().getAnimationHandler().getFrame() == 7 && !addTime) {
			Jeffrey.getActiveJeffrey().setSprite(Jeffrey.WHIP_LENGTH);
			Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(0);
			Jeffrey.getActiveJeffrey().changeFrameTime(false);
			Jeffrey.getActiveJeffrey().getAnimationHandler().setAnimationFrame(this.dealWithWhipFrame());
		}
		if (addTime) {
			timer = timer + 1;
			if (timer == 5) {
				Jeffrey.getActiveJeffrey().getAnimationHandler().setAnimationFrame(7);
			}
			if (timer == 10) {
				timer = 0;
				addTime =false;
				Jeffrey.getActiveJeffrey().getAnimationHandler().setRepeat(true);
				Jeffrey.getActiveJeffrey().changeFrameTime(true);
				Jeffrey.getActiveJeffrey().changeSprite(true);
				Jeffrey.getActiveJeffrey().crouchElegable(true);
				Jeffrey.getActiveJeffrey().setSprite(Jeffrey.RYAN_MICROPHONE_WALKING);
				if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
					Jeffrey.getActiveJeffrey().desyncSpriteX(0);
					whippingLeft = false;
				}
			}
		}
		
	}
	@Override
	public void onFlip() {
		if (Jeffrey.getActiveJeffrey().getSprite().equals(Jeffrey.RYAN_WHIPPING) || Jeffrey.getActiveJeffrey().getSprite().equals(Jeffrey.WHIP_LENGTH)) {
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
				Jeffrey.getActiveJeffrey().desyncSpriteX(0);
			} else {
				Jeffrey.getActiveJeffrey().desyncSpriteX(-34);
			}
		}
	}
	@Override 
	public void pausedEvent () {
		try {
		if (box.getSelected() == 0) {
			Jeffrey.status.statusAppliedOnJeffrey[6] = true;
			Power power = new Power (0);
			power.declare();
			Jeffrey.status.statusAppliedOnJeffrey[7] = true;
			Regeneration regen = new Regeneration (0);
			regen.declare();
			ObjectHandler.pause(false);
			box.close();
		} else {
			if (box.getSelected() == 1) {
				Jeffrey.status.statusAppliedOnSam[6] = true;
				Power power = new Power (1);
				power.declare();
				Jeffrey.status.statusAppliedOnSam[7] = true;
				Regeneration regen = new Regeneration (1);
				regen.declare();
				ObjectHandler.pause(false);
				box.close();
			} else {
				if (box.getSelected() == 2) {
				Jeffrey.status.statusAppliedOnRyan[6] = true;
				Power power = new Power (2);
				power.declare();
				Jeffrey.status.statusAppliedOnRyan[7] = true;
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
	@Override 
	public void onSwitch () {
		Jeffrey.getActiveJeffrey().changeSprite(true);
		Jeffrey.getActiveJeffrey().getAnimationHandler().setRepeat(true);
		if (whippingLeft) {
			Jeffrey.getActiveJeffrey().desyncSpriteX(0);
		}
	}
	int dealWithWhipFrame () {
		int length = 0;
		for (int v = 0; v <= 7; v = v + 1 ) {
			length = length + 4;
			if (!Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
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
