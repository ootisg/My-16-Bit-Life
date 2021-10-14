package gameObjects;

import main.GameObject;
import main.ObjectHandler;
import players.Player;
import resources.LoopableSprite;
import resources.Sprite;

public class BubbleWater extends GameObject {
	int endX = 0;
	int endY = 0;
	int waterLevel = 0;
	boolean inzialized = false;
	public BubbleWater () {
		this.adjustHitboxBorders();
		this.setRenderPriority(1);
		this.setGameLogicPriority(-1);
	}
	private void generateSprite () {
		LoopableSprite water = new LoopableSprite (new Sprite ("resources/sprites/Bubble_Water_Bottom.png"),0,16,-1,endY);
		water.addStartSprite(new Sprite ("resources/sprites/config/bubbleWater.txt"));
		LoopableSprite fullWater = new LoopableSprite (water, 16, 0, endX, -1);
		this.setSprite(fullWater);
		this.desyncSpriteX(-16);
	}
	@Override
	public void frameEvent () {
		if (!inzialized) {
			int uppyThing = 0;
			while (uppyThing < ObjectHandler.getObjectsByName("HitboxRightBottomBound").size()) {
			if(ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getVariantAttribute("Partner").equals(this.getVariantAttribute("Partner"))){
				endX = (int) ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getX();
				endY = (int) ObjectHandler.getObjectsByName("HitboxRightBottomBound").get(uppyThing).getY();
				break;
			}
			uppyThing = uppyThing + 1;	
		}
		this.setHitboxAttributes(0, 0, (int)(endX - this.getX()) + 16, (int)(endY - this.getY()) + 16);
		waterLevel = (int)this.getY();
		this.generateSprite();
		inzialized = true;
		}
		if (this.getY() != waterLevel) {
			if (this.getY() > waterLevel) {
				this.setY(this.getY() - 1);
				if (this.getY() < waterLevel) {
					this.setY(waterLevel);
				}
			} else {
				if (this.getY() < waterLevel) {
					this.setY(this.getY() + 1);
					if (this.getY() > waterLevel) {
						this.setY(waterLevel);
				}
			}
		}
		this.setHitboxAttributes(0, 0, (int)(endX - this.getX()) + 16, (int)(endY - this.getY()) +16);
		this.generateSprite();
	}
	
}
	
	public void setWaterLevel(int newWaterLevel) {
		waterLevel = newWaterLevel;
	}
}
