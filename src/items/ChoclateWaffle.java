package items;

import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Fastness;

public class ChoclateWaffle extends Item{
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	private static final Sprite WAFFLE = new Sprite ("resources/sprites/Chocolate_Waffle.png");
	public ChoclateWaffle () {
		this.setSprite(WAFFLE); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(int witchCharictar) {
		if (witchCharictar == 0) {
			j.jeffreyHealth = j.jeffreyHealth + 70;
			if (j.jeffreyHealth >= j.maxJeffreyHealth ) {
				j.jeffreyHealth = j.maxJeffreyHealth;
			}
		}
		if (witchCharictar == 1) {
			j.samHealth = j.samHealth + 50;
			if (j.samHealth >= j.maxSamHealth ) {
				j.samHealth = j.maxSamHealth;
			}
		}
		if (witchCharictar == 2) {
			j.ryanHealth = j.maxRyanHealth + 50;
			if (j.ryanHealth >= j.maxRyanHealth ) {
				j.ryanHealth = j.maxRyanHealth;
			}
		}
		Fastness fast;
		fast = new Fastness (witchCharictar);
		fast.declare();
		GameCode.gui.menu.frozen = false;
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
