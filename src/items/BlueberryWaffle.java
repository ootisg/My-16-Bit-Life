package items;


import gui.Gui;
import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Regeneration;

public class BlueberryWaffle extends Item{
	Sprite waffle = new Sprite ("resources/sprites/Blueberry_Waffle.png");
public BlueberryWaffle () {
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
	Regeneration regeneration;
	regeneration = new Regeneration (witchCharictar);
	regeneration.declare();
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
