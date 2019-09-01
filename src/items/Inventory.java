package items;

import java.util.ArrayList;
import java.util.Iterator;

import resources.Sprite;
import weapons.Unarmed;

public class Inventory {
	ArrayList <Item> consuables = new ArrayList ();
	ArrayList <Item> key = new ArrayList ();
	ArrayList <Item> ammo = new ArrayList ();
	ArrayList <Item> weapons = new ArrayList ();
	int money;
	int WEXP;
	public Inventory (){
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
			while (this.checkConsumable(ItemToCheck)) {
				numberOfItems = numberOfItems + 1;
				this.removeItem(ItemToCheck);
			}
			int copyOfNumberOfItems = numberOfItems;
			while (copyOfNumberOfItems != 0) {
				copyOfNumberOfItems = copyOfNumberOfItems - 1;
				consuables.add(ItemToCheck);
			}
			return numberOfItems;
		}
		if (coolNumber == 2) {
			int numberOfItems = 0;
			while (this.checkKey(ItemToCheck)) {
				numberOfItems = numberOfItems + 1;
				this.removeItem(ItemToCheck);
			}
			int copyOfNumberOfItems = numberOfItems;
			while (copyOfNumberOfItems != 0) {
				copyOfNumberOfItems = copyOfNumberOfItems - 1;
				key.add(ItemToCheck);
			}
			return numberOfItems;
		}
		if (coolNumber == 3) {
			int numberOfItems = 0;
			while (this.checkAmmo(ItemToCheck)) {
				numberOfItems = numberOfItems + 1;
				this.removeItem(ItemToCheck);
			}
			int copyOfNumberOfItems = numberOfItems;
			while (copyOfNumberOfItems != 0) {
				copyOfNumberOfItems = copyOfNumberOfItems - 1;
				ammo.add(ItemToCheck);
			}
			return numberOfItems;
		}
		return 0;
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
	public void addWeapon (Item itemToAdd) {
		weapons.add(itemToAdd);
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
	public boolean checkConsumable (Item itemtoCheck) {
		for (Item item : this.consuables) {
		if (item.getClass().equals(itemtoCheck.getClass())){
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
	public boolean checkWeapons (Item itemtoCheck) {
		for (Item item : this.weapons) {
			if (item.getClass().equals(itemtoCheck.getClass())){
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
		public int checkWeaponIndex (Item itemtoCheck) {
			int index = 0;
			for (Item item : this.weapons) {
			index = index + 1;
			if (item.getClass().equals(itemtoCheck.getClass())){
				return index - 1;
			}
		}
			return 256;
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
				return new RedBlackPaintBall();
				}
		}
		public Item findWeaponAtIndex (int index) {
			try {
			return weapons.get(index);
			} catch (IndexOutOfBoundsException e) {
			return new Unarmed(new Sprite ("resources/sprites/blank.png"));
			}
		}
		public int amountOfConsumbles (){
			return consuables.size();
		}
		public int amountOfKey () {
			return key.size();
		}
		public int amountOfAmmo() {
			return ammo.size();
		}
		public int amountOfWeapons () {
			return weapons.size();
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
