package items;

import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import gui.Gui;
import gui.Tbox;
import resources.Sprite;

public class LemonPacket extends Item {
	Sprite lemonPacket = new Sprite ("resources/sprites/Lemon_Packet.png");
	public LemonPacket () {
		this.setSprite(lemonPacket); 
		this.setHitboxAttributes(0, 0, 24, 20);
	}
	@Override
	public void useItem(Player user) {
	
	user.status.applyStatus("Lemony", 4500);
	user.status.applyStatus("Powerful", 6000);
	user.status.applyStatus("Fast", 6000);
		
	Gui.getGui().menu.frozen = false;
	
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
