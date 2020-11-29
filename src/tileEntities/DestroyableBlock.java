package tileEntities;

import main.GameObject;
import map.Room;
import map.TileEntitiy;
import resources.Sprite;

public class DestroyableBlock extends TileEntitiy {
	boolean isDestroyed = false;
	public DestroyableBlock () {
		
	}
	@Override
	public void onCollision (GameObject o) {
		if (o.getClass().getSimpleName().equals("ChaseObject")) {
		isDestroyed = true;
		}
	}
	@Override
	public boolean doesColide (GameObject o)  {
		if (!o.getClass().getSimpleName().equals("ChaseObject")) {
			return !isDestroyed;
		} else {
			isDestroyed = true;
			this.setTexture(Sprite.getImage("resources/tilesets/transparent.png"));
			Room.getChungus(this.getX(), this.getY()).invalidate(this.getLayer());
			return false;
		}
	}
}
