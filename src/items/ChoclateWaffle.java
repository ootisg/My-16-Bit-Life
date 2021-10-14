package items;

import gui.Gui;
import main.GameCode;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class ChoclateWaffle extends Item{
	private static final Sprite WAFFLE = new Sprite ("resources/sprites/Chocolate_Waffle.png");
	public ChoclateWaffle () {
		this.setSprite(WAFFLE); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(Player toHeal) {
		if (toHeal.getClass().getName() == "Jeffrey") {
			toHeal.health = toHeal.health + 60;
		} else {
			toHeal.health = toHeal.health + 40;
		}
		
		toHeal.getStatus().applyStatus("Fast", 600);
		
		Gui.getGui().menu.frozen = false;
		this.forget();
	}	
	@Override
	public String checkEnetry() {
		return "HOW DOES THIS HEAL MORE THAN THE ORIGINAL? IT JUST HAS MORE SUGAR THIS GAME IS SENDING NEGATIVE MESSAGES TO CHILDREN 0/10";
	}
	public String checkName () {
		return "CHOCOLATE CHIP WAFFLE";
	}
	@Override 
	public String getItemType() {
		return "Consumable";
	}
}
