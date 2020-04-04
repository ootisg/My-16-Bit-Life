package gameObjects;

import java.util.Random;

import map.Room;
import projectiles.PokaDot;
import resources.Sprite;

public class MafiaShooter extends Enemy {
	Sprite shooter = new Sprite ("resources/sprites/mafiaShooter.png");
	Sprite mafiaShooterAttack = new Sprite ("resources/sprites/mafiaShooterAttack.png");
	int timer;
	PokaDot bullet;
	Random RNG = new Random ();
	int waitUntil = RNG.nextInt(60) + 1;
	public MafiaShooter () {
		this.setFalls(true);
		timer = 0;
		this.setSprite(shooter);
		this.setHealth(150);
		this.setHitboxAttributes(0, 0, 16, 32);
	}
	@Override 
	public String checkName () {
		return "MAFIA SHOOTER";
	}
	@Override
	public String checkEntry () {
		return " I WONDER AT WHAT POINT IN YOUR LIFE YOU HAVE TO BE AT TO CONSIDER WORKING FOR THE MAFIA A VIABLE CAREER PATH.  WHATEVER I GUESS THAT IS JUST HOW MAFIA WORK";
	}
	@Override 
	public void enemyFrame () {
		if (!this.getSprite().equals(mafiaShooterAttack)) {
		this.patrol(0, 0, 75, 0, 75, mafiaShooterAttack, shooter, 2, 0,0, 0, 16, 32);
		} else {
			timer = timer + 1;
			
			if (timer == waitUntil) {
				timer = 0;
				waitUntil = RNG.nextInt(60) + 1;
				if (this.getAnimationHandler().flipHorizontal()) {
				bullet = new PokaDot (0);
				bullet.declare(this.getX(),this.getY() + 10);
				} else {
					bullet = new PokaDot (3.14);
					bullet.declare(this.getX(),this.getY() + 10);	
				}
			}
			if (!this.jumpDone) {
			this.jump(0, 7);
			} else {
			this.setY(this.getY()  + 1);
			if (Room.isColliding(this.hitbox())) {
				this.jumpDone = false;
			}
			this.setY(this.getY() - 1);
			}
			
			if (!this.isNearPlayerX(0, 75, 0, 75)) {
				this.setSprite(shooter);
			}
		}
	}
}
