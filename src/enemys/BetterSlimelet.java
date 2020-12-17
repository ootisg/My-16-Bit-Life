package enemys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import extensions.AnchoredSprite;
import main.GameAPI;
import resources.Sprite;

public class BetterSlimelet extends Enemy {

	public static final String CONFIG_PATH = "resources/sprites/config/Slimelet/";
	public static final String MASK_PATH = "resources/sprites/sprte_masks/";
	
	//Bitmap: (1=CW, 0=CCW), (00=LEFT,01=RIGHT,10=UP,11=DOWN)
	public static AnchoredSprite[] slimeletCrawl;
	public static AnchoredSprite[] slimeletInnerCorner;
	public static AnchoredSprite[] slimeletOuterCorner;
	
	public static final int DIRECTION_LEFT = 0x0;
	public static final int DIRECTION_RIGHT = 0x1;
	public static final int DIRECTION_UP = 0x2;
	public static final int DIRECTION_DOWN = 0x3;
	
	public enum SlimeletState {
		CRAWL,
		INNER_CORNER,
		OUTER_CORNER
	}
	
	public BetterSlimelet () {

		
	}

	@Override
	public void init () {
		
		//Brofist my guy
		HashMap <Color,String> working = new HashMap <Color,String> ();
		working.put(new Color (0xFF0000), "front");
		working.put(new Color (0x0000FF), "anchor");
		working.put(new Color (0xFF00FF), "tail");
		AnchoredSprite aroundBLCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_BL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BL_ccw.txt"),working);
		AnchoredSprite aroundBLCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + " around_BL_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BL_cw.txt"),working);
		AnchoredSprite aroundBRCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_BR_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BR_cw.txt"),working);
		AnchoredSprite aroundBRCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_BR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_BR_ccw.txt"),working);
		AnchoredSprite aroundTLCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_TL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TL_ccw.txt"),working);
		AnchoredSprite aroundTLCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_TL_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TL_cw.txt"),working);
		AnchoredSprite aroundTRCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_TR_cw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TR_cw.txt"),working);
		AnchoredSprite aroundTRCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "around_TR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_around_mask.png", CONFIG_PATH + "around_TR_ccw.txt"),working);
		AnchoredSprite climbBLCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_BL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_BL_ccw.txt"),working);
		AnchoredSprite climbBLCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_BL_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_BL_cw.txt"),working);
		AnchoredSprite climbBRCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_BR_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_BR_cw.txt"),working);
		AnchoredSprite climbBRCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_BR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_BR_ccw.txt"),working);
		AnchoredSprite climbTLCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_TL_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_TL_ccw.txt"),working);
		AnchoredSprite climbTLCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_TL_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_TL_cw.txt"),working);
		AnchoredSprite climbTRCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_TR_cw.txt"),new Sprite (MASK_PATH + "slimelet_climb_vertical_mask.png", CONFIG_PATH + "climb_TR_cw.txt"),working);
		AnchoredSprite climbTRCCW =  new AnchoredSprite (new Sprite (CONFIG_PATH + "climb_TR_ccw.txt"),new Sprite (MASK_PATH + "slimelet_climb_up_mask.png", CONFIG_PATH + "climb_TR_ccw.txt"),working);
		AnchoredSprite crawlLeftCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "left_cw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "left_cw.txt"), working);
		AnchoredSprite crawlLeftCCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "left_ccw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "left_ccw.txt"), working);
		AnchoredSprite crawlRightCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "right_cw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "right_cw.txt"), working);
		AnchoredSprite crawlRightCCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "right_ccw.txt"),new Sprite (MASK_PATH + "slimelet_horizontal_mask.png", CONFIG_PATH + "right_ccw.txt"), working);
		AnchoredSprite crawlDownCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "down_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_cw.txt"), working);
		AnchoredSprite crawlDownCCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "down_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "down_ccw.txt"), working);
		AnchoredSprite crawlUpCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "up_cw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_cw.txt"), working);
		AnchoredSprite crawlUpCCW = new AnchoredSprite (new Sprite (CONFIG_PATH + "up_ccw.txt"),new Sprite (MASK_PATH + "slimelet_vertical_mask.png", CONFIG_PATH + "up_ccw.txt"), working);
		slimeletCrawl = new AnchoredSprite[] {crawlLeftCCW, crawlRightCCW, crawlUpCCW, crawlDownCCW, crawlLeftCW, crawlRightCW, crawlUpCW, crawlDownCW};
		slimeletInnerCorner = new AnchoredSprite[] {climbBLCCW, climbTRCCW, climbTLCCW, climbBRCCW, climbTLCW, climbBRCW, climbTRCW, climbBLCW};
		slimeletOuterCorner = new AnchoredSprite[] {aroundBLCCW, aroundTRCCW, aroundTLCCW, aroundBRCCW, aroundTLCW, aroundBRCW, aroundTRCW, aroundBLCW};
	}
	
	@Override
	public void onDeclare () {
		setSprite (getSlimeletSprite (SlimeletState.CRAWL, DIRECTION_RIGHT, true));
		getAnimationHandler ().setFrameTime (250);
	}
	
	@Override
	public void draw () {
		super.draw ();
		Point pt = (((AnchoredSprite)getSprite ()).getRelativeToAnchor ((int)getX (), (int)getY (), getAnimationHandler ().getFrame (), "tail"));
		Graphics g = GameAPI.window.getBufferGraphics ();
		g.setColor(new Color (0xFF00FF));
		g.fillRect(pt.x, pt.y, 1, 1);
	}
	
	public AnchoredSprite getSlimeletSprite (SlimeletState state, int direction, boolean isClockwise) {
		
		//Calculate the index
		int isCWInt = 0x0;
		if (isClockwise) {
			isCWInt = 0x4;
		}
		int index = isCWInt | direction;
		
		//Return the correct sprite from the correct array
		switch (state) {
			case CRAWL:
				return slimeletCrawl [index];
			case INNER_CORNER:
				return slimeletInnerCorner [index];
			case OUTER_CORNER:
				return slimeletOuterCorner [index];
			default:
				return null;
		}
	}
	
}
