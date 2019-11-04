package weapons;

import resources.Sprite;

public class Unarmed extends AimableWeapon {

	public Unarmed(Sprite sprite) {
		super(sprite);
		this.setSprite(new Sprite ("resources/sprites/blank.png"));
		// TODO Auto-generated constructor stub
	}
	@Override
	public String checkName (){
		return "UNARMED";
	}

}
