package gameObjects;

import main.GameCode;
import resources.Sprite;

public class MissleadingStop extends Enemy {
	public MissleadingStop () {
		this.setDeath(false);
		this.setSprite(new Sprite ("resources/sprites/config/Missleading_stop.txt"));
		try{
			if (this.getVariantAttribute("Oriantation").equals("left")) {
			this.getAnimationHandler().setFlipHorizontal(true);
		}
	}catch (NullPointerException e) {
	}	
		this.health = 60;
		this.defence = 30;
		setHitboxAttributes (0,0,44,77);
}
	@Override
	public void frameEvent () {
		if (this.health <= 0) {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey[3] = false;
			GameCode.testJeffrey.status.statusAppliedOnRyan[3]= false;
			GameCode.testJeffrey.status.statusAppliedOnSam[3] = false;
			this.forget();
		}
		if (GameCode.testJeffrey.getX() - this.getX() < 150 &&GameCode.testJeffrey.getX() - this.getX() >= -150 ) {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey[3] = true;
			GameCode.testJeffrey.status.statusAppliedOnRyan[3]= true;
			GameCode.testJeffrey.status.statusAppliedOnSam[3] = true;
		} else {
			GameCode.testJeffrey.status.statusAppliedOnJeffrey[3] = false;
			GameCode.testJeffrey.status.statusAppliedOnRyan[3]= false;
			GameCode.testJeffrey.status.statusAppliedOnSam[3] = false;
		}
	}
}
