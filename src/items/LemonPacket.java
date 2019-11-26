package items;

import main.GameCode;
import map.Room;
import gui.Tbox;
import resources.Sprite;

public class LemonPacket extends Item {
	int amountToAdd;
	Sprite lemonPacket = new Sprite ("resources/sprites/Lemon_Packet.png");
public LemonPacket () {
	this.setSprite(lemonPacket); 
	try {
	amountToAdd = Integer.parseInt(this.getVariantAttribute("Amount"));
	}catch (NumberFormatException e) {
	amountToAdd = 1;
	}
}
public LemonPacket (int amount) {
	this.setSprite(lemonPacket); 
	amountToAdd = amount;
	this.setHitboxAttributes(0, 0, 24, 20);
}
@Override
public String checkEnetry() {
	return "WARNING HIGHLY ADDICTIVE SUBSTANCE AVOID CONSUMING WITHOUT LIQUID ACCOMPNYMENT";
}
public String checkName () {
	return "LEMON PACKET";
}
@Override 
public String getItemType() {
	return "Consumable";
}
@Override 
public void frameEvent () {
	this.setY(this.getY() + 1);
	if (!(Room.isColliding(this.hitbox()))) {
		this.setY(this.getY() + 3);
	}
	this.setY(this.getY() - 1);
	if (this.isColliding(GameCode.testJeffrey)) {
	if (amountToAdd != 1) {
		Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 2, "YOU GOT " + Integer.toString(amountToAdd) + " LEMON PACKETS THEY LOOK VERY TANTALIZING", true);
		while (amountToAdd != 0) {
			GameCode.testJeffrey.inventory.addConsumable(this);
			amountToAdd = amountToAdd - 1;
			}
		this.forget();
		} else {
			Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT A LEMON PACKET", true);
			while (amountToAdd != 0) {
				GameCode.testJeffrey.inventory.addConsumable(this);
				amountToAdd = amountToAdd - 1;
				}
			this.forget();
		}
	}
}
}
