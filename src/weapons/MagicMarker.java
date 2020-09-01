package weapons;

import gui.Tbox;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import projectiles.MarkerPaint;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class MagicMarker extends AimableWeapon {
	
	public static final Sprite outtaAmmo = new Sprite ("resources/sprites/Outta_Ammo.png");
	Sprite redMarker = new Sprite ("resources/sprites/config/marker_weapon_red.txt");
	Sprite yellowMarker = new Sprite ("resources/sprites/config/marker_weapon_yellow.txt");
	Sprite blueMarker = new Sprite ("resources/sprites/config/marker_weapon_blue.txt");
	Sprite orangeMarker = new Sprite ("resources/sprites/config/marker_weapon_orange.txt");
	Sprite greenMarker = new Sprite ("resources/sprites/config/marker_weapon_green.txt");
	Sprite purpleMarker = new Sprite ("resources/sprites/config/marker_weapon_purple.txt");
	Sprite Marker = new Sprite ("resources/sprites/config/marker_weapon.txt");
	int color;
	private Sprite paintballiconSprite;
	private int textTimer;
	private int cooldown;	
	boolean itsOver = false;
	boolean firstRun = true;
	Tbox box;
	private int [] upgradeInfo;
	Tbox ammoAmount;
	boolean inzilaztion = false;
	public MagicMarker(Sprite sprite) {
		super(sprite);
		ammoAmount = new Tbox (0,0,24,1, "0", false);
		ammoAmount.declare(252,10);
		ammoAmount.keepOpen(true);
		textTimer = 0;
		paintballiconSprite = new Sprite ("resources/sprites/config/paintballIcon.txt");
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(Marker);
	}
	@Override
	public String checkName () {
		return "MAGIC MARKER";
	}
	@Override
	public String checkEnetry() {
		return "THE BACK OF THE BOX DOESN'T LIE.";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"SQUID BUDDY", "SECONDARY COLORS", "I DONT KNOW", "IDK ITS SCENTED OR SOMETHIN"};
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
		return redMarker;
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
		if (mouseButtonPressed (2)) {
			color = color + 1;
			if (color > 6) {
				color = 1;
			}
			switch(color) { //Sets the sprite depending on the color. Color order is R-Y-B-O-G-P
			case 1:
				changeSprite (redMarker);
				break;
			case 2:
				changeSprite (yellowMarker);
				break;
			case 3:
				changeSprite (blueMarker);
				break;
			case 4:
				changeSprite (orangeMarker);
				break;
			case 5:
				changeSprite (greenMarker);
				break;
			case 6:
				changeSprite (purpleMarker);
				break;
		}
		}
		
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		ammoAmount.setContent("");
		itsOver = false;
		if (this.cooldown > 0) {
			this.cooldown --;
		} else {
			this.getAnimationHandler().setAnimationFrame(0);
		}
		if (mouseButtonClicked (0) && cooldown == 0 && !jeffrey.isCrouched() && !mouseButtonReleased (0)) {
			this.shoot (new MarkerPaint(color));
			this.getAnimationHandler().setAnimationFrame(1);
			cooldown = 5;
		}
		if (textTimer > 0) {
			AfterRenderDrawer.drawAfterRender((int)this.getX() - Room.getViewX(), (int)this.getY() - 20, outtaAmmo);
			textTimer--;
		}
	}
	
}
