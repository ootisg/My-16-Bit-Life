package weapons;

import gui.Tbox;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import projectiles.BubbleProjectile;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class BubbleGun extends AimableWeapon {
	
	public static final Sprite GUN = new Sprite ("resources/sprites/config/bubble_weapon.txt");
	private int textTimer;
	private int cooldown;
	private int [] upgradeInfo;
	boolean inzilaztion = false;
	public BubbleGun() {
		super(GUN);
		textTimer = 0;
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(GUN);
	}
	
	@Override
	public String checkName () {
		return "BUBBLE BLASTER";
	}
	@Override
	public String checkEnetry() {
		return "MORE THREATENING THAN YOU'D THINK.";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"HA", "HA", "BUBL", "BUBBLE"};
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
		return GUN;
	}
	
	@Override
	public void frameEvent () {
		if (!inzilaztion) {
			setY (Player.getActivePlayer().getY() + 16);
			inzilaztion = true;
		}
		if (this.cooldown > 0) {
			this.cooldown --;
		} else {
			this.getAnimationHandler().setAnimationFrame(0);
		}
	}
	
	@Override
	public void onHold () {
		if (cooldown == 0) {
			this.getAnimationHandler().setFrameTime(20);
			this.shoot (new BubbleProjectile(5 + 2 * Player.getActivePlayer().vx));
			this.getAnimationHandler().setAnimationFrame(1);
			cooldown = 4;
		}
	}
	
}
