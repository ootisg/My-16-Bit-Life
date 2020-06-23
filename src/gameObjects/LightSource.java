package gameObjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public interface LightSource {

	public Rectangle getVeiwport();
	/**
	 * GET LITTTTTTTTTT
	 * @return wheather or not this object should emit light
	 */
	public boolean getLit();
	
	public BufferedImage getOverlay();
	
	public static BufferedImage getLightSourceImage (int width, int red, int green, int blue, int alpha) {
		
		//Make the image and get its raster
		BufferedImage lightImg = new BufferedImage (width, width, BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster r = lightImg.getRaster ();
		
		//Compute some important stuffs
		double midpt = (double)width / 2;
		
		//Write pixels to the image
		for (int wy = 0; wy < width; wy++) {
			for (int wx = 0; wx < width; wx++) {
				double xDist = wx - midpt;
				double yDist = wy - midpt;
				double dist = Math.sqrt (xDist * xDist + yDist * yDist) / midpt; //Compute the distance from the center, with 1 being a far edge
				if (dist > 1) {
					r.setPixel (wx, wy, new double[] {0, 0, 0, 255});
				} else {
					int wRed = (int)(red - red * dist);
					int wGreen = (int)(green - green * dist);
					int wBlue = (int)(blue - blue * dist);
					int wAlpha = (int)(alpha + ((255 - alpha) * dist));
					double[] color = new double[] {wRed, wGreen, wBlue, wAlpha};
					r.setPixel (wx, wy, color);
				}
			}
		}
		
		return lightImg;
	}
	
	public static void writeLightSourceImage (int width, int red, int green, int blue, int alpha, String filepath) {
		
		BufferedImage lightImg = getLightSourceImage (width, red, green, blue, alpha);
		try {
			File f = new File (filepath);
			if (!f.exists ()) {
				f.createNewFile ();
			}
			ImageIO.write (lightImg, "PNG", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
