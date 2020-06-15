package enemys;

import java.util.Random;

import map.Room;
import resources.Sprite;

public class Sheep extends Enemy {
	//I will be very sad to see sheepSheet go with the new engine
	//Spritesheet sheepSheet; //sheepSheet lives on in our hearts

	boolean isDoingSomething;
	
	int IMSORANDOMRIGHT;
	
	Random RNG;
	
	boolean walkinBra;
	boolean walkinBro;
	
	int timer;
	
	private static final Sprite IDLE_SPRITE = new Sprite ("resources/sprites/config/SheepIdle.txt");
	private static final Sprite WALK_SPRITE = new Sprite ("resources/sprites/config/SheepWalking.txt");
	private static final Sprite LOOK_UP_SPRITE = new Sprite ("resources/sprites/config/SheepLookingUp.txt");
	private static final Sprite LOOK_OTHERWAY_SPRITE = new Sprite ("resources/sprites/config/SheepOtherWay.txt");
	private static final Sprite LOOK_SPRITE = new Sprite ("resources/sprites/config/SheepLook.txt");
	public Sheep () {
		this.setHitboxAttributes(0, 0, 25, 16);
		RNG = new Random();
		this.health = 100;
		this.setFalls(true);
		isDoingSomething = false;
		IMSORANDOMRIGHT = 0;
		walkinBro = false;
		walkinBra = false;
		timer = 0;
		this.defence = 0;
		this.setSprite(IDLE_SPRITE);
		this.getAnimationHandler().setFrameTime(100);
	}
	@Override
	public void enemyFrame () {
	if (!isDoingSomething) {
	IMSORANDOMRIGHT = RNG.nextInt(200);
	}
	if (walkinBro && IMSORANDOMRIGHT == 69) {
		IMSORANDOMRIGHT = 71;
	}
	if (walkinBra && IMSORANDOMRIGHT == 70) {
		IMSORANDOMRIGHT = 71;
	}
	if (IMSORANDOMRIGHT == 87) {
		if (!isDoingSomething) {
			this.getAnimationHandler().setRepeat(false);
		this.setSprite(LOOK_SPRITE);
		isDoingSomething = true;
		} 
		timer = timer + 1;
		if (timer == 60) {
			timer = 0;
			isDoingSomething = false;
			this.setSprite(IDLE_SPRITE);
		}
	}
	if (IMSORANDOMRIGHT == 85) {
		if (!isDoingSomething) {
			this.getAnimationHandler().setRepeat(false);
		this.setSprite(LOOK_OTHERWAY_SPRITE);
		isDoingSomething = true;
		} 
		timer = timer + 1;
		if (timer == 60) {
			timer = 0;
			isDoingSomething = false;
			this.setSprite(IDLE_SPRITE);
		}
	}
	if (IMSORANDOMRIGHT == 84) {
		if (!isDoingSomething) {
		this.setSprite(LOOK_UP_SPRITE);
		isDoingSomething = true;
		} 
		timer = timer + 1;
		if (timer == 60) {
			timer = 0;
			isDoingSomething = false;
			this.setSprite(IDLE_SPRITE);
		}
	}
	if (IMSORANDOMRIGHT ==  70|| walkinBro) {
		if (!this.getAnimationHandler().flipHorizontal()) {
			this.getAnimationHandler().setFlipHorizontal(true);
		}
		walkinBro = true;
		if (!this.getSprite().equals(WALK_SPRITE)) {
			this.getAnimationHandler().setRepeat(true);
		this.setSprite(WALK_SPRITE);
		}
	
		if (IMSORANDOMRIGHT < 5) {
			walkinBro = false;
			this.setSprite(IDLE_SPRITE);
		}
		if (!this.goX(this.getX () + 3)) {
			walkinBro = false;
			walkinBra = true;
		}
	}
	if (IMSORANDOMRIGHT ==  69|| walkinBra) {
		if (this.getAnimationHandler().flipHorizontal()) {
			this.getAnimationHandler().setFlipHorizontal(false);
		}
		walkinBra = true;
		if (!this.getSprite().equals(WALK_SPRITE)) {
			this.getAnimationHandler().setRepeat(true);
			this.setSprite(WALK_SPRITE);
			}
		if (IMSORANDOMRIGHT < 5) {
			walkinBra = false;
			this.setSprite(IDLE_SPRITE);
		}
		if (!this.goX(this.getX () - 3)) {
			walkinBra = false;
			walkinBro = true;
		}
	}
	}
}
