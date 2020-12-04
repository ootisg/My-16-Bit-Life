package extensions;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import resources.Sprite;
import spriteParsers.ParsedFrame;
import spriteParsers.Pixel;
import spriteParsers.PixelParser;

public class AnchoredSprite {

	ArrayList <HashMap<String,Point>> offsets;
	
	public AnchoredSprite (Sprite soruce, Sprite meta, HashMap<Color, String> config) {
		
		ArrayList<LinkedList<Pixel>> oofsets = new ArrayList<LinkedList<Pixel>> ();
		
		//Get the awesome thing
		ArrayList<ParsedFrame> parsedFrames = PixelParser.parse (meta);
		for (int i = 0; i < parsedFrames.size (); i++) {
			//Ready the cannons
			ArrayList<Pixel> pixels = parsedFrames.get (i).getPixels ();
			LinkedList<Pixel> workingPixels = new LinkedList<Pixel> ();
			oofsets.add (workingPixels);
			for (int j = 0; j < pixels.size (); j++) {
				//Filter anchor point to front
				Pixel p = pixels.get (i);
				Point pt = new Point (pixels.get (i).getX (), pixels.get (i).getY ());
				Color c = new Color (p.getRgb ());
				if (config.get (c).equals ("anchor")) {
					workingPixels.addFirst (p);
				} else {
					workingPixels.add (p);
				}
			}
		}
		
		// gets the correct set of offsets for each of the points
		offsets = new ArrayList<HashMap<String, Point>> ();
		Pixel anchorFirst = oofsets.get(0).getFirst();
		Pixel anchor = null;
		Point offset;
		for (int i = 0; i < oofsets.size ();i++) {
			Iterator <Pixel> iter = oofsets.get (i).iterator ();
			anchor = iter.next();
			offset = new Point (anchorFirst.getX()-anchor.getX(), anchorFirst.getY() - anchor.getY());
			offsets.get(i).put("anchor", offset);
			while(iter.hasNext()) {
				Pixel px = iter.next ();
				Point pxPt = new Point (px.getX () - anchor.getX (), px.getY () - anchor.getY ());
				String key = config.get (new Color (px.getRgb ()));
				offsets.get (i).put (key, pxPt);
			}
		}
	}
	
	
	
}
