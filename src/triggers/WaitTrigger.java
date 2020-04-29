package triggers;

import main.ObjectHandler;

public class WaitTrigger extends Trigger {
	int timer;
	public WaitTrigger() {
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
		if(timer == (Integer.parseInt(this.getVariantAttribute("time")) * 30)) {
			ObjectHandler.pause(false);
			this.eventFinished = true;
		}
		timer = timer + 1;
	}
}