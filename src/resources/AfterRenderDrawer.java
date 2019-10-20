package resources;

import java.util.ArrayList;

public class AfterRenderDrawer {
	private static ArrayList <Sprite> spritesToDraw = new ArrayList <Sprite>();
	private static ArrayList <Integer> x = new ArrayList <Integer> ();
	private static ArrayList <Integer> y = new ArrayList <Integer> ();
	private static ArrayList <Integer> frames = new ArrayList <Integer> ();
	private static boolean isRendering = false;
	public static void drawAfterRender (int X, int Y, Sprite sprite) {
		if (!isRendering){
		spritesToDraw.add(sprite);
		x.add(X);
		y.add(Y);
		frames.add(0);
		}
	}
	public static void drawAfterRender(int X, int Y, Sprite sprite, int frame) {
		if (!isRendering) {
		spritesToDraw.add(sprite);
		x.add(X);
		y.add(Y);
		frames.add(frame);
		}
	}
	public static void drawAll () {
		isRendering = true;
		while (!spritesToDraw.isEmpty()) {
		spritesToDraw.get(0).draw(x.get(0), y.get(0),frames.get(0));
		spritesToDraw.remove(0);
		x.remove(0);
		y.remove(0);
		frames.remove(0);
		
		}
		isRendering = false;
	}
}