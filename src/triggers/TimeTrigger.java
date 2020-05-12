package triggers;

public class TimeTrigger extends Trigger {
	int timer;
	public TimeTrigger() {
		 this.setHitboxAttributes(0, 0, 16, 16);
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
