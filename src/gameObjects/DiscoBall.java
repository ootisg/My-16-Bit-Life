package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.RenderLoop;
import map.Room;
import resources.Sprite;

public class DiscoBall extends Enemy {
	int timer;
	boolean direction;
	double rotation1;
	double rotation2;
	double rotation3;
	double x1;
	double x2;
	double x3;
	double y1;
	double y2;
	double y3;
	double newX1;
	double newX2;
	double newX3;
	double newY1;
	double newY2;
	double newY3;
	Graphics lines;
	Sprite ballSprite = new Sprite ("resources/sprites/disco ball.png");
	Random RNG = new Random ();
	public DiscoBall () {
		this.setSprite(ballSprite);
		rotation1 = 1.04;
		timer = 0;
		direction = false;
		lines = RenderLoop.window.getBufferGraphics();
		lines.setColor(new Color (0x0324fc));
		rotation2 = 1.57;
		rotation3 = 2.03;
		this.setHealth(80);
		this.defence = 30;
		this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override 
	public String checkName () {
		return "DISCO BALL";
	}
	@Override
	public String checkEntry () {
		return "THIS IS PRETTY HASARDAS TO ALL UNSUSPECTING DANCERS I AM NOT SURE WHY ANYONE THOUGHT THIS WOULD BE A GREAT IDEA";
	}
	@Override
	public void enemyFrame () {
		if (timer < 30) {
		if (RNG.nextInt(80) == 69) {
			direction = !direction;
		}
		if (direction) {
			rotation1 = rotation1 + 0.01;
			rotation2 = rotation2 + 0.01;
			rotation3 = rotation3 + 0.01;
		} else {
			rotation1 = rotation1 - 0.01;
			rotation2 = rotation2 - 0.01;
			rotation3 = rotation3 - 0.01;
		}
			if (rotation1 > 2.62) {
				rotation1 = 0.52;
			}
			if (rotation1 < 0.52) {
				rotation1 = 2.62;
			}
			if (rotation2 > 2.62) {
				rotation2 = 0.52;
			}
			if (rotation2 < 0.52) {
				rotation2 = 2.62;
			}
			if (rotation3 > 2.62) {
				rotation3 = 0.52;
			}
			if (rotation3 < 0.52) {
				rotation3 = 2.62;
			}
		
			//deals with the first laser
			x1 = this.getX();
			y1 = this.getY();
			int count = 0;
			while (true) {
				try {
					count = count + 1;
				if (!this.goX (this.getX() + Math.cos (rotation1)) || !this.goY (this.getY () + Math.sin (rotation1))) {
					break;
				}
				
				if (this.isColliding(player)) {
				player.damage(3);
				break;
				}
				if (count > 900) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			newX1 = this.getX();
			newY1 = this.getY();
			this.setX(x1);
			this.setY(y1);
			y1 = y1 - Room.getViewY();
			newY1 = newY1 - Room.getViewY();
			x1 = x1 - Room.getViewX();
			newX1 = newX1 - Room.getViewX();
			//deals with the second laser
			x2 = this.getX();
			y2 = this.getY();
			count = 0;
			while (true) {
				try {
					count = count + 1;
				if (!this.goX (this.getX() + Math.cos (rotation2)) || !this.goY (this.getY () + Math.sin (rotation2))) {
					break;
				}
				if (this.isColliding(player)) {
				player.damage(3);
				break;
				}
				if (count > 900) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			newX2 = this.getX();
			newY2 = this.getY();
			this.setX(x2);
			this.setY(y2);
			y2 = y2 - Room.getViewY();
			newY2 = newY2 - Room.getViewY();
			x2 = x2 - Room.getViewX();
			newX2 = newX2 - Room.getViewX();
			//deals with the third laser
			x3 = this.getX();
			y3 = this.getY();
			count = 0;
			while (true) {
				try {
					count = count + 1;
				if (!this.goX (this.getX() + Math.cos (rotation3)) || !this.goY (this.getY () + Math.sin (rotation3))) {
					break;
				}
				if (this.isColliding(player)) {
				player.damage(3);
				break;
				}
				if (count > 900) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			newX3 = this.getX();
			newY3 = this.getY();
			this.setX(x3);
			this.setY(y3);
			y3 = y3 - Room.getViewY();
			newY3 = newY3 - Room.getViewY();
			x3 = x3 - Room.getViewX();
			newX3 = newX3 - Room.getViewX();
			} 
			timer = timer + 1;
			if (timer == 50) {
				timer = 0;
			}
	}
	@Override
	public void draw () {
		ballSprite.draw((int)this.getX() - Room.getViewX(), (int)this.getY() - Room.getViewY());
		if (timer < 30) {
		lines.drawLine((int)x1 + 8, (int)y1 + 16, (int)newX1, (int)newY1);
		lines.drawLine((int)x2 + 8, (int)y2 + 16, (int)newX2, (int)newY2);
		lines.drawLine((int)x3 + 8, (int)y3 + 16, (int)newX3, (int)newY3);
		}
	}
}
