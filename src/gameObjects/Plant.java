package gameObjects;

import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Plant extends BreakableObject {
	
	private boolean inPot = false;
	
	private boolean inzialize = true;
	
	private boolean broken = false;
	
	private Jeffrey j = (Jeffrey)ObjectHandler.getObjectsByName("Jeffrey").get(0);
	
	private int despawnTimer = 0;
	
	private int originX;
	private int originY;
	
	private static final Sprite J_IDLE = new Sprite ("resources/sprites/config/Plant/J_Idle.txt");
	private static final Sprite R_IDLE = new Sprite ("resources/sprites/config/Plant/R_Idle.txt");
	private static final Sprite S_IDLE = new Sprite ("resources/sprites/config/Plant/S_Idle.txt");
	
	private static final Sprite J_WALK = new Sprite ("resources/sprites/config/Plant/J_Walking.txt");
	private static final Sprite R_WALK = new Sprite ("resources/sprites/config/Plant/R_Walking.txt");
	private static final Sprite S_WALK = new Sprite ("resources/sprites/config/Plant/S_Walking.txt");
	
	private static final Sprite J_DOWN = new Sprite ("resources/sprites/config/Plant/J_Down.txt");
	private static final Sprite R_DOWN = new Sprite ("resources/sprites/config/Plant/R_Down.txt");
	private static final Sprite S_DOWN = new Sprite ("resources/sprites/config/Plant/S_Down.txt");
	
	
	private static final Sprite J_UP = new Sprite ("resources/sprites/config/Plant/J_Up.txt");
	private static final Sprite S_UP = new Sprite ("resources/sprites/config/Plant/S_Up.txt");
	private static final Sprite R_UP = new Sprite ("resources/sprites/config/Plant/R_Up.txt");
	
	
	public Plant () {
		this.setSprite(J_IDLE);
		this.getAnimationHandler().setFrameTime(40);
		this.setHitboxAttributes(0,18,16,30);
		this.adjustHitboxBorders();
	}
	public boolean isExposed() {
		return !broken && !(this.getSprite().equals(J_IDLE) || this.getSprite().equals(R_IDLE) || this.getSprite().equals(S_IDLE));
	}
	@Override 
	public void frameEvent () {
		if (!broken) {
			if (inzialize) {
				this.setY(this.getY() - 35);
				inzialize = false;
			}
			this.goY(this.getY() + 2);
			if (j.isColliding(this) && !inPot) {
				originX = (int) this.getX();
				originY = (int) this.getY();
				if (j.witchCharictar == 0) {
					this.setSprite(J_IDLE);
				}
				if (j.witchCharictar == 1) {
					this.setSprite(S_IDLE);
				}
				if (j.witchCharictar == 2) {
					this.setSprite(R_IDLE);
				}
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
				if (keyDown('D') || keyDown ('A')) {
					if (!(this.getSprite().equals(R_UP) || this.getSprite().equals(J_UP) || this.getSprite().equals(S_UP))) {
						if (this.getSprite().equals(J_DOWN) || this.getSprite().equals(J_IDLE)) {
							this.setSprite(J_UP);
							this.setHitboxAttributes(0,0,16,48);
						}
						if (this.getSprite().equals(R_DOWN) || this.getSprite().equals(R_IDLE)) {
							this.setSprite(R_UP);
							this.setHitboxAttributes(0,0,16,48);
						}
						if (this.getSprite().equals(S_DOWN) || this.getSprite().equals(S_IDLE)) {
							this.setSprite(S_UP);
							this.setHitboxAttributes(0,0,16,48);
						}
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
					} else {
						if (this.getAnimationHandler().getFrame() == 7) {
							if (this.getSprite().equals(J_UP)) {
								this.setSprite(J_WALK);
							}
							if (this.getSprite().equals(S_UP)) {
								this.setSprite(S_WALK);
							}
							if (this.getSprite().equals(R_UP)) {
								this.setSprite(R_WALK);
							}
						}
					}
				} else {
					if (this.getSprite().equals(J_UP) || this.getSprite().equals(J_WALK)) {
						this.setSprite(J_DOWN);
					}
					if (this.getSprite().equals(S_UP) || this.getSprite().equals(S_WALK)) {
						this.setSprite(S_DOWN);
					}
					if (this.getSprite().equals(R_UP) || this.getSprite().equals(R_WALK)) {
						this.setSprite(R_DOWN);
					}
					if (this.getAnimationHandler().getFrame() == 3) {
						if (this.getSprite().equals(J_DOWN)) {
							this.setSprite(J_IDLE);
							this.setHitboxAttributes(0,18,16,30);
						}
						if (this.getSprite().equals(S_DOWN)) {
							this.setSprite(S_IDLE);
							this.setHitboxAttributes(0,18,16,30);
						}
						if (this.getSprite().equals(R_DOWN)) {
							this.setSprite(R_IDLE);
							this.setHitboxAttributes(0,18,16,30);
						}
					}
				}
			}
			if (inPot) {
				try {
				if (this.isCollidingChildren("Enemy") || this.isCollidingChildren("Projectile")) {
					this.makeBroken();
				}
			} catch (NullPointerException e) {
				
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
		this.setSprite(new Sprite ("resources/sprites/Broken_Plant.png"));
		Plant plant = new Plant();
		plant.declare(originX, originY);
	}
}
