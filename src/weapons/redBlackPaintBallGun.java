package weapons;

import gui.Tbox;
import items.RedBlackPaintBall;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.Paintball;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class redBlackPaintBallGun extends AimableWeapon {
	
	public static final Sprite outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
	public static final Sprite gunSprite = new Sprite ("resources/sprites/redblack_gun.png");
	private boolean trippleMode;
	private int textTimer;
	private int cooldown;
	Tbox box;
	public int [] upgradeInfo;
	public RedBlackPaintBall testball;
	public redBlackPaintBallGun(Sprite sprite) {
		super(sprite);
		trippleMode = false;
		textTimer = 0;
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,1,0};
		testball = new RedBlackPaintBall (1);
		this.setSprite(new Sprite ("resources/sprites/redblack_gun.png"));
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
	public void frameEvent () {
		if (upgradeInfo [2] >= 1 && mouseButtonClicked (2)) {
			if (!trippleMode) {
			trippleMode = true;
			box = new Tbox (this.getX(),this.getY(), 20, 2, "TRIPPLE MODE ENGAGED", false);
			} else {
			trippleMode = false;
			box = new Tbox (this.getX(),this.getY(), 20, 2, "TRIPPLE MODE DISENGAGED", false);
			}
			box.setScrollRate(0);
			box.configureTimerCloseing(30);
		}
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
		if (this.cooldown > 0) {
			this.cooldown --;
		}
		if (mouseButtonClicked (0) && cooldown == 0 ) {
			if ((jeffrey.inventory.checkAmmo(testball) && !trippleMode) || (jeffrey.inventory.checkItemAmount(testball) >= 3 && trippleMode)) {
			this.shoot (new Paintball ());
			jeffrey.inventory.removeItem(testball);
			if (this.trippleMode) {
			jeffrey.inventory.removeItem(testball);
			this.shoot(new Paintball(), this.rotation + (3.14/6));
			jeffrey.inventory.removeItem(testball);
			this.shoot(new Paintball(), this.rotation - (3.14/6));
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
