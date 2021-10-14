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
import players.Player;
import resources.Sprite;

public class SlimeSword extends Weapon {
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
}
//TODO rewrite this once the Jeffrey sprites get worked out more
//	@Override
//	public void frameEvent() {
//		if (extended && (currX != desX || currY != desY)) {
//			if (desX > currX) {
//				currX = currX + 30;
//				if (currX > desX) {
//					currX = desX;
//					fifthteenthX = (desX - Player.getActivePlayer().getX())/15;
//				}
//			} else {
//				currX = currX - 30;
//				if (currX < desX) {
//					currX = desX;
//					fifthteenthX = ( Player.getActivePlayer().getX() - desX)/15;
//				}
//			}
//			if (desY > currY) {
//				currY = currY + (30*slope);
//				if (currY > desY) {
//					currY = desY;
//					fifthteenthY = (desY - Player.getActivePlayer().getY())/15;
//				}
//			} else {
//				currY = currY + (30*slope);
//				if (currY < desY) {
//					currY = desY;
//					fifthteenthY = (Player.getActivePlayer().getY() - desY)/15;
//				}
//			}
//		} else {
//			try {
//			if (Room.getTileProperties(Room.collisionLayer, (int)desX, (int)desY).getName() != null) {
//				if (Room.getTileProperties(Room.collisionLayer, (int)desX, (int)desY).getName().contains("code")) {
//					extended = false;
//					Player.getActivePlayer().stopFall(false);
//					Player.getActivePlayer().setVy(0);
//					broke = true;
//					Player.getActivePlayer().binded = false;
//				} else {
//					this.junkCode();
//				}
//			} else {
//				this.junkCode();
//			}
//			} catch (ArrayIndexOutOfBoundsException e) {
//				this.junkCode();
//			}
//		}
//		if (this.mouseButtonPressed(2)&& !extended && !broke) {
//			extended = true;
//			this.setHitboxAttributes(11, 0, 3, 3);
//			x = this.getX();
//			y = this.getY();
//			currX = x;
//			currY = y;
//			Point currentPoint = new Point (x,y);
//			Point mousePoint = new Point (getCursorX() + Room.getViewX(),getCursorY() + Room.getViewY());
//			slope =currentPoint.getSlope(mousePoint);
//			int toUse = 1;
//			if (slope > 5 || slope < -5) {
//				slope = slope/4;
//				toUse = 1/4;
//			}
//			boolean change = false;
//			if ((mousePoint.getX()> currentPoint.getX() && mousePoint.getY() < currentPoint.getY()) || (mousePoint.getX()> currentPoint.getX() && mousePoint.getY() > currentPoint.getY() )) {
//				change = true;
//			}
//			
//			while (true) {
//				try {
//				if (!change) {
//				if (!this.goXandY(this.getX() - toUse, this.getY() + slope)) {
//					
//					break;
//				}
//				} else {
//					if (!this.goXandY(this.getX() + toUse, this.getY() + slope)) {
//						break;
//					}
//				}
//				if (this.getY() < 0) {
//					this.setY(0);
//					break;
//				}
//				if (this.getX() < 0) {
//					this.setX(0);
//					break;
//				}
//				if (this.getX() > Room.getWidth() * 16) {
//					this.setX(Room.getWidth() * 16);
//					break;
//				}
//				if (this.getY() > Room.getHeight() * 16) {
//					this.setY(Room.getHeight() * 16);
//					break;
//				}
//				} catch (ArrayIndexOutOfBoundsException e) {
//					break;
//				}
//			}
//			desX = this.getX();
//			desY = this.getY();
//			
//			
//		} 
//		if ((this.keyPressed(32)&& extended) && !Player.getActivePlayer().isCrouched()) {
//			extended = false;
//			Player.getActivePlayer().stopFall(false);
//			Player.getActivePlayer().setVy(0);
//			broke = true;
//			Player.getActivePlayer().binded = false;
//		}
//		if (broke && !mouseButtonDown (2)) {
//			broke = false;
//		}
//		
//		
//		if (Player.getActivePlayer().getSprite().equals(samSwingSprite)) {
//			if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//			this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { -34, -34, -34, -34,-22,-19,-24,-22,-24, -24,-34,-34}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , Player.getActivePlayer());
//			} else {
//				this.createExpandingHitBoxBasedOnADiffrentObject(new int [] { 0, 0, 0, 45,0,0,0,0,0, 36,0,0}, new int [] {0,0,0,3,24,30,40,36,33,4,0,0}, new int [] {0,0,0,11,2,2,2,14,21,22,0,0} , new int [] {0,0,0,11,10,7,11,26,16,7,0,0} , Player.getActivePlayer());
//			}
//		for (int i = 0; i < Enemy.enemyList.size(); i ++) {
//			if (this.isColliding(Enemy.enemyList.get(i)) && !hitEnemys.contains(Enemy.enemyList.get(i)) ){
//				hitEnemys.add(Enemy.enemyList.get(i));
//				if (this.getTierInfo()[0] >= 1) {
//				Enemy.enemyList.get(i).knockback(10, this.getAnimationHandler().flipHorizontal());
//				}
//				Enemy.enemyList.get(i).appliedStatuses[3] = true;
//				Enemy.enemyList.get(i).damage (RNG.nextInt(50) + 20);
//			}
//		}
//		}
//		if (this.mouseButtonDown(0) && !Player.getActivePlayer().getSprite().equals(samSwingSprite)  && !Player.getActivePlayer().isCrouched()) {
//			Player.getActivePlayer().setSprite(samSwingSprite);
//			Player.getActivePlayer().getAnimationHandler().setFrameTime(50);
//			Player.getActivePlayer().changeSprite(false);
//			Player.getActivePlayer().crouchElegable(false);
//			if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//				Player.getActivePlayer().desyncSpriteX(-34);
//			} 
//		}
//		
//	}
//	@Override 
//	public void onFlip() {
//		if (Player.getActivePlayer().getSprite().equals(samSwingSprite)) {
//			if  (!Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//				Player.getActivePlayer().desyncSpriteX(-34);
//			} else {
//				Player.getActivePlayer().desyncSpriteX(0);
//			}
//		}
//	}
//	private void junkCode () {
//		if (extended) {
//			if (mouseButtonDown(2)) {
//				Point currentPoint = new Point (this.getX(),this.getY());
//				Point mousePoint = new Point (desX,desY);
//				slope =currentPoint.getSlope(mousePoint);
//				Player.getActivePlayer().stopFall(true); 
//				if (fifthteenthX > Room.TILE_WIDTH) {
//					fifthteenthX = Room.TILE_WIDTH;
//					}
//				if (fifthteenthY> Room.TILE_HEIGHT) {
//					fifthteenthY = Room.TILE_HEIGHT;
//					}
//					if (Player.getActivePlayer().getY ()  > desY) {
//						Player.getActivePlayer().goY(Player.getActivePlayer().getY() - fifthteenthY);
//					} else {
//						Player.getActivePlayer().goY(Player.getActivePlayer().getY() + fifthteenthY);
//					}
//					Player.getActivePlayer().binded = true;
//					if (Player.getActivePlayer().getX() > desX) {
//					
//						Player.getActivePlayer().goX(Player.getActivePlayer().getX() - fifthteenthX);
//					} else {
//						Player.getActivePlayer().goX(Player.getActivePlayer().getX() + fifthteenthX);
//					}
//			} else {
//				Player.getActivePlayer().binded = false;
//				Player.getActivePlayer().stopFall(true); 
//				double slack =  Player.getActivePlayer().getY() - desY;
//				if (slack < 0) {
//					slack = slack * -1;
//				}
//				if (slack > 1) {
//					double toUse = slack/15;
//					if (toUse > 8) {
//						toUse = 8;
//					}
//				if (!(Player.getActivePlayer().getX() < desX + 10 && Player.getActivePlayer().getX() > desX -10)) {
//				if (Player.getActivePlayer().getX() > desX) {
//					Player.getActivePlayer().vx = Player.getActivePlayer().vx  -(toUse/10);
//				} else {
//					Player.getActivePlayer().vx = Player.getActivePlayer().vx + (toUse/10);
//				}
//				}
//				if (Player.getActivePlayer().vx > 15.9 && Player.getActivePlayer().vx > 0) {
//					Player.getActivePlayer().vx = 15.9;
//				}
//				if (Player.getActivePlayer().vx < -15.9 && Player.getActivePlayer().vx  < 0) {
//					Player.getActivePlayer().vx = -15.9;
//				}
//				} else {
//					Player.getActivePlayer().vx = 0;
//					Player.getActivePlayer().goX(desX);
//				}
//			}
//		}
//	}
//	@Override 
//	public void draw () {
//			if (Player.getActivePlayer().getSprite().equals(samSwingSprite) && Player.getActivePlayer().getAnimationHandler().getFrame() ==  9) {
//				if ( !mouseButtonDown(0)) {
//					Player.getActivePlayer().changeSprite(true);
//					Player.getActivePlayer().crouchElegable(true);
//					if (Player.getActivePlayer().getAnimationHandler().flipHorizontal()) {
//						Player.getActivePlayer().setSprite(Player.SAM_SWORD);
//						Player.getActivePlayer().desyncSpriteX(0);
//					}
//				}
//				hitEnemys.removeAll(hitEnemys);
//			}
//			if (!Player.getActivePlayer().getSprite().equals(samSwingSprite)) {
//				Player.getActivePlayer().desyncSpriteX(0);
//			}
//		if (extended) {
//		graphics =(Graphics2D) RenderLoop.window.getBufferGraphics();
//		graphics.setColor(new Color (0x19ED45));
//		graphics.setStroke(new BasicStroke (2));
//		graphics.drawLine((int)this.getX() - Room.getViewX(), (int)this.getY() - Room.getViewY(), (int)currX - Room.getViewX(), (int)currY - Room.getViewY());
//		}
//	}
//}
