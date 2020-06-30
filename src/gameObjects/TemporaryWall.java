package gameObjects;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.GameObject;
import map.Room;
import resources.Sprite;
import switches.Activateable;

public class TemporaryWall extends GameObject implements Activateable {
	public boolean activated;
	private boolean wasActivated;
	public TemporaryWall () {
		activated = false;
		wasActivated = false;
		this.setHitboxAttributes(0, 0, 16, 16);
		this.getAnimationHandler().hide();
	}
	@Override
	public void frameEvent () {
		if (this.activated) {
			Room.getMapObjects().put(Room.toPackedLong((int)this.getX()/16, (int)this.getY()/16),this);
			try {
			this.setSprite(new Sprite ("resources/sprites/" + this.getVariantAttribute("sprite") + ".png"));
			this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
			} catch (NullPointerException e) {
				
			}
			this.getAnimationHandler().show();
			wasActivated = true;
		} else {
			if (wasActivated) {
				Room.getMapObjects().remove(Room.toPackedLong((int)this.getX()/16, (int)this.getY()/16));
				wasActivated = false;
			}
		}
	}
	/*
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
	}*/
	@Override
	public void activate() {
		this.activated = true;
	}
	@Override
	public void deactivate() {
		this.activated = false;
	}
	@Override
	public boolean isActivated() {
		return this.activated;
	}
	@Override
	public void pair() {
		
	}
	

}
