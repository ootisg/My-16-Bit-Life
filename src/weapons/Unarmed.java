package weapons;

import resources.Sprite;

public class Unarmed extends AimableWeapon {

	public Unarmed(Sprite sprite) {
		super(sprite);
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
	}
	@Override
	public String checkName (){
		return "UNARMED";
	}
	@Override 
	public String getItemType() {
		return "Weapon";
	}

}
