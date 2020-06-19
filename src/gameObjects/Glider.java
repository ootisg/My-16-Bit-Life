package gameObjects;

import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Glider extends BreakableObject {
	private boolean inPot = false;
	
	private boolean inzialize = true;
	
	private boolean broken = false;
	
	private Jeffrey j = (Jeffrey)ObjectHandler.getObjectsByName("Jeffrey").get(0);
	
	
	private int originX;
	private int originY;
	
	public Glider () {
		this.setSprite(new Sprite ("resources/sprites/Glider.png"));
		this.getAnimationHandler().setFrameTime(40);
		this.setHitboxAttributes(0,0,16,16);
		this.adjustHitboxBorders();
	}
	@Override 
	public void frameEvent () {
		if (!broken) {
			this.goY(this.getY() + 2);
			if (j.isColliding(this) && !inPot) {
				originX = (int) this.getX();
				originY = (int) this.getY();
				j.getAnimationHandler().hide();
				j.blackList();
				inPot = true;
			}
			if (inPot) {
				double x = this.getX ();
				double y = this.getY ();
				int viewX = Room.getViewX ();
				int viewY = Room.getViewY ();
				if (y - viewY >= 320 && y - 320 < Room.getHeight () * 16 - 480) {
					viewY = (int) y - 320;
					Room.setView (Room.getViewX (), viewY);
				}
				if (y - viewY <= 160 && y - 160 > 0) {
					viewY = (int) y - 160;
					Room.setView (Room.getViewX (), viewY);
				}
				if (x - viewX >= 427 && x - 427 < Room.getWidth () * 16 - 640) {
					viewX = (int) x - 427;
					Room.setView (viewX, Room.getViewY ());
				}
				if (x - viewX <= 213 && x - 213 > 0) {
					viewX = (int) x - 213;
					Room.setView (viewX, Room.getViewY ());
				}
				j.setX(this.getX());
				j.setY(this.getY());
					
						if (this.keyDown('A') && !this.keyDown('D')) {
							if (!this.getAnimationHandler().flipHorizontal()) {
								this.getAnimationHandler().setFlipHorizontal(true);
							}
							this.goX(this.getX() - 2);
						}
						if (this.keyDown('D') && !this.keyDown('A')) {
							if (this.getAnimationHandler().flipHorizontal()) {
								this.getAnimationHandler().setFlipHorizontal(false);
							}
							this.goX(this.getX() + 2);
						}
			if (inPot) {
				try {
				if (this.isCollidingChildren("Enemy") || this.isCollidingChildren("Projectile")) {
					this.makeBroken();
				}
			} catch (NullPointerException e) {
				
			}
			}
			}
		} else {
		this.despawnAllCoolLike(200);
		}
	}
	public void makeBroken () {
		broken = true;
		j.whiteList();
		j.getAnimationHandler().show();
		this.Break(new Sprite [] {new Sprite ("resources/sprites/config/Plant/shards/shard1.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard2.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard3.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard4.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard5.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard6.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard7.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard8.txt"), new Sprite ("resources/sprites/config/Plant/shards/shard9.txt")},this.getX(),this.getY() + 18, 9, 2, 4, 0, 3.14);
		Glider plant = new Glider();
		plant.declare(originX, originY);
	}
}
