package tileEntities;

import main.GameObject;
import map.TileEntitiy;
import players.Jeffrey;
import statusEffect.Slippery;

public class SliperyTile extends TileEntitiy {
	public SliperyTile () {
		
	}
	@Override
	public void onCollision (GameObject o) {
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
			if (!Jeffrey.getActiveJeffrey().checkIfSlippery()) {
				Slippery slip = new Slippery (Jeffrey.getActiveJeffrey().witchCharictar);
				slip.declare();
				slip.setTime(10);
			}
		}
	}
}
