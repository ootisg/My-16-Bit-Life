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
	double x;
	double y;
	double currX;
	boolean broke = false;
	double currY;
	double desX;
	double desY;
	double slope;
	boolean extended;
	double fifthteenthX;
	double fifthteenthY;
	int [] upgradeInfo;
	Graphics2D graphics =(Graphics2D) RenderLoop.window.getBufferGraphics();
	public SlimeSword () {
		extended = false;
		this.setRenderPriority(-1);
		this.setHitboxAttributes(11, 0, 3, 3);
		this.adjustHitboxBorders();
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
		RNG = new Random ();
		upgradeInfo = new int [] {0,0,0,0};
		samWalkingSword = new Sprite ("resources/sprites/config/sam_walking_with_sword.txt");
		damageCoolDown = 20;
		coolDown = false;
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
				currX = currX + 30;
				if (currX > desX) {
					currX = desX;
					fifthteenthX = (desX - Jeffrey.getActiveJeffrey().getX())/15;
				}
			} else {
				currX = currX - 30;
				if (currX < desX) {
					currX = desX;
					fifthteenthX = ( Jeffrey.getActiveJeffrey().getX() - desX)/15;
				}
			}
			if (desY > currY) {
				currY = currY + (30*slope);
				if (currY > desY) {
					currY = desY;
					fifthteenthY = (desY - Jeffrey.getActiveJeffrey().getY())/15;
				}
			} else {
				currY = currY + (30*slope);
				if (currY < desY) {
					currY = desY;
					fifthteenthY = (Jeffrey.getActiveJeffrey().getY() - desY)/15;
				}
			}
		} else {
			if (extended) {
				if (mouseButtonDown(2)) {
					Point currentPoint = new Point (this.getX(),this.getY());
					Point mousePoint = new Point (desX,desY);
					slope =currentPoint.getSlope(mousePoint);
					Jeffrey.getActiveJeffrey().stopFall(true); 
					if (fifthteenthX > Room.TILE_WIDTH) {
						fifthteenthX = Room.TILE_WIDTH;
						}
					if (fifthteenthY> Room.TILE_HEIGHT) {
						fifthteenthY = Room.TILE_HEIGHT;
						}
						if (Jeffrey.getActiveJeffrey().getY ()  > desY) {
							Jeffrey.getActiveJeffrey().goY(Jeffrey.getActiveJeffrey().getY() - fifthteenthY);
						} else {
							Jeffrey.getActiveJeffrey().goY(Jeffrey.getActiveJeffrey().getY() + fifthteenthY);
						}
						Jeffrey.getActiveJeffrey().binded = true;
						if (Jeffrey.getActiveJeffrey().getX() > desX) {
						
							Jeffrey.getActiveJeffrey().goX(Jeffrey.getActiveJeffrey().getX() - fifthteenthX);
						} else {
							Jeffrey.getActiveJeffrey().goX(Jeffrey.getActiveJeffrey().getX() + fifthteenthX);
						}
				} else {
					Jeffrey.getActiveJeffrey().binded = false;
					Jeffrey.getActiveJeffrey().stopFall(true); 
					double slack =  Jeffrey.getActiveJeffrey().getY() - desY;
					if (slack < 0) {
						slack = slack * -1;
					}
					if (slack > 1) {
						double toUse = slack/15;
						if (toUse > 8) {
							toUse = 8;
						}
					if (!(Jeffrey.getActiveJeffrey().getX() < desX + 10 && Jeffrey.getActiveJeffrey().getX() > desX -10)) {
					if (Jeffrey.getActiveJeffrey().getX() > desX) {
						Jeffrey.getActiveJeffrey().vx = Jeffrey.getActiveJeffrey().vx  -(toUse/10);
					} else {
						Jeffrey.getActiveJeffrey().vx = Jeffrey.getActiveJeffrey().vx + (toUse/10);
					}
					}
					if (Jeffrey.getActiveJeffrey().vx > 15.9 && Jeffrey.getActiveJeffrey().vx > 0) {
						Jeffrey.getActiveJeffrey().vx = 15.9;
					}
					if (Jeffrey.getActiveJeffrey().vx < -15.9 && Jeffrey.getActiveJeffrey().vx  < 0) {
						Jeffrey.getActiveJeffrey().vx = -15.9;
					}
					} else {
						Jeffrey.getActiveJeffrey().vx = 0;
						Jeffrey.getActiveJeffrey().goX(desX);
					}
				}
			}
		}
		if (this.mouseButtonPressed(2)&& !extended && !broke) {
			extended = true;
			this.setHitboxAttributes(11, 0, 3, 3);
			x = this.getX();
			y = this.getY();
			currX = x;
			currY = y;
			Point currentPoint = new Point (x,y);
			Point mousePoint = new Point (getCursorX() + Room.getViewX(),getCursorY() + Room.getViewY());
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
				if (!change) {
				if (!this.goXandY(this.getX() - toUse, this.getY() + slope)) {
					break;
				}
				} else {
					if (!this.goXandY(this.getX() + toUse, this.getY() + slope)) {
						break;
					}
				}
				if (this.getY() < 0) {
					this.setY(0);
					break;
				}
				if (this.getX() < 0) {
					this.setX(0);
					break;
				}
				if (this.getX() > Room.getWidth() * 16) {
					this.setX(Room.getWidth() * 16);
					break;
				}
				if (this.getY() > Room.getHeight() * 16) {
					this.setY(Room.getHeight() * 16);
					break;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			desX = this.getX();
			desY = this.getY();
			
			
		} 
		if ((this.keyPressed(32)&& extended) && !Jeffrey.getActiveJeffrey().isCrouched()) {
			extended = false;
			Jeffrey.getActiveJeffrey().stopFall(false);
			Jeffrey.getActiveJeffrey().vy = 0;
			broke = true;
			Jeffrey.getActiveJeffrey().binded = false;
		}
		if (broke && !mouseButtonDown (2)) {
			broke = false;
		}
		
		
		if (Jeffrey.getActiveJeffrey().getSprite().equals(samSwingSprite)) {
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
			this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { -34, -34, -34, -34,-22,-19,-24,-22,-24, -24,-34,-34}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , Jeffrey.getActiveJeffrey());
			} else {
				this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { 0, 0, 0, 45,0,0,0,0,0, 36,0,0}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , Jeffrey.getActiveJeffrey());
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
		if (this.mouseButtonDown(0) && !Jeffrey.getActiveJeffrey().getSprite().equals(samSwingSprite)  && !Jeffrey.getActiveJeffrey().isCrouched()) {
			Jeffrey.getActiveJeffrey().setSprite(samSwingSprite);
			Jeffrey.getActiveJeffrey().getAnimationHandler().setFrameTime(50);
			Jeffrey.getActiveJeffrey().changeSprite(false);
			Jeffrey.getActiveJeffrey().crouchElegable(false);
			if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
				Jeffrey.getActiveJeffrey().desyncSpriteX(-34);
			} 
		}
		
	}
	@Override 
	public void onFlip() {
		if (Jeffrey.getActiveJeffrey().getSprite().equals(samSwingSprite)) {
			if  (!Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
				Jeffrey.getActiveJeffrey().desyncSpriteX(-34);
			} else {
				Jeffrey.getActiveJeffrey().desyncSpriteX(0);
			}
		}
	}
	@Override 
	public void draw () {
			if (Jeffrey.getActiveJeffrey().getSprite().equals(samSwingSprite) && Jeffrey.getActiveJeffrey().getAnimationHandler().getFrame() ==  9) {
				if ( !mouseButtonDown(0)) {
					Jeffrey.getActiveJeffrey().changeSprite(true);
					Jeffrey.getActiveJeffrey().crouchElegable(true);
					if (Jeffrey.getActiveJeffrey().getAnimationHandler().flipHorizontal()) {
						Jeffrey.getActiveJeffrey().setSprite(Jeffrey.SAM_SWORD);
						Jeffrey.getActiveJeffrey().desyncSpriteX(0);
					}
				}
				hitEnemys.removeAll(hitEnemys);
			}
			if (!Jeffrey.getActiveJeffrey().getSprite().equals(samSwingSprite)) {
				Jeffrey.getActiveJeffrey().desyncSpriteX(0);
			}
		if (extended) {
		graphics =(Graphics2D) RenderLoop.window.getBufferGraphics();
		graphics.setColor(new Color (0x19ED45));
		graphics.setStroke(new BasicStroke (2));
		graphics.drawLine((int)this.getX() - Room.getViewX(), (int)this.getY() - Room.getViewY(), (int)currX - Room.getViewX(), (int)currY - Room.getViewY());
		}
	}
}
