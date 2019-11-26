package items;

import main.GameCode;
import map.Room;
import gui.Tbox;
import resources.Sprite;

public class FairUseKey extends Item {
	int amountToAdd;
	Sprite FUKey = new Sprite("resources/sprites/key.png");
	public FairUseKey () {
		this.setSprite(FUKey); 
		amountToAdd = Integer.parseInt(this.getVariantAttribute("amountToAdd"));
		this.setHitboxAttributes(0, 0, 9, 5);
	}
	public FairUseKey (int ItemAmount) {
		this.setSprite(FUKey); 
		amountToAdd = ItemAmount;
		this.setHitboxAttributes(0, 0, 9, 5);
	}
	@Override
	public String checkName () {
		return "FAIR USE KEY";
	}
	@Override 
	public String getItemType() {
		return "Key";
	}
	@Override
	public String checkEnetry() {
		return "THIS IS A KEY THAT ALLOWS YOU TO ENTER COPYWRIGHTED AREAS ... SOMETIMES IT DOESEN'T WORK BUT HEY ITS SOMETHING";
	}
	@Override
	public void frameEvent () {
		this.setY(this.getY() + 1);
		if (!(Room.isColliding(this.hitbox()))) {
			this.setY(this.getY() + 3);
		} else {
		this.setY(this.getY() - 1);
		}
		if (this.isColliding(GameCode.testJeffrey)) {
		Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT A FAIR USE KEY", true);
		GameCode.testJeffrey.inventory.addKeyItem(this);
		this.forget();
		}
	}
}
