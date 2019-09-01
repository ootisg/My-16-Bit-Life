package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.MapTile;
import map.MapTileCollision;
import map.Room;
import map.TileAttributesList;
import map.TileData;
import players.Jeffrey;

public class Ladder extends GameObject {
	public Ladder () {
	}
	public void frameEvent () {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")&& keyPressed('W')) {
			Jeffrey.onLadder = true;
			Jeffrey.vy = 0;
			Jeffrey.vx = 0;
			MapTile[] ladder = Room.getCollidingTiles(jeffrey.hitbox(), "Ladder.png:0");
			jeffrey.setX(ladder[0].x);
			
		}
		if (Jeffrey.onLadder && keyDown ('W') && Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() -3);
		}
		jeffrey.setY(jeffrey.getY() + 3);
		if (Jeffrey.onLadder && keyDown ('S') && Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() + 3);
		}
		jeffrey.setY(jeffrey.getY() - 3);
		jeffrey.setY(jeffrey.getY() + 1);
		if (Room.isColliding(jeffrey.hitbox(), "Ladder.png:0") && !Jeffrey.onLadder && jeffrey.vy>0) {
			Jeffrey.vy = 0;
			jeffrey.isJumping = false;
		} 
		jeffrey.setY(jeffrey.getY() - 1);
		if (Jeffrey.onLadder && (keyPressed (32))){
			Jeffrey.onLadder = false;
		}
	}
}

