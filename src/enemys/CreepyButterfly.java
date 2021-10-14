package enemys;

import java.util.Random;

import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class CreepyButterfly extends Enemy {
	
	public static final Sprite butterflySprite = new Sprite ("resources/sprites/config/creepy_butterfly.txt");
	
	//This class is not yet commented 
	private Random RNG;
	public CreepyButterfly () {
		setSprite (butterflySprite);
		setHitboxAttributes (0, 0, 16, 16);
		getAnimationHandler ().setFrameTime (55.5);
		RNG = new Random ();
		this.health = 1;
		this.defence = 0;
	}
	@Override 
	public String checkName () {
		return "CREEPY BUTTERFLY";
	}
	@Override
	public String checkEntry () {
		return "CREEEEEEEEEEEEEEEEPY BUTTERFLYS?";
	}
	@Override
	public void enemyFrame () {
		double targetX = Player.getActivePlayer().getX ();
		double targetY = Player.getActivePlayer().getY ();
		if (((targetX > this.getX() && targetX < this.getX() + 22) || (targetX < this.getX() && targetX> this.getX() - 22) &&  ((targetY > this.getY() && targetY < this.getY() + 22) || (targetY < this.getY() && targetY> this.getY() - 22)))) {
				double gayBabyJail;
				gayBabyJail = RNG.nextInt (6) + (RNG.nextInt(27) + 1)/100.0;
				goX ((int)  this.getX() + Math.cos (gayBabyJail) * 6);
				goY ((int) this.getY() + Math.sin (gayBabyJail) * 6);
		} else {
		if (Math.sqrt ((this.getX() - targetX) * (this.getX() - targetX) + (this.getY() - targetY) * (this.getY() - targetY)) <= 128) {
			double slope = (this.getY() - targetY) / (this.getX() - targetX);
			double angle = Math.atan (slope);
			if (this.getX() > targetX) {
				angle -= Math.PI;
			}
			goX ((int) this.getX() + Math.cos (angle) * 6);
			goY ((int) this.getY() + Math.sin (angle) * 6);
		}
		}
	}
}