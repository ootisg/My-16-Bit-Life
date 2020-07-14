package items;


import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Regeneration;

public class BlueberryWaffle extends Item{
	Sprite waffle = new Sprite ("resources/sprites/Blueberry_Waffle.png");
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
public BlueberryWaffle () {
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
	Regeneration regeneration;
	regeneration = new Regeneration (witchCharictar);
	regeneration.declare();
	GameCode.gui.menu.frozen = false;
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
