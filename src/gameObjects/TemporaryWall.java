package gameObjects;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.GameObject;
import resources.Sprite;

public class TemporaryWall extends GameObject {
	public boolean activated;
	public static ArrayList <TemporaryWall> walls = new ArrayList <TemporaryWall>();
	public TemporaryWall () {
		activated = false;
		this.setHitboxAttributes(0, 0, 16, 16);
		this.getAnimationHandler().hide();
		walls.add(this);
	}
	public boolean isHitting (Rectangle box) {
		this.setSprite(new Sprite ("resources/sprites/" + this.getVariantAttribute("sprite") + ".png"));
		if (activated) {
			this.getAnimationHandler().show();
		} else {
			this.hide();
		}
		if (this.isColliding(box) && activated) {
			return true;
		} else {
			return false;
		}
	}
	

}
