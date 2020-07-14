package gameObjects;

import items.Item;

public class ItemDropRate {
	Item item;
	int dropRate;
	int minDropAmount = 1;
	int maxDropAmount = 1;
	public ItemDropRate (Item item, int dropRate) {
		this.item = item;
		this.dropRate = dropRate;
	}
	public ItemDropRate (Item item, int dropRate, int minDrop, int maxDrop) {
		this.item = item;
		this.dropRate = dropRate;
		this.minDropAmount = minDrop;
		this.maxDropAmount = maxDrop;
	}
	public Item getItem () {
		return item.clone();
	}
	public int getDropRate () {
		return dropRate;
	}
	public int getMinDrop() {
		return this.minDropAmount;
	}
	public int getMaxDrop () {
		return this.maxDropAmount;
	}
}
