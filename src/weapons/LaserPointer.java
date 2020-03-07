package weapons;


import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Enemy;
import main.RenderLoop;
import map.Room;
import resources.Sprite;

public class LaserPointer extends AimableWeapon {
	Graphics lol;
	double x;
	double y;
	double realX;
	double realY;
	double newX;
	double newY;
	int [] tierInfo;
	Sprite stickSprite = new Sprite ("resources/sprites/laserPointer.png");
	public LaserPointer () {
		super(new Sprite ("resources/sprites/laserPointer.png"));
		lol = RenderLoop.window.getBufferGraphics();
		lol.setColor(new Color (0xF01313));
		tierInfo = new int [] {0,0,0,0};
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "LASER POINTER";
	}
	@Override
	public String checkEnetry() {
		return "ITS A LASER POINTER POPULAR AMOUNT COLLEGE PROFFESSORS AND BORED KIDS";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"LASER 2", "ELECTRIC BOOGLO", "AND KNUCKLES", "NEW FUNKEY MODE"};
			return returnArray;
		}
	@Override 
	public String getItemType() {
		return "WeaponJeffrey";
	}
	@Override
		public int [] getTierInfo () {
			return tierInfo;
		}
	@Override 
	public Sprite getUnrotatedSprite () {
		return stickSprite;
	}
	@Override 
	public void frameEvent () {
		if (mouseButtonDown(0)) {
			x = this.getX();
			y = this.getY();
			int count = 0;
			while (true) {
				try {
					count = count + 1;
				if (!this.getAnimationHandler().flipHorizontal()) {
				if (!this.goX (this.getX() + Math.cos (rotation)) || !this.goY (this.getY () + Math.sin (rotation))) {
					break;
				}
				} else {
					if (!this.goX (this.getX() - Math.cos (rotation)) || !this.goY (this.getY () + Math.sin (rotation))) {
						break;
					}	
				}
				Boolean end = false;
				for (int i = 0; i < Enemy.enemyList.size(); i++) {
					if (Enemy.enemyList.get(i).isColliding(this)) {
						Enemy.enemyList.get(i).damage(1);
						end = true;
						break;
					}
				}
				if (end) {
					break;
				}
				if (count > 900) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			newX = this.getX();
			newY = this.getY();
			this.setX(x - Room.getViewX());
			this.setY(y);
			//potentially add y stuff if it becomes a problem
			x = x - Room.getViewX();
			newX = newX - Room.getViewX();
		} else {
			x = this.getX();
			y = this.getY();
			newX = this.getX();
			newY = this.getY();
		}
	}
	public void draw () {
		lol.drawLine((int)x,(int) y, (int)newX, (int)newY);
	}
}
