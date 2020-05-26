package items;

import gui.Tbox;
import main.GameCode;
import main.ObjectHandler;
import map.Room;
import players.Jeffrey;
import resources.Sprite;

public class BluePaint extends Item{
	int amountToAdd;
	Sprite paintball;
	Jeffrey j = (Jeffrey) ObjectHandler.getObjectsByName("Jeffrey").get(0); 
	public BluePaint () {
		paintball = new Sprite ("resources/sprites/Blue_Paint.png");
		this.setSprite(paintball); 
		amountToAdd = Integer.parseInt(this.getVariantAttribute ("AmountDroped"));
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	public BluePaint (int amountOfBalls) {
		paintball = new Sprite ("resources/sprites/Blue_Paint.png");
		this.setSprite(paintball); 
		amountToAdd = amountOfBalls;
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "BLUE PAINT";
	}
	@Override
	public String checkEnetry() {
		return "ITS PAINT THATS BLUE USALLY FOUND IN HARDWARE STORES";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
	@Override 
	public void frameEvent() {
		
		this.setY(this.getY() + 1);
		if (!(Room.isColliding(this))) {
			this.setY(this.getY() + 3);
		} else {
		this.setY(this.getY() - 1);
		}
		if (this.isColliding(j)) {
		Tbox box = new Tbox (j.getX(), j.getY() - 8, 28, 1, "YOU GOT " + Integer.toString(amountToAdd) + " GLOBS OF PAINT (HOWEVER THOSE ARE QUANTAFIED)", true);
		while (amountToAdd != 0) {
		Jeffrey.inventory.addAmmo(this);
		amountToAdd = amountToAdd - 1;
		}
		this.forget();
		}
	}
}
