package tileEntities;

import java.awt.image.BufferedImage;
import java.util.Random;

import main.GameObject;
import map.Room;
import map.TileEntitiy;
import mapObjects.BubblePlatform;
import resources.Sprite;

/**
 * You fall through it, and it slowly spawns bubbles.
 * @author Sam
 *
 */
public class BubbleWater extends AnimatedBlock {
	
	// RNG for timing bubble spawns. Can range from just below 9 seconds to theoretically forever.
	private Random RNG = new Random();
	
	// Timer for... uh... timing. Bubble spawns.
	private int timer = 0;
	
	// Initializes the sprite
	private boolean initialized = true;
	
	public BubbleWater () {
		
	}
	
	@Override
	public boolean doesColide (GameObject o) {
	    return false;
	}
	
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (!initialized) {
		this.setSprite(new Sprite ("resources/sprites/tiles/config/bubbleWater.txt"));
		System.out.println(this.blockSprite);
		initialized = true;
		}
		timer += RNG.nextInt(5);
		BubblePlatform bubble = new BubblePlatform();
		if (timer >= 1300) {
			bubble.declare(this.getX(), this.getY());
			timer = 0;
		}
	}
	@Override
	public void onCollision(GameObject o) {
		
	}
}
