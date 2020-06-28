package tileEntities;

import main.AnimationHandler;
import map.Room;
import map.TileEntitiy;
import resources.Sprite;

public class AnimatedBlock extends TileEntitiy {
	Sprite blockSprite;
	AnimationHandler handler;
	boolean inzialized = false;
	public AnimatedBlock  () {
	
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			if (this.getData().getAttribute("sprite") != null) {
				blockSprite = new Sprite (this.getData().getAttribute("sprite"));
				} else {
				blockSprite = new Sprite ("resources/sprites/missing.png");
				}
				if (this.getData().getAttribute("speed") == null) {
				handler = new AnimationHandler (blockSprite,100);
				} else {
				handler = new AnimationHandler (blockSprite,Integer.parseInt(this.getData().getAttribute("speed")));	
				}
				inzialized = true;
		}
		if (!this.getTexture().equals(blockSprite.getFrame(handler.getFrame()))) {
			this.setTexture(blockSprite.getFrame(handler.getFrame()));
			Room.getChungus(this.getX(), this.getY()).invalidate(this.getLayer());
		}
	}
	public void setSprite (Sprite newSprite) {
		blockSprite = newSprite;
		handler.setImage(blockSprite);
	}
}
