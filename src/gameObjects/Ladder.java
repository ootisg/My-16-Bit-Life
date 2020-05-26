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
	Jeffrey j;
	public Ladder () {
		j =  (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0);
	}
	public void frameEvent () {
		Jeffrey jeffrey = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").get (0);
		if (Room.isColliding(jeffrey, "Ladder.png:0")&& keyPressed('W')) {
			j.onLadder = true;
			j.vy = 0;
			j.vx = 0;
			MapTile[] ladder = Room.getCollidingTiles(jeffrey, "Ladder.png:0");
			jeffrey.setX(ladder[0].x);
			
		}
		if (j.onLadder && keyDown ('W') && Room.isColliding(jeffrey, "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() -3);
		}
		jeffrey.setY(jeffrey.getY() + 3);
		if (j.onLadder && keyDown ('S') && Room.isColliding(jeffrey, "Ladder.png:0")) {
			jeffrey.setY(jeffrey.getY() + 3);
		}
		jeffrey.setY(jeffrey.getY() - 3);
		jeffrey.setY(jeffrey.getY() + 1);
		if (Room.isColliding(jeffrey, "Ladder.png:0") && !j.onLadder && jeffrey.vy>0) {
			j.vy = 0;
			jeffrey.isJumping = false;
		} 
		jeffrey.setY(jeffrey.getY() - 1);
		if (j.onLadder && (keyPressed (32))){
			j.onLadder = false;
		}
	}
}

