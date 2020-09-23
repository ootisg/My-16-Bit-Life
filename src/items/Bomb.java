package items;


import resources.Sprite;

public class Bomb extends Item{
	public Bomb () {
		this.setSprite(new Sprite ("resources/sprites/config/bomb_Icon.txt")); 
		this.setHitboxAttributes(0, 0, 4, 4);
	}
	@Override
	public String checkName () {
		return "BOMB";
	}
	@Override
	public String checkEnetry() {
		return "ITS A BOMB IT LIKE EXPLODES AND STUFF";
	}
	@Override 
	public String getItemType() {
		return "Ammo";
	}
}
