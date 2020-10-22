package tileEntities;

import java.util.Arrays;

import main.GameObject;
import main.ObjectHandler;
import map.Room;
import map.TileEntitiy;
import players.Jeffrey;

public class SpikeRight extends TileEntitiy{
	public SpikeRight() {
		super();
	}
	
	@Override
	public void onCollision(GameObject o) {	
		if (o.getClass().getSimpleName().equals("Jeffrey")) {
				Jeffrey j = (Jeffrey) o;
				if (j.vx < -3 && (j.getY() + 20 > this.getY()*16)) {
					j.damage(12);
					
				}
		}
	}
	@Override 
	public boolean doesColide (GameObject o) {
		return true;
	}
}
