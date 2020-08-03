package gameObjects;

import enemys.BuggyBoi;
import main.GameObject;
import players.Jeffrey;
import projectiles.PokaDot;
import resources.Sprite;

public class BugAimer extends GameObject {
	BuggyBoi friend;
	int xDisplacement;
	int yDisplacement;
	PokaDot shot;
	int timer;
	public BugAimer (BuggyBoi squad) {
	friend = squad;
	this.setSprite(new Sprite ("resources/sprites/config/bug aimer/left.txt"));
	xDisplacement = -12;
	yDisplacement = 0;
	timer = 0;
	shot = new PokaDot (3.14);
	}
	public void frameEvent () {
		
		
		if (friend.isNearPlayerXWithoutCheckingWalls(0, 0, 20, 80) && friend.isNearPlayerYWithoutCheckingWalls(0, 40, 0, 40)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/left.txt"));
			xDisplacement = -12;
			yDisplacement = 0;
			if (timer % 20 == 0) {
				shot = new PokaDot (3.14);
				shot.declare(this.getX(),this.getY());
			}
		}
		if (friend.isNearPlayerXWithoutCheckingWalls(20, 80, 0, 0) && friend.isNearPlayerYWithoutCheckingWalls(0, 40, 0, 40)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/right.txt"));
			xDisplacement = 13;
			yDisplacement = 0;
			if (timer % 20 == 0) {
				shot = new PokaDot (0);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(20, 80, 0, 0) && friend.isNearPlayerYWithoutCheckingWalls(40, 120, 0, 0)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/upright.txt"));
			xDisplacement = 9;
			yDisplacement = -6;
			if (timer % 20 == 0) {
				shot = new PokaDot (5.495);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(0, 0, 20, 80) && friend.isNearPlayerYWithoutCheckingWalls(40, 120, 0, 0)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/upleft.txt"));
			xDisplacement = -8;
			yDisplacement = -8;
			if (timer % 20 == 0) {
				shot = new PokaDot (3.925);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(0, 0, 20, 80) && friend.isNearPlayerYWithoutCheckingWalls(0, 0, 40, 120)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/downleft.txt"));
			xDisplacement = -7;
			yDisplacement = 8;
			if (timer % 20 == 0) {
				shot = new PokaDot (2.355);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(20, 80, 0, 0) && friend.isNearPlayerYWithoutCheckingWalls(0, 0, 40, 120)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/downright.txt"));
			xDisplacement = 10;
			yDisplacement = 7;
			if (timer % 20 == 0) {
				shot = new PokaDot (0.785);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(0, 20, 0, 20) && friend.isNearPlayerYWithoutCheckingWalls(0, 0, 0, 120)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/down.txt"));
			xDisplacement = 2;
			yDisplacement = 12;
			if (timer % 20 == 0) {
				shot = new PokaDot (2.09333333);
				shot.declare(this.getX(),this.getY());
			}
			}
		if (friend.isNearPlayerXWithoutCheckingWalls(0, 20, 0, 20) && friend.isNearPlayerYWithoutCheckingWalls(0, 120, 0, 0)) {
			timer = timer + 1;
			this.setSprite(new Sprite ("resources/sprites/config/bug aimer/up.txt"));
			xDisplacement = 0;
			yDisplacement = -11;
			if (timer % 20 == 0) {
				shot = new PokaDot (1.57);
				shot.declare(this.getX(),this.getY());
			}
			}
	}
	public int getXDisplacement () {
		return xDisplacement;
	}
	public int getYDisplacement () {
		return yDisplacement;
	}
}
