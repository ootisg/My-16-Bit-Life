package gameObjects;

import java.util.Random;

import items.Item;
import main.GameObject;
import resources.Sprite;

public class BreakableObject extends GameObject {
	public BreakableObject () {
		
	}
	public void Break (Shard [] shards, int shardCount, double minSpeed, double maxSpeed, double minDirection, double maxDirection) {
		Random rand = new Random ();
		for (int i = 0; i < shardCount; i++) {
			Shard currentShard = shards[rand.nextInt (shards.length)];
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
			currentShard.setHitboxAttributes(currentShard.getSprite().getWidth()/3,currentShard.getSprite().getHeight()/3, currentShard.getSprite().getWidth()/3, currentShard.getSprite().getHeight()/3);
			currentShard.declare(this.getX(),this.getY());
		}
		
	}
	public void Break (Item [] shards, double minSpeed, double maxSpeed, double minDirection, double maxDirection) {
		Random rand = new Random ();
		for (int i = 0; i < shards.length; i++) {
			Item currentShard = shards[i];
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
			currentShard.setHitboxAttributes(currentShard.getSprite().getWidth()/3,currentShard.getSprite().getHeight()/3, currentShard.getSprite().getWidth()/3, currentShard.getSprite().getHeight()/3);
			currentShard.declare(this.getX(),this.getY());
		}
		
	}
	public void Break (Shard [] shards, double startX, double startY, int shardCount, double minSpeed, double maxSpeed, double minDirection, double maxDirection) {
		Random rand = new Random ();
		for (int i = 0; i < shardCount; i++) {
			Shard currentShard = shards[rand.nextInt (shards.length)];
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
			currentShard.setHitboxAttributes(currentShard.getSprite().getWidth()/3,currentShard.getSprite().getHeight()/3, currentShard.getSprite().getWidth()/3, currentShard.getSprite().getHeight()/3);
			currentShard.declare(startX,startY);
		}
		
	}
}
