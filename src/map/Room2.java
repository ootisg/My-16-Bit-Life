package map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import gameObjects.TemporaryWall;
import main.GameAPI;
import main.GameObject;
import main.ObjectHandler;
import resources.Sprite;
import resources.SpriteParser;

public class Room2 {
	
	private static ArrayList <TileData> dataList;  // sorted by numeric ID
	private static HashMap <String,TileData> nameList; //indexed by tile name
	private static ArrayList <BufferedImage> tileIcons; //yeah? (sorted by numeric ID)
	
	
	private static int [][][] tileData; //all tiles listed by numeric ID
	private static ArrayList <Background> backgrounds;// all backgrounds sorted by layers
	
	private static ArrayList <TileEntitiy> tileEntitiys; //objects that contain code related to certain tiles
	private static HashMap <Long, TileEntitiy> positionToEntitiys;
	
	private static MapChungus [][] mapChungi; 
	
	private static int scrollX;
	private static int scrollY;
	
	private static byte [] inData;
	private static int readPos;
	
	private static int mapWidth;
	private static int mapHeight;
	private static int numLayers;
	
	private static int collisionLayer = 0;
	
	private static int chungusWidth = 20;
	private static int chungusHeight = 15;
	
	private static double gravity = .65625;
	
	private static boolean isLoaded = false;
	
	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;
	public static final int SPECIAL_TILE_ID = -1;
	public Room2 () {
		//Non-electric boogaloo
	}
	public static boolean isColliding (double x1, double y1, double x2, double y2) {
		return false;
	}
	
	public static double[] getCollidingCoords (double x1, double y1, double x2, double y2) {
		return null;
	}
	
	public static void setTileBuffer (double x1, double y1, double x2, double y2) {
		
	}
	/**
	 * checks to see if the given object is colliding with a solid tile
	 * @param obj the object to check
	 * @return wheather or not its touching a solid tile
	 */
	public static boolean isColliding (GameObject obj) {
		Rectangle hitbox = obj.hitbox();
		int startX = Math.max(hitbox.x/TILE_WIDTH,0);
		int startY = Math.max(hitbox.y/TILE_HEIGHT,0);
		int endX = Math.min((hitbox.x + hitbox.width)/TILE_WIDTH,mapWidth-1);
		int endY = Math.min((hitbox.y + hitbox.height)/TILE_HEIGHT,mapHeight-1);
		boolean foundCollision = false;
		for (int wx = startX; wx <= endX; wx++ ){
			for (int wy = startY; wy <= endY; wy++) {
				int index = tileData[collisionLayer][wx][wy];
				
				if (index == SPECIAL_TILE_ID) {
					long pos = toPackedLong (wx,wy);
					foundCollision = positionToEntitiys.get(pos).onCollision(obj);
				} else if (dataList.get(index).isSolid()) {
					foundCollision = true;
				}
			}
		}
		return foundCollision;
	}
	private static long toPackedLong (int x, int y) {
		long a = x;
		long b = y;
		a |= b << 32;
		return a;
	}
	public static boolean isColliding (GameObject obj, String tileId) {
		Rectangle hitbox = obj.hitbox();
		int startX = Math.max(hitbox.x/TILE_WIDTH,0);
		int startY = Math.max(hitbox.y/TILE_HEIGHT,0);
		int endX = Math.min((hitbox.x + hitbox.width)/TILE_WIDTH,mapWidth-1);
		int endY = Math.min((hitbox.y + hitbox.height)/TILE_HEIGHT,mapHeight-1);
		boolean foundCollision = false;
		for (int wx = startX; wx <= endX; wx++ ){
			for (int wy = startY; wy <= endY; wy++) {
				int index = tileData[collisionLayer][wx][wy];
				if (index == SPECIAL_TILE_ID) {
					long pos = toPackedLong (wx,wy);
					if (positionToEntitiys.get(pos).getData().getName().equals(tileId)) {
					foundCollision = positionToEntitiys.get(pos).onCollision(obj);
					}
				} else if (dataList.get(index).getName().equals(tileId)) {
					foundCollision= true;
				}
			}
		}
		return foundCollision;
	}
	
	public static MapTile[] getCollidingTiles (GameObject obj) {
		Rectangle hitbox = obj.hitbox();
		ArrayList<MapTile> working =new ArrayList<MapTile>();
		int startX = Math.max(hitbox.x/TILE_WIDTH,0);
		int startY = Math.max(hitbox.y/TILE_HEIGHT,0);
		int endX = Math.min((hitbox.x + hitbox.width)/TILE_WIDTH,mapWidth-1);
		int endY = Math.min((hitbox.y + hitbox.height)/TILE_HEIGHT,mapHeight-1);
		boolean foundCollision = false;
		for (int wx = startX; wx <= endX; wx++ ){
			for (int wy = startY; wy <= endY; wy++) {
				int index = tileData[collisionLayer][wx][wy];
				
				if (index == SPECIAL_TILE_ID) {
					long pos = toPackedLong (wx,wy);
					foundCollision = positionToEntitiys.get(pos).onCollision(obj);
					if (foundCollision) {
					working.add(new MapTile (positionToEntitiys.get(pos).getData(),wx,wy));	
					}
				} else if (dataList.get(index).isSolid()) {
					working.add(new MapTile (dataList.get(index),wx,wy));
				}
			}
		}
		return working.toArray(new MapTile[0]);
	}
	public static MapTile[] getCollidingTiles (GameObject obj, String tileName) {
		Rectangle hitbox = obj.hitbox();
		ArrayList<MapTile> working =new ArrayList<MapTile>();
		int startX = Math.max(hitbox.x/TILE_WIDTH,0);
		int startY = Math.max(hitbox.y/TILE_HEIGHT,0);
		int endX = Math.min((hitbox.x + hitbox.width)/TILE_WIDTH,mapWidth-1);
		int endY = Math.min((hitbox.y + hitbox.height)/TILE_HEIGHT,mapHeight-1);
		boolean foundCollision = false;
		for (int wx = startX; wx <= endX; wx++ ){
			for (int wy = startY; wy <= endY; wy++) {
				int index = tileData[collisionLayer][wx][wy];
				
				if (index == SPECIAL_TILE_ID) {
					long pos = toPackedLong (wx,wy);
					if (positionToEntitiys.get(pos).getData().getName().equals(tileName)) {
						foundCollision = positionToEntitiys.get(pos).onCollision(obj);
						working.add(new MapTile (positionToEntitiys.get(pos).getData(),wx,wy));	
					}
				} else if (dataList.get(index).getName().equals(tileName)) {
					working.add(new MapTile (dataList.get(index),wx,wy));
				}
			}
		}
		return working.toArray(new MapTile[0]);
	}
	
	/**
	 * returns true if the specified tile is a solid tile
	 * @param layer the layer the tile is on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return true if its solid like a rock or my mixtape
	 */
	public static boolean isSolid (int layer,int x, int y) {
		return dataList.get(tileData[layer][y][x]).isSolid();
	}
	/**
	 * returns true if the specified tile is a solid tile (false if the tile is not loaded)
	 * @param name the name of the specified tile
	 * @return true if its solid like a rock or my mixtape
	 */
	public static boolean isSolid (String name) {
		try {
		return nameList.get(name).isSolid();
		} catch (NullPointerException e) {
		return false;
		}
	}
	/**
	 * renders the map and associated chungi
	 */
	public static void render () {
		Rectangle viewport = new Rectangle (scrollX,scrollY,GameAPI.getWindow().getWidth(),GameAPI.getWindow().getHeight());
		for (int wy = 0; wy < mapChungi.length; wy++) {
			for (int wx = 0; wx < mapChungi[0].length; wx++) {
				MapChungus currentChungus = mapChungi[wy][wx];
				Rectangle chungtangle = new Rectangle (currentChungus.getX()*TILE_WIDTH,currentChungus.getY()*TILE_HEIGHT,chungusWidth*TILE_WIDTH,chungusHeight*TILE_HEIGHT);
				if (!viewport.contains(chungtangle)) {
					if (!currentChungus.isFree()) {
						currentChungus.freeImage();
					}
				} else {
					currentChungus.draw();
				}
			}
		}
	}
	/**
	 * loads the map file at the given filepath
	 * @param path the filepath to the map file
	 * @return wheater or not the loading was succesful
	 */
	public static boolean loadRoom (String path) {
		//START OF HEADER
		//Bytes 0-3: RMF# (# is version number)
		//Bytes 4-7: Map width, in tiles
		//Bytes 8-11: Map height, in tiles
		//Bytes 12-15: Number of layers
		//Bytes 16-19: Number of objects (placed)
		//END OF HEADER
		//Tileset list (background layers are excluded)
		//Object import list
		//Background list
		//Tiles
		//Object list (x, y, id, variant)
		LinkedList<LinkedList<GameObject>> objList = ObjectHandler.getChildrenByName("GameObject");
		for (int i = 0; i < objList.size (); i ++) { 
			if (objList.get (i) != null) {
				int listSize = objList.get (i).size ();
				for (int j = 0; j < listSize; j ++) {
					if (objList.get (i).get (j) != null) {
						objList.get (i).get (j).forget ();
					}
				}
			}
		}
		//Loads the RMF file at the given filepath
		File file = null;
		FileInputStream stream = null;
		file = new File (path);
		inData = new byte[(int) file.length ()];
		try {
			stream = new FileInputStream (file);
		} catch (FileNotFoundException e1) {
			return false;
		}
		try {
			stream.read (inData);
			stream.close ();
		} catch (IOException e) {
			return false;
		}
		readPos = 0;
		char MAX_VERSION = '1';
		
		//Check header
		String fmtString = getString (3);
		if (!fmtString.equals ("RMF")) {
			System.out.println("ERROR: not an RMF (raw map file)");
			System.out.println("you most likely used the wrong map editor");
		}
		
		String verString = getString (1);
		if (verString.charAt (0) < '1' || verString.charAt (0) > MAX_VERSION) {
			System.out.println("it apperas your map is an old version but if it worked anyway just ignore this message");
		}
		//Read attributes
				int mapWidth = getInteger (4);
				int mapHeight = getInteger (4);
				Room2.mapHeight = mapHeight;
				Room2.mapWidth = mapWidth;
				int numLayers = getInteger (4);
				Room2.numLayers = numLayers;
				int numObjects = getInteger (4);
		//Parse tile set list
		String tilesets = getString (';');
		String[] tilesetNameArray = tilesets.split (",");
	
		//Parse object list
		String objectString = getString (';');
		String [] objectList = objectString.split(",");
		
		//parse backgrounds
		String backgroundString = getString (';');
		String[] backgroundList = backgroundString.split (",");
		
		
		//import all tilesets
		for (int i= 0; i < tilesetNameArray.length; i++) {
			importTileset(tilesetNameArray[i]);
		}
		
		//import all backgrounds
		for(int i = 0; i < backgroundList.length; i++) {
			Background newBackground;
			if (!backgroundList[i].equals("_NULL")) {
				newBackground = new Background (new Sprite (backgroundList[i]));
				newBackground.setScrollRateHorizontal(Double.parseDouble(backgroundList[++i]));
				newBackground.setScrollRateVertiacal(Double.parseDouble(backgroundList[++i]));
				backgrounds.add(newBackground);
			} else {
				backgrounds.add(null);
			}
		}
		
		//importing tiles
		//TODO deal with tileEntitys
		int amountOfTiles = tileIcons.size();
		int tileByteCount = getByteCount(amountOfTiles);
		for (int wl = 0; wl < numLayers; wl++) {
			if (backgrounds.get(wl)== null) {
				for (int wy = 0; wy < mapWidth; wy++) {
					for (int wx = 0; wx < mapHeight; wx++) {
						tileData [wl][wy][wx] = getInteger(tileByteCount);
					}
				}
			}
		}
		//importing objects
		int widthByteCount = getByteCount(mapWidth);
		int heightByteCount = getByteCount(mapHeight);
		int objectByteCount = getByteCount(numObjects);
		for (int i = 0; i < numObjects; i++) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
			int x = getInteger (widthByteCount);
			int y = getInteger(heightByteCount);
			int object = getInteger(objectByteCount);
			GameObject objectToUse = ObjectHandler.getInstance(objectList[object]);
			objectToUse.declare(x*16, y*16);
		}

		//convert the map to a big number of map chungi
		int gridWidth = (int)Math.ceil((((double)mapWidth)/chungusWidth));
		int gridHidth = (int)Math.ceil((((double)mapHeight)/chungusHeight)); // short for width2
		mapChungi = new MapChungus [gridHidth][gridWidth];
		for (int wx = 0; wx < mapChungi[0].length; wx++) {
			for (int wy = 0; wy<mapChungi.length; wy++) {
				mapChungi[wy][wx] = new MapChungus(wx*chungusWidth,wy*chungusHeight);
			}
		}
		isLoaded = true;
		return false;
	}
	
	/**
	 * adds all of the tiles to the map from the given tileset
	 * @param path the path to the tileset
	 */
	public static void importTileset(String path) {
		//parses out the tiles from the tileset
		ArrayList <String> working = new ArrayList <String>();
		working.add("grid " + TILE_WIDTH + " " + TILE_HEIGHT);
		Sprite newSprite = new Sprite (path,new SpriteParser(working));
		int toAdd = newSprite.getFrameCount();
		for (int i = 0; i < toAdd; i++) {
			String [] fork = path.split("/"); // dude thats deep
			String [] spoon = fork[fork.length -1].split(".");
			String tileName = spoon[0] + "." + i;
			TileData current = new TileData (tileName);
			dataList.add(current);
			nameList.put(tileName, current);
			BufferedImage tile = newSprite.getFrame(i);
			tileIcons.add(tile);
		}
	}
	private static String getString (int length) {
		byte[] usedData = new byte[length];
		int endPos = readPos + length;
		int i = 0;
		while (readPos < endPos) {
			usedData [i] = inData [readPos];
			readPos ++;
			i ++;
		}
		return new String (usedData);
	}
	private static int getInteger (int bytes) {
		int total = 0;
		for (int i = 0; i < bytes; i ++) {
			int toRead = inData [readPos + i];
			if (toRead < 0) {
				toRead += 256;
			}
			total += (toRead << ((bytes - 1 - i) * 8));
		}
		readPos += bytes;
		return total;
	}
	private static String getString (char endChar) {
		int len = 0;
		int i = readPos;
		while (inData [i] != endChar) {
			len ++;
			i ++;
		}
		String str = getString (len);
		readPos ++;
		return str;
	}
	private static int getByteCount (int value) {
		int i = 0;
		while (value != 0) {
			value /= 256;
			i ++;
		}
		return i;
	}
	/**
	 * set the points visable on the screen to whatever
	 * @param x the x coordinate to set the veiw two 2
	 * @param y the y coordinate to set the veiw too
	 */
	public static void setView (int x, int y) {
		scrollX = x;
		scrollY = y;
	}
	
	/**
	 * returns what point of the map the leftmost edge of the screen is on
	 * @return the x coordinate of the viewport
	 */
	public static int getViewX () {
		return scrollX;
	}
	/**
	 * returns what point of the map the top edge of the screen is on
	 * @return the y coordinate of the viewport
	 */
	 public static int getViewY () {
		return scrollY;
	}
	/**
	 * returns the width of the map
	 * @return how THICC the map is
	 */
	public static int getWidth () {
		return mapWidth;
	}
	/**
	 * returns the height of the map
	 * @return how high the map is (420!)
	 */
	public static int getHeight () {
		return mapHeight;
	}
	
	/**
	 * returns the info about a specific type of tile
	 * @param tileName the name of the tile you want info about
	 * @return info about the tile if the tile is currently loaded null otherwise
	 */
	public static TileData getTileProperties(String tileName) {
		return nameList.get(tileName);
	}
	/**
	 * returns the info about a specific type of tile
	 * @param layer the layer the tile is on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return info about the tile specified
	 */
	public static TileData getTileProperties(int layer, int x, int y) {
		return dataList.get(tileData[layer][y][x]);
	}
	/**
	 * returns the name of a speicfic tile
	 * @param layer the layer the tile is on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return name of the tile specified
	 */
	public static String getTileName (int layer, int x, int y) {
		return dataList.get(tileData[layer][y][x]).getName();
	}
	/**
	 * returns a list of the backgrounds
	 * @return duh
	 */
	public static ArrayList<Background> getBackgroundList () {
		return backgrounds;
	}
	/**
	 * gets the gravity (in pixels/frame/frame)
	 * @return how fast things go down dude
	 */
	public static double getGravity () {
		return gravity;
	}
	
	/**
	 * sets the gravity (whoa why havent scientists done this yet)
	 * @param grav the gravity to set it too
	 */
	public static void setGravity (double grav) {
		gravity = grav;
	}
	/**
	 * returns true if the tile is within the confines of the map
	 * @param x the xpos of the tile
	 * @param y the y position of the tile
	 * @return true if the tile is in the map: false otherwise
	 */
	public static boolean tileInBounds (int x, int y) {
		return !(x> mapWidth|| y > mapHeight);
	}
	/**
	 * returns true if the map is makin bank
	 * @return true if the map is rich 
	 */
	public static boolean isLoaded () {
		return isLoaded;
	}
	//its a map chunk ... a big map chunk
	/**
	 * a portion of the map
	 * @author GOD 
	 *(god made map chungus)
	 */
	private static class MapChungus {
		ArrayList <BufferedImage> renderedImages; // the compressed images sorted by layer (and each layer contains all layers that wont cause problems)
		// if you don't understand this anymore your stupid
		ArrayList <Boolean> valid; // states wheather each of the images is valid
		
		int x;
		int y;
		
		ArrayList <TileEntitiy> tilelist;
		ArrayList<Integer> layerClassfications; // gives the index for the layers bufferedimage
		
		
		
		boolean incompleate = false;
		int width = chungusWidth;
		int height = chungusHeight;
		/**
		 * makes the map chungus
		 * @param x the x coordinate of the map chungus (in tiles)
		 * @param y the y coordinate of the map chungus (in tiles)
		 */
		public MapChungus (int x, int y) {
			this.x = x;
			this.y = y;
			if (x + chungusWidth > mapWidth || y + chungusHeight > mapHeight) {
				incompleate =true;
				width = x + chungusWidth - mapWidth;
				height = y + chungusHeight - mapHeight;
			}
			int mappedLayerIndex = 0;
			for (int i = 0; i < numLayers; i++) {
				if (isSpecialLayer(i)) {
					if (i!= 0) {
					valid.add(false);
					renderedImages.add(new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR));
					mappedLayerIndex = mappedLayerIndex +1;
					}
					layerClassfications.add(mappedLayerIndex);
					if (i != numLayers) {
						valid.add(false);
						renderedImages.add(new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR));
					}
					mappedLayerIndex = mappedLayerIndex +1;
				} else {
					layerClassfications.add(mappedLayerIndex);
				}
			}
			valid.add(false);
			renderedImages.add(new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR));
			
		}
		
		/**
		 * returns whearer or not this layer can be rendered with all the rest
		 * @return whearer or not this layer can be rendered with all the rest
		 */
		public boolean isSpecialLayer (int layerNum) {
			if (backgrounds.get(layerNum) != null) {
				if (backgrounds.get(layerNum).getScrollRateHorizontal() != 1 || backgrounds.get(layerNum).getScrollRateVertical() != 1 || backgrounds.get(layerNum).getImage().getFrameCount() > 1){
					return true;
				}
			} else {
				for (int wx = 0; wx < width; wx++) {
					for (int wy = 0; wy < height; wy++) {
						if (tileData[layerNum][wy + y][wx + x] == SPECIAL_TILE_ID) {
							return true;
						}
					}
				}
			}
			return false;
		}
		
		/**
		 * destroyes the pre rendered image when called
		 */
		public void freeImage() {
			for (int i = 0; i < renderedImages.size(); i++) {
				renderedImages.set(i, null);
			}
		}
		
		/**
		 * returns true if the image is not pre rendered
		 * @return true if the image is not pre rendered
		 */
		public boolean isFree () {
			if (renderedImages.get(0) == null) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * adds a special tile to the tiles rendered by this map chungus
		 * @param entity the entity to add
		 */
		public void addTileEntity (TileEntitiy entity) {
			tilelist.add(entity);
		}
		
		/**
		 * tells the computer that this render of the map chungus is no longer correct
		 */
		public void invalidate (int layer) {
			valid.set(layerClassfications.get(layer), false);
		}
		
		/**
		 * returns wheather or not the map chungus is still valid
		 * @return wheather or not the map chungus is still valid
		 */
		public boolean isValid (int layer){
			return valid.get(layerClassfications.get(layer));	
		}
		
		/**
		 * gets the x value of the map chungi (in tiles)
		 * @return that 
		 */
		public int getX() {
			return x;
		}
		
		/**
		 * gets the y value of the map chungi (in tiles)
		 * @return that 
		 */
		public int getY() {
			return y;
		}
		
		/**
		 * draws the map chungus (and any special entitys that are associated with it)
		 */
		public void draw () {
				
				for (int l = 0; l <renderedImages.size(); l++) {
					if (!this.isValid(l)) {
						Graphics g = renderedImages.get(l).getGraphics();
						if (backgrounds.get(l) == null) {
						for (int wx = 0; wx < width; wx++) {
							for (int wy = 0; wy < height; wy++) {
								g.drawImage(tileIcons.get(tileData[l][wy + y][wx + x]),wy*16,wx*16,null);
							}
						}
						} else {
							//TODO get this to work with animated backgrounds
							BufferedImage working= backgrounds.get(l).getImage().getFrame(0).getSubimage(x*16, y*16, chungusWidth*16, chungusHeight*16);
							g.drawImage(working,0,0,null);
						}
						valid.set(layerClassfications.get(l), true);
					} 
				}
				
			for (int i = 0; i < renderedImages.size(); i++) {
				Graphics working = GameAPI.getWindow().getBufferGraphics();
				working.drawImage(renderedImages.get(i),x      *16 - scrollX, y*16 - scrollY,null);
			}
		}
		
	}
}