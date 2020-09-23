package items;


import resources.Sprite;

public class PopcornBag extends Item{
	public PopcornBag () {
		this.setSprite(new Sprite ("resources/sprites/popcorn_bag(no fire).png")); 
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "Popcorn Bag";
	}
	@Override
	public String checkEnetry() {
		return "A DELICIOS BAG OF MICROWAVABLE POPCORN";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
}
