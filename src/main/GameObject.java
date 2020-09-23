package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import enemys.Enemy;
import map.Room;
import resources.Sprite;

/**
 * Represents an in-game object that can be interacted with in some way
 * @author nathan
 *
 */
public abstract class GameObject extends GameAPI {
	/**
	 * if true code for this wont run
	 */
	private boolean blackListed = false;
	/**
	 * x-coordinate of this GameObject
	 */
	protected double x;
	/**
	 * y-coordinate of this GameObject
	 */
	private double y;
	/**
	 * Previous x-coordinate of this GameObject
	 */
	protected double xprevious;
	/**
	 * Previous y-coordinate of this GameObject
	 */
	private double yprevious;
	/**
	 * The width of this GameObject's hitbox
	 */
	public boolean visible = true;
	private double hitboxWidth;
	/**
	 * The height of this GameObject's hitbox
	 */
	private double hitboxHeight;
	/**
	 * The horizontal offset of this GameObject's hitbox
	 */
	private double hitboxXOffset;
	/**
	 * The vertical offset of this GameObject's hitbox
	 */
	private double hitboxYOffset;
	/**
	 * Whether or not this object has a hitbox
	 */
	private boolean hasHitbox = true;
	/**
	 * Whether or not this object has been declared
	 */
	private boolean declared;
	
	/**
	 * lol yes = pixel collisions my guy
	 */
	private boolean pixelCollisions = false;
	
	/**
	 * If true, this GameObject persists between room transitions
	 */
	private boolean persistent = false;
	
	/**
	 * The AnimationHandler object used to render this GameObject
	 */
	private AnimationHandler animationHandler = new AnimationHandler (null);
	/**
	 * The variant of this GameObject
	 */
	/**
	 * coordinates where the sprites are drawn
	 */
	protected double spriteX;
	private double spriteY;
	/**
	 * for use with despawnAllCoolLike
	 */
	private int despawnTimer;
	
	protected boolean hiboxBorders = false;
	
	private Variant variant;
	/**
	 * The CollisionInfo object generated by the most recent collision check done by this GameObject
	 */
	private CollisionInfo lastCollision;
	
	/**
	 * The render priority of this GameObject
	 */
	private double renderPriority = 0;
	
	/**
	 * The game logic priority of this GameObject
	 */
	private double gameLogicPriority = 0;
	
	/**
	 * Container and utility class for GameObject variants
	 * @author nathan
	 *
	 */
	public class Variant {
		
		/**
		 * Contains the list of attributes for this variant
		 */
		private LinkedList<Attribute> attributes;
		
		/**
		 * Container and utility class for mapping variant names to values
		 * @author nathan
		 *
		 */
		private class Attribute {
			
			private String name;
			
			private String value;
			
			public Attribute (String name, String value) {
				this.name = name;
				this.value = value;
			}
			
			public String getName () {
				return name;
			}
			
			public boolean isNamed (String name) {
				return this.name.equals (name);
			}
			
			public String getValue () {
				return value;
			}
			
			public void setValue (String value) {
				this.value = value;
			}
			
			@Override
			public String toString () {
				return name + ":" + value;
			}
		}
		
		/**
		 * No-arg constructor which constructs an empty variant
		 */
		public Variant () {
			attributes = new LinkedList<Attribute> ();
		}
		
		/**
		 * Constructs a new variant using the given data string.
		 * @param attributeData A variant data string formatted according to the specification of the setAttributes method
		 */
		public Variant (String attributeData) {
			this ();
			setAttributes (attributeData);
		}
		
		/**
		 * Sets the attribute with the given name to the given value.
		 * @param name The name of the attribute to assign
		 * @param value The new value of the attribute
		 */
		public void setAttribute (String name, String value) {
			Attribute workingAttribute = null;
			Iterator<Attribute> iter = attributes.iterator ();
			while (iter.hasNext ()) {
				Attribute working = iter.next ();
				if (working.isNamed (name)) {
					workingAttribute = working;
					break;
				}
			}
			if (workingAttribute == null) {
				attributes.add (new Attribute (name, value));
			} else {
				workingAttribute.setValue (value);
			}
		}
		/**
		 * Sets the attributes to the values indicated in the data string attributeData
		 * @param attributeData TODO
		 */
		public void setAttributes (String attributeData) {
			String[] variants = attributeData.split ("&");
			for (int i = 0; i < variants.length; i ++) {
				String[] attribute = variants [i].split (":");
				if (attribute.length == 2) {
					setAttribute (attribute [0], attribute [1]);
				}
			}
		}
		
		/**
		 * Gets the value of the attribute with the given name.
		 * @param name The name of the attribute
		 * @return The value mapped to the given name
		 */
		public String getAttribute (String name) {
			Iterator<Attribute> iter = attributes.iterator ();
			while (iter.hasNext ()) {
				Attribute working = iter.next ();
				if (working.isNamed (name)) {
					return working.getValue ();
				}
			}
			return null;
		}
		
		@Override
		public String toString () {
			String data = "";
			Iterator<Attribute> iter = attributes.iterator ();
			while (iter.hasNext ()) {
				data += iter.next ().toString ();
				if (iter.hasNext ()) {
					data += "&";
				}
			}
			return data;
		}
	}
	
	/**
	 * No-argument constructor for ease of inheretence
	 */
	public GameObject () {
		
	}
	
	/**
	 * Constructs a new GameObject at the given x and y coordinates.
	 * @param x The x-coordinate to use
	 * @param y The y-coordinate to use
	 */
	public GameObject (double x, double y) {
		this.x = x;
		this.y = y;
		spriteX = x;
		spriteY = y;
		xprevious = x;
		yprevious = y;
	}
	/**
	 * makes code for this object no longer run
	 */
	public void blackList () {
		blackListed = true;
	}
	/**
	 * makes code for this object run again
	 */
	public void whiteList () {
		blackListed = false;
	}
	public boolean isBlackListed () {
		return blackListed;
	}
	/**
	 * Inserts this object into the static instance of ObjectHandler, effectively scheduling it for calls to frameEvent and draw, in addition to allowing collision detection with it.
	 */
	public void declare () {
		
		ObjectHandler.insert (this);
		declared = true;
	}
	
	public void declare (double x, double y) {
		declare ();
		setX (x);
		setY (y);
	}
	/**
	 * Whether or not this GameObject is currently declared.
	 * @return true if declared; false otherwise
	 */
	public boolean declared () {
		return declared;
	}
	/**
	 * Removes this object from the static instance of ObjectHandler.
	 */
	public void forget () {
		ObjectHandler.remove (this);
		declared = false;
	}
	/**
	 * turns hitbox borders on if they are off (off if they are on)
	 */
	public void adjustHitboxBorders() {
		hiboxBorders = !hiboxBorders;
	}
	/**
	 * Draws this GameObject at its x and y coordinates relative to the room view.
	 */
	public void draw () {
		//TODO
		if (visible) {
		animationHandler.draw (spriteX - Room.getViewX (), spriteY - Room.getViewY ());
		if (hiboxBorders) {
			if (this.hitbox() != null) {
				Graphics g = RenderLoop.window.getBufferGraphics();
				g.setColor(new Color(0xFFFFFF));
				g.drawRect((int)(this.getX() + this.getHitboxXOffset() - Room.getViewX()),(int) (this.getY() + this.getHitboxYOffset() - Room.getViewY()), this.hitbox().width, this.hitbox().height);
				}
			}
		}
	}
	
	/**
	 * turns on pixel collisions
	 */
	protected void enablePixelCollisions () {
		pixelCollisions = true;
	}
	/**
	 * turns off pixel collisions
	 */
	protected void disablePixelCollisions () {
		pixelCollisions = false;
	}
	/**
	 * Draws this GameObject at its x and y coordinates relative to the screen.
	 */
	public void drawAbsolute () {
		animationHandler.draw (spriteX, spriteY);
	}
	
	/**
	 * Runs at every iteration of GameLoop
	 */
	public void frameEvent () {
		
	}
	//Runs when the game is paused
	public void pausedEvent() {
		
	}
	private boolean runPixelCollsions (GameObject pixelObject, Rectangle hitboxObject) {
		Raster mask;
		mask = pixelObject.getAnimationHandler().getImage().getFrame(pixelObject.getAnimationHandler().getFrame()).getAlphaRaster();
		
		int [] sample = new int [1];
		Rectangle working = pixelObject.hitbox().intersection(hitboxObject);
		int startPosX = (int) (working.x - pixelObject.hitbox().getX());
		int startPosY =(int) (working.y - pixelObject.hitbox().getY());
		if (startPosX < 0) {
			startPosX = startPosX*-1;
		}
		if (startPosY < 0) {
			startPosY = startPosY*-1;
		}
		for (int wy = 0; wy < working.height; wy++) {
			for (int wx = 0; wx < working.width; wx++){
				try {
				mask.getPixel (startPosX + wx, startPosY + wy,sample);	
				if (sample[0] != 0) {
					return true;
				}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
		return false;
	}
	private boolean runMultipulePixelCollsions (GameObject pixelObject, GameObject hitboxObject) {
		Raster mask1;
		Raster mask3;
		mask1 = pixelObject.getAnimationHandler().getImage().getFrame(pixelObject.getAnimationHandler().getFrame()).getAlphaRaster();
		mask3 = hitboxObject.getAnimationHandler().getImage().getFrame(hitboxObject.getAnimationHandler().getFrame()).getAlphaRaster();
		int [] sample = new int [1];
		Rectangle working = pixelObject.hitbox().intersection(hitboxObject.hitbox());
		int startPosX = (int) (pixelObject.hitbox().getX() - working.x);
		int startPosY =(int) (pixelObject.hitbox().getY() - working.y);
		int startPos_1 = (int)(hitboxObject.hitbox().getX() - working.x);
		int startPosbee = (int)(hitboxObject.hitbox().getY() - working.y);
		if (startPosX < 0) {
			startPosX = startPosX*-1;
		}
		if (startPosY < 0) {
			startPosY = startPosY*-1;
		}
		if (startPos_1 < 0) {
			startPos_1 = startPos_1*-1;
		}
		if (startPosbee < 0) {
			startPosbee = startPosbee*-1;
		}
		for (int wy = 0; wy < working.height; wy++) {
			for (int wx = 0; wx < working.width; wx++){
				try {
				mask1.getPixel (wx + startPosX,wy + startPosY,sample);
				if (sample[0] != 0) {
					mask3.getPixel (wx + startPos_1,wy + startPosbee,sample);
						if (sample[0] != 0) {
							return true;
					}
				}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
		return false;
	}
	/**
	 * uses the rotate method in animation handler to rotate the sprite also adjusts the hitbox to match (kinda)
	 * @param rotation the angle to rotate the sprte to (actually in degrees belive it or not)
	 */
	public void rotateSprite (double rotation) {
		BufferedImage working = this.getAnimationHandler().rotate(rotation,this.getSprite().getFrame(this.getAnimationHandler().getFrame()));
		//copy pasted from stack overflow
		  WritableRaster raster = working.getAlphaRaster();
		    int width = raster.getWidth();
		    int height = raster.getHeight();
		    int left = 0;
		    int top = 0;
		    int right = width - 1;
		    int bottom = height - 1;
		    int minRight = width - 1;
		    int minBottom = height - 1;

		    top:
		    for (;top < bottom; top++){
		        for (int x = 0; x < width; x++){
		            if (raster.getSample(x, top, 0) != 0){
		                minRight = x;
		                minBottom = top;
		                break top;
		            }
		        }
		    }

		    left:
		    for (;left < minRight; left++){
		        for (int y = height - 1; y > top; y--){
		            if (raster.getSample(left, y, 0) != 0){
		                minBottom = y;
		                break left;
		            }
		        }
		    }

		    bottom:
		    for (;bottom > minBottom; bottom--){
		        for (int x = width - 1; x >= left; x--){
		            if (raster.getSample(x, bottom, 0) != 0){
		                minRight = x;
		                break bottom;
		            }
		        }
		    }

		    right:
		    for (;right > minRight; right--){
		        for (int y = bottom; y >= top; y--){
		            if (raster.getSample(right, y, 0) != 0){
		                break right;
		            }
		        }
		    }

		  working = working.getSubimage(left, top, right - left + 1, bottom - top + 1);
		  this.setHitboxAttributes(0, 0,working.getWidth() ,working.getHeight());
		  this.getSprite().setFrame(this.getAnimationHandler().getFrame(), working);
	}
	/**
	 * Runs a collision check between this GameObject and another GameObject. Does not generate a CollisionInfo object.
	 * @param obj The object to check for collision with
	 * @return True if the objects collide; false otherwise
	 */
	public boolean isColliding (GameObject obj) {
	
		Rectangle thisHitbox = hitbox ();
		Rectangle objHitbox = obj.hitbox ();
		
		if (thisHitbox == null || objHitbox == null) {
			return false;
		}
		if (thisHitbox.intersects (objHitbox)) {
			boolean pixelCollisionsEnabled = obj.pixelCollisions;
			if ((!pixelCollisionsEnabled && !pixelCollisions)) {
				return true;
			} else if (!pixelCollisionsEnabled && pixelCollisions) {
				return this.runPixelCollsions(this, obj.hitbox());
			} else if (pixelCollisionsEnabled && !pixelCollisions) {
				return this.runPixelCollsions(obj, this.hitbox());
			} else {
				return this.runMultipulePixelCollsions(obj, this);
			}
		}
		return false;
	}
	public boolean isColliding (Rectangle hitbox) {
		Rectangle thisHitbox = hitbox ();
		Rectangle objHitbox = hitbox;
		if (thisHitbox == null || objHitbox == null) {
			return false;
		}
		if (thisHitbox.intersects (objHitbox)) {
			if (this.pixelCollisions) {
				return this.runPixelCollsions(this, hitbox);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Checks for collision with all GameObjects of the given type.
	 * @param objectType The type of GameObject to check for, as given by calling getClass.getSimpleName() on the object
	 * @return True if a collision was detected; false otherwise
	 */
	public boolean isColliding (String objectType) {
		lastCollision = ObjectHandler.checkCollision (objectType, this);
		return lastCollision.collisionOccured ();
	}
	
	/**
	 * Checks for collision with all GameObjects that are children of the given type.
	 * @param parentType The type of the parent GameObject, as given by calling getClass.getSimpleName() on the object 
	 * @return True if a collision was detected; false otherwise
	 */
	public boolean isCollidingChildren (String parentType) {
		lastCollision = ObjectHandler.checkCollisionChildren (parentType, this);
		return lastCollision.collisionOccured ();
	}
	
	/**
	 * Gets the CollisionInfo object generated by the last collision check performed by this object.
	 * @return The most recently generated CollisionInfo object
	 */
	public CollisionInfo getCollisionInfo () {
		return lastCollision;
	}
	
	/**
	 * Gets the x component of this GameObject's position.
	 * @return The x coordinate of this GameObject
	 */
	public double getX () {
		return x;
	}
	
	/**
	 * Gets the y component of this GameObject's position.
	 * @return The y coordinate of this GameObject
	 */
	public double getY () {
		return y;
	}
	
	/**
	 * Gets the x component of this GameObject's previous position.
	 * @return The x coordinate of this GameObject
	 */
	public double getXPrevious () {
		return xprevious;
	}
	
	/**
	 * Gets the y component of this GameObject's previous position.
	 * @return The y coordinate of this GameObject
	 */
	public double getYPrevious () {
		return yprevious;
	}
	
	public double getHitboxXOffset () {
		return hitboxXOffset;
	}
	
	public double getHitboxYOffset () {
		return hitboxYOffset;
	}
	
	public void useHitbox (boolean toUse) {
		hasHitbox = toUse;
	}
	
	/**
	 * Gets the sprite used to render this GameObject
	 * @return The sprite associated with this GameObject
	 */
	public Sprite getSprite () {
		return animationHandler.getImage ();
	}
	
	/**
	 * Gets the value associated with the given variant name from this GameObject's variant.
	 * @param attributeName The name of the attribute
	 * @return The value of the attribute; null if not found
	 */
	public String getVariantAttribute (String attributeName) {
		if (variant != null) {
			return variant.getAttribute (attributeName);
		}
		return null;
	}
	
	/**
	 * Returns this GameObject's hitbox. Constructs a new Rectangle object each call.
	 * @return A Rectangle object representing this GameObject's hitbox
	 */
	public Rectangle hitbox () {
		if (hitboxWidth == 0 || hitboxHeight == 0 || !hasHitbox) {
			return null;
		}
		return new Rectangle ((int)x + (int)hitboxXOffset, (int)y + (int)hitboxYOffset, (int)hitboxWidth, (int)hitboxHeight);
	}
	
	/**
	 * Gets the variant object representing this GameObject's variant.
	 * @return This GameObject's variant
	 */
	public Variant variant () {
		return variant;
	}
	
	/**
	 * Gets the AnimationHandler object associated with this GameObject.
	 * @return This GameObject's AnimationHandler
	 */
	public AnimationHandler getAnimationHandler () {
		return animationHandler;
	}
	
	/**
	 * Gets the render priority for this GameObject
	 * @return the render priority; e.g. the rendering order
	 */
	public double getRenderPriority () {
		return renderPriority;
	}
	
	/**
	 * Gets the game logic evaluation priority for this GameObject
	 * @return figure it out dumbass 2 electric boogaloo
	 */
	public double getGameLogicPriority () {
		return gameLogicPriority;
	}

	/**
	 * Returns the persistence of this GameObject as a boolean
	 * @return if true, this object will persist through room transitions; returns false otherwise
	 */
	public boolean isPersistent () {
		return persistent;
	}
	
	public void desyncSpriteX(int offset) {
		spriteX = x + offset;
	}
	public void desyncSpriteY(int offset) {
		spriteY = y + offset;
	}
	/**
	 * Updates the x component of this GameObject's position.
	 * @param val The new value to use
	 */
	public void setX (double val) {
		xprevious = x;
		spriteX =  (spriteX + (val - x));
		x = val;
	}
	public double getSpriteX() {
		return spriteX;
	}
	public double getSpriteY() {
		return spriteY;
	}
	//simalar to setX but will abort if this will cause it to go it the wall
	//will return false if it aborts
	public boolean goX(double val) {
		xprevious = x;
		spriteX =  (spriteX + (val - x));
		x = val;
		if (Room.isColliding(this)) {
			x = xprevious;
			spriteX = (spriteX - (val- x));
			return false;
		} else {
			return true;
		}
	}
	//used for testing purposes moves x and y and aborts
	public boolean goXandY(double xval, double yval) {
		xprevious = x;
		spriteX =  (spriteX + (xval - x));
		x = xval;
		yprevious = y;
		spriteY =  (spriteY + (yval - y));
		y = yval;
		if (Room.isColliding(this)) {
			x = xprevious;
			spriteX = (spriteX - (xval- x));
			y = yprevious;
			spriteY = (spriteY - (yval - y));
			return false;
		} else {
			return true;
		}
	}
	//simalar to setY but will abort if this will cause it to go it the celling or floor
	//will return false if it aborts
	public boolean goY(double val) {
		yprevious = y;
		spriteY =  (spriteY + (val - y));
		y = val;
		if (Room.isColliding(this)) {
			y = yprevious;
			spriteY = (spriteY - (val - y));
			return false;
		} else {
			return true;
		}
	}
	/**
	 * returns true if you could go to that x posision without being in a wall
	 * probably will conflict with a problamatic y positon use checkXAndY to avoid that
	 * @param val the x posion to test
	 * @return true if that ya dummy
	 */
	public boolean checkX(double val) {
		xprevious = x;
		spriteX =  (spriteX + (val - x));
		x = val;
		if (Room.isColliding(this)) {
			x = xprevious;
			spriteX = (spriteX - (val- x));
			return false;
		} else {
			x = xprevious;
			spriteX = (spriteX - (val- x));
			return true;
		}
	}

	/**
	 * returns true if you could go to that y posision without being in a wall
	 *  probably will conflict with a problamatic x positon use checkXAndY to avoid that
	 * @param val the y posion to test
	 * @return true if that ya dummy
	 */
	public boolean checkY(double val) {
		yprevious = y;
		spriteY =  (spriteY + (val - y));
		y = val;
		if (Room.isColliding(this)) {
			y = yprevious;
			spriteY = (spriteY - (val - y));
			return false;
		} else {
			y = yprevious;
			spriteY = (spriteY - (val - y));
			return true;
		}
	}
	/**
	 * returns true if you could go to that  posision without being in a wall
	 * @param xval the x position to test
	 * @param yval the y position to test
	 * @return true if that ya dummy
	 */
	public boolean checkXandY(double xval, double yval) {
		xprevious = x;
		spriteX =  (spriteX + (xval - x));
		x = xval;
		yprevious = y;
		spriteY =  (spriteY + (yval - y));
		y = yval;
		if (Room.isColliding(this)) {
			x = xprevious;
			spriteX = (spriteX - (xval- x));
			y = yprevious;
			spriteY = (spriteY - (yval - y));
			return false;
		} else {
			x = xprevious;
			spriteX = (spriteX - (xval- x));
			y = yprevious;
			spriteY = (spriteY - (yval - y));
			return true;
		}
	}
	/**
	 * Updates the y component of this GameObject's position.
	 * @param val The new value to use
	 */
	public void setY (double val) {
		yprevious = y;
		spriteY =  (spriteY + (val - y));
		y = val;
	}
	
	/**
	 * Sets the sprite of this GameObject to the given sprite.
	 * @param sprite The sprite to use
	 */
	public void setSprite (Sprite sprite) {
		animationHandler.resetImage (sprite);
	}
	//changes the hitbox to another one when the sprite gets bigger
	//based off of length of xOffset array
	public void createExpandingHitBox (int [] xoffsetArray, int [] widthArray, int [] yOffsetArray, int [] heightArray) {
		for (int i = 0; i < xoffsetArray.length; i++ ) {
			if (i == this.getAnimationHandler().getFrame()) {
				this.setHitboxAttributes(xoffsetArray [i], yOffsetArray [i], widthArray [i], heightArray [i]);
			}
		}
		
	}
	//same as createExpandingHitBox execept it uses the frame of a diffrent object as a reference
	public void createExpandingHitBoxBasedOnADiffrentObject (int [] xoffsetArray, int [] widthArray, int [] yOffsetArray, int [] heightArray, GameObject reference) {
		for (int i = 0; i < xoffsetArray.length; i++ ) {
			if (i == reference.getAnimationHandler().getFrame()) {
				this.setHitboxAttributes(xoffsetArray [i], yOffsetArray [i], widthArray [i], heightArray [i]);
			}
		}
		
	}
	/**
	 * Sets the given variant attribute to the specified value.
	 * @param name The name of the attribute
	 * @param value The new value to assign
	 */
	public void setVariantAttribute (String name, String value) {
		if (variant == null) {
			variant = new Variant ();
		}
		variant.setAttribute (name, value);
	}
	// returns true it the gameObject is stuck in the floor
	public boolean checkIfStuckInFloor(int verticalSpeed) {
		if ((Room.isColliding(this) && this.checkIfColidingWithWall(1))){
			this.setY(getY() - (verticalSpeed +1));
			if (!Room.isColliding(this)){
				this.setY(getY() + (verticalSpeed +1) );
				return true;
			}
			this.setY(getY() + verticalSpeed + 1);
			}
		return false;
	}
	//returns true if the GameObject is stuck in the celing
	public boolean checkIfStuckInCeling(double d) {
		if ((Room.isColliding(this) && this.checkIfColidingWithWall(1))){
			this.setY(getY() + (d +1));
			if (!Room.isColliding(this)){
				this.setY(getY() - (d +1) );
				return true;
			}
			this.setY(getY() - d + 1);
			}
		return false;
	}
	//returns true if the GameObject is coliding with a wall
		public boolean checkIfColidingWithWall(double d) {
			if ((Room.isColliding(this))){
				this.setX(getX() - d);
				if (!Room.isColliding(this)){
					this.setX(getX() + d);
					return true;
				}
				this.setX(getX() + d);
				}
			if (Room.isColliding(this)){
				this.setX(getX() + d);
				if (!Room.isColliding(this)){
					this.setX(getX() - d);
					return true;
				}
				this.setX(getX() - d);
				}
			return false;
		}
		//returns true if your colliding on the right false if on the left ONLY WORKS IF YOUR ONE PIXEL INTO THE WALL
		//will probably retun false if its not coliding except in some rare cases
		public boolean checkWitchWallYourCollidingWith () {
			this.setX(this.getX() + 1);
			if (!Room.isColliding(this)) {
				this.setX(this.getX() - 1);
				return false;
			} else {
				this.setX(this.getX() -1);
				return true;
			}
		}
		/**
		 * flashes in and out a bit before despawning
		 */
		public void despawnAllCoolLike(int timeTillEnd) {
			despawnTimer = despawnTimer + 1;
			if (despawnTimer % (timeTillEnd/despawnTimer) == 0) {
				this.visible = !this.visible;
			}
			if (despawnTimer == timeTillEnd) {
				this.forget();
		}
	}
	/**
	 * Sets the variants specified in attributeData to the respective values.
	 * @param attributeData The attributes as a formatted String, as specified by the setAttributes method in the class GameObject.Variant
	 */
	public void setVariantAttributes (String attributeData) {
		if (variant == null) {
			variant = new Variant (attributeData);
		} else {
			variant.setAttributes (attributeData);
		}
	}
	
	public void setHitboxAttributes (int xOffset, int yOffset, int width, int height) {
		hitboxXOffset = xOffset;
		hitboxYOffset = yOffset;
		hitboxWidth = width;
		hitboxHeight = height;
	}
	
	/**
	 * Sets the drawing order of this GameObject
	 * @param renderPriority the priority to use
	 */
	public void setRenderPriority (double renderPriority) {
		this.renderPriority = renderPriority;
	}
	
	/**
	 * Hell no I'm not javadocing this
	 * @param gameLogicPriority yeet
	 */
	public void setGameLogicPriority (double gameLogicPriority) {
		this.gameLogicPriority = gameLogicPriority;
	}
	
	/**
	 * Sets the persistence of this GameObject
	 * @param persistent this object will persist through room transitions if true; not if false
	 */
	public void setPersistence (boolean persistent) {
		this.persistent = persistent;
	}
	
	public void hide () {
		this.visible = false;
		this.getAnimationHandler().hide();
	}
	public void show () {
		this.visible = true;
		this.getAnimationHandler().show();
	}
}
