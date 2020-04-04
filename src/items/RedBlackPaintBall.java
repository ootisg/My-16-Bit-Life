package items;

import main.GameCode;
import gui.Tbox;
import map.Room;
import resources.Sprite;

public class RedBlackPaintBall extends Item{
	int amountToAdd;
	Sprite paintball;
	public RedBlackPaintBall () {
		paintball = new Sprite ("resources/sprites/redblack_ball.png");
		this.setSprite(paintball); 
		amountToAdd = Integer.parseInt(this.getVariantAttribute ("AmountDroped"));
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	public RedBlackPaintBall (int amountOfBalls) {
		paintball = new Sprite ("resources/sprites/redblack_ball.png");
		this.setSprite(paintball); 
		amountToAdd = amountOfBalls;
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
	@Override 
	public void frameEvent() {
		
		this.setY(this.getY() + 1);
		if (!(Room.isColliding(this.hitbox()))) {
			this.setY(this.getY() + 3);
		} else {
		this.setY(this.getY() - 1);
		}
		if (this.isColliding(GameCode.testJeffrey)) {
		Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT " + Integer.toString(amountToAdd) + " PAINTBALLS", true);
		while (amountToAdd != 0) {
		GameCode.testJeffrey.inventory.addAmmo(this);
		amountToAdd = amountToAdd - 1;
		}
		this.forget();
		}
	}
}
