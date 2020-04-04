package gameObjects;

import main.GameLoop;
import map.Room;
import resources.Sprite;

public class Slimelet extends Enemy {
	
	public Sprite slimeletHorizontal = new Sprite ("resources/sprites/config/slimelet_horizontal.txt");
	public Sprite slimeletVertical = new Sprite ("resources/sprites/config/slimelet_vertical.txt");
	public Sprite slimeletClimbHorizontal = new Sprite ("resources/sprites/config/slimelet_climb_horizontal.txt");
	public Sprite slimeletClimbVertical = new Sprite ("resources/sprites/config/slimelet_climb_vertical.txt");
	public Sprite slimeletAround = new Sprite ("resources/sprites/config/slimelet_around.txt");
	public Sprite slimeletOver = new Sprite ("resources/sprites/config/slimelet_over.txt");
	
	private int slimeletFrame;
	
	private byte direction;
	private byte animation;
	private int animationTimer;
	private int animationFrames;
	private int climbTimer;
	private int climbFrames;
	private int slimeX;
	private int slimeY;
	private boolean altFrame;
	private boolean converse;
	private boolean conversePrevious;
	public Slimelet () {
		//this.setSprite (slimeletClimbHorizontal);
		this.getAnimationHandler ().setFrameTime (111.11);
		this.setHitboxAttributes (0, 0, 15, 15);
		this.animationFrames = 6;
		this.climbFrames = 1;
		this.direction = 0;
		this.animation = 0;
		this.converse = false;
		this.defence = 0;
		this.health = 47;
		this.conversePrevious = false;
	}
	@Override 
	public String checkName () {
		return "SLIMELET";
	}
	@Override
	public String checkEntry () {
		return "THIS WEAK MOSTER LEAVS BEHIND A TRAIL OF SLIME IT ALSO CAN WALK ON WALLS AND CEILINGS";
	}
	@Override
	public void enemyFrame () {
		//Random numbers correspond to sprite coordinates
		int slimeOffset;
		if (this.animation != 2) {
			slimeOffset = (climbTimer + 1) / climbFrames;
		} else {
			slimeOffset = -65535;
		}
		if (!this.conversePrevious) {
			switch (this.direction) {
				case 0:
					this.setHitboxRect (6, 7, 15, 15);
					this.slimeX = 3 + slimeOffset;
					this.slimeY = 15;
					break;
				case 1:
					this.setHitboxRect (0, 7, 9, 15);
					this.slimeX = 12 - slimeOffset;
					this.slimeY = 15;
					break;
				case 2:
					this.setHitboxRect (7, 0, 15, 9);
					this.slimeX = 15;
					this.slimeY = 12 - slimeOffset;
					break;
				case 3:
					this.setHitboxRect (7, 6, 15, 15);
					this.slimeX = 15;
					this.slimeY = 3 + slimeOffset;
					break;
			}
		} else {
			switch (this.direction) {
				case 0:
					this.setHitboxRect (6, 0, 15, 8);
					this.slimeX = 3 + slimeOffset;
					this.slimeY = 0;
					break;
				case 1:
					this.setHitboxRect (0, 0, 9, 8);
					this.slimeX = 12 - slimeOffset;
					this.slimeY = 0;
					break;
				case 2:
					this.setHitboxRect (0, 0, 8, 9);
					this.slimeX = 0;
					this.slimeY = 12 - slimeOffset;
					break;
				case 3:
					this.setHitboxRect (0, 6, 8, 15);
					this.slimeX = 0;
					this.slimeY = 3 + slimeOffset;
					break;
				}
		}
		if (this.animation == 0) {
			switch (this.direction) {
				case 0:
					this.setX (this.getX () + 1);
					if (Room.isColliding (this.hitbox ())) {
						this.setX (getXPrevious ());
						this.animation = 1;
					}
					break;
				case 1:
					this.setX (this.getX () - 1);
					if (Room.isColliding (this.hitbox ())) {
						this.setX (getXPrevious ());
						this.animation = 1;
					}
					break;
				case 2:
					this.setY (this.getY () - 1);
					if (Room.isColliding (this.hitbox ())) {
						this.setY (getYPrevious ());
						this.animation = 1;
						this.converse = true;
					}
					break;
				case 3:
					this.setY (this.getY () + 1);
					if (Room.isColliding (this.hitbox ())) {
						this.setY (getYPrevious ());
						this.animation = 1;
						this.converse = false;
					}
					break;
			}
			if (!this.conversePrevious) {
				switch (this.direction) {
					case 0:
						if (!this.roomIsCollidingOffset (this.hitbox ().width, this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () + 8);
							this.setY (this.getY () + 5);
						}
						break;
					case 1:
						if (!this.roomIsCollidingOffset (-this.hitbox ().width, this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () - 8);
							this.setY (this.getY () + 5);
						}
						break;
					case 2:
						if (!this.roomIsCollidingOffset (this.hitbox ().width, -this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () + 5);
							this.setY (this.getY () - 8);
						}
						break;
					case 3:
						if (!this.roomIsCollidingOffset (this.hitbox ().width, this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () + 5);
							this.setY (this.getY () + 8);
						}
						break;
				}
			} else {
				switch (this.direction) {
					case 0:
						if (!this.roomIsCollidingOffset (this.hitbox ().width, -this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () + 8);
							this.setY (this.getY () - 5);
						}
						break;
					case 1:
						if (!this.roomIsCollidingOffset (-this.hitbox ().width, -this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () - 8);
							this.setY (this.getY () - 5);
						}
						break;
					case 2:
						if (!this.roomIsCollidingOffset (-this.hitbox ().width, -this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () - 5);
							this.setY (this.getY () - 8);
						}
						break;
					case 3:
						if (!this.roomIsCollidingOffset (-this.hitbox ().width, this.hitbox ().height) && !Room.isColliding (this.hitbox ())) {
							this.animation = 2;
							this.setX (this.getX () - 5);
							this.setY (this.getY () + 8);
						}
						break;
					}
			}
		}
		if (this.animation == 2) {
			if (!this.conversePrevious) {
				switch (this.direction) {
					case 0:
						this.setY (this.getY () + 1);
						break;
					case 1:
						this.setY (this.getY () + 1);
						break;
					case 2:
						this.setX (this.getX () + 1);
						break;
					case 3:
						this.setX (this.getX () + 1);
						break;
				}
			} else {
				switch (this.direction) {
					case 0:
						this.setY (this.getY () - 1);
						break;
					case 1:
						this.setY (this.getY () - 1);
						break;
					case 2:
						this.setX (this.getX () - 1);
						break;
					case 3:
						this.setX (this.getX () - 1);
						break;
				}
			}
		}
		new Slime ().declare (this.getX () + this.slimeX, this.getY () + this.slimeY);
	}
	@Override
	public void draw () {
		//System.out.print ("DRAW: ");
		if (animationTimer % animationFrames == animationFrames - 1) {
			altFrame = !altFrame;
			animationTimer = 0;
		}
		getAnimationHandler ().setFlipVertical (false);
		getAnimationHandler ().setFlipHorizontal (false);
		if (direction == 1) {
			getAnimationHandler ().setFlipHorizontal (true);
		}
		if (direction == 3) {
			getAnimationHandler ().setFlipVertical (true);
		}
		if ((direction == 0 || direction == 1) && conversePrevious) {
			getAnimationHandler ().setFlipVertical (true);
		}
		if ((direction == 2 || direction == 3) && conversePrevious) {
			getAnimationHandler ().setFlipHorizontal (true);
		}
		if (this.animation == 2) {
			if (climbTimer >= climbFrames * 9) {
				climbTimer = 0;
				this.animation = 0;
				//System.out.println(this.converse);
				if (this.conversePrevious) {
					switch (this.direction) {
						case 0:
							this.direction = 2;
							this.converse = true;
							this.setX (this.getX () + 7);
							this.setY (this.getY () + 4);
							break;
						case 1:
							this.direction = 2;
							this.converse = false;
							this.setX (this.getX () - 7);
							this.setY (this.getY () + 7);
							break;
						case 2:
							this.direction = 1;
							this.converse = false;
							this.setY (this.getY () - 7);
							break;
						case 3:
							this.direction = 1;
							this.converse = true;
							this.setY (this.getY () + 7);
							break;
					}
				} else {
					switch (this.direction) {
						case 0:
							this.direction = 3;
							this.converse = true;
							this.setX (this.getX () + 7);
							this.setY (this.getY () - 4);
							break;
						case 1:
							this.direction = 3;
							this.converse = false;
							this.setX (this.getX () - 7);
							this.setY (this.getY () - 4);
							break;
						case 2:
							this.direction = 0;
							this.converse = false;
							this.setY (this.getY () - 7);
							break;
						case 3:
							this.direction = 0;
							this.converse = true;
							this.setY (this.getY () + 7);
							break;
					}
				}
				this.conversePrevious = this.converse;
				getAnimationHandler ().setFlipVertical (false);
				getAnimationHandler ().setFlipHorizontal (false);
				if (direction == 1) {
					getAnimationHandler ().setFlipHorizontal (true);
				}
				if (direction == 3) {
					getAnimationHandler ().setFlipVertical (true);
				}
				if ((direction == 0 || direction == 1) && converse) {
					getAnimationHandler ().setFlipVertical (true);
				}
				if ((direction == 2 || direction == 3) && converse) {
					getAnimationHandler ().setFlipHorizontal (true);
				}
				groundAnimationStep ();
			} else {
				int frameToDraw = (climbTimer / climbFrames);
				if (this.direction == 0 || this.direction == 1) {
					slimeletFrame = frameToDraw;
					slimeletOver.draw ((int)this.getX () - Room.getViewX (), (int)this.getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
				} else {
					slimeletFrame = frameToDraw;
					slimeletAround.draw ((int)this.getX () - Room.getViewX (), (int)this.getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
				}
				climbTimer ++;
			}
		} else if (this.animation == 1) {
			//System.out.println("1");
			if (climbTimer >= climbFrames * 9) {
				climbTimer = 0;
				this.animation = 0;
				//System.out.println(this.converse);
				if (this.conversePrevious) {
					switch (this.direction) {
						case 0:
							this.direction = 3;
							this.converse = false;
							this.setY (this.getY () - 5);
							break;
						case 1:
							this.direction = 3;
							this.converse = true;
							this.setY (this.getY () - 5);
							break;
						case 2:
							this.direction = 0;
							this.converse = true;
							this.setX (this.getX () - 5);
							break;
						case 3:
							this.direction = 0;
							this.converse = false;
							this.setX (this.getX () - 5);
							break;
					}
				} else {
					switch (this.direction) {
						case 0:
							this.direction = 2;
							this.converse = false;
							this.setY (this.getY () + 5);
							break;
						case 1:
							this.direction = 2;
							this.converse = true;
							this.setY (this.getY () + 5);
							break;
						case 2:
							this.direction = 1;
							this.converse = true;
							this.setX (this.getX () + 5);
							break;
						case 3:
							this.direction = 1;
							this.converse = false;
							this.setX (this.getX () + 5);
							break;
					}
				}
				this.conversePrevious = this.converse;
				getAnimationHandler ().setFlipVertical (false);
				getAnimationHandler ().setFlipHorizontal (false);
				if (direction == 1) {
					getAnimationHandler ().setFlipHorizontal (true);
				}
				if (direction == 3) {
					getAnimationHandler ().setFlipVertical (true);
				}
				if ((direction == 0 || direction == 1) && converse) {
					getAnimationHandler ().setFlipVertical (true);
				}
				if ((direction == 2 || direction == 3) && converse) {
					getAnimationHandler ().setFlipHorizontal (true);
				}
				groundAnimationStep ();
			} else {
				int frameToDraw = 0;
				if (altFrame) {
					frameToDraw = 9;
				}
				frameToDraw += (climbTimer / climbFrames);
				if (this.direction == 0 || this.direction == 1) {
					slimeletFrame = frameToDraw;
					slimeletClimbHorizontal.draw ((int)this.getX () - Room.getViewX (), (int)this.getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
				} else {
					slimeletFrame = frameToDraw;
					slimeletClimbVertical.draw ((int)this.getX () - Room.getViewX (), (int)this.getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
				}
				climbTimer ++;
			}
		} else if (animation == 0) {
			groundAnimationStep ();
		}
		animationTimer ++;
		//MainLoop.getWindow ().getBuffer ().drawRect ((int)(this.getX () + this.getHitboxXOffset ()), (int)(this.getY () + this.getHitboxYOffset ()), this.getHitbox ().width, this.getHitbox ().height);
	}
	public void groundAnimationStep () {
		if (altFrame) {
			slimeletFrame = 1;
		} else {
			slimeletFrame = 0;
		}
		if (direction == 0 || direction == 1) {
			slimeletHorizontal.draw ((int)getX () - Room.getViewX (), (int)getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
		}
		if (direction == 2 || direction == 3) {
			slimeletVertical.draw ((int)getX () - Room.getViewX (), (int)getY () - Room.getViewY (), getAnimationHandler ().flipHorizontal (), getAnimationHandler ().flipVertical (), slimeletFrame);
		}
	}
	public void setHitboxRect (int x1, int y1, int x2, int y2) {
		setHitboxAttributes (x1, y1, x2 - x1, y2 - y1);
	}
	public boolean roomIsCollidingOffset (double offsetX, double offsetY) {
		this.setX (this.getX () + offsetX);
		this.setY (this.getY () + offsetY);
		if (Room.isColliding (this.hitbox ())) {
			setX (getXPrevious ());
			setY (getYPrevious ());
			return true;
		} else {
			setX (getXPrevious ());
			setY (getYPrevious ());
			return false;
		}
	}
}
