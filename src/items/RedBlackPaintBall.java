package items;

import resources.Sprite;

public class RedBlackPaintBall extends Item{
	Sprite paintball;
	public RedBlackPaintBall () {
		paintball = new Sprite ("resources/sprites/redblack_ball.png");
		this.setSprite(paintball); 
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "RED BLACK PAINTBALL";
	}
	@Override
	public String checkEnetry() {
		return "ENETRY HAS NOT BEEN DEDCIDED YET";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
}
