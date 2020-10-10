package triggers;

import main.ObjectHandler;

public class WaitTrigger extends RessesiveTrigger {
	long timer;
	boolean inzilaztion;
	long startTime;
	public WaitTrigger() {
		 this.setHitboxAttributes(0, 0, 16, 16);
		timer = 0;
	}
	@Override
	public void triggerEvent() {
		if (!ObjectHandler.isPaused()) {
			ObjectHandler.pause(true);
		}
	}
	@Override
	public void pausedEvent() {
		if (!inzilaztion) {
			inzilaztion = true;
			startTime = System.currentTimeMillis();
		}
		if(timer >= (Integer.parseInt(this.getVariantAttribute("time")) * 1000)) {
			this.eventFinished = true;
		}
		timer = System.currentTimeMillis() - startTime;
	}
}