package enemys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import extensions.AnchoredSprite;
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
		System.out.println (flippedRaw);
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
		if (!isClockwise) {
			floorDirection = (floorDirection + 2) % 4;
		}
		
		//Set the sprite accordingly
		setSprite (getSlimeletSprite ("nv", SlimeletState.CRAWL, floorDirection, isClockwise));
		getAnimationHandler ().setFrameTime (250);
		
	}
	
	@Override
	public void draw () {
		//System.out.println (getVariantAttribute ("direction"));
		super.draw ();
		Point pt = (((AnchoredSprite)getSprite ()).getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "tail"));
		Graphics g = GameAPI.window.getBufferGraphics ();
		g.setColor(new Color (0xFF00FF));
		g.fillRect(pt.x, pt.y, 1, 1);
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
	
	public Vector2D getDirectionVector (int direction) {
		return OFFSET_VECTORS [direction];
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
			AnchoredSprite crawlBottomCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "down_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_cw.txt"), working);
			AnchoredSprite crawlBottomCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "down_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_ccw.txt"), working);
			AnchoredSprite crawlTopCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "up_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_cw.txt"), working);
			AnchoredSprite crawlTopCCW = new AnchoredSprite (new Sprite (SPRITE_PATH + "slimelet_vertical_" + type + ".png",CONFIG_PATH + "up_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_ccw.txt"), working);
			slimeletCrawl = new AnchoredSprite[] {crawlLeftCCW, crawlTopCCW, crawlRightCCW, crawlBottomCCW, crawlLeftCW, crawlTopCW, crawlRightCW, crawlBottomCW};
			slimeletInnerCorner = new AnchoredSprite[] {climbBLCCW, climbTLCCW, climbTRCCW, climbBRCCW, climbTLCW, climbTRCW, climbBRCW, climbBLCW};
			slimeletOuterCorner = new AnchoredSprite[] {aroundTLCCW, aroundTRCCW, aroundBRCCW, aroundBLCCW, aroundBLCW, aroundTLCW, aroundTRCW, aroundBRCW};
		}
		
	}
}
