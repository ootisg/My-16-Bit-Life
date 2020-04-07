package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.MapTile;
import map.MapTileCollision;
import map.Room;
import map.TileData;
import players.Jeffrey;

public class Ladder extends GameObject {
	public Ladder () {
	}
	public void frameEvent () {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")&& keyPressed('W')) {
			GameCode.testJeffrey.onLadder = true;
			GameCode.testJeffrey.vy = 0;
			GameCode.testJeffrey.vx = 0;
			MapTile[] ladder = Room.getCollidingTiles(jeffrey.hitbox(), "Ladder.png:0");
			jeffrey.setX(ladder[0].x);
			
		}
		if (GameCode.testJeffrey.onLadder && keyDown ('W') && Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() -3);
		}
		jeffrey.setY(jeffrey.getY() + 3);
		if (GameCode.testJeffrey.onLadder && keyDown ('S') && Room.isColliding(jeffrey.hitbox(), "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() + 3);
		}
		jeffrey.setY(jeffrey.getY() - 3);
		jeffrey.setY(jeffrey.getY() + 1);
		if (Room.isColliding(jeffrey.hitbox(), "Ladder.png:0") && !GameCode.testJeffrey.onLadder && jeffrey.vy>0) {
			GameCode.testJeffrey.vy = 0;
			jeffrey.isJumping = false;
		} 
		jeffrey.setY(jeffrey.getY() - 1);
		if (GameCode.testJeffrey.onLadder && (keyPressed (32))){
			GameCode.testJeffrey.onLadder = false;
		}
	}
}

