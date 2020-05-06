package weapons;

import gui.Tbox;
import items.BluePaint;
import items.RedBlackPaintBall;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.Fist;
import projectiles.Paintball;
import projectiles.Paintball_Weak;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class redBlackPaintBallGun extends AimableWeapon {
	
	public static final Sprite outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
	public static final Sprite gunSprite = new Sprite ("resources/sprites/redblack_gun.png");
	private boolean fists;
	private Sprite paintballiconSprite;
	private Sprite fisticonSprite;
	private int textTimer;
	private int cooldown;
	boolean itsOver = false;
	boolean firstRun = true;
	Tbox box;
	private int [] upgradeInfo;
	BluePaint paint;
	private RedBlackPaintBall testball;
	Tbox ammoAmount;
	public redBlackPaintBallGun(Sprite sprite) {
		super(sprite);
		fists = false;
		ammoAmount = new Tbox (0,0,24,1, "0", false);
		ammoAmount.declare(252,10);
		ammoAmount.keepOpen(true);
		textTimer = 0;
		paintballiconSprite = new Sprite ("resources/sprites/config/paintballIcon.txt");
		fisticonSprite = new Sprite ("resources/sprites/config/fistIcon.txt");
		paint = new BluePaint (1);
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,1,1};
		testball = new RedBlackPaintBall (1);
		this.setSprite(gunSprite);
	}
	@Override
	public String checkName () {
		return "REDBLACK PAINTBALL GUN";
	}
	@Override
	public String checkEnetry() {
		return "WHAT HAPPENS WHEN YOU MIX RED AND BLACK?";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"CHARGE SHOT", "CHEMICAL PAINTS", "TRIPPLE SHOTS", "REDBLACK AND BLUE PAINTBALL GUN"};
			return returnArray;
		}
	@Override 
	public String getItemType() {
		return "WeaponJeffrey";
	}
	@Override
		public int [] getTierInfo () {
			return upgradeInfo;
		}
	@Override 
	public Sprite getUnrotatedSprite () {
		return gunSprite;
	}
	@Override 
	public void onSwitch () {
		itsOver = true;
	
	}
	@Override
	public void frameEvent () {
		if (firstRun) {
			AfterRenderDrawer.drawAfterRender(260, 0, paintballiconSprite, 0, true);
			firstRun = false;
		}
		if (upgradeInfo [3] >= 1 && mouseButtonPressed (2)) {
			if (!fists) {
			fists = true;
			AfterRenderDrawer.removeElement(paintballiconSprite, 260, 0);
			AfterRenderDrawer.drawAfterRender(260, 0, fisticonSprite, 0, true);
			box = new Tbox (this.getX() - Room.getViewX() ,this.getY(), 20, 2, "SHOOT THOSE FISTS BRO", false);
			} else {
			fists = false;
			AfterRenderDrawer.removeElement(fisticonSprite, 260, 0);
			AfterRenderDrawer.drawAfterRender(260, 0, paintballiconSprite, 0, true);
			box = new Tbox (this.getX() - Room.getViewX(),this.getY(), 20, 2, "STOP DEM FISTS", false);
			}
			
			box.setScrollRate(0);
			box.configureTimerCloseing(30);
		}
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
		if (!itsOver) {
		ammoAmount.setContent(Integer.toString(jeffrey.getInventory().checkItemAmount(paint)));
		} else {
		AfterRenderDrawer.removeElement(paintballiconSprite, 260, 0);
		AfterRenderDrawer.removeElement(fisticonSprite, 260, 0);
		ammoAmount.setContent("");
		itsOver = false;
		}
		if (this.cooldown > 0) {
			this.cooldown --;
		}
		if (mouseButtonClicked (0) && cooldown == 0 ) {
			if ((jeffrey.inventory.checkAmmo(testball) && !fists) || jeffrey.inventory.checkAmmo(testball)&& (jeffrey.inventory.checkAmmo(paint) && fists)) {
			if (!this.fists) {
			this.shoot (new Paintball ());
			jeffrey.inventory.removeItem(testball);
			if (upgradeInfo [2] >= 1) {
			this.shoot(new Paintball_Weak(), this.rotation + (3.14/32));
			this.shoot(new Paintball_Weak(), this.rotation - (3.14/32));
			}
			} else {
			this.shoot(new Fist ());
			jeffrey.inventory.removeItem(paint);
			jeffrey.inventory.removeItem(testball);
			}
			cooldown = 5;
			} else {
				textTimer = 10;
				AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 20, outtaAmmo);
			}
		}
		if (textTimer > 0) {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 20, outtaAmmo);
			textTimer = textTimer - 1;
		}
	}
	
}
