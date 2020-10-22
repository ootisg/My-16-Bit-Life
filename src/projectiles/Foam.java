package projectiles;

import enemys.Enemy;
import gameObjects.Extinguisable;
import map.Room;
import resources.Sprite;

public class Foam extends Projectile {
	boolean falling = false;
	int timer = 0;
	public Foam () {
		this.setHitboxAttributes(0,0, 7, 7);
		this.setSprite(new Sprite ("resources/sprites/foam.png"));
		this.setSpeed(20);
	}
	@Override 
	public void frameEvent () {
		timer = timer + 1;
		if (timer == 90) {
			forget ();
			return;
		}
		if (timer % 3 == 0) {
			this.setSpeed(this.getSpeed() - 5);
			
		}
		if (this.getSpeed() <= 0) {
			falling = true;
			this.setDirection(Math.PI/2);
			this.setSpeed(3);
		}
		if (this.goingIntoWall) {
			falling = true;
			this.setDirection(Math.PI/2);
		}
		if (falling && !this.checkY(this.getY() + 1)) {
			this.forget();
		}
		this.isCollidingChildren("GameObject");
		if (this.getCollisionInfo().getCollidingObjects() != null){
			for (int i= 0; i < this.getCollisionInfo().getCollidingObjects().size(); i++) {
				if (this.getCollisionInfo().getCollidingObjects().get(i) instanceof Extinguisable) {
					Extinguisable working = (Extinguisable) this.getCollisionInfo().getCollidingObjects().get(i);
					working.extinguse();
				}
			}
		}
		boolean resetX = false;
		boolean resetY = false;
		if (!this.goX(this.getX () + Math.cos (direction) * speed)) {
			this.setX (this.getX () + Math.cos (direction) * speed); 
			resetX = true;
		}
		if (!this.goY (this.getY () + Math.sin (direction) * speed)) {
			this.setY (this.getY () + Math.sin (direction) * speed);
			resetY = true;
		}
		for (int i = 0; i < Room.getCollidingTiles(this).length; i++) {	
			if (Room.getCollidingTiles(this)[i].partner instanceof Extinguisable) {
				Extinguisable working = (Extinguisable) Room.getCollidingTiles(this)[i].partner;
				working.extinguse();
			}
		}
		if (resetX) {
			this.setX (this.getX () - Math.cos (direction) * speed); 
		}
		if (resetY) {
			this.setY (this.getY () - Math.sin (direction) * speed);
		}
	}
	
}
