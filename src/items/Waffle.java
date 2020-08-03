package items;

import gui.Tbox;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class Waffle extends Item{
	Sprite waffle = new Sprite ("resources/sprites/Waffle.png");
	public Waffle () {
		this.setSprite(waffle); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(int witchCharictar) {
		if (witchCharictar == 0) {
			Jeffrey.jeffreyHealth = Jeffrey.jeffreyHealth + 60;
			if (Jeffrey.jeffreyHealth >= Jeffrey.maxJeffreyHealth ) {
				Jeffrey.jeffreyHealth = Jeffrey.maxJeffreyHealth;
			}
		}
		if (witchCharictar == 1) {
			Jeffrey.samHealth = Jeffrey.samHealth + 40;
			if (Jeffrey.samHealth >= Jeffrey.maxSamHealth ) {
				Jeffrey.samHealth = Jeffrey.maxSamHealth;
			}
		}
		if (witchCharictar == 2) {
			Jeffrey.ryanHealth = Jeffrey.maxRyanHealth + 40;
			if (Jeffrey.ryanHealth >= Jeffrey.maxRyanHealth ) {
				Jeffrey.ryanHealth = Jeffrey.maxRyanHealth;
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
