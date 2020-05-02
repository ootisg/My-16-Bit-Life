package theHeist;

import main.GameObject;
import resources.Sprite;

public class Item extends GameObject {
	String name;
	public Item (String name, Sprite sprite) {
		this.setSprite(sprite);
		this.name = name;
	}
	public String getName () {
		return name;
	}
}
