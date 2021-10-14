package weapons;

import gui.Tbox;
import items.Item;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import projectiles.Fist;
import projectiles.Paintball;
import projectiles.PaintballWeak;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class redBlackPaintBallGun extends AimableWeapon {
	
	public static final Sprite GUN_SPRITE = new Sprite ("resources/sprites/jeffrey_walking_redblack_1.png");
	private boolean fists;
	private Sprite paintballiconSprite;
	private Sprite fisticonSprite;
	private int cooldown;
	boolean firstRun = true;
	//Tbox box;
	private int [] upgradeInfo;
	Tbox ammoAmount;
	public redBlackPaintBallGun() {
		super(GUN_SPRITE);
		fists = false;
		ammoAmount = new Tbox ();
		ammoAmount.setX(340);
		ammoAmount.setY(10);
		ammoAmount.setWidth(24);
		ammoAmount.setHeight(1);
		ammoAmount.keepOpen(true);
		ammoAmount.renderBox(false);
		ammoAmount.setPlace();
		paintballiconSprite = new Sprite ("resources/sprites/config/paintballIcon.txt");
		fisticonSprite = new Sprite ("resources/sprites/config/fistIcon.txt");
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,0,1};
	
	}
	@Override
	public String checkName () {
		return "REDBLACK PAINTBALL GUN";
	}
	@Override
	public String checkEnetry() {
		return "WHAT HAPPENS WHEN YOU MIX RED AND BLACK?  YOU GET REDBLACK DUH";
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
		return GUN_SPRITE;
	}

	@Override
	public void frameEvent () {
		
		if (this.cooldown > 0) {
			this.cooldown --;
		}
	}
	
	@Override
	public void onFire () {
		if ((canFire() && !fists) || (canFire() && canFireSecondary() && fists)) {
			if (!this.fists) {
				this.shoot (new Paintball ());
				this.fireAmmo(1);
			if (upgradeInfo [2] >= 1) {
				this.shoot(new PaintballWeak(), this.rotation + (3.14/32));
				this.shoot(new PaintballWeak(), this.rotation - (3.14/32));
			}
			} else {
				this.shoot(new Fist ());
				this.fireAmmo(1);
				this.fireSecondaryAmmo(1);
			}
			cooldown = 5;
		} 
	}
	
	@Override
	public void onSwitchModes () {
		
		if (upgradeInfo [3] >= 1) {
			if (!fists) {
			fists = true;
			//box = new Tbox (this.getX() - Room.getViewX() ,this.getY(), 20, 2, "SHOOT THOSE FISTS BRO", false);
			} else {
			fists = false;
			//box = new Tbox (this.getX() - Room.getViewX(),this.getY(), 20, 2, "STOP DEM FISTS", false);
			}
			
			//box.setScrollRate(0);
			//box.configureTimerCloseing(30);
		}
	}
	@Override 
	public void draw () {
		super.draw();
		if (this.getTierInfo()[3] == 1) {
			ammoAmount.setContent(Double.toString(this.getSecondaryAmmoCount()));
			ammoAmount.draw();
			if (fists) {
				fisticonSprite.draw(350, 0);
			} else {
				paintballiconSprite.draw(350, 0);
			}
		}
	}
}
