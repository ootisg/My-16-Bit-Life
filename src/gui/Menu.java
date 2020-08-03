package gui;


import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.AfterRenderDrawer;
import resources.Sprite;


public class Menu extends GameObject{
	int pageNumber;
	Sprite menuSprite;
	boolean iterateTab;
	boolean notChanged;
	boolean prepared = false;
	int charictarIndex;
	boolean mouseControls;
	Tbox nameBox;
	Tbox entryBox;
	Tbox weaponNameBox;
	int adjustingTime;
	Tbox weaponEntryBox;
	Tbox upgradeBox1;
	public boolean frozen;
	ListTbox charictarBox;
	Tbox upgradeBox2;
	Tbox upgradeBox3;
	Tbox upgradeBox4;
	boolean inBigPictureMode;
	Tbox ammoBox;
	Tbox ownerBox;
	Tbox itemName1;
	Tbox itemName2;
	Tbox itemName3;
	Tbox detailedEnemyName;
	Tbox detailedEnemyEntry;
	Tbox descriptionBox;
	int mouseAdjustingTime;
	Tbox itemNameBox;
	boolean consumabels;
	Tbox itemName4;
	int weaponIndex;
	int itemIndex1;
	int itemPosition;
	int itemIndex2;
	int itemIndex3;
	int itemIndex4;
	int itemIndex5;
	int itemIndex6;
	int itemIndex7;
	int itemIndex8;
	int enemyIndex1;
	int enemyIndex2;
	int enemyIndex3;
	int frame;
	int enemyIndex4;
	int enemyPosition;
	Tbox enemyName1;
	Tbox enemyName2;
	int enemyFrame;
	Tbox enemyName3;
	int oldMouseX;
	int oldMouseY;
	Tbox enemyName4;
	Tbox healthBox;
	Sprite questionMark;
	Sprite ball;
	public Menu () {
	questionMark = new Sprite ("resources/sprites/config/question_Mark.txt");
	charictarIndex = 0;
	weaponIndex = 0;
	itemPosition = -1;
	itemIndex1 = 0;
	consumabels = true;
	itemIndex2 = 1;
	itemIndex3 = 2;
	itemIndex4 = 3;
	mouseAdjustingTime = 2;
	enemyFrame = 29;
	adjustingTime = 0;
	itemIndex5 = 0;
	inBigPictureMode = false;
	itemIndex6 = 1;
	frozen = false;
	mouseControls = true;
	itemIndex7 = 2;
	frame = 17;
	itemIndex8 = 3;
	enemyIndex1 = 0;
	enemyIndex2 = 1;
	enemyIndex3 = 2;
	enemyIndex4 = 3;
	enemyPosition = -1;
	ball = new Sprite ("resources/sprites/redblack_ball.png");
	notChanged = true;
	iterateTab = true;
	pageNumber = 0;
	
	menuSprite = new Sprite ("resources/sprites/config/menu.txt");
	this.setSprite(menuSprite);
	}
	@Override
	public void pausedEvent () {
		if (!keyDown('E')) {
			prepared = true;
		}
		boolean abortCheck = false;
		// mouse controls for the tabs
		if (getCursorX() > 25 && getCursorX() < 481 && getCursorY() > 64 && getCursorY() < 116 && mouseButtonPressed (0)){
			this.cleanUp();
			if (getCursorX() > 25 && getCursorX() < 120) {
				pageNumber = 0;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
				nameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				nameBox.setScrollRate(0);
				entryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				entryBox.setScrollRate(0);
				nameBox.keepOpen(true);
				entryBox.keepOpen(true);
			this.setUpNewPlayableCharictar();
			}
			if (getCursorX() > 120 && getCursorX() < 207) {
				pageNumber = 1;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				weaponNameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName(),false);
				weaponNameBox.setScrollRate(0);
				weaponEntryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry(),false);
				weaponEntryBox.setScrollRate(0);
				upgradeBox1 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0],false);
				upgradeBox1.setScrollRate(0);
				upgradeBox2 = new Tbox(this.getX() + 315 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1],false);
				upgradeBox2.setScrollRate(0);
				upgradeBox3 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 335, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2],false);
				upgradeBox3.setScrollRate(0);
				upgradeBox4 = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 400, 10, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3],false);
				upgradeBox4.setScrollRate(0);
				this.addTierOrbs();
				ammoBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 275, 12, 2,"AMMO " + Integer.toString(Jeffrey.getInventory().checkAmmoAmountOfWeapon(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex))),false);
				ammoBox.setScrollRate(0);
				weaponNameBox.keepOpen(true);
				weaponEntryBox.keepOpen(true);
				upgradeBox1.keepOpen(true);
				upgradeBox2.keepOpen(true);
				upgradeBox3.keepOpen(true);
				upgradeBox4.keepOpen(true);
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
				ownerBox = new Tbox(this.getX() +  35 - Room.getViewX(), this.getY() + 335, 12, 2,"JEFFREY",false);
				}
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
					ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"SAM",false);
					}
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
					ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"RYAN",false);
					}
				ownerBox.setScrollRate(0);
				ammoBox.keepOpen(true);
				ownerBox.keepOpen(true);
				}
			if (getCursorX() > 207 && getCursorX() < 311) {
				pageNumber = 2;
				this.showItemList(consumabels);
				try {
				if (consumabels) {
				descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry(), false);
				itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName(), false);
				descriptionBox.keepOpen(true);
				itemNameBox.keepOpen(true);
				} else {
						descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry(), false);
					itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName(), false);
					descriptionBox.keepOpen(true);
					itemNameBox.keepOpen(true);
				}
				descriptionBox.setScrollRate(0);
				itemNameBox.setScrollRate(0);
				} catch (IndexOutOfBoundsException e) {
					descriptionBox = new Tbox ();
					itemNameBox = new Tbox ();
					descriptionBox.keepOpen(true);
					itemNameBox.keepOpen(true);
				}
			}
			if (getCursorX() > 311 && getCursorX() < 391) {
				pageNumber = 3;
				if (!this.inBigPictureMode) {
				this.showEnemyList();
				abortCheck = true;
				} else {
					this.inBigPictureMode = false;
				}
			}
			if (getCursorX() > 391 && getCursorX() < 495) {
				pageNumber = 4;
			}
		}
		//closes the menu if b is pressed
	if (keyDown('E') && prepared && !notChanged && !frozen) {
			this.cleanUp();
			ObjectHandler.pause(false);
			this.forget();
			
		}
		if (notChanged) {
			// sets up the textboxes insially
			nameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
			entryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
			nameBox.keepOpen(true);
			entryBox.keepOpen(true);
			//adds the charictar sprite into the after render draw arraylist
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 70 -Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
			this.setUpNewPlayableCharictar();
			//makes textboxes not scroll
			nameBox.setScrollRate(0);
			entryBox.setScrollRate(0);
			
			//finishes up menu inzilizations
			notChanged = false;
		}
		//moves to the previos tab if a is pressed and the player is on the tabs and they are not on the first tab
		if (keyPressed ('A') && pageNumber != 0 && iterateTab) {
			pageNumber = pageNumber - 1;
			//enemy tab stuff
			if (pageNumber == 3) {
				this.showEnemyList();
			}
			//item tab stuff
			if (pageNumber == 2) {
				// removes enemy tab stuff
				this.removeEnemyList();
				//adds item tab stuff
				this.showItemList(consumabels);
				try {
				if (consumabels) {
				descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry(), false);
				itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName(), false);
				descriptionBox.keepOpen(true);
				itemNameBox.keepOpen(true);
				} else {
						descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry(), false);
					itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName(), false);
					descriptionBox.keepOpen(true);
					itemNameBox.keepOpen(true);
				}
				descriptionBox.setScrollRate(0);
				itemNameBox.setScrollRate(0);
				} catch (IndexOutOfBoundsException e) {
					descriptionBox = new Tbox ();
					itemNameBox = new Tbox ();
					descriptionBox.keepOpen(true);
					itemNameBox.keepOpen(true);
				}
			}
			//weapon tab stuff 
			if (pageNumber == 1) {
				//removes item tab stuff
				this.removeItemList(consumabels);
				descriptionBox.forget();
				itemNameBox.forget();
				//adds weapon tab stuff
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				weaponNameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName(),false);
				weaponNameBox.setScrollRate(0);
				weaponEntryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry(),false);
				weaponEntryBox.setScrollRate(0);
				upgradeBox1 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0],false);
				upgradeBox1.setScrollRate(0);
				upgradeBox2 = new Tbox(this.getX() + 315 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1],false);
				upgradeBox2.setScrollRate(0);
				upgradeBox3 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 335, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2],false);
				upgradeBox3.setScrollRate(0);
				upgradeBox4 = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 400, 10, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3],false);
				upgradeBox4.setScrollRate(0);
				this.addTierOrbs();
				ammoBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 275, 12, 2,"AMMO " + Integer.toString(Jeffrey.getInventory().checkAmmoAmountOfWeapon(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex))),false);
				ammoBox.setScrollRate(0);
				weaponNameBox.keepOpen(true);
				weaponEntryBox.keepOpen(true);
				upgradeBox1.keepOpen(true);
				upgradeBox2.keepOpen(true);
				upgradeBox3.keepOpen(true);
				upgradeBox4.keepOpen(true);
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
				ownerBox = new Tbox(this.getX() +  35 - Room.getViewX(), this.getY() + 335, 12, 2,"JEFFREY",false);
				}
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
					ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"SAM",false);
					}
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
					ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"RYAN",false);
					}
				ownerBox.setScrollRate(0);
				ammoBox.keepOpen(true);
				ownerBox.keepOpen(true);
				}
			//charictar tab stuff
			if (pageNumber == 0) {
				// removes weapon tab stuff
				weaponNameBox.forget();
				weaponEntryBox.forget();
				upgradeBox1.forget();
				upgradeBox2.forget();
				upgradeBox3.forget();
				upgradeBox4.forget();
				ammoBox.forget();
				ownerBox.forget();
				this.removeTierOrbs();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185);
				//adds the charictar sprite to the arraylist for draw after render
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
				//sets the textbox equal to what its supposed to be
				nameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				nameBox.setScrollRate(0);
				entryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				entryBox.setScrollRate(0);
				nameBox.keepOpen(true);
				entryBox.keepOpen(true);
			this.setUpNewPlayableCharictar();
			}
		}
		//moves to the next tab if a is pressed and the player is on the tabs and they are not on the last tab
		if (keyPressed ('D') && pageNumber !=4 && iterateTab) {
			if (pageNumber == 0) {
				//removes old stuff from page 1
				nameBox.forget();
				healthBox.forget();
				entryBox.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185);
				this.removeWeaponsOfOldCharictar();
				//adds page 2 stuff
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				weaponNameBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 110, 42, 3,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName(),false);
				weaponNameBox.setScrollRate(0);
				weaponEntryBox = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 155, 42, 10,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry(),false);
				weaponEntryBox.setScrollRate(0);
				upgradeBox1 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0],false);
				upgradeBox1.setScrollRate(0);
				upgradeBox2 = new Tbox(this.getX() + 315 - Room.getViewX(), this.getY() + 275, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1],false);
				upgradeBox2.setScrollRate(0);
				upgradeBox3 = new Tbox(this.getX() + 145 - Room.getViewX(), this.getY() + 335, 20, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2],false);
				upgradeBox3.setScrollRate(0);
				upgradeBox4 = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 400, 12, 4,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3],false);
				upgradeBox4.setScrollRate(0);
				ammoBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 275, 12, 2,"AMMO " + Integer.toString(Jeffrey.getInventory().checkAmmoAmountOfWeapon(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex))),false);
				ammoBox.setScrollRate(0);
				weaponNameBox.keepOpen(true);
				weaponEntryBox.keepOpen(true);
				upgradeBox1.keepOpen(true);
				upgradeBox2.keepOpen(true);
				upgradeBox3.keepOpen(true);
				upgradeBox4.keepOpen(true);
				this.addTierOrbs();
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
					ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"JEFFREY",false);
					}
					if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
						ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"SAM",false);
						}
					if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
						ownerBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY() + 335, 12, 2,"RYAN",false);
						} 
					ownerBox.setScrollRate(0);
					ammoBox.keepOpen(true);
					ownerBox.keepOpen(true);
			}
			if (pageNumber == 1) {
				//removes page 2 stuff
				weaponNameBox.forget();
				weaponEntryBox.forget();
				upgradeBox1.forget();
				upgradeBox2.forget();
				upgradeBox3.forget();
				upgradeBox4.forget();
				ammoBox.forget();
				ownerBox.forget();
				this.removeTierOrbs();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185);
				//adds stuff for page 3
					this.showItemList(consumabels);
					try {
					if (consumabels) {
						descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry(), false);
						itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName(), false);
						descriptionBox.keepOpen(true);
						itemNameBox.keepOpen(true);	
					} else {
								descriptionBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 225, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry(), false);
								itemNameBox = new Tbox (this.getX() + 325 - Room.getViewX(), this.getY() + 153, 19, 8, Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName(), false);
								descriptionBox.keepOpen(true);
								itemNameBox.keepOpen(true);
					}
						descriptionBox.setScrollRate(0);
						itemNameBox.setScrollRate(0);
					} catch (IndexOutOfBoundsException e) {
						descriptionBox = new Tbox ();
						itemNameBox = new Tbox();
						descriptionBox.keepOpen(true);
						itemNameBox.keepOpen(true);
					}
			}
			if (pageNumber == 2) {
				//removes page 3 stuff
				descriptionBox.forget();
				itemNameBox.forget();
				this.removeItemList(consumabels);
				//adds page 4 stuff
				this.showEnemyList();
			}
			if (pageNumber == 3) {
				//removes page 4 stuff
				this.removeEnemyList();
			}
			pageNumber = pageNumber +1;
		}
		//draws stuff for page 1
		if(pageNumber == 0) {
		//makes the background the page 1 background
		this.getAnimationHandler().setAnimationFrame(1);
		//makes you go into the tab if you push s and are out of the tab
		if(iterateTab && keyPressed ('S')) {
			iterateTab = false;
		}
		//makes you go out of the tab if you push w and are in the tab
		if(!iterateTab && keyPressed ('W')) {
			iterateTab = true;
		}
		if (getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154){
			this.getAnimationHandler().setAnimationFrame(3);
		}
		if ( getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157){
			this.getAnimationHandler().setAnimationFrame(2);
		}
		//iterates to the next charictar if you are in the tab you push d and you are not on the last charictar
		if((!iterateTab && (keyPressed ('D'))|| (mouseButtonPressed(0) && getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154)) && charictarIndex != Jeffrey.getInventory().amountOfFreinds() -1) {
			//removes the sprite of the old charictar
			this.removeWeaponsOfOldCharictar();
			AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185);
			charictarIndex = charictarIndex + 1;
			//makes the sprties of the new charictar
			AfterRenderDrawer.drawAfterRender((int) this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
			//adds weapons and other stuff if its a special charictar
			healthBox.forget();
			this.setUpNewPlayableCharictar();
			
		}
		
		//iterates to the privios charictar if you are in the tab you push a and you are not on the first charictar
		if(((!iterateTab && keyPressed ('A'))|| (mouseButtonPressed(0) && getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157)) && charictarIndex != 0) {
			AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185);
			//removes stuff from the old charictar
			this.removeWeaponsOfOldCharictar();
			charictarIndex = charictarIndex - 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
			//adds weapons and health in if its a specail charictar
			healthBox.forget();
				this.setUpNewPlayableCharictar();
				
		}
		//sets the name and entry to the correct one
		nameBox.setContent(Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName());
		entryBox.setContent(Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkEntry());
		if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("JEFFREY")) {
			healthBox.setContent(Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(0)) + "/" + Integer.toString((int) Jeffrey.maxJeffreyHealth));
			}
			if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("SAM")) {
				healthBox.setContent(Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(1)) + "/" + Integer.toString((int) Jeffrey.maxSamHealth));
				}
			if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("RYAN")) {
				healthBox.setContent(Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(2)) + "/" + Integer.toString((int) Jeffrey.maxRyanHealth));
				}
		}
		//draws stuff for page 2
		if (pageNumber == 1) {
			//makes background the page 2 background
		this.getAnimationHandler().setAnimationFrame(6);
		//makes you go into the tab if you push s and are out of the tab
				if(iterateTab && keyPressed ('S')) {
					iterateTab = false;
				}
				//makes you go out of the tab if you push w and are in the tab
				if(!iterateTab && keyPressed ('W')) {
					iterateTab = true;
				}
				if (getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154){
					this.getAnimationHandler().setAnimationFrame(8);
				}
				if ( getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157){
					this.getAnimationHandler().setAnimationFrame(7);
				}
				if(((!iterateTab && keyPressed ('D')) || (mouseButtonPressed(0) && getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154)) && weaponIndex != Jeffrey.getInventory().amountOfWeaponsOfAllCharictars() -1) {
					//removes the sprite of the old weapon
					this.removeTierOrbs();
					AfterRenderDrawer.forceRemoveElement((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185);
					weaponIndex = weaponIndex + 1;
					//makes the sprties of the new weapon
					this.addTierOrbs();
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				}
				//iterates to the privios weapon if you are in the tab you push a and you are not on the first weapon
				if(((!iterateTab && keyPressed ('A')) || (mouseButtonPressed(0) && getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157)) && weaponIndex != 0) {
					this.removeTierOrbs();
					AfterRenderDrawer.forceRemoveElement((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185);
					weaponIndex = weaponIndex - 1;
					this.addTierOrbs();
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);		
				}
				weaponNameBox.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName());
				weaponEntryBox.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry());
				upgradeBox1.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0]);
				upgradeBox2.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1]);
				upgradeBox3.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2]);
				upgradeBox4.setContent(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3]);
				if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
					ownerBox.setContent("JEFFREY");
					}
					if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
						ownerBox.setContent("SAM");
						}
					if (Jeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
						ownerBox.setContent("RYAN");
						}
				ammoBox.setContent("AMMO " + Integer.toString(Jeffrey.getInventory().checkAmmoAmountOfWeapon(Jeffrey.getInventory().findWeaponAtIndex(weaponIndex))));
		}
		//draws stuff for page 3
		if(pageNumber == 2) {
			if (oldMouseX != getCursorX() || oldMouseY != getCursorY()) {
				mouseControls = true;
				oldMouseX =getCursorX();
				oldMouseY = getCursorY();
			} 
			//deals with item textboxes
			if (frozen && charictarBox.getSelected() != -1) {
				if (charictarBox.getSelected() != 3) {                                                                                                                                                                       
					this.removeItemList(true);
				switch (itemPosition) {
				case 0: 
				Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).allwaysRunItemStuff(charictarBox.getSelected());
				break;
				
				case 1: 
				Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).allwaysRunItemStuff(charictarBox.getSelected());
				break;
				
				case 2: 
			Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).allwaysRunItemStuff(charictarBox.getSelected());
			break;
			
				case 3: 
		Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).allwaysRunItemStuff(charictarBox.getSelected());
		break;
		}
				this.showItemList(true);
				} else {
					frozen = false;
				}
				charictarBox.close();
			}
			//sets the background to the page 3 background
			if ((adjustingTime > 2 && !mouseControls) ||(mouseAdjustingTime > 2 && mouseControls)) {
			this.getAnimationHandler().setAnimationFrame(frame);
			}
			if ((getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154 && consumabels) && !frozen  && !keyDown('S') && !keyDown('W')){
				this.getAnimationHandler().setAnimationFrame(18);
			}
			if ( !frozen && (getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157 && !consumabels) && !keyDown('S') && !keyDown('W')){
				this.getAnimationHandler().setAnimationFrame(24);
			}
			if ( mouseControls && !frozen&& (getCursorX() > 31 && getCursorX() < 228 && getCursorY() > 151 && getCursorY() < 447 )){
				try {
				if (getCursorX() > 31 && getCursorX() < 228 && getCursorY() > 153 && getCursorY() < 229){
					if (consumabels) {
					Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1);
					frame = 19;
					} else {
						Jeffrey.getInventory().getSortedKey().get(itemIndex5);
						frame = 25;
					}
				
					itemPosition = 0;
				}
				} catch (IndexOutOfBoundsException e) {
					
				}
				try {
					if ( getCursorX() > 31 && getCursorX() < 228 && getCursorY() > 229  && getCursorY() < 300 ) {
						if (consumabels) {
						Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2);
					frame = 20;
					} else {
						Jeffrey.getInventory().getSortedKey().get(itemIndex6);
						frame = 26;
					}
					
					itemPosition = 1;
					}
					} catch (IndexOutOfBoundsException e) {
						
					}
				try {
			if ( getCursorX() > 31 && getCursorX() < 228 && getCursorY() > 300  && getCursorY() < 474 ){
				if (consumabels) {
				Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3);
				frame = 21;
				} else {
					Jeffrey.getInventory().getSortedKey().get(itemIndex7);
					frame = 27;
				}
			
				itemPosition = 2;
			}
				} catch (IndexOutOfBoundsException e) {
					
				}
			try {
			if ( getCursorX() > 31 && getCursorX() < 228 && getCursorY() > 374  && getCursorY() < 447){
				if (consumabels) {
				Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4);
				frame = 22;
				} else {
					Jeffrey.getInventory().getSortedKey().get(itemIndex8);
					frame = 28;
				}
				
				itemPosition =3;
			}
			} catch (IndexOutOfBoundsException e) {
				
			}
			this.setItemEntryAndNameInfo();
			} 
			if (!mouseControls) {
				adjustingTime = adjustingTime + 1;
			} else {
				adjustingTime = 0;
			}
			if (mouseControls) {
				mouseAdjustingTime = mouseAdjustingTime + 1;
			} else {
				mouseAdjustingTime = 0;
			}
			//switches to key if d or a is pushed when you are in the menu
			boolean justChanged = false;
			if ( (!frozen && (((keyPressed ('D') && consumabels) || (!consumabels&&keyPressed('A'))) && !iterateTab) || (mouseButtonPressed(0) && getCursorX() > 375 && getCursorX() < 477 && getCursorY() > 113 && getCursorY() < 154 && consumabels) || (mouseButtonPressed(0) && getCursorX() > 34 && getCursorX() < 139 && getCursorY() > 116  && getCursorY() < 157 & !consumabels))) {
				justChanged = true;
				if (!consumabels) {
					if (!Jeffrey.getInventory().getSortedConsumablesAndAmmo().isEmpty()) {
						this.removeItemList(consumabels);
						consumabels = !consumabels;
						this.showItemList(consumabels);
						if (itemPosition == -1) {
							frame = 17;
						} else {
						frame = 19 + itemPosition;
						if (itemPosition > Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 - itemIndex5 ) {
							frame = 25;
							itemPosition = 0;
						}
						}
						this.setItemEntryAndNameInfo();
					}
				} else {
					if (!Jeffrey.getInventory().getSortedKey().isEmpty()) {
						this.removeItemList(consumabels);
						consumabels = !consumabels;
						this.showItemList(consumabels);
						if (itemPosition == -1) {
							frame = 23;
						} else {
						frame = 25 + itemPosition;
						if (itemPosition > Jeffrey.getInventory().getSortedKey().size() - 1 - itemIndex1 ) {
							itemPosition =0;
							frame = 19;
						}
						}
						this.setItemEntryAndNameInfo();
				}
				}
			}
			//deals with clicking on itmes (and using items with keyboard controls)
			if (!justChanged&& !frozen &&consumabels && ((keyPressed(32) || (mouseButtonPressed (0) && (getCursorX() > 34 && getCursorY() > 116  && getCursorX() < 477  && getCursorY() < 450))))) {
				charictarBox = new ListTbox (100, 180, new String [] {"JEFFREY","SAM","RYAN","BACK"});
				iterateTab = false;
				frozen = true;		
	}
			//moves the cursor down if s is pushed
			if( !frozen &&keyPressed ('S') && (!(Jeffrey.getInventory().getSortedConsumablesAndAmmo().isEmpty() && consumabels) || !(Jeffrey.getInventory().getSortedKey().isEmpty() && !consumabels))) {
				mouseControls = false;
				if (frame == 17) {
					if (consumabels) {
						frame = 18;
					} else {
						frame = 23;
					}
				}
				
				if ( (consumabels&& ((itemPosition != 3 && itemIndex4 <= Jeffrey.getInventory().getSortedConsumablesAndAmmo().size()-1) || (itemPosition != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 - itemIndex1 && itemIndex4 > Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1)))|| ( !consumabels&&((itemPosition != 3 && itemIndex8 <= Jeffrey.getInventory().getSortedKey().size()-1) || (itemPosition != Jeffrey.getInventory().getSortedKey().size() - 1 - itemIndex5 && itemIndex8 > Jeffrey.getInventory().getSortedKey().size() -1)))) {
					itemPosition = itemPosition + 1;
					frame = frame + 1;
					this.setItemEntryAndNameInfo();
				} else {
					if (consumabels) {
					if (itemIndex4 != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 && itemIndex3 != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1 && itemIndex2 != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1 && itemIndex1 != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1) {
					this.removeItemList(consumabels);
					itemIndex1 = itemIndex1 + 1;
					itemIndex2 = itemIndex2 + 1;
					itemIndex3 = itemIndex3 + 1;
					itemIndex4 = itemIndex4 + 1;
					this.showItemList(consumabels);
					descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkEnetry());
					itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName());
					}
					} else {
						if (itemIndex8 != Jeffrey.getInventory().getSortedKey().size() -1 && itemIndex7 != Jeffrey.getInventory().getSortedKey().size() - 1 && itemIndex6 != Jeffrey.getInventory().getSortedKey().size() - 1 && itemIndex5 != Jeffrey.getInventory().getSortedKey().size() - 1) {
							this.removeItemList(consumabels);
							itemIndex5 = itemIndex5 + 1;
							itemIndex6 = itemIndex6 + 1;
							itemIndex7 = itemIndex7 + 1;
							itemIndex8 = itemIndex8 + 1;
							this.showItemList(consumabels);
							descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex8).checkEnetry());
							itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex8).checkName());
							}	
					}
				}
			}
			if (itemPosition != -1) {
				iterateTab = false;
			}
			//makes you go out of the tab if you push w and are at the top of the tab
			if( !frozen &&!iterateTab && keyPressed ('W') && itemPosition == 0 && ((itemIndex1 == 0 && consumabels) || (itemIndex5 == 0 && !consumabels)) ) {
				if (consumabels) {
				frame = 17;
				} else {
				frame = 23;
				}
				iterateTab = true;
				itemPosition = itemPosition - 1;
				mouseControls = false;
			}
			//incriments things up by 1 if you press w
			if ((!frozen &&!iterateTab && (itemPosition != 0  || (itemIndex1 != 0 && consumabels || itemIndex5 !=0 && !consumabels)) && keyPressed ('W')) ) {
				mouseControls = false;
				if ( (consumabels&& ( (itemPosition != 0 && itemIndex4 <= Jeffrey.getInventory().getSortedConsumablesAndAmmo().size()-1) || (itemPosition != Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() - itemIndex1 && itemIndex4 > Jeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1)))|| ( !consumabels&&((itemPosition != 0 && itemIndex8 <= Jeffrey.getInventory().getSortedKey().size()-1) || (itemPosition != Jeffrey.getInventory().getSortedKey().size() - itemIndex5 && itemIndex8 > Jeffrey.getInventory().getSortedKey().size() -1)))) {
					itemPosition = itemPosition - 1;
					frame = frame - 1;
					this.setItemEntryAndNameInfo();
				} else {
					if (consumabels) {
					this.removeItemList(consumabels);
					itemIndex1 = itemIndex1 - 1;
					itemIndex2 = itemIndex2 - 1;
					itemIndex3 = itemIndex3 - 1;
					itemIndex4 = itemIndex4 - 1;
					this.showItemList(consumabels);
					descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
					itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
					} else {
						
							this.removeItemList(consumabels);
							itemIndex5 = itemIndex5 - 1;
							itemIndex6 = itemIndex6 - 1;
							itemIndex7 = itemIndex7 - 1;
							itemIndex8 = itemIndex8 - 1;
							
							this.showItemList(consumabels);
							descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry());
							itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName());
					}
				}
					
		}
		}
		//draws stuff for page 4
		
		if (pageNumber == 3) {
			if (oldMouseX != getCursorX() || oldMouseY != getCursorY()) {
				mouseControls = true;
				oldMouseX =getCursorX();
				oldMouseY = getCursorY();
			} 
			if (keyPressed (32) || mouseButtonPressed(0) && enemyPosition != -1) {
				if (!abortCheck) {
				if (inBigPictureMode) {
					this.showEnemyList();
					AfterRenderDrawer.forceRemoveElement((int) this.getX() + 250 - Room.getViewX(),  (int) this.getY() + 225);
					detailedEnemyName.forget();
					detailedEnemyEntry.forget();
				} else {
				
				this.removeEnemyList();
				this.getAnimationHandler().setAnimationFrame(35);
				if (enemyPosition == 0) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250 - Room.getViewX(), (int)this.getY() + 225, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175 - Room.getViewX(), this.getY() + 125, 40, 1, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337 - Room.getViewX(), this.getY() + 155, 18, 18, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkEntry(), false);
					
				}
				if (enemyPosition == 1) {
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 250 - Room.getViewX(), (int)this.getY() + 225, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2).getSprite(),0, true);
				detailedEnemyName = new Tbox (this.getX() + 175 - Room.getViewX(), this.getY() + 125, 40, 1, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkName(), false);
				detailedEnemyEntry = new Tbox (this.getX() + 337 - Room.getViewX(), this.getY() + 155, 18, 18, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkEntry(), false);
				}
				if (enemyPosition == 2) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250 - Room.getViewX(), (int)this.getY() + 225, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175 - Room.getViewX(), this.getY() + 125, 40, 1, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337 - Room.getViewX(), this.getY() + 155, 18, 18, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkEntry(), false);
				}
				if (enemyPosition == 3) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250 - Room.getViewX(), (int)this.getY() + 225, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175 - Room.getViewX(), this.getY() + 125, 40, 1, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337 - Room.getViewX(), this.getY() + 155, 18, 18, Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkEntry(), false);
				}
				detailedEnemyName.setScrollRate(0);
				detailedEnemyEntry.setScrollRate(0);
				detailedEnemyName.keepOpen(true);
				detailedEnemyEntry.keepOpen(true);
				}
				inBigPictureMode = !inBigPictureMode;
				}
			}
			//sets the background to the enemy background
			if (!inBigPictureMode && ((adjustingTime > 2 && !mouseControls) ||(mouseAdjustingTime > 2 && mouseControls))) {
		this.getAnimationHandler().setAnimationFrame(enemyFrame);
			}
				if ( mouseControls && !inBigPictureMode && (getCursorX() > 31 && getCursorX() < 481 && getCursorY() > 151 && getCursorY() < 447)){
						try {
					if (getCursorX() > 31 && getCursorX() < 481 && getCursorY() > 153 && getCursorY() < 229){
						Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1);
						enemyFrame = 30;					
						enemyPosition = 0;
					}
					} catch (IndexOutOfBoundsException e) {
						
					}
					try {
						if ( getCursorX() > 31 && getCursorX() < 481 && getCursorY() > 229  && getCursorY() < 300 ) {
							Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2);
						enemyFrame = 31;
						enemyPosition = 1;
						}
						} catch (IndexOutOfBoundsException e) {
							
						}
					try {
				if ( getCursorX() > 31 && getCursorX() < 481 && getCursorY() > 300  && getCursorY() < 374 ){
					Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3);
					enemyFrame = 32;
					enemyPosition = 2;
				}
					} catch (IndexOutOfBoundsException e) {
						
					}
				try {
				if ( getCursorX() > 31 && getCursorX() < 481 && getCursorY() > 374  && getCursorY() < 447){
					Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4);
					enemyFrame = 33;
					enemyPosition =3;
				}
				} catch (IndexOutOfBoundsException e) {
					
				}
				} 
				if (!mouseControls) {
					adjustingTime = adjustingTime + 1;
				} else {
					adjustingTime = 0;
				}
				if (mouseControls) {
					mouseAdjustingTime = mouseAdjustingTime + 1;
				} else {
					mouseAdjustingTime = 0;
				}
				if (enemyPosition != -1) {
					iterateTab = false;
				}
		//moves the cursor down if s is pushed
		if(keyPressed ('S') && !inBigPictureMode && Jeffrey.getInventory().amountOfKills() != 0) {
			iterateTab = false;
			mouseControls = false;
			if ((enemyPosition != 3 && enemyIndex4 <= Jeffrey.getInventory().amountOfKills()-1) || (enemyPosition != Jeffrey.getInventory().amountOfKills() -1 -enemyIndex1 && enemyIndex4 > Jeffrey.getInventory().amountOfKills() -1)) {
				enemyPosition = enemyPosition + 1;
				enemyFrame = enemyFrame + 1;
			} else {
				if (enemyIndex4 != Jeffrey.getInventory().amountOfKills() - 1 && enemyIndex3 != Jeffrey.getInventory().amountOfKills() -1 && enemyPosition != Jeffrey.getInventory().amountOfKills()  && enemyIndex1 != Jeffrey.getInventory().amountOfKills() -1) {
				this.removeEnemyList();
				enemyIndex1 = enemyIndex1 + 1;
				enemyIndex2 = enemyIndex2 + 1;
				enemyIndex3 = enemyIndex3 + 1;
				enemyIndex4 = enemyIndex4 + 1;
				this.showEnemyList();			
				}
			}
		}
		//makes you go out of the tab if you push w and are at the top of the tab
		if(!iterateTab && keyPressed ('W') && enemyPosition == 0 && enemyIndex1 == 0 && !inBigPictureMode) {
			mouseControls = false;
			iterateTab = true;
			enemyPosition = enemyPosition - 1;
			enemyFrame = enemyFrame -1;
		}
		//incriments things up by 1 if you press w
		if ( !inBigPictureMode && !iterateTab && (enemyPosition != 0  || enemyIndex1 != 0) && keyPressed ('W') ) {
			mouseControls = false;
			if ((enemyPosition != 0 && enemyIndex4 <= Jeffrey.getInventory().amountOfKills()-1) || (enemyPosition != Jeffrey.getInventory().amountOfKills() - enemyIndex1 && enemyIndex4 > Jeffrey.getInventory().amountOfKills() -1)) {
				enemyPosition = enemyPosition - 1;
				enemyFrame = enemyFrame -1;
			} else {
				this.removeEnemyList();
				enemyIndex1 = enemyIndex1 - 1;
				enemyIndex2 = enemyIndex2 - 1;
				enemyIndex3 = enemyIndex3 - 1;
				enemyIndex4 = enemyIndex4 - 1;
				this.showEnemyList();			
			}
	}
		}
		//draws stuff for page 5
		if(pageNumber == 4) {
			this.getAnimationHandler().setAnimationFrame(36);
		}
	}
	public void setItemEntryAndNameInfo () {
		if (consumabels) {
			if (itemPosition == 0) {
		descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
		itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
			}
			try {
			if (itemPosition == 1) {
				descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkEnetry());
				itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkName());
					}
			if (itemPosition == 2) {
				descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkEnetry());
				itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkName());
					}
			if (itemPosition == 3) {
				descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkEnetry());
				itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName());
					}
			} catch (IndexOutOfBoundsException e) {
				try {
				descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
				itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
				} catch (IndexOutOfBoundsException b) {
					
				}
			}
		} else {
			try {
			if (itemPosition == 0) {
				descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry());
				itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName());
					}
					if (itemPosition == 1) {
						descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex6).checkEnetry());
						itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex6).checkName());
							}
					if (itemPosition == 2) {
						descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex7).checkEnetry());
						itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex7).checkName());
							}
					if (itemPosition == 3) {
						descriptionBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex8).checkEnetry());
						itemNameBox.setContent(Jeffrey.getInventory().getSortedKey().get(itemIndex8).checkName());
							}
		} catch (IndexOutOfBoundsException e) {
			try {
			descriptionBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
			itemNameBox.setContent(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
			} catch (IndexOutOfBoundsException b) {
				
			}
		}
	}
	}
	public void removeItemList (boolean consumablesOrKey) {
		if (consumablesOrKey) {
		try {
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185);
			itemName1.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245);
			itemName2.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 315);
			itemName3.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400);
			itemName4.forget();
		} catch(IndexOutOfBoundsException e) {
			
		}
		} else {
			try {
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185);
				itemName1.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245);
				itemName2.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 315);
				itemName3.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400);
				itemName4.forget(); 
			} catch(IndexOutOfBoundsException e) {
				
			}	
		}
	}
	public void cleanUp () {
		switch (pageNumber) {
		case 0:
			nameBox.forget();
			healthBox.forget();
			entryBox.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 185);
			this.removeWeaponsOfOldCharictar();
			break;
		case 1:
			weaponNameBox.forget();
			weaponEntryBox.forget();
			upgradeBox1.forget();
			upgradeBox2.forget();
			upgradeBox3.forget();
			upgradeBox4.forget();
			ammoBox.forget();
			ownerBox.forget();
			this.removeTierOrbs();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 60 - Room.getViewX(), (int)this.getY() + 185);
			break;
		case 2:
			this.removeItemList(consumabels);
			descriptionBox.forget();
			itemNameBox.forget();
			break;
		case 3:
			if (!inBigPictureMode) {
			this.removeEnemyList();
			} else {
				AfterRenderDrawer.forceRemoveElement((int) this.getX() + 250 - Room.getViewX(),  (int) this.getY() + 225);
				detailedEnemyName.forget();
				detailedEnemyEntry.forget();
			}
			break;
		}
	}
	public void removeEnemyList () {
			try {
				try {
				AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185);
				enemyName1.forget();
				} catch (NullPointerException e) {
				}
				try {
				AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245);
				enemyName2.forget();
				} catch (NullPointerException e) {
					
				}
				try {
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 315);
				enemyName3.forget();
				} catch (NullPointerException e) {
				
				}
				try {
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400);
				enemyName4.forget();
				} catch (NullPointerException e) {
			
				}
			} catch(IndexOutOfBoundsException e) {
			}	
	}
	public void showEnemyList () {
		int beforeCrash = 0;
		try {
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1).getSprite(), 0, true);
			enemyName1 = new Tbox(this.getX() + 165 - Room.getViewX(), this.getY() + 155, 30, 4,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkName(),false);
			enemyName1.setScrollRate(0);
			beforeCrash = 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2).getSprite(), 0, true);
			enemyName2 = new Tbox(this.getX() + 165 - Room.getViewX(), this.getY() + 225, 30, 4,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkName(),false);
			enemyName2.setScrollRate(0);
			beforeCrash = 2;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() +  315,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3).getSprite(), 0, true);
			enemyName3 = new Tbox(this.getX() + 165 - Room.getViewX(), this.getY() + 295, 30, 4,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkName(),false);
			enemyName3.setScrollRate(0);
			beforeCrash = 3;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4).getSprite(), 0, true);
			enemyName4 = new Tbox(this.getX() + 165 - Room.getViewX(), this.getY() + 365, 30, 4,Jeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkName(),false);
			enemyName4.setScrollRate(0);
		} catch(IndexOutOfBoundsException e) {
			if (beforeCrash == 0) {
				enemyName1 = new Tbox();
				enemyName2 = new Tbox();
				enemyName3 = new Tbox ();
				enemyName4 = new Tbox ();
			}
			
			if (beforeCrash == 1) {
				enemyName2 = new Tbox();
				enemyName3 = new Tbox ();
				enemyName4 = new Tbox ();
			}
			if (beforeCrash == 2) {
				enemyName3 = new Tbox ();
				enemyName4 = new Tbox ();
			}
			if (beforeCrash == 3) {
				enemyName4 = new Tbox ();
			}
		}
		enemyName1.keepOpen(true);
		enemyName2.keepOpen(true);
		enemyName3.keepOpen(true);
		enemyName4.keepOpen(true);
	}
	//true for consumabels false for key
	public void showItemList (boolean consumablesOrKey) {
		int beforeCrash = 0;
		if (consumablesOrKey) {
		try {
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).getSprite(), 0, true);
			itemName1 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 155, 20, 4,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1))),false);
			itemName1.setScrollRate(0);
			beforeCrash = 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).getSprite(), 0, true);
			itemName2 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 225, 20, 4,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2))),false);
			itemName2.setScrollRate(0);
			beforeCrash = 2;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() +  315,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).getSprite(), 0, true);
			itemName3 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 295, 20, 4,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3))),false);
			itemName3.setScrollRate(0);
			beforeCrash = 3;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).getSprite(), 0, true);
			itemName4 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 370, 20, 4,Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4))),false);
			itemName4.setScrollRate(0);
		} catch(IndexOutOfBoundsException e) {
			if (beforeCrash == 0) {
				itemName1 = new Tbox();
				itemName2 = new Tbox();
				itemName3 = new Tbox ();
				itemName4 = new Tbox ();
			}
			if (beforeCrash == 1) {
				itemName2 = new Tbox();
				itemName3 = new Tbox ();
				itemName4 = new Tbox ();
			}
			if (beforeCrash == 2) {
				itemName3 = new Tbox ();
				itemName4 = new Tbox ();
			}
			if (beforeCrash == 3) {
				itemName4 = new Tbox ();
			}
		}
		} else {
			try {
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 185,Jeffrey.getInventory().getSortedKey().get(itemIndex5).getSprite(), 0, true);
				itemName1 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 155, 20, 4,Jeffrey.getInventory().getSortedKey().get(itemIndex5).checkName()+ " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedKey().get(itemIndex5))),false);
				itemName1.setScrollRate(0);
				beforeCrash =1;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 245,Jeffrey.getInventory().getSortedKey().get(itemIndex6).getSprite(), 0, true);
				itemName2 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 225, 20, 4,Jeffrey.getInventory().getSortedKey().get(itemIndex6).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedKey().get(itemIndex6))),false);
				itemName2.setScrollRate(0);
				beforeCrash = 2;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() +  315,Jeffrey.getInventory().getSortedKey().get(itemIndex7).getSprite(), 0, true);
				itemName3 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 295, 20, 4,Jeffrey.getInventory().getSortedKey().get(itemIndex7).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedKey().get(itemIndex7))),false);
				itemName3.setScrollRate(0);
				beforeCrash = 3;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55 - Room.getViewX(), (int)this.getY() + 400,Jeffrey.getInventory().getSortedKey().get(itemIndex8).getSprite(), 0, true);
				itemName4 = new Tbox(this.getX() + 105 - Room.getViewX(), this.getY() + 370, 20, 4,Jeffrey.getInventory().getSortedKey().get(itemIndex8).checkName() + " X " + Integer.toString(Jeffrey.getInventory().checkItemAmount(Jeffrey.getInventory().getSortedKey().get(itemIndex8))),false);
				itemName4.setScrollRate(0);
			} catch(IndexOutOfBoundsException e) {
				if (beforeCrash == 0) {
					itemName1 = new Tbox();
					itemName2 = new Tbox();
					itemName3 = new Tbox ();
					itemName4 = new Tbox ();
				}
				if (beforeCrash == 1) {
					itemName2 = new Tbox();
					itemName3 = new Tbox ();
					itemName4 = new Tbox ();
				}
				
				if (beforeCrash == 2) {
					itemName3 = new Tbox ();
					itemName4 = new Tbox ();
				}
				if (beforeCrash == 3) {
					itemName4 = new Tbox ();
				}
			}	
		}
		itemName1.keepOpen(true);
		itemName2.keepOpen(true);
		itemName3.keepOpen(true);
		itemName4.keepOpen(true);
	}
	public void addTierOrbs () {
		for (int i = 0; i < 4; i++) {
			for (int x = 0; x < Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getTierInfo() [i]; x ++ ) {
				if (i == 0) {
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 145 + (x*60) - Room.getViewX(), (int)this.getY() + 320, ball, 0, true);
				}
				if (i == 1) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 330 + (x*60) - Room.getViewX(), (int)this.getY() + 320, ball, 0, true);
					}
				if (i == 2) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 145 + (x*60) - Room.getViewX(), (int)this.getY() + 380, ball, 0, true);
					}
				if (i == 3) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 70 - Room.getViewX(), (int)this.getY() + 435, ball, 0, true);
					}
			}
		}
	}
	public void removeTierOrbs() {
		for (int i = 0; i <4; i++) {
			for (int x = 0; x < Jeffrey.getInventory().findWeaponAtIndex(weaponIndex).getTierInfo() [i]; x ++ ) {
				if (i == 0) {
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 145 + (x*60) - Room.getViewX(), (int)this.getY() + 320);
				}
				if (i == 1) {
					AfterRenderDrawer.forceRemoveElement((int)this.getX() + 330 + (x*60) - Room.getViewX(), (int)this.getY() + 320);
					}
				if (i == 2) {
					AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 145 + (x*60) - Room.getViewX(), (int)this.getY() + 380);
					}
				if (i == 3) {
					AfterRenderDrawer.forceRemoveElement((int)this.getX() +70 - Room.getViewX(), (int)this.getY() + 435);
					}
			}
		}
	}
	
	public void setUpNewPlayableCharictar () {
		if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("JEFFREY")) {
			//creates a new display of jeffreys health when it gets changed to the charitar page if jeffrey's page is the active page
		healthBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY()+ 280, 16,9,Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(0)) + "/" + Integer.toString((int) Jeffrey.maxJeffreyHealth), false);
		healthBox.setScrollRate(0);
		healthBox.keepOpen(true);
		//adds the weapons owned by jeffrey to the after render draw arraylist (adds a question mark for the ones he doesen't own) if jeffrey's page is the active page
		for (int i = 0; i <4; i++) {
			if (!Jeffrey.getInventory().findWeaponAtIndex(i, 0).getClass().getSimpleName().equals("Unarmed")) {
				AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60))- Room.getViewX() , (int)this.getY() + 320,Jeffrey.getInventory().findWeaponAtIndex(i, 0).getUnrotatedSprite(), 0, true);
			} else {
				AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60))- Room.getViewX() , (int)this.getY() + 320,questionMark, 0, true);	
			}
		}
		}
		
		if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("SAM")) {
			//creates a new display of sam's health when it gets changed to the charitar page is sam's page is the active page
			healthBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY()+ 280, 16,9,Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(1)) + "/" + Integer.toString((int) Jeffrey.maxSamHealth), false);
			healthBox.setScrollRate(0);
			healthBox.keepOpen(true);
			//adds the weapons owned by sam to the after render draw arraylist (adds a question mark for the ones he doesen't own) if sam's page is the active page
			for (int i = 0; i <4; i++) {
				if (!Jeffrey.getInventory().findWeaponAtIndex(i, 1).getClass().getSimpleName().equals("Unarmed")) {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) - Room.getViewX() , (int)this.getY() + 320,Jeffrey.getInventory().findWeaponAtIndex(i, 1).getUnrotatedSprite(), 0, true);
				} else {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) - Room.getViewX() , (int)this.getY() + 320,questionMark, 0, true);	
				}
			}
			}
		if (Jeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("RYAN")) {
			//creates a new display of Ryan's health when it gets changed to the charitar page is Ryan's page is the active page
			healthBox = new Tbox(this.getX() + 35 - Room.getViewX(), this.getY()+ 280, 16,9,Integer.toString((int)Jeffrey.getActiveJeffrey().getHealth(2)) + "/" + Integer.toString((int) Jeffrey.maxRyanHealth), false);
			healthBox.setScrollRate(0);
			healthBox.keepOpen(true);
			//adds the weapons owned by Ryan to the after render draw arraylist (adds a question mark for the ones he doesen't own) if sam's page is the active page
			for (int i = 0; i <4; i++) {
				if (!Jeffrey.getInventory().findWeaponAtIndex(i, 2).getClass().getSimpleName().equals("Unarmed")) {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) - Room.getViewX(), (int)this.getY() + 320,Jeffrey.getInventory().findWeaponAtIndex(i, 2).getUnrotatedSprite(), 0, true);
				} else {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60))- Room.getViewX() , (int)this.getY() + 320,questionMark, 0, true);	
				}
			}
		}
	}
	public void removeWeaponsOfOldCharictar () {
			for (int i = 0; i <4; i++) {
					AfterRenderDrawer.forceRemoveElement((int)(this.getX() + 180 + (i*60)) - Room.getViewX() , (int)this.getY() + 320);
			}	
	}
}
