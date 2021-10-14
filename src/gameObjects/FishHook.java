package gameObjects;

import main.GameObject;
import players.Player;
import resources.ColidableVector;
import resources.LoopableSprite;
import resources.Sprite;
import java.awt.Point;

public class FishHook extends GrabbableObject {
	
	public static final double MAX_SPEED = 10;
	
	public double fastness = 3;
	
	int ogY;
	
	public FishHook() {
		LoopableSprite spr = new LoopableSprite (new Sprite ("resources/sprites/string.png"), 0, 27, -1, 0);
		spr.addStartSprite(new Sprite ("resources/sprites/hook.png"));
		this.setSprite(spr);
	}
	
	@Override
	public void whileGrabbed() {
		Player j = Player.getActivePlayer();
		
		fastness = fastness + 0.5;
		
		this.setY(this.getY() - fastness);
		j.setY(j.getY() - fastness);
		
	}
	
	@Override
	public void whileNotGrabbed() {
		if (this.getY() < ogY) {
			this.setY(this.getY() + 1);
		}
	}
	
	@Override
	public void onRelease () {
		Player.getActivePlayer().vy = -fastness;
		fastness = 0;
	}

	@Override
	public void onDeclare () {
		this.setHitbox((int)this.getX() + 17, 0, 1, (int)this.getY() + 27);
		ogY = (int)this.getY();
	}
	
}
