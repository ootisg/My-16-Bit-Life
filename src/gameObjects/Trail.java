package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import players.Player;
import resources.Sprite;
import resources.SpriteParser;

public class Trail extends GameObject implements StickyObject {
	int loopLength = 1;
	long endTime;
	long startTime;
	int height;
	int frame = 0;
	//despawn time is in seconds
	public Trail (int frame, double despawnTime, int height) {
		this.setHitboxAttributes(0, 0, 1, height);
		endTime = (long) (despawnTime * 1000);
		this.height = height;
		this.frame = frame;
	}
	@Override
	public void onDeclare () {
		startTime = System.currentTimeMillis();
	}
	@Override
	public void frameEvent () {
		if (this.isColliding(Player.getActivePlayer())) {
			this.collisionEvent();
		}
		if ((System.currentTimeMillis() - startTime) > endTime ) {
			this.despawnAllCoolLike(20);
		}
	}
	@Override
	public void pausedEvent () {
		endTime = endTime + (System.currentTimeMillis() - startTime);
	}
	public void collisionEvent () {
		
	}
	public void setLoopLength (int newLength) {
		loopLength = newLength;
	}
	public int getLoopLength () {
		return loopLength;
	}
	@Override
	public void setSprite (Sprite newSprite) {
		ArrayList <String> spriteParams = new ArrayList <String> ();
		for (int i = this.frame; i < Sprite.getImage(newSprite.getImagePath()).getWidth(); i = i + loopLength) {
			spriteParams.add("rectangle " + i + " 0 1 "+ this.height + ",");
		}
		super.setSprite(new Sprite (Sprite.getImage(newSprite.getImagePath()),new SpriteParser (spriteParams)));
	}
}
