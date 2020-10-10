package triggers;

import gui.Textbox;
import main.ObjectHandler;

public class TextboxTrigger extends RessesiveTrigger {
	Textbox TriggeredTextbox;
	public TextboxTrigger () {
		TriggeredTextbox = new Textbox ("LOLMEMZ");
		 this.setHitboxAttributes(0, 0, 16, 16);
	}
	public void triggerEvent() {
		if (!TriggeredTextbox.declared()) {
			TriggeredTextbox.changeText(this.getVariantAttribute("message"));
			if (this.getVariantAttribute("font") != null) {
			TriggeredTextbox.changeFont ("resources/sprites/config/" + this.getVariantAttribute("font") + ".txt");
			}
		TriggeredTextbox.declare(0,380);
		TriggeredTextbox.chagePause();
		}
		if(TriggeredTextbox.isDone) {
			TriggeredTextbox.forget();
			ObjectHandler.pause(false);
			this.eventFinished = true;
		}
	}

}
