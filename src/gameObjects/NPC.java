package gameObjects;

import items.Item;
import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import gui.Textbox;

public class NPC extends GameObject{
	Textbox diolog;
	Boolean proximityTriggered;
	String [] messages;
	Boolean messageSeenOnce;
	Boolean checkToChange;
	int index;
	protected String [] itemMessages;
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
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	Boolean disapear;
	int amountOfItemMessages;
	Boolean setup;
	public NPC () {
		itemCheck = new Item ();
		setup = true;
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
		index = 1;
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
	public void giveItem (Item itemToGive, int amountToGive) {
		freeItem = itemToGive;
		amountOfFreeItem = amountToGive;
	}
	public String checkName (){
		return this.getVariantAttribute("Name");
	}
	public String checkEntry () {
		return this.getVariantAttribute("Entry");
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
	public void exchangeItem (Item itemToGive, int amountToGive) {
		itemToTake = itemToGive;
		amountToTake = amountToGive;
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
		if (index < amountOfNonDefultMessages) {
		messages[index] = newMessage;
		if (index + 1 < amountOfNonDefultMessages) {
		index = index + 1;
		}
		}
	}
	@Override
	public void frameEvent () {
		if (setup) {
			this.getAnimationHandler().setFrameTime(100);
			try {
				this.setHitboxAttributes(Integer.parseInt(this.getVariantAttribute("x_offset")), Integer.parseInt(this.getVariantAttribute("y_offset")), Integer.parseInt(this.getVariantAttribute("width")), Integer.parseInt(this.getVariantAttribute("height")));
			}catch (NumberFormatException e) {
				this.setHitboxAttributes(0, 0, 16, 16);
			}
			if (this.getVariantAttribute("sprite") != null) {
			this.setSprite(new Sprite (this.getVariantAttribute("sprite")));
			}else {
				this.setSprite(new Sprite ("resources/sprites/config/point_guy.txt"));
			}
				int i = 0;
				while (this.getVariantAttribute("message " +Integer.toString(i + 1)) != null) {
					messages[i] = this.getVariantAttribute("message " + Integer.toString(i+1));
					if (i > 1) {
						checkToChange = true;
					}
					i = i + 1;
				}
				amountOfNonDefultMessages = i;
				i = 0;
				while (this.getVariantAttribute("itemMessage " +Integer.toString(i + 1)) != null) {
					itemMessages[i] = this.getVariantAttribute("itemMessage " + Integer.toString(i+1));
					if (i > 1) {
						checkToChange = true;
					}
					i = i +1;
				}
				amountOfItemMessages = i;
				if (this.getVariantAttribute("itemNeeded") !=null) {
					try {
						  Object itemToUse = Class.forName(this.getVariantAttribute("itemNeeded")).newInstance();
						  int amountToCheck;
						  if(this.getVariantAttribute("amountToCheck") != null) {
							  amountToCheck = Integer.parseInt(this.getVariantAttribute("amountToCheck"));
						  } else {
							  amountToCheck = 1;
						  }
						  if (this.getVariantAttribute("action") != null) {
							  if (this.getVariantAttribute("action").equals("take")) {
								  this.takeItem((Item) itemToUse, amountToCheck);
							  } else {
								  if (this.getVariantAttribute("action").equals("check")) {
								 this.checkItem((Item) itemToUse, amountToCheck);
								  } else {
									  this.giveItem((Item) itemToUse, amountToCheck);
								  }
							  }
						  } else {
							  this.checkItem((Item) itemToUse, amountToCheck);
						  }
						if (this.getVariantAttribute("itemToExchange") != null) {
							  Object itemToExchange = Class.forName(this.getVariantAttribute("itemToExchange")).newInstance();
							  int amountToExchange;
							  if(this.getVariantAttribute("amountToExchange") != null) {
								  amountToExchange = Integer.parseInt(this.getVariantAttribute("amountToExchange"));
							  } else {
								  amountToExchange = 1;
							  }
							  this.exchangeItem((Item) itemToExchange, amountToExchange);
							  
						}
					} catch (ClassNotFoundException e) {
						System.out.println("the class of your item got a bit fooked up m8");
					} catch (InstantiationException e) {
						System.out.println("the class of your item got a bit fooked up m8");
					} catch (IllegalAccessException e) {
						System.out.println("the class of your item got a bit fooked up m8");
					}
				}
			setup = false;	
			}		
		if ((exchangeItem != 0) && itemFound) {
				this.giveItem(itemToTake, amountToTake);
				exchangeItem = 0;
		}
		
		try {
		if (diolog.isDone && diolog != null) {
			if (!Jeffrey.getInventory().checkFreinds(this)) {
				Jeffrey.getInventory().addFreind(this);
			}
			messageSeenOnce = true;
			diolog = null;
		}
		} catch (NullPointerException e) {
			
		}
		if (checkIfMessageSeen() && checkToChange) {
			if (!itemFound) {
			messages [0] = messages [index];
			this.changeMessageIfSeen(messages [index], amountOfNonDefultMessages);
			messageSeenOnce = false;
			} else {
			itemMessages [0] = itemMessages [index];
			this.changeMessageIfSeen(itemMessages [index], amountOfItemMessages);
			messageSeenOnce = false;
			}
		}
		if (checkForItem && Jeffrey.inventory.checkItemAmount(itemCheck) >= amountOfItemNeeded) {
			itemFound = true;
			amountOfNonDefultMessages = amountOfItemMessages;
			index = 1;
			checkForItem = false;
			if (keepItem) {
				for (int i =amountOfItemNeeded; i == 0; i = i - 1) {
				Jeffrey.inventory.removeItem(itemCheck);
				}
			}
		}
		if (this.isColliding (j) && (keyPressed (84) || proximityTriggered) && (diolog == null || diolog.isDone == true)) {
			if (!itemFound) {
			diolog = new Textbox (messages [0]);
			} else {
			diolog = new Textbox (itemMessages [0]);
			}
			diolog.chagePause();
			diolog.declare((int)this.getX(), (int)this.getY() - 140);
			if (giveItem != 0) {
				Jeffrey.getInventory().addItem(freeItem);
			}
			if (itemFound && disapear) {
				this.forget();
			}
			if (proximityTriggered && this.isColliding (j)) {
				j.vx = 0;
				if (this.getX()> j.getX()) {
					j.setX(j.getX() - 5);
				} else {
					j.setX(j.getX() + 5);
			}
			}
		}
	}
}