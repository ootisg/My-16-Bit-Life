package weapons;


import java.awt.Color;
import java.awt.Graphics;

import enemys.Enemy;
import main.GameObject;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;
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
	int fireTimer = 0;
	int power = 0;
	
	public LaserPointer () {
		super(new Sprite ("resources/sprites/laserPointer.png"));
		lol = RenderLoop.window.getBufferGraphics();
		tierInfo = new int [] {0,0,0,1};
		this.setHitboxAttributes(0, 0, 4, 4);
		//Checks for "Green Light" upgrade
		if (tierInfo [3] >= 1) {
			lol.setColor(new Color (0x13F013));
			power = 1;
		} else {
			lol.setColor(new Color (0xF01313));
			power = 0;
		}
	}
	@Override
	public String checkName () {
		return "LASER POINTER";
	}
	@Override
	public String checkEnetry() {
		return "ITS A LASER POINTER POPULAR AMOUNG COLLEGE PROFFESSORS AND BORED KIDS";
	}
	@Override
		public String [] getUpgrades () {
			String [] returnArray;
			returnArray = new String [] {"CHARGE FIRE???", "ELECTRIC BOOGLO", "AND KNUCKLES", "GREEN LIGHT"};
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
	public boolean isCollidingLaser (GameObject obectToCheck) {
		int count =0;
		x = this.getX();
		y = this.getY();
		if (mouseButtonDown(0) && !Jeffrey.getActiveJeffrey().isCrouched()) {
			while (true) {
				count = count + 1;
				try {
				if (!this.getAnimationHandler().flipHorizontal()) {
				if (!this.goX (this.getX() + Math.cos (rotation)) || !this.goY (this.getY () + Math.sin (rotation))) {
					break;
				}
				} else {
					if (!this.goX (this.getX() - Math.cos (rotation)) || !this.goY (this.getY () + Math.sin (rotation))) {
						break;
					}	
				}
				if (this.isColliding(obectToCheck)){
					this.setX(x - Room.getViewX());
					this.setY(y - Room.getViewY());
					return true;
				}
				if (count > 900) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			this.setX(x- Room.getViewX());
			this.setY(y - Room.getViewY());
			return false;
		} else {
			return false;
		}
	}
	@Override 
	public void frameEvent () {
		if (mouseButtonDown(0) && !Jeffrey.getActiveJeffrey().isCrouched()) {
			fireTimer++;
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
						end = true;
						//Determines charge fire power.
						switch (tierInfo[0]) {
							case 0:
								if (fireTimer % 3 == 1) {
									Enemy.enemyList.get(i).damage(1 + power);
									break;
								}
							case 1:
								if (fireTimer < 60 && fireTimer % 3 == 1) {
									Enemy.enemyList.get(i).damage(1 + power);
									break;
								}
								if (fireTimer >= 60 && fireTimer % 2 == 1) {
									Enemy.enemyList.get(i).damage(2 + power);
									break;							
								}
							case 2:
								if (fireTimer < 60 && fireTimer % 3 == 1) {
									Enemy.enemyList.get(i).damage(1 + power);
									break;
								}
								if (fireTimer >= 60 && fireTimer <= 150 && fireTimer % 2 == 1) {
									Enemy.enemyList.get(i).damage(1 + power);
									break;							
								}
								if (fireTimer > 150) {
									Enemy.enemyList.get(i).damage(2 + power);
									break;
								}
						}
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
			this.setY(y - Room.getViewY());
			x = x - Room.getViewX();
			y = y - Room.getViewY();
			newX = newX - Room.getViewX();
			newY = newY - Room.getViewY();
		} else {
			x = this.getX();
			y = this.getY();
			newX = this.getX();
			newY = this.getY();
			fireTimer = 0;
		}
	}
	public void draw () {
		if (mouseButtonDown(0) && !Jeffrey.getActiveJeffrey().isCrouched()) {
		lol.drawLine((int)x,(int) y, (int)newX, (int)newY);
		}
	}
}
