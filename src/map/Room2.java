package map;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Stack;

import gameObjects.TemporaryWall;
import main.GameAPI;
import main.GameObject;
import resources.Sprite;
import resources.SpriteParser;

public class Room2 {
	
	
	
	public Room2 () {
		//Non-electric boogaloo
	}
	
	private static int readBits (int num) {
		return -1;
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
		return false;
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