package map;

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
	
	static ArrayList <TileData> dataList;  // sorted by numeric ID
	static HashMap <String,TileData> nameList; //indexed by tile name
	static ArrayList <BufferedImage> tileIcons; //yeah? (sorted by numeric ID)
	
	
	static int [][][] tileData; //all tiles listed by numeric ID
	static ArrayList <Background> backgrounds;// all backgrounds sorted by layers
	
	static ArrayList <TileEntitiy> tileEntitiys; //objects that contain code related to certain tiles
	
	
	static byte [] inData;
	static int readPos;
	
	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;
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
	
	public static double snap16 (double num, int direction) {
		return Double.NaN;
	}
	
	public static boolean isColliding (Rectangle hitbox) {
		return false;
	}
	
	public static boolean isColliding (Rectangle hitbox, String tileId) {
		return false;
	}
	
	public static boolean isColliding (Rectangle hitbox, double xTo, double yTo) {
		return false;
	}
	
	public static double[] getCollidingCoords (Rectangle hitbox, double xTo, double yTo) {
		return null;
	}
	
	public static double[] doHitboxVectorCollison (Rectangle hitbox, double xTo, double yTo) {
		return null;
	}
	
	public static MapTile[] getCollidingTiles (Rectangle hitbox) {
		return null;
	}
	
	public static MapTile[] getCollidingTiles (Rectangle hitbox, String tileId) {
		return null;
	}
	
	public static short getTileId (int x, int y) {
		return -1;
	}
	
	public static boolean isSolid (int x, int y) {
		return false;
	}
	
	public static String getTileIdString (int x, int y) {
		return null;
	}
	
	public static void frameEvent () {
		
	}
	
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
				int numLayers = getInteger (4);
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
	public static int numBits (int num) {
		return -1;
	}
	
	public static void setView (int x, int y) {
		
	}
	
	public static int getViewX () {
		return -1;
	}
	
	public static int getViewY () {
		return -1;
	}
	
	public static int getWidth () {
		return -1;
	}
	
	public static int getHeight () {
		return -1;
	}
	
	public static int bind (int value, int min, int max) {
		return -1;
	}
	
	public static boolean isBetween (double num, double bound1, double bound2) {
		return false;
	}
	
	public static ArrayList<Background> getBackgroundList () {
		return null;
	}
	
	public static double getGravity () {
		return Double.NaN;
	}
	
	public static void setGravity (double grav) {
		
	}
	
	public static TileAttributesList getTileAttributesList () {
		return null;
	}
	
	public static void setTileAttributesList (TileAttributesList list) {
		
	}
	
	public static boolean tileInBounds (int x, int y) {
		return false;
	}
	
	public static boolean isLoaded () {
		return false;
	}
}