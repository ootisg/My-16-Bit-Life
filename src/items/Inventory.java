package items;

import java.util.ArrayList;
import java.util.Iterator;

import enemys.CreepyButterfly;
import enemys.Enemy;
import main.GameCode;
import npcs.Door;
import npcs.NPC;
import resources.Sprite;
import weapons.LifeVaccum;
import weapons.Unarmed;
import weapons.redBlackPaintBallGun;

public class Inventory {
	ArrayList <Item> consuables = new ArrayList ();
	ArrayList <Item> key = new ArrayList ();
	ArrayList <Item> ammo = new ArrayList ();
	ArrayList <Item> jeffreyWeapons = new ArrayList ();
	ArrayList <Item> samWeapons = new ArrayList ();
	ArrayList <Item> ryanWeapons = new ArrayList ();
	ArrayList <NPC> metCharcitars = new ArrayList ();
	ArrayList <Enemy> defeatedEnemys = new ArrayList ();
	int money;
	int WEXP;
	Sprite lol;
	int lifeBattary;
	public Inventory (){
		lifeBattary = 1000;
		money = 0;
		WEXP = 0;
		lol = new Sprite ("resources/sprites/blank.png");
	}
	//checkItemAmount returns the amount of that Item in the players inventory
	public int checkItemAmount (Item ItemToCheck) {
		int coolNumber = 0;
		if (this.checkConsumable(ItemToCheck)){
			coolNumber = 1;
		} else {
			if (this.checkKey(ItemToCheck)){
				coolNumber = 2;
			} else {
				if (this.checkAmmo(ItemToCheck)){
					coolNumber = 3;
				}
			}
		}
		if (coolNumber == 0) {
		return 0;
		}
		if (coolNumber == 1) {
			int numberOfItems = 0;
			for (Item item : this.consuables) {
				if (item.getClass().equals(ItemToCheck.getClass())){
					numberOfItems = numberOfItems + 1;
		}
		}
			return numberOfItems;
		}
		if (coolNumber == 2) {
			int numberOfItems = 0;
			for (Item item : this.key) {
				if (item.getClass().equals(ItemToCheck.getClass())){
					numberOfItems = numberOfItems + 1;
		}
		}
			return numberOfItems;
		}
		if (coolNumber == 3) {
			int numberOfItems = 0;
			for (Item item : this.ammo) {
				if (item.getClass().equals(ItemToCheck.getClass())){
					numberOfItems = numberOfItems + 1;
		}
		}
			return numberOfItems;
		}
		return 0;
	}
	//returns an array of itmes that only has one of each item in it
	public ArrayList <Item> getSortedInventory (){
		ArrayList <Item> sortedInventory = new ArrayList ();
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
		ArrayList <Item> sortedInventory = new ArrayList ();
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
		public ArrayList <Item> getSortedConsumablesAndAmmo (){
			ArrayList <Item> sortedInventory = new ArrayList ();
			for (int i = 0; i < this.amountOfConsumables() + amountOfAmmo(); i++) {
				if (i < this.amountOfConsumables()) {
				for (int j = 0; j < sortedInventory.size() + 1; j++ ) {
					if (j == sortedInventory.size()) {
					sortedInventory.add	(consuables.get(i));
					break;
					}
					if (sortedInventory.get(j).getClass().getSimpleName().equals(consuables.get(i).getClass().getSimpleName())) {
						break;
					}
				}
				} else {
					for (int j = 0; j < sortedInventory.size() + 1; j++ ) {
						if (j == sortedInventory.size()) {
						sortedInventory.add	(ammo.get(i - this.amountOfConsumables()));
						break;
						}
						if (sortedInventory.get(j).getClass().getSimpleName().equals(ammo.get(i - this.amountOfConsumables()).getClass().getSimpleName())) {
							break;
						}
					}
				}
			}
			
			return sortedInventory;
		}
		public void addItem (Item itemToAdd) {
			if (itemToAdd.getItemType().equals("Ammo")) {
				ammo.add(itemToAdd);
			} else {
				if (itemToAdd.getItemType().equals("WeaponSam")) {
					samWeapons.add(itemToAdd);
				} else {
					if (itemToAdd.getItemType().equals("WeaponJeffrey")) {
						jeffreyWeapons.add(itemToAdd);
					} else {
						if (itemToAdd.getItemType().equals("WeaponRyan")) {
							ryanWeapons.add(itemToAdd);
						} else {
							if (itemToAdd.getItemType().equals("Consumable")) {
								consuables.add(itemToAdd);
							} else {
								if (itemToAdd.getItemType().equals("Key")) {
									key.add(itemToAdd);
								}
							}
						}
					}
				}
			}
		}
	public void addKill (Enemy enemyToAdd) {
		defeatedEnemys.add(enemyToAdd);
	}
	public void addAmmo (Item itemToAdd) {
		ammo.add(itemToAdd);
	}
	public void addKeyItem (Item itemToAdd) {
		key.add(itemToAdd);
	}
	public void addConsumable (Item itemToAdd) {
		consuables.add(itemToAdd);
	}
	public void addFreind (NPC friendToAdd) {
		metCharcitars.add(friendToAdd);
	}
	public void addWeapon (Item itemToAdd, int witchCharictar) {
		if (witchCharictar == 0) {
		jeffreyWeapons.add(itemToAdd);
		} 
		if (witchCharictar == 1) {
		samWeapons.add(itemToAdd);
		}
		if (witchCharictar == 2) {
		ryanWeapons.add(itemToAdd);	
		}
	}
	// removeItem returns false if the item is not in the players inventory
	public boolean removeItem (Item itemtoRemove) {
		if (this.checkConsumable(itemtoRemove)){
			Iterator <Item> Itr = consuables.iterator();
			while (Itr.hasNext()) {
				Item item = Itr.next(); 
				if (item.getClass().equals(itemtoRemove.getClass())){
					Itr.remove();
					return true;
				}
			}
		} else {
			if (this.checkKey(itemtoRemove)){
				Iterator <Item> Itr = key.iterator();
				while (Itr.hasNext()) {
					Item item = Itr.next();
					if (item.getClass().equals(itemtoRemove.getClass())){
						Itr.remove();
						return true;
					}
				}
			} else {
				if (this.checkAmmo(itemtoRemove)){
					Iterator <Item> Itr = ammo.iterator();
					while (Itr.hasNext() ) {
						Item item = Itr.next();
						if (item.getClass().equals(itemtoRemove.getClass())){
							Itr.remove();
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	// checkItem returns true if the item is in the players inventory
	public boolean checkItem (Item itemToCheck) {
		if (checkConsumable(itemToCheck) || checkKey(itemToCheck) || checkAmmo(itemToCheck) || checkWeapons(itemToCheck) ){
			return true;
		} else {
			return false;
		}
	}
	public boolean checkConsumable (Item itemtoCheck) {
		for (Item item : this.consuables) {
		if (item.getClass().equals(itemtoCheck.getClass())){
			return true;
		}
	}
		return false;
	}
	// checkFreinds returns true if the player has met this charictar
	public boolean checkFreinds (NPC freindtoCheck) {
		for (NPC item : this.metCharcitars) {
		if (item.getClass().equals(freindtoCheck.getClass())){
			return true;
		}
	}
		return false;
	}
	// checkKills returns true if the player has defeated this enemy
	public boolean checkKill (Enemy enemytoCheck) {
		for (Enemy item : this.defeatedEnemys) {
		if (item.getClass().equals(enemytoCheck.getClass())){
			return true;
		}
	}
		return false;
	}
	public boolean checkKey (Item itemtoCheck) {
		for (Item item : this.key) {
			if (item.getClass().equals(itemtoCheck.getClass())){
				return true;
			}
		}
		return false;
	}
	public boolean checkAmmo (Item itemtoCheck) {
		for (Item item : this.ammo) {
			if (item.getClass().equals(itemtoCheck.getClass())){
				return true;
	}
	}
		return false;
	}
	public boolean checkWeapons (Item itemtoCheck, int witchCharictar) {
		if (witchCharictar == 0) {
		for (Item item : this.jeffreyWeapons) {
			if (item.getClass().equals(itemtoCheck.getClass())){
				return true;
	}
	}
		}if (witchCharictar == 1) {
			for (Item item : this.samWeapons) {
				if (item.getClass().equals(itemtoCheck.getClass())){
					return true;
		}
		}
		}
		if (witchCharictar == 2) {
			for (Item item : this.ryanWeapons) {
				if (item.getClass().equals(itemtoCheck.getClass())){
					return true;
		}
		}
		}
		
		return false;
	}
	public boolean checkWeapons (Item itemtoCheck) {
		for (Item item : this.jeffreyWeapons) {
			if (item.getClass().equals(itemtoCheck.getClass())){
				return true;
		}
		}
			for (Item nutz : this.samWeapons) {
				if (nutz.getClass().equals(itemtoCheck.getClass())){
					return true;
		}
		}
			for (Item dude : this.ryanWeapons) {
				if (dude.getClass().equals(itemtoCheck.getClass())){
					return true;
		}
		}
		
		return false;
	}
	//retuns 256 if the item is not in the players inventory
		public int checkConsumableIndex (Item itemtoCheck) {
			int index = 0;
			for (Item item : this.consuables) {
			index = index + 1;
			if (item.getClass().equals(itemtoCheck.getClass())){
				return index - 1;
			}
		}
			return 256;
		}
		//retuns 256 if the item is not in the players inventory
		public int checkKeyIndex (Item itemtoCheck) {
			int index = 0;
			for (Item item : this.key) {
			index = index + 1;
			if (item.getClass().equals(itemtoCheck.getClass())){
				return index - 1;
			}
		}
			return 256;
		}
		//retuns 256 if the item is not in the players inventory
		public int checkAmmoIndex (Item itemtoCheck) {
			int index = 0;
			for (Item item : this.ammo) {
			index = index + 1;
			if (item.getClass().equals(itemtoCheck.getClass())){
				return index - 1;
			}
		}
			return 256;
		}
		//retuns 256 if the item is not in the players inventory
		public int checkWeaponIndex (Item itemtoCheck, int witchCharictar) {
			int index = 0;
			if (witchCharictar == 0) {
			for (Item item : this.jeffreyWeapons) {
			index = index + 1;
			if (item.getClass().equals(itemtoCheck.getClass())){
				return index - 1;
			}
		}
			}
			if (witchCharictar == 1) {
				for (Item item : this.samWeapons) {
				index = index + 1;
				if (item.getClass().equals(itemtoCheck.getClass())){
					return index - 1;
				}
			}
				}
			if (witchCharictar == 2) {
				for (Item item : this.ryanWeapons) {
				index = index + 1;
				if (item.getClass().equals(itemtoCheck.getClass())){
					return index - 1;
				}
			}
				}
			return 256;
		}
		public int checkAmmoAmountOfWeapon (Item weaponToCheck) {
			redBlackPaintBallGun testGun;
			testGun = new redBlackPaintBallGun(lol);
			if (weaponToCheck.getClass().equals(testGun.getClass())) {
				RedBlackPaintBall ball;
				ball = new RedBlackPaintBall (1);
				return (this.checkItemAmount(ball));
			}
			LifeVaccum testVaccum;
			testVaccum = new LifeVaccum(lol);
			if (weaponToCheck.getClass().equals(testVaccum.getClass())) {
				return (lifeBattary);
			}
			return (0);
		}
		public Item findConsumableAtIndex (int index) {
			return consuables.get(index);
		}
		public Item findKeyAtIndex (int index) {
			try {
			return key.get(index);
		} catch (IndexOutOfBoundsException e) {
			return new FairUseKey ();
			}
		}
		public Item findAmmoAtIndex (int index) {
			try {
			return ammo.get(index);
			} catch (IndexOutOfBoundsException e) {
				return new RedBlackPaintBall(1);
				}
		}
		public Item findItemAtIndex (int index) {
			try {
				if (this.getItemIndex(index) [1] == 0) {
				return consuables.get(getItemIndex(index) [0]);
				} 
				if (this.getItemIndex(index) [1] == 1) {
				return key.get(getItemIndex(index) [0]);
				}
				if (this.getItemIndex(index) [1] == 2) {
				return ammo.get(getItemIndex(index) [0]);	
				}
				return new LemonPacket();
				} catch (IndexOutOfBoundsException e) {
					return new LemonPacket();
				}
		}
		public int checkAmountOfItems () {
			return consuables.size() + key.size() + ammo.size();
		}
		public NPC findFreindAtIndex (int index) {
			try {
			return metCharcitars.get(index);
			} catch (IndexOutOfBoundsException e) {
				return new Door();
				}
		}
			public Enemy findEnemyAtIndex (int index) {
				return defeatedEnemys.get(index);
		}
		//gives you the index and item type from a strate number 
		// in other words if jeffrey has 3 consumables and you give this a 4 it will return 0 and 1 the is to show the index is the 0 the one it too show that that index represents a key item
		public int[] getItemIndex ( int index) {
			if (index > this.amountOfConsumables() + this.amountOfKey() -1) {
				int [] coolArray = {index - (this.amountOfConsumables() + this.amountOfKey()), 2};
				return coolArray;
			}
			if (index > this.amountOfConsumables() - 1) {
				int [] coolArray = {index - (this.amountOfConsumables()) , 1};
				return coolArray;
			}
			int [] coolArray = {index, 0};
			return coolArray;
		}
//gives you the index and charictar number from a strate number 
	// in other words if jeffrey has 3 weapons and you give this a 4 it will return 0 and 1 the is to show the index is the 0 the one it too show that that index corrisponds with sam's inventory
public int[] getWeaponIndex ( int index) {
	if (index > this.amountOfWeapons(0) + this.amountOfWeapons(1) -1) {
		int [] coolArray = {index - (this.amountOfWeapons(0) + this.amountOfWeapons(1)), 2};
		return coolArray;
	}
	if (index > this.amountOfWeapons(0) - 1) {
		int [] coolArray = {index - (this.amountOfWeapons(0)) , 1};
		return coolArray;
	}
	int [] coolArray = {index, 0};
	return coolArray;
}
public int amountOfWeaponsOfAllCharictars () {
	return amountOfWeapons (0) + amountOfWeapons (1) + amountOfWeapons (2);
}
		public Item findWeaponAtIndex (int index, int witchCharictar) {
			try {
			if (witchCharictar == 0) {
			return jeffreyWeapons.get(index);
			} 
			if (witchCharictar == 1) {
			return samWeapons.get(index);
			}
			if (witchCharictar == 2) {
			return ryanWeapons.get(index);	
			}
			return new Unarmed(new Sprite ("resources/sprites/blank.png"));
			} catch (IndexOutOfBoundsException e) {
			return new Unarmed(new Sprite ("resources/sprites/blank.png"));
			}
		}
		public Item findWeaponAtIndex (int index) {
			try {
				if (this.getWeaponIndex(index) [1] == 0) {
				return jeffreyWeapons.get(getWeaponIndex(index) [0]);
				} 
				if (this.getWeaponIndex(index) [1] == 1) {
				return samWeapons.get(getWeaponIndex(index) [0]);
				}
				if (this.getWeaponIndex(index) [1] == 2) {
				return ryanWeapons.get(getWeaponIndex(index) [0]);	
				}
				return new Unarmed(new Sprite ("resources/sprites/blank.png"));
				} catch (IndexOutOfBoundsException e) {
				return new Unarmed(new Sprite ("resources/sprites/blank.png"));
				}
		}
		public int amountOfConsumables (){
			return consuables.size();
		}
		public int amountOfKills () {
			return defeatedEnemys.size();
		}
		public int amountOfKey () {
			return key.size();
		}
		public int amountOfAmmo() {
			return ammo.size();
		}
		public int amountOfFreinds () {
			return metCharcitars.size();
		}
		//returns -1 if things fail
		public int amountOfWeapons (int witchCharictar) {
			if (witchCharictar == 0) {
			return jeffreyWeapons.size();
		}
			if (witchCharictar == 1) {
			return samWeapons.size();	
		}
			if (witchCharictar == 2) {
			return ryanWeapons.size();
			}
			return -1;
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
	public int checkWEXP () {
		return this.WEXP;
	}
	public void addWEXP (int amount) {
		WEXP = WEXP + amount;
	}
	public void addLifeVaccumBattary (int amount) {
		lifeBattary = lifeBattary + amount;
		if (lifeBattary > 100) {
			lifeBattary = 100;
		}
	}
	public void subtractLifeVaccumBattary (int amount) {
		lifeBattary = lifeBattary - amount;
		if (lifeBattary < 0) {
			lifeBattary = 0;
		}
	}
	public int checkLifeVaccumBattary () {
		return lifeBattary;
	}
	// subractWEXP returns false if the amount you want to subtract is more than the amount you have
	public boolean subractWEXP (int amount) {
		if (WEXP >= amount) {
			WEXP = WEXP - amount;
			return true;
		} else {
			return false;
		}
	}
}
