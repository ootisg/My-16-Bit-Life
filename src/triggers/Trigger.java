package triggers;

import main.GameCode;
import main.GameObject;
import resources.Sprite;

public class Trigger extends GameObject {
	Boolean Triggered;
	public Trigger () {
		Triggered = false;
		this.setHitboxAttributes(0, 0, 16, 80);
		
	}
	public boolean Triggered () {
		return (Triggered);
	}
	public void triggerEvent() {
		
	}
	@Override
	public void frameEvent () {
		this.triggerEvent();
		if (GameCode.testJeffrey.isColliding(this) && !Triggered) {
			Triggered = true;
			System.out.println("debug");
		}
	}
}
