package gameObjects;

import cutsceens.Cutsceen;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	public CutsceenTrigger () {
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
	public void frameEvent() {
		
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
			this.eventFinished = true;
		}
	}
	}
}
