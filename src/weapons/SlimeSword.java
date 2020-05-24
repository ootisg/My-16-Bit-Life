package weapons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import enemys.Enemy;
import gameObjects.Point;
import items.Item;
import main.GameCode;
import main.ObjectHandler;
import main.RenderLoop;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class SlimeSword extends Item {
	Sprite samSwingSprite;
	Random RNG;
	public Sprite samWalkingSword;
	boolean coolDown;
	ArrayList <Enemy> hitEnemys = new ArrayList ();
	int damageCoolDown;
	boolean faceingLeft;
	double x;
	double y;
	double currX;
	boolean broke = false;
	double currY;
	double desX;
	double desY;
	double slope;
	boolean extended;
	public static Jeffrey player = (Jeffrey) ObjectHandler.getObjectsByName ("Jeffrey").getFirst ();
	int [] upgradeInfo;
	Graphics2D graphics =(Graphics2D) RenderLoop.window.getBufferGraphics();
	public SlimeSword () {
		extended = false;
		this.setHitboxAttributes(11, 0, 3, 3);
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
		RNG = new Random ();
		upgradeInfo = new int [] {0,0,0,0};
		samWalkingSword = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
		damageCoolDown = 20;
		coolDown = false;
		graphics.setColor(new Color (0x19ED45));
		graphics.setStroke(new BasicStroke (2));
	samSwingSprite = new Sprite ("resources/sprites/config/slime_swing.txt");
	}
	@Override
	public String checkName () {
		return "SLIME SWORD";
	}
	@Override
	public String checkEnetry() {
		return "ALITERATION IS FUN";
	}
	@Override
	public String [] getUpgrades () {
		String [] returnArray;
		returnArray = new String [] {"ARIAL ATTACKS", "SWORD THROW", "SLOWNESS OR SOMETHING", "HOOKSHOT"};
		return returnArray;
	}
	@Override 
	public String getItemType() {
		return "WeaponSam";
	}
@Override
	public int [] getTierInfo () {
		return upgradeInfo;
	}
	@Override
	public void frameEvent() {
		if (extended && (currX != desX || currY != desY)) {
			if (desX > currX) {
				currX = currX + 5;
				if (currX > desX) {
					currX = desX;
				}
			} else {
				currX = currX - 5;
				if (currX < desX) {
					currX = desX;
				}
			}
			if (desY > currY) {
				currY = currY + (5*slope);
				if (currY > desY) {
					currY = desY;
				}
			} else {
				currY = currY + (5*slope);
				if (currY < desY) {
					currY = desY;
				}
			}
		} else {
			if (extended) {
				double slack =  player.getY() - desY;
				if (slack < 0) {
					slack = slack * -1;
				}
				if (slack > 1) {
					double toUse = slack/15;
					if (toUse > 8) {
						toUse = 8;
					}
				if ((desX - player.getX() > slack && desX - player.getX() > 0) ||(player.getX() - desX > slack && player.getX() - desX > 0)) {
					if (player.getX() > desX) {
						player.vx = -toUse;
					} else {
						player.vx = toUse;
						
					}
					
				}
				//System.out.println(player.getX() - desX);
				if (!(player.getX() < desX + 10 && player.getX() > desX -10)) {
				if (player.getX() > desX) {
					player.vx = player.vx  -(toUse/10);
				} else {
					player.vx = player.vx + (toUse/10);
					
				}
				}
				} else {
					player.vx = 0;
					player.setX(desX);
				}
				if (!mouseButtonDown(2) && !player.isCrouched()) {
					Point currentPoint = new Point (this.getX(),this.getY());
					Point mousePoint = new Point (desX,desY);
					slope =currentPoint.getSlope(mousePoint);
					player.stopFall(true);
					int slopeDeturent;
					if (slope > 0) {
						slopeDeturent = 1;
					} else {
						slopeDeturent = -1;
					}
					if (!Double.isNaN(slope)) {
					player.goY(player.getY() + (slopeDeturent*1));
					}
					if ( !Double.isNaN(slope)&& (slope > 0 && player.getY() > desY) || (slope < 0 && player.getY() < desY)) {
						player.goY(player.getY() - (slopeDeturent*1));
					}
				} 
			}
		}
		this.setX(player.getX());
		this.setY(player.getY());
		if (this.mouseButtonPressed(2)&& !extended && !broke) {
			extended = true;
			this.setHitboxAttributes(11, 0, 3, 3);
			x = this.getX();
			y = this.getY();
			currX = x;
			currY = y;
			int count = 0;
			Point currentPoint = new Point (x,y);
			Point mousePoint = new Point (getCursorX(),getCursorY());
			slope =currentPoint.getSlope(mousePoint);
			int toUse = 1;
			if (slope > 5 || slope < -5) {
				slope = slope/4;
				toUse = 1/4;
			}
			boolean change = false;
			if ((mousePoint.getX()> currentPoint.getX() && mousePoint.getY() < currentPoint.getY()) || (mousePoint.getX()> currentPoint.getX() && mousePoint.getY() > currentPoint.getY() )) {
				change = true;
			}
			
			while (true) {
				try {
					count = count + 1;
				if (!change) {
				if (!this.goXandY(this.getX() - toUse, this.getY() + slope)) {
					break;
				}
				} else {
					if (!this.goXandY(this.getX() + toUse, this.getY() + slope)) {
						break;
					}
				}
				if (count > 400 ) {
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			desX = this.getX();
			desY = this.getY();
			this.setX(x - Room.getViewX());
			this.setY(y);
			
			//potentially add y stuff if it becomes a problem
			x = x - Room.getViewX();
			desX = desX - Room.getViewX();
		} 
		if (((this.keyPressed(32)|| this.mouseButtonPressed(0)) && extended) && !player.isCrouched()) {
			extended = false;
			player.stopFall(false);
			player.vy = 0;
			broke = true;
		}
		if (broke && !mouseButtonDown (2)) {
			broke = false;
		}
		if (this.mouseButtonDown(0) && !GameCode.testJeffrey.getSprite().equals(samSwingSprite) && !extended && !player.isCrouched() ) {
			GameCode.testJeffrey.setSprite(samSwingSprite);
			GameCode.testJeffrey.getAnimationHandler().setFrameTime(50);
			GameCode.testJeffrey.getAnimationHandler().setRepeat(false);
			GameCode.testJeffrey.changeSprite(false);
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				GameCode.testJeffrey.desyncSpriteX(-34);
				faceingLeft = true;
			}
		}
		if (GameCode.testJeffrey.getSprite().equals(samSwingSprite)) {
		if (faceingLeft && !GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
			GameCode.testJeffrey.desyncSpriteX(0);
			faceingLeft = false;
		}
		if (!faceingLeft && GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
			GameCode.testJeffrey.desyncSpriteX(-34);
			faceingLeft = true;
		}
		}
		if (GameCode.testJeffrey.getSprite().equals(samSwingSprite) && GameCode.testJeffrey.getAnimationHandler().getFrame() ==  11) {
			GameCode.testJeffrey.changeSprite(true);
			GameCode.testJeffrey.setSprite(samWalkingSword);
			GameCode.testJeffrey.getAnimationHandler().setRepeat(true);
			hitEnemys.removeAll(hitEnemys);
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
				GameCode.testJeffrey.desyncSpriteX(0);
				faceingLeft = false;
			}
		}
		
		if (GameCode.testJeffrey.getSprite().equals(samSwingSprite)) {
			if (GameCode.testJeffrey.getAnimationHandler().flipHorizontal()) {
			this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { -34, -34, -34, -34,-22,-19,-24,-22,-24, -24,-34,-34}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , GameCode.testJeffrey);
			} else {
				this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { 0, 0, 0, 45,0,0,0,0,0, 36,0,0}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , GameCode.testJeffrey);
			}
		}
		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
			if (this.isColliding(Enemy.enemyList.get(i)) && !hitEnemys.contains(Enemy.enemyList.get(i)) ){
				hitEnemys.add(Enemy.enemyList.get(i));
				if (this.getTierInfo()[0] >= 1) {
				Enemy.enemyList.get(i).knockback(10, this.getAnimationHandler().flipHorizontal());
				}
				Enemy.enemyList.get(i).appliedStatuses[3] = true;
				Enemy.enemyList.get(i).damage (RNG.nextInt(50) + 20);
			}
		}
	}
	@Override 
	public void draw () {
		super.draw();
		if (extended) {
		graphics.drawLine((int)this.getX(), (int)this.getY(), (int)currX, (int)currY);
		}
	}
}
