package resources;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;


import main.RenderLoop;

/**
 * Represents a drawable image
 * @author nathan
 *
 */
public class Sprite {
	/**
	 * The global image cache
	 */
	private static HashMap<String, CacheNode> cache = new HashMap<String, CacheNode> ();
	/**
	 * The image data this sprite contains
	 */
	private BufferedImage[] images;
	/**
	 * The filepath to this sprite, if applicable
	 */
	private String imagePath;
	/**
	 * The filepath to the parsing parameters, if applicable
	 */
	private String parsePath;
	/**
	 * Whether the sprite is animated or not
	 */
	private boolean isAnimated;
	
	/**
	 * 
	 * Constructs a sprite with the given image filepath and parsing parameter filepath.
	 * @param imagepath The filepath to the image to use
	 * @param parsepath The filepath to the parsing parameters to use
	 */
	public Sprite (String imagepath, String parsepath) {
		this.imagePath = imagepath;
		this.parsePath = parsepath;
		String key = imagepath + ":" + parsepath;
		CacheNode data = cache.get (key);
		if (data == null) {
			SpriteParser parser = new SpriteParser (parsepath);
			File imageFile = new File (imagepath);
			BufferedImage img = null;
			try {
				img = ImageIO.read (imageFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			images = parser.parse (img);
			cache.put (key, new CacheNode (key, images));
		} else {
			images = data.getData ();
		}
		if (images.length > 1) {
			isAnimated = true;
		} else {
			isAnimated = false;
		}
	}
	
	/**
	 * Constructs a sprite with the given image and parsing parameter filepath. Does not support caching.
	 * @param image The image to use
	 * @param parsepath The filepath to the parsing parameters to use
	 */
	public Sprite (BufferedImage image, String parsepath) {
		this.parsePath = parsepath;
		SpriteParser parser = new SpriteParser (parsepath);
		images = parser.parse (image);
		if (images.length > 1) {
			isAnimated = true;
		} else {
			isAnimated = false;
		}
	}
	
	/**
	 * Constructs a sprite with the given image and parser. Does not support caching.
	 * @param image The image to use
	 * @param parser The parser to use
	 */
	public Sprite (BufferedImage image, SpriteParser parser) {
		images = parser.parse (image);
		if (images.length > 1) {
			isAnimated = true;
		} else {
			isAnimated = false;
		}
	}
	/**
	 * Constructs a sprite with the given image path and parser. Does not support caching.
	 * @param imagepath The image to use
	 * @param parser The parser to use
	 */
	public Sprite (String imagepath, SpriteParser parser) {
		this.imagePath = imagepath;
		File imageFile = new File (imagepath);
		BufferedImage img = null;
		try {
			img = ImageIO.read (imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		images = parser.parse (img);
		if (images.length > 1) {
			isAnimated = true;
		} else {
			isAnimated = false;
		}
	}
	
	/**
	 * Constructs a sprite with the given image filepath--or uses only the filepath of the parser if given a .txt filepath.
	 * @param imagepath The filepath to use for the image
	 */
	public Sprite (String imagepath) {
		String[] splitPath = imagepath.split ("\\.");
		if (splitPath.length != 0 && splitPath [splitPath.length - 1].equals ("txt")) {
			parsePath = imagepath;
			images = new SpriteParser (imagepath).parse ();
			if (images.length > 1) {
				isAnimated = true;
			} else {
				isAnimated = false;
			}
		} else {
			this.imagePath = imagepath;
			CacheNode data = cache.get (imagepath);
			if (data == null) {
				File imageFile = new File (imagepath);
				BufferedImage img = null;
				try {
					img = ImageIO.read (imageFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				images = new BufferedImage[] {img};
				cache.put (imagepath, new CacheNode (imagepath, images));
			} else {
				images = data.getData ();
			}
			isAnimated = false;
		}
	}
	
	/**
	 * Copy constructor for ease of inheretence
	 * @param sprite The Sprite object to copy
	 */
	public Sprite (Sprite sprite) {
		this.images = sprite.images;
		this.isAnimated = sprite.isAnimated;
		this.imagePath = sprite.imagePath;
		this.parsePath = sprite.parsePath;
	}
	
	/**
	 * Gets the frame count of this sprite.
	 * @return The number of frames in this sprite
	 */
	public int getFrameCount () {
		return images.length;
	}
	
	/**
	 * Constructs a sprite with the given image. Does not support caching.
	 * @param image The image to use
	 */
	public Sprite (BufferedImage image) {
		images = new BufferedImage[] {image};
	}
	
	/**
	 * Draws the first frame of this sprite at the given x and y coordinates.
	 * @param usedX The x coordinate to draw this sprite at
	 * @param usedY The y coordinate to draw this sprite at
	 */
	public void draw (int usedX, int usedY) {
		draw (usedX, usedY, 0);
	}
	
	/**
	 * Draws the given frame of this sprite at the given x and y coordinates.
	 * @param usedX The x coordinate to draw this sprite at
	 * @param usedY The y coordinate to draw this sprite at
	 * @param frame The frame of this sprite to draw
	 */
	public void draw (int usedX, int usedY, int frame) {
		if (frame < images.length) {
			RenderLoop.window.getBufferGraphics ().drawImage (images [frame], usedX, usedY, null);
		}
	}
	
	/**
	 * Draws the given frame of this sprite at the given x and y coordinates.
	 * @param usedX The x coordinate to draw this sprite at
	 * @param usedY The y coordinate to draw this sprite at
	 * @param flipHorizontal whether to apply horizontal flip
	 * @param flipVertical whether to apply vertical flip
	 * @param frame The frame of this sprite to draw
	 */
	public void draw (int usedX, int usedY, boolean flipHorizontal, boolean flipVertical, int frame) {
		//Yeaaaaaaah this doesn't actually do anything special right now. TODO
		int x1, x2, y1, y2;
		if (flipHorizontal) {
			x1 = getFrame (frame).getWidth ();
			x2 = 0;
		} else {
			x1 = 0;
			x2 = getFrame (frame).getWidth ();
		}
		if (flipVertical) {
			y1 = getFrame (frame).getHeight ();
			y2 = 0;
		} else {
			y1 = 0;
			y2 = getFrame (frame).getHeight ();
		}
		if (frame < images.length) {
			RenderLoop.window.getBufferGraphics ().drawImage (getFrame (frame), usedX, usedY, usedX + getFrame (frame).getWidth (), usedY + getFrame (frame).getHeight (), x1, y1, x2, y2, null);
		}
	}
	
	/**
	 * Gets the BufferedImage representing the given frame of the sprite.
	 * @param frame The frame to get
	 * @return the given frame
	 */
	public BufferedImage getFrame (int frame) {
		return images [frame];
	}
	
	/**
	 * Gets the filepath of the image used to create this sprite.
	 * @return the filepath of this sprite's image; returns null if not applicable
	 */
	public String getImagePath () {
		return imagePath;
	}
	
	/**
	 * Gets the filepath of the file used to parse this sprite.
	 * @return the filepath used to parse this sprite; returns null if not applicable
	 */
	public String getParsePath () {
		return parsePath;
	}
	
	/**
	 * Gets the BufferedImage associated with the given filepath.
	 * @param path the filepath to use
	 * @return the resulting image; null if no image is found
	 */
	public static BufferedImage getImage (String path) {
		CacheNode data = cache.get (path);
		if (data == null) {
			File imageFile = new File (path);
			BufferedImage img = null;
			try {
				img = ImageIO.read (imageFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedImage[] currentImg = new BufferedImage[] {img};
			cache.put (path, new CacheNode (path, currentImg));
			return currentImg [0];
		} else {
			return data.getData () [0];
		}
	}
	
	private static class CacheNode {
		
		/**
		 * The number of times the data in this node has been accessed
		 */
		private int accessCount;
		/**
		 * The images stored in this node
		 */
		private BufferedImage[] data;
		/**
		 * The key associated with this node
		 */
		private String key;
		
		/**
		 * Construct a new CacheNode indexed by the given key, holding the given data.
		 * @param key The key to index this node
		 * @param data The data to store in this node
		 */
		public CacheNode (String key, BufferedImage[] data) {
			accessCount = 0;
			this.key = key;
			this.data = data;
			cache.put (key, this);
		}
		
		/**
		 * Gets the key value associated with this node.
		 * @return This node's key
		 */
		public String getKey () {
			return key;
		}
		
		/**
		 * Gets the data stored by this node.
		 * @return This node's data
		 */
		public BufferedImage[] getData () {
			accessCount ++;
			return data;
		}
		
		/**
		 * Gets the number of times getData has been called on this node.
		 * @return The number of calls to getData
		 */
		public int getAccessCount () {
			return accessCount;
		}
		
		/**
		 * Removes this element from the cache.
		 * @return True if the element was removed; false oftherwise
		 */
		public boolean remove () {
			return cache.remove (key, this);
		}
	}
}
