package weapons;

import gui.Tbox;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.BubbleProjectile;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class BubbleGun extends AimableWeapon {
	
	public static final Sprite outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
	Sprite Gun = new Sprite ("resources/sprites/config/bubble_weapon.txt");
	int color;
	private Sprite paintballiconSprite;
	private int textTimer;
	private int cooldown;	
	boolean itsOver = false;
	boolean firstRun = true;
	private int [] upgradeInfo;
	boolean inzilaztion = false;
	public BubbleGun(Sprite sprite) {
		super(sprite);
		textTimer = 0;
		paintballiconSprite = new Sprite ("resources/sprites/config/paintballIcon.txt");
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(Gun);
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
		return Gun;
	}
	@Override 
	public void onSwitch () {
		itsOver = true;
	
	}
	@Override
	public void frameEvent () {
		if (!inzilaztion) {
			setY (Jeffrey.getActiveJeffrey().getY() + 16);
			inzilaztion = true;
		}
		
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		itsOver = false;
		if (this.cooldown > 0) {
			this.cooldown --;
		} else {
			this.getAnimationHandler().setAnimationFrame(0);
		}
		if (mouseButtonDown(0) && cooldown == 0 && !jeffrey.isCrouched()) {
			this.getAnimationHandler().setFrameTime(20);
			this.shoot (new BubbleProjectile(5 + 2 * Jeffrey.getActiveJeffrey().vx));
			this.getAnimationHandler().setAnimationFrame(1);
			cooldown = 4;
		}
		if (textTimer > 0) {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 20, outtaAmmo);
			textTimer--;
		}
	}
	
}
