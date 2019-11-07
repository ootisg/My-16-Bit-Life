package triggers;

import cutsceens.Cutsceen;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	public CutsceenTrigger () {
		try {
		this.setHitboxAttributes(0, 0, Integer.parseInt(this.getVariantAttribute("width")), Integer.parseInt(this.getVariantAttribute("height")));
		}catch (NumberFormatException e) {
			this.setHitboxAttributes(0, 0, 16, 80);
		}
	}
	@Override
	public void triggerEvent () {
		if (this.Triggered()) {
			try {
			Cutsceen.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
			} catch (NumberFormatException e) {
				Cutsceen.playSceen(1);
			}
		}
	}
	@Override 
	public void pausedEvent () {
		if (this.Triggered()) {
		try {
		Cutsceen.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
		} catch (NumberFormatException e) {
			Cutsceen.playSceen(1);
		}
		if (!Cutsceen.playing) {
			ObjectHandler.pause(false);
			this.forget();
		}
	}
	}
}
