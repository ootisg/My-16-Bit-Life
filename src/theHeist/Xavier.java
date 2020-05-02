package theHeist;

import java.util.ArrayList;
import java.util.LinkedList;

import main.GameObject;
import main.ObjectHandler;
import resources.Sprite;

public class Xavier extends GameObject{
	public ArrayList <Item> currentItems = new ArrayList<Item>();
	int iq = 0;
	boolean patience = false;
	boolean strength = false;
	public Xavier() {
		this.setSprite(new Sprite ("resources/sprites/the-heist/Xavier.png"));
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (keyDown ('D')) {
			this.goX(this.getX() + 2);
		}
		if (keyDown ('A')) {
			this.goX(this.getX() - 2);
		}
		if (keyDown ('S')) {
			this.goY(this.getY() + 2);
		}
		if (keyDown ('W')) {
			this.goY(this.getY() - 2);
		}
		LinkedList<LinkedList<GameObject>> checkableObjects =  ObjectHandler.getChildrenByName("CheckableObject");
		for (int i = 0; i < checkableObjects.size(); i++) {
			for (int j = 0; j<checkableObjects.get(0).size(); j++) {
				if (this.isColliding(checkableObjects.get(i).get(j)) && keyPressed(10)) {
				
					CheckableObject c = (CheckableObject) checkableObjects.get(i).get(j);
					c.onCheck();
				}
			}
		}
	}
	public void giveItem (Item item) {
		currentItems.add(item);
	}
	/**
	 * returns true if the player had the specified item and removes it from their inventory
	 * @param item the item to check
	 * @return wheather or not it was removed
	 */
	public boolean takeItem(String itemName) {
		for (int i = 0; i < currentItems.size(); i++) {
			if (currentItems.get(i).getName().equals(itemName)) {
				currentItems.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * returns true if the item is in the inventory
	 * @param whitch item to check
	 * @return wheather or not xavier has the item
	 */
	public boolean hasItem (String itemName) {
		for (int i = 0; i < currentItems.size(); i++) {
			if (currentItems.get(i).getName().equals(itemName)) {
				return true;
			}
		}
		return false;
	}
	public void powerUp () {
		strength = true;
	}
	public void becomePatient () {
		patience = true;
	}
	public void setIQ(int newIQ) {
		iq = newIQ;
	}
	public boolean isPowerful() {
		return strength;
	}
	public boolean isPatient() {
		return patience;
	}
	public int getIQ() {
		return iq;
	}
}
