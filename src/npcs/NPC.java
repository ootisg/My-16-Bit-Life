package npcs;

import items.Item;
import main.GameCode;
import main.GameObject;
import gui.Textbox;

public class NPC extends GameObject{
	Textbox diolog;
	Boolean proximityTriggered;
	String [] messages;
	Boolean messageSeenOnce;
	Boolean checkToChange;
	int index;
	String [] itemMessages;
	Boolean checkForItem;
	Boolean itemFound;
	Item itemCheck;
	int amountOfItemNeeded;
	int giveItem;
	Item freeItem;
	int amountOfFreeItem;
	int amountOfNonDefultMessages;
	Boolean keepItem;
	int witchCharictar;
	Item itemToTake;
	int amountToTake;
	int exchangeItem;
	int charictarToRecive;
	Boolean disapear;
	int amountOfItemMessages;
	public NPC () {
		itemCheck = new Item ();
		amountOfItemMessages = 1;
		disapear = false;
		checkToChange = false;
		amountOfFreeItem = 1;
		amountToTake = 1;
		charictarToRecive = 0;
		exchangeItem = 0;
		keepItem = false;
		amountOfNonDefultMessages = 1;
		giveItem = 0;
		checkForItem = false;
		amountOfItemNeeded = 1;
		itemFound = false;
		messages = new String [5];
		itemMessages = new String [5];
		proximityTriggered = false;
		index = 0;
		messageSeenOnce = false;
		messages [0] = "TOTALLY NOT A DEFULT MESSAGE";
		messages [1]= "SERIOSLY IT ISENT A DEFULT MESSAGE";
		messages [2] = "IM NOT KIDDING ITS NOT A DEFULT MESSAGE";
		messages [3] = "STOP TELLING ME THIS IS A DEFULT MESSAGE ITS NOT ITS DIFFRENT UGG YOU DON'T KNOW ME  AT ALL MOM";
		messages [4] = "FINE MAYBE IT IS A DEFULT MESSAGE BUT FOR ALL YOU KNOW ITS NOT";
		itemMessages [0] = "YOU MAY HAVE GIVEN ME AN ITEM BUT YOU WILL NEVER GET MY DEFULT MESSAGE";
		itemMessages [1] = "THEY CAN TAKE OUR LIVES BUT THEY CAN NEVER TAKE OUR DEFULT MESSAGES";
		itemMessages [2] = "IF YOU THINK THIS IS A DUFULT MESSAGE THAN YOUR BRAIN ISEN'T WORKING CORRENCTLY";
		itemMessages [3] = "THIS IS NOT A DEFULT MESSAGE YOU ARE JUST OBVIOSLY TOO STUPID TO UNDERSTAND MY GENUS";
		itemMessages [4] = "NOT A DEFULT MESSAGE NO ... A DABFAULT MESSAGE";
	}
	public void setAmountOfItemMessages (int amount) {
		amountOfItemMessages = amount;
	}
	public void changeMessage (String newMessage) {
		messages [0] = newMessage;
	}
	// makes the NPC give Jeffrey an Item
	public void giveConsumableItem (Item itemToGive, int amountToGive) {
		freeItem = itemToGive;
		giveItem = 1;
		amountOfFreeItem = amountToGive;
	}
	public void giveKeyItem (Item itemToGive, int amountToGive) {
		freeItem = itemToGive;
		giveItem = 2;
		amountOfFreeItem = amountToGive;
	}
	public void giveAmmo (Item itemToGive, int amountToGive) {
		freeItem = itemToGive;
		giveItem = 3;
		amountOfFreeItem = amountToGive;
	}
	public void giveWeapon (Item itemToGive, int amountToGive, int charictarForWeapon) {
		freeItem = itemToGive;
		giveItem = 4;
		amountOfFreeItem = amountToGive;
		witchCharictar = charictarForWeapon;
	}
	//changes diolog to itemMessages if Item is in Jeffrey's inventory
	public void checkItem (Item itemToCheck, int amountNeeded) {
		itemCheck = itemToCheck;
		checkForItem = true;
		amountOfItemNeeded = amountNeeded;
	}
	// funtions similarly to checkItem but the NPC takes that Item from Jeffrey (typicly used with exchangeItem)
	public void takeItem (Item itemToTake, int amountToTake){
		itemCheck = itemToTake;
		checkForItem = true;
		amountOfItemNeeded = amountToTake;
		keepItem = true;
	}
	// makes the NPC disapear once Jeffrey talks to him with the Item (used with takeItem or checkItem)
	public void disapear () {
		disapear = true;
	}
	// used in conjustion with checkItem or takeItem makes the NPC give Jeffrey an Item after Jeffrey shows them (or gives them) his
	public void exchangeConsumableItem (Item itemToGive, int amountToGive) {
		itemToTake = itemToGive;
		amountToTake = amountToGive;
		exchangeItem = 1;
	}
	public void exchangeKeyItem (Item itemToGive, int amountToGive) {
		itemToTake = itemToGive;
		amountToTake = amountToGive;
		exchangeItem = 2;
	}
	public void exchangeAmmo (Item itemToGive, int amountToGive) {
		itemToTake = itemToGive;
		amountToTake = amountToGive;
		exchangeItem = 3;
	}
	public void exchangeWeapon (Item itemToGive, int amountToGive, int witchCharictar) {
		itemToTake = itemToGive;
		amountToTake = amountToGive;
		exchangeItem = 4;
		charictarToRecive = witchCharictar;
	}
	//changes it from being triggered by touching the NPC or triggered by pressing a key
	public void changeTriggering () {
		proximityTriggered = true;
	}
	public Boolean checkIfMessageSeen() {
		return messageSeenOnce;
	}
	public void changeMessageIfSeen (String newMessage, int amountOfMessages) {
		checkToChange = true;
		amountOfNonDefultMessages = amountOfMessages;
		index = index + 1;
		if (index > amountOfNonDefultMessages)
		messages[index] = newMessage;
	}
	@Override
	public void frameEvent () {
		if ((exchangeItem != 0) && itemFound) {
			if (exchangeItem == 1) {
				this.giveConsumableItem(itemToTake, amountToTake);
				exchangeItem = 0;
			}
			if (exchangeItem == 2) {
				this.giveKeyItem(itemToTake, amountToTake);
				exchangeItem = 0;
			}
			if (exchangeItem == 3) {
				this.giveAmmo(itemToTake, amountToTake);
				exchangeItem = 0;
			}
			if (exchangeItem == 1) {
				this.giveWeapon(itemToTake, amountToTake, charictarToRecive);
				exchangeItem = 0;
			}
		}
		try {
		if (diolog.isDone) {
			messageSeenOnce = true;
		}
		} catch (NullPointerException e) {
			
		}
		if (checkIfMessageSeen() && checkToChange) {
			if (!itemFound) {
			messages [0] = messages [index];
			this.changeMessageIfSeen(messages [index + 1], amountOfNonDefultMessages);
			messageSeenOnce = false;
			} else {
			itemMessages [0] = itemMessages [index];
			this.changeMessageIfSeen(itemMessages [index + 1], amountOfNonDefultMessages);
			messageSeenOnce = false;
			}
		}
		if (checkForItem && GameCode.testJeffrey.inventory.checkItemAmount(itemCheck) >= amountOfItemNeeded) {
			itemFound = true;
			amountOfNonDefultMessages = amountOfItemMessages;
			index = 0;
			checkForItem = false;
			if (keepItem) {
				for (int i =amountOfItemNeeded; i == 0; i = i - 1) {
				GameCode.testJeffrey.inventory.removeItem(itemCheck);
				}
			}
		}
		if (this.isColliding (GameCode.testJeffrey) && (keyPressed (84) || proximityTriggered) && (diolog == null || diolog.isDone == true)) {
			if (!itemFound) {
			diolog = new Textbox (messages [0]);
			} else {
			diolog = new Textbox (itemMessages [0]);
			}
			diolog.chagePause();
			diolog.declare((int)this.getX(), (int)this.getY() - 140);
			if (giveItem != 0) {
				if (giveItem == 1) {
				GameCode.testJeffrey.inventory.addConsumable(freeItem);
				}
				if (giveItem == 2) {
				GameCode.testJeffrey.inventory.addAmmo(freeItem);
				}
				if (giveItem == 3) {
				GameCode.testJeffrey.inventory.addKeyItem(freeItem);
				}
				if (giveItem == 4) {
				GameCode.testJeffrey.inventory.addWeapon(freeItem, witchCharictar);
				}
			}
			if (itemFound && disapear) {
				this.forget();
			}
			if (proximityTriggered && this.isColliding (GameCode.testJeffrey)) {
				GameCode.testJeffrey.vx = 0;
				if (this.getX()> GameCode.testJeffrey.getX()) {
					GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() - 5);
				} else {
					GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() + 5);
			}
			}
		}
	}
}