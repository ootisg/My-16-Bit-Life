package items;

import java.util.ArrayList;
import java.util.Iterator;

import enemys.CreepyButterfly;
import enemys.Enemy;
import main.GameCode;
import main.ObjectHandler;
import npcs.NPC;
import resources.Sprite;
import weapons.LifeVaccum;
import weapons.Unarmed;
import weapons.Weapon;
import weapons.redBlackPaintBallGun;

public class Inventory {
	ArrayList <Item> items = new ArrayList <Item> ();
	ArrayList <Weapon> weapons = new ArrayList <Weapon> ();
	ArrayList <NPC> metCharcitars = new ArrayList <NPC>();
	ArrayList <Enemy> defeatedEnemys = new ArrayList <Enemy>();
	int money;
	public Inventory (){
		money = 0;
	}
	public Inventory (ArrayList <Item> allItems, ArrayList <NPC> metCharictars, ArrayList <Enemy> defeatedEnmies, int money) {
		for (int i = 0; i < allItems.size(); i++) {
			switch (allItems.get(i).getItemType()){
			case "Consumable":
				items.add(allItems.get(i).clone());
				break;
			case "Key":
				items.add(allItems.get(i).clone());
				break;
			case "Item":
				items.add(allItems.get(i).clone());
				break;
			case "WeaponSam":
				weapons.add((Weapon)allItems.get(i).clone());
				break;
			case "WeaponJeffrey":
				weapons.add((Weapon)allItems.get(i).clone());
				break;
			case "WeaponRyan":
				weapons.add((Weapon)allItems.get(i).clone());
				break;
			}
		}
		for (int i = 0; i < metCharictars.size(); i++) {
			this.metCharcitars.add((NPC)metCharictars.get(i).clone());
		}
		
		for (int i = 0; i < defeatedEnemys.size(); i++) {
			this.defeatedEnemys.add((Enemy)ObjectHandler.getInstance(defeatedEnemys.get(i).getClass().getSimpleName()));
		}
		this.money = money;
	}
	@Override 
	public Inventory clone () {
		ArrayList <Item> allItems = new ArrayList<Item>();
		allItems.addAll(items);
		allItems.addAll(weapons);
		return new Inventory (allItems,metCharcitars,defeatedEnemys,money);
	}
	//checkItemAmount returns the amount of that Item in the players inventory
	public int checkItemAmount (String itemType) {
			int count = 0;
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getClass().getSimpleName() == itemType) {
					count = count + 1;
				}
			}
			return count;
		}
	public ArrayList <Item> getKeyItems (){
		ArrayList <Item> keyItems = new ArrayList <Item> ();
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == "Key") {
				keyItems.add(items.get(i));
			}
		}
		return keyItems;
		
	}
	
	public ArrayList <Item> getConsumableItems (){
		ArrayList <Item> consumables = new ArrayList <Item> ();
		
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == "Consumable") {
				consumables.add(items.get(i));
			}
		}
		return consumables;
		
	}
	
	//returns an array of itmes that only has one of each item in it
	public ArrayList <Item> getSortedInventory (){
		ArrayList <Item> sortedInventory = new ArrayList <Item>();
		for (int i = 0; i < this.checkAmountOfItems(); i++) {
			for (int j = 0; j < sortedInventory.size() + 1; j++ ) {
				if (j == sortedInventory.size()) {
					sortedInventory.add	(findItemAtIndex(i));
					break;
				}
				if (sortedInventory.get(j).getClass().getSimpleName().equals(findItemAtIndex(i).getClass().getSimpleName())) {
					break;
				}
			}
		}
		return sortedInventory;
	}
	public ArrayList <Item> getSortedKey (){
		ArrayList <Item> sortedInventory = new ArrayList<Item> ();
		
		ArrayList <Item> key = this.getKeyItems();
		
		for (int i = 0; i < this.amountOfKey(); i++) {
			for (int j = 0; j < sortedInventory.size() + 1; j++ ) {
				if (j == sortedInventory.size()) {
					sortedInventory.add	(key.get(i));
					break;
				}
				if (sortedInventory.get(j).getClass().getSimpleName().equals(key.get(i).getClass().getSimpleName())) {
					break;
				}
			}
		}
		return sortedInventory;
	}
	//returns an array of itmes that only has one of each item in it
		public ArrayList <Item> getSortedConsumables (){
			ArrayList <Item> sortedInventory = new ArrayList <Item>();
			
			ArrayList <Item> consuables = getConsumableItems();
			
			for (int i = 0; i < this.amountOfConsumables(); i++) {
				for (int j = 0; j < sortedInventory.size() + 1; j++ ) {
					if (j == sortedInventory.size()) {
						sortedInventory.add	(consuables.get(i));
						break;
					}
					if (sortedInventory.get(j).getClass().getSimpleName().equals(consuables.get(i).getClass().getSimpleName())) {
						break;
					}
				}
			}
			
			return sortedInventory;
		}
		public void addItem (Item itemToAdd) {
			
			switch (itemToAdd.getItemType()) {
				case "WeaponSam":
					weapons.add((Weapon)itemToAdd);
					break;
				case "WeaponJeffrey":
					weapons.add((Weapon)itemToAdd);
					break;
				case "WeaponRyan":
					weapons.add((Weapon)itemToAdd);
					break;
				case "Consumable":
					items.add(itemToAdd);
					break;
				case "Key":
					items.add(itemToAdd);
					break;
				case "Item":
					items.add(itemToAdd);
					break;
			}
			
		}
	public Weapon getWeapon (String weaponType) {
		for (int i = 0; i < weapons.size(); i++) {
			if (weapons.get(i).getClass().getSimpleName().equals(weaponType)) {
				return weapons.get(i);
			}
		}
		return null;
	}
	public void addKill (Enemy enemyToAdd) {
		defeatedEnemys.add(enemyToAdd);
	}
	public void addKeyItem (Item itemToAdd) {
		items.add(itemToAdd);
	}
	public void addConsumable (Item itemToAdd) {
		items.add(itemToAdd);
	}
	public void addFreind (NPC friendToAdd) {
		metCharcitars.add(friendToAdd);
	}
	
	public void addWeapon (Weapon itemToAdd) {
		weapons.add(itemToAdd);
	}
	// removeItem returns false if the item is not in the players inventory
	public boolean removeItem (Item itemtoRemove) {
		if (items.contains(itemtoRemove)) {
			itemtoRemove.onRemove();
			items.remove(itemtoRemove);
			return true;
		}
		return false;
	}
	
	// removeItem returns false if the item is not in the players inventory
		public boolean removeItem (String itemType) {
			
			for (int i = 0; i < items.size(); i++) {
				
				if (items.get(i).getClass().getSimpleName() == itemType) {
					items.get(i).onRemove();
					items.remove(i);
					return true;
				}
				
			}
			
			return false;
		}
	
	// checkItem returns true if the item is in the players inventory
	public boolean checkItem (String itemType) {
		
		Item itemToCheck = (Item)ObjectHandler.getInstance(itemType);
		
		if (items.contains(itemToCheck) || weapons.contains(itemToCheck)){
			return true;
		} else {
			return false;
		}
	}
	// checkFreinds returns true if the player has met this charictar
	public boolean checkFreinds (NPC freindtoCheck) {
		return metCharcitars.contains(freindtoCheck);
	}
	// checkKills returns true if the player has defeated this enemy
	public boolean checkKill (Enemy enemytoCheck) {
		return defeatedEnemys.contains(enemytoCheck);
	}
	//retuns 256 if the item is not in the players inventory
		public int checkItemIndex (String itemType) {
			for (int i = 0; i <items.size(); i++) {
				if (itemType == items.get(i).getClass().getSimpleName()) {
					return i;
				}
			}
			return 256;
		}
		//retuns 256 if the item is not in the players inventory
		public int checkKeyIndex (String itemType) {
			
			ArrayList <Item> key = getKeyItems();
			
			for (int i = 0; i <key.size(); i++) {
				if (itemType == key.get(i).getClass().getSimpleName()) {
					return i;
				}
			}
			return 256;
		}
		//retuns 256 if the item is not in the players inventory
		public int checkWeaponIndex (String itemType) {
			
			for (int i = 0; i <weapons.size(); i++) {
				if (itemType == weapons.get(i).getClass().getSimpleName()) {
					return i;
				}
			}
			return 256;
		}
		public Item findItemAtIndex (int index) {
			return items.get(index);
		}
		public int checkAmountOfItems () {
			return items.size();
		}
		public ArrayList <NPC> getFreinds (){
			return metCharcitars;
		}
		public ArrayList <Enemy> getEnimes (){
			return defeatedEnemys;
		}
		public ArrayList <Weapon> getWeapons (){
			return weapons;
		}
		public ArrayList <Weapon> getJeffreyWeapons (){
			ArrayList <Weapon>jeffreyWeapons = new ArrayList <Weapon>();
			
			for (int i = 0; i < weapons.size(); i++) {
				if (weapons.get(i).getItemType() == "WeaponJeffrey") {
					jeffreyWeapons.add(weapons.get(i));
				}
			}
			return jeffreyWeapons;
		}
		public ArrayList <Weapon> getSamWeapons (){
			ArrayList <Weapon>samWeapons = new ArrayList <Weapon>();
			
			for (int i = 0; i < weapons.size(); i++) {
				if (weapons.get(i).getItemType() == "WeaponSam") {
					samWeapons.add(weapons.get(i));
				}
			}
			return samWeapons;
		}
		
		public ArrayList <Weapon> getRyanWeapons (){
			ArrayList <Weapon>ryanWeapons = new ArrayList <Weapon>();
			
			for (int i = 0; i < weapons.size(); i++) {
				if (weapons.get(i).getItemType() == "WeaponRyan") {
					ryanWeapons.add(weapons.get(i));
				}
			}
			return ryanWeapons;
		}
		public ArrayList <Weapon> getPlayerWeapon (int player){
			switch (player) {
			case 0:
				return getJeffreyWeapons();
			case 1:
				return getSamWeapons();
			case 2:
				return getRyanWeapons();
			}
			return null;
		}
		
		public int amountOfConsumables (){
			return this.getConsumableItems().size();
		}
		public int amountOfKills () {
			return defeatedEnemys.size();
		}
		public int amountOfKey () {
			return this.getKeyItems().size();
		}
		public int amountOfFreinds () {
			return metCharcitars.size();
		}
		//returns -1 if things fail
		public int amountOfWeapons () {
			return this.weapons.size();
		}
	public int checkMoney () {
		return this.money;
	}
	public void addMoney (int amount) {
		money = money + amount;
	}
	// subractMoney returns false if the amount you want to subtract is more than the amount you have
	public boolean subractMoney (int amount) {
		if (money >= amount) {
			money = money - amount;
			return true;
		} else {
			return false;
		}
	}
}
