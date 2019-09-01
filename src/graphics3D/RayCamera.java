package graphics3D;

import java.awt.image.DataBuffer;
import java.awt.image.Raster;

import main.GameObject;
import main.GameLoop;
import map.Room;
import map.TileBuffer;
import main.GameWindow;

public class RayCamera extends GameObject {
	//Sadly, getting/setting the screen data no longer works
	double ang;
	int[] imageData;
	public double fov = Math.PI / 3;
	public double clipFar = 640;
	public double base = 32;
	int portWidth = 0;
	int portHeight = 0;
	public boolean enabled = false;
	public RayCamera (double x, double y, double ang) {
		this.ang = ang;
		this.declare (x, y);
	}
	@Override
	public void frameEvent () {
		if (this.enabled) {
			imageData = GameLoop.getWindow ().getImageData ();
			portWidth = GameLoop.getWindow ().getResolution ()[0];
			portHeight = GameLoop.getWindow ().getResolution ()[1];
			if (keyDown ('W')) {
				this.setX (this.getX () + Math.cos (ang) * 3);
				this.setY (this.getY () - Math.sin (ang) * 3);
			}
			if (keyDown ('S')) {
				this.setX (this.getX () - Math.cos (ang) * 3);
				this.setY (this.getY () + Math.sin (ang) * 3);
			}
			double x = this.getX ();
			double y = this.getY ();
			if (keyDown ('A')) {
				this.ang -= (Math.PI / 180) * 3;
			}
			if (keyDown ('D')) {
				this.ang += (Math.PI / 180) * 3;
			}
			double castAng = ang - fov / 2;
			TileBuffer buffer = Room.tileBuffer;
			DataBuffer textureData;
			for (int i = 0; i < portWidth; i ++) {
				castAng = (ang - fov / 2) + ((i * fov) / portWidth);
				Room.setTileBuffer (x, y, x + clipFar * Math.cos (castAng), y - clipFar * Math.sin (castAng));
				//System.out.println(buffer.collisionX);
				if (buffer.enabled) {
					//System.out.println(buffer.spriteUsed);
					textureData = buffer.spriteUsed.getFrame (0).getRaster ().getDataBuffer ();
					double dist = Math.sqrt ((x - buffer.collisionX) * (x - buffer.collisionX) + (y - buffer.collisionY) * (y - buffer.collisionY)) * Math.cos ((i * fov) / 640 - (fov / 2));
					//System.out.println(dist);
					double columnHeight = (portHeight / (dist / base));
					int startY = (portHeight / 2) - (int)(columnHeight / 2);
					int topY = startY;
					int endY = (portHeight / 2) + (int)(columnHeight / 2);
					if (startY < 0) {
						startY = 0;
					}
					if (endY > portHeight) {
						endY = portHeight;
					}
					int ratioY;
					int ratioX;
					for (int j = startY; j < endY; j ++) {
						if (buffer.collisionX % 16 == 0) {
							ratioX = (int)(buffer.collisionY % 16);
						} else {
							ratioX = (int)(buffer.collisionX % 16);
						}
						ratioY = (int)(((double)(j - topY) / (double)columnHeight) * 16);
						setPixel (i, j, textureData.getElem (ratioY * 16 + ratioX));
					}
				}
			}
		}
	}
	public void setPixel (int x, int y, int val) {
		imageData [y * portWidth + x] = val;
	}
	@Override
	public void forget () {
		
	}
}