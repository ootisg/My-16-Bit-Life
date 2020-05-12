package resources;

import java.util.ArrayList;

import main.AnimationHandler;

public class LoopableSprite extends Sprite {
	int desX;
	int desY;
	int moveX;
	int frameTime = 100;
	int moveY;
	ArrayList <AnimationHandler> handlers = new ArrayList <AnimationHandler> ();
	AnimationHandler endSprite = null;
	public LoopableSprite(Sprite sprite, int moveX,int moveY, int desX, int desY) {
		super(sprite);
		AnimationHandler handler = new AnimationHandler (sprite, frameTime);
		handlers.add(handler);
		this.moveX = moveX;
		this.moveY = moveY;
		this.desX = desX;
		this.desY = desY;
	}
	@Override 
	public void draw(int x, int y) {
		int index = 0;
		int tempX = x;
		int tempY = y;
		boolean directionX = x > desX;
		boolean directionY = y > desY;
		while ((tempX > desX && directionX) || (tempX < desX && !directionX) || (tempY > desY && directionY) || (tempY < desY && !directionY)){
			
			if (directionX) {
				tempX = tempX - moveX;
				if (tempX < desX) {
					tempX = desX;
				}
			} else {
				tempX = tempX + moveX;
				if (tempX > desX) {
					tempX = desX;
				}
			}
			if (directionY) {
				tempY = tempY - moveY;
				if (tempY < desY) {
					tempY = desY;
				}
			} else {
				tempY = tempY + moveY;
				if (tempY > desY) {
					tempY = desY;
				}
			}
			if (!(tempX == desX && tempY == desY)) {
				handlers.get(index).draw(tempX, tempY);
			} 
			index = index + 1;
			if (index == handlers.size()) {
				index = 0;
			}
		}
		if (endSprite != null) {
		endSprite.draw(desX, desY);
		}
	}
	public void setDestanation (int x, int y) {
		desX = x;
		desY = y;
	}
	/**
	 * adds a sprite to be looped
	 */
	public void addSprite (Sprite newSprite) {
		AnimationHandler handler = new AnimationHandler (newSprite, frameTime);
		handlers.add(handler);
	}
	public void setFrameTime (int newFrameTime) {
		frameTime = newFrameTime;
		for (int i = 0; i < handlers.size(); i++) {
			handlers.get(i).setFrameTime(frameTime);
		}
		if (endSprite != null) {
			endSprite.setFrameTime(frameTime);
		}
	}
	public void addEndSprite(Sprite endSprite) {
		this.endSprite = new AnimationHandler (endSprite, frameTime);
	}
}
