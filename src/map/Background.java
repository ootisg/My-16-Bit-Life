package map;

import main.GameLoop;
import main.AnimationHandler;
import resources.Sprite;

public class Background {
	//Big oof here, I'm gonna have to fix this at some point
	private AnimationHandler animationHandler;
	private double scrollRate;
	public Background (Sprite image) {
		this.animationHandler = new AnimationHandler (image);
		this.scrollRate = 1.0;
	}
	public void draw (double viewX, double viewY) {
		int width = GameLoop.getWindow ().getResolution () [0];
		int height = GameLoop.getWindow ().getResolution () [1];
		int imgWidth = animationHandler.getImage ().getFrame (animationHandler.getFrame ()).getWidth ();
		int imgHeight = animationHandler.getImage ().getFrame (animationHandler.getFrame ()).getHeight ();
		for (int i = -((int)viewX % imgWidth); i < width; i += imgWidth) {
			for (int j = -((int)viewY % imgHeight); j < height; j += imgHeight) {
				animationHandler.animate (i, j, false, false);
			}
		}
		animationHandler.animate ((int)(-viewX / scrollRate), (int)(-viewY / scrollRate), false, false);
	}
	public void setImage (Sprite image) {
		animationHandler.setImage (image);
	}
	public void setScrollRate (double scrollRate) {
		this.scrollRate = scrollRate;
	}
	public Sprite getImage () {
		return animationHandler.getImage ();
	}
	public double getScrollRate () {
		return scrollRate;
	}
}