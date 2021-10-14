package weapons;

import java.util.Random;

import enemys.Enemy;
import gui.ListTbox;
import items.Item;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import players.Ryan;
import resources.Sprite;

public class MagicMicrophone extends Weapon {

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
//TODO rewrite this entirly once the sprite stuff is finalized
	@Override
	public void frameEvent () {
//		if (this.mouseButtonClicked(2) && this.getTierInfo()[3] == 1) {
//			// + room.getViewY may need to be - Room.getVeiwY()
//			box = new ListTbox (this.getX() - Room.getViewX(),this.getY() + Room.getViewY(),new String [] {"JEFFREY","SAM","RYAN"});
//			ObjectHandler.pause(true);
//		}
//		
//		this.setX(Player.getActivePlayer().getX());
//		this.setY(Player.getActivePlayer().getY());
//		if (Player.getActivePlayer().getSprite().equals(Player.RYAN_WHIPPING) && Player.getActivePlayer().getAnimationHandler().getFrame() == 7 && !addTime) {
//			Player.getActivePlayer().setSprite(Player.WHIP_LENGTH);
//			Player.getActivePlayer().getAnimationHandler().setFrameTime(0);
//			Player.getActivePlayer().changeFrameTime(false);
//			Player.getActivePlayer().getAnimationHandler().setAnimationFrame(this.dealWithWhipFrame());
//		}
//		if (addTime) {
//			timer = timer + 1;
//			if (timer == 5) {
//				Player.getActivePlayer().getAnimationHandler().setAnimationFrame(7);
//			}
//			if (timer == 10) {
//				timer = 0;
//				addTime =false;
//				Player.getActivePlayer().getAnimationHandler().setRepeat(true);
//				Player.getActivePlayer().changeFrameTime(true);
//				Player.getActivePlayer().changeSprite(true);
//				Player.getActivePlayer().crouchElegable(true);
//				Player.getActivePlayer().setSprite(Player.RYAN_MICROPHONE_WALKING);
//				if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//					Player.getActivePlayer().desyncSpriteX(0);
//					whippingLeft = false;
//				}
//			}
//		}
		
	}
	
	@Override
	public void onHold () {
		
		
		//TODO rewirte sprite stuff
//		Player.getActivePlayer().getAnimationHandler().setRepeat(false);
//		Player.getActivePlayer().setSprite(Ryan.RYAN_WHIPPING);
//		Player.getActivePlayer().getAnimationHandler().setFrameTime(10);
//		Player.getActivePlayer().changeSprite(false);
//		Player.getActivePlayer().crouchElegable(false);
//		if (Player.getActivePlayer().getAnimationHandler().flipHorizontal() ) {
//			whippingLeft = true;
//			Player.getActivePlayer().desyncSpriteX(-34);
//		}
//		
	}
	
	@Override
	public void onFlip() {

		//TODO rewrite the sprite stuff
//		if (!Player.getActivePlayer().getSprite().equals(Ryan.RYAN_WHIPPING)) {
//			if (Player.getActivePlayer().getSprite().equals(Ryan.RYAN_WHIPPING) || Player.getActivePlayer().getSprite().equals(Ryan.WHIP_LENGTH)) {
//				if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//					Player.getActivePlayer().desyncSpriteX(0);
//				} else {
//					Player.getActivePlayer().desyncSpriteX(-34);
//				}
//			}
//		}
	}
	@Override 
	public void pausedEvent () {
		try {
			GameCode.getPartyManager().getPlayer(box.getSelected()).status.applyStatus("Powerful", 100);
			GameCode.getPartyManager().getPlayer(box.getSelected()).status.applyStatus("Regeneration2", 100);
			ObjectHandler.pause(false);
			box.close();
		} catch (NullPointerException e) {
			
		}
	}
	@Override 
	public void onSwitch () {
//		Player.getActivePlayer().changeSprite(true);
//		Player.getActivePlayer().getAnimationHandler().setRepeat(true);
//		if (whippingLeft) {
//			Player.getActivePlayer().desyncSpriteX(0);
//		}
	}
	int dealWithWhipFrame () {
		int length = 0;
		for (int v = 0; v <= 7; v = v + 1 ) {
			length = length + 4;
			if (!Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
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
