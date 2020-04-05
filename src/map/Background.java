package map;

import main.GameLoop;
import main.RenderLoop;
import main.AnimationHandler;
import resources.Sprite;

public class Background {
	//Big oof here, I'm gonna have to fix this at some point
	private AnimationHandler animationHandler;
	private double scrollRateHorizontal;
	private double scrollRateVertical;
	public Background (Sprite image) {
		this.animationHandler = new AnimationHandler (image);
		this.scrollRateHorizontal = 1.0;
		this.scrollRateVertical = 1.0;
	}
	public void draw (double viewX, double viewY) {
		int width = RenderLoop.window.getResolution () [0];
		int height = RenderLoop.window.getResolution () [1];
		int imgWidth = animationHandler.getImage ().getFrame (animationHandler.getFrame ()).getWidth ();
		int imgHeight = animationHandler.getImage ().getFrame (animationHandler.getFrame ()).getHeight ();
		for (int i = -((int)viewX % imgWidth); i < width; i += imgWidth) {
			for (int j = -((int)viewY % imgHeight); j < height; j += imgHeight) {
				animationHandler.draw (i, j);
			}
		}
		animationHandler.draw ((int)(-viewX / scrollRateHorizontal), (int)(-viewY / scrollRateVertical));
	}
	public void setImage (Sprite image) {
		animationHandler.setImage (image);
	}
	public void setScrollRateHorizontal (double scrollRate) {
		this.scrollRateHorizontal = scrollRate;
	}
	public void setScrollRateVertiacal (double scrollRate) {
		this.scrollRateVertical = scrollRate;
	}
	public Sprite getImage () {
		return animationHandler.getImage ();
	}
	public double getScrollRateHorizontal () {
		return scrollRateHorizontal;
	}
	public double getScrollRateVertical () {
		return scrollRateVertical;
	}
}