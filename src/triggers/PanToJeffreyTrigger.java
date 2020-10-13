package triggers;

import gameObjects.CameraObjectJeffrey;

public class PanToJeffreyTrigger extends RessesiveTrigger {
	CameraObjectJeffrey panner;
	boolean activated = false;
	int panTime;
	public PanToJeffreyTrigger() {
		super();
	}
	@Override
	public void onDeclare () {
		if (this.getVariantAttribute("panTime") != null) {
			if (!this.getVariantAttribute("panTime").equals("nv")) {
				panTime = Integer.parseInt(this.getVariantAttribute("panTime"));
			}
		} else {
			panTime = 0;
		}
		panner = new CameraObjectJeffrey (panTime);
	}
	@Override
	public void triggerEvent (){
		if (!panner.isActivated()) {
			if (activated) {
				this.eventFinished = true;
			} else {
				panner.activate();
				activated = true;
			}
		} 
		if (!this.eventFinished) {
			panner.frameEvent();
		}
	}
}
