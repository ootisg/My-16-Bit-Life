package items;

import gui.Tbox;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;
import statusEffect.Fastness;
import statusEffect.Regeneration;

public class ChoclateWaffle extends Item{
	int amountToAdd;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	Sprite waffle = new Sprite ("resources/sprites/Chocolate_Waffle.png");
public ChoclateWaffle () {
	this.setSprite(waffle); 
	try {
	amountToAdd = Integer.parseInt(this.getVariantAttribute("Amount"));
	}catch (NumberFormatException e) {
	amountToAdd = 1;
	}
	this.setHitboxAttributes(0, 0, 14, 20);
}
public ChoclateWaffle (int amount) {
	this.setSprite(waffle); 
	amountToAdd = amount;
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
	@Override 
	public void frameEvent () {
		this.setY(this.getY() + 1);
		if (!(Room.isColliding(this))) {
			this.setY(this.getY() + 3);
		}
		this.setY(this.getY() - 1);
		if (this.isColliding(j)) {
		if (amountToAdd != 1) {
			Tbox box = new Tbox (j.getX(), j.getY() - 8, 28, 2, "YOU GOT " + Integer.toString(amountToAdd) + " WAFFLES THEY LOOK REALLY FREAKIN TASTY", true);
			while (amountToAdd != 0) {
				Jeffrey.inventory.addConsumable(this);
				amountToAdd = amountToAdd - 1;
				}
			this.forget();
			} else {
				Tbox box = new Tbox (j.getX(), j.getY() - 8, 28, 1, "YOU GOT A WAFFLE", true);
				while (amountToAdd != 0) {
					Jeffrey.inventory.addConsumable(this);
					amountToAdd = amountToAdd - 1;
					}
				this.forget();
			}
		}
	}
}
