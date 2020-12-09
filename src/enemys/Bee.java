package enemys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Vector;

import main.GameAPI;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import spriteParsers.ParsedFrame;
import spriteParsers.PixelParser;
import vector.Vector2D;

public class Bee extends Enemy {
	
	public static final int ATTACK_RADIUS = 400;
	public static final int ATTACK_DELAY = 60;
	public static final int DOUBLE_PUNCH_DELAY = 15;
	public static final int ATTACK_UNCERTAINTY = 10;
	
	public static Vector2D headOffset;
	public static Vector2D bubbleOffset;
	public static Point bubbleCenter;
	
	public static final Sprite beastBubble = new Sprite ("resources/sprites/bubble_beast_bubble.png");
	public static final Sprite beastHeadLeft = new Sprite ("resources/sprites/config/bubble_beast_head_left.txt");
	public static final Sprite beastHeadRight = new Sprite ("resources/sprites/config/bubble_beast_head_right.txt");
	public static final Sprite beastSadLeft = new Sprite ("resources/sprites/config/bubble_beast_sad_left.txt");
	public static final Sprite beastSadRight = new Sprite ("resources/sprites/config/bubble_beast_sad_right.txt");
	//Frame 0 is normal, 1 is looking up, 2 is looking down
	//public static final Sprite 
	
	public static final Sprite maskLeft = new Sprite ("resources/sprites/sprte_masks/bubble_beast_mask_left.png");
	public static final Sprite maskRight = new Sprite ("resources/sprites/sprte_masks/bubble_beast_mask_right.png");
	
	private static boolean initialized = false;
	private static Vector2D[] arm1OffsetsLeft;
	private static Vector2D[] arm1OffsetsRight;
	private static Vector2D[] arm2OffsetsLeft;
	private static Vector2D[] arm2OffsetsRight;
	
	private Vector2D[] arm1OffsetsPrevious;
	private Vector2D[] arm2OffsetsPrevious;
	private Vector2D[] arm1Offsets;
	private Vector2D[] arm2Offsets;
	
	private Bean[] arm1;
	private Bean[] arm2;
	
	private int arm1Length = 5;
	private int arm2Length = 5;

	public static final int TURN_TIME = 20;
	private int direction = 0; //0 is left, 1 is right
	private int turnTimer = 0;
	
	public static final int PUNCH_TIME = 20;
	private boolean arm1Punching = false;
	private boolean arm2Punching = false;
	private boolean arm1Retracting = false;
	private boolean arm2Retracting = false;
	private int punchTime1 = 0; //1 is arm 1, 2 is arm 2
	private int punchTime2 = 0;
	private Point punchTarget1;
	private Point punchTarget2;
	private int attackTime = ATTACK_DELAY;
	private int delayTime = -1;
	
	private boolean sadMode = false;
	private int sadTime = 0;
	
	public Bee () {	
		this.setSprite (beastHeadLeft);
		this.getAnimationHandler ().setFrameTime (0);
		this.setHitboxAttributes (24, 16, 31, 31);
		this.setHealth (1000);
	}
	
	@Override
	public void onDeclare () {
		
		//Set offsets to left
		arm1Offsets = arm1OffsetsLeft;
		arm2Offsets = arm2OffsetsLeft;
		
		//Make the beans
		arm1 = new Bean[5];
		arm2 = new Bean[5];
		for (int i = 4; i >= 0; i--) {
			arm1 [i] = new Bean ();
			arm2 [i] = new Bean ();
			double arm1X = getX () + bubbleCenter.x + arm1Offsets [i].x - 8;
			double arm1Y = getY () + bubbleCenter.y + arm1Offsets [i].y - 8;
			double arm2X = getX () + bubbleCenter.x + arm2Offsets [i].x - 8;
			double arm2Y = getY () + bubbleCenter.y + arm2Offsets [i].y - 8;
			arm1 [i].declare (arm1X, arm1Y);
			arm2 [i].declare (arm2X, arm2Y);
			arm1 [i].setBee (this);
			arm2 [i].setBee (this);
		}
		arm1 [4].makeHand ();
		arm2 [4].makeHand ();
	}
	
	@Override
	public void init () {
		if (initialized == false) {
			
			//Get the parsed images (woot)
			ParsedFrame facingLeft = PixelParser.parse (maskLeft).get (0);
			ParsedFrame facingRight = PixelParser.parse (maskRight).get (0);
			HashMap<Integer, Point> colorMapLeft = facingLeft.getColorMap ();
			HashMap<Integer, Point> colorMapRight = facingRight.getColorMap ();
			System.out.println (colorMapLeft);
			
			//Parse out the body pixels
			headOffset = new Vector2D (colorMapLeft.get (0xFF0000FF)); //Head offset is full blue
			bubbleOffset = new Vector2D (colorMapLeft.get (0xFF000080)); //Head offset is 0x80 blue
			bubbleCenter = new Point ((int)bubbleOffset.x + 16, (int)bubbleOffset.y + 16); //Center of the bubble is (16, 16) from its top-left
			
			//Parse out the arm pixels
			arm1OffsetsLeft = new Vector2D[5];
			arm1OffsetsRight = new Vector2D[5];
			arm2OffsetsLeft = new Vector2D[5];
			arm2OffsetsRight = new Vector2D[5];
			for (int i = 0; i < 5; i++) {
				int arm1Color = 0x100000 * (i + 1) + 0xFF000000; //Arm 1 is red
				int arm2Color = 0x001000 * (i + 1) + 0xFF000000; //Arm 2 is green
				//Get the raw points
				Point arm1Left = colorMapLeft.get (arm1Color);
				Point arm1Right = colorMapRight.get (arm1Color);
				Point arm2Left = colorMapLeft.get (arm2Color);
				Point arm2Right = colorMapRight.get (arm2Color);
				//Fill the offset arrays
				arm1OffsetsLeft [i] = new Vector2D (bubbleCenter, arm1Left);
				arm1OffsetsRight [i] = new Vector2D (bubbleCenter, arm1Right);
				arm2OffsetsLeft [i] = new Vector2D (bubbleCenter, arm2Left);
				arm2OffsetsRight [i] = new Vector2D (bubbleCenter, arm2Right);
			}
			
			//Set initialized to true
			initialized = true;
		}
	}
	
	@Override
	public void enemyFrame () {
		
		//Handle sad mode
		if (arm1Length == 0 && arm2Length == 0 && !sadMode) {
			sadMode = true;
			sadTime = 0;
			if (direction == 0) {
				setSprite (beastSadLeft);
			} else {
				setSprite (beastSadRight);
			}
		}
		if (sadTime == 30) {
			getAnimationHandler ().setAnimationFrame (1);
		}
		if (sadTime == 75) {
			getAnimationHandler ().setFrameTime (350);
			getAnimationHandler ().setRepeat (false);
		}
		if (sadMode) {
			sadTime++;
			return;
		}
		
		//Dynamic eyes! UwU
		double jeffY = Jeffrey.getActiveJeffrey ().getY ();
		if (getY () - jeffY > 32) {
			getAnimationHandler ().setAnimationFrame (2);
		} else if (getY () - jeffY < -48) {
			getAnimationHandler ().setAnimationFrame (1);
		} else {
			getAnimationHandler ().setAnimationFrame (0);
		}
		
		//Temporary punch 'AI'
		if (Jeffrey.getActiveJeffrey ().keyPressed ('1')) {
			punch (1);
		} else if (Jeffrey.getActiveJeffrey ().keyPressed ('2')) {
			punch (2);
		} else if (Jeffrey.getActiveJeffrey ().keyDown ('3')) {
			setY (getY () + 1);
		} else if (Jeffrey.getActiveJeffrey ().keyDown ('4')) {
			setY (getY () - 1);
		}
		//Handle attacking
		if (Math.abs (Jeffrey.getActiveJeffrey ().getX () - getX ()) < ATTACK_RADIUS && Math.abs (Jeffrey.getActiveJeffrey ().getY () - getY ()) < ATTACK_RADIUS) {
			if (attackTime == 0) {
				int attackType = (int)(Math.random () * 8);
				if (attackType < 4) {
					if (attackType < 2) {
						punch (1);
					} else {
						punch (2);
					}
				} else if (attackType <= 6) {
					punch (1);
					delayTime = DOUBLE_PUNCH_DELAY;
				} else {
					punch (1);
					punch (2);
				}
				attackTime = (int)(ATTACK_DELAY + Math.random () * (ATTACK_UNCERTAINTY * 2) - ATTACK_UNCERTAINTY);				
			}
			if (delayTime == 0) {
				punch (2);
			}
			attackTime--;
			if (delayTime != -1) {
				delayTime--;
			}
		} else {
			attackTime = ATTACK_DELAY;
		}
		
		//Handle turning
		if (turnTimer != 0) {
			
			//Move the bubbles
			for (int i = 0; i < 5; i++) {
				//Get offset vectors
				Vector2D offs1 = new Vector2D (arm1OffsetsPrevious [i], arm1Offsets [i]);
				Vector2D offs2 = new Vector2D (arm2OffsetsPrevious [i], arm2Offsets [i]);
				double t = (double)(TURN_TIME - turnTimer) / TURN_TIME;
				offs1 = offs1.getScaled (t);
				offs2 = offs2.getScaled (t);
				
				//Calculate and set bubble positions
				double arm1X = getX () + bubbleCenter.x + arm1OffsetsPrevious [i].x + offs1.x - 8;
				double arm1Y = getY () + bubbleCenter.y + arm1OffsetsPrevious [i].y + offs1.y - 8;
				double arm2X = getX () + bubbleCenter.x + arm2OffsetsPrevious [i].x + offs2.x - 8;
				double arm2Y = getY () + bubbleCenter.y + arm2OffsetsPrevious [i].y + offs2.y - 8;
				if (arm1 [i] != null) {
					arm1 [i].setX (arm1X);
					arm1 [i].setY (arm1Y);
				}
				if (arm2 [i] != null) {
					arm2 [i].setX (arm2X);
					arm2 [i].setY (arm2Y);
				}
			}
			
			//Set the face sprite accordingly
			if (turnTimer == TURN_TIME) {
				if (direction == 0) {
					setSprite (beastHeadLeft);
				} else {
					setSprite (beastHeadRight);
				}
			}
			turnTimer--;
		}
		
		//Check to start turning around
		if (Jeffrey.getActiveJeffrey ().getX () < getX () && direction == 1 && turnTimer == 0) {
			turn (0);
		} else if (Jeffrey.getActiveJeffrey ().getX () > getX () && direction == 0 && turnTimer == 0) {
			turn (1);
		}
		
		//Handle punching
		for (int armIndex = 1; armIndex <= 2; armIndex++) {
			Point target = null;
			Vector2D shoulder = null;
			Vector2D hand = null;
			Vector2D[] arm = null;
			Bean[] armBeans = null;
			double punchAmt = 0;
			int bubbleCount = 0;
			boolean doPunch = false;
			if (armIndex == 1 && arm1Punching) {
				//Setup for arm 1
				shoulder = arm1Offsets [0];
				hand = arm1Offsets [4];
				arm = arm1Offsets;
				armBeans = arm1;
				punchAmt = (double)punchTime1 / PUNCH_TIME;
				target = punchTarget1;
				bubbleCount = arm1Length;
				doPunch = true;
			}
			if (armIndex == 2 && arm2Punching) {
				//Setup for arm 2
				shoulder = arm2Offsets [0];
				hand = arm2Offsets [4];
				arm = arm2Offsets;
				armBeans = arm2;
				punchAmt = (double)punchTime2 / PUNCH_TIME;
				target = punchTarget2;
				bubbleCount = arm2Length;
				doPunch = true;
			}
			
			if (doPunch) {
				
				//Get the player's position as a vector
				double playerX = Jeffrey.getActiveJeffrey ().getX ();
				double playerY = Jeffrey.getActiveJeffrey ().getY ();
				Vector2D player = new Vector2D (playerX, playerY);
				
				//Get the offset vector from the shoulder to the player
				Vector2D shoulderWorld = toWorldCoords (shoulder);
				Vector2D shoulderToPlayer = new Vector2D (shoulderWorld, player);
				Vector2D shoulderToHand = new Vector2D (shoulder, hand);
				
				//Get the length of the orthographic projection of the hand vector on the 'punch' vector
				double startLength = shoulderToPlayer.getDot (hand) / shoulderToPlayer.getDot (shoulderToPlayer);
				startLength *= shoulderToPlayer.getMagnitude ();
				
				//Get the magnitude of the 'punch' vector for the end length
				double endLength = shoulderToPlayer.getMagnitude ();
				
				//Get the scalar for the punch
				double finalAmt = (endLength - startLength) * punchAmt;
				finalAmt += startLength;
				finalAmt /= shoulderToPlayer.getMagnitude ();
				
				//Get the 'final' punch vector
				Vector2D punchVector = shoulderToPlayer.getScaled (finalAmt);
				
				for (int i = 0; i < 5; i++) {
					
					if (armBeans [i] != null) {
						//Set the deform factor appropriately
						double deform = punchAmt * 5;
						if (deform > 1) {
							deform = 1;
						}
						
						//Compute the bubble position
						Vector2D bubbleVector = punchVector.getScaled ((double)(i + 1) / bubbleCount);
						Vector2D offsVector = new Vector2D (shoulder, arm [i]);
						bubbleVector = bubbleVector.getScaled (deform);
						offsVector = offsVector.getScaled (1 - deform);
						Vector2D finalVector = bubbleVector.getSum (offsVector);
						finalVector = toWorldCoords (finalVector);
						finalVector = finalVector.getSum (shoulder);
						
						//Set the bubbles to their correct spots
						armBeans [i].setX (finalVector.x - 8);
						armBeans [i].setY (finalVector.y - 8);
					}
					
					
					/** failed approach, how sad
					//Compute the orthographic projection of this bubble onto the punch vector
					Vector2D armFromShoulder = new Vector2D (shoulder, arm [i]);
					double projLength = shoulderToHand.getDot (armFromShoulder) / shoulderToHand.getDot (shoulderToHand);
					Vector2D projection = shoulderToHand.getScaled (projLength);
					Vector2D offset = new Vector2D (projection, armFromShoulder);
					System.out.println (i + ": " + offset);
					
					//Move the bubbles along the punch vector
					Vector2D bubbleVector = punchVector.getScaled ((double)(i + 1) / 5);
					bubbleVector = bubbleVector.getSum (offset);
					bubbleVector = toWorldCoords (bubbleVector);
					bubbleVector = bubbleVector.getSum (shoulder);
					armBeans [i].setX (bubbleVector.x - 8);
					armBeans [i].setY (bubbleVector.y - 8);
					**/
				}
			}
			
			//Update arm 1 punch timer
			if (arm1Punching) {
				if (!arm1Retracting) {
					punchTime1++;
				} else {
					punchTime1--;
				}
				if (punchTime1 >= PUNCH_TIME) {
					arm1Retracting = true;
				}
				if (punchTime1 == 0) {
					arm1Punching = false;
					arm1Retracting = false;
					setHandRotationLock (1, false);
					resetArm (1);
				}
			}
			
			//Update arm 2 punch timer
			if (arm2Punching) {
				if (!arm2Retracting) {
					punchTime2++;
				} else {
					punchTime2--;
				}
				if (punchTime2 >= PUNCH_TIME) {
					arm2Retracting = true;
				}
				if (punchTime2 == 0) {
					arm2Punching = false;
					arm2Retracting = false;
					setHandRotationLock (2, false);
					resetArm (2);
				}
			}
		}
	}
	
	public void resetArm (int arm) {
		for (int i = 0; i < 5; i++) {
			if (arm == 1 && arm1 [i] != null) {
				double arm1X = getX () + bubbleCenter.x + arm1Offsets [i].x - 8;
				double arm1Y = getY () + bubbleCenter.y + arm1Offsets [i].y - 8;
				arm1 [i].setX (arm1X);
				arm1 [i].setY (arm1Y);
			}
			if (arm == 2 && arm2 [i] != null) {
				double arm2X = getX () + bubbleCenter.x + arm2Offsets [i].x - 8;
				double arm2Y = getY () + bubbleCenter.y + arm2Offsets [i].y - 8;
				arm2 [i].setX (arm2X);
				arm2 [i].setY (arm2Y);
			}
		}
	}
	
	public void setHandRotationLock (int arm, boolean lock) {
		if (arm == 1) {
			if (arm1 [4] != null) {
				arm1 [4].setRotationLocked (lock);
			}
		}
		if (arm == 2) {
			if (arm2 [4] != null) {
				arm2 [4].setRotationLocked (lock);
			}
		}
	}
	
	public void turn (int dir) {
		if (direction != dir) {
			
			//Setup new direction stuff
			direction = dir;
			turnTimer = TURN_TIME;
			
			//Update offset arrays accordingly
			if (dir == 0) {
				arm1OffsetsPrevious = arm1OffsetsRight;
				arm2OffsetsPrevious = arm2OffsetsRight;
				arm1Offsets = arm1OffsetsLeft;
				arm2Offsets = arm2OffsetsLeft;
			} else {
				arm1OffsetsPrevious = arm1OffsetsLeft;
				arm2OffsetsPrevious = arm2OffsetsLeft;
				arm1Offsets = arm1OffsetsRight;
				arm2Offsets = arm2OffsetsRight;
			}
		}
	}
	
	public void punch (int arm) {
		Point targetPoint = new Point ((int)Jeffrey.getActiveJeffrey ().getX (), (int)Jeffrey.getActiveJeffrey ().getY () + 13);
		if (arm == 1) {
			arm1Punching = true;
			punchTime1 = 0;
			punchTarget1 = targetPoint;
			setHandRotationLock (1, true);
		} else if (arm == 2) {
			arm2Punching = true;
			punchTime2 = 0;
			punchTarget2 = targetPoint;
			setHandRotationLock (2, true);
		}
	}
	
	public boolean isPunching () {
		return arm1Punching || arm2Punching;
	}
	
	public void removeBean (Bean b) {
		
		//Find the bean and free it
		int arm = 0;
		int index = -1;
		for (int i = 0; i < 5; i++) {
			if (arm1 [i] == b) {
				arm = 1;
				arm1Length = i;
			}
			if (arm == 1 && arm1 [i] != null) {
				arm1 [i].setFree ();
				arm1 [i] = null;
			}
			if (arm2 [i] == b) {
				arm = 2;
				arm2Length = i;
			}
			if (arm == 2 && arm2 [i] != null) {
				arm2 [i].setFree ();
				arm2 [i] = null;
			}
		}
	}
	
	/**
	 * Transforms a vector v from offset coords (from center of big bubble) to world coords
	 * @param v the vector to transform
	 * @return the epic transformed vector
	 */
	public Vector2D toWorldCoords (Vector2D v) {
		double vecX = getX () + bubbleCenter.x + v.x;
		double vecY = getY () + bubbleCenter.y + v.y;
		return new Vector2D (vecX, vecY);
	}
	
	/**
	 * Transforms a vector v from world coords to offset coords (from center of big bubble)
	 * @param v the vector to transform
	 * @return the epic transformed vector
	 */
	public Vector2D toOffsetCoords (Vector2D v) {
		double vecX = v.x + bubbleCenter.x - getX ();
		double vecY = v.y + bubbleCenter.y - getY ();
		return new Vector2D (vecX, vecY);
	}
	
	@Override
	public void draw () {
		int drawX = (int)(getX () - Room.getViewX ());
		int drawY = (int)(getY () - Room.getViewY ());
		setX (getX () + headOffset.x);
		setY (getY () + headOffset.y);
		super.draw ();
		setX (getX () - headOffset.x);
		setY (getY () - headOffset.y);
		beastBubble.draw (drawX + (int)bubbleOffset.x, drawY + (int)bubbleOffset.y);
	}
	
}
