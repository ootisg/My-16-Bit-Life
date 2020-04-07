package items;

import gui.Tbox;
import main.GameCode;
import map.Room;
import resources.Sprite;
import statusEffect.Fastness;
import statusEffect.Regeneration;

public class ChoclateWaffle extends Item{
	int amountToAdd;
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
		GameCode.testJeffrey.jeffreyHealth = GameCode.testJeffrey.jeffreyHealth + 70;
		if (GameCode.testJeffrey.jeffreyHealth >= GameCode.testJeffrey.maxJeffreyHealth ) {
			GameCode.testJeffrey.jeffreyHealth = GameCode.testJeffrey.maxJeffreyHealth;
		}
	}
	if (witchCharictar == 1) {
		GameCode.testJeffrey.samHealth = GameCode.testJeffrey.samHealth + 50;
		if (GameCode.testJeffrey.samHealth >= GameCode.testJeffrey.maxSamHealth ) {
			GameCode.testJeffrey.samHealth = GameCode.testJeffrey.maxSamHealth;
		}
	}
	if (witchCharictar == 2) {
		GameCode.testJeffrey.ryanHealth = GameCode.testJeffrey.maxRyanHealth + 50;
		if (GameCode.testJeffrey.ryanHealth >= GameCode.testJeffrey.maxRyanHealth ) {
			GameCode.testJeffrey.ryanHealth = GameCode.testJeffrey.maxRyanHealth;
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
		if (this.isColliding(GameCode.testJeffrey)) {
		if (amountToAdd != 1) {
			Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 2, "YOU GOT " + Integer.toString(amountToAdd) + " WAFFLES THEY LOOK REALLY FREAKIN TASTY", true);
			while (amountToAdd != 0) {
				GameCode.testJeffrey.inventory.addConsumable(this);
				amountToAdd = amountToAdd - 1;
				}
			this.forget();
			} else {
				Tbox box = new Tbox (GameCode.testJeffrey.getX(), GameCode.testJeffrey.getY() - 8, 28, 1, "YOU GOT A WAFFLE", true);
				while (amountToAdd != 0) {
					GameCode.testJeffrey.inventory.addConsumable(this);
					amountToAdd = amountToAdd - 1;
					}
				this.forget();
			}
		}
	}
}
