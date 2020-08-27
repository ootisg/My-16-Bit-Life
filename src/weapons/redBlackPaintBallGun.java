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
import projectiles.PaintballWeak;
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
		ammoAmount = new Tbox ();
		ammoAmount.setX(340);
		ammoAmount.setY(10);
		ammoAmount.setWidth(24);
		ammoAmount.setHeight(1);
		ammoAmount.keepOpen(true);
		ammoAmount.renderBox(false);
		textTimer = 0;
		paintballiconSprite = new Sprite ("resources/sprites/config/paintballIcon.txt");
		fisticonSprite = new Sprite ("resources/sprites/config/fistIcon.txt");
		paint = new BluePaint ();
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,1,1};
		testball = new RedBlackPaintBall ();
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
		if (upgradeInfo [3] >= 1 && mouseButtonPressed (2)) {
			if (!fists) {
			fists = true;
			box = new Tbox (this.getX() - Room.getViewX() ,this.getY(), 20, 2, "SHOOT THOSE FISTS BRO", false);
			} else {
			fists = false;
			box = new Tbox (this.getX() - Room.getViewX(),this.getY(), 20, 2, "STOP DEM FISTS", false);
			}
			
			box.setScrollRate(0);
			box.configureTimerCloseing(30);
		}
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (this.cooldown > 0) {
			this.cooldown --;
		}
		if (mouseButtonClicked (0) && cooldown == 0 && !jeffrey.isCrouched() && !mouseButtonReleased (0)) {
			if ((Jeffrey.getInventory().checkAmmo(testball) && !fists) || Jeffrey.getInventory().checkAmmo(testball)&& (Jeffrey.getInventory().checkAmmo(paint) && fists)) {
			if (!this.fists) {
			this.shoot (new Paintball ());
			Jeffrey.getInventory().removeItem(testball);
			if (upgradeInfo [2] >= 1) {
			this.shoot(new PaintballWeak(), this.rotation + (3.14/32));
			this.shoot(new PaintballWeak(), this.rotation - (3.14/32));
			}
			} else {
			this.shoot(new Fist ());
			Jeffrey.getInventory().removeItem(paint);
			Jeffrey.getInventory().removeItem(testball);
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
	@Override 
	public void draw () {
		super.draw();
		if (this.getTierInfo()[3] == 1) {
			ammoAmount.setContent(Integer.toString(Jeffrey.getInventory().checkItemAmount(paint)));
			ammoAmount.draw();
			if (fists) {
				fisticonSprite.draw(350, 0);
			} else {
				paintballiconSprite.draw(350, 0);
			}
		}
	}
}
