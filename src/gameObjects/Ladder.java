package gameObjects;

import main.GameCode;
import main.GameObject;
import main.ObjectHandler;
import map.MapTile;
import map.MapTileCollision;
import map.Room;
import map.TileData;
import players.Player;

public class Ladder extends GameObject {
	int timer = 0;
	public Ladder () {
	}
	//TODO rewrite using static logic
	public void frameEvent () {
		try {
		if (Room.isColliding(Player.getActivePlayer(), "Ladder.0")&& keyPressed('W')) {
			Player.getActivePlayer().onLadder = true;
			Player.getActivePlayer().setVy(0);
			Player.getActivePlayer().vx = 0;
			MapTile[] ladder = Room.getCollidingTiles(Player.getActivePlayer(), "Ladder.0");
			Player.getActivePlayer().setX(ladder[0].x);
			
		}
		if (Player.getActivePlayer().onLadder && keyDown ('W') && Room.isColliding(Player.getActivePlayer(), "Ladder.0")) {
			Player.getActivePlayer().setY(Player.getActivePlayer().getY() -3);
			if (!Room.isColliding(Player.getActivePlayer(), "Ladder.0")) {
				Player.getActivePlayer().setY(Player.getActivePlayer().getY() +3);
			}
		}
		Player.getActivePlayer().setY(Player.getActivePlayer().getY() + 3);
		if (Player.getActivePlayer().onLadder && keyDown ('S') && Room.isColliding(Player.getActivePlayer(), "Ladder.0")) {
			Player.getActivePlayer().setY(Player.getActivePlayer().getY() + 3);
			if (!Room.isColliding(Player.getActivePlayer(), "Ladder.0")) {
				Player.getActivePlayer().setY(Player.getActivePlayer().getY() -3);
			}
		}
		Player.getActivePlayer().setY(Player.getActivePlayer().getY() - 3);
		Player.getActivePlayer().setY(Player.getActivePlayer().getY() + 1);
		if (Room.isColliding(Player.getActivePlayer(), "Ladder.0") && !Player.getActivePlayer().onLadder && Player.getActivePlayer().getVy()>0) {
			Player.getActivePlayer().setVy(0);
			Player.getActivePlayer().isJumping = false;
		} 
		//attempt to fix the 1 frame ladder glich
		if (Player.getActivePlayer().onLadder && !Room.isColliding(Player.getActivePlayer(), "Ladder.0")) {
			Player.getActivePlayer().isJumping = false;
		} 
		Player.getActivePlayer().setY(Player.getActivePlayer().getY() - 1);
		if (Player.getActivePlayer().onLadder && (keyPressed (32))){
			Player.getActivePlayer().onLadder = false;
			Player.getActivePlayer().isJumping = true;
		}
		} catch (NullPointerException e) {
			
		}
	}
}

