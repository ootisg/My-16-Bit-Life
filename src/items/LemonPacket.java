package items;

import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import gui.Tbox;
import resources.Sprite;
import statusEffect.Fastness;
import statusEffect.Lemoney;
import statusEffect.Power;

public class LemonPacket extends Item {
	int amountToAdd;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
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
public void useItem(int witchCharictar) {
Lemoney lemons;
lemons = new Lemoney(witchCharictar);
lemons.declare();
Power power;
power = new Power (witchCharictar);
power.declare();
Fastness fastness;
fastness = new Fastness(witchCharictar);
fastness.declare();
GameCode.gui.menu.frozen = false;
if (witchCharictar == 0) {
	Jeffrey.status.statusAppliedOnJeffrey[2] = true;
}
if (witchCharictar == 1) {
	Jeffrey.status.statusAppliedOnSam[2] = true;
}
if (witchCharictar == 2) {
	Jeffrey.status.statusAppliedOnRyan[2] = true;
}
this.forget();
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
	if (!(Room.isColliding(this))) {
		this.setY(this.getY() + 3);
	}
	this.setY(this.getY() - 1);
	if (this.isColliding(j)) {
	if (amountToAdd != 1) {
		Tbox box = new Tbox (j.getX(), j.getY() - 8, 28, 2, "YOU GOT " + Integer.toString(amountToAdd) + " LEMON PACKETS THEY LOOK VERY TANTALIZING", true);
		while (amountToAdd != 0) {
			Jeffrey.inventory.addConsumable(this);
			amountToAdd = amountToAdd - 1;
			}
		this.forget();
		} else {
			Tbox box = new Tbox (j.getX(), j.getY() - 8, 28, 1, "YOU GOT A LEMON PACKET", true);
			while (amountToAdd != 0) {
				Jeffrey.inventory.addConsumable(this);
				amountToAdd = amountToAdd - 1;
				}
			this.forget();
		}
	}
}
}
