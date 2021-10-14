package tileEntities;

import main.GameObject;
import map.Room;
import map.TileEntitiy;
import players.Player;
import resources.Sprite;

public class TreadmillLeft extends TileEntitiy {
	public TreadmillLeft () {

	}
	@Override
	public void onCollision(GameObject o) {
		o.setX(o.getX() -1);
	}
}

