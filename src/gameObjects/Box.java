package gameObjects;

import main.GameObject;
import map.Room;
import resources.Sprite;

public class Box extends MapObject {
	public Box () {
		this.setHitboxAttributes(0, 0, 32, 32);
		this.setSprite(new Sprite ("resources/sprites/box.png"));
	}
	public void frameEvent () {
		super.frameEvent();
		this.goY(this.getY() + 3);
	}
}
