package triggers;

import main.GameCode;
import main.GameObject;

public abstract class Trigger extends GameObject {
	Boolean Triggered;
	public Trigger () {
		
	}
	public boolean Triggered () {
		return (Triggered);
	}
	@Override
	public void frameEvent () {
		if (this.isColliding(GameCode.testJeffrey) && !Triggered) {
			Triggered = true;
			//MainLoop.pause when that becomes a thing
		}
	}
}
