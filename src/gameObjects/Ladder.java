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
		
		if (Room.isColliding(Jeffrey.getActiveJeffrey(), "Ladder.0")&& keyPressed('W')) {
			Jeffrey.getActiveJeffrey().onLadder = true;
			Jeffrey.getActiveJeffrey().vy = 0;
			Jeffrey.getActiveJeffrey().vx = 0;
			MapTile[] ladder = Room.getCollidingTiles(Jeffrey.getActiveJeffrey(), "Ladder.0");
			Jeffrey.getActiveJeffrey().setX(ladder[0].x);
			
		}
		if (Jeffrey.getActiveJeffrey().onLadder && keyDown ('W') && Room.isColliding(Jeffrey.getActiveJeffrey(), "Ladder.0")) {
			Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() -3);
		}
		Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() + 3);
		if (Jeffrey.getActiveJeffrey().onLadder && keyDown ('S') && Room.isColliding(Jeffrey.getActiveJeffrey(), "Ladder.0")) {
			Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() + 3);
		}
		Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() - 3);
		Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() + 1);
		if (Room.isColliding(Jeffrey.getActiveJeffrey(), "Ladder.0") && !Jeffrey.getActiveJeffrey().onLadder && Jeffrey.getActiveJeffrey().vy>0) {
			Jeffrey.getActiveJeffrey().vy = 0;
			Jeffrey.getActiveJeffrey().isJumping = false;
		} 
		Jeffrey.getActiveJeffrey().setY(Jeffrey.getActiveJeffrey().getY() - 1);
		if (Jeffrey.getActiveJeffrey().onLadder && (keyPressed (32))){
			Jeffrey.getActiveJeffrey().onLadder = false;
		}
	}
}

