package items;

import gui.Textbox;
import main.GameObject;
import resources.Sprite;

public class Item extends GameObject {
	public void Item () {
	}
	//override to set effect
	public void useItem() {
		Textbox box;
		box = new Textbox("YOU CANT USE THAT");
		box.changeUnpause();
		box.unfrezeMenu();
		box.declare(100,120);
	}
	public String checkName () {
		return "";	
	}
	//override to set entry
	public String checkEnetry() {
		return "";
	}
	//overriden in classes to clarify what type of item the item is
	public String getItemType() {
		return "";
	}
	//override in weapon classes
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"DEFULT", "DEFULT", "DEFULT", "DEFULT"};
		return returnArray;
	}
	//override in weapon classes
	public int [] getTierInfo () {
		int [] returnArray;
		returnArray = new int [] {0,0,0,0};
		return returnArray;
	}
 	//overriden in AimableWeapon
	public Sprite getUnrotatedSprite () {
		return this.getSprite();
	}
}
