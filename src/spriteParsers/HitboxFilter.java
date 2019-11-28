package spriteParsers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;



public class HitboxFilter {

	private HitboxFilter () {
		//Cannot be constructed
	}
	
	public static ArrayList<Rectangle> filter (ArrayList<ParsedFrame> frames, int color) {
		ArrayList<Rectangle> result = new ArrayList<Rectangle> ();
		Iterator<ParsedFrame> frameIter = frames.iterator ();
		Iterator<Pixel> pixelIter;
		while (frameIter.hasNext ()) {
			ArrayList<Pixel> hitboxPixels = new ArrayList<Pixel> ();
			pixelIter = frameIter.next ().getPixels ().iterator ();
			while (pixelIter.hasNext ()) {
				Pixel working = pixelIter.next ();
				if (working.getRgb () == color) {
					hitboxPixels.add (working);
				}
			}
			if (hitboxPixels.size () == 2) {
				Pixel topLeft = hitboxPixels.get (0);
				Pixel bottomRight = hitboxPixels.get (1);
				result.add (new Rectangle (topLeft.getX (), topLeft.getY (), bottomRight.getX () - topLeft.getX (), bottomRight.getY () - topLeft.getY ()));
			} else {
				result.add (null);
			}
		}
		return result;
	}
}
