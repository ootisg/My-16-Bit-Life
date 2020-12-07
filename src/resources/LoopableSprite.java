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
	AnimationHandler startSprite = null;
	AnimationHandler remainderHandler;
	public LoopableSprite(Sprite sprite, int moveX,int moveY, int desX, int desY) {
		super(sprite);
		AnimationHandler handler = new AnimationHandler (sprite, frameTime);
		handlers.add(handler);
		remainderHandler = new AnimationHandler(sprite,frameTime);
		this.moveX = moveX;
		this.moveY = moveY;
		this.desX = desX;
		this.desY = desY;
	}
	//setting the destination to -1 will make the sprite not requre anything of that particualr axis (useful for when you want to loop along one axis but not both)	
	@Override 
	public void draw(int x, int y) {
		this.draw(x, y, false, false, 0, this.getFrame(0).getWidth(), this.getFrame(0).getHeight());
	}
	@Override 
	public void draw(int x, int y, boolean flipHorizontal, boolean flipVertical, int startFrame) {
		this.draw(x, y, flipHorizontal, flipVertical, startFrame, this.getFrame(0).getWidth(), this.getFrame(0).getHeight());
	}
	@Override 
	public void draw(int x, int y, boolean flipHorizontal, boolean flipVertical, int startFrame, int width, int height) {
		int index = 0;
		int tempX = x;
		int tempY = y;
		boolean directionX = x > desX;
		boolean directionY = y > desY;
		if (startSprite != null) {
			startSprite.draw(tempX, tempY);
		}
		while ((((tempX > desX && directionX) || (tempX < desX && !directionX)) && desX != -1) || (((tempY > desY && directionY) || (tempY < desY && !directionY)) && desY != -1)){
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
		int remanderWidth = 0;
		int remainderHeight = 0;
			if (moveY != 0) {
				remainderHeight = desY % moveY;
			}
			if (moveX != 0) {
				remanderWidth = desX % moveX; 
			}
			if (remainderHeight != 0) {
				remainderHandler.setHeight(remainderHeight);
			}
			if (remanderWidth != 0) {
				remainderHandler.setWidth(remanderWidth);
			}
			if (remainderHeight != 0 || remanderWidth != 0) {
				remainderHandler.draw(tempX, tempY);
			}
				
		if (endSprite != null) {
			if (!directionY) {
				endSprite.draw(desX, desY);
			} else {
				endSprite.draw(desX, desY - endSprite.getHeight());
			}
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
		if (startSprite != null) {
			startSprite.setFrameTime(frameTime);
		}
	}
	/**
	 * adds a sprite to be played at the end of the sprite
	 * @param endSprite the sprite you want to play after the looping
	 */
	public void addEndSprite(Sprite endSprite) {
		this.endSprite = new AnimationHandler (endSprite, frameTime);
	}
	/**
	 * adds a sprite to be played at the beginning of the sprite
	 * @param startSprite the sprite you want to play before the looping
	 */
	public void addStartSprite(Sprite startSprite) {
		this.startSprite = new AnimationHandler (startSprite, frameTime);
	}
}
