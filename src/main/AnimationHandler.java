package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import resources.Sprite;

public class AnimationHandler {
	
	/**
	 * The image for this AnimationHandler to draw
	 */
	private Sprite image;
	
	/**
	 * The frame which corresponds to the starting time
	 */
	private int startFrame;
	
	/**
	 * The time the animation started
	 */
	private long startTime;
	
	/**
	 * The time of each frame, in milliseconds
	 */
	private double frameTime;
	
	/**
	 * Whether or not this animation repeats
	 */
	private boolean repeat;
	/**
	 * make true to make the sprite alternate from going forward to back to backward to forward
	 */
	private boolean alternate = false;
	/**
	 * Whether or not to apply horizontal flip
	 */
	private boolean visible = true;
	private boolean flipHorizontal;
	
	/**
	 * Whether or not to apply vertical flip
	 */
	private boolean flipVertical;
	
	
	
	/**
	 * deal with the reversal
	 */
	private boolean reverse = false;
	private boolean hasReversed = false;
	
	/**
	 * the height to draw too
	 */
	private int height;
	
	/**
	 * the width to draw too
	 */
	
	private int width;
	/**
	 * tells the handler wherether or not to keep the sprite to scale
	 */
	private boolean keepScale = false;
	/**
	 * Constructs a new AnimationHandler with the given image, defaulting to a static image.
	 * @param image The image to use
	 */
	public AnimationHandler (Sprite image) {
		this (image, 0);
	}
	
	/**
	 * Constructs a new AnimationHandler with the given image, with each frame lasting for frameTime milliseconds.
	 * @param image The image to use
	 * @param frameTime The numer of milliseconds to show each frame for; displays a static image if set to 0
	 */
	public AnimationHandler (Sprite image, double frameTime) {
		this.image = image;
		startTime = RenderLoop.frameStartTime ();
		startFrame = 0;
		this.frameTime = frameTime;
		repeat = true;
		try {
			width = image.getFrame(0).getWidth();
			height = image.getFrame(0).getHeight();
		} catch (NullPointerException e) {
			
		}
	}
	
	/**
	 * Draws the sprite's current animation frame at the given x and y coordinates.
	 * @param x The x coordinate to draw at
	 * @param y The y coordinate to draw at
	 */
	public void draw (double x, double y) {
		if (visible) {
		if (image != null) {
			if (frameTime == 0) {
				startTime = RenderLoop.frameStartTime ();
				image.draw ((int)x, (int)y, flipHorizontal, flipVertical, startFrame);
			} else {
				long elapsedTime = RenderLoop.frameStartTime () - startTime;
				int elapsedFrames = ((int)(((double)elapsedTime) / ((double)frameTime)) + startFrame);
				if (!repeat && elapsedFrames >= image.getFrameCount ()) {
					image.draw ((int)x, (int)y, flipHorizontal, flipVertical, image.getFrameCount () - 1,width,height);
				} else {
					int frame = elapsedFrames % image.getFrameCount ();
					if (frame == 0 && alternate) {
						if (!hasReversed) {
						reverse = !reverse;
						hasReversed = true;
						}
					} else {
						hasReversed = false;
					}
					if (reverse) {
						frame = (image.getFrameCount() - 1) - frame;
					}
					image.draw ((int)x, (int)y, flipHorizontal, flipVertical, frame,width,height);
				}
			}
		}
		}
	}
	
	/**
	 * Sets the image used by this AnimationHandler to the given sprite.
	 * @param image The image to use
	 */
	public void setImage (Sprite image) {
		this.image = image;
		if ((width == 0 && height == 0) || !keepScale) {
		width = image.getFrame(0).getWidth();
		height = image.getFrame(0).getHeight();
		} else {
			if (width != image.getFrame(0).getWidth() || height != image.getFrame(0).getHeight()) {
				this.scale(width, height);
			}
		}
	}
	
	/**
	 * Sets the image used by this AnimationHandler to the given sprite, and restarts the animation from the beginning.
	 * @param image The image to use
	 */
	public void resetImage (Sprite image) {
		setImage (image);
		startTime = RenderLoop.frameStartTime ();
		startFrame = 0;
	}
	/**
	 * makes the animation handler keep the sprite to scale
	 * ie will NOT allow changes to width and height
	 */
	public void keepScale () {
		keepScale = true;
	}
	/**
	 * makes the animation handler not keep the sprite to scale
	 * ie will allow changes to width and height
	 */
	public void dontKeepScale () {
		keepScale = false;
	}
	/**
	 * Sets the current frame of the animation to the given frame.
	 * @param frame The frame to use
	 */
	public void setAnimationFrame (int frame) {
		startFrame = frame;
		startTime = RenderLoop.frameStartTime ();
	}
	
	/**
	 * Sets the time between frames to the given time.
	 * @param frameTime The time to use, in milliseconds
	 */
	public void setFrameTime (double frameTime) {
		this.frameTime = frameTime;
	}
	
	/**
	 * Sets whether this animation repeats or not after it is complete.
	 * @param repeats Whether the animation repeats
	 */
	public void setRepeat (boolean repeats) {
		repeat = repeats;
	}
	/**
	 * makes the sprite alternate
	 */
	public void enableAlternate () {
		alternate = true;
	}
	/**
	 * makes the sprite not alternate
	 */
	public void disableAlternate () {
		alternate = false;
		reverse = false;
		this.setAnimationFrame(0);
	}
	/**
	 * changes how far out it draws the image
	 */
	public void setHeight(int newHeight) {
		height = newHeight;
	}
	/**
	 * changes how far to the right it draws the image
	 */
	public void setWidth(int newWidht) {
		width = newWidht;
	}
	/**
	 * Gets the image used by this AnimationHandler.
	 * @return The image field for this AnimationHandler
	 */
	public Sprite getImage () {
		return image;
	}
	/**
	 * scales the image to a specific size
	 *@param width the width you want
	 *@param height the height you want
	 */
	public void scale (int width, int height) {
		for (int i = 0; i < image.getFrameCount(); i++) {
			Image img =image.getFrame(i).getScaledInstance(width, height, Image.SCALE_FAST);
			BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();
			image.setFrame(i,bimage);
		}
		this.width = width;
		this.height = height;
	}
	/**
	 * Gets the frame which would be drawn at the time this method is called. Not guarenteed to be the frame that will be drawn next.
	 * @return The current frame for this AnimationHandler
	 */
	public int getFrame () {
		if (image != null) {
			long elapsedTime = RenderLoop.frameStartTime () - startTime;
			int frame = ((int)(((double)elapsedTime) / ((double)frameTime)) + startFrame) % image.getFrameCount ();
			if (reverse) {
			return (image.getFrameCount() -1) - frame;
			} else {
			return frame;
			}
		} else {
			return -1;
		}
	}
	public int getWidth () {
		return width;
	}
	public int getHeight () {
		return height;
	}
	/**
	 * Gets the time each frame should be displayed.
	 * @return The length of time, in milliseconds, to display each frame
	 */
	public double getFrameTime () {
		return frameTime;
	}
	
	/**
	 * Returns true if this animation is set to repeat; false otherwise.
	 * @return Whether this animation repeats or not
	 */
	public boolean repeats () {
		return repeat;
	}
	
	/**
	 * Returns whether or not horizontal flipping is applied.
	 * @return the horizontal flip
	 */
	public boolean flipHorizontal () {
		return flipHorizontal;
	}
	
	/**
	 * Returns whether or not vertical flipping is applied.
	 * @return the vertical flip
	 */
	public boolean flipVertical () {
		return flipVertical;
	}
	
	/**
	 * Sets whether or not to flip the image horizontally to the given value.
	 * @param flip whether or not to apply horizontal flip
	 */
	public void setFlipHorizontal (boolean flip) {
		flipHorizontal = flip;
	}
	
	/**
	 * Sets whether or not to flip the image vertically to the given value.
	 * @param flip whether or not to apply vertical flip
	 */
	public void setFlipVertical (boolean flip) {
		flipVertical = flip;
	}
	public void hide () {
		this.visible = false;
	}
	public void show () {
		this.visible = true;
	}
}
