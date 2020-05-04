package enemys;

import java.util.Random;

import javax.sound.sampled.Clip;

import gameObjects.Leg;
import main.GameCode;
import resources.SoundPlayer;
import resources.Sprite;

public class TomatoFunction extends Enemy {
		Leg leg1;
		Leg leg2;
		Leg leg3;
		Leg leg4;
		Leg leg5;
		Random RNG;
		Clip clip;
		Leg leg6;
		boolean decalre = true;
		int patternNumber;
		int shouldFlip;
		boolean jeffreyOnYourRight;
		Leg leg7;
		int movementTimer;
		SoundPlayer player;
		Leg leg8;
		int timer;
	public TomatoFunction (int x, int y) {
		this.health = 2500;
		this.defence = 0;
		this.setSprite(new Sprite ("resources/sprites/Tomato_Function.png"));
		leg1 = new Leg (1);
		leg2 = new Leg (2);
		leg3 = new Leg (3);
		player = new SoundPlayer ();
		movementTimer = 0;
		leg4 = new Leg (4);
		RNG = new Random ();
		timer = 0;
		patternNumber = 1;
		jeffreyOnYourRight = false;
		leg5 = new Leg (5);
		leg6 = new Leg (6);
		shouldFlip = 0;
		leg7 = new Leg (7);
		leg8 = new Leg (8);
	
		this.setHitboxAttributes(0, 0, 65,  51 );
	}
	@Override 
	public String checkName () {
		return "TOMATO FUNCTION";
	}
	@Override
	public String checkEntry () {
		return "THE ULTIMATE FORM OF CONTENT CONTROL CLAIMING OWNERSHIP OF ALL CONCEPTS IN EXISTANCE COULD OF BEEN PEFECT IF FLOATS DIDEN'T CAP SO QUICKLY BUT ALL MASTERPEICES MUST BE DESTROIED AT SOME POINT A MOMENT OF SILENCE FOR WHAT COULD OF BEEN";
	}
	@Override
	public void enemyFrame () {
		if (decalre) {
		leg1.declare(this.getX()-30,this.getY() -70);
		leg2.declare(this.getX() -12,this.getY() - 77);
		leg3.declare(this.getX() +30,this.getY()- 77);
		leg4.declare(this.getX() + 45,this.getY() -70);
		leg5.declare(this.getX()-30, this.getY() + 30);
		leg6.declare(this.getX() -12,this.getY() + 38);
		leg7.declare(this.getX() + 30,this.getY() + 38);
		leg8.declare(this.getX() + 45, this.getY() + 30);
		decalre = false;
		}
		//make legs de-attach as more damage is received
		if (this.health < 2200 && leg1.isAttached()) {
			leg1.deattach();
		}
		if (this.health < 1900 && leg8.isAttached()) {
			leg8.deattach();
		}
		if (this.health < 1600 && leg2.isAttached()) {
			leg2.deattach();
		}
		if (this.health < 1300 && leg7.isAttached()) {
			leg7.deattach();
		}
		if (this.health < 1000 && leg3.isAttached()) {
			leg3.deattach();
		}if (this.health < 700&& leg6.isAttached()) {
			leg6.deattach();
		}
		if (this.health < 400 && leg4.isAttached()) {
			leg4.deattach();
		}
		if (this.health <= 0 && leg5.isAttached()) {
			leg5.deattach();
			
		}
		//deals with making legs occasionally flip
		shouldFlip = RNG.nextInt(149) +1;
		if (shouldFlip == 69 && !leg1.isAttached()) {
			leg5.flip();
		}
		if (shouldFlip == 111 && !leg2.isAttached()) {
			leg6.flip();
		}
		if (shouldFlip == 119 && !leg7.isAttached()) {
			leg3.flip();
		}
		if (shouldFlip == 88 && !leg8.isAttached()) {
			leg4.flip();
		}
		// deals with movement 
		if (this.getX() > GameCode.testJeffrey.getX()) {
			if (jeffreyOnYourRight) {
				jeffreyOnYourRight = false;
				movementTimer = 0;
			}
			movementTimer = movementTimer + 1;
			this.setX(leg5.getX() + 30);
			this.setY(leg5.getY()- 30);
			if (movementTimer == 30) {
				leg1.keepMovingLeft();
				leg2.keepMovingLeft();
				leg3.keepMovingLeft();
				leg4.keepMovingLeft();
				leg5.keepMovingLeft();
				leg6.keepMovingLeft();
				leg7.keepMovingLeft();
				leg8.keepMovingLeft();
			}
			if (movementTimer == 45) {
				leg1.setSpeed(6);
				leg2.setSpeed(6);
				leg3.setSpeed(6);
				leg4.setSpeed(6);
				leg5.setSpeed(6);
				leg6.setSpeed(6);
				leg7.setSpeed(6);
				leg8.setSpeed(6);
			}
			if (movementTimer == 60) {
				leg1.setSpeed(9);
				leg2.setSpeed(9);
				leg3.setSpeed(9);
				leg4.setSpeed(9);
				leg5.setSpeed(9);
				leg6.setSpeed(9);
				leg7.setSpeed(9);
				leg8.setSpeed(9);
			}
			if (movementTimer == 75) {
				leg1.setSpeed(12);
				leg2.setSpeed(12);
				leg3.setSpeed(12);
				leg4.setSpeed(12);
				leg5.setSpeed(12);
				leg6.setSpeed(12);
				leg7.setSpeed(12);
				leg8.setSpeed(12);
			}
		}
			if (this.getX() < GameCode.testJeffrey.getX()) {
				if (!jeffreyOnYourRight) {
					jeffreyOnYourRight = true;
					movementTimer = 0;
				}
				this.setX(leg5.getX() + 30);
				this.setY(leg5.getY()- 30);
				movementTimer = movementTimer + 1;
				if (movementTimer == 30) {
					leg1.keepMovingRight();
					leg2.keepMovingRight();
					leg3.keepMovingRight();
					leg4.keepMovingRight();
					leg5.keepMovingRight();
					leg6.keepMovingRight();
					leg7.keepMovingRight();
					leg8.keepMovingRight();
				}
				if (movementTimer == 45) {
					leg1.setSpeed(6);
					leg2.setSpeed(6);
					leg3.setSpeed(6);
					leg4.setSpeed(6);
					leg5.setSpeed(6);
					leg6.setSpeed(6);
					leg7.setSpeed(6);
					leg8.setSpeed(6);
				}
				if (movementTimer == 60) {
					leg1.setSpeed(9);
					leg2.setSpeed(9);
					leg3.setSpeed(9);
					leg4.setSpeed(9);
					leg5.setSpeed(9);
					leg6.setSpeed(9);
					leg7.setSpeed(9);
					leg8.setSpeed(9);
				}
				if (movementTimer == 75) {
					leg1.setSpeed(12);
					leg2.setSpeed(12);
					leg3.setSpeed(12);
					leg4.setSpeed(12);
					leg5.setSpeed(12);
					leg6.setSpeed(12);
					leg7.setSpeed(12);
					leg8.setSpeed(12);
				}
		}
		// deals with red patterns
		if (timer == 160) {
			patternNumber = RNG.nextInt(3) + 1;
			timer = 0;
		}
		if (patternNumber == 2) {
			if (timer == 0) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg2.turnRed();
				leg3.turnRed();
				leg4.turnRed();
				leg5.turnRed();
				leg6.turnRed();
				leg7.turnRed();
				leg8.turnRed();
			}
			if (timer == 40) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg2.turnGreen();
				leg3.turnGreen();
				leg4.turnGreen();
				leg5.turnGreen();
				leg6.turnGreen();
				leg7.turnGreen();
				leg8.turnGreen();
			}
			if (timer == 80) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg2.turnRed();
				leg3.turnRed();
				leg4.turnRed();
				leg5.turnRed();
				leg6.turnRed();
				leg7.turnRed();
				leg8.turnRed();
			}
			if (timer == 120) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg2.turnGreen();
				leg3.turnGreen();
				leg4.turnGreen();
				leg5.turnGreen();
				leg6.turnGreen();
				leg7.turnGreen();
				leg8.turnGreen();
			}
		}
		if (patternNumber == 3) {
			if (timer == 0) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
			}
			if (timer == 10) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg2.turnRed();
			}
			if (timer == 20) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg3.turnRed();
			}
			if (timer == 30) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg4.turnRed();
			}
			if (timer == 40) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg5.turnRed();
			}
			if (timer == 50) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg6.turnRed();
			}
			if (timer == 60) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg7.turnRed();
			}
			if (timer == 70) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg8.turnRed();
			}
			if (timer == 80) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
			}
			if (timer == 90) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg2.turnGreen();
			}
			if (timer == 100) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg3.turnGreen();
			}
			if (timer == 110) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg4.turnGreen();
			}
			if (timer == 120) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg5.turnGreen();
			}
			if (timer == 130) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg6.turnGreen();
			}
			if (timer == 140) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg7.turnGreen();
			}
			if (timer == 150) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg8.turnGreen();
			}
		}
		if (patternNumber == 1) {
			if (timer == 0) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg2.turnRed();
				leg7.turnRed();
				leg8.turnRed();
			}
			if (timer == 20) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg2.turnGreen();
				leg7.turnGreen();
				leg8.turnGreen();
				leg3.turnRed();
				leg4.turnRed();
				leg5.turnRed();
				leg6.turnRed();
			}
			if (timer == 40) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg8.turnRed();
				leg3.turnGreen();
				leg6.turnGreen();
			}
			if (timer == 60) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg8.turnGreen();
				leg5.turnGreen();
				leg4.turnGreen();
				leg2.turnRed();
				leg7.turnRed();
				leg3.turnRed();
				leg6.turnRed();
			}
			if (timer == 80) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg2.turnGreen();
				leg7.turnGreen();
				leg3.turnGreen();
				leg6.turnGreen();
			}
			if (timer == 100) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg8.turnRed();
				leg5.turnRed();
				leg4.turnRed();
				leg2.turnRed();
				leg7.turnRed();
				leg3.turnRed();
				leg6.turnRed();
			}
			if (timer == 120) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg2.turnGreen();
				leg7.turnGreen();
				leg8.turnGreen();
			}
			if (timer == 140) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnRed();
				leg2.turnRed();
				leg7.turnRed();
				leg8.turnRed();
				leg3.turnGreen();
				leg4.turnGreen();
				leg5.turnGreen();
				leg6.turnGreen();
			}
			if (timer == 159) {
				player.playSoundEffect(6F, "resources/audio/soundEffects/beep.wav", clip );
				leg1.turnGreen();
				leg2.turnGreen();
				leg7.turnGreen();
				leg8.turnGreen();
			}
		}
		timer = timer + 1;
	}
}