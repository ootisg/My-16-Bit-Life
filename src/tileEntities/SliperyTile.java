package tileEntities;

import main.GameObject;
import map.TileEntitiy;
import players.Player;

public class SliperyTile extends TileEntitiy {
	public SliperyTile () {
		
	}
	@Override
	public void onCollision (GameObject o) {
		if (o.getClass().getSimpleName().equals("Player")) {
			if (!Player.getActivePlayer().status.checkStatus("Slippery")) {
				Player.getActivePlayer().status.applyStatus("Slippery",1);
			}
		}
	}
}
