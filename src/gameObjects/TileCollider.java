package gameObjects;

import main.GameObject;
import map.Room;
import vector.Vector2D;

public class TileCollider extends GameObject {

	public TileCollider () {
		this.setHitboxAttributes (1, 1, 14, 14);
	}
	
	/**
	 * What do you think it does you dumbass *dab*
	 * @param tileX
	 * @param tileY
	 * @return
	 */
	public boolean isCollidingWithTile (int tileX, int tileY) {
		this.setX (tileX * 16);
		this.setY (tileY * 16);
		return Room.isColliding (this);
	}
	
	public boolean isCollidingWithTile (Vector2D pointless) {
		return isCollidingWithTile ((int)pointless.x, (int)pointless.y);
	}
	
}
