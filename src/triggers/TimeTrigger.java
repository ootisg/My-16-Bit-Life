package triggers;

public class TimeTrigger extends RessesiveTrigger {
	long timer;
	long startTime;
	boolean inzilaztion;
	public TimeTrigger() {
		this.setHitboxAttributes(0, 0, 16, 16);
		timer = 0;
	}
	@Override
	public void triggerEvent() {
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
