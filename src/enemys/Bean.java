package enemys;

import map.Room;
import players.Jeffrey;
import resources.Sprite;
import vector.Vector2D;

public class Bean extends Enemy {

	public static final double HAND_CENTER_X = 12;
	public static final double HAND_CENTER_Y = 8;
	
	public static final Sprite armBubble = new Sprite ("resources/sprites/config/bubble_beast_arm_bubble.txt");
	public static final Sprite handBubble = new Sprite ("resources/sprites/bubble_beast_hand.png");
	
	public static final Sprite armBubblePop = new Sprite ("resources/sprites/config/bubble_beast_arm_pop.txt");
	
	private Bee parent;
	
	private boolean isHand = false;
	private boolean rotationLocked = false;
	private double prevRotation;
	
	private boolean isFree = false;
	private boolean popping = false;
	
	public Bean () {
		this.setSprite (armBubble);
		this.getAnimationHandler ().setFrameTime (33);
		this.setHitboxAttributes (0, 0, 16, 16);
		this.setHealth (30);
	}
	
	public void setBee (Bee bee) {
		this.parent = bee;
	}
	
	public void setFree () {
		isFree = true;
	}
	
	public void makeHand () {
		isHand = true;
		this.setBasePower (20);
		this.setSprite (handBubble);
	}
	
	public void setRotationLocked (boolean lock) {
		rotationLocked = lock;
	}
	
	@Override
	public void enemyFrame () {
		if (isFree && !popping) {
			setY (getY () - 1);
		}
		if (popping && getAnimationHandler ().getFrame () == armBubblePop.getFrameCount () - 1) {
			super.deathEvent ();
		}
	}
	
	@Override
	public void deathEvent () {
		if (!popping) {
			parent.removeBean (this);
			this.setSprite (armBubblePop);
			this.getAnimationHandler ().setAnimationFrame (0);
			this.getAnimationHandler ().setRepeat (false);
			isHand = false; //So that animation works
			popping = true;
		}
	}
	
	@Override
	public void draw () {
		if (!isHand) {
			super.draw ();
		} else {
			double ang;
			if (!rotationLocked) {
				Jeffrey j = Jeffrey.getActiveJeffrey ();
				double targetX = j.getX ();
				double targetY = j.getY ();
				Vector2D a = new Vector2D (HAND_CENTER_X, HAND_CENTER_Y, 1, 16);
				Vector2D b = new Vector2D (getX () + HAND_CENTER_X, getY () + HAND_CENTER_Y, targetX, targetY);
				//Calculate the slope and y coordinate of the vector
				double slope = a.y / a.x;
				double y = slope * (targetX - (getX () + HAND_CENTER_X)) + (getY () + HAND_CENTER_Y);
				double dirVal = y - targetY;
				ang = Math.acos (a.getDot (b) / (a.getMagnitude () * b.getMagnitude ()));
				if (dirVal < 0) {
					ang = -ang;			
				}
				prevRotation = ang;
			} else {
				ang = prevRotation;
			}
			this.getSprite ().drawRotated ((int)getX () - Room.getViewX (), (int)getY () - Room.getViewY (), 0, HAND_CENTER_X, HAND_CENTER_Y, ang);
		}
	}
	
}
