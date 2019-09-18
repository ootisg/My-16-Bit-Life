package gameObjects;

import main.AnimationHandler;
import resources.Sprite;

public class FireRextinguser extends Enemy {
	public Sprite idleSprite;
	public Sprite attackingSprite;
	AnimationHandler handler;
	public  FireRextinguser() {
		idleSprite = new Sprite ("resources/sprites/config/Fire_Rextinguisher_Idle.txt");
		attackingSprite = new Sprite ("resources/sprites/config/Fire_Rextinguisher_Attacking.txt");
		this.setSprite (attackingSprite);
		
	}
}
