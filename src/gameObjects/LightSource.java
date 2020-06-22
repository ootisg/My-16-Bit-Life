package gameObjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface LightSource {

	public Rectangle getVeiwport();
	/**
	 * GET LITTTTTTTTTT
	 * @return wheather or not this object should emit light
	 */
	public boolean getLit();
	
	public BufferedImage getOverlay();
}
