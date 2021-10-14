package items;


import gui.Gui;
import main.GameCode;
import main.ObjectHandler;
import players.Player;
import resources.Sprite;

public class BlueberryWaffle extends Item{
	Sprite waffle = new Sprite ("resources/sprites/Blueberry_Waffle.png");
public BlueberryWaffle () {
	this.setSprite(waffle); 
	this.setHitboxAttributes(0, 0, 14, 20);
}
@Override
public void useItem(Player toHeal) {
	if (toHeal.getClass().getName() == "Jeffrey") {
		toHeal.health = toHeal.health + 60;
	} else {
		toHeal.health = toHeal.health + 40;
	}
	
	toHeal.getStatus().applyStatus("Regeneration1", 600);

	Gui.getGui().menu.frozen = false;
	this.forget();
}	
@Override
public String checkEnetry() {
	return "A WAFFLE THAT HEALS SOME AND A BIT MORE OVER TIME BECAUSE I GOTTA SHOW OFF THE LAME REGEN STATUS I PROGRAMMED IN";
}
public String checkName () {
	return "BLUEBERRY WAFFLE";
}
@Override 
public String getItemType() {
	return "Consumable";
}
}
