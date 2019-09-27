package gameObjects;

import resources.Sprite;

public class SplitSlimelet extends Enemy {
	Sprite idleSprite;
	Sprite attackingSprite;
	public SplitSlimelet () {
		this.setFalls(true);
		this.health = 60;
		this.defence = 0;
		idleSprite = new Sprite ("resources/sprites/config/Split_Slimelet_Idle.txt");
		attackingSprite = new Sprite ("resources/sprites/config/Split_Slimelet_Jumping.txt");
		this.setSprite(idleSprite);
		this.setHitboxAttributes(0, 0, 44, 48);
		this.getAnimationHandler().setFrameTime(100);
	}
	@Override
	public void enemyFrame () {
		this.jumpyBoi(idleSprite, attackingSprite);
	}
}
