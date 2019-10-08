package items;

import java.util.ArrayList;
import java.util.Iterator;

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
	int money;
	int WEXP;
	Sprite lol;
	int lifeBattary;
	public Inventory (){
		lifeBattary = 100;
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
		public int amountOfConsumbles (){
			return consuables.size();
		}
		public int amountOfKey () {
			return key.size();
		}
		public int amountOfAmmo() {
			return ammo.size();
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
