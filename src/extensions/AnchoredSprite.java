package extensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import main.GameAPI;
import resources.Sprite;
import spriteParsers.ParsedFrame;
import spriteParsers.Pixel;
import spriteParsers.PixelParser;
import vector.Vector2D;

public class AnchoredSprite extends Sprite {

	Point startOffset;
	ArrayList <HashMap<String,Point>> offsets;
	ArrayList <HashMap<String,Point>> rawPixels;
	
	public AnchoredSprite (Sprite soruce, Sprite meta, HashMap<Color, String> config) {
		
		super (soruce);
		
		ArrayList<LinkedList<Pixel>> oofsets = new ArrayList<LinkedList<Pixel>> ();
		
		//Get the awesome thing
		boolean foundAnchor = false;
		ArrayList<ParsedFrame> parsedFrames = PixelParser.parse (meta);
		for (int i = 0; i < parsedFrames.size (); i++) {
			//Ready the cannons
			ArrayList<Pixel> pixels = parsedFrames.get (i).getPixels ();
			LinkedList<Pixel> workingPixels = new LinkedList<Pixel> ();
			oofsets.add (workingPixels);
			for (int j = 0; j < pixels.size (); j++) {
				//Filter anchor point to front
				Pixel p = pixels.get (j);
				Point pt = new Point (pixels.get (j).getX (), pixels.get (j).getY ());
				Color c = new Color (p.getRgb ());
				if (config.get (c).equals ("anchor")) {
					workingPixels.addFirst (p);
					foundAnchor = true;
				} else {
					workingPixels.add (p);
				}
			}
		}
		
		// gets the correct set of offsets for each of the points
		offsets = new ArrayList<HashMap<String, Point>> ();
		rawPixels = new ArrayList<HashMap<String, Point>> ();
		Pixel anchorFirstPx = oofsets.get(0).getFirst();
		Point anchorFirst = new Point (anchorFirstPx.getX (), anchorFirstPx.getY ());
		if (!foundAnchor) {
			anchorFirst = new Point (0, 0);
		}
		startOffset = anchorFirst;
		Point anchor = null;
		Point offset;
		for (int i = 0; i < oofsets.size ();i++) {
			Iterator <Pixel> iter = oofsets.get (i).iterator ();
			if (foundAnchor) {
				Pixel anchorPx = iter.next ();
				anchor = new Point (anchorPx.getX (), anchorPx.getY ());
			} else {
				anchor = new Point (0, 0);
			}
			offset = new Point ((int)anchorFirst.getX()-(int)anchor.getX(), (int)anchorFirst.getY() - (int)anchor.getY());
			offsets.add(new HashMap<String,Point>());
			rawPixels.add (new HashMap<String,Point>());
			if (!foundAnchor) {
				offsets.get (i).put ("anchor", null);
				rawPixels.get (i).put ("anchor", null);
			} else {
				offsets.get(i).put("anchor", offset);
				rawPixels.get (i).put ("anchor", anchor);
			}
			while(iter.hasNext()) {
				Pixel px = iter.next ();
				Point pxPtRaw = new Point (px.getX (), px.getY ());
				Point pxPt = new Point (px.getX () - (int)anchor.getX (), px.getY () - (int)anchor.getY ());
				String key = config.get (new Color (px.getRgb ()));
				offsets.get (i).put (key, pxPt);
				rawPixels.get (i).put (key, pxPtRaw);
			}
		}
	}
	
	@Override
	public void draw (int x, int y, boolean flipHorizontal, boolean flipVertical, int frame) {
		draw (x, y, frame);
	}
	
	@Override
	public void draw (int x, int y, boolean flipHorizontal, boolean flipVertical, int frame, int width, int height) {
		draw (x, y, frame);
	}
	
	@Override
	public void draw (int anchorX, int anchorY) {
		draw (anchorX, anchorY, 0);
	}
	
	@Override
	public void draw (int anchorX, int anchorY, int frame) {
		Point anchorPt = offsets.get (frame).get ("anchor");
		if (anchorPt != null) {
			int drawX = anchorX + anchorPt.x - startOffset.x;
			int drawY = anchorY + anchorPt.y - startOffset.y;
			super.draw (drawX, drawY, frame);
			Graphics g = GameAPI.window.getBufferGraphics ();
			g.setColor(new Color (0x0000FF));
			g.fillRect (anchorX, anchorY, 1, 1);
		} else {
			super.draw (anchorX, anchorY, frame);
		}
	}
	
	public Point getRelativeToAnchor (int anchorX, int anchorY, int frame, String type) {
		Point pt = offsets.get (frame).get (type);
		return new Point (anchorX + pt.x, anchorY + pt.y);
	}
	
}
