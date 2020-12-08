package gameObjects;

import main.GameObject;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import triggers.Checkpoint;

public class ChaseObject extends GameObject {
	boolean direction;
	int speed;
	double vy = 1;
	int damage;
	int timer = 0;
	double delayedX;
	double delayedY;
	public ChaseObject () {
		this.enablePixelCollisions();
	}
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("direction") != null) {
			if (this.getVariantAttribute("direction").equals("left")) {
				direction = false;
			} else {
				direction = true;
			}
		}
		if (this.getVariantAttribute("speed") != null) {
			speed = Integer.parseInt(this.getVariantAttribute("speed"));
		} else {
			speed = 2;
		}
		if (this.getVariantAttribute("sprite") != null) {
			this.setSprite(new Sprite (this.getVariantAttribute("sprite")));
		} else {
			this.setSprite(new Sprite ("resources/sprites/Shere.png"));
		}
		this.setHitboxAttributes(0, 0, this.getSprite().getFrame(0).getWidth(), this.getSprite().getFrame(0).getHeight());
	}
	@Override
	public void frameEvent () {
		if (!this.isVisable()) {
			timer = timer + 1;
			if (timer > 100) {
				this.setX(delayedX);
				this.setY(delayedY);
				this.show();
				if (Jeffrey.getActiveJeffrey().getX() < this.getX()){
					direction = false;
				} else {
					direction = true;
				}
			}
		}
		if (this.isVisable()) {
			if (direction) {
				if (!this.goX(this.getX() + speed)) {
					direction = !direction;
				}
			} else {
				if (!this.goX(this.getX() - speed)) {
					direction = !direction;
				}
			}
			if (this.goY(this.getY() + vy)) {
				vy = vy + Room.getGravity();
				if (vy > 15) {
					vy = 15;
				}
			} else {
				vy = 1;
			}
			if (this.isColliding(Jeffrey.getActiveJeffrey())) {
				Jeffrey.getActiveJeffrey().damage(10);
				CheckpointSystem.loadNewestCheckpoint();
			}
			if (this.isCollidingChildren("Enemy")) {
				
			}
		}
	}
	@Override
	public void checkpointCode (Checkpoint checkpoint) {
		this.hide();
		delayedX = checkpoint.getX();
		delayedY = checkpoint.getY();
	}
}
