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
	Sprite lemonPacket = new Sprite ("resources/sprites/Lemon_Packet.png");
	public LemonPacket () {
		this.setSprite(lemonPacket); 
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
}
