package resources;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.AnimationHandler;
import main.RenderLoop;
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
	@Override
	public void drawRotated (int x, int y, int frame, double anchorX, double anchorY, double rotation) {
		AffineTransform transform = new AffineTransform ();
		transform.translate (x, y);
		transform.rotate (rotation, anchorX, anchorY);
		draw (x, y, frame, transform);
	}
	@Override
	public void draw (double usedX, double usedY, int frame, AffineTransform transform) {
		int index = 0;
		int tempX = 0;
		int tempY = 0;
		int desXReal = (int) (desX - usedX);
		int desYReal = (int) (desY - usedY);
		boolean directionX = tempX > desXReal;
		boolean directionY = tempY > desYReal;
		if (startSprite != null) {
			startSprite.draw(tempX, tempY);
		}
		
		int width;
		
		if (desX != -1) {
			width = (int) Math.abs(desX - usedX);
		} else {
			width = handlers.get(0).getWidth();
		}
		
		int height;
		
		if (desY != -1) {
			height = (int) Math.abs(desY - usedY);
		} else {
			height = handlers.get(0).getHeight();
		}
		
		BufferedImage tempImg = new BufferedImage(width, height,BufferedImage.TYPE_4BYTE_ABGR);
		while ((((tempX > desXReal && directionX) || (tempX < desXReal && !directionX)) && desX != -1) || (((tempY > desYReal && directionY) || (tempY < desYReal && !directionY)) && desY != -1)){
			if (!(tempX == desXReal && tempY == desYReal)) {
				handlers.get(index).draw(tempX, tempY, tempImg);
			} 
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
				remainderHandler.draw(tempX, tempY, tempImg);
			}
				
		if (endSprite != null) {
			if (!directionY) {
				endSprite.draw(desXReal, desYReal, tempImg);
			} else {
				endSprite.draw(desXReal, desYReal - endSprite.getHeight(), tempImg);
			}
		}
		
		Graphics2D windowGraphics = (Graphics2D) RenderLoop.window.getBufferGraphics();
		windowGraphics.drawImage (tempImg, transform, null);
		
		
		
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