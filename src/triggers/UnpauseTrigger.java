package triggers;

import main.ObjectHandler;

public class UnpauseTrigger extends RessesiveTrigger {
	public UnpauseTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
	}
	public void triggerEvent () {
		ObjectHandler.pause(false);
		this.eventFinished = true;
	}
}