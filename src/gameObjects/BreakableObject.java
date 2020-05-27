package gameObjects;

import java.util.Random;

import main.GameObject;
import resources.Sprite;

public class BreakableObject extends GameObject {
	public BreakableObject () {
		
	}
	public void Break (Sprite [] shards, int shardCount, double minSpeed, double maxSpeed, double minDirection, double maxDirection) {
		Random rand = new Random ();
		for (int i = 0; i < shardCount; i++) {
		
			Sprite currentSprite = shards[rand.nextInt (shards.length)];
			Shard currentShard = new Shard (currentSprite);
			currentShard.setSprite(currentSprite);
			double direciton;
			if ((int)(maxDirection - minDirection) != 0) {
				direciton = rand.nextDouble() + rand.nextInt((int)(maxDirection - minDirection)) + minDirection;
			} else {
				direciton = rand.nextDouble() + minDirection;
			}
			currentShard.setDirection(direciton);
			if (direciton % 6.28 > 0 && direciton %6.28 < 3.14) {
			currentShard.setSpeed(rand.nextDouble() + rand.nextInt((int)(maxSpeed- minSpeed)) + minSpeed);
			} else {
				currentShard.setSpeed(rand.nextDouble() + rand.nextInt((int)(maxSpeed- minSpeed)) + minSpeed + 5);	
			}
			currentShard.setHitboxAttributes(currentSprite.getWidth()/3,currentSprite.getHeight()/3, currentSprite.getWidth()/3, currentSprite.getHeight()/3);
			currentShard.declare(this.getX(),this.getY());
		}
		
	}
	public void Break (Sprite [] shards, double startX, double startY, int shardCount, double minSpeed, double maxSpeed, double minDirection, double maxDirection) {
		Random rand = new Random ();
		for (int i = 0; i < shardCount; i++) {
			Sprite currentSprite = shards[rand.nextInt (shards.length)];
			Shard currentShard = new Shard (currentSprite);
			currentShard.setSprite(currentSprite);
			double direciton;
			if ((int)(maxDirection - minDirection) != 0) {
				direciton = rand.nextDouble() + rand.nextInt((int)(maxDirection - minDirection)) + minDirection;
			} else {
				direciton = rand.nextDouble() + minDirection;
			}
			currentShard.setDirection(direciton);
			if (direciton % 6.28 > 0 && direciton %6.28 < 3.14) {
			currentShard.setSpeed(rand.nextDouble() + rand.nextInt((int)(maxSpeed- minSpeed)) + minSpeed);
			} else {
				currentShard.setSpeed(rand.nextDouble() + rand.nextInt((int)(maxSpeed- minSpeed)) + minSpeed + 5);	
			}
			currentShard.setHitboxAttributes(currentSprite.getWidth()/3,currentSprite.getHeight()/3, currentSprite.getWidth()/3, currentSprite.getHeight()/3);
			currentShard.declare(startX,startY);
		}
		
	}
}
