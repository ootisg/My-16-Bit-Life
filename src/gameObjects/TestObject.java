package gameObjects;

import enemys.CreepyButterfly;
import main.GameObject;

public class TestObject extends GameObject {
	public TestObject () {
		this.setSprite(CreepyButterfly.butterflySprite);
		this.setHitboxAttributes (0, 0, 8, 8);
	}
	@Override
	public void frameEvent () {
		if (this.isColliding ("players.Player")) {
			System.out.println("HELLO");
		}
	}
}