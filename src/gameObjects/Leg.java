package gameObjects;

import main.GameObject;

import java.util.Random;

import main.AnimationHandler;
import main.GameCode;
import resources.Sprite;

public class Leg extends GameObject{
	Sprite crawlleft;
	boolean flipin;
	Sprite idle;
	int timer;
	boolean wouldOfBeenFlipped;
	boolean red;
	boolean reallyScootin;
	AnimationHandler handler;
	boolean onWall;
	boolean scootin;
	Sprite flipingSprite;
	Sprite LongTopRightGreenIdle;
	Sprite LongTopRightGreenMoveing;
	Sprite LongTopRightGreenFliping;
	Sprite LongTopRightGreenWall;
	Sprite LongTopRightRedIdle;
	Sprite LongTopRightRedMoveing;
	Sprite LongTopRightRedFliping;
	Sprite LongTopRightRedWall;
	Sprite LongBottomRightGreenIdle;
	Sprite LongBottomRightGreenMoveing;
	Sprite LongBottomRightGreenFliping;
	Random RNG;
	Sprite LongBottomRightGreenWall;
	Sprite LongBottomRightRedIdle;
	Sprite LongBottomRightRedMoveing;
	Sprite LongBottomRightRedFliping;
	Sprite LongBottomRightRedWall;
	Sprite LongTopLeftGreenIdle;
	Sprite LongTopLeftGreenMoveing;
	Sprite LongTopLeftGreenFliping;
	Sprite LongTopLeftGreenWall;
	Sprite LongTopLeftRedIdle;
	Sprite LongTopLeftRedMoveing;
	Sprite LongTopLeftRedFliping;
	Sprite LongTopLeftRedWall;
	boolean deattached;
	Sprite LongBottomLeftGreenIdle;
	Sprite FinalSprite;
	int finalx;
	Sprite LongBottomLeftGreenMoveing;
	Sprite LongBottomLeftGreenFliping;
	Sprite LongBottomLeftGreenWall;
	Sprite LongBottomLeftRedIdle;
	Sprite LongBottomLeftRedMoveing;
	Sprite LongBottomLeftRedFliping;
	boolean finalflip;
	Sprite LongBottomLeftRedWall;
	Sprite ShortTopRightGreenIdle;
	Sprite ShortTopRightGreenMoveing;
	Sprite ShortTopRightGreenFliping;
	Sprite ShortTopRightGreenWall;
	Sprite ShortTopRightRedIdle;
	Sprite ShortTopRightRedMoveing;
	Sprite ShortTopRightRedFliping;
	Sprite ShortTopRightRedWall;
	Sprite ShortBottomRightGreenIdle;
	Sprite ShortBottomRightGreenMoveing;
	Sprite ShortBottomRightGreenFliping;
	Sprite ShortBottomRightGreenWall;
	Sprite ShortBottomRightRedIdle;
	int legShit;
	Sprite ShortBottomRightRedMoveing;
	Sprite ShortBottomRightRedFliping;
	Sprite ShortBottomRightRedWall;
	Sprite ShortTopLeftGreenIdle;
	Sprite ShortTopLeftGreenMoveing;
	Sprite ShortTopLeftGreenFliping;
	Sprite ShortTopLeftGreenWall;
	Sprite ShortTopLeftRedIdle;
	Sprite ShortTopLeftRedMoveing;
	int speed;
	Sprite ShortTopLeftRedFliping;
	Sprite ShortTopLeftRedWall;
	Sprite ShortBottomLeftGreenIdle;
	Sprite ShortBottomLeftGreenMoveing;
	Sprite ShortBottomLeftGreenFliping;
	Sprite ShortBottomLeftGreenWall;
	Sprite ShortBottomLeftRedIdle;
	Sprite ShortBottomLeftRedMoveing;
	Sprite ShortBottomLeftRedFliping;
	Sprite ShortBottomLeftRedWall;
	public Leg(int legNumber) {
		LongTopRightGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Right_Leg_Idle.txt");
		LongTopRightGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Right_Leg_Moveing.txt");
		LongTopRightGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Right_Leg_Fliping.txt");
		LongTopRightGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Right_Leg_Wall.txt");
		LongTopRightRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Right_Leg_Idle.txt");
		LongTopRightRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Right_Leg_Moveing.txt");
		LongTopRightRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Right_Leg_Fliping.txt");
		LongTopRightRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Right_Leg_Wall.txt");
		LongBottomRightGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Right_Leg_Idle.txt");
		LongBottomRightGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Right_Leg_Moveing.txt");
		LongBottomRightGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Right_Leg_Fliping.txt");
		LongBottomRightGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Right_Leg_Wall.txt");
		LongBottomRightRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Right_Leg_Idle.txt");
		LongBottomRightRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Right_Leg_Moveing.txt");
		LongBottomRightRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Right_Leg_Fliping.txt");
		LongBottomRightRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Right_Leg_Wall.txt");
		LongTopLeftGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Left_Leg_Idle.txt");
		LongTopLeftGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Left_Leg_Moveing.txt");
		LongTopLeftGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Left_Leg_Fliping.txt");
		LongTopLeftGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Green_Left_Leg_Wall.txt");
		LongTopLeftRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Left_Leg_Idle.txt");
		LongTopLeftRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Left_Leg_Moveing.txt");
		LongTopLeftRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Left_Leg_Fliping.txt");
		LongTopLeftRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Top_Red_Left_Leg_Wall.txt");
		LongBottomLeftGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Left_Leg_Idle.txt");
		LongBottomLeftGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Left_Leg_Moveing.txt");
		LongBottomLeftGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Left_Leg_Fliping.txt");
		LongBottomLeftGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Green_Left_Leg_Wall.txt");
		LongBottomLeftRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Left_Leg_Idle.txt");
		LongBottomLeftRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Left_Leg_Moveing.txt");
		LongBottomLeftRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Left_Leg_Fliping.txt");
		LongBottomLeftRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Long_Bottom_Red_Left_Leg_Wall.txt");
		ShortTopRightGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Right_Leg_Idle.txt");
		ShortTopRightGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Right_Leg_Moving.txt");
		ShortTopRightGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Right_Leg_Fliping.txt");
		ShortTopRightGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Right_Leg_Wall.txt");
		ShortTopRightRedIdle= new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Right_Leg_Idle.txt");
		ShortTopRightRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Right_Leg_Moveing.txt");
		ShortTopRightRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Right_Leg_Fliping.txt");
		ShortTopRightRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Right_Leg_Wall.txt");
		ShortBottomRightGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Right_Leg_Idle.txt");
		ShortBottomRightGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Right_Leg_Moveing.txt");
		ShortBottomRightGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Right_Leg_Fliping.txt");
		ShortBottomRightGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Right_Leg_Wall.txt");
		ShortBottomRightRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Right_Leg_Idle.txt");
		ShortBottomRightRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Right_Leg_Moveing.txt");
		ShortBottomRightRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Right_Leg_Fliping.txt");
		ShortBottomRightRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Right_Leg_Wall.txt");
		ShortTopLeftGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Left_Leg_Idle.txt");
		ShortTopLeftGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Left_Leg_Moveing.txt");
		ShortTopLeftGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Left_Leg_Fliping.txt");
		ShortTopLeftGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Green_Left_Leg_Wall.txt");
		ShortTopLeftRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Left_Leg_Idle.txt");
		ShortTopLeftRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Left_Leg_Moveing.txt");
		ShortTopLeftRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Left_Leg_Fliping.txt");
		ShortTopLeftRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Top_Red_Left_Leg_Wall.txt");
		ShortBottomLeftGreenIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Left_Leg_Idle.txt");
		ShortBottomLeftGreenMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Left_Leg_Moveing.txt");
		ShortBottomLeftGreenFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Left_Leg_Fliping.txt");
		ShortBottomLeftGreenWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Green_Left_Leg_Wall.txt");
		ShortBottomLeftRedIdle = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Left_Leg_Idle.txt");
		ShortBottomLeftRedMoveing = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Left_Leg_Moveing.txt");
		ShortBottomLeftRedFliping = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Left_Leg_Fliping.txt");
		ShortBottomLeftRedWall = new Sprite ("resources/sprites/config/Tomato function/Tomato_Function_Short_Bottom_Red_Left_Leg_Wall.txt");
		reallyScootin = false;
		RNG = new Random();
		deattached = false;
		red = false;
		speed = 3;
		flipin = false;
		wouldOfBeenFlipped = false;
		switch (legNumber) {
		case 1:
			this.setSprite(LongTopLeftGreenIdle);
			break;
		case 2:
			this.setSprite(ShortTopLeftGreenIdle);
			break;
		case 3:
			this.setSprite(ShortTopRightGreenIdle);
			break;
		case 4:
			this.setSprite(LongTopRightGreenIdle);
			break;
		case 5:
			this.setSprite(LongBottomLeftGreenIdle);
			break;
		case 6:
			this.setSprite(ShortBottomLeftGreenIdle);
			break;
		case 7:
			this.setSprite(ShortBottomRightGreenIdle);
			break;
		case 8:
			this.setSprite(LongBottomRightGreenIdle);
			break;
		}
		
		scootin = false;
		legShit = legNumber;
		timer = 0;
		setHitboxAttributes(0,0, 48, 89);
		getAnimationHandler ().setFrameTime (112.5);
	}
	public void turnRed () {
		red = true;
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftRedIdle);
			break;
		case 2:
			this.setSprite(ShortTopLeftRedIdle);
			break;
		case 3:
			this.setSprite(ShortTopRightRedIdle);
			break;
		case 4:
			this.setSprite(LongTopRightRedIdle);
			break;
		case 5:
			this.setSprite(LongBottomLeftRedIdle);
			break;
		case 6:
			this.setSprite(ShortBottomLeftRedIdle);
			break;
		case 7:
			this.setSprite(ShortBottomRightRedIdle);
			break;
		case 8:
			this.setSprite(LongBottomRightRedIdle);
			break;
		}
		
	}
	public boolean isAttached () {
		return !deattached;
	}
	public void setSpeed (int newSpeed) {
		speed = newSpeed;
	}
	public void deattach () {
		deattached = true;
		FinalSprite = this.getSprite();
		finalx = (int) this.getX();
		finalflip = getAnimationHandler().flipVertical();
		
	}
	public void turnGreen() {
		red = false;
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftGreenIdle);
			break;
		case 2:
			this.setSprite(ShortTopLeftGreenIdle);
			break;
		case 3:
			this.setSprite(ShortTopRightGreenIdle);
			break;
		case 4:
			this.setSprite(LongTopRightGreenIdle);
			break;
		case 5:
			this.setSprite(LongBottomLeftGreenIdle);
			break;
		case 6:
			this.setSprite(ShortBottomLeftGreenIdle);
			break;
		case 7:
			this.setSprite(ShortBottomRightGreenIdle);
			break;
		case 8:
			this.setSprite(LongBottomRightGreenIdle);
			break;
		}
	}
	public void moveLeft () {
		wouldOfBeenFlipped = false;
		if (!red) {
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftGreenMoveing);
			break;
		case 2:
			this.setSprite(ShortTopLeftGreenMoveing);
			break;
		case 3:
			this.setSprite(ShortTopRightGreenMoveing);
			break;
		case 4:
			this.setSprite(LongTopRightGreenMoveing);
			break;
		case 5:
			this.setSprite(LongBottomLeftGreenMoveing);
			break;
		case 6:
			this.setSprite(ShortBottomLeftGreenMoveing);
			break;
		case 7:
			this.setSprite(ShortBottomRightGreenMoveing);
			break;
		case 8:
			this.setSprite(LongBottomRightGreenMoveing);
			break;
		}
		} else {
			switch (legShit) {
			case 1:
				this.setSprite(LongTopLeftRedMoveing);
				break;
			case 2:
				this.setSprite(ShortTopLeftRedMoveing);
				break;
			case 3:
				this.setSprite(ShortTopRightRedMoveing);
				break;
			case 4:
				this.setSprite(LongTopRightRedMoveing);
				break;
			case 5:
				this.setSprite(LongBottomLeftRedMoveing);
				break;
			case 6:
				this.setSprite(ShortBottomLeftRedMoveing);
				break;
			case 7:
				this.setSprite(ShortBottomRightRedMoveing);
				break;
			case 8:
				this.setSprite(LongBottomRightRedMoveing);
				break;
			}
		}
		
		scootin = true;
		timer = 0;
		}
	public void moveRight () {
		wouldOfBeenFlipped = true;
		if (!red) {
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftGreenMoveing);
			break;
		case 2:
			this.setSprite(ShortTopLeftGreenMoveing);
			break;
		case 3:
			this.setSprite(ShortTopRightGreenMoveing);
			break;
		case 4:
			this.setSprite(LongTopRightGreenMoveing);
			break;
		case 5:
			this.setSprite(LongBottomLeftGreenMoveing);
			break;
		case 6:
			this.setSprite(ShortBottomLeftGreenMoveing);
			break;
		case 7:
			this.setSprite(ShortBottomRightGreenMoveing);
			break;
		case 8:
			this.setSprite(LongBottomRightGreenMoveing);
			break;
		}
	} else {
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftRedMoveing);
			break;
		case 2:
			this.setSprite(ShortTopLeftRedMoveing);
			break;
		case 3:
			this.setSprite(ShortTopRightRedMoveing);
			break;
		case 4:
			this.setSprite(LongTopRightRedMoveing);
			break;
		case 5:
			this.setSprite(LongBottomLeftRedMoveing);
			break;
		case 6:
			this.setSprite(ShortBottomLeftRedMoveing);
			break;
		case 7:
			this.setSprite(ShortBottomRightRedMoveing);
			break;
		case 8:
			this.setSprite(LongBottomRightRedMoveing);
			break;
		}
	}
		scootin = true;
		timer = 0;
	}
	public void keepMovingRight(){
		reallyScootin = true;
		this.moveRight();
	}
	public void keepMovingLeft(){
		reallyScootin = true;
		this.moveLeft();
	}
	public void flip () {
		if (!red) {
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftGreenFliping);
			break;
		case 2:
			this.setSprite(ShortTopLeftGreenFliping);
			break;
		case 3:
			this.setSprite(ShortTopRightGreenFliping);
			break;
		case 4:
			this.setSprite(LongTopRightGreenFliping);
			break;
		case 5:
			this.setSprite(LongBottomLeftGreenFliping);
			break;
		case 6:
			this.setSprite(ShortBottomLeftGreenFliping);
			break;
		case 7:
			this.setSprite(ShortBottomRightGreenFliping);
			break;
		case 8:
			this.setSprite(LongBottomRightGreenFliping);
			break;
		}
	} else {
		switch (legShit) {
		case 1:
			this.setSprite(LongTopLeftRedIdle);
			break;
		case 2:
			this.setSprite(ShortTopLeftRedIdle);
			break;
		case 3:
			this.setSprite(ShortTopRightRedIdle);
			break;
		case 4:
			this.setSprite(LongTopRightRedIdle);
			break;
		case 5:
			this.setSprite(LongBottomLeftRedIdle);
			break;
		case 6:
			this.setSprite(ShortBottomLeftRedIdle);
			break;
		case 7:
			this.setSprite(ShortBottomRightRedIdle);
			break;
		case 8:
			this.setSprite(LongBottomRightRedIdle);
			break;
		}
		}
		flipin = true; 
		timer = 0;
		}
	public void stop() {
		this.reallyScootin = false;
	}
	@Override
	public void frameEvent() {
		if (scootin && timer == 5) {
			if (wouldOfBeenFlipped) {
				if (reallyScootin) {
					this.setX(this.getX() + speed);
				}else {
				this.setX(this.getX() + speed);
				}
				if (!red) {
					switch (legShit) {
					case 1:
						this.setSprite(LongTopLeftGreenIdle);
						break;
					case 2:
						this.setSprite(ShortTopLeftGreenIdle);
						break;
					case 3:
						this.setSprite(ShortTopRightGreenIdle);
						break;
					case 4:
						this.setSprite(LongTopRightGreenIdle);
						break;
					case 5:
						this.setSprite(LongBottomLeftGreenIdle);
						break;
					case 6:
						this.setSprite(ShortBottomLeftGreenIdle);
						break;
					case 7:
						this.setSprite(ShortBottomRightGreenIdle);
						break;
					case 8:
						this.setSprite(LongBottomRightGreenIdle);
						break;
					}
					} else {
					switch (legShit) {
					case 1:
						this.setSprite(LongTopLeftRedIdle);
						break;
					case 2:
						this.setSprite(ShortTopLeftRedIdle);
						break;
					case 3:
						this.setSprite(ShortTopRightRedIdle);
						break;
					case 4:
						this.setSprite(LongTopRightRedIdle);
						break;
					case 5:
						this.setSprite(LongBottomLeftRedIdle);
						break;
					case 6:
						this.setSprite(ShortBottomLeftRedIdle);
						break;
					case 7:
						this.setSprite(ShortBottomRightRedIdle);
						break;
					case 8:
						this.setSprite(LongBottomRightRedIdle);
						break;
					}
					}
				scootin = false;
				timer = 0;
				if (reallyScootin) {
					this.moveRight();
				}
			} else {
				if (reallyScootin) {
					this.setX(this.getX() - speed);
				}else {
				this.setX(this.getX() - speed);
				}
				if (!red) {
					switch (legShit) {
					case 1:
						this.setSprite(LongTopLeftGreenIdle);
						break;
					case 2:
						this.setSprite(ShortTopLeftGreenIdle);
						break;
					case 3:
						this.setSprite(ShortTopRightGreenIdle);
						break;
					case 4:
						this.setSprite(LongTopRightGreenIdle);
						break;
					case 5:
						this.setSprite(LongBottomLeftGreenIdle);
						break;
					case 6:
						this.setSprite(ShortBottomLeftGreenIdle);
						break;
					case 7:
						this.setSprite(ShortBottomRightGreenIdle);
						break;
					case 8:
						this.setSprite(LongBottomRightGreenIdle);
						break;
					}
					} else {
					switch (legShit) {
					case 1:
						this.setSprite(LongTopLeftRedIdle);
						break;
					case 2:
						this.setSprite(ShortTopLeftRedIdle);
						break;
					case 3:
						this.setSprite(ShortTopRightRedIdle);
						break;
					case 4:
						this.setSprite(LongTopRightRedIdle);
						break;
					case 5:
						this.setSprite(LongBottomLeftRedIdle);
						break;
					case 6:
						this.setSprite(ShortBottomLeftRedIdle);
						break;
					case 7:
						this.setSprite(ShortBottomRightRedIdle);
						break;
					case 8:
						this.setSprite(LongBottomRightRedIdle);
						break;
					}
					}
				scootin = false;
				timer = 0;
			
			if (reallyScootin) {
				this.moveLeft();
			}
			}
		}
		
		if (flipin && timer == 5 ) {
			timer = 0;
			flipin = false;
			if (!red) {
			switch (legShit) {
			case 1:
				this.setSprite(LongTopLeftGreenIdle);
				break;
			case 2:
				this.setSprite(ShortTopLeftGreenIdle);
				break;
			case 3:
				this.setSprite(ShortTopRightGreenIdle);
				break;
			case 4:
				this.setSprite(LongTopRightGreenIdle);
				break;
			case 5:
				this.setSprite(LongBottomLeftGreenIdle);
				break;
			case 6:
				this.setSprite(ShortBottomLeftGreenIdle);
				break;
			case 7:
				this.setSprite(ShortBottomRightGreenIdle);
				break;
			case 8:
				this.setSprite(LongBottomRightGreenIdle);
				break;
			}
			} else {
			switch (legShit) {
			case 1:
				this.setSprite(LongTopLeftRedIdle);
				break;
			case 2:
				this.setSprite(ShortTopLeftRedIdle);
				break;
			case 3:
				this.setSprite(ShortTopRightRedIdle);
				break;
			case 4:
				this.setSprite(LongTopRightRedIdle);
				break;
			case 5:
				this.setSprite(LongBottomLeftRedIdle);
				break;
			case 6:
				this.setSprite(ShortBottomLeftRedIdle);
				break;
			case 7:
				this.setSprite(ShortBottomRightRedIdle);
				break;
			case 8:
				this.setSprite(LongBottomRightRedIdle);
				break;
			}
			}
			if (!getAnimationHandler().flipVertical()) {
				this.setY(this.getY() + 32);
			} 
			getAnimationHandler().setFlipVertical(!getAnimationHandler().flipVertical());
		}
		timer = timer + 1;
		if (red & !deattached) {
			if (this.isColliding(GameCode.testJeffrey)) {
				GameCode.testJeffrey.damage(RNG.nextInt(30) + 10);
			}
		}
		if (deattached) {
			this.setSprite(FinalSprite);
			this.setX(finalx);
			
		}
	}
	}
