package resources;

import java.util.ArrayList;

import main.AnimationHandler;
import map.Room;

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
		int desXReal = desX - Room.getViewX();
		int desYReal = desY - Room.getViewY();
		boolean directionX = tempX > desXReal;
		boolean directionY = tempY > desYReal;
		if (startSprite != null) {
			startSprite.draw(tempX, tempY);
		}
		while ((((tempX > desXReal && directionX) || (tempX < desXReal && !directionX)) && desX != -1) || (((tempY > desYReal && directionY) || (tempY < desYReal && !directionY)) && desY != -1)){
			if (directionX) {
				tempX = tempX - moveX;
				if (tempX < desXReal) {
					tempX = desXReal;
				}
			} else {
				tempX = tempX + moveX;
				if (tempX > desXReal) {
					tempX = desXReal;
				}
			}
			if (directionY) {
				tempY = tempY - moveY;
				if (tempY < desYReal) {
					tempY = desYReal;
				}
			} else {
				tempY = tempY + moveY;
				if (tempY > desYReal) {
					tempY = desYReal;
				}
			}
			if (!(tempX == desXReal && tempY == desYReal)) {
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
				remainderHeight = desYReal % moveY;
			}
			if (moveX != 0) {
				remanderWidth = desXReal % moveX; 
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
				endSprite.draw(desXReal, desYReal);
			} else {
				endSprite.draw(desXReal, desYReal - endSprite.getHeight());
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
