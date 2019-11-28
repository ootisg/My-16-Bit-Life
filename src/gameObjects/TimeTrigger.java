package gameObjects;


public class TimeTrigger extends Trigger {
	int timer;
	public TimeTrigger() {
		timer = 0;
	}
	@Override
	public void triggerEvent() {
		if(timer == (Integer.parseInt(this.getVariantAttribute("time")) * 30)) {
			this.eventFinished = true;
		}
		timer = timer + 1;
	}
}
