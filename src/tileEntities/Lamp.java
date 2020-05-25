package tileEntities;

import java.awt.image.BufferedImage;

import main.GameObject;
import map.Room;
import map.TileEntitiy;
import resources.Sprite;

/**
 * moths like this class
 * @author Jeffrey to the James to the Marsh to the two 
 *
 */
public class Lamp extends TileEntitiy {
	Boolean beListhed = false;
	int timer = 0;
	public Lamp () {
		super ();
	}
	@Override 
	public void frameEvent () {
		if (beListhed) {
			timer = timer + 1;
		}
		if (timer >= 10) {
			Sprite stupid = new Sprite ("resources/tilesets/LAMP.png");
			this.setTexture(stupid.getFrame(0));
			Room.getChungus(this.getX(), this.getY()).invalidate(this.getLayer());
			beListhed = false;
			timer = 0;
		}
		
	}
	@Override
	public void onCollision(GameObject o) {
			
		if (o.getClass().getSimpleName().equals("Jeffrey") || o.getClass().getPackageName().equals("projectiles")) {
			Sprite stupid = new Sprite ("resources/tilesets/yellow.png");
			timer = 0;
			if (!this.getTexture().equals (stupid.getFrame(0))) {
			this.setTexture(stupid.getFrame(0));
			Room.getChungus(this.getX(), this.getY()).invalidate(this.getLayer());
			beListhed = true;
			}
			
		}
		
	}
}
