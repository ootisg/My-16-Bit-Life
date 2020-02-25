package resources;

import java.util.ArrayList;


public class AfterRenderDrawer {
	private static ArrayList <Sprite> spritesToDraw = new ArrayList <Sprite>();
	private static ArrayList <Integer> x = new ArrayList <Integer> ();
	private static ArrayList <Integer> y = new ArrayList <Integer> ();
	private static ArrayList <Integer> frames = new ArrayList <Integer> ();
	private static ArrayList <Boolean> keep = new ArrayList <Boolean> ();
	private static boolean isRendering = false;
	private static int currentIndex = 0;

	public static void drawAfterRender (int X, int Y, Sprite sprite) {
		drawAfterRender(X,Y,sprite,0,false);
	}
	public static void drawAfterRender(int X, int Y, Sprite sprite, int frame) {
		drawAfterRender(X,Y,sprite,frame,false);
	}
	public static void drawAfterRender (int X, int Y, Sprite sprite, int frame, boolean toKeepOrNotToKeep) {
		if (!isRendering){
		spritesToDraw.add(sprite);
		x.add(X);
		y.add(Y);
		frames.add(frame);
		keep.add(toKeepOrNotToKeep);
		}
	}
	public static void removeElement (Sprite sprite, int X, int Y) {
		for (int i = 0; i < spritesToDraw.size() && i <x.size() && i <y.size() && i <frames.size(); i++) {
			if (spritesToDraw.get(i).equals(sprite) && x.get(i) == X  && y.get(i) == Y && keep.get(i)){
				spritesToDraw.remove(i);
				x.remove(i);
				y.remove(i);
				frames.remove(i);
				keep.remove(i);
				return;
			}
		}
		System.out.println("nothing was removed");
	}
	// removes any element at a certain x and y coordintate only use if you can't get the same instance for the sprite (or don't you can live your life however you want too im not forceing you to do anything)
	public static void forceRemoveElement (int X, int Y) {
		for (int i = 0; i < spritesToDraw.size() && i <x.size() && i <y.size() && i <frames.size(); i++) {
			if (x.get(i) == X &&  y.get(i) == Y && keep.get(i)){
				spritesToDraw.remove(i);
				x.remove(i);
				y.remove(i);
				frames.remove(i);
				keep.remove(i);
				break;
			}
		}
	}
	public static void drawAll () {
		isRendering = true;
		for (int i = 0; i < spritesToDraw.size() && i <x.size() && i <y.size() && i <frames.size(); i++) {
		try {
		try {
		spritesToDraw.get(i).draw(x.get(i), y.get(i),frames.get(i));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("index out of bounds in draw after render");
		}
		} catch (NullPointerException e) {
			System.out.println("null pointer in draw after render check info about the sprite and coordinates on the next lines");
			try {
			System.out.println(spritesToDraw.get(i).toString());
			} catch (NullPointerException b) {
			System.out.println("sprite was the issue");
			}
			try {
				System.out.println(Integer.toString(x.get(i)));
				} catch (NullPointerException b) {
				System.out.println("x was the issue");
				}
			try {
				System.out.println(Integer.toString(y.get(i)));
				} catch (NullPointerException b) {
				System.out.println("y was the issue");
				}
		}
		}
		while (spritesToDraw.size() != currentIndex && keep.size() != currentIndex) {
			if (!keep.get(currentIndex)) {
			spritesToDraw.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
		currentIndex = 0;
		while (x.size() != currentIndex && keep.size() != currentIndex) {
			if (!keep.get(currentIndex)) {
			x.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
		currentIndex = 0;
		while (y.size() != currentIndex && keep.size() != currentIndex) {
			if (!keep.get(currentIndex)) {
			y.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
		currentIndex = 0;
		while (frames.size() != currentIndex && keep.size() != currentIndex) {
			if (!keep.get(currentIndex)) {
			frames.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
		currentIndex = 0;
		while (keep.size() != currentIndex) {
			if (!keep.get(currentIndex)) {
			keep.remove(currentIndex);
			} else {
			currentIndex = currentIndex + 1;
			}
		}
		currentIndex = 0;
		isRendering = false;
	}
}