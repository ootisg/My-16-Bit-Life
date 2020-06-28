package tileEntities;

import gameObjects.Extinguisable;
import main.GameObject;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class BurrningBrick extends AnimatedBlock implements Extinguisable {
	boolean extinguesed = false;
	boolean inzialized = false;
	public BurrningBrick () {

	}
	@Override 
	public void frameEvent () {
		super.frameEvent();
		if (!inzialized) {
		this.setSprite(new Sprite ("resources/sprites/config/burrningBrick.txt"));
		inzialized = true;
		}
	}
	@Override
	public void onCollision(GameObject o) {
		if (!extinguesed) {
			if (o instanceof Jeffrey) {
				Jeffrey j = (Jeffrey) o;
				j.damage(10);
			}
		}
	}
	@Override
	public void extinguse() {
		extinguesed = true;
		this.setSprite(new Sprite ("resources/sprites/extinutesedBlock.png"));
		Room.getChungus(this.getX(), this.getY()).invalidate(this.getLayer());
	}
}
