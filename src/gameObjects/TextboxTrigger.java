package gameObjects;

import gui.Textbox;
import main.ObjectHandler;

public class TextboxTrigger extends Trigger {
	Textbox TriggeredTextbox;
	public TextboxTrigger () {
		TriggeredTextbox = new Textbox ("LOLMEMZ");
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
