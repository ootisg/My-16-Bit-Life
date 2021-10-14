package enemys;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import gameObjects.QuickTimer;
import gameObjects.RetractableObject;
import gameObjects.Warning;
import main.GameObject;
import main.ObjectHandler;
import map.Room;
import mapObjects.MapObject;
import players.Player;
import resources.Sprite;

public class ZombeeTreeBoss extends Enemy{
	Eye eye;
	Leaves leaves;
	Pusher pusher;
	MapObject wall;
	BeeHive hive;
	
	boolean attacking1 = false; // using an attack from the first attack list (short root, eye, or lazer)
	boolean attacking2 = false; // using an attack from the second attack list (long root or worms)
	
	long lastAttack1; // used for timing of the first loop
	int downTime1 = 0; 
	
	long lastAttack2; // used for timing of the second loop
	int downTime2 = 0;
	
	// odds for attack usage in loop 1
	int laserChance = 0;
	int eyeChance = 30;
	int smallRootChance = 35;
	int nothingChance1 = 15;
	
	// odds for attack usage in loop 2
	int largeRootChance = 45;
	int wormChance = 45;
	int nothingChance2 = 10;
	
	private final static int CHANCE_INCREASE_1 = 4;
	private final static int CHANCE_DECREASE_1 = 12;
	
	private final static int CHANCE_INCREASE_2 = 5;
	private final static int CHANCE_DECREASE_2 = 10;
	
	private final Sprite TREE_SPRITE = new Sprite ("resources/sprites/zombee_tree/tree.png");
	
	public ZombeeTreeBoss () {
		eye = new Eye (this);
		leaves = new Leaves (this);
		pusher = new Pusher ();
		wall = new MapObject(16,272);
		lastAttack1 = System.currentTimeMillis();
		hive = new BeeHive();
		this.setSprite(TREE_SPRITE);
	}
	@Override
	public void declare (double x, double y) {
		super.declare(x, y);
		wall.declare(x,y);
		pusher.declare(this.getX() - 112, this.getY() + 272);
		eye.declare(this.getX() - 50, this.getY() + 100);
		leaves.declare(this.getX() - 832,this.getY() - 80);
		hive.declare(this.getX() - 160, this.getY() + 40);
	}
	// read the method name dumbfuck 
	private void useSpikeAttackShort () {
		double spikeStartPos = this.getX() - 960;
		double spikeEndPos = this.getX() - 112;
		
		double warnTime = 1.5; // the warning time
		
		while (spikeEndPos > spikeStartPos) {
			 ShortSpike spike = new ShortSpike (warnTime,this);
			spike.declare(spikeStartPos, this.getY() + 272 + 128);
			spikeStartPos = spikeStartPos + 16;
		}
		
	}
	//uses the long spike attack
	private void useSpikeAttackLong () {
		double warnTime = 1.5;
		
		LongSpike spike = new LongSpike (warnTime,this);
		spike.declare(Player.getActivePlayer().getX(), this.getY() + 272 + 128);
	}
	
	@Override
	public void enemyFrame () {
		if (!attacking1 && System.currentTimeMillis() > lastAttack1 + downTime1) {
			attacking1 = true;
			this.attack(0);

		}
		if (!attacking2 && System.currentTimeMillis() > lastAttack2 + downTime2) {
			attacking2 = true;
			this.attack(1);
		}
	}
	/**
	 * deals with choseing the correct attack to use
	 * @param loopID the group of attacks to chose from
	 */
	private void attack (int loopID) {
		if (loopID == 0) {
			// choeses a value between 1 and the sum of all the percentages (witch is 100 usally) 
			Random rand = new Random ();
			int maxChoice = laserChance + eyeChance + smallRootChance + nothingChance1;
			int attackChoice = rand.nextInt(maxChoice) + 1;
			
			if (attackChoice < laserChance) {
				//uses attack
				//eye.useLaserAttack();
				
				this.endAttack(0);
				//updates percentages
				laserChance = laserChance - CHANCE_DECREASE_1;
				eyeChance = eyeChance + CHANCE_INCREASE_1;
				smallRootChance = smallRootChance + CHANCE_INCREASE_1;
				nothingChance1 = nothingChance1 + CHANCE_INCREASE_1;
			
			} else if (attackChoice < laserChance + eyeChance && eyeChance != 1000000) {
				// uses attack
				eye.useThrowAttack();
				
				//updates percentages
				eyeChance = eyeChance - CHANCE_DECREASE_1;
				laserChance = laserChance + CHANCE_INCREASE_1;
				smallRootChance = smallRootChance + CHANCE_INCREASE_1;
				nothingChance1 = nothingChance1 + CHANCE_INCREASE_1;
			} else if (attackChoice < laserChance + eyeChance + smallRootChance) {
				// uses attack
				this.useSpikeAttackShort();
				//updates percentages
				eyeChance = eyeChance + CHANCE_INCREASE_1;
				laserChance = laserChance + CHANCE_INCREASE_1;
				smallRootChance = smallRootChance - CHANCE_DECREASE_1;
				nothingChance1 = nothingChance1 + CHANCE_INCREASE_1;
			} else {
				// set up another attack
				this.endAttack(0);
				
				//updates percentages
				eyeChance = eyeChance + CHANCE_INCREASE_1;
				laserChance = laserChance + CHANCE_INCREASE_1;
				smallRootChance = smallRootChance + CHANCE_INCREASE_1;
				nothingChance1 = nothingChance1 - CHANCE_DECREASE_1;
			}
			
		} else if (loopID == 1) {
			// choeses a value between 1 and the sum of all the percentages (witch is 100 usally) 
			Random rand = new Random ();
			int maxChoice = largeRootChance + wormChance + nothingChance2;
			int attackChoice = rand.nextInt(maxChoice) + 1;
			
			if (attackChoice < largeRootChance) {
				//uses attack
				this.useSpikeAttackLong();
				
				//updates percentages
				largeRootChance = largeRootChance - CHANCE_DECREASE_2;
				wormChance = wormChance + CHANCE_INCREASE_2;
				nothingChance2 = nothingChance2 + CHANCE_INCREASE_2;
			
			} else if (attackChoice < largeRootChance + wormChance) {
				// uses attack
				leaves.rustle();
				
				//updates percentages
				largeRootChance = largeRootChance + CHANCE_INCREASE_2;
				wormChance = wormChance - CHANCE_DECREASE_2;
				nothingChance2 = nothingChance2 + CHANCE_INCREASE_2;
			} else {
				// set up another attack
				this.endAttack(1);
				
				//updates percentages
				largeRootChance = largeRootChance + CHANCE_INCREASE_2;
				wormChance = wormChance + CHANCE_INCREASE_2;
				nothingChance2 = nothingChance2 - CHANCE_DECREASE_2;
			}
		}
	}
	
	public void endAttack (int loopID) {
		if (loopID == 0) {
			if (attacking1) {
				lastAttack1 = System.currentTimeMillis();
				attacking1 = false;
				Random rand = new Random ();
				downTime1 = (rand.nextInt(100)) * 10;
			}
		} else if (loopID == 1) {
			if (attacking2) {
				lastAttack2 = System.currentTimeMillis();
				attacking2 = false;
				Random rand = new Random ();
				downTime2 = (rand.nextInt(100)) * 10;
			}
		}
	}
	
	@Override
	public void deathEvent () {
		wall.forget();
	}
	
	/*@Override
	// TODO make the indent draw funny
	public void draw () {
		
	}*/
	public class Eye extends Enemy {
		ZombeeTreeBoss boss;
		
		private final Sprite EYE_SPRITE = new Sprite ("resources/sprites/zombee_tree/Zombee_Eyeball.png");
		private final Sprite EYE_SPRITE_SHRIVELED = new Sprite ("resources/sprites/zombee_tree/Zombee_Eyeball_Shriveled.png");
		
		//where the eye SHOULD be if it is attached to the tree y position only
		private double indentPos;
		
		private double xIndent;
		
		
		//wheather or not the eye is attached to the tree
		private boolean attached = true;
		
		Point iris = new Point (14, 56);
		Point center = new Point (56,56);
		
		Point irisShriveled = new Point (10,39);
		Point centerSchrivled = new Point (40,40);
		
		Point focusPoint = new Point (0,0);
		
		double prevAngle = 0; // prevents shakeing of eye
		double angCopy = 0;
		
		double vx = 0;
		double vy = 0;
		
		boolean rollback = false;
		
		private final static double GRAVITY = 2;
		private final static double FRICTION = 5;
		
		public Eye (ZombeeTreeBoss boss) {
			this.setHealth(1);
			this.boss = boss;
			this.setRenderPriority(-1);
			this.setSprite(EYE_SPRITE);
			this.enablePixelCollisions();
			this.setHitboxAttributes(EYE_SPRITE.getWidth(), EYE_SPRITE.getHeight());
			ArrayList <String> excludeList = new ArrayList<String> ();
			excludeList.add("MapObject");
			this.setExcludeList(excludeList);
			this.adjustHitboxBorders();
		}
		
		@Override
		public void damage (int damage) {
			boss.damage(damage);
		}
		//TODO make the throw attack work
		public void useThrowAttack() {
			attached = false;
			this.setSprite(EYE_SPRITE_SHRIVELED);
			this.setHitboxAttributes(EYE_SPRITE_SHRIVELED.getWidth(), EYE_SPRITE_SHRIVELED.getHeight());
			vx = -35;
			vy = -10;
		}
		@Override
		public void enemyFrame () {
			if (!this.attached) {
				if (!this.goX(this.getX() + vx)) {
					this.setY(this.getY() - vx);
					if (this.getX() < xIndent) {
						this.setX(this.getX() + vx);
						if (this.getX() > xIndent){
							this.setX(xIndent);
						}
					}
					if (this.getY() < indentPos) {
						this.unShrivel();
						indentPos = this.getY();
					}
				}
				if (!(vx > 3)) {
					vy = vy + GRAVITY;
					if(!this.goY(this.getY() + vy)) {
						vy = 0;
						rollback = true;
						ArrayList <String> empty = new ArrayList <String>();
						this.setExcludeList(empty);
					}
				}
				if (rollback && vx < 3) {
					vx = vx + FRICTION;
				}
			}
			focusPoint = new Point ((int)Player.getActivePlayer().getX(),(int)Player.getActivePlayer().getY());
			if (this.isColliding("BeeHive")) {
				boss.hive.spawnBee(3);
			}
		}
		
		private void unShrivel () {
			attached = true;
			this.setSprite(EYE_SPRITE);
			rollback = false;
			
			ArrayList <String> excludeList = new ArrayList<String> ();
			excludeList.add("MapObject");
			this.setExcludeList(excludeList);
			
			this.setHitboxAttributes(EYE_SPRITE.getWidth(), EYE_SPRITE.getHeight());
			
			boss.endAttack(0);
			
		}
		
		//TODO make the laser attack work
		public void useLaserAttack() {
			
		}
		@Override
		public void draw () {
			Point irisUse;
			Point centerUse;
			
			
			if (attached) {
				irisUse = iris;
				centerUse = center;
			} else {
				irisUse = irisShriveled;
				centerUse = centerSchrivled;
			}
			double angle;
			if (!rollback) {
				angle = Math.atan2(focusPoint.getY() - (irisUse.y + this.getY()), focusPoint.getX() - (irisUse.x + this.getX()));
			} else {
				angle = angCopy + (vx)/10;
				
			}
			//I diden't realise it until now but this code does not work the way it is supposed too it still like works in game, but in code it is doing much diffrent things than I intended it too
			if ((angle != prevAngle && angle != -prevAngle) || rollback) {
				this.getSprite().drawRotated((int)this.getX() - Room.getViewX(), (int)this.getY() - Room.getViewY(), this.getAnimationHandler().getFrame(), centerUse.x, centerUse.y, angle - Math.PI);
				if (!rollback) {
					angle = prevAngle;
				}
				//cool formula I found online (not entirly sure how it even works honestly)
				if (attached) {
					iris = new Point ((int) (center.x + (iris.x - center.x)*Math.cos(angle) - (iris.y - center.y) * Math.sin(angle)), (int) (center.y + (iris.x - center.x)*Math.sin(angle) + (iris.y - center.y)*Math.cos(angle))); 
				} else {
					irisShriveled = new Point ((int) (centerSchrivled.x + (irisShriveled.x - centerSchrivled.x)*Math.cos(angle) - (irisShriveled.y - centerSchrivled.y) * Math.sin(angle)), (int) (centerSchrivled.y + (irisShriveled.x - centerSchrivled.x)*Math.sin(angle) + (irisShriveled.y - centerSchrivled.y)*Math.cos(angle)));
				}
				angCopy = angle;
			}
			
		}
		
		//make sure the indent moves along with the eye if the eye is attached to the tree
		@Override
		public void setY(double val) {
			super.setY(val);
			if (attached) {
				indentPos = val;
			}
		}
		@Override
		public boolean goY(double val) {
			boolean answer = super.goY(val);
			if (attached && answer) {
				indentPos = val;
			}
			return answer;
		}
		
		@Override
		public void onDeclare () {
			indentPos = this.getY();
			xIndent = this.getX();
		}
		public double getIndentPos () {
			return indentPos;
		}
		
		public boolean isAttached() {
			return attached;
		}
	}
	
	public class Leaves extends GameObject {
		
		private final Sprite LEAVE_SPRITE = new Sprite ("resources/sprites/zombee_tree/leaves.png");
		
		// the max amount of worms that can drop
		private final int MAX_WORMS = 6;
		
		private final int LEAVE_HEIGHT = 160;
		private final int LEAVE_WIDTH = 832;
		
		private boolean rustling = false;
		
		ZombeeTreeBoss boss;
		
		public Leaves (ZombeeTreeBoss boss) {
			this.setSprite(LEAVE_SPRITE);
			this.setRenderPriority(1);
			this.boss = boss;
		}
		@Override
		public void frameEvent () {
			if (rustling) {
				if (ObjectHandler.getObjectsByName("ZombeeWorm").isEmpty()) {
					rustling = false;
					boss.endAttack(1);
				}
			}
		}
		//drops worms 
		public void rustle () {
			Random rand = new Random ();
			int wormCount = rand.nextInt(MAX_WORMS) + 1;
			
			for (int i = 0; i < wormCount; i++) {
				ZombeeWorm worm = new ZombeeWorm ();
				int place = rand.nextInt(LEAVE_WIDTH);
				worm.declare(place + this.getX(), rand.nextInt(this.getLeaveHeight(place)) + this.getY());
			}
			ZombeeWorm worm = new ZombeeWorm ();
			worm.declare(Player.getActivePlayer().getX(), rand.nextInt(this.getLeaveHeight((int)Player.getActivePlayer().getX())) + this.getY());
			rustling = true;
		}
		private int getLeaveHeight (int place) {
			for (int i = LEAVE_HEIGHT - 1; i > 0; i--) {
				try {
				if (!this.isTransparent(place, i)) {
					return i;
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					return 1; // illegal argument 
				}
			}
			return 0;
		}
		/**
		 * copy pasted from stack overflow babyyyyyyy
		 */
		private boolean isTransparent( int x, int y) {
			  int pixel = LEAVE_SPRITE.getFrame(0).getRGB(x,y);
			  if( (pixel>>24) == 0x00 ) {
			      return true;
			  }
			  return false;
			}
	}
	
	public class Pusher extends MapObject {
		
		private final Sprite PUSHER_SPRITE = new Sprite ("resources/sprites/zombee_tree/pusher.txt");
		
		private final double [] X_OFFSETS = {0,80,96,112};
		private final double [] Y_OFFSETS = {112,96,64,0};
		private final double [] WIDTHS = {80,20,16,128};
		private final double [] HEIGHTS = {16,18,32,128};
		
		
		QuickTimer timer = new QuickTimer (5.5);
		
		public Pusher () {
			this.setSprite(PUSHER_SPRITE);
			this.setHitboxAttributes(X_OFFSETS, Y_OFFSETS, WIDTHS, HEIGHTS);
		}
		@Override
		public void frameEvent () {
			super.frameEvent();
			this.setHitboxAttributes(PUSHER_SPRITE.getWidth(), PUSHER_SPRITE.getHeight());
			if (this.isColliding(Player.getActivePlayer())) {
				timer.turnOn();
			} else {
				timer.turnOff();
			}
			this.setHitboxAttributes(X_OFFSETS, Y_OFFSETS, WIDTHS, HEIGHTS);
			if (timer.hasElapsed()) {
				timer.setWaitTime(5.5);
				this.push();
			}
		}
		//TODO push duh
		public void push () {
			Player.getActivePlayer().vx = -25;
			Player.getActivePlayer().setVy(-10);
		}
		
	}
	
	public class Spike extends RetractableObject {
		
		public Sprite spikeSprite = new Sprite ("resources/sprites/zombee_tree/Spike_Short.png");
		
		public double stayTime = .3; // time that the spikes stick around
		
		Warning warning;
		QuickTimer time;
		
		ZombeeTreeBoss boss;
		
		int attackLoop = 0;
		
		double speed = 1;
		
		
		public Spike (double waitTime, ZombeeTreeBoss boss) {
			super(2.2);
			warning = new Warning (waitTime, new Sprite ("resources/sprites/config/basicWarrning.txt"));
			this.hide();
			this.boss = boss;
			time = new QuickTimer (stayTime);
		}
		@Override
		public void onDeclare () {
			warning.declare(this.getX(), this.getY() - 16);
			this.setSprite(spikeSprite);
			this.setSpeed(speed);
			
		}
		@Override
		public void frameEvent () {
			super.frameEvent();
			if (warning.isWarned() && !this.visible) {
				this.extend();
				this.show();
			}
			if (this.isExtended()) {
				time.turnOn();
			}
			if (time.hasElapsed()) {
				this.retract();
				if (this.isRetracted()) {
					boss.endAttack(attackLoop);
					this.forget();
				}
			}
			if (this.isColliding(Player.getActivePlayer())) {
				Player.getActivePlayer().damage(30);
			}
			if (this.isColliding("BeeHive")) {
				boss.hive.damage(1);
			}
		}
	}
	public class ShortSpike extends Spike{
		public ShortSpike (double waitTime, ZombeeTreeBoss boss) {
			super(waitTime,boss);
			this.speed = 10;
		}
	}
	public class LongSpike extends Spike {
		public LongSpike (double waitTime, ZombeeTreeBoss boss) {
			super(waitTime,boss);
			this.stayTime = 5;
			this.spikeSprite = new Sprite ("resources/sprites/zombee_tree/spikeLong.png");
			attackLoop = 1;
			this.speed = 60;
		}
	}
	public class BeeHive extends Enemy {
		
		private final Sprite BEE_SPRITE = new Sprite ("resources/sprites/zombee_tree/beehive.png");
		
		QuickTimer time = new QuickTimer (.5);
		
		public BeeHive () {
			this.setSprite(BEE_SPRITE);
			this.setHitboxAttributes(0, 0, BEE_SPRITE.getWidth(), BEE_SPRITE.getHeight());
			this.setHealth(1);
			time.turnOn();
		}
		@Override
		public void damage (int damage) {
			if (time.hasElapsed()) {
				Zombee bee = new Zombee ();
				bee.declare(this.getX(), this.getY());
				time.setWaitTime(.5);
			}
		}
		public void spawnBee(int numBees) {
			if (time.hasElapsed()) {
				for (int i = 0; i < numBees; i++) {
					Zombee bee = new Zombee ();
					bee.declare(this.getX(), this.getY());
					time.setWaitTime(.5);
				}
			}
		}
	}
}
