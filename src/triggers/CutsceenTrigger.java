package triggers;

import cutsceens.Cutsceen;
import main.ObjectHandler;

public class CutsceenTrigger extends Trigger {
	public CutsceenTrigger () {
	}
	@Override
	public void triggerEvent () {
		if (this.Triggered()) {
			//Cutsceen.play(Integer.parseInt(this.getVariantAttribute("cutsceen")));
			Cutsceen.playSceen(1);
		}
	}
	@Override 
	public void pausedEvent () {
		//Cutsceen.play(Integer.parseInt(this.getVariantAttribute("cutsceen")));
		Cutsceen.playSceen(1);
		if (!Cutsceen.playing) {
			ObjectHandler.pause(false);
			this.forget();
		}
	}
	
}
