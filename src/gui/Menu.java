package gui;


import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import resources.AfterRenderDrawer;
import resources.Sprite;


public class Menu extends GameObject{
	int pageNumber;
	Sprite menuSprite;
	boolean iterateTab;
	boolean notChanged;
	int charictarIndex;
	Tbox nameBox;
	Tbox entryBox;
	Tbox weaponNameBox;
	Tbox weaponEntryBox;
	Tbox upgradeBox1;
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
	int enemyIndex4;
	int enemyPosition;
	Tbox enemyName1;
	Tbox enemyName2;
	Tbox enemyName3;
	Tbox enemyName4;
	Tbox healthBox;
	Sprite questionMark;
	Sprite ball;
	public Menu () {
	questionMark = new Sprite ("resources/sprites/config/question_Mark.txt");
	charictarIndex = 0;
	weaponIndex = 0;
	itemPosition = 0;
	itemIndex1 = 0;
	consumabels = true;
	itemIndex2 = 1;
	itemIndex3 = 2;
	itemIndex4 = 3;
	itemIndex5 = 0;
	inBigPictureMode = false;
	itemIndex6 = 1;
	itemIndex7 = 2;
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
		//closes the menu if e is pressed
		if (keyPressed('E') ) {
			switch (pageNumber) {
			case 0:
				nameBox.forget();
				healthBox.forget();
				entryBox.forget();
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), (int)this.getX() + 70, (int)this.getY() + 185);
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
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), (int)this.getX() + 60, (int)this.getY() + 185);
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
					AfterRenderDrawer.forceRemoveElement((int) this.getX() + 250,  (int) this.getY() + 225);
					detailedEnemyName.forget();
					detailedEnemyEntry.forget();
				}
				break;
			}
			ObjectHandler.pause(false);
			this.forget();
		}
		if (notChanged) {
			// sets up the textboxes insially
			nameBox = new Tbox(this.getX() + 145, this.getY() + 110, 42, 3,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
			entryBox = new Tbox(this.getX() + 145, this.getY() + 155, 42, 10,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
			//adds the charictar sprite into the after render draw arraylist
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 70, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
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
				if (consumabels) {
				descriptionBox = new Tbox (this.getX() + 325, this.getY() + 225, 19, 8, GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry(), false);
				itemNameBox = new Tbox (this.getX() + 325, this.getY() + 153, 19, 8, GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName(), false);
				} else {
						descriptionBox = new Tbox (this.getX() + 325, this.getY() + 225, 19, 8, GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry(), false);
						itemNameBox = new Tbox (this.getX() + 325, this.getY() + 153, 19, 8, GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkName(), false);
				}
				descriptionBox.setScrollRate(0);
				itemNameBox.setScrollRate(0);
			}
			//weapon tab stuff 
			if (pageNumber == 1) {
				//removes item tab stuff
				this.removeItemList(consumabels);
				descriptionBox.forget();
				itemNameBox.forget();
				//adds weapon tab stuff
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 60, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				weaponNameBox = new Tbox(this.getX() + 145, this.getY() + 110, 42, 3,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName(),false);
				weaponNameBox.setScrollRate(0);
				weaponEntryBox = new Tbox(this.getX() + 145, this.getY() + 155, 42, 10,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry(),false);
				weaponEntryBox.setScrollRate(0);
				upgradeBox1 = new Tbox(this.getX() + 145, this.getY() + 275, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0],false);
				upgradeBox1.setScrollRate(0);
				upgradeBox2 = new Tbox(this.getX() + 315, this.getY() + 275, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1],false);
				upgradeBox2.setScrollRate(0);
				upgradeBox3 = new Tbox(this.getX() + 145, this.getY() + 335, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2],false);
				upgradeBox3.setScrollRate(0);
				upgradeBox4 = new Tbox(this.getX() + 35, this.getY() + 400, 10, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3],false);
				upgradeBox4.setScrollRate(0);
				this.addTierOrbs();
				ammoBox = new Tbox(this.getX() + 35, this.getY() + 275, 12, 2,"AMMO " + Integer.toString(GameCode.testJeffrey.getInventory().checkAmmoAmountOfWeapon(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex))),false);
				ammoBox.setScrollRate(0);
				if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
				ownerBox = new Tbox(this.getX() +  35, this.getY() + 335, 12, 2,"JEFFREY",false);
				}
				if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
					ownerBox = new Tbox(this.getX() + 35, this.getY() + 335, 12, 2,"SAM",false);
					}
				if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
					ownerBox = new Tbox(this.getX() + 35, this.getY() + 335, 12, 2,"RYAN",false);
					}
				ownerBox.setScrollRate(0);
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
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), (int)this.getX() + 60, (int)this.getY() + 185);
				//adds the charictar sprite to the arraylist for draw after render
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 70, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
				//sets the textbox equal to what its supposed to be
				nameBox = new Tbox(this.getX() + 145, this.getY() + 110, 42, 3,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				nameBox.setScrollRate(0);
				entryBox = new Tbox(this.getX() + 145, this.getY() + 155, 42, 10,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName(),false);
				entryBox.setScrollRate(0);
				
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
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), (int)this.getX() + 70, (int)this.getY() + 185);
				this.removeWeaponsOfOldCharictar();
				//adds page 2 stuff
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 60, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				weaponNameBox = new Tbox(this.getX() + 145, this.getY() + 110, 42, 3,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName(),false);
				weaponNameBox.setScrollRate(0);
				weaponEntryBox = new Tbox(this.getX() + 145, this.getY() + 155, 42, 10,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry(),false);
				weaponEntryBox.setScrollRate(0);
				upgradeBox1 = new Tbox(this.getX() + 145, this.getY() + 275, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0],false);
				upgradeBox1.setScrollRate(0);
				upgradeBox2 = new Tbox(this.getX() + 315, this.getY() + 275, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1],false);
				upgradeBox2.setScrollRate(0);
				upgradeBox3 = new Tbox(this.getX() + 145, this.getY() + 335, 20, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2],false);
				upgradeBox3.setScrollRate(0);
				upgradeBox4 = new Tbox(this.getX() + 35, this.getY() + 400, 12, 4,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3],false);
				upgradeBox4.setScrollRate(0);
				ammoBox = new Tbox(this.getX() + 35, this.getY() + 275, 12, 2,"AMMO " + Integer.toString(GameCode.testJeffrey.getInventory().checkAmmoAmountOfWeapon(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex))),false);
				ammoBox.setScrollRate(0);
				this.addTierOrbs();
				if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
					ownerBox = new Tbox(this.getX() + 35, this.getY() + 335, 12, 2,"JEFFREY",false);
					}
					if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
						ownerBox = new Tbox(this.getX() + 35, this.getY() + 335, 12, 2,"SAM",false);
						}
					if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
						ownerBox = new Tbox(this.getX() + 35, this.getY() + 335, 12, 2,"RYAN",false);
						}
					ownerBox.setScrollRate(0);
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
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), (int)this.getX() + 60, (int)this.getY() + 185);
				//adds stuff for page 3
					this.showItemList(consumabels);
					if (consumabels) {
						descriptionBox = new Tbox (this.getX() + 325, this.getY() + 225, 19, 8, GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry(), false);
						itemNameBox = new Tbox (this.getX() + 325, this.getY() + 153, 19, 8, GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName(), false);
						} else {
								descriptionBox = new Tbox (this.getX() + 325, this.getY() + 225, 19, 8, GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry(), false);
								itemNameBox = new Tbox (this.getX() + 325, this.getY() + 153, 19, 8, GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkName(), false);
						}
						descriptionBox.setScrollRate(0);
						itemNameBox.setScrollRate(0);
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
		//iterates to the next charictar if you are in the tab you push d and you are not on the last charictar
		if(!iterateTab && keyPressed ('D') && charictarIndex != GameCode.testJeffrey.getInventory().amountOfFreinds() -1) {
			//removes the sprite of the old charictar
			this.removeWeaponsOfOldCharictar();
			AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), (int)this.getX() + 70, (int)this.getY() + 185);
			charictarIndex = charictarIndex + 1;
			//makes the sprties of the new charictar
			AfterRenderDrawer.drawAfterRender((int) this.getX() + 70, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
			//adds weapons and other stuff if its a special charictar
			healthBox.forget();
			this.setUpNewPlayableCharictar();
			
		}
		//iterates to the privios charictar if you are in the tab you push a and you are not on the first charictar
		if(!iterateTab && keyPressed ('A') && charictarIndex != 0) {
			AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), (int)this.getX() + 70, (int)this.getY() + 185);
			//removes stuff from the old charictar
			this.removeWeaponsOfOldCharictar();
			charictarIndex = charictarIndex - 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 70, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).getSprite(), 0, true);
			//adds weapons and health in if its a specail charictar
			healthBox.forget();
				this.setUpNewPlayableCharictar();
				
		}
		//sets the name and entry to the correct one
		nameBox.setContent(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName());
		entryBox.setContent(GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkEntry());
		if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("JEFFREY")) {
			healthBox.setContent(Integer.toString((int)GameCode.testJeffrey.getHealth(0)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth));
			}
			if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("SAM")) {
				healthBox.setContent(Integer.toString((int)GameCode.testJeffrey.getHealth(1)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth));
				}
			if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("RYAN")) {
				healthBox.setContent(Integer.toString((int)GameCode.testJeffrey.getHealth(2)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth));
				}
		}
		//draws stuff for page 2
		if (pageNumber == 1) {
			//makes background the page 2 background
		this.getAnimationHandler().setAnimationFrame(2);
		//makes you go into the tab if you push s and are out of the tab
				if(iterateTab && keyPressed ('S')) {
					iterateTab = false;
				}
				//makes you go out of the tab if you push w and are in the tab
				if(!iterateTab && keyPressed ('W')) {
					iterateTab = true;
				}
				if(!iterateTab && keyPressed ('D') && weaponIndex != GameCode.testJeffrey.getInventory().amountOfWeaponsOfAllCharictars() -1) {
					//removes the sprite of the old weapon
					this.removeTierOrbs();
					AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), (int)this.getX() + 60, (int)this.getY() + 185);
					weaponIndex = weaponIndex + 1;
					//makes the sprties of the new weapon
					this.addTierOrbs();
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 60, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);
				}
				//iterates to the privios weapon if you are in the tab you push a and you are not on the first weapon
				if(!iterateTab && keyPressed ('A') && weaponIndex != 0) {
					this.removeTierOrbs();
					AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), (int)this.getX() + 60, (int)this.getY() + 185);
					weaponIndex = weaponIndex - 1;
					this.addTierOrbs();
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 60, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUnrotatedSprite(), 0, true);		
				}
				weaponNameBox.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkName());
				weaponEntryBox.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).checkEnetry());
				upgradeBox1.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [0]);
				upgradeBox2.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [1]);
				upgradeBox3.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [2]);
				upgradeBox4.setContent(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getUpgrades() [3]);
				if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 0){
					ownerBox.setContent("JEFFREY");
					}
					if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 1){
						ownerBox.setContent("SAM");
						}
					if (GameCode.testJeffrey.getInventory().getWeaponIndex(weaponIndex)[1] == 2){
						ownerBox.setContent("RYAN");
						}
				ammoBox.setContent("AMMO " + Integer.toString(GameCode.testJeffrey.getInventory().checkAmmoAmountOfWeapon(GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex))));
		}
		//draws stuff for page 3
		if(pageNumber == 2) {
			//sets the background to the page 3 background
			this.getAnimationHandler().setAnimationFrame(4);
			//switches to key if d or a is pushed when you are in the menu
			if ((keyPressed ('D') || keyPressed('A')) && !iterateTab) {
				this.removeItemList(consumabels);
				consumabels = !consumabels;
				this.showItemList(consumabels);
				if (consumabels) {
					if (itemPosition > GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 - itemIndex1 ) {
						itemPosition =0;
					}
				} else {
					if (itemPosition > GameCode.testJeffrey.getInventory().getSortedKey().size() - 1 - itemIndex5 ) {
						itemPosition = 0;
					}
				}
				this.setItemEntryAndNameInfo();
			}
			//moves the cursor down if s is pushed
			if(keyPressed ('S')) {
				iterateTab = false;
				if ( (consumabels&& ((itemPosition != 3 && itemIndex4 <= GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size()-1) || (itemPosition != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 - itemIndex1 && itemIndex4 > GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1)))|| ( !consumabels&&((itemPosition != 3 && itemIndex8 <= GameCode.testJeffrey.getInventory().getSortedKey().size()-1) || (itemPosition != GameCode.testJeffrey.getInventory().getSortedKey().size() - 1 - itemIndex5 && itemIndex8 > GameCode.testJeffrey.getInventory().getSortedKey().size() -1)))) {
					itemPosition = itemPosition + 1;
					this.setItemEntryAndNameInfo();
				} else {
					if (consumabels) {
					if (itemIndex4 != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 && itemIndex3 != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1 && itemIndex2 != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1 && itemIndex1 != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1) {
					this.removeItemList(consumabels);
					itemIndex1 = itemIndex1 + 1;
					itemIndex2 = itemIndex2 + 1;
					itemIndex3 = itemIndex3 + 1;
					itemIndex4 = itemIndex4 + 1;
					this.showItemList(consumabels);
					descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkEnetry());
					itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName());
					}
					} else {
						if (itemIndex8 != GameCode.testJeffrey.getInventory().getSortedKey().size() -1 && itemIndex7 != GameCode.testJeffrey.getInventory().getSortedKey().size() - 1 && itemIndex6 != GameCode.testJeffrey.getInventory().getSortedKey().size() - 1 && itemIndex5 != GameCode.testJeffrey.getInventory().getSortedKey().size() - 1) {
							this.removeItemList(consumabels);
							itemIndex5 = itemIndex5 + 1;
							itemIndex6 = itemIndex6 + 1;
							itemIndex7 = itemIndex7 + 1;
							itemIndex8 = itemIndex8 + 1;
							this.showItemList(consumabels);
							descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).checkEnetry());
							itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).checkName());
							}	
					}
				}
			}
			//makes you go out of the tab if you push w and are at the top of the tab
			if(!iterateTab && keyPressed ('W') && itemPosition == 0 && ((itemIndex1 == 0 && consumabels) || (itemIndex5 == 0 && !consumabels)) ) {
				iterateTab = true;
				itemPosition = itemPosition - 1;
			}
			//incriments things up by 1 if you press w
			if ( !iterateTab && (itemPosition != 0  || (itemIndex1 != 0 && consumabels || itemIndex5 !=0 && !consumabels)) && keyPressed ('W') ) {
				if ( (consumabels&& ( (itemPosition != 0 && itemIndex4 <= GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size()-1) || (itemPosition != GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() - 1 - itemIndex1 && itemIndex4 > GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().size() -1)))|| ( !consumabels&&((itemPosition != 0 && itemIndex8 <= GameCode.testJeffrey.getInventory().getSortedKey().size()-1) || (itemPosition != GameCode.testJeffrey.getInventory().getSortedKey().size() - 1 - itemIndex5 && itemIndex8 > GameCode.testJeffrey.getInventory().getSortedKey().size() -1)))) {
					itemPosition = itemPosition - 1;
					this.setItemEntryAndNameInfo();
				} else {
					if (consumabels) {
					this.removeItemList(consumabels);
					itemIndex1 = itemIndex1 - 1;
					itemIndex2 = itemIndex2 - 1;
					itemIndex3 = itemIndex3 - 1;
					itemIndex4 = itemIndex4 - 1;
					this.showItemList(consumabels);
					descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
					itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
					} else {
						
							this.removeItemList(consumabels);
							itemIndex5 = itemIndex5 - 1;
							itemIndex6 = itemIndex6 - 1;
							itemIndex7 = itemIndex7 - 1;
							itemIndex8 = itemIndex8 - 1;
							this.showItemList(consumabels);
							descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry());
							itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkName());
					}
				}
					
		}
		}
		//draws stuff for page 4
		if (pageNumber == 3) {
			if (keyPressed (32) && enemyPosition != -1) {
				if (inBigPictureMode) {
					this.showEnemyList();
					AfterRenderDrawer.forceRemoveElement((int) this.getX() + 250,  (int) this.getY() + 225);
					detailedEnemyName.forget();
					detailedEnemyEntry.forget();
				} else {
				this.removeEnemyList();
				this.getAnimationHandler().setAnimationFrame(6);
				if (enemyPosition == 0) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250, (int)this.getY() + 225, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex1).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175, this.getY() + 125, 40, 1, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337, this.getY() + 155, 18, 18, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkEntry(), false);
				}
				if (enemyPosition == 1) {
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 250, (int)this.getY() + 225, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex2).getSprite(),0, true);
				detailedEnemyName = new Tbox (this.getX() + 175, this.getY() + 125, 40, 1, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkName(), false);
				detailedEnemyEntry = new Tbox (this.getX() + 337, this.getY() + 155, 18, 18, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkEntry(), false);
				}
				if (enemyPosition == 2) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250, (int)this.getY() + 225, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex3).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175, this.getY() + 125, 40, 1, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337, this.getY() + 155, 18, 18, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkEntry(), false);
				}
				if (enemyPosition == 3) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 250, (int)this.getY() + 225, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex4).getSprite(),0, true);
					detailedEnemyName = new Tbox (this.getX() + 175, this.getY() + 125, 40, 1, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkName(), false);
					detailedEnemyEntry = new Tbox (this.getX() + 337, this.getY() + 155, 18, 18, GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkEntry(), false);
				}
				detailedEnemyName.setScrollRate(0);
				detailedEnemyEntry.setScrollRate(0);
				}
				inBigPictureMode = !inBigPictureMode;
			}
			//sets the background to the enemy background
			if (!inBigPictureMode) {
		this.getAnimationHandler().setAnimationFrame(5);
			}
		//moves the cursor down if s is pushed
		if(keyPressed ('S') && !inBigPictureMode) {
			iterateTab = false;
			if ((enemyPosition != 3 && enemyIndex4 <= GameCode.testJeffrey.getInventory().amountOfKills()-1) || (enemyPosition != GameCode.testJeffrey.getInventory().amountOfKills() - 1 - enemyIndex1 && enemyIndex4 > GameCode.testJeffrey.getInventory().amountOfKills() -1)) {
				enemyPosition = enemyPosition + 1;
			} else {
				if (enemyIndex4 != GameCode.testJeffrey.getInventory().amountOfKills() - 1 && enemyIndex3 != GameCode.testJeffrey.getInventory().amountOfKills() -1 && enemyIndex2 != GameCode.testJeffrey.getInventory().amountOfKills() -1 && enemyIndex1 != GameCode.testJeffrey.getInventory().amountOfKills() -1) {
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
			iterateTab = true;
			enemyPosition = enemyPosition - 1;
		}
		//incriments things up by 1 if you press w
		if ( !inBigPictureMode && !iterateTab && (enemyPosition != 0  || enemyIndex1 != 0) && keyPressed ('W') ) {
			if ((enemyPosition != 0 && enemyIndex4 <= GameCode.testJeffrey.getInventory().amountOfKills()-1) || (enemyPosition != GameCode.testJeffrey.getInventory().amountOfKills() - 1 - enemyIndex1 && enemyIndex4 > GameCode.testJeffrey.getInventory().amountOfKills() -1)) {
				enemyPosition = enemyPosition - 1;
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
			this.getAnimationHandler().setAnimationFrame(7);
		}
	}
	public void setItemEntryAndNameInfo () {
		if (consumabels) {
			if (itemPosition == 0) {
		descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkEnetry());
		itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName());
			}
			if (itemPosition == 1) {
				descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkEnetry());
				itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkName());
					}
			if (itemPosition == 2) {
				descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkEnetry());
				itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkName());
					}
			if (itemPosition == 3) {
				descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkEnetry());
				itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName());
					}
		} else {
			if (itemPosition == 0) {
				descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkEnetry());
				itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkName());
					}
					if (itemPosition == 1) {
						descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6).checkEnetry());
						itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6).checkName());
							}
					if (itemPosition == 2) {
						descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7).checkEnetry());
						itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7).checkName());
							}
					if (itemPosition == 3) {
						descriptionBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).checkEnetry());
						itemNameBox.setContent(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).checkName());
							}
		}
	}
	public void removeItemList (boolean consumablesOrKey) {
		if (consumablesOrKey) {
		try {
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 185);
			itemName1.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 245);
			itemName2.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 315);
			itemName3.forget();
			AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 400);
			itemName4.forget();
		} catch(IndexOutOfBoundsException e) {
			
		}
		} else {
			try {
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).getSprite(), (int)this.getX() + 55, (int)this.getY() + 185);
				itemName1.forget();
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6).getSprite(), (int)this.getX() + 55, (int)this.getY() + 245);
				itemName2.forget();
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7).getSprite(),(int)this.getX() + 55, (int)this.getY() + 315);
				itemName3.forget();
				AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).getSprite(),(int)this.getX() + 55, (int)this.getY() + 400);
				itemName4.forget();
			} catch(IndexOutOfBoundsException e) {
				
			}	
		}
	}
	public void removeEnemyList () {
			try {
				AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 55, (int)this.getY() + 185);
				enemyName1.forget();
				AfterRenderDrawer.forceRemoveElement( (int)this.getX() + 55, (int)this.getY() + 245);
				enemyName2.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 315);
				enemyName3.forget();
				AfterRenderDrawer.forceRemoveElement((int)this.getX() + 55, (int)this.getY() + 400);
				enemyName4.forget();
			} catch(IndexOutOfBoundsException e) {
				
			}	
	}
	public void showEnemyList () {
		int beforeCrash = 0;
		try {
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex1).getSprite(), 0, true);
			enemyName1 = new Tbox(this.getX() + 165, this.getY() + 155, 30, 4,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex1).checkName(),false);
			enemyName1.setScrollRate(0);
			beforeCrash = 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 245,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex2).getSprite(), 0, true);
			enemyName2 = new Tbox(this.getX() + 165, this.getY() + 225, 30, 4,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex2).checkName(),false);
			enemyName2.setScrollRate(0);
			beforeCrash = 2;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() +  315,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex3).getSprite(), 0, true);
			enemyName3 = new Tbox(this.getX() + 165, this.getY() + 295, 30, 4,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex3).checkName(),false);
			enemyName3.setScrollRate(0);
			beforeCrash = 3;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 400,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex4).getSprite(), 0, true);
			enemyName4 = new Tbox(this.getX() + 165, this.getY() + 365, 30, 4,GameCode.testJeffrey.getInventory().findEnemyAtIndex(enemyIndex4).checkName(),false);
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
	}
	//true for consumabels false for key
	public void showItemList (boolean consumablesOrKey) {
		int beforeCrash = 0;
		if (consumablesOrKey) {
		try {
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).getSprite(), 0, true);
			itemName1 = new Tbox(this.getX() + 105, this.getY() + 155, 20, 4,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex1))),false);
			itemName1.setScrollRate(0);
			beforeCrash = 1;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 245,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).getSprite(), 0, true);
			itemName2 = new Tbox(this.getX() + 105, this.getY() + 225, 20, 4,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex2))),false);
			itemName2.setScrollRate(0);
			beforeCrash = 2;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() +  315,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).getSprite(), 0, true);
			itemName3 = new Tbox(this.getX() + 105, this.getY() + 295, 20, 4,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex3))),false);
			itemName3.setScrollRate(0);
			beforeCrash = 3;
			AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 400,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).getSprite(), 0, true);
			itemName4 = new Tbox(this.getX() + 105, this.getY() + 370, 20, 4,GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedConsumablesAndAmmo().get(itemIndex4))),false);
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
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 185,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).getSprite(), 0, true);
				itemName1 = new Tbox(this.getX() + 105, this.getY() + 155, 20, 4,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5).checkName()+ " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex5))),false);
				itemName1.setScrollRate(0);
				beforeCrash =1;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 245,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6).getSprite(), 0, true);
				itemName2 = new Tbox(this.getX() + 105, this.getY() + 225, 20, 4,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex6))),false);
				itemName2.setScrollRate(0);
				beforeCrash = 2;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() +  315,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7).getSprite(), 0, true);
				itemName3 = new Tbox(this.getX() + 105, this.getY() + 295, 20, 4,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex7))),false);
				itemName3.setScrollRate(0);
				beforeCrash = 3;
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 55, (int)this.getY() + 400,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).getSprite(), 0, true);
				itemName4 = new Tbox(this.getX() + 105, this.getY() + 370, 20, 4,GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8).checkName() + " X " + Integer.toString(GameCode.testJeffrey.getInventory().checkItemAmount(GameCode.testJeffrey.getInventory().getSortedKey().get(itemIndex8))),false);
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
	}
	public void addTierOrbs () {
		for (int i = 0; i < 4; i++) {
			for (int x = 0; x < GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getTierInfo() [i]; x ++ ) {
				if (i == 0) {
				AfterRenderDrawer.drawAfterRender((int)this.getX() + 145 + (x*60), (int)this.getY() + 320, ball, 0, true);
				}
				if (i == 1) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 330 + (x*60), (int)this.getY() + 320, ball, 0, true);
					}
				if (i == 2) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 145 + (x*60), (int)this.getY() + 380, ball, 0, true);
					}
				if (i == 3) {
					AfterRenderDrawer.drawAfterRender((int)this.getX() + 70, (int)this.getY() + 435, ball, 0, true);
					}
			}
		}
	}
	public void removeTierOrbs() {
		for (int i = 0; i <4; i++) {
			for (int x = 0; x < GameCode.testJeffrey.getInventory().findWeaponAtIndex(weaponIndex).getTierInfo() [i]; x ++ ) {
				if (i == 0) {
				AfterRenderDrawer.removeElement(ball,(int)this.getX() + 145 + (x*60), (int)this.getY() + 320);
				}
				if (i == 1) {
					AfterRenderDrawer.removeElement(ball, (int)this.getX() + 330 + (x*60), (int)this.getY() + 320);
					}
				if (i == 2) {
					AfterRenderDrawer.removeElement(ball, (int)this.getX() + 145 + (x*60), (int)this.getY() + 380);
					}
				if (i == 3) {
					AfterRenderDrawer.removeElement(ball, (int)this.getX() +70, (int)this.getY() + 435);
					}
			}
		}
	}
	public void setUpNewPlayableCharictar () {
		if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("JEFFREY")) {
			//creates a new display of jeffreys health when it gets changed to the charitar page if jeffrey's page is the active page
		healthBox = new Tbox(this.getX() + 35, this.getY()+ 280, 16,9,Integer.toString((int)GameCode.testJeffrey.getHealth(0)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth), false);
		healthBox.setScrollRate(0);
		//adds the weapons owned by jeffrey to the after render draw arraylist (adds a question mark for the ones he doesen't own) if jeffrey's page is the active page
		for (int i = 0; i <4; i++) {
			if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 0).getClass().getSimpleName().equals("Unarmed")) {
				AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 0).getUnrotatedSprite(), 0, true);
			} else {
				AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,questionMark, 0, true);	
			}
		}
		}
		
		if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("SAM")) {
			//creates a new display of sam's health when it gets changed to the charitar page is sam's page is the active page
			healthBox = new Tbox(this.getX() + 35, this.getY()+ 280, 16,9,Integer.toString((int)GameCode.testJeffrey.getHealth(1)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth), false);
			healthBox.setScrollRate(0);
			//adds the weapons owned by sam to the after render draw arraylist (adds a question mark for the ones he doesen't own) if sam's page is the active page
			for (int i = 0; i <4; i++) {
				if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 1).getClass().getSimpleName().equals("Unarmed")) {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 1).getUnrotatedSprite(), 0, true);
				} else {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,questionMark, 0, true);	
				}
			}
			}
		if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("RYAN")) {
			//creates a new display of Ryan's health when it gets changed to the charitar page is Ryan's page is the active page
			healthBox = new Tbox(this.getX() + 35, this.getY()+ 280, 16,9,Integer.toString((int)GameCode.testJeffrey.getHealth(2)) + "/" + Integer.toString((int) GameCode.testJeffrey.maxHealth), false);
			healthBox.setScrollRate(0);
			//adds the weapons owned by Ryan to the after render draw arraylist (adds a question mark for the ones he doesen't own) if sam's page is the active page
			for (int i = 0; i <4; i++) {
				if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 2).getClass().getSimpleName().equals("Unarmed")) {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 2).getUnrotatedSprite(), 0, true);
				} else {
					AfterRenderDrawer.drawAfterRender((int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320,questionMark, 0, true);	
				}
			}
		}
	}
	public void removeWeaponsOfOldCharictar () {
		if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("JEFFREY")) {
			for (int i = 0; i <4; i++) {
				if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 0).getClass().getSimpleName().equals("Unarmed")) {
					AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 0).getUnrotatedSprite(),(int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320);
				} else {
					AfterRenderDrawer.removeElement(questionMark,(int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320);	
				}
			}
			}
			
			if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("SAM")) {
				for (int i = 0; i <4; i++) {
					if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 1).getClass().getSimpleName().equals("Unarmed")) {
						AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 1).getUnrotatedSprite(),(int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320);
					} else {
						AfterRenderDrawer.removeElement(questionMark,(int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320);	
					}
				}
				}
			if (GameCode.testJeffrey.getInventory().findFreindAtIndex(charictarIndex).checkName().equals("RYAN")) {
				for (int i = 0; i <4; i++) {
					if (!GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 2).getClass().getSimpleName().equals("Unarmed")) {
						AfterRenderDrawer.removeElement(GameCode.testJeffrey.getInventory().findWeaponAtIndex(i, 2).getUnrotatedSprite(),(int)(this.getX() + 90 + (i*60)) , (int)this.getY() + 320);
					} else {
						AfterRenderDrawer.removeElement(questionMark,(int)(this.getX() + 180 + (i*60)) , (int)this.getY() + 320);	
					}
				}
			}
	}
}
