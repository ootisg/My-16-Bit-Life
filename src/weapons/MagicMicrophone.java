package weapons;

import java.util.Random;

import gameObjects.Enemy;
import items.Item;
import main.GameCode;
import resources.Sprite;

public class MagicMicrophone extends Item {
	private Sprite ryanWhipping;
	private Sprite whipLength;
	public Sprite ryanMicrophoneWalking;
	private Random RNG;
	int timer;
	boolean addTime;
	public  MagicMicrophone () {
		ryanWhipping = new Sprite ("resources/sprites/config/microphoneWhip.txt");
		ryanMicrophoneWalking = new Sprite("resources/sprites/config/ryan_walking_microphone.txt");
		RNG = new Random ();
		timer = 0;
		addTime = false;
		whipLength = new Sprite ("resources/sprites/config/microphoneWhipVariableFrame.txt");
	}
	@Override
	public void frameEvent () {
		this.setX(GameCode.testJeffrey.getX());
		this.setY(GameCode.testJeffrey.getY());
		if (this.mouseButtonClicked(0) && !GameCode.testJeffrey.getSprite().equals(ryanWhipping)) {
			GameCode.testJeffrey.getAnimationHandler().setRepeat(false);
			GameCode.testJeffrey.setSprite(ryanWhipping);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(1);
			GameCode.testJeffrey.changeSprite(false);
			GameCode.testJeffrey.binded = true;
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() -34);
			}
		}
		if (GameCode.testJeffrey.getSprite().equals(ryanWhipping) && GameCode.testJeffrey.getAnimationHandler().getFrame() == 7 && !addTime) {
			GameCode.testJeffrey.setSprite(whipLength);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(0);
			GameCode.testJeffrey.changeFrameTime(false);
			GameCode.testJeffrey.getAnimationHandler().setAnimationFrame(this.dealWithWhipFrame());
		}
		if (addTime) {
			timer = timer + 1;
			if (timer == 5) {
				GameCode.testJeffrey.setSprite(ryanWhipping);
				GameCode.testJeffrey.getAnimationHandler().setAnimationFrame(7);
			}
			if (timer == 10) {
				timer = 0;
				addTime =false;
				GameCode.testJeffrey.getAnimationHandler().setRepeat(true);
				GameCode.testJeffrey.changeFrameTime(true);
				GameCode.testJeffrey.changeSprite(true);
				GameCode.testJeffrey.binded = false;
				GameCode.testJeffrey.setSprite(ryanMicrophoneWalking);
				if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
					GameCode.testJeffrey.setX(GameCode.testJeffrey.getX() +34);
				}
			}
		}
		
	}
	int dealWithWhipFrame () {
		int length = 0;
		for (int v = 0; v <= 7; v = v + 1 ) {
			length = length + 4;
			if (!GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				this.setHitboxAttributes(13, 17, length, 4);	
			} else {
				this.setHitboxAttributes(0, 17, length, 4);
			}
			for (int i = 0; i < Enemy.enemyList.size(); i ++) {
				if (this.isColliding(Enemy.enemyList.get(i))){
					Enemy.enemyList.get(i).damage (RNG.nextInt(20) + 20);
					addTime =true;
					return(v);
				}
			}
		}
		addTime =true;
		return(7);
	}
}
