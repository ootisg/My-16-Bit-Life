package items;

import gui.Tbox;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Waffle extends Item{
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	Sprite waffle = new Sprite ("resources/sprites/Waffle.png");
	public Waffle () {
		this.setSprite(waffle); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(int witchCharictar) {
		if (witchCharictar == 0) {
			j.jeffreyHealth = j.jeffreyHealth + 60;
			if (j.jeffreyHealth >= j.maxJeffreyHealth ) {
				j.jeffreyHealth = j.maxJeffreyHealth;
			}
		}
		if (witchCharictar == 1) {
			j.samHealth = j.samHealth + 40;
			if (j.samHealth >= j.maxSamHealth ) {
				j.samHealth = j.maxSamHealth;
			}
		}
		if (witchCharictar == 2) {
			j.ryanHealth = j.maxRyanHealth + 40;
			if (j.ryanHealth >= j.maxRyanHealth ) {
				j.ryanHealth = j.maxRyanHealth;
			}
		}
		GameCode.gui.menu.frozen = false;
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
