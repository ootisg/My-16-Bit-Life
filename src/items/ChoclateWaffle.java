package items;

import main.GameCode;
import main.ObjectHandler;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Fastness;

public class ChoclateWaffle extends Item{
	private static final Sprite WAFFLE = new Sprite ("resources/sprites/Chocolate_Waffle.png");
	public ChoclateWaffle () {
		this.setSprite(WAFFLE); 
		this.setHitboxAttributes(0, 0, 14, 20);
	}
	@Override
	public void useItem(int witchCharictar) {
		if (witchCharictar == 0) {
			Jeffrey.jeffreyHealth = Jeffrey.jeffreyHealth + 70;
			if (Jeffrey.jeffreyHealth >= Jeffrey.maxJeffreyHealth ) {
				Jeffrey.jeffreyHealth = Jeffrey.maxJeffreyHealth;
			}
		}
		if (witchCharictar == 1) {
			Jeffrey.samHealth = Jeffrey.samHealth + 50;
			if (Jeffrey.samHealth >= Jeffrey.maxSamHealth ) {
				Jeffrey.samHealth = Jeffrey.maxSamHealth;
			}
		}
		if (witchCharictar == 2) {
			Jeffrey.ryanHealth = Jeffrey.maxRyanHealth + 50;
			if (Jeffrey.ryanHealth >= Jeffrey.maxRyanHealth ) {
				Jeffrey.ryanHealth = Jeffrey.maxRyanHealth;
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
