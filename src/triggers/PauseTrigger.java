package triggers;

import main.ObjectHandler;

public class PauseTrigger extends Trigger {
	public PauseTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
	}
	public void triggerEvent () {
		ObjectHandler.pause(true);
		this.eventFinished = true;
	}
}
