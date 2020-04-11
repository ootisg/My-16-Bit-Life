package tileEntities;

import main.GameObject;
import map.TileEntitiy;

public class Stepladder extends TileEntitiy{
	public Stepladder() {
		super();
	}
	@Override
	public void onCollision(GameObject o) {
	System.out.println("ow, " + o.getClass().getSimpleName() + " just stepped on me");
	}
	@Override 
	public boolean doesColide (GameObject o) {
		return true;
	}
}
