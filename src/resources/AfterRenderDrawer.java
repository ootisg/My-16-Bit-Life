package resources;

import java.util.ArrayList;

import main.ObjectHandler;


public class AfterRenderDrawer {
	private static ArrayList <DrawableObject> objectsToDraw = new ArrayList <DrawableObject>(); 
	private static boolean isRendering = false;
	public static void drawAfterRender (int X, int Y, Sprite sprite) {
		drawAfterRender(X,Y,sprite,0,false);
	}
	public static void drawAfterRender(int X, int Y, Sprite sprite, int frame) {
		drawAfterRender(X,Y,sprite,frame,false);
	}
	public static void drawAfterRender (int X, int Y, Sprite sprite, int frame, boolean toKeepOrNotToKeep) {
		if (!isRendering){
		objectsToDraw.add(new DrawableObject (X,Y,sprite,frame,toKeepOrNotToKeep));
		} else {
		DrawableObject working = new DrawableObject (X,Y,sprite,frame,toKeepOrNotToKeep);
		working.blacklist();
		objectsToDraw.add(working);
		}
	}
	public static void removeElement (Sprite sprite, int X, int Y) {
		for (int i = 0; i <objectsToDraw.size(); i++) {
			if (objectsToDraw.get(i).getSprite().equals(sprite) && objectsToDraw.get(i).getX() == X  && objectsToDraw.get(i).getY() == Y){
				objectsToDraw.remove(i);
				break;
			}
		}
		System.out.println("nothing was removed");
	}
	// removes any element at a certain x and y coordintate only use if you can't get the same instance for the sprite (or don't you can live your life however you want too im not forceing you to do anything)
	public static void forceRemoveElement (int X, int Y) {
		for (int i = 0; i < objectsToDraw.size(); i++) {
			if (objectsToDraw.get(i).getX() == X &&  objectsToDraw.get(i).getY() == Y){
				objectsToDraw.remove(i);
				break;
			}
		}
	}
	public static void clear () {
		int currentIndex = 0;
		while (objectsToDraw.size() != currentIndex) {
			
			if (!objectsToDraw.get(currentIndex).keep()) {
			objectsToDraw.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
	}
	public static void clearWithACoolLookinEffect () {
		for (int i = 0; i < objectsToDraw.size(); i++) {
			if (!objectsToDraw.get(i).keep()) {
			objectsToDraw.remove(i);
			} 
		}
	}
	public static void drawAll () {
		isRendering = true;
		for (int i = 0; i <objectsToDraw.size(); i++) {
		try {
		try {		
		if (!objectsToDraw.get(i).isBlacklisted()) {
		objectsToDraw.get(i).sprite.draw(objectsToDraw.get(i).getX(), objectsToDraw.get(i).getY(),objectsToDraw.get(i).getFrame());
		}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("index out of bounds in draw after render");
		}
		} catch (NullPointerException e) {
			System.out.println("null pointer in draw after render check info about the sprite and coordinates on the next lines");
			try {
			System.out.println(objectsToDraw.get(i).getSprite().toString());
			} catch (NullPointerException b) {
			System.out.println("sprite was the issue");
			}
			try {
				System.out.println(Integer.toString(objectsToDraw.get(i).getX()));
				} catch (NullPointerException b) {
				System.out.println("x was the issue");
				}
			try {
				System.out.println(Integer.toString(objectsToDraw.get(i).getY()));
				} catch (NullPointerException b) {
				System.out.println("y was the issue");
				}
		}
		}
		clear();
		isRendering = false;
	}
	public static class DrawableObject {
		int x;
		int y;
		boolean blacklisted = false;
		Sprite sprite;
		int frame;
		boolean keep;
		public DrawableObject(int x, int y, Sprite sprite, int frame,boolean keep) {
			this.x = x;
			this.y = y;
			this.sprite = sprite;
			this.keep = keep;
			this.frame = frame;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getFrame() {
			return frame;
		}
		public Sprite getSprite() {
			return sprite;
		}
		public boolean keep() {
			return keep;
		}
		public void blacklist () {
			blacklisted = true;
		}
		public void whiteList () {
			blacklisted = false;
		}
		public boolean isBlacklisted () {
			return blacklisted;
		}
		
	}
}
