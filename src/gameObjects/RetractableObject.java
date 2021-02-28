package gameObjects;

import main.GameObject;
import resources.Sprite;

public class RetractableObject extends GameObject {
	
	double speed = 0;
	double scrolledPos = 0;
	
	int targetPos;
	
	Sprite fullSprite;
	
	public RetractableObject (double speed) {
		this.speed = speed;
		
	}
	@Override 
	public void frameEvent () {
		
		if (targetPos > scrolledPos) {
			double oldPos = scrolledPos;
			scrolledPos = scrolledPos + speed;
			if (scrolledPos > targetPos) {
				scrolledPos = targetPos;
			}
			if (scrolledPos > 0.5) {
				super.setSprite(new Sprite (fullSprite.getFrame(0).getSubimage(0, 0, fullSprite.getFrame(0).getWidth(), (int) scrolledPos)));
			} else {
				super.setSprite(new Sprite ("resources/sprites/blank.png"));
			}
			this.setHitboxAttributes(0,0, fullSprite.getFrame(0).getWidth(), (int)scrolledPos);
			this.setY(this.getY() - (scrolledPos - oldPos));
		} else if (scrolledPos > targetPos) {
			double oldPos = scrolledPos;
			scrolledPos = scrolledPos - speed;
			if (scrolledPos < targetPos) {
				scrolledPos = targetPos;
			}
			if (scrolledPos > 0.5) {
				super.setSprite(new Sprite (fullSprite.getFrame(0).getSubimage(0, 0, fullSprite.getFrame(0).getWidth(), (int) scrolledPos)));
			} else {
				super.setSprite(new Sprite ("resources/sprites/blank.png"));
			}			
			this.setHitboxAttributes(0,0, fullSprite.getFrame(0).getWidth(), (int)scrolledPos);
			this.setY(this.getY() + (oldPos - scrolledPos));
		}
	}
	public void extend () {
		targetPos = fullSprite.getFrame(0).getHeight();
	}
	public void retract () {
		targetPos = 0;
	}
	public void goToPos (int pos) {
		if (pos >= 0 && pos <= fullSprite.getFrame(0).getWidth());{
			targetPos = pos;
		}
	}
	public boolean isExtended () {
		return scrolledPos == fullSprite.getFrame(0).getHeight();
	}
	public boolean isRetracted () {
		return scrolledPos == 0;
	}
	@Override 
	public void setSprite (Sprite sprite) {
		if (scrolledPos > sprite.getFrame(0).getHeight()) {
			scrolledPos = sprite.getFrame(0).getHeight();
		}
		if (targetPos > sprite.getFrame(0).getHeight()) {
			targetPos = sprite.getFrame(0).getHeight();
		}
		if (scrolledPos >0.5) {
			super.setSprite(new Sprite (sprite.getFrame(0).getSubimage(0, 0, sprite.getFrame(0).getWidth(), (int) scrolledPos)));
		} else {
			super.setSprite(new Sprite ("resources/sprites/blank.png"));
		}
		this.setHitboxAttributes(0,0, sprite.getFrame(0).getWidth(), (int)scrolledPos);
		fullSprite = sprite;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
