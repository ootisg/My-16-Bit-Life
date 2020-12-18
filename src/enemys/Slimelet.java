package enemys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import extensions.AnchoredSprite;
import gameObjects.TileCollider;
import main.GameAPI;
import resources.Sprite;
import vector.Vector2D;

public class Slimelet extends Enemy {

	public static final String CONFIG_PATH = "resources/sprites/config/Slimelet/";
	public static final String MASK_PATH = "resources/sprites/sprte_masks/";
	public static final String SPRITE_PATH = "resources/sprites/";
	
	public static HashMap<String, SlimeletSpriteBoosterPack> lootBundles;
	
	//DO NOT FUCK WITH THESE
	public static final int DIRECTION_UP = 0x0;
	public static final int DIRECTION_RIGHT = 0x1;
	public static final int DIRECTION_DOWN = 0x2;
	public static final int DIRECTION_LEFT = 0x3;
	
	//Offset vectors for the directions, in tiles
	public static final Vector2D[] OFFSET_VECTORS = new Vector2D[] {
			new Vector2D (0, -1),
			new Vector2D (1, 0),
			new Vector2D (0, 1),
			new Vector2D (-1, 0)
	};
	
	private String slimeletType;
	private boolean isClockwise;
	private int floorDirection;
	private SlimeletState animState = SlimeletState.CRAWL;
	
	private double speed = .25;
	
	public enum SlimeletState {
		CRAWL,
		INNER_CORNER,
		OUTER_CORNER
	}
	
	public Slimelet () {

		
	}

	@Override
	public void init () {
		
		//Init the loot bundles
		lootBundles = new HashMap<String, SlimeletSpriteBoosterPack> ();
		
		//Add the booster packs
		lootBundles.put ("nv", new SlimeletSpriteBoosterPack ("nv"));
		
	}
	
	@Override
	public void onDeclare () {
		
		//Do initial direction and stuff
		String directionRaw = this.getVariantAttribute ("direction");
		String flippedRaw = this.getVariantAttribute ("flip");
		//Assign default values
		if (directionRaw == null) {
			directionRaw = "right";
		}
		if (flippedRaw == null) {
			flippedRaw = "false";
		}
		isClockwise = true;
		if (flippedRaw.equals ("true")) {
			isClockwise = false;
		}
		//I don't like switch statements but oh well
		switch (directionRaw) {
			case "left":
				floorDirection = DIRECTION_UP;
				break;
			case "right":
				floorDirection = DIRECTION_DOWN;
				break;
			case "up":
				floorDirection = DIRECTION_RIGHT;
				break;
			case "down":
				floorDirection = DIRECTION_LEFT;
				break;
		}
		/*if (!isClockwise) {
			floorDirection = (floorDirection + 2) % 4;
		}*/
		
		//Set the sprite accordingly
		setSprite (getSlimeletSprite ("nv", SlimeletState.CRAWL, floorDirection, isClockwise));
		getAnimationHandler ().setFrameTime (0);
		
	}
	
	@Override
	public void frameEvent () {
		if (animState.equals (SlimeletState.CRAWL)) {
			
			//Get our handy under tile
			int facingDir = getFacingDirection (floorDirection, isClockwise);
			Vector2D dirVector = getDirectionVector (facingDir);
			Vector2D normalVector = getDirectionVector (floorDirection);
			Vector2D underTile = getTile ().getSum (normalVector);
			
			//Move and shit like that idunno man
			Vector2D moveVector = dirVector.getScaled (speed);
			setX (getX () + moveVector.x);
			setY (getY () + moveVector.y);
			
			//Check for turning corners, etc.
			Vector2D tile = getTile ();
			Vector2D aroundTile = tile.getSum (getDirectionVector (floorDirection));
			TileCollider e = new TileCollider ();
			if (e.isCollidingWithTile (tile)) {
				setX (getXPrevious ());
				setY (getYPrevious ());
				AnchoredSprite ss = getCurrentSlimeletSprite ();
				Point pt = ss.getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "front");
				this.setX(pt.getX() + normalVector.x);
				this.setY(pt.getY() + normalVector.y);
				animState = SlimeletState.INNER_CORNER;
				updateSprite ();
			} else if (!e.isCollidingWithTile (aroundTile) && e.isCollidingWithTile (underTile)) {
				setX (getXPrevious ());
				setY (getYPrevious ());
				AnchoredSprite ss = getCurrentSlimeletSprite ();
				Point pt = ss.getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "front");
				this.setX(pt.getX() + normalVector.x);
				this.setY(pt.getY() + normalVector.y);
				animState = SlimeletState.OUTER_CORNER;
				updateSprite ();
			}
		} else {
			if (this.getAnimationHandler().isAnimationDone()) {
				AnchoredSprite sss = getCurrentSlimeletSprite ();
				Point pts = sss.getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "front");
				//this is dumb v
				if (this.animState.equals(SlimeletState.OUTER_CORNER)) {
					floorDirection = this.rotate90Deg(floorDirection, isClockwise);
				} else {
					floorDirection = this.rotate90Deg(floorDirection, !isClockwise);	
				}
				//its that one ^
				animState = SlimeletState.CRAWL;
				getAnimationHandler().setAnimationFrame(0);
				Point pt = getCurrentSlimeletSprite ().getOffsetFromAnchor (getAnimationHandler ().getFrame (), "front");
				this.setX(pts.getX() - pt.getX ());
				this.setY(pts.getY() - pt.getY ());	
				updateSprite();
			}
		}
		
		
	}
	
	@Override
	public void draw () {
		
		super.draw ();
		Vector2D tile = getTile ();
		Graphics g = GameAPI.window.getBufferGraphics ();
		g.setColor(new Color(0x0000FF));
		g.drawRect((int)tile.x * 16, (int)tile.y * 16, 16, 16);
		
	}
	
	public AnchoredSprite getSlimeletSprite (String type, SlimeletState state, int floorDirection, boolean isClockwise) {
		
		//Calculate the index
		int isCWInt = 0x0;
		if (isClockwise) {
			isCWInt = 0x4;
		}
		int index = isCWInt | floorDirection;
		
		//Get the correct booster pack
		SlimeletSpriteBoosterPack usedBundle = lootBundles.get (type);
		
		//Return the correct sprite from the correct array
		switch (state) {
			case CRAWL:
				return usedBundle.slimeletCrawl [index];
			case INNER_CORNER:
				return usedBundle.slimeletInnerCorner [index];
			case OUTER_CORNER:
				return usedBundle.slimeletOuterCorner [index];
			default:
				return null;
		}
	}
	
	public AnchoredSprite getCurrentSlimeletSprite () {
		return getSlimeletSprite ("nv", animState, floorDirection, isClockwise);
	}
	
	public void updateSprite () {
		switch (animState) {
			case CRAWL:
				setSprite (getCurrentSlimeletSprite ());
				getAnimationHandler ().setFrameTime (250);
				getAnimationHandler ().setRepeat (true);
				break;
			case INNER_CORNER:
				setSprite (getCurrentSlimeletSprite ());
				getAnimationHandler ().setFrameTime (100);
				getAnimationHandler ().setRepeat (false);
				break;
			case OUTER_CORNER:
				setSprite (getCurrentSlimeletSprite ());
				getAnimationHandler ().setFrameTime (100);
				getAnimationHandler ().setRepeat (false);
				break;
		}
	}
	
	public int rotate90Deg (int direction, boolean clockwise) {
		if (!clockwise) {
			return Math.floorMod (direction - 1, 4);
		} else {
			return (direction + 1) % 4;
		}
	}
	
	public int getFacingDirection (int floorDir, boolean clockwise) {
		return rotate90Deg (floorDir, !clockwise);
	}
	public int getFloorDirection (int faceDir, boolean clockwise) {
		return rotate90Deg (faceDir, clockwise);
	}
	public Vector2D getDirectionVector (int direction) {
		return OFFSET_VECTORS [direction];
	}
	
	public Vector2D getTile () {
		AnchoredSprite ss = getCurrentSlimeletSprite ();
		Point pt = ss.getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "front");
		return new Vector2D (pt.x / 16, pt.y / 16);
	}
	
	private class SlimeletSpriteBoosterPack {
		
		public AnchoredSprite[] slimeletCrawl;
		public AnchoredSprite[] slimeletInnerCorner;
		public AnchoredSprite[] slimeletOuterCorner;
		
		public SlimeletSpriteBoosterPack (String type) {
			
			//Brofist my guy
			HashMap <Color,String> working = new HashMap <Color,String> ();
			working.put(new Color (0xFF0000), "front");
			working.put(new Color (0x0000FF), "anchor");
			working.put(new Color (0xFF00FF), "tail");
			AnchoredSprite aroundBLCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_around_" + type + ".png", CONFIG_PATH + "around_BL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BL_ccw.txt"),working);
			AnchoredSprite aroundBLCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_over_" + type + ".png",CONFIG_PATH + "around_BL_cw.txt"),new Sprite (MASK_PATH + "slimelet_over_mask.png", CONFIG_PATH + "around_BL_cw.txt"),working);
			AnchoredSprite aroundBRCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_around_" + type + ".png",CONFIG_PATH + "around_BR_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BR_cw.txt"),working);
			AnchoredSprite aroundBRCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_over_" + type + ".png",CONFIG_PATH + "around_BR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_over_mask.png", CONFIG_PATH + "around_BR_ccw.txt"),working);
			AnchoredSprite aroundTLCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_over_" + type + ".png",CONFIG_PATH + "around_TL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_over_mask.png", CONFIG_PATH + "around_TL_ccw.txt"),working);
			AnchoredSprite aroundTLCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_around_" + type + ".png",CONFIG_PATH + "around_TL_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TL_cw.txt"),working);
			AnchoredSprite aroundTRCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_over_" + type + ".png",CONFIG_PATH + "around_TR_cw.txt"),new Sprite (MASK_PATH + "slimelet_over_mask.png", CONFIG_PATH + "around_TR_cw.txt"),working);
			AnchoredSprite aroundTRCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_around_" + type + ".png",CONFIG_PATH + "around_TR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TR_ccw.txt"),working);
			AnchoredSprite climbBLCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_horizontal_" + type + ".png",CONFIG_PATH + "climb_BL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_BL_ccw.txt"),working);
			AnchoredSprite climbBLCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_vertical_" + type + ".png",CONFIG_PATH + "climb_BL_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_BL_cw.txt"),working);
			AnchoredSprite climbBRCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_horizontal_" + type + ".png",CONFIG_PATH + "climb_BR_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_BR_cw.txt"),working);
			AnchoredSprite climbBRCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_vertical_" + type + ".png",CONFIG_PATH + "climb_BR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_BR_ccw.txt"),working);
			AnchoredSprite climbTLCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_vertical_" + type + ".png",CONFIG_PATH + "climb_TL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_TL_ccw.txt"),working);
			AnchoredSprite climbTLCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_horizontal_" + type + ".png",CONFIG_PATH + "climb_TL_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_TL_cw.txt"),working);
			AnchoredSprite climbTRCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_vertical_" + type + ".png",CONFIG_PATH + "climb_TR_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_TR_cw.txt"),working);
			AnchoredSprite climbTRCCW =  new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_climb_horizontal_" + type + ".png",CONFIG_PATH + "climb_TR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_TR_ccw.txt"),working);
			AnchoredSprite crawlLeftCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_horizontal_" + type + ".png",CONFIG_PATH + "left_cw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "left_cw.txt"), working);
			AnchoredSprite crawlLeftCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_horizontal_" + type + ".png",CONFIG_PATH + "left_ccw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "left_ccw.txt"), working);
			AnchoredSprite crawlRightCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_horizontal_" + type + ".png",CONFIG_PATH + "right_cw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "right_cw.txt"), working);
			AnchoredSprite crawlRightCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_horizontal_" + type + ".png",CONFIG_PATH + "right_ccw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "right_ccw.txt"), working);
			AnchoredSprite crawlDownCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "down_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_cw.txt"), working);
			AnchoredSprite crawlDownCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "down_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_ccw.txt"), working);
			AnchoredSprite crawlUpCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "up_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_cw.txt"), working);
			AnchoredSprite crawlUpCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "up_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_ccw.txt"), working);
			slimeletCrawl = new AnchoredSprite[] {crawlRightCCW, crawlDownCCW, crawlLeftCCW, crawlUpCCW, crawlLeftCW, crawlUpCW, crawlRightCW, crawlDownCW};
			slimeletInnerCorner = new AnchoredSprite[] {climbTRCCW, climbBRCCW, climbBLCCW, climbTLCCW, climbTLCW, climbTRCW, climbBRCW, climbBLCW};
			slimeletOuterCorner = new AnchoredSprite[] {aroundBRCCW, aroundBLCCW, aroundTLCCW, aroundTRCCW, aroundBLCW, aroundTLCW, aroundTRCW, aroundBRCW};
		}
		
	}
}
