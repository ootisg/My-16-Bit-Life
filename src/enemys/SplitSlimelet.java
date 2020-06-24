package enemys;

import resources.Sprite;

public class SplitSlimelet extends PresetEnemy {
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
		this.adjustHitboxBorders();
		this.getAnimationHandler().setFrameTime(100);
	}
	@Override 
	public String checkName () {
		return "SPLIT SLIMELET";
	}
	@Override
	public String checkEntry () {
		return "THIS CREATURES EXISTINCE MUST BE AN ABSOLUT NIGHTMARE ONLY THROUGH ITS PARENTS DEATH CAN IT BE BROUGHT TO LIFE AND SAID LIFE IS EXTINGUSED WITH A PAINTBALL GUN OR VACCUM SHORTLY AFTER";
	}
	@Override
	public void enemyFrame () {
		this.jumpyBoi(idleSprite, attackingSprite, 8, 6, 30);
	}
}
