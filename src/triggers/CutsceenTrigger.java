package triggers;

import cutsceens.Cutsceen;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	public CutsceenTrigger () {
	}
	@Override
	public void triggerEvent () {
		if (this.Triggered()) {
			Cutsceen.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
		}
	}
	@Override 
	public void pausedEvent () {
		if (this.Triggered()) {
		Cutsceen.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
		if (!Cutsceen.playing) {
			ObjectHandler.pause(false);
			this.forget();
		}
	}
	}
}
