package map;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Stack;

import gameObjects.TemporaryWall;
import main.GameWindow;
import main.GameObject;
import main.GameAPI;
import main.GameLoop;
import resources.Sprite;
import resources.SpriteParser;

public class Room {
	private static Sprite[] tileList;
	private static String[] tileIdList;
	private static String[] objectList;
	private static short[][][] tileData = new short[1][32][32];
	private static boolean[] collisionData;
	private static double gravity = .65625;
	private static int levelWidth = 32;
	private static int levelHeight = 32;
	private static int viewX = 0;
	private static int viewY = 0;
	private static int readBit = 0;
	private static byte[] inData;
	private static final double[] hitboxCorners = new double[] {0, 0, 1, 0, 1, 1, 0, 1, 0, 0};
	private static TileAttributesList tileAttributesList = new TileAttributesList (MapConstants.tileList);
	private static ArrayList<Background> backgroundList = new ArrayList<Background> ();
	public static TileBuffer tileBuffer = new TileBuffer ();
	
	private static boolean loaded;
	
	public Room () {
		//A fairly generic constructor
	}
	private static int readBits (int num) {
		//Reads a number of bits from the byte[] inData equal to num and returns them as an int
		int result = 0;
		int mask;
		while (num > 0) {
			if (num >= 8 - (readBit % 8)) {
				int numbits = 8 - (readBit % 8);
				mask = (1 << numbits) - 1;
				result = result + ((inData [readBit >> 3] & mask) << (num - numbits));
				num = num - numbits;
				readBit += numbits;
			} else {
				mask = ((1 << num) - 1) << (8 - (readBit % 8) - num);
				result = result + ((inData [readBit >> 3] & mask) >> (8 - (readBit % 8) - num));
				readBit += num;
				num = 0;
			}
		}
		return result;
	}
	public static boolean isColliding (double x1, double y1, double x2, double y2) {
		if (getCollidingCoords (x1, y1, x2, y2) != null) {
			return true;
		}
		return false;
	}
	public static double[] getCollidingCoords (double x1, double y1, double x2, double y2) {
		if (x1 < 0 || x1 > levelWidth * 16 || x2 < 0 || x2 > levelWidth * 16 || y1 < 0 || y1 > levelHeight * 16 || y2 < 0 || y2 > levelHeight * 16) {
			return null;
		}
		int xdir = 1;
		int ydir = 1;
		double xcheck1 = 0;
		double ycheck1 = 0;
		double xcheck2 = 0;
		double ycheck2 = 0;
		double xstep = x1;
		double ystep = y1;
		byte tileXOffset = 0;
		byte tileYOffset = 0;
		if (x1 > x2) {
			xdir = -1;
		}
		if (y1 > y2) {
			ydir = -1;
		}
		if (collisionData [getTileId ((int) x1 / 16, (int) y1 / 16)]) {
			return new double[] {x1, y1};
		}
		if (x1 == x2) {
			while (true) {
				tileYOffset = 0;
				ystep = snap16 (ystep, ydir);
				if (ydir == -1 && ystep % 16 == 0) {
					tileYOffset = -1;
				}
				if (!isBetween (ystep, y1, y2)) {
					return null;
				}
				if (collisionData [getTileId ((int) x1 / 16, (int) ystep / 16 + tileYOffset)]) {
					return new double[] {x1, ystep};
				}
			}
		}

		if (y1 == y2) {
			while (true) {
				tileXOffset = 0;
				xstep = snap16 (xstep, xdir);
				if (xdir == -1 && xstep % 16 == 0) {
					tileXOffset = -1;
				}
				if (!isBetween (xstep, x1, x2)) {
					return null;
				}
				if (collisionData [getTileId ((int) x1 / 16 + tileXOffset, (int) ystep / 16)]) {
					return new double[] {xstep, y1};
				}
			}
		}
		double m = (y1 - y2) / (x1 - x2);
		double b = y1 - m * x1;
		while (true) {
			tileXOffset = 0;
			tileYOffset = 0;
			xcheck1 = snap16 (xstep, xdir);
			ycheck1 = m * xcheck1 + b;
			ycheck2 = snap16 (ystep, ydir);
			xcheck2 = (ycheck2 - b) / m;
			if (Math.abs (x1 - xcheck1) > Math.abs (x1 - xcheck2)) {
				double temp = xcheck1;
				xcheck1 = xcheck2;
				xcheck2 = temp;
				temp = ycheck1;
				ycheck1 = ycheck2;
				ycheck2 = temp;
			}
			xstep = xcheck1;
			ystep = ycheck1;
			if (!isBetween (xstep, x1, x2) || !isBetween (ystep, y1, y2)) {
				return null;
			}
			if (xdir == -1 && xstep % 16 == 0) {
				tileXOffset = -1;
			}
			if (ydir == -1 && ystep % 16 == 0) {
				tileYOffset = -1;
			}
			if (collisionData [getTileId ((int) xstep / 16 + tileXOffset, (int) ystep / 16 + tileYOffset)]) {
				return new double[] {xstep, ystep};
			}
		}
	}
	public static void setTileBuffer (double x1, double y1, double x2, double y2) {
		int xdir = 1;
		int ydir = 1;
		byte tileXOffset = 0;
		byte tileYOffset = 0;
		//I may have runined this but I don't think I did
		if (x1 >= x2) {
	
		}
		if ( x1 % 16 == 0 && x1 >= x2) {
			tileXOffset = -1;
			xdir = -1;
		}
		if (y1 >= y2) {
			
		}
		if (y1 % 16 == 0 && y1 >= y2) {
			tileYOffset = -1;
			ydir = -1;
		}
		if (tileInBounds ((int)(x1 / 16 + tileXOffset), (int)(y1 / 16 + tileYOffset))) {
			if (collisionData [getTileId ((int)(x1 / 16 + tileXOffset), (int)(y1 / 16 + tileYOffset))] == true) {
				tileBuffer.enabled = true;
				tileBuffer.collisionX = x1;
				tileBuffer.collisionY = y1;
				tileBuffer.spriteUsed = tileList [getTileId ((int)(x1 / 16), (int)(y1 / 16))];
				tileBuffer.mapTile.tileId = tileIdList [getTileId ((int)(x1 / 16), (int)(y1 / 16))];
				tileBuffer.mapTile.x = (int) x1 / 16;
				tileBuffer.mapTile.y = (int) y1 / 16;
				return;
			}
		}
		if ((x1 < 0 && x2 < 0) || (x1 > levelWidth * 16 && x2 > levelWidth * 16) || (y1 < 0 && y2 < 0) || (y1 > levelWidth * 16 && y2 > levelWidth * 16)) {
			tileBuffer.enabled = false;
			return;
		} else {
			tileBuffer.enabled = true;
		}
		double xcheck1 = 0;
		double ycheck1 = 0;
		double xcheck2 = 0;
		double ycheck2 = 0;
		double xstep = x1;
		double ystep = y1;
		/*if (collisionData [getTileId ((int) x1 / 16, (int) y1 / 16)]) {
			tileBuffer.collisionX = x1;
			tileBuffer.collisionY = y2;
			tileBuffer.spriteUsed = tileList [getTileId ((int) x1 / 16, (int) y1 / 16)];
			return;
		}*/
		if (x1 == x2) {
			while (true) {
				tileYOffset = 0;
				ystep = snap16 (ystep, ydir);
				if (ydir == -1 && ystep % 16 == 0) {
					tileYOffset = -1;
				}
				if (!isBetween (ystep, y1, y2)) {
					tileBuffer.enabled = false;
					return;
				}
				int tileFinalX = (int) x1 / 16;
				int tileFinalY = (int) ystep / 16 + tileYOffset;
				if (x1 % 16 == 0) {
					if (tileFinalX <= 0 || tileFinalX >= levelWidth || tileFinalY < 0 || tileFinalY >= levelHeight) {
						tileBuffer.enabled = false;
						return;
					}
					if (collisionData [getTileId (tileFinalX, tileFinalY)] && collisionData [getTileId (tileFinalX - 1, tileFinalY)]) {
						System.out.print(tileFinalX);
						System.out.print(", ");
						System.out.println(tileFinalY);
						tileBuffer.collisionX = x1;
						tileBuffer.collisionY = y2;
						tileBuffer.spriteUsed = tileList [getTileId ((int) x1 / 16, (int) ystep / 16 + tileYOffset)];
						tileBuffer.mapTile.tileId = tileIdList [getTileId ((int) x1 / 16, (int) ystep / 16 + tileYOffset)];
						tileBuffer.mapTile.x = (int) x1 / 16;
						tileBuffer.mapTile.y = (int) ystep / 16 + tileYOffset;
						return;
					}
				} else {
					if (tileFinalX < 0 || tileFinalX >= levelWidth || tileFinalY < 0 || tileFinalY >= levelHeight) {
						tileBuffer.enabled = false;
						return;
					}
					if (collisionData [getTileId (tileFinalX, tileFinalY)]) {
						tileBuffer.collisionX = x1;
						tileBuffer.collisionY = y2;
						tileBuffer.spriteUsed = tileList [getTileId ((int) x1 / 16, (int) ystep / 16 + tileYOffset)];
						tileBuffer.mapTile.tileId = tileIdList [getTileId ((int) x1 / 16, (int) ystep / 16 + tileYOffset)];
						tileBuffer.mapTile.x = (int) x1 / 16;
						tileBuffer.mapTile.y = (int) ystep / 16 + tileYOffset;
						return;
					}
				}
			}
		}
		/*System.out.print (y1);
		System.out.print (", ");
		System.out.println (y2);*/
		if (y1 == y2) {
			while (true) {
			
				tileXOffset = 0;
				xstep = snap16 (xstep, xdir);
				if (xdir == -1 && xstep % 16 == 0) {
					tileXOffset = -1;
				}
				if (!isBetween (xstep, x1, x2)) {
					tileBuffer.enabled = false;
					return;
				}
				int tileFinalX = (int) x1 / 16 + tileXOffset;
				int tileFinalY = (int) ystep / 16;
				if (y1 % 16 == 0) {
					if (tileFinalX < 0 || tileFinalX >= levelWidth || tileFinalY <= 0 || tileFinalY >= levelHeight) {
						tileBuffer.enabled = false;
						return;
					}
					if (collisionData [getTileId (tileFinalX, tileFinalY)] && collisionData [getTileId (tileFinalX, tileFinalY - 1)]) {
						tileBuffer.collisionX = xstep;
						tileBuffer.collisionY = y1;
						tileBuffer.spriteUsed = tileList [getTileId ((int) x1 / 16 + tileXOffset, (int) ystep / 16)];
						tileBuffer.mapTile.tileId = tileIdList [getTileId ((int) x1 / 16 + tileXOffset, (int) ystep / 16)];
						tileBuffer.mapTile.x = (int) x1 / 16 + tileXOffset;
						tileBuffer.mapTile.y = (int) ystep / 16;
						return;
					}
				} else {
					if (tileFinalX < 0 || tileFinalX >= levelWidth || tileFinalY < 0 || tileFinalY >= levelHeight) {
						tileBuffer.enabled = false;
						return;
					}
					if (collisionData [getTileId (tileFinalX, tileFinalY)]) {
						tileBuffer.collisionX = xstep;
						tileBuffer.collisionY = y1;
						tileBuffer.spriteUsed = tileList [getTileId ((int) x1 / 16 + tileXOffset, (int) ystep / 16)];
						tileBuffer.mapTile.tileId = tileIdList [getTileId ((int) x1 / 16 + tileXOffset, (int) ystep / 16)];
						tileBuffer.mapTile.x = (int) x1 / 16 + tileXOffset;
						tileBuffer.mapTile.y = (int) ystep / 16;
						return;
					}
				}
			}
		}
		double m = (y1 - y2) / (x1 - x2);
		double b = y1 - m * x1;
		int time = 0;
		while (true) {
			time = time +1;
			tileXOffset = 0;
			tileYOffset = 0;
			xcheck1 = snap16 (xstep, xdir);
			ycheck1 = m * xcheck1 + b;
			ycheck2 = snap16 (ystep, ydir);
			xcheck2 = (ycheck2 - b) / m;
			if (Math.abs (x1 - xcheck1) > Math.abs (x1 - xcheck2)) {
				double temp = xcheck1;
				xcheck1 = xcheck2;
				xcheck2 = temp;
				temp = ycheck1;
				ycheck1 = ycheck2;
				ycheck2 = temp;
			}
			xstep = xcheck1;
			ystep = ycheck1;
			//MainLoop.GameAPI.getWindow ().getBuffer ().fillRect ((int)xstep, (int)ystep, 1, 1);
			if (!isBetween (xstep, x1, x2) || !isBetween (ystep, y1, y2)) {
				tileBuffer.enabled = false;
				return;
			}
			if (xdir == -1 && xstep % 16 == 0) {
				tileXOffset = -1;
			}
			if (ydir == -1 && ystep % 16 == 0) {
				tileYOffset = -1;
			}
			int tileFinalX = (int) xstep / 16 + tileXOffset;
			int tileFinalY = (int) ystep / 16 + tileYOffset;
			if (tileFinalX < 0 || tileFinalX >= levelWidth || tileFinalY < 0 || tileFinalY >= levelHeight) {
				tileBuffer.enabled = false;
				return;
			}
			
			if (collisionData [getTileId (tileFinalX, tileFinalY)]) {
				tileBuffer.collisionX = xstep;
				tileBuffer.collisionY = ystep;
				tileBuffer.spriteUsed = tileList [getTileId ((int) xstep / 16 + tileXOffset, (int) ystep / 16 + tileYOffset)];
				tileBuffer.mapTile.tileId = tileIdList [getTileId ((int) xstep / 16 + tileXOffset, (int) ystep / 16 + tileYOffset)];
				tileBuffer.mapTile.x = (int) xstep / 16 + tileXOffset;
				tileBuffer.mapTile.y = (int) ystep / 16 + tileYOffset;
				return;
			}
			/*if (time > 45) {
				System.out.println("debug");
				return;
			}*/
		}
	}
	public static double snap16 (double num, int direction) {
		if (num % 16 == 0) {
			if (direction == 1) {
				return num + 16;
			} else {
				return num - 16;
			}
		} else {
			if (direction == 1) {
				return Math.ceil (num / 16) * 16;
			} else {
				return Math.floor (num / 16) * 16;
			}
		}
	}
	public static boolean isColliding (Rectangle hitbox) {
		//Returns true if the given Hitbox is colliding with a solid tile
		for (int i = 0; i <= TemporaryWall.walls.size() - 1; i++) {
			if(TemporaryWall.walls.get(i).isHitting(hitbox)){
				return true;
			}
		}
		int x = hitbox.x;
		int y = hitbox.y;
		int width = hitbox.width;
		int height = hitbox.height;
		int x1 = bind (x / 16, 0, levelWidth * 16);
		int x2 = bind ((x + width) / 16, 0, levelWidth * 16);
		int y1 = bind (y / 16, 0, levelHeight * 16);
		int y2 = bind ((y + height) / 16, 0, levelHeight * 16);
		for (int i = x1; i <= x2; i ++) {
			for (int j = y1; j <= y2; j ++) {
				if (collisionData [getTileId (i, j)] == true) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isColliding (Rectangle hitbox, String tileId) {
		//Returns true if the given Hitbox is colliding with a tile of type tileId
		int x = hitbox.x;
		int y = hitbox.y;
		int width = hitbox.width;
		int height = hitbox.height;
		int x1 = bind (x / 16, 0, levelWidth * 16);
		int x2 = bind ((x + width) / 16, 0, levelWidth * 16);
		int y1 = bind (y / 16, 0, levelHeight * 16);
		int y2 = bind ((y + height) / 16, 0, levelHeight * 16);
		for (int i = x1; i <= x2; i ++) {
			for (int j = y1; j <= y2; j ++) {
				if (tileIdList [getTileId (i, j)].equals (tileId)) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isColliding (Rectangle hitbox, double xTo, double yTo) {
		for (int i = 0; i <= 6; i += 2) {
			if (isColliding (hitbox.x + hitboxCorners [i] * hitbox.width, hitbox.y + hitboxCorners [i + 1] * hitbox.height, xTo + hitboxCorners [i] * hitbox.width, yTo + hitboxCorners [i + 1] * hitbox.height)) {
				return true;
			}
		}
		return false;
	}
	public static double[] getCollidingCoords (Rectangle hitbox, double xTo, double yTo) {
		double[][] collisionData = new double [4][2];
		for (int i = 0; i <= 6; i += 2) {
			collisionData [i / 2] = getCollidingCoords (hitbox.x + hitboxCorners [i] * hitbox.width, hitbox.y + hitboxCorners [i + 1] * hitbox.height, xTo + hitboxCorners [i] * hitbox.width, yTo + hitboxCorners [i + 1] * hitbox.height);
			if (collisionData [i / 2] != null) {
				if (collisionData [i / 2][0] >= hitbox.x + hitboxCorners [i]) {
					collisionData [i / 2][0] -= hitboxCorners [i] * hitbox.width;
				}
				if (collisionData [i / 2][1] >= hitbox.y * hitboxCorners [i + 1]) {
					collisionData [i / 2][1] -= hitboxCorners [i + 1] * hitbox.height;
				}
			}
		}
		double[] returnData = null;
		double minDist = -1;
		double currentDist;
		for (int i = 0; i < 4; i ++) {
			//System.out.println(collisionData[i][1]);
			if (collisionData [i] != null) {
				currentDist = (collisionData [i][0] - hitbox.x) * (collisionData [i][0] - hitbox.x) + (collisionData [i][1] - hitbox.y) * (collisionData [i][1] - hitbox.y);
				if (currentDist <= minDist || minDist == -1) {
					minDist = currentDist;
					returnData = collisionData [i];
				}
			}
		}
		return returnData;
	}
	public static double[] doHitboxVectorCollison (Rectangle hitbox, double xTo, double yTo) {
		//Performs a hitbox-on-tilemap vector collision, sets tileBuffer accordingly, and returns the position the object was in when it collided
		MapTile[] collidingTiles = new MapTile [4];
		double[][] collisionData = new double [4][2];
		double[][] collisionPoints = new double [4][2];
		MapTile[] tileList = new MapTile[4];
		double xDiff = hitbox.x - xTo;
		double yDiff= hitbox.y -yTo;
		int[] ptrlist;
		if (xDiff < 0 && yDiff < 0) {
			ptrlist = new int[] {0, 1, 2, 3};
		} else if (xDiff >= 0 && yDiff < 0) {
			ptrlist = new int[] {1, 0, 2, 3};
		} else if (xDiff < 0 && yDiff >= 0) {
			ptrlist = new int[] {2, 0, 1, 3};
		} else {
			ptrlist = new int[] {3, 0, 1, 2};
		}
		for (int i = 0; i <= 6; i += 2) {
		//	System.out.println(i);
			setTileBuffer (hitbox.x + hitboxCorners [i] * hitbox.width, hitbox.y + hitboxCorners [i + 1] * hitbox.height, xTo + hitboxCorners [i] * hitbox.width, yTo + hitboxCorners [i + 1] * hitbox.height);
		
			//tileBuffer.drawLine ((int)(hitbox.x + hitboxCorners [i] * hitbox.width), (int)(hitbox.y + hitboxCorners [i + 1] * hitbox.height), (int)(xTo + hitboxCorners [i] * hitbox.width), (int)(yTo + hitboxCorners [i + 1] * hitbox.height));
			if (tileBuffer.enabled) {
				collisionData [i / 2][0] = tileBuffer.collisionX;
				collisionPoints [i / 2][0] = tileBuffer.collisionX;
				collisionData [i / 2][1] = tileBuffer.collisionY;
				collisionPoints [i / 2][1] = tileBuffer.collisionY;
				if (collisionData [i / 2][0] >= hitbox.x + hitboxCorners [i]) {
					collisionData [i / 2][0] -= hitboxCorners [i] * hitbox.width;
				}
				if (collisionData [i / 2][1] >= hitbox.y * hitboxCorners [i + 1]) {
					collisionData [i / 2][1] -= hitboxCorners [i + 1] * hitbox.height;
				}
				/*MainLoop.GameAPI.getWindow ().getBuffer ().setColor (new Color(0xFF0000));
				MainLoop.GameAPI.getWindow ().getBuffer ().fillRect ((int)tileBuffer.collisionX, (int)tileBuffer.collisionY, 2, 2);
				MainLoop.GameAPI.getWindow ().getBuffer ().setColor (new Color(0x000000));*/
				//MainLoop.GameAPI.getWindow ().getBuffer ().fillRect ((int)tileBuffer.collisionX, (int)tileBuffer.collisionY, 2, 2);
				collidingTiles [i / 2] = new MapTile (tileBuffer.mapTile.tileId, tileBuffer.mapTile.x, tileBuffer.mapTile.y);
			}
		}
		int closestTile = -1;
		double minDist = -1;
		double currentDist;
		int usedIndex;
		for (int i = 0; i < 4; i ++) {
			//System.out.println(collisionData[i][1]);
			usedIndex = ptrlist [i];
			if (collidingTiles [usedIndex] != null) {
				currentDist = (collisionData [usedIndex][0] - hitbox.x) * (collisionData [usedIndex][0] - hitbox.x) + (collisionData [usedIndex][1] - hitbox.y) * (collisionData [usedIndex][1] - hitbox.y);
				if (currentDist <= minDist || minDist == -1) {
					minDist = currentDist;
					closestTile = usedIndex;
				}
			}
		}
		if (closestTile != -1) {
			tileBuffer.collisionX = collisionPoints [closestTile][0];
			tileBuffer.collisionY = collisionPoints [closestTile][1];
			tileBuffer.mapTile.x  = collidingTiles [closestTile].x;
			tileBuffer.mapTile.y = collidingTiles [closestTile].y;
			tileBuffer.mapTile.tileId = collidingTiles [closestTile].tileId;
			tileBuffer.spriteUsed = null; //Because I'm lazy
			return collisionData [closestTile];
			//Returns true for a successful collision
		} else {
			return null;
			//Returns false for no collision detected
		}
	}
	public static MapTile[] getCollidingTiles (Rectangle hitbox) {
		//Returns a list of all the tiles this object is colliding with
		Stack<MapTile> workingTiles = new Stack<MapTile> ();
		int x = hitbox.x;
		int y = hitbox.y;
		int width = hitbox.width;
		int height = hitbox.height;
		int x1 = bind (x / 16, 0, levelWidth * 16);
		int x2 = bind ((x + width) / 16, 0, levelWidth * 16);
		int y1 = bind (y / 16, 0, levelHeight * 16);
		int y2 = bind ((y + height) / 16, 0, levelHeight * 16);
		for (int i = y1; i <= y2; i ++) {
			for (int j = x1; j <= x2; j ++) {
				if (collisionData [getTileId (j, i)]) {
					workingTiles.push (new MapTile (tileIdList [getTileId (j, i)], j * 16, i * 16));
				}
			}
		}
		return workingTiles.toArray (new MapTile[0]);
	}
	public static MapTile[] getCollidingTiles (Rectangle hitbox, String tileId) {
		//Returns a matrix of tiles that are under the given Hitbox and have the given tileId
		Stack<MapTile> workingTiles = new Stack<MapTile> ();
		int x = hitbox.x;
		int y = hitbox.y;
		int width = hitbox.width;
		int height = hitbox.height;
		int x1 = bind (x / 16, 0, levelWidth * 16);
		int x2 = bind ((x + width) / 16, 0, levelWidth * 16);
		int y1 = bind (y / 16, 0, levelHeight * 16);
		int y2 = bind ((y + height) / 16, 0, levelHeight * 16);
		for (int i = y1; i <= y2; i ++) {
			for (int j = x1; j <= x2; j ++) {
				if (tileIdList [getTileId (j, i)].equals (tileId)) {
					workingTiles.push (new MapTile (tileIdList [getTileId (j, i)], j * 16, i * 16));
				}
			}
		}
		return workingTiles.toArray (new MapTile[0]);
	}
	public static short getTileId (int x, int y) {
		//Returns the numerical tile ID at x, y
		return tileData [0][x][y];
	}
	public static boolean isSolid (int x, int y) {
		//Returns true if the tile at x, y is solid
		return collisionData[tileData [0][x][y]];
	}
	public static String getTileIdString (int x, int y) {
		//Returns the string tile ID at x, y
		return tileIdList [tileData [0][x][y]];
	}
	public static void frameEvent () {
		//Renders the room
		if (isLoaded ()) {
			int windowWidth = GameAPI.getWindow ().getResolution ()[0];
			int windowHeight = GameAPI.getWindow ().getResolution()[1];
			for (int i = 0; i < backgroundList.size (); i ++) {
				backgroundList.get (i).draw (viewX, viewY);
			}
			for (int layer = tileData.length - 1; layer >= 0; layer --) {
				for (int i = -(viewX % 16); i < windowWidth && i < levelWidth * 16; i += 16) {
					for (int j = (-viewY % 16); j < windowHeight && j < levelHeight * 16; j += 16) {
						tileList [tileData [layer][(viewX + i) / 16][(viewY + j) / 16]].draw (i, j);
					}
				}
			}
		}
	}
	public static boolean loadRoom (String path) {
		//Purges the gameObjects
		/*ArrayList<ArrayList<GameObject>> objList = GameLoop.getObjectMatrix ().objectMatrix;
		for (int i = 0; i < objList.size (); i ++) {
			if (objList.get (i) != null) {
				int listSize = objList.get (i).size ();
				for (int j = 0; j < listSize; j ++) {
					if (objList.get (i).get (j) != null) {
						objList.get (i).get (j).forget ();
					}
				}
			}
		}*/
		//Loads the CMF file at the given filepath
		loaded = false;
		readBit = 0;
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
		if (readBits (24) != 0x434D46) {
			System.out.println ("Error: file is corrupted or in an invalid format");
		}
		int version = readBits (8); //For future use
		int layerCount = readBits (8); //For future use
		//resizeLevel (readBits (16), readBits (16));
		levelWidth = readBits (16);
		levelHeight = readBits (16);
		tileData = new short[layerCount][levelWidth][levelHeight];
		for (int layer = 0; layer < layerCount; layer ++) {
			for (int i = 0; i < levelWidth; i ++) {
				for (int c = 0; c < levelHeight; c ++) {
					tileData [layer][i][c] = -1;
				}
			}
		}
		int tilesUsedLength = readBits (16);
		int objectsPlacedLength = readBits (32);
		int tempReadBit = readBit;
		int result = 0;
		int index = 0;
		//Parse tile set list
		while (result != 0x3B) {
			result = readBits (8);
			index ++;
		}
		readBit = tempReadBit;
		char[] tilesetNames = new char[index - 1];
		for (int i = 0; i < index - 1; i ++) {
			tilesetNames [i] = (char) readBits (8);
		}
		readBit += 8;
		String tilesetList = new String (tilesetNames);
		String[] tilesetNameArray = tilesetList.split (",");
		//Parse object list
		tempReadBit = readBit;
		index = 0;
		result = 0;
		while (result != 0x3B) {
			result = readBits (8);
			index ++;
		}
		readBit = tempReadBit;
		char[] objectNames = new char[index - 1];
		for (int i = 0; i < index - 1; i ++) {
			objectNames [i] = (char) readBits (8);
		}
		readBit += 8;
		String objectString = new String (objectNames);
		if (objectString.equals ("")) {
			objectList = new String[0];
		} else {
			objectList = objectString.split (",");
		}
		//Import tiles
		ArrayList<Sprite> tileSheet = new ArrayList<Sprite> ();
		ArrayList<String> tileIdArrList = new ArrayList<String> ();
		Sprite importSprite;
		ArrayList<String> tileParams = new ArrayList<String> ();
		tileParams.add ("grid 16 16");
		for (int i = 0; i < tilesetNameArray.length; i ++) {
			//System.out.println("resources/tilesets/" + tilesetNameArray [i]);
			importSprite = new Sprite ("resources/tilesets/" + tilesetNameArray [i], new SpriteParser (tileParams));
			Sprite[] tempSheet = new Sprite[importSprite.getFrameCount ()];
			for (int j = 0; j < tempSheet.length; j ++) {
				tempSheet [j] = new Sprite (importSprite.getFrame (j));
			}
			//System.out.println(tempSheet.length);
			for (int j = 0; j < tempSheet.length; j ++) {
				tileSheet.add (tempSheet [j]);
				tileIdArrList.add (tilesetNameArray [i] + ":" + String.valueOf (j));
			}
		}
		short[] tilesUsed = new short[tilesUsedLength];
		int tileBits = numBits (tilesUsedLength - 1);
		tileList = new Sprite[tilesUsed.length];
		tileIdList = new String[tileIdArrList.size ()];
		int tileSheetBits = numBits (tileSheet.size () - 1);
		for (int i = 0; i < tilesUsedLength; i ++) {
			tilesUsed [i] = (short) readBits (tileSheetBits);
		}
		for (int i = 0; i < tileList.length; i ++) {
			tileList [i] = tileSheet.get (tilesUsed [i]);
			tileIdList [i] = tileIdArrList.get (tilesUsed [i]);
		}
		for (int i = 0; i < tileIdArrList.size (); i ++) {
			tileIdList [i] = tileIdArrList.get (i);
		}
		for (int i = 0; i < tileList.length; i ++) {
			tileSheet.add (tileList [i]);
		}
		collisionData = new boolean[tileIdList.length];
		for (int i = 0; i < collisionData.length; i ++) {
			TileData workingTile = tileAttributesList.getTile (tileIdList [i]);
			if (workingTile != null) {
				collisionData [i] = workingTile.isSolid ();
			} else {
				collisionData [i] = true;
			}
		}
		//Import object icons
		int widthBits = numBits (levelWidth - 1);
		int heightBits = numBits (levelHeight - 1);
		int objectBits = numBits (objectList.length - 1);
		int objId;
		int objX;
		int objY;
		Class<?> objectClass = null;
		Constructor<?> constructor = null;
		String workingName;
		boolean hasVariants;
		for (int i = 0; i < objectsPlacedLength; i ++) {
			objId = readBits (objectBits);
			objX = readBits (widthBits);
			objY = readBits (heightBits);
			if (objectList [objId].split ("#").length == 2) {
				workingName = objectList [objId].split ("#")[0];
				hasVariants = true;
			} else {
				workingName = objectList [objId];
				hasVariants = false;
			}
			try {
				objectClass = Class.forName ("gameObjects." + workingName);
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				GameObject obj = (GameObject) objectClass.newInstance ();
				obj.declare (objX * 16, objY * 16);
				if (hasVariants) {
					obj.setVariantAttributes (objectList [objId].split ("#")[1]);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//objectList.add (new GameObject (readBits (objectBits), readBits (widthBits), readBits (heightBits)));
		}
		short id;
		int x1;
		int x2;
		int y1;
		int y2;
		for (int layer = 0; layer < layerCount; layer ++) {
			int fullRangesSize = readBits (32);
			int horizRangesSize = readBits (32);
			int vertRangesSize = readBits (32);
			for (int i = 0; i < fullRangesSize; i ++) {
				id = (short) readBits (tileBits);
				x1 = readBits (widthBits);
				x2 = readBits (widthBits);
				y1 = readBits (heightBits);
				y2 = readBits (heightBits);
				//System.out.println(readBit);
				for (int j = x1; j <= x2; j ++) {
					for (int k = y1; k <= y2; k ++) {
						tileData [layer][j][k] = id;
					}
				}
			}
			for (int i = 0; i < horizRangesSize; i ++) {
				id = (short) readBits (tileBits);
				x1 = readBits (widthBits);
				x2 = readBits (widthBits);
				y1 = readBits (heightBits);
				for (int j = x1; j <= x2; j ++) {
					tileData [layer][j][y1] = id;
				}
			}
			for (int i = 0; i < vertRangesSize; i ++) {
				id = (short) readBits (tileBits);
				x1 = readBits (widthBits);
				y1 = readBits (heightBits);
				y2 = readBits (heightBits);
				for (int j = y1; j <= y2; j ++) {
					tileData [layer][x1][j] = id;
				}
			}
			for (int i = 0; i < levelWidth; i ++) {
				for (int c = 0; c < levelHeight; c ++) {
					if (tileData [layer][i][c] == -1) {
						tileData [layer][i][c] = (short) readBits (tileBits);
					}
				}
			}
		}
		loaded = true;
		return true;
	}
	public static int numBits (int num) {
		//Returns the number of bits needed to represent a given number
		for (int i = 31; i > 0; i --) {
			if (num >= (1 << (i - 1))) {
				return i;
			}
		}
		return 1;
	}
	public static void setView (int x, int y) {
		//Sets the top-right coordinate of the viewport of the room to (x, y)
		viewX = x;
		viewY = y;
	}
	public static int getViewX () {
		//Returns the x-coordinate of the viewport of the room
		return viewX;
	}
	public static int getViewY () {
		//Returns the y-coordinate of the viewport of the room
		return viewY;
	}
	public static int getWidth () {
		//Returns the width of the room in tiles
		return levelWidth;
	}
	public static int getHeight () {
		//Returns the height of the room in tiles
		return levelHeight;
	}
	public static int bind (int value, int min, int max) {
		//Binds a value to within the range min, max
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}
	public static boolean isBetween (double num, double bound1, double bound2) {
		//Returns true if num is between bound1 and bound2
		if (bound1 >= bound2) {
			double temp = bound2;
			bound2 = bound1;
			bound1 = temp;
		}
		return (num >= bound1 && num < bound2);
	}
	public static ArrayList<Background> getBackgroundList () {
		return backgroundList;
	}
	public static double getGravity () {
		return gravity;
	}
	public static void setGravity (double grav) {
		gravity = grav;
	}
	public static TileAttributesList getTileAttributesList () {
		return tileAttributesList;
	}
	public static void setTileAttributesList (TileAttributesList list) {
		tileAttributesList = list;
	}
	public static boolean tileInBounds (int x, int y) {
		if (x >= 0 && x < levelWidth && y >= 0 && y < levelHeight) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isLoaded () {
		return loaded;
	}
}