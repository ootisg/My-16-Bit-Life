package gameObjects;

import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class Glider extends EnterableObject {
	
	
	private double currentSpeed = 0;
	private double ySpeed = 2;
	
	private boolean goingRight = true;
	private boolean falling = false;
	
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
		super.frameEvent();
		
		this.goX(this.getX() + currentSpeed);
		if (!this.isBroken) {
			if (this.goY(this.getY() + ySpeed) && !falling && ySpeed > 0) {
				falling = true;
				if (this.keyDown('A') && !this.keyDown('D')) {
					goingRight = false;
				}
				if (this.keyDown('D') && !this.keyDown('A')) {
						goingRight = true;
				}
			} else {
				if (!this.checkY(this.getY() + ySpeed) && ySpeed > 0) {
					falling = false;
				}
			}
			if (ySpeed < 2) {
				ySpeed = ySpeed + 0.1;
			}
			if (inside) {
					if (falling) {
						if (this.keyDown('A') && !this.keyDown('D')) {
							if (!goingRight) {
								currentSpeed = currentSpeed - .1;
								if (!this.getAnimationHandler().flipHorizontal()) {
									this.getAnimationHandler().setFlipHorizontal(true);
								}
							} else {
								if (falling) {
									currentSpeed = currentSpeed - .55;
									if (currentSpeed > 0) {
										ySpeed = ySpeed-0.5;
									} else {
										currentSpeed = currentSpeed + .55;	
									}
								}
							}
						}
						if (this.keyDown('D') && !this.keyDown('A')) {
							if (goingRight) {
								currentSpeed = currentSpeed + .1;
								if (this.getAnimationHandler().flipHorizontal()) {
									this.getAnimationHandler().setFlipHorizontal(false);
								}
							} else {
								if (falling) {
									currentSpeed = currentSpeed +.55;
									if (currentSpeed < 0) {
										ySpeed = ySpeed-0.5;
									} else {
										currentSpeed = currentSpeed - .55;	
									}
								}
							}
						}
					} else {
						if (this.keyDown('D') && !this.keyDown('A')) {
							this.getAnimationHandler().setFlipHorizontal(false);
							currentSpeed = 1;
						} else {
							if (this.keyDown('A') && !this.keyDown('D')) {
								this.getAnimationHandler().setFlipHorizontal(true);
								currentSpeed = -1;
							} else {
								currentSpeed = 0;
							}
						}
					}
				}
		} else {
		this.despawnAllCoolLike(200);
		}
	}
	@Override 
	public void onEntry () {
		originX = (int) this.getX();
		originY = (int) this.getY();
		Player.getActivePlayer().getAnimationHandler().hide();
		Player.getActivePlayer().blackList();
		this.inside = true;
	}
	@Override
	public void onBreak () {
		this.makeBroken();
	}
	public void makeBroken () {
		this.isBroken = true;
		this.exit();
		Player.getActivePlayer().setY(Player.getActivePlayer().getY() - 16);
		this.Break(new Shard [] {new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard1.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard2.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard3.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard4.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard5.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard6.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard7.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard8.txt")), new Shard (new Sprite ("resources/sprites/config/Plant/shards/shard9.txt"))},this.getX(),this.getY() + 18, 9, 2, 4, 0, 3.14);
		Glider plant = new Glider();
		plant.declare(originX, originY);
	}
}
