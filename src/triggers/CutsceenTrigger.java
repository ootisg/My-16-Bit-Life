package triggers;

import cutsceens.CutsceenHandler;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	public CutsceenTrigger () {
		 this.setHitboxAttributes(0, 0, 16, 16);
	}
	@Override
	public void triggerEvent () {
		if (this.Triggered()) {
			try {
			CutsceenHandler.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
			} catch (NumberFormatException e) {
				CutsceenHandler.playSceen(1);
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
		CutsceenHandler.playSceen(Integer.parseInt(this.getVariantAttribute("cutsceen")));
		} catch (NumberFormatException e) {
			CutsceenHandler.playSceen(1);
		}
		if (!CutsceenHandler.playing) {
			ObjectHandler.pause(false);
			this.eventFinished = true;
		}
	}
	}
}
