package items;

import gui.Gui;
import gui.Tbox;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Player;
import resources.Sprite;

public class Waffle extends Item{
	Sprite waffle = new Sprite ("resources/sprites/Waffle.png");
	public Waffle () {
		this.setSprite(waffle); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(Player toUse) {
		if (toUse.getPlayerNum() == 0) {
			toUse.heal(60);
		} else {
			toUse.heal(40);
		}
		Gui.getGui().menu.frozen = false;
		this.forget();
	}	
	@Override
	public String checkEnetry() {
		return "JEFFREYS FAVORATE FOOD - IT IS TASTY AND PROBABLY HEALTHY BECAUSE IT WOULDENT MAKE YOU RECOVER HP IF IT WASENT";
	}
	public String checkName () {
		return "WAFFLE";
	}
	@Override 
	public String getItemType() {
		return "Consumable";
	}
}
