package gameObjects;

import main.GameObject;
import resources.Sprite;

public class Slime extends GameObject {
	
	public static final Sprite slime = new Sprite ("resources/sprites/config/slime.txt");
	
	private int lifespan;
	public Slime () {
		this.lifespan = 200;
		this.setSprite (slime);
		this.setHitboxAttributes (0, 0, 2, 2);
	}
	@Override
	public void frameEvent () {
		lifespan --;
		if (lifespan <= 0) {
			this.forget ();
		}
	}
}