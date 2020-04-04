package items;


import gui.ListTbox;
import gui.Tbox;
import gui.Textbox;
import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class Item extends GameObject {
	Boolean activeBox = false;
	ListTbox box;
	LemonPacket packet;
	public void Item () {
	}
	//override to set effect
	public void useItem(int witchCharictar) {
		Textbox box;
		box = new Textbox("YOU CANT USE THAT");
		box.changeUnpause();
		box.unfrezeMenu();
		box.declare(100,120);
	}
	public String checkName () {
		return "";	
	}
	/**
	 * run whenver weapons are switch overriden in wepon classes (if nessasary)
	 */
	public void onSwitch () {
		
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
	public void allwaysRunItemStuff (int witchCharitar) {
		if (GameCode.testJeffrey.checkIfSomeoneIsLemoney(witchCharitar) && !activeBox && this.getItemType().equals("Consumable") && !this.checkName().equals("LEMON PACKET")) {
			Tbox twobox2furios;
			activeBox = true;
			packet = new LemonPacket ();
			if (GameCode.testJeffrey.getInventory().checkConsumable(packet)) {
			twobox2furios = new Tbox (100, 80, 24, 8, "HE DOESEN'T WANT THAT HE WANTS LEMON PACKETS HE WILL ONLY EAT IT IF HE CAN HAVE LEMON PACKETS WITH IT", true);
			twobox2furios.setScrollRate(0);
			box = new ListTbox (290,400, new String []{"WHATEVER TAKE IT DUDE", "SCREW THAT"});
			} else {
				twobox2furios = new Tbox (100, 80, 24, 8, "HE DOESEN'T WANT THAT HE WANTS LEMON PACKETS HE WILL ONLY EAT IT IF HE CAN HAVE LEMON PACKETS WITH IT BUT YOU DONT HAVE ANY SO YOUR OUTTA LUCK", true);
				twobox2furios.setScrollRate(0);
				GameCode.gui.menu.frozen = false;
				activeBox = false;
			}
			twobox2furios.declare();
		} 
		try {
			if(box.getSelected() == 0) {
				this.useItem(witchCharitar);
				packet.useItem(witchCharitar);
				GameCode.testJeffrey.getInventory().removeItem(this);
				GameCode.testJeffrey.getInventory().removeItem(packet);
				box.close();
			}
			if (box.getSelected() == 1) {
				GameCode.gui.menu.frozen = false;
				activeBox = false;
				box.close();
			}
		} catch (NullPointerException e) {
			
		}
		if (!this.getItemType().equals("Consumable") && !activeBox) {
			this.useItem(witchCharitar);
			activeBox = true;
		}
		if ((!GameCode.testJeffrey.checkIfSomeoneIsLemoney(witchCharitar) || this.checkName().equals("LEMON PACKET")) && this.getItemType().equals("Consumable")) {
			this.useItem(witchCharitar);
			GameCode.testJeffrey.getInventory().removeItem(this);
		}
	}
 	//overriden in AimableWeapon
	public Sprite getUnrotatedSprite () {
		return this.getSprite();
	}
}
