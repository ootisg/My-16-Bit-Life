package gameObjects;

import main.AnimationHandler;
import projectiles.PokaDot;
import resources.Sprite;

public class Celing_boi extends Enemy {
	public Sprite leftToMiddle;
	public Sprite MiddleToRight;
	public Sprite RightToMiddle;
	public Sprite MiddleToLeft;
	public Sprite shootLeft;
	public Sprite shootMiddle;
	public Sprite shootRight;
	int place;
	PokaDot dot;
	int timer;
	boolean shooting;
	Sprite middleIdle;
	Sprite leftIdle;
	Sprite rightIdle;
	boolean direction;
	public Celing_boi () {
		direction = true;
		timer = 0;
		shooting = true;
		MiddleToLeft = new Sprite ("resources/sprites/config/Ceiling_Boi_moving_right_from_left.txt");
		RightToMiddle = new Sprite("resources/sprites/config/Ceiling_Boi_moving_left_from_right.txt");
		leftToMiddle = new Sprite("resources/sprites/config/Ceiling_Boi_moving_right_from_left.txt");
		MiddleToRight = new Sprite("resources/sprites/config/Ceiling_Boi_moving_right_from_middle.txt");
		shootLeft = new Sprite("resources/sprites/config/Ceiling_Boi_shooting_downLeft.txt");
		shootMiddle = new Sprite("resources/sprites/config/Ceiling_Boi_shooting_down.txt");
		shootRight = new Sprite("resources/sprites/config/Ceiling_Boi_shooting_downRight.txt");
		middleIdle = new Sprite ("resources/sprites/config/Ceiling_Boi_middle_idle.txt");
		leftIdle = new Sprite ("resources/sprites/config/Ceiling_Boi_left_Idle.txt");
		rightIdle = new Sprite ("resources/sprites/config/Ceiling_Boi_right_Idle.txt");
		this.getAnimationHandler().setFrameTime(333);
		//if (this.getVariantAttribute("startPos").equals ("left")){
		// place = 0;
		// } 
		//if (this.getVariantAttribute("startPos").equals ("middle")){
				// place = 1;
				// } 
		//if (this.getVariantAttribute("startPos").equals ("right")){
				// place = 2;
				// direction = false;
				// } 
		place = 0;
		if (place == 0) {
			this.setSprite(shootLeft);
			}
			if (place == 1) {
			this.setSprite(shootMiddle);
			}
			if (place == 2) {
			this.setSprite(shootRight);
			}
	}
	
	@Override
	public void frameEvent () {
		timer = timer + 1;
		if (timer == 3) {
			if (place == 0) {
				this.getAnimationHandler().resetImage(leftIdle);
				}
				if (place == 1) {
					this.getAnimationHandler().resetImage(middleIdle);
				}
				if (place == 2) {
					this.getAnimationHandler().resetImage(rightIdle);
				}
				this.getAnimationHandler().setFrameTime(0);
		}
		if (timer == 30) {
			timer = 0;
			if (shooting) {
				shooting = false;
				if (place == 0) {
				dot = new PokaDot (((3*3.14)/4));
				dot.declare((int) this.getX(), (int)this.getY() + 20);
				}
				if (place == 1) {
				dot = new PokaDot (((3.14)/2));
				dot.declare((int) this.getX(), (int)this.getY() + 20);
				}
				if (place == 2) {
				dot = new PokaDot (((3.14)/4));
				dot.declare((int) this.getX(), (int)this.getY() + 20);
				}
			} else {
				if (direction) {
						if (place == 2) {
						direction = false;
						}
						if (place == 1) {
							this.setSprite(MiddleToRight);
							this.getAnimationHandler().setFrameTime(333);
							shooting = true;
							place = 2;
							}
						if (place == 0) {
							shooting = true;
							this.setSprite(leftToMiddle);
							this.getAnimationHandler().setFrameTime(333);
							place = 1;
							}
				} else {
					if (place == 0) {
						direction = true;
						}
						if (place == 1) {
						shooting = true;
						this.setSprite(MiddleToLeft);
						this.getAnimationHandler().setFrameTime(333);
						place = 0;
						}
						if (place == 2) {
						shooting = true;
						this.setSprite(RightToMiddle);
						this.getAnimationHandler().setFrameTime(333);
						place = 1;
						}
				}
			}
		}
	}
}
