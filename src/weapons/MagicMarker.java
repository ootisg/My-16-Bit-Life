package weapons;

import gui.Tbox;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Player;
import projectiles.MarkerPaint;
import resources.AfterRenderDrawer;
import resources.Sprite;

public class MagicMarker extends AimableWeapon {
	
	public static final Sprite RED_MARKER = new Sprite ("resources/sprites/config/marker_weapon_red.txt");
	public static final Sprite YELLOW_MARKER = new Sprite ("resources/sprites/config/marker_weapon_yellow.txt");
	public static final Sprite BLUE_MARKER = new Sprite ("resources/sprites/config/marker_weapon_blue.txt");
	public static final Sprite ORANGE_MARKER = new Sprite ("resources/sprites/config/marker_weapon_orange.txt");
	public static final Sprite GREEN_MARKER = new Sprite ("resources/sprites/config/marker_weapon_green.txt");
	public static final Sprite PURPLE_MARKER = new Sprite ("resources/sprites/config/marker_weapon_purple.txt");
	public static final Sprite MARKER = new Sprite ("resources/sprites/config/marker_weapon.txt");
	int color;
	private int cooldown;	
	private int [] upgradeInfo;
	boolean inzilaztion = false;
	public MagicMarker() {
		super(MARKER);
		this.cooldown = 0;
		upgradeInfo = new int [] {0,0,0,0};
		this.setSprite(MARKER);
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
		return MARKER;
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
	public void onFire () {
		if (cooldown == 0) {
			this.shoot (new MarkerPaint(color));
			this.getAnimationHandler().setAnimationFrame(1);
			cooldown = 5;
		}
	}
	
	@Override
	public void onSwitchModes () {
		color = color + 1;
		if (color > 6) {
			color = 1;
		}
		switch(color) { //Sets the sprite depending on the color. Color order is R-Y-B-O-G-P
			case 1:
				changeSprite (RED_MARKER);
				break;
			case 2:
				changeSprite (YELLOW_MARKER);
				break;
			case 3:
				changeSprite (BLUE_MARKER);
				break;
			case 4:
				changeSprite (ORANGE_MARKER);
				break;
			case 5:
				changeSprite (GREEN_MARKER);
				break;
			case 6:
				changeSprite (PURPLE_MARKER);
				break;
		}
	}
	
}
